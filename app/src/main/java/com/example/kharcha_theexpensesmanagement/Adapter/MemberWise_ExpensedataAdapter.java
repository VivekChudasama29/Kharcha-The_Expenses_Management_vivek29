package com.example.kharcha_theexpensesmanagement.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kharcha_theexpensesmanagement.Activity.Expenses_wise_info;
import com.example.kharcha_theexpensesmanagement.Model.ExpensesModel;
import com.example.kharcha_theexpensesmanagement.Model.MemberModel;
import com.example.kharcha_theexpensesmanagement.Model.Member_Selection_Model;
import com.example.kharcha_theexpensesmanagement.Model.Selectmember_Model;
import com.example.kharcha_theexpensesmanagement.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MemberWise_ExpensedataAdapter extends RecyclerView.Adapter<MemberWise_ExpensedataAdapter.MyViewHolder> {
    Context context;
    ArrayList<ExpensesModel> allArrayList;

    TextView group_id;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    int mTotalAmount=0;

    public MemberWise_ExpensedataAdapter(Context context) {
        this.context = context;
        allArrayList = new ArrayList<>();
    }

    public void add(ExpensesModel expenseDATA) {
        allArrayList.add(expenseDATA);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.expense_member_list, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        ExpensesModel expensesModel = allArrayList.get(position);

        holder.expense_name.setText(expensesModel.getExpenses_Name());
        holder.expense_amount.setText(expensesModel.getAmount_expense());
        holder.expenseTime.setText(expensesModel.getTimeStamp());
        holder.expen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(view.getContext(), Expenses_wise_info.class);
                i1.putExtra("expenses_id",expensesModel.getExpenses_id());
                i1.putExtra("expenses_name",expensesModel.getExpenses_Name());
                i1.putExtra("who_paid",expensesModel.getWho_paid());

                view.getContext().startActivity(i1);
            }
        });
    //    mTotalAmount+=Integer.parseInt(expensesModel.getAmount_expense());
        //   holder.group_id.setText(memberModel.getGroup_id());
       // holder.expense_amount.setText(mTotalAmount);


    }

    @Override
    public int getItemCount() {
        return allArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView expense_name, expense_amount,expenseTime,member_id;
        CardView expen;
        public MyViewHolder(View itemView) {
            super(itemView);
            expense_name = itemView.findViewById(R.id.expName);
            expense_amount = itemView.findViewById(R.id.expAmt);
            expenseTime=itemView.findViewById(R.id.expTime);
            expen=itemView.findViewById(R.id.add_expense_info);

        }
    }
    public int getTotalAmount(){
        return mTotalAmount;
    }
}
