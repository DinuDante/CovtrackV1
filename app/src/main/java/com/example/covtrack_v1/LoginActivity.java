package com.example.covtrack_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText iphone, ipsswd;
    private Button LoginBtn;
    private ProgressDialog loadbar;

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

            //Validatephone(name, phone, psswd);
        }


    }
}