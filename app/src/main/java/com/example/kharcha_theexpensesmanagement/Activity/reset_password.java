package com.example.kharcha_theexpensesmanagement.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kharcha_theexpensesmanagement.R;

public class reset_password extends AppCompatActivity {
    private Button submit_RESET;
    EditText reset_password, confirm_reset_password;
    ImageView Back;
    boolean PasswordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        submit_RESET = findViewById(R.id.Submit_RESET);
        reset_password = findViewById(R.id.reset_password);
        confirm_reset_password = findViewById(R.id.confirm_reset_password);
        submit_RESET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(reset_password.this, navigationbar.class);
                startActivity(i1);
            }
        });
        Back = findViewById(R.id.back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(reset_password.this, VerifyOtp.class);
                startActivity(i1);
            }
        });
        reset_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= reset_password.getRight() - reset_password.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = reset_password.getSelectionEnd();
                        if (PasswordVisible) {
                            reset_password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.key_, 0, R.drawable.visibility_off, 0);
                            //hide password
                            reset_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            PasswordVisible = false;
                        } else {
                            reset_password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.key_, 0, R.drawable.visibility, 0);
                            //hide password
                            reset_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            PasswordVisible = true;
                        }
                        reset_password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        confirm_reset_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= confirm_reset_password.getRight() - confirm_reset_password.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = confirm_reset_password.getSelectionEnd();
                        if (PasswordVisible) {
                            confirm_reset_password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.key_, 0, R.drawable.visibility_off, 0);
                            //hide password
                            confirm_reset_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            PasswordVisible = false;
                        } else {
                            confirm_reset_password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.key_, 0, R.drawable.visibility, 0);
                            //hide password
                           confirm_reset_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            PasswordVisible = true;
                        }
                        confirm_reset_password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

    }
}