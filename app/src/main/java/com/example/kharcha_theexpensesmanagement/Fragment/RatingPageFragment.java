package com.example.kharcha_theexpensesmanagement.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.kharcha_theexpensesmanagement.Fragment.HomeFragment;
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

public class RatingPageFragment extends Fragment {
    EditText useremail;
    String userid;
    private ImageView ratingimage;
    private  float userRate=0;
    private RatingBar mRatingBar;

    private Button mSendButton;

    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser currentUser;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_rating_page, container, false);
        useremail = view.findViewById(R.id.text_email);
        mRatingBar = view.findViewById(R.id.ratingBar);
        ratingimage=view.findViewById(R.id.imoji_img);

        mSendButton = view.findViewById(R.id.btnSubmit);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();


        db = FirebaseFirestore.getInstance();
        userid = auth.getCurrentUser().getUid();

        useremail.setFocusable(false);

        userData();
        mRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            String ratingText = String.format(Locale.getDefault(), "%.1f", rating);
            Toast.makeText(getActivity(), "Rating: " + ratingText, Toast.LENGTH_SHORT).show();
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
                db.collection("rating_info").document().set(ratingmodel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Thanks for Rating", Toast.LENGTH_SHORT).show();
                            Intent i1 = new Intent(getActivity(), HomeFragment.class);
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

        useremail.setText(currentUser.getEmail());
        mRatingBar.setRating(userRate);
    }

    private void  animateImage(ImageView ratingImage){
        ScaleAnimation scaleanimation=new ScaleAnimation(0,1f,0,1f, Animation.RELATIVE_TO_SELF,0.5F,Animation.RELATIVE_TO_SELF,0.5f);
        scaleanimation.setFillAfter(true);
        scaleanimation.setDuration(200);
        ratingImage.startAnimation(scaleanimation);
    }
}








//package com.example.kharcha_theexpensesmanagement;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.RatingBar;






//import android.widget.Toast;
//
//import java.util.Locale;
//
//public class RatingPageFragment extends Fragment {
//
//
//
//    private RatingBar mRatingBar;
//    // private TextInputEditText mFeedback;
//    private Button mSendButton;
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view =inflater.inflate(R.layout.fragment_rating_page, container, false);
//
//
//        mRatingBar = view.findViewById(R.id.ratingBar);
//        //   mFeedback = findViewById(R.id.etFeedback);
//        mSendButton = view.findViewById(R.id.btnSubmit);
//
//        mRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
//            String ratingText = String.format(Locale.getDefault(), "%.1f", rating);
//            Toast.makeText(getActivity(), "Rating: " + ratingText, Toast.LENGTH_SHORT).show();
//        });
//
//        mSendButton.setOnClickListener(view1 -> {
//            //  String feedbackText = mFeedback.getText().toString().trim();
////
////            if (TextUtils.isEmpty(feedbackText)) {
////                mFeedback.setError("Please enter your feedback");
////                return;
////            }
//
//
//            float rating = mRatingBar.getRating();
//
//            // mFeedback.setText("");
//            mRatingBar.setRating(0);
//
//
//            Toast.makeText(getActivity(), "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
//        });
//        return view;
//    }
//}