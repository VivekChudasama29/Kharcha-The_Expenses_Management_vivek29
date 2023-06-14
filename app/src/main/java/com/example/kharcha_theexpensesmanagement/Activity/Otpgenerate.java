package com.example.kharcha_theexpensesmanagement.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kharcha_theexpensesmanagement.R;

public class Otpgenerate extends AppCompatActivity {
    private Button submit_otp;
    ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpgenerate);
        submit_otp = findViewById(R.id.Submit_OTP);
        submit_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Otpgenerate.this, reset_password.class);
                startActivity(i1);
            }
        });
        Back = findViewById(R.id.back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Otpgenerate.this, forgot_password.class);
                startActivity(i1);
            }
        });
    }
}