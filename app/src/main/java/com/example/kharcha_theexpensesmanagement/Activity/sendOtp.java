package com.example.kharcha_theexpensesmanagement.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kharcha_theexpensesmanagement.R;
import com.example.kharcha_theexpensesmanagement.databinding.ActivitySendOtpBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class sendOtp extends AppCompatActivity {
    ImageView Back;
    private ActivitySendOtpBinding binding;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    // final EditText inputmobile = findViewById(R.id.inputMobile);
    // Button buttonGetOtp = findViewById(R.id.buttonGetOtp);
    //   final ProgressBar progressBar = findViewById(R.id.progressBar);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_send_otp);

        binding = ActivitySendOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        Back = findViewById(R.id.back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(sendOtp.this, Logins.class);
                startActivity(i1);
            }
        });
        mAuth=FirebaseAuth.getInstance();
        binding.buttonGetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.inputMobile.getText().toString().trim().isEmpty()) {
                    Toast.makeText(sendOtp.this, "Enter Mobile", Toast.LENGTH_SHORT).show();
                } else if (binding.inputMobile.getText().toString().trim().length()!=10) {
                    Toast.makeText(sendOtp.this, "Enter valid Mobile", Toast.LENGTH_SHORT).show();
                } else {
                    otpSend();
                }

            }
        });
//        final EditText inputmobile = findViewById(R.id.inputMobile);
//        Button buttonGetOtp = findViewById(R.id.buttonGetOtp);
//        final ProgressBar progressBar = findViewById(R.id.progressBar);
//        buttonGetOtp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (inputmobile.getText().toString().trim().isEmpty()) {
//                    Toast.makeText(sendOtp.this, "Enter Mobile", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                progressBar.setVisibility(View.VISIBLE);
//                buttonGetOtp.setVisibility(View.INVISIBLE);
//
//                PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                        "+91" + inputmobile.getText().toString(),
//                        60,
//                        TimeUnit.SECONDS,
//                        sendOtp.this,
//                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                            @Override
//                            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//                                progressBar.setVisibility(View.GONE);
//                                buttonGetOtp.setVisibility(View.VISIBLE);
//                            }
//
//                            @Override
//                            public void onVerificationFailed(FirebaseException e) {
//                                progressBar.setVisibility(View.GONE);
//                                buttonGetOtp.setVisibility(View.VISIBLE);
//                                Toast.makeText(sendOtp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onCodeSent(@NotNull String verificationId, @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                                progressBar.setVisibility(View.GONE);
//                                buttonGetOtp.setVisibility(View.VISIBLE);
//                                Intent i1 = new Intent(getApplicationContext(), VerifyOtp.class);
//                                i1.putExtra("mobile", inputmobile.getText().toString());
//                                i1.putExtra("VerificationId", verificationId);
//                                startActivity(i1);
//                            }
//                        }
//                );
//
//            }
//        });
    }

    private void otpSend() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.buttonGetOtp.setVisibility(View.INVISIBLE);
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NotNull PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(@NotNull FirebaseException e) {
                binding.progressBar.setVisibility(View.GONE);
                binding.buttonGetOtp.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCodeSent(@NotNull String verificationId,
                                   @NotNull PhoneAuthProvider.ForceResendingToken token) {

                binding.progressBar.setVisibility(View.GONE);
                binding. buttonGetOtp.setVisibility(View.VISIBLE);
                Intent i1 = new Intent(getApplicationContext(), VerifyOtp.class);
                i1.putExtra("mobile", binding.inputMobile.getText().toString().trim());
                i1.putExtra("VerificationId", verificationId);
                startActivity(i1);
            }
        };
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+binding.inputMobile.getText().toString().trim())       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }
}