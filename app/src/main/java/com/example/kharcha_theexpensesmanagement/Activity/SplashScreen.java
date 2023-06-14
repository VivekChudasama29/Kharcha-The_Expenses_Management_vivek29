package com.example.kharcha_theexpensesmanagement.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kharcha_theexpensesmanagement.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
       // Intent IHome = new Intent(SplashScreen.this, MainActivity.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences=getSharedPreferences("LOGIN",MODE_PRIVATE);
                boolean hasLoggedIn=sharedPreferences.getBoolean("hasLoggedIn",false);
                Intent i1;
                if(hasLoggedIn)
                {
                    i1=new Intent(SplashScreen.this, navigationbar.class);
                    startActivity(i1);
                    finish();
                }
                else
                {
                    i1=new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i1);
                    finish();
                }

                startActivity(i1);
                finish();
            }
        }, 1400);


    }
}