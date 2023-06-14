package com.example.kharcha_theexpensesmanagement.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kharcha_theexpensesmanagement.Adapter.Recycler_expense_Adapter;
import com.example.kharcha_theexpensesmanagement.Model.MemberModel;
import com.example.kharcha_theexpensesmanagement.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class expense_details extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore db;
    TextView display_amt, display_expensename;
    TextView who_paid_name, who_paidid;
    TextView datetime;
    TextView group_id, Expense_id, member_id;
    ImageView Back;
    CollectionReference member_Name;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    CheckBox cb;


    Recycler_expense_Adapter recycler_expense_adapter;

    AppCompatButton save;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_details);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        Back = findViewById(R.id.back_member_expense);
        display_expensename = findViewById(R.id.expenseName1);
        who_paid_name = findViewById(R.id.memberName1);
        who_paidid = findViewById(R.id.whopaidid);
        display_amt = findViewById(R.id.expenseAmount1);
        datetime = findViewById(R.id.expenseDateTime1);
        group_id = findViewById(R.id.group_id_show_expense_devide);
        Expense_id = findViewById(R.id.expense_id_show);
        save = findViewById(R.id.save_expense);
        member_Name = db.collection("member_info");
        recyclerView = findViewById(R.id.member_rec1);
        Bundle b = getIntent().getExtras();
        String receivingexpensename = b.getString("Expense_Name");
        String receivingamount = b.getString("Expense_amt");
        String receivingwhopaid = b.getString("Expense_who_paid");
        String receivingdatetime = b.getString("Date_Time");
        String receivinggroupid = b.getString("Group_id");
        String receivingexpenseid = b.getString("Expense_id");
        String receivingwho_id = b.getString("Expense_who_id");
        display_expensename.setText(receivingexpensename);
        display_amt.setText(receivingamount);
        who_paid_name.setText(receivingwhopaid);
        datetime.setText(receivingdatetime);
        group_id.setText(receivinggroupid);
        Expense_id.setText(receivingexpenseid);
        who_paidid.setText(receivingwho_id);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(expense_details.this, Add_Expense_AMT.class);
                startActivity(i1);
                //finish();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<MemberModel> dataList = new ArrayList<>();

        db.collection("member_info").whereEqualTo("group_id", group_id.getText().toString()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    MemberModel model = document.toObject(MemberModel.class);
                    dataList.add(model);
                }
                recycler_expense_adapter.notifyDataSetChanged();
            }
        });
        recycler_expense_adapter = new Recycler_expense_Adapter(dataList);
        recyclerView.setAdapter(recycler_expense_adapter);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<MemberModel> selectedList = recycler_expense_adapter.getSelectedList();
                String passingsize = String.valueOf(recycler_expense_adapter.getSelectedListsize(selectedList));
                double expenseAmount = Double.parseDouble(display_amt.getText().toString());
                int totalMembers = Integer.parseInt(passingsize);
                String who__name = who_paid_name.getText().toString();
                String total_amout = receivingamount;
                String who_id_ = who_paidid.getText().toString();

                // Calculate the expense per member
                double expensePerMember = expenseAmount / totalMembers;

                if (totalMembers >= 2) {
                    Intent intent = new Intent(expense_details.this, Split_Equally.class);
                    intent.putParcelableArrayListExtra("selected_list", selectedList);

                    Bundle b = new Bundle();
                    b.putDouble("Divide_amt", Double.parseDouble(new DecimalFormat("##.##").format(expensePerMember)));
                    b.putString("SelectSize", passingsize);
                    b.putString("group_id", group_id.getText().toString());
                    b.putString("expense_id", Expense_id.getText().toString());

                    b.putString("Total", total_amout);
                    b.putString("who_name", who__name);
                    b.putString("payee_id", who_id_);
                    intent.putExtras(b);
                    //Toast.makeText(expense_details.this, "size" + recycler_expense_adapter.getSelectedListsize(selectedList), Toast.LENGTH_SHORT).show();

                    startActivity(intent);
                } else {
                    AlertDialog dialog = new AlertDialog.Builder(expense_details.this)
                            .setTitle("Kharcha")
                            .setMessage("You Need at least 2 Members Select ")
                            .setPositiveButton("Select Member", null)
                            .show();

                    Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(expense_details.this, "You Need at least 2 Members Select ", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
    }
}

