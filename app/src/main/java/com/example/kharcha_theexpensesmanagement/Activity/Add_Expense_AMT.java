package com.example.kharcha_theexpensesmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kharcha_theexpensesmanagement.Model.ExpensesModel;
import com.example.kharcha_theexpensesmanagement.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Add_Expense_AMT extends AppCompatActivity {


    AutoCompleteTextView autoCompleteText;
    AppCompatButton btnexpense;
    EditText add_amt, add_expensename;
    FirebaseAuth auth;
    FirebaseFirestore db;
    CollectionReference collectionReference;

    TextInputLayout dropdownlist;
    CollectionReference membername;
    TextView group_name;
    ImageView Back;
    TextView group_id;
    String member_id="";
    String passingwhopaidid="";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense_amt);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        btnexpense = findViewById(R.id.btnaddexpenses);
        dropdownlist = findViewById(R.id.dropdown_);
        add_amt = findViewById(R.id.enter_amt);
        Back=findViewById(R.id.back_member_expense1);
        add_expensename = findViewById(R.id.enter_expense_name);
        collectionReference = db.collection("expense_info");
        membername = db.collection("member_info");
        Bundle b = getIntent().getExtras();
        String receivinggname = b.getString("Group_Name");
        String receivinggid = b.getString("Group_Id");
        group_name = findViewById(R.id.group_add_member_expense1);
        group_id = findViewById(R.id.group_id_show_ex);
        group_name.setText(receivinggname);
        group_id.setText(receivinggid);

        autoCompleteText = findViewById(R.id.auto_complete_text);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(Add_Expense_AMT.this,Add_Member.class);
                startActivity(i1);
            }
        });
        membername.whereEqualTo("group_id", group_id.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<String> memberlist = new ArrayList<>();
                for (DocumentSnapshot documentsnapshot : queryDocumentSnapshots) {
                    String data = documentsnapshot.getString("memberName");
                    memberlist.add(data);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, memberlist);
                autoCompleteText.setAdapter(arrayAdapter);
            }
        });

        autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String member = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(Add_Expense_AMT.this, "Member: " + member, Toast.LENGTH_SHORT).show();
            }
        });

        btnexpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Group_id = group_id.getText().toString().trim();
                String Expenses_id = UUID.randomUUID().toString();
                String expense_amount = add_amt.getText().toString().trim();
                String expense_name = add_expensename.getText().toString().trim();
                String who_paid = autoCompleteText.getText().toString().trim();

                if (TextUtils.isEmpty(add_expensename.getText().toString())) {
                    add_expensename.setError("Expense name cannot be empty");
                } else if (!expense_amount.matches("^[0-9]+")) {
                    add_amt.setError("Amount can not be Empty");
                } else {
                    // Input is valid. Proceed with logic.

                    Query query = membername.whereEqualTo("memberName", autoCompleteText.getText().toString()).whereEqualTo("group_id", group_id.getText().toString());


                    query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                                member_id += documentSnapshot.getId();


                                ExpensesModel expensesModel = new ExpensesModel(Group_id, member_id, Expenses_id, expense_name, who_paid, expense_amount, DateFormat.getDateTimeInstance().format(new Date()));

                                db.collection("expenses_info").document(Expenses_id).set(expensesModel);
                                Toast.makeText(Add_Expense_AMT.this, "Expenses Added", Toast.LENGTH_SHORT).show();

                            }
                        }

                    });


                    String passingExpensename = add_expensename.getText().toString();
                    String passingamount = add_amt.getText().toString();
                    String passingwhopaid = autoCompleteText.getText().toString();
                    String passinggroupid = group_id.getText().toString();
                    String passingEpenseid = Expenses_id;

                    Query query1 = membername.whereEqualTo("memberName", autoCompleteText.getText().toString()).whereEqualTo("group_id", group_id.getText().toString());
                    query1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                                passingwhopaidid += documentSnapshot.getId();


                                Intent i1 = new Intent(Add_Expense_AMT.this, expense_details.class);

                                Bundle b = new Bundle();
                                b.putString("Expense_Name", passingExpensename);
                                b.putString("Expense_amt", passingamount);
                                b.putString("Expense_who_paid", passingwhopaid);
                                b.putString("Date_Time", DateFormat.getDateTimeInstance().format(new Date()));
                                b.putString("Group_id", passinggroupid);
                                b.putString("Expense_id", passingEpenseid);
                                b.putString("Expense_who_id", passingwhopaidid);
                                i1.putExtras(b);
                                startActivity(i1);
                            }
                        }
                    });
                }
            }
        });

    }
}