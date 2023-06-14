package com.example.kharcha_theexpensesmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kharcha_theexpensesmanagement.Model.Settlement;
import com.example.kharcha_theexpensesmanagement.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Group_settlement extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore db;
    TextView group_id, who_paid,who_name;
    Double total_owes = 0.0;
    Double totalpaidFor = 0.0;
    AppCompatButton settlementBtn;
    Double totalexpense = 0.0;
    TextView totalexpenses_counter;
    TextView totalOwes_counter;
    TextView total_balance_counter;
    Double Totalpaid = 0.0;
    ProgressDialog progressDialog;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_settlement);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data......");
        progressDialog.isShowing();

        who_name=findViewById(R.id.passwho_name2);
        group_id = findViewById(R.id.passgroup_id2);
        who_paid = findViewById(R.id.passwho_paid2);
        totalexpenses_counter = findViewById(R.id.Totalexpensescount);
        totalOwes_counter = findViewById(R.id.total_owes_counter);
        total_balance_counter = findViewById(R.id.total_balance_counter);
//
back=findViewById(R.id.back_groupwise);
back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i1=new Intent(Group_settlement.this,Settlement_member2.class);
        startActivity(i1);
        finish();
    }
});
//
//        Bundle b = new Bundle();
//
//        b.getString("id_group", group_id.getText().toString()));
//        expense_id.setText(b.getString("id_Expense", expense_id.getText().toString()));
//        who_paid.setText(b.getString("who", who_paid.getText().toString()));
        Bundle b = getIntent().getExtras();
        group_id.setText(b.getString("GroupIdSettle2"));
        who_paid.setText(b.getString("MemberIdSettle2"));
        who_name.setText(b.getString("MemberNameSettle2"));


//
//
//
//                db.collection("divide_settle_info")
//                        .whereEqualTo("group_id", group_id.getText().toString())
//                        .whereEqualTo("payee_id", who_paid.getText().toString())
//                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        total_owes = 0.0;
//                        totalpaidFor = 0.0;
////                                Settlement settlementModel=new Settlement();
//
//                        for (QueryDocumentSnapshot Document : queryDocumentSnapshots) {
//                            Settlement settlementModel = Document.toObject(Settlement.class);
//                            Double amountpaid = Double.parseDouble(settlementModel.getTotal_Amt());
//                            Toast.makeText(Group_settlement.this, "amountpaid" + amountpaid, Toast.LENGTH_SHORT).show();
//                            Double total_settled = 0.0;
//
////                            for (QueryDocumentSnapshot Document1 : queryDocumentSnapshots1) {
////                                        settlementModel = Document1.toObject(Settlement.class);
////                                    }
//                            HashMap<String, Double> hm = new HashMap<>(settlementModel.getPaidFor());
//
//                            for (Map.Entry<String, Double> e : hm.entrySet()) {
////                                        75635ea4-bb67-4759-bbed-f4c2a7cf38b0
//                                if (e.getKey().equals(who_paid.getText().toString())) {
//                                    total_settled += e.getValue();
//                                    Toast.makeText(Group_settlement.this, e.getValue().toString() + " " + e.getKey(), Toast.LENGTH_SHORT).show();
//                                }                                    //    Toast.makeText(Final_Settlement.this,  , Toast.LENGTH_SHORT).show();
////                                        if (hm.get("75635ea4-bb67-4759-bbed-f4c2a7cf38b0") == who_paid.getText().toString()){
////                                            Toast.makeText(Final_Settlement.this, e.getValue().toString(), Toast.LENGTH_SHORT).show();
////                                        }
//                                Toast.makeText(Group_settlement.this, who_paid.getText().toString() + "current", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(Group_settlement.this, e.getKey(), Toast.LENGTH_SHORT).show();
//                            }
//
//                            Double owes = amountpaid - total_settled;
//                            Double paidFor = total_settled;
//
//                            total_owes += owes;
//                            totalpaidFor += paidFor;
//                            Log.d("settlement", "owes   " + total_owes + "   'paid for    " + totalpaidFor);
//                        }
//                        Toast.makeText(Group_settlement.this, "owes   " + total_owes + "   'paid for    " + totalpaidFor, Toast.LENGTH_SHORT).show();
//
//                    }
//                });
        db.collection("divide_settle_info")
                .whereEqualTo("group_id", group_id.getText().toString())
                .whereEqualTo("payee_id", who_paid.getText().toString())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                Double AMOUNT_total = 0.0;
                for (QueryDocumentSnapshot Document : queryDocumentSnapshots) {
                    Settlement settlementModel = Document.toObject(Settlement.class);
                    AMOUNT_total += Double.parseDouble(settlementModel.getTotal_Amt());
//                                    settlementModel1=new Settlement();

                }

                totalexpense = AMOUNT_total;
                totalexpenses_counter.setText(totalexpense.toString());

            }
        });
        System.out.println("Amount_Total : " + totalexpense);
        db.collection("divide_settle_info")
                .whereEqualTo("group_id", group_id.getText().toString())

                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Settlement settlementModel1 = new Settlement();

                for (QueryDocumentSnapshot Document1 : queryDocumentSnapshots) {
                    settlementModel1 = Document1.toObject(Settlement.class);
                    HashMap<String, Double> hm = new HashMap<>(settlementModel1.getPaidFor());
                    for (Map.Entry<String, Double> e : hm.entrySet()) {
//                                        75635ea4-bb67-4759-bbed-f4c2a7cf38b0
                        if (e.getKey().equals(who_name.getText().toString())) {
                            Totalpaid += e.getValue();
                            System.out.println(Totalpaid);

                        }
                        totalpaidFor = Totalpaid;
                    }

                    System.out.println("TOTALPAIDFOR  " + totalpaidFor);
                    totalOwes_counter.setText(totalpaidFor.toString());


                }

                totalexpenses_counter.setText(totalexpense.toString());
                total_balance_counter.setText(String.valueOf(totalexpense - totalpaidFor));

                Log.d("Final", String.valueOf((totalexpense - totalpaidFor)));
                // Toast.makeText(Final_Settlement.this, (int) (totalexpense-totalpaidFor), Toast.LENGTH_SHORT).show();
                System.out.println("Total Amount Fully " + (totalexpense - totalpaidFor));
                totalpaidFor = 0.0;

//
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

   });
}
}