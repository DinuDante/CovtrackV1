package com.example.covtrack_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Snapshot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.covtrack_v1.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText iphone, ipsswd;
    private Button LoginBtn;
    private ProgressDialog loadbar;
    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginBtn = (Button) findViewById(R.id.loginbtn);
        iphone = (EditText) findViewById(R.id.iphno);
        ipsswd = (EditText) findViewById(R.id.ipswd);
        loadbar = new ProgressDialog(this);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginUser();

            }
        });

    }

    private void LoginUser() {

        String phone = iphone.getText().toString();
        String psswd = ipsswd.getText().toString();

        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please Enter your Phone Number...", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(psswd))
        {
            Toast.makeText(this, "Please Enter your Password...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadbar.setTitle("Logging In");
            loadbar.setMessage("Please wait.....");
            loadbar.setCanceledOnTouchOutside(false);
            loadbar.show();

            AllowAccess(phone, psswd);
        }


    }

    private void AllowAccess(final String phone, final String psswd) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(parentDbName).child(phone).exists())
                {
                    User userData = snapshot.child(parentDbName).child(phone).getValue(User.class);

                    if (userData.getPhone().equals(phone))
                    {
                        if (userData.getPassword().equals(psswd))
                        {
                            Toast.makeText(LoginActivity.this, "Logging in, Please Wait...", Toast.LENGTH_SHORT).show();
                            loadbar.dismiss();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Password do not match, PLease try again", Toast.LENGTH_SHORT).show();
                            loadbar.dismiss();

                        }
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "No Account is assigned with "+phone+". Please register", Toast.LENGTH_SHORT).show();
                    loadbar.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}