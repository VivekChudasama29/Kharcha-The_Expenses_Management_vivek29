package com.example.kharcha_theexpensesmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kharcha_theexpensesmanagement.Adapter.DivideAdapter;
import com.example.kharcha_theexpensesmanagement.Model.MemberModel;
import com.example.kharcha_theexpensesmanagement.Model.Settlement;
import com.example.kharcha_theexpensesmanagement.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Split_Equally extends AppCompatActivity {
    TextView MemberName;
    TextView whoid;
    TextView groupid;
    TextView expensesid;
    TextView totallly;
    TextView memberid;
    TextView amt;
    FirebaseFirestore db;
    FirebaseAuth auth;
    String mmdetail = "";
    RecyclerView recyclerView;
    DivideAdapter divide_adapter;
    String Member_id = "";
    AppCompatButton save;
    List<String> memberid_list = new ArrayList<>();
    List<String> memberName_list = new ArrayList<>();
    HashMap<String,Double> paidFor=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_equally);
        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        save=findViewById(R.id.save_expense_equally);
        MemberName = findViewById(R.id.newtxtmemnm);
        totallly=findViewById(R.id.newtxttotal);
        groupid = findViewById(R.id.newtxtgroupid);
        whoid=findViewById(R.id.newtxtwhoid);
        expensesid = findViewById(R.id.newtxtexpenseid);
        amt = findViewById(R.id.newtxtmemamt);
        memberid = findViewById(R.id.new_txt_member_id);
        Intent intent = getIntent();
        ArrayList<MemberModel> selectedList = intent.getParcelableArrayListExtra("selected_list");
        Bundle b = getIntent().getExtras();
        Double receivingamount = b.getDouble("Divide_amt");
        String gid = b.getString("group_id");
        String expid = b.getString("expense_id");
        String who_id_ex=b.getString("payee_id");
        String total_ex=b.getString("Total");
        String who_name=b.getString("who_name");

        groupid.setText(gid);
        expensesid.setText(expid);
        whoid.setText(who_id_ex);
        totallly.setText(total_ex);

        amt.setText(receivingamount.toString());
        Double divide=Double.parseDouble(amt.getText().toString());
        recyclerView = findViewById(R.id.divide_exp_amt_);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        for (MemberModel member : selectedList) {
            String memberName = member.getMemberName();
            System.out.println(memberName);
            String Memberid = member.getMember_id();
            memberid_list.add(Memberid);
            memberName_list.add(memberName);
            paidFor.put(memberName,divide);
            mmdetail += memberName + "\n";
            Member_id += Memberid + "\n";
        }

        MemberName.setText(mmdetail);
        memberid.setText(Member_id);
        memberid.setText((CharSequence) Member_id);
        divide_adapter = new DivideAdapter(this);
        recyclerView.setAdapter(divide_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataInitialize();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {String settleid = UUID.randomUUID().toString();

                Settlement settlement = new Settlement(settleid, paidFor, memberName_list, who_id_ex, who_name, groupid.getText().toString(), expensesid.getText().toString(), totallly.getText().toString());
                db.collection("divide_settle_info").document(settleid).set(settlement);
             //   Toast.makeText(Split_Equally.this, "Successfully !!!", Toast.LENGTH_SHORT).show();
                db.collection("member_info").whereEqualTo("group_id", groupid.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        HashMap<String, Double> owns = new HashMap<>();
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            String memberId = document.getId();
                            owns.putAll((Map) document.get("owns"));
                        }

                        //Toast.makeText(Split_Equally.this, owns.toString(), Toast.LENGTH_SHORT).show();
                        System.out.println(owns);
                        HashMap<String,Object> hm1=new HashMap<>();
                        HashMap<String, Double> hm = new HashMap<>();

                        for (Map.Entry<String, Double> e : owns.entrySet()) {
                            if(!memberName_list.contains(e.getKey())){
                                hm.put(e.getKey(),e.getValue());
                            }
                        }
                        for (int i = 0; i < memberName_list.size(); i++) {
                            for (Map.Entry<String, Double> e : owns.entrySet()) {
                                if ((memberName_list.get(i)).equals(e.getKey())) {
                                    if ((memberName_list.get(i).equals(who_name))) {
                                        hm.put(e.getKey(), e.getValue() + (divide * (memberid_list.size()-1 )));
                                        System.out.println(e.getKey() + " " + (e.getValue() + (divide * (memberid_list.size() - 1))));
                                        //Toast.makeText(Split_Equally.this, e.getKey() + " " + (e.getValue() + divide), Toast.LENGTH_SHORT).show();

                                    } else {
                                        hm.put(e.getKey(), e.getValue() - divide);
                                        System.out.println(e.getKey() + " " + (e.getValue() - divide));
                                        //Toast.makeText(Split_Equally.this, e.getKey() + " " + (e.getValue() - divide), Toast.LENGTH_SHORT).show();
                                    }
                                    //Toast.makeText(Split_Equally.this, e.getKey() + " " + e.getValue() + " ", Toast.LENGTH_SHORT).show();
                                }


                            }
                            hm1.put("owns",hm);
                        }


                        db.collection("member_info").whereEqualTo("group_id", groupid.getText().toString())
                                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (DocumentSnapshot documentSnapshot1:queryDocumentSnapshots){
                                    String ID=documentSnapshot1.getId();
                                    db.collection("member_info").document(ID).update(hm1);
                                }
                            }
                        });


                    }
                });


                Intent i1 = new Intent(Split_Equally.this, Settlement_member.class);
                Bundle b = new Bundle();
                b.putString("id_group", groupid.getText().toString());
                b.putString("who", whoid.getText().toString());
                b.putString("id_Expense", expensesid.getText().toString());
                i1.putExtras(b);
                startActivity(i1);
            }
        });
    }

    private void dataInitialize() {
        ArrayList<MemberModel> selectedListItem = getIntent().getParcelableArrayListExtra("selected_list");
        for (MemberModel member : selectedListItem) {
            divide_adapter.add(member);
        }
    }
}