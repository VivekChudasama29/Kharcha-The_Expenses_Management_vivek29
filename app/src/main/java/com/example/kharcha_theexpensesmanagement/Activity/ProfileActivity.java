package com.example.kharcha_theexpensesmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.kharcha_theexpensesmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;
import java.util.UUID;

public class ProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST_CODE = 71;
    Button edit;
    ImageView imageView;
    //    ImageView click;
    ImageView Back;
    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser currentUser;
    FirebaseStorage st;
    StorageReference sr;
    TextView user_email;
    TextView user_mobile;
    TextView user_fname;
    TextView user_lname;
    ////    Uri uri;
//    Uri coverUri;
    EditText up_user_lname;
    EditText up_user_mobile;
    EditText up_user_fname;
    ImageView edit_image;

    Button logout;
    String uid;
    Uri url;
    Uri file;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        st = FirebaseStorage.getInstance();
        sr = st.getReference();

        uid = auth.getCurrentUser().getUid();
        imageView = findViewById(R.id.profile_image);
        user_email = findViewById(R.id.Profile_email);
        user_mobile = findViewById(R.id.User_Mobile);
        user_fname = findViewById(R.id.profile_name1);

        //  user_lname = findViewById(R.id.profile_name2);

        logout = findViewById(R.id.sign_out_button);
//        click = findViewById(R.id.add_img);
//        click.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slctImg();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // FirebaseAuth.getInstance().signOut();
                Intent i1 = new Intent(ProfileActivity.this, Logins.class);
                startActivity(i1);
                finish();
            }
        });
        db.collection("user")
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
                                    Glide.with(ProfileActivity.this)
                                            .load(imageUrl)
                                            .centerCrop()
                                            .placeholder(R.drawable.person_)
                                            .transform(new CircleCrop())
                                            .into(imageView);
                                    user_email.setText(doc.getString("user_email"));
                                    imageView.setImageURI(url);
                                    user_fname.setText(doc.getString("fullname"));
                                    user_mobile.setText(doc.getString("mobile_No"));
                                }
                            }
                        });
//        //   userData();
        edit = findViewById(R.id.edit_profile_button);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        Back = findViewById(R.id.back_profile);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(ProfileActivity.this, navigationbar.class);
                startActivity(i1);
            }
        });
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(ProfileActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_profile);

        up_user_fname = dialog.findViewById(R.id._fullname);
        up_user_mobile = dialog.findViewById(R.id.mobile);

        db.collection("user")
                .document(uid)
                .get()
                .addOnCompleteListener
                        (new OnCompleteListener<DocumentSnapshot>() {

                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                if (task.isSuccessful()) {
                                    DocumentSnapshot doc = task.getResult();
                                    up_user_fname.setText(doc.getString("fullname"));
                                    up_user_mobile.setText(doc.getString("mobile_No"));

                                }
                            }
                        });
        Button createLayout = dialog.findViewById(R.id.editbtn);
        createLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.collection("user").document(uid).update("fullname", up_user_fname.getText().toString(), "mobile_No", up_user_mobile.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ProfileActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(ProfileActivity.this, ProfileActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                });
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void slctImg() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            file = data.getData();
            Glide.with(ProfileActivity.this).load(file).into(imageView);
            Uri fileUri = file;
            StorageReference imageRef = sr.child("images/" + UUID.randomUUID().toString());
            UploadTask uploadTask = imageRef.putFile(fileUri);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri downloadUrl) {
                            FirebaseFirestore databaseRef = FirebaseFirestore.getInstance();
                            db.collection("user").document(uid).update("profileImageUrl", downloadUrl.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ProfileActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            });
        }
    }
}