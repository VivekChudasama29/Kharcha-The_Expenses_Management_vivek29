package com.example.kharcha_theexpensesmanagement.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kharcha_theexpensesmanagement.Adapter.settlement_adapter2;
import com.example.kharcha_theexpensesmanagement.Model.MemberModel;
import com.example.kharcha_theexpensesmanagement.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Settlement_member2 extends AppCompatActivity {
    TextView group_id;
    FirebaseAuth auth;
    FirebaseFirestore db;
    settlement_adapter2 settlement_adapters;
    RecyclerView recyclerView;
    TextView group_name;
    ImageView back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement_member2);
        Bundle b = getIntent().getExtras();

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        group_id = findViewById(R.id.passgroup_id_set_);
        group_name = findViewById(R.id.passgroup_name_set_);
        group_id.setText(b.getString("GroupID3"));
        group_name.setText(b.getString("GroupName3"));


        back=findViewById(R.id.back_total_settlement2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(Settlement_member2.this,navigationbar.class);
                startActivity(i1);
                finish();
            }
        });
        recyclerView = findViewById(R.id.total_settlement_recycle);
        settlement_adapters = new settlement_adapter2(this);
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
//                    if (progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
//                if (progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
            }
        });

    }

}