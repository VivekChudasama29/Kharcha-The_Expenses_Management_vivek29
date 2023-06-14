package com.example.kharcha_theexpensesmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kharcha_theexpensesmanagement.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Expenses_wise_info extends AppCompatActivity {
        TextView expense_id;
        TextView expense_name;
        TextView who_paid;
        TextView settle_expense;
        FirebaseAuth auth;
        FirebaseFirestore firestore;
        String str="";
        ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_wise_info);
        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        expense_id=findViewById(R.id.expenses_id1);
        expense_name=findViewById(R.id.expenses_name1);
        who_paid=findViewById(R.id.who_paid1);
        settle_expense=findViewById(R.id.settle_expense1);
        Bundle b= getIntent().getExtras();
        expense_name.setText(b.getString("expenses_name"));
        expense_id.setText(b.getString("expenses_id"));
        who_paid.setText(b.getString("who_paid"));
        back=findViewById(R.id.back_to_members);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(Expenses_wise_info.this,Member_wise_Expenses.class);
                startActivity(i1);
                finish();
            }
        });

        firestore.collection("divide_settle_info").whereEqualTo("expense_id",expense_id.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot doc:queryDocumentSnapshots) {
                    HashMap<String,Double> hm= (HashMap<String, Double>) doc.get("paidFor");
                    for (Map.Entry<String,Double> e:hm.entrySet()) {
                        if(!who_paid.getText().toString().equals(e.getKey())) {
                            str += "\t" + e.getKey() + "\t" + "->" + "\t" + who_paid.getText().toString() + "\t"+":"+"\t" + e.getValue() + "\n"+ "\n";
                        }
                    }
                    settle_expense.setText(str);
                }

            }
        });

    }
}