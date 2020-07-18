package com.example.covtrack_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button crtacc;
    private EditText rname, rphno, rpswd, rcpswd;
    private ProgressDialog loadbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        crtacc = (Button) findViewById(R.id.regdbtn);
        rname = (EditText) findViewById(R.id.rname);
        rphno = (EditText) findViewById(R.id.rphno);
        rpswd = (EditText) findViewById(R.id.rpswd);
        rcpswd = (EditText) findViewById(R.id.rcpswd);
        loadbar = new ProgressDialog(this);

        crtacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });


    }


    private void CreateAccount()
    {
        String name = rname.getText().toString();
        String phone = rphno.getText().toString();
        String psswd = rpswd.getText().toString();
        String cpsswd = rcpswd.getText().toString();

        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please Enter your Name...", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please Enter your Phone Number...", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(psswd))
        {
            Toast.makeText(this, "Please Enter your Password...", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(cpsswd))
        {
            Toast.makeText(this, "Please Confirm your Password...", Toast.LENGTH_SHORT).show();
        }

        else if(!(psswd.equals(cpsswd)))
        {
            Toast.makeText(this, "Passwords Do not Match...", Toast.LENGTH_SHORT).show();

        }
        else
        {
            loadbar.setTitle("Create Account");
            loadbar.setMessage("Please wait while your credentials are being validated");
            loadbar.setCanceledOnTouchOutside(false);
            loadbar.show();

            Validatephone(name, phone, psswd);
        }




    }

    private void Validatephone(final String name, final String phone, final String psswd) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (!(datasnapshot.child("Users").child(phone).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", phone);
                    userdataMap.put("password", psswd);
                    userdataMap.put("name", name);

                    RootRef.child("Users").child(phone).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isComplete())
                            {
                                Toast.makeText(RegisterActivity.this, "Account Successfully Created", Toast.LENGTH_SHORT).show();
                                loadbar.dismiss();

                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                loadbar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Network Error, Please try Again", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Phone Number Already Registered/Linked", Toast.LENGTH_SHORT).show();
                    loadbar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try using another phone number", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}