package com.abhishek.collegecu.LoginPages;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.abhishek.collegecu.R;


public class SignUpActivity extends AppCompatActivity {
    private  EditText rollno , name , email, password;
    private Button registerbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        rollno = findViewById(R.id.signrollno);
        name = findViewById(R.id.studentName);
        email = findViewById(R.id.signemail);
        password = findViewById(R.id.signpassword);

        registerbtn = findViewById(R.id.registerbtn);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rollno.getText().toString().isEmpty() |
                        name.getText().toString().isEmpty() |
                        email.getText().toString().isEmpty() |
                        password.getText().toString().isEmpty()){

                    rollno.setError("Enter Roll No.");
                    name.setError("Enter Name");
                    email.setError("Enter E-mail");
                    password.setError("Enter Password");

                }
            }
        });
    }
}