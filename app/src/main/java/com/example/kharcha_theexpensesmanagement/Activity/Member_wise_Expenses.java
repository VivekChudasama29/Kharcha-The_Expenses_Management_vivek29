package com.example.kharcha_theexpensesmanagement.Activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kharcha_theexpensesmanagement.Adapter.MemberWise_ExpensedataAdapter;
import com.example.kharcha_theexpensesmanagement.Model.ExpensesModel;
import com.example.kharcha_theexpensesmanagement.Model.Settlement;
import com.example.kharcha_theexpensesmanagement.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Member_wise_Expenses extends AppCompatActivity {
    TextView group_id, group_name, membermno, membername, memberid, total_amt;
    ImageView imgback;
    Double total_owes = 0.0;
    Double totalpaidFor = 0.0;
    AppCompatButton settlementBtn;
    Double totalexpense = 0.0;
    Double Totalpaid = 0.0;
    ProgressDialog progressDialog;
    MemberWise_ExpensedataAdapter member_wise_adapter;
    CollectionReference collectionReference;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    AppCompatButton save;
    RecyclerView recyclerView;
    int amt = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_wise_expenses);
        group_name = findViewById(R.id.member_ex_gname);
        group_id = findViewById(R.id.member_ex_gid);
        total_amt = findViewById(R.id.member_ex_total);
        memberid = findViewById(R.id.member_ex_memberid);
        membername = findViewById(R.id.member_ex_membername);
        membermno = findViewById(R.id.member_ex_membermno);
        imgback = findViewById(R.id.back_expense_data);
        recyclerView = findViewById(R.id.member_expenses_info);
       // settlementBtn = findViewById(R.id.Settlement1);
        Bundle b = getIntent().getExtras();
        String receivinggname = b.getString("GroupName");
        String receivinggid = b.getString("GroupId");
        String receivingmembername = b.getString("MemberName");
        String receivingmemberid = b.getString("MemberId");
        String receivingmembermno = b.getString("MemberMno");
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        collectionReference = firestore.collection("expenses_info");
        group_name.setText(receivinggname);
        group_id.setText(receivinggid);
        memberid.setText(receivingmemberid);
        membername.setText(receivingmembername);
        membermno.setText(receivingmembermno);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Member_wise_Expenses.this, Add_Member.class);
                startActivity(i1);
            }
        });
//        settlementBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                firestore.collection("divide_settle_info")
//                        .whereEqualTo("group_id", group_id.getText().toString())
//                        .whereEqualTo("payee_id", memberid.getText().toString())
//                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
//                        Double AMOUNT_total = 0.0;
//                        for (QueryDocumentSnapshot Document : queryDocumentSnapshots) {
//                            Settlement settlementModel = Document.toObject(Settlement.class);
//                            AMOUNT_total += Double.parseDouble(settlementModel.getTotal_Amt());
//                        }
//                        totalexpense = AMOUNT_total;
//                        Toast.makeText(Member_wise_Expenses.this, totalexpense.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//                System.out.println(totalexpense);
//                Toast.makeText(Member_wise_Expenses.this, "Amount Total Expenses : " + totalexpense, Toast.LENGTH_SHORT).show();
//
//                firestore.collection("divide_settle_info")
//                        .whereEqualTo("group_id", group_id.getText().toString())
//                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        Settlement settlementModel1 = new Settlement();
//
//                        for (QueryDocumentSnapshot Document1 : queryDocumentSnapshots) {
//                            settlementModel1 = Document1.toObject(Settlement.class);
//                            HashMap<String, Double> hm = new HashMap<>(settlementModel1.getPaidFor());
//                            for (Map.Entry<String, Double> e : hm.entrySet()) {
//                                if (e.getKey().equals(memberid.getText().toString())) {
//                                    Totalpaid += e.getValue();
//                                    Toast.makeText(Member_wise_Expenses.this, e.getValue().toString() + " " + e.getKey(), Toast.LENGTH_SHORT).show();
//                                }
//                                totalpaidFor = Totalpaid;
//                            }
//                            Toast.makeText(Member_wise_Expenses.this, totalpaidFor.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                        Log.d("Final", String.valueOf((totalexpense - totalpaidFor)));
//                        // Toast.makeText(Final_Settlement.this, (int) (totalexpense-totalpaidFor), Toast.LENGTH_SHORT).show();
//                        System.out.println("Total Amount Fully " + (totalexpense - totalpaidFor));
//                        totalpaidFor = 0.0;
//                    }
//                });
//            }
//        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("LOADING......");
        progressDialog.show();
        member_wise_adapter = new MemberWise_ExpensedataAdapter(this);
        recyclerView.setAdapter(member_wise_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataInitialize();
        member_wise_adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int total = member_wise_adapter.getTotalAmount();
                Log.d(TAG, "Total Amount :" + total);
            }
        });

    }

    private void dataInitialize() {
        collectionReference.whereEqualTo("member_id", memberid.getText()).whereEqualTo("group_id", group_id.getText()).whereEqualTo("who_paid", membername.getText()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> dslist = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot DS : dslist) {
                    ExpensesModel expensesModel = DS.toObject(ExpensesModel.class);
                    member_wise_adapter.add(expensesModel);
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