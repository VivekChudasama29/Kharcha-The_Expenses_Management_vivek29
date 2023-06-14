package com.example.kharcha_theexpensesmanagement.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.kharcha_theexpensesmanagement.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class Recent_ActivityFragment extends Fragment {
    TextView expense_id;
    TextView expense_name;
    TextView who_paid;
    TextView settle_expense;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    String str="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_recent__activity, container, false);

        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        expense_id=view.findViewById(R.id.expenses_id1);
        expense_name=view.findViewById(R.id.expenses_name1);
        who_paid=view.findViewById(R.id.who_paid1);
        settle_expense=view.findViewById(R.id.settle_expense1);
        Bundle b = new Bundle();
        expense_name.setText(b.getString("expenses_name"));
        expense_id.setText(b.getString("expenses_id"));
        who_paid.setText(b.getString("who_paid"));

        firestore.collection("divide_settle_info").whereEqualTo("expense_id",expense_id.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot doc:queryDocumentSnapshots) {
                    HashMap<String,Double> hm= (HashMap<String, Double>) doc.get("paidFor");
                    for (Map.Entry<String,Double> e:hm.entrySet()) {
                        if(!who_paid.getText().toString().equals(e.getKey())) {
                            str += "\t" + e.getKey() + "\t" + "->" + "\t" + who_paid.getText().toString() + "\t"+":"+"\t" + e.getValue() + "\n";
                        }
                    }
                    settle_expense.setText(str);
                }

            }
        });

        return view;
    }
}