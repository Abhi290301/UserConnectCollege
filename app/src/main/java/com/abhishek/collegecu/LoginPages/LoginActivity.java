package com.abhishek.collegecu.LoginPages;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.abhishek.collegecu.R;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class LoginActivity extends AppCompatActivity {
    EditText rollNo , password;
    TextView forgotPassword;
    Button loginBtn;
    CircularProgressIndicator progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rollNo = findViewById(R.id.rollno);
        password = findViewById(R.id.password);
        forgotPassword = findViewById(R.id.forgotPassword);
        loginBtn = findViewById(R.id.loginbtn);
        progressBar = findViewById(R.id.progressbar);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rollNo.getText().toString().isEmpty()) {
                    rollNo.setError("Fill Roll No");
                    if(password.getText().toString().isEmpty()){
                        password.setError("Fill password");

                    }



                }
                loginBtn.setVisibility(View.INVISIBLE);
                loginBtn.setVisibility(View.VISIBLE);
            }
        });

    }
}