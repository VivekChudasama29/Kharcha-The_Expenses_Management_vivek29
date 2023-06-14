package com.example.kharcha_theexpensesmanagement.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kharcha_theexpensesmanagement.Adapter.settlement_adapter;
import com.example.kharcha_theexpensesmanagement.Model.MemberModel;
import com.example.kharcha_theexpensesmanagement.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Settlement_member extends AppCompatActivity {
    TextView group_id, who_paid, expense_id;
    FirebaseAuth auth;
    FirebaseFirestore db;
    settlement_adapter settlement_adapters;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    ImageView back;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement_member);
        Bundle b = getIntent().getExtras();

        auth= FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("LOADING......");
        progressDialog.show();

        group_id = findViewById(R.id.passgroup_id_set);
        who_paid = findViewById(R.id.passwho_paid_set);
        expense_id = findViewById(R.id.passexpense_id_set);
        group_id.setText(b.getString("id_group"));
        who_paid.setText(b.getString("who"));
        expense_id.setText(b.getString("id_Expense"));

        back=findViewById(R.id.back_total_settlement);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(Settlement_member.this,navigationbar.class);
                startActivity(i1);
                finish();
            }
        });

        recyclerView=findViewById(R.id.total_settlement_recycle);
        settlement_adapters = new settlement_adapter(this);
        dataInitialize();
        recyclerView.setAdapter(settlement_adapters);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void dataInitialize() {
        db.collection("member_info").whereEqualTo("group_id", group_id.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> dslist = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot DS : dslist) {
                    MemberModel memberModel = DS.toObject(MemberModel.class);
                    settlement_adapters.add(memberModel);
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                if (dslist.isEmpty()){
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