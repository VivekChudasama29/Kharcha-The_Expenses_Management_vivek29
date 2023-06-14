package com.example.kharcha_theexpensesmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kharcha_theexpensesmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Final_Settlement extends AppCompatActivity {
    String whopaidmembernm = "";
    FirebaseAuth auth;
    FirebaseFirestore db;
    TextView group_id, who_paid;
    Double total_owes = 0.0;
    Double totalpaidFor = 0.0;
    AppCompatButton DoneBtn;
    Double totalexpense = 0.0;
    TextView totalexpenses_counter;
    TextView totalOwes_counter;
    TextView total_balance_counter;
    Double Totalpaid = 0.0;
    ProgressDialog progressDialog;
    String str = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_settlement);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data......");
        progressDialog.isShowing();

        group_id = findViewById(R.id.passgroup_id);
        who_paid = findViewById(R.id.passwho_paid);
        totalexpenses_counter = findViewById(R.id.Totalexpensescount);
        totalOwes_counter = findViewById(R.id.total_owes_counter);
        total_balance_counter = findViewById(R.id.total_balance_counter);
        DoneBtn = findViewById(R.id.Done);

        Bundle b = getIntent().getExtras();
        group_id.setText(b.getString("GroupIdSettle"));
        who_paid.setText(b.getString("MemberIdSettle"));

        db.collection("member_info").whereEqualTo("group_id", group_id.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Map<String, Double> whoPaid = new HashMap<>();
                Map<String, Double> whoOwes = new HashMap<>();
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult().getDocuments().get(0);

                    String MemID = document.getId();
                    HashMap<String, Double> hashMap = (HashMap<String, Double>) document.get("owns");
                    for (Map.Entry<String, Double> e : hashMap.entrySet()) {
                        if (e.getValue() < 0.0) {
                            whoOwes.put(e.getKey(), -e.getValue());

                            System.out.println("Minus  Member :: " + e.getKey() + "  " + e.getValue());

                        } else if (e.getValue() > 0) {
                            whoPaid.put(e.getKey(), e.getValue());

                            System.out.println("Plus Member :: " + e.getKey() + " " + e.getValue());
                        }
                    }

                }
                System.out.println("who owns :");
                System.out.println(whoOwes);
                System.out.println("who paid :");
                System.out.println(whoPaid);
                Map<String, Double> settlement = new HashMap<>();
                for (Map.Entry<String, Double> entry : whoPaid.entrySet()) {
                    String paidBy = entry.getKey();
                    double amountPaid = entry.getValue();
                    for (Map.Entry<String, Double> owesEntry : whoOwes.entrySet()) {
                        String owesTo = owesEntry.getKey();
                        double amountOwed = owesEntry.getValue();
                        if (amountPaid >= amountOwed) {
                            settlement.put(owesTo + "\t -> \t" + paidBy, Double.parseDouble(new DecimalFormat("##.##").format(amountOwed)));
                           // Toast.makeText(Final_Settlement.this, whopaidmembernm, Toast.LENGTH_SHORT).show();

                            amountPaid -= amountOwed;
                            owesEntry.setValue(0.0);
                        } else {
                            settlement.put(owesTo + "\t -> \t" + paidBy, Double.parseDouble(new DecimalFormat("##.##").format(amountPaid)));

                            amountOwed -= amountPaid;
                            owesEntry.setValue(amountOwed);
                            break;
                        }
                    }
                }
                if(!settlement.isEmpty()) {
                    for (Map.Entry<String, Double> entry : settlement.entrySet()) {
                        String description = entry.getKey();
                        double amount = entry.getValue();
                        str += "  " + description + " \t : \t" + amount + "\n" + "\n" + "\n";
                        System.out.println(description + ": " + amount);
                    }

                total_balance_counter.setText(str);
                }else{
total_balance_counter.setText("No Actiity");
                }
            }
        });
        DoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(Final_Settlement.this,navigationbar.class);
                startActivity(i1);
                finishAffinity();
            }
        });

    }
}