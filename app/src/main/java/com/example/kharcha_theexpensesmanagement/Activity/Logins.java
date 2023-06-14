package com.example.kharcha_theexpensesmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kharcha_theexpensesmanagement.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

public class Logins extends AppCompatActivity {
    boolean PasswordVisible;
    private EditText email, password;
    AppCompatButton SubBtn;
    private TextView redirectregister;
    FirebaseAuth auth;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    TextView orlogin;
    TextView Forgotpass;

    Dialog dialog;
    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 100;
    public static String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logins);

        auth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.UserName);
        password = findViewById(R.id.Password_);
        redirectregister = findViewById(R.id.txtlogin);
        SubBtn = findViewById(R.id.Loginbtn);

        db = FirebaseFirestore.getInstance();
        dialog=new Dialog(Logins.this);
        dialog.setContentView(R.layout.custom_dialog_layout);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            //dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations=R.style.animation;

        Button okay=dialog.findViewById(R.id.btn_okay);
       // Button cancel=dialog.findViewById(R.id.btn_cancel);

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Logins.this, "Successfully!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                Intent i2 = new Intent(Logins.this, navigationbar.class);
                                        startActivity(i2);
                                        finishAffinity();
            }
        });



        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= password.getRight() - password.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = password.getSelectionEnd();
                        if (PasswordVisible) {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.key_, 0, R.drawable.visibility_off, 0);
                            //hide password
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            PasswordVisible = false;
                        } else {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.key_, 0, R.drawable.visibility, 0);
                            //hide password
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            PasswordVisible = true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        SubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putBoolean("hasLoggedIn", true);
                editor.apply();


                String user_email = email.getText().toString().trim();
                String user_password = password.getText().toString().trim();

                if (!user_email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(user_email).matches()) {
                    if (!user_password.isEmpty()) {


                        auth.signInWithEmailAndPassword(user_email, user_password)

                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        dialog.show();
                                        Toast.makeText(Logins.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                               }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(Logins.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        password.setError("Password Cannot Be Empty");
                    }
                } else if (user_email.isEmpty()) {
                    email.setError("Email cannot be Empty");
                } else {
                    email.setError("Please Enter Valid Email");
                }
            }
        });
        Forgotpass = findViewById(R.id.forgotpass);
        Forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Logins.this, forgot_password.class);
                startActivity(i1);
                finish();
            }
        });
        redirectregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Logins.this, Registration.class);
                startActivity(i1);
                finish();
            }
        });



    }





}