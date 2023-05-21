package com.abhishek.collegecu.LoginPages;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.abhishek.collegecu.R;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {
    TextView welcome,atalconnect;
    LottieAnimationView animationView;
    private static int splashTimeout = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide Task Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        welcome = findViewById(R.id.text1);
        atalconnect = findViewById(R.id.text2);
        animationView = findViewById(R.id.animationLottie);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext() , LoginWelcomePage.class);
                startActivity(intent);
                finish();
            }
        },splashTimeout);


        Animation animation = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.animation2);
        welcome.startAnimation(animation);
        Animation animation2 = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.animation1);
        atalconnect.startAnimation(animation2);

    }
}