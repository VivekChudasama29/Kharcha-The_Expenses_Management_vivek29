package com.example.kharcha_theexpensesmanagement.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.kharcha_theexpensesmanagement.Adapter.memberAdapter;
import com.example.kharcha_theexpensesmanagement.Model.MemberModel;
import com.example.kharcha_theexpensesmanagement.R;
import com.example.kharcha_theexpensesmanagement.databinding.ActivityAddMemberBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class Add_Member extends AppCompatActivity {

    ActivityAddMemberBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    FirebaseFirestore db;
    TextView GroupAddMember, groupid_show;
    com.example.kharcha_theexpensesmanagement.Adapter.memberAdapter memberAdapter;
    ImageView Back, addmember;
    CollectionReference collectionReference;
    EditText membername, membermno;
    FirebaseUser currentuser;
    FirebaseStorage st;
    StorageReference sr;
    Button createmember;
    RecyclerView recyclerView;
    int count;
   // FloatingActionButton fab1;
Button fab1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        fab1 = findViewById(R.id.add_mem_ex);
        addmember = findViewById(R.id.group_navbar);
        GroupAddMember = findViewById(R.id.group_add_member);
        groupid_show = findViewById(R.id.group_id_show);
        recyclerView = findViewById(R.id.grp_recycle12);
        GroupAddMember.setText(getIntent().getExtras().getString("GroupAddMember"));
        groupid_show.setText(getIntent().getExtras().getString("GroupId"));
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("LOADING...........");
        progressDialog.show();
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("member_info");
        count = 0;

        Back = findViewById(R.id.back_member);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Add_Member.this, Add_group.class);
                startActivity(i1);
            }
        });
        addmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.collection("divide_settle_info")
                        .whereEqualTo("group_id",groupid_show.getText().toString())
                                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                int count=0;
                                for (DocumentSnapshot doc:queryDocumentSnapshots) {
                                    String name=doc.getId();
                                    System.out.println(name);
                                  count++;
                                }
                                System.out.println(count);
                                if(count==0){
                                    showDialog();
                                }else{
                                    addmember.setEnabled(false);
                                    Toast.makeText(Add_Member.this, "Member Not Added", Toast.LENGTH_SHORT).show();
                                }

                            }});

            }
        });


        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (memberAdapter != null) {
                    count = memberAdapter.getItemCount();
                }
                if (count >= 2) {
                    String passinggroupname = GroupAddMember.getText().toString();
                    String passinggroupid = groupid_show.getText().toString();
                    Intent i1 = new Intent(Add_Member.this, Add_Expense_AMT.class);

                    Bundle b = new Bundle();
                    b.putString("Group_Name", passinggroupname);
                    b.putString("Group_Id", passinggroupid);
                    i1.putExtras(b);
                    startActivity(i1);
                } else {
                    AlertDialog dialog = new AlertDialog.Builder(Add_Member.this)
                            .setTitle("Kharcha")
                            .setMessage("You Need at least 2 Members to add expenses.")
                            .setPositiveButton("Add Member", null)
                            .show();

                    Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showDialog();
                            Toast.makeText(Add_Member.this, "Add Member", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
        memberAdapter = new memberAdapter(this);
        recyclerView.setAdapter(memberAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataInitialize();

    }

    private void showDialog() {
        final Dialog dialog = new Dialog(Add_Member.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_add_member);


        membername = dialog.findViewById(R.id.member_fullname);
        membermno = dialog.findViewById(R.id.add_member_mobile_);
        createmember = dialog.findViewById(R.id.addmemberbtn);

        auth = FirebaseAuth.getInstance();

        st = FirebaseStorage.getInstance();
        sr = st.getReference();
        currentuser = auth.getCurrentUser();
        createmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Member_id = UUID.randomUUID().toString();
                String Member_name = membername.getText().toString().trim();
                String Member_Mno = membermno.getText().toString().trim();

                if (Member_name.isEmpty()) {
                    membername.setError("Member Name can not be Empty");
                } else if (!Member_name.matches("[a-zA-Z ]+")) {
                    membername.setError("only text allowed in Member Name");
                } else if (Member_Mno.isEmpty()) {
                    membermno.setError("Mobile number  can not be Empty");
                } else if (!Member_Mno.matches("^[+]?[0-9]{10}$")) {
                    membermno.setError("Mobile number should be 10 digits long");
                } else if (!Member_Mno.matches("^[+]?[6-9]{1}[\\s-]?[0-9]{2,10}$")) {
                    membermno.setError("Invalid mobile number format");
                } else {
//                    progressDialog = new ProgressDialog(Add_Member.this);
//                    progressDialog.setCancelable(false);
//                    progressDialog.setMessage("LOADING...........");
//                    progressDialog.show();
                    HashMap<String, Double> hm = new HashMap<>();

                    db.collection("member_info").whereEqualTo("group_id", groupid_show.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (DocumentSnapshot document : queryDocumentSnapshots) {
                                String memberId = document.getId();
                                String memberName = document.getString("memberName");
                                Log.d("TAG_MEM", "Member ID: " + memberId);
                              //  Toast.makeText(Add_Member.this, memberId, Toast.LENGTH_SHORT).show();
                                hm.put(memberName, 0.0);
                            }
                            hm.put(Member_name, 0.0);
                            MemberModel memberModel = new MemberModel(GroupAddMember.getText().toString(), groupid_show.getText().toString(), Member_Mno, Member_name, Member_id, hm);
                            db.collection("member_info").document(Member_id).set(memberModel);
                        }
                    });
                    HashMap<String, Object> hm1 = new HashMap<>();
                    hm1.put("owns", hm);
                    System.out.println(hm);
                    progressDialog = new ProgressDialog(Add_Member.this);
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("LOADING...........");
                    progressDialog.show();
                    db.collection("member_info").whereEqualTo("group_id", groupid_show.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (DocumentSnapshot document : queryDocumentSnapshots) {
                                String memberId = document.getId();
                                db.collection("member_info").document(memberId).update(hm1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        //Toast.makeText(Add_Member.this, "Update", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });


//                MemberModel memberModel=new MemberModel(GroupAddMember.getText().toString(),groupid_show.getText().toString(),Member_Mno,Member_name,Member_id,0.0,hm);

//                db.collection("member_info").document(Member_id).set(memberModel);

                    memberAdapter = new memberAdapter(Add_Member.this);
                    recyclerView.setAdapter(memberAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Add_Member.this));

                    dataInitialize();
                    dialog.dismiss();

                    Toast.makeText(Add_Member.this, "Member Added", Toast.LENGTH_SHORT).show();
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    private void dataInitialize() {

        collectionReference.whereEqualTo("group_Name", GroupAddMember.getText()).whereEqualTo("group_id", groupid_show.getText()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> dslist = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot DS : dslist) {
                    MemberModel memberModel = DS.toObject(MemberModel.class);
                    memberAdapter.add(memberModel);
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                if(dslist.isEmpty()){
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