package com.example.kharcha_theexpensesmanagement.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kharcha_theexpensesmanagement.Fragment.HomeFragment;
import com.example.kharcha_theexpensesmanagement.Model.feedbackModel;
import com.example.kharcha_theexpensesmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Date;

public class FeedbackFragment extends Fragment {
    FirebaseAuth auth;
    AppCompatButton btn;
    FirebaseFirestore db;
    FirebaseUser currentUser;
    EditText feedbackEmail;
    EditText feedbackDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view=inflater.inflate(R.layout.fragment_feedback, container, false);


        feedbackEmail = view.findViewById(R.id.f_email);
        feedbackDescription = view.findViewById(R.id.feedback_information);
        btn = view.findViewById(R.id.Feedback);
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

                feedbackModel feedbackmodel = new feedbackModel(user_email,user_feedback, DateFormat.getDateTimeInstance().format(new Date()));

                db.collection("feedback_info").document().set(feedbackmodel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Thanks for Feedback", Toast.LENGTH_SHORT).show();
                            Intent i1=new Intent(getActivity(), HomeFragment.class);
                            startActivity(i1);
                        } else {
                            Toast.makeText(getActivity(), "Please retry!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return view;
    }

    private void userData() {
        feedbackEmail.setText(currentUser.getEmail());
    }
}
