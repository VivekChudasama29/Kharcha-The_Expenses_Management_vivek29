package com.example.kharcha_theexpensesmanagement.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import databinding.ActivityVerifyOtpBinding;
import com.example.kharcha_theexpensesmanagement.R;
import com.example.kharcha_theexpensesmanagement.databinding.ActivityVerifyOtpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class VerifyOtp extends AppCompatActivity {
ImageView Back;
    private EditText inputcode1, inputcode2, inputcode3, inputcode4, inputcode5, inputcode6;
    private ActivityVerifyOtpBinding binding;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_verify_otp);
        binding = ActivityVerifyOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        edittextInput();

        binding.textMobile.setText(String.format(
                "+91-%s", getIntent().getStringExtra("mobile")
        ));

        verificationId = getIntent().getStringExtra("VerificationId");

        binding.ResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(VerifyOtp.this, "OTP Send Successfully!", Toast.LENGTH_SHORT).show();

            }
        });

        binding.buttonVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.progressBar2.setVisibility(View.VISIBLE);
                binding.buttonVerifyOtp.setVisibility(View.INVISIBLE);
                if (binding.inputcode1.getText().toString().isEmpty() ||
                        binding.inputcode2.getText().toString().isEmpty() ||
                        binding.inputcode3.getText().toString().isEmpty() ||
                        binding.inputcode4.getText().toString().isEmpty() ||
                        binding.inputcode5.getText().toString().isEmpty() ||
                        binding.inputcode6.getText().toString().isEmpty()) {
                    Toast.makeText(VerifyOtp.this, "Otp is not valid!", Toast.LENGTH_SHORT).show();
                } else {
                    if (verificationId != null) {
                        String code = binding.inputcode1.getText().toString().trim() +
                                binding.inputcode2.getText().toString().trim() +
                                binding.inputcode3.getText().toString().trim() +
                                binding.inputcode4.getText().toString().trim() +
                                binding.inputcode5.getText().toString().trim() +
                                binding.inputcode6.getText().toString().trim();

                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
                        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    binding.progressBar2.setVisibility(View.VISIBLE);
                                    binding.buttonVerifyOtp.setVisibility(View.INVISIBLE);
                                    Intent intent = new Intent(VerifyOtp.this, reset_password.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else {
                                    binding.progressBar2.setVisibility(View.GONE);
                                    binding.buttonVerifyOtp.setVisibility(View.VISIBLE);
                                    Toast.makeText(VerifyOtp.this, "OTP is not Valid!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }

            }
        });


//        TextView textMobile=findViewById(R.id.inputMobile);
//        textMobile.setText(String.format(
//                "+91-%s",getIntent().getStringExtra("mobile")
//        ));
//        inputcode1 = findViewById(R.id.inputcode1);
//        inputcode2 = findViewById(R.id.inputcode2);
//        inputcode3 = findViewById(R.id.inputcode3);
//        inputcode4 = findViewById(R.id.inputcode4);
//        inputcode5 = findViewById(R.id.inputcode5);
//        inputcode6 = findViewById(R.id.inputcode6);
//
//        setupOTPInputs();
//    }
//
//    private void setupOTPInputs() {
//        inputcode1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.toString().trim().isEmpty()) {
//                    inputcode2.requestFocus();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        inputcode2.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.toString().trim().isEmpty()) {
//                    inputcode3.requestFocus();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        inputcode3.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.toString().trim().isEmpty()) {
//                    inputcode4.requestFocus();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        inputcode4.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.toString().trim().isEmpty()) {
//                    inputcode5.requestFocus();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        inputcode5.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.toString().trim().isEmpty()) {
//                    inputcode6.requestFocus();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
    }

    private void edittextInput() {
        binding.inputcode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.inputcode2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.inputcode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.inputcode3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.inputcode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.inputcode4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.inputcode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.inputcode5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.inputcode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.inputcode6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Back = findViewById(R.id.back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(VerifyOtp.this, sendOtp.class);
                startActivity(i1);
            }
        });
    }
}