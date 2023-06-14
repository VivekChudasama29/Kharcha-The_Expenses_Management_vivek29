package com.example.kharcha_theexpensesmanagement.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.kharcha_theexpensesmanagement.Activity.Logins;
import com.example.kharcha_theexpensesmanagement.Activity.ProfileActivity;
import com.example.kharcha_theexpensesmanagement.Activity.navigationbar;
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

import java.util.UUID;


public class SettingFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST_CODE = 71;
    private static final int RESULT_OK = 0;
    Button edit;
    ImageView imageView;
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
    EditText up_user_lname;
    EditText up_user_mobile;
    EditText up_user_fname;
    ImageView edit_image;

    Button logout;
    String uid;
    Uri url;
    Uri file;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        st = FirebaseStorage.getInstance();
        sr = st.getReference();

        uid = auth.getCurrentUser().getUid();
        imageView = view.findViewById(R.id.profile_image);
        user_email = view.findViewById(R.id.Profile_email);
        user_mobile = view.findViewById(R.id.User_Mobile);
        user_fname = view.findViewById(R.id.profile_name1);



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
                                    Glide.with(SettingFragment.this)
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

        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            file = data.getData();
            Glide.with(getActivity()).load(file).into(imageView);
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
                                    Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            });
        }
    }
}