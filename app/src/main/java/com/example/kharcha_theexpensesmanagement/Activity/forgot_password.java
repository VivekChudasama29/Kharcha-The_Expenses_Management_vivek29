package com.example.kharcha_theexpensesmanagement.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kharcha_theexpensesmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

public class forgot_password extends AppCompatActivity {
    Button forget_submit;

    EditText enter_email;
    ImageView Back;
    FirebaseAuth auth;
    FirebaseFirestore db;

    ProgressDialog progressDialog;
    CollectionReference collectionReference;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        enter_email = findViewById(R.id.forget_email);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        collectionReference=db.collection("user");
        forget_submit = findViewById(R.id.Submit_FORGOT);
        forget_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = enter_email.getText().toString().trim();

//                progressDialog = new ProgressDialog(getApplicationContext());
//                progressDialog.setCancelable(false);
//                progressDialog.setMessage("LOADING.........");
//                progressDialog.show();
                if(email.isEmpty()){
                    enter_email.setError("Email can not be empty");
                } else if (!email.matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")) {
                    enter_email.setError("Please enter valid Email");
                }else {
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(forgot_password.this, "Sent Successfully.......", Toast.LENGTH_SHORT).show();

                                db.collection("user").whereEqualTo("user_email", email).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        Registration.UserModel userModel = new Registration.UserModel();
                                        //        userModel.setUser_password();
                                    }
                                });
                            } else {
                                Toast.makeText(forgot_password.this, "Failed........", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        Back = findViewById(R.id.back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(forgot_password.this, Logins.class);
                startActivity(i1);
            }
        });
    }
}