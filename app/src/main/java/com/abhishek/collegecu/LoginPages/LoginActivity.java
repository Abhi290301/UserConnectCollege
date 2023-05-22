package com.abhishek.collegecu.LoginPages;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.abhishek.collegecu.R;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class LoginActivity extends AppCompatActivity {
    EditText rollNo , password;
    TextView forgotPassword;
    Button loginBtn;
    CircularProgressIndicator progressBar;
    private int counter = 3;
    final int countdownDuration = 3 * 60 * 1000; // 3 minutes in milliseconds
    AlertDialog alertDialog;
    CountDownTimer countDownTimer;

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
                    if (password.getText().toString().isEmpty()) {
                        password.setError("Fill password");
                        counter--;
                    }
                }

                if (counter == 0) {
                    loginBtn.setEnabled(false);
                    // Display the countdown alert dialog

                }


            }
        });
    }




}