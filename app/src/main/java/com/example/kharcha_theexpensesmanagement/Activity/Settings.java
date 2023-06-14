package com.example.kharcha_theexpensesmanagement.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.kharcha_theexpensesmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

public class Settings extends AppCompatActivity {
    CardView feedbackclick;
    CardView rateclick;
    CardView updateclick;
    CardView logoutclick;
    CardView about;
    ImageView imageView;
    String uid;
    Uri url;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    FirebaseUser currentUser;
    TextView user_fname;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        feedbackclick = findViewById(R.id.feedback_click);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        currentUser = auth.getCurrentUser();
        uid = auth.getCurrentUser().getUid();
        imageView = findViewById(R.id.profile_image);
        user_fname = findViewById(R.id.profile_full_name);
        back=findViewById(R.id.back_setting);
        feedbackclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Settings.this, Feedback.class);
                startActivity(i1);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(Settings.this,navigationbar.class);
                startActivity(i1);
            }
        });
        rateclick = findViewById(R.id.rate_click);
        rateclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Settings.this, RatingActivity.class);
                startActivity(i1);
            }
        });

        about = findViewById(R.id.about_click);
about.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i1 = new Intent(Settings.this, About_kharcha.class);
        startActivity(i1);
    }
});

        updateclick = findViewById(R.id.profile_click);
        updateclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Settings.this, ProfileActivity.class);
                startActivity(i1);
            }
        });



        logoutclick=findViewById(R.id.logout_click);
        logoutclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog dialog=new AlertDialog.Builder(Settings.this)
                        .setTitle("Kharcha")
                        .setMessage("Are You Sure")
                        .setPositiveButton("Yes",null)
                        .setNegativeButton("No",null)
                        .show();

                Button positive=dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences sharedPreferences=getSharedPreferences("LOGIN",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();

                        editor.putBoolean("hasLoggedIn",false);
                        editor.apply();
                        Toast.makeText(Settings.this, "LOGOUT SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        Intent i1=new Intent(Settings.this, Logins.class);
                        startActivity(i1);
                        finishAffinity();
                        //dialog.dismiss();
                    }
                });
            }
        });
        firestore.collection("user")
                .document(uid)
                .get()
                .addOnCompleteListener
                        (new OnCompleteListener<DocumentSnapshot>() {

                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                if (task.isSuccessful()) {
                                    DocumentSnapshot doc = task.getResult();

                                    //set name next to Name TextView
                                    String imageUrl = doc.getString("profileImageUrl");
                                    Glide.with(Settings.this)
                                            .load(imageUrl)
                                            .centerCrop()
                                            .placeholder(R.drawable.person_)
                                            .transform(new CircleCrop())
                                            .into(imageView);
                                    imageView.setImageURI(url);
                                    user_fname.setText(doc.getString("fullname"));


                                    //    (editText3.setText(doc.getLong("Contact").toString());


                                }
                            }
                        });



    }
}