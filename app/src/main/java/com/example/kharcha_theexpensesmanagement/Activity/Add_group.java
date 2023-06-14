package com.example.kharcha_theexpensesmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
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

import com.example.kharcha_theexpensesmanagement.Activity.navigationbar;
import com.example.kharcha_theexpensesmanagement.Adapter.grpAdapter;
import com.example.kharcha_theexpensesmanagement.Model.GroupData;
import com.example.kharcha_theexpensesmanagement.Model.GroupModel;
import com.example.kharcha_theexpensesmanagement.Model.MemberModel;
import com.example.kharcha_theexpensesmanagement.R;
import com.example.kharcha_theexpensesmanagement.databinding.ActivityAddGroupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Add_group extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST_CODE = 71;
    ImageView addgroup;
    EditText useremail;
    EditText groupnm;
    ImageView showimage;
    Button chooseimg;
    Uri filepath;
    private FirebaseAuth auth;
    FirebaseFirestore db;
    AppCompatButton creategrp;
    FirebaseUser currentuser;
    FirebaseStorage st;
    StorageReference sr;
    String downimg;
    TextView file_image_picker;
    ProgressDialog progressDialog;
    com.example.kharcha_theexpensesmanagement.Adapter.grpAdapter grpAdapter;

    ImageView Back;
    ActivityAddGroupBinding binding;

    CollectionReference collectionReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        collectionReference = db.collection("group_info");
        addgroup = findViewById(R.id.community_addgroup);
        addgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        Back = findViewById(R.id.back_group);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Add_group.this, navigationbar.class);
                startActivity(i1);
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("LOADING......");
        progressDialog.show();
        grpAdapter = new grpAdapter(this);
        binding.grpRecycle.setAdapter(grpAdapter);
        binding.grpRecycle.setLayoutManager(new LinearLayoutManager(this));
        dataInitialize();

    }

    private void showDialog() {
        final Dialog dialog = new Dialog(Add_group.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        useremail = dialog.findViewById(R.id.group_user_email);
        groupnm = dialog.findViewById(R.id.group_name_);

        chooseimg = dialog.findViewById(R.id.group_btnFilePicker_);
        showimage = dialog.findViewById(R.id.group_circle_image_);
        creategrp = dialog.findViewById(R.id.create_btn);
        file_image_picker = dialog.findViewById(R.id.group_file_Name);
        useremail.setEnabled(false);

        auth = FirebaseAuth.getInstance();

        st = FirebaseStorage.getInstance();
        sr = st.getReference();
        currentuser = auth.getCurrentUser();
        userData();

        creategrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String group_id = UUID.randomUUID().toString();
                String userEmail = useremail.getText().toString().trim();
                String Group_name = groupnm.getText().toString().trim();
                String Member_id = UUID.randomUUID().toString();


                if (Group_name.isEmpty()) {
                    groupnm.setError("Group Name can not be Empty.");
                } else if (!Group_name.matches("[a-zA-Z ]+")) {
                    groupnm.setError("only text allowed in Group Name");
                } else if (filepath == null || filepath.toString().isEmpty()) {
                    file_image_picker.setText("Please select profile image");
                } else {
                    progressDialog = new ProgressDialog(Add_group.this);
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("LOADING");
                    progressDialog.show();
                    Uri fileUri = filepath; // Get the Uri of the selected image
                    StorageReference imageRef = sr.child("group_images/" + UUID.randomUUID().toString());
                    UploadTask uploadTask = imageRef.putFile(fileUri);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadUrl) {
                                    GroupModel groupModel = new GroupModel(group_id, userEmail, groupnm.getText().toString(), downloadUrl.toString());
                                    downimg = downloadUrl.toString();
                                    FirebaseFirestore databaseRef = FirebaseFirestore.getInstance();
                                    db.collection("group_info").document(group_id).set(groupModel);


                                    db.collection("user")
                                            .document(FirebaseAuth.getInstance().getUid())
                                            .get()
                                            .addOnCompleteListener
                                                    (new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                                            if (task.isSuccessful()) {
                                                                DocumentSnapshot doc = task.getResult();

                                                                HashMap<String, Double> hm = new HashMap<>();

                                                                hm.put(doc.getString("fullname"), 0.0);
                                                                MemberModel memberModel = new MemberModel(Group_name, group_id, doc.getString("mobile_No"), doc.getString("fullname"), Member_id, hm);
                                                                db.collection("member_info").document(Member_id).set(memberModel);

                                                                grpAdapter = new grpAdapter(Add_group.this);
                                                                binding.grpRecycle.setAdapter(grpAdapter);
                                                                binding.grpRecycle.setLayoutManager(new LinearLayoutManager(Add_group.this));
                                                                dataInitialize();
                                                                if (progressDialog.isShowing()) {
                                                                    progressDialog.dismiss();
                                                                }

                                                            }
                                                        }
                                                    });
                                }
                            });
                        }
                    });

                    dialog.dismiss();
                    Toast.makeText(Add_group.this, "Group Created", Toast.LENGTH_SHORT).show();
                }
            }
        });
        chooseimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectimg(view);
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void userData() {
        useremail.setText(currentuser.getEmail());
    }

    private void selectimg(View view) {
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
            filepath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                showimage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void dataInitialize() {


        collectionReference.whereEqualTo("user_email", auth.getCurrentUser().getEmail()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> dslist = queryDocumentSnapshots.getDocuments();

                for (DocumentSnapshot DS : dslist) {

                    GroupModel groupModel = DS.toObject(GroupModel.class);
                    grpAdapter.add(groupModel);
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                if (dslist.isEmpty()) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });

    }
}
