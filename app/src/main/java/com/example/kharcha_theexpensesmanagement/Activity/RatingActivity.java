package com.example.kharcha_theexpensesmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kharcha_theexpensesmanagement.Model.ratingModel;
import com.example.kharcha_theexpensesmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


public class RatingActivity extends AppCompatActivity {
    TextView useremail;
    String userid;
    private ImageView ratingimage,Back;
    private  float userRate=0;
    private RatingBar mRatingBar;

    private Button mSendButton;

    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser currentUser;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        useremail = findViewById(R.id.text_email);
        mRatingBar = findViewById(R.id.ratingBar);
        ratingimage=findViewById(R.id.imoji_img);

        mSendButton = findViewById(R.id.btnSubmit);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();


        db = FirebaseFirestore.getInstance();
        userid = auth.getCurrentUser().getUid();


        useremail.setFocusable(false);
        userData();
        mRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            String ratingText = String.format(Locale.getDefault(), "%.1f", rating);
            Toast.makeText(RatingActivity.this, "Rating: " + ratingText, Toast.LENGTH_SHORT).show();
        });
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating <= 1 || rating <= 1.5){
                    ratingimage.setImageResource(R.drawable.imoji_three);
                }else if(rating <= 2 || rating <= 2.5){
                    ratingimage.setImageResource(R.drawable.imoji_four);
                }else if(rating <= 3 || rating <= 3.5){
                    ratingimage.setImageResource(R.drawable.imoji_one);
                }else if(rating <= 4 || rating <= 4.5){
                    ratingimage.setImageResource(R.drawable.imoji_five);
                }else if(rating <= 5){
                    ratingimage.setImageResource(R.drawable.imoji_two);
                }
                // animate imoji image
                animateImage(ratingimage);
                //selected rating by user
                userRate=rating;
            }
        });
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_email = useremail.getText().toString().trim();
                ratingModel ratingmodel = new ratingModel(user_email,userRate, DateFormat.getDateTimeInstance().format(new Date()));
                if(userRate<=0.0){
                    Toast.makeText(RatingActivity.this, "Please Give a Rate", Toast.LENGTH_SHORT).show();
                }else {
                    db.collection("rating_info").document().set(ratingmodel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if (task.isSuccessful()) {
//                            ProgressDialog progressDialog=new ProgressDialog(getApplicationContext());
//                                            progressDialog.setTitle("UPLOADING......");
//                                            progressDialog.show();
                                Toast.makeText(RatingActivity.this, "Thanks for Rating", Toast.LENGTH_SHORT).show();
                                mRatingBar.setRating(0);
//                            Intent i1 = new Intent(retrieve.this, CommunityFragment.class);
//                            startActivity(i1);
                            } else {
                                Toast.makeText(RatingActivity.this, "Please retry!!", Toast.LENGTH_SHORT).show();
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
                Intent i1 = new Intent(RatingActivity.this, navigationbar.class);
                startActivity(i1);
            }
        });
    }
    private void userData() {
        useremail.setText(currentUser.getEmail());
        mRatingBar.setRating(userRate);
    }
    private void animateImage(ImageView ratingimage) {
        ScaleAnimation scaleanimation=new ScaleAnimation(0,1f,0,1f, Animation.RELATIVE_TO_SELF,0.5F,Animation.RELATIVE_TO_SELF,0.5f);
        scaleanimation.setFillAfter(true);
        scaleanimation.setDuration(200);
        ratingimage.startAnimation(scaleanimation);
    }
}