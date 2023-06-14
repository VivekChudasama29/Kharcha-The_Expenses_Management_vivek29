package com.example.kharcha_theexpensesmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kharcha_theexpensesmanagement.Model.feedbackModel;
import com.example.kharcha_theexpensesmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Date;

public class Feedback extends AppCompatActivity {
    FirebaseAuth auth;
    AppCompatButton btn;
    FirebaseFirestore db;
    FirebaseUser currentUser;
    TextView feedbackEmail;
    EditText feedbackDescription;
    ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedbackEmail = findViewById(R.id.f_email);
        feedbackDescription = findViewById(R.id.feedback_information);
        btn = findViewById(R.id.Feedback);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        userData();
        feedbackEmail.setFocusable(false);
        db = FirebaseFirestore.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_email = feedbackEmail.getText().toString().trim();
                String user_feedback = feedbackDescription.getText().toString().trim();
                if(user_feedback.isEmpty()) {
                    feedbackDescription.setError("Feedback Details can not be empty");
                } else {
                    feedbackModel feedbackmodel = new feedbackModel(user_email, user_feedback, DateFormat.getDateTimeInstance().format(new Date()));

                    db.collection("feedback_info").document().set(feedbackmodel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Feedback.this, "Thanks for Feedback", Toast.LENGTH_SHORT).show();
                                feedbackDescription.setText("");
                            } else {
                                Toast.makeText(Feedback.this, "Please Retry!!", Toast.LENGTH_SHORT).show();
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
                Intent i1 = new Intent(Feedback.this, navigationbar.class);
                startActivity(i1);
            }
        });
    }

    private void userData() {
        feedbackEmail.setText(currentUser.getEmail());
    }
}