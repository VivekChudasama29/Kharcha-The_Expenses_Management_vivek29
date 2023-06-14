package com.example.kharcha_theexpensesmanagement.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kharcha_theexpensesmanagement.Activity.Final_Settlement;
import com.example.kharcha_theexpensesmanagement.Model.MemberModel;
import com.example.kharcha_theexpensesmanagement.R;

import java.util.ArrayList;

public class settlement_adapter extends RecyclerView.Adapter<settlement_adapter.MyViewHolder> {
    Context context;
    ArrayList<MemberModel> allArrayList;

//    TextView group_id;
//    FirebaseAuth firebaseAuth;
//    FirebaseFirestore firebaseFirestore;

//    int mTotalAmount=0;

    public settlement_adapter(Context context) {
        this.context = context;
        allArrayList = new ArrayList<>();
    }

    public void add(MemberModel mmdata) {
        allArrayList.add(mmdata);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_member_settle, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseFirestore = FirebaseFirestore.getInstance();
        MemberModel memberModel = allArrayList.get(position);

        holder.member_id.setText(memberModel.getMember_id());
        holder.settle_member.setText(memberModel.getMemberName());
        //holder.settle_amt.setText(memberModel.getMemberMno());
holder.arrow_forword.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i1=new Intent(context, Final_Settlement.class);
        i1.putExtra("GroupNameSettle",memberModel.getGroup_Name());
        i1.putExtra("GroupIdSettle",memberModel.getGroup_id());
        i1.putExtra("MemberIdSettle",memberModel.getMember_id());
        i1.putExtra("MemberNameSettle",memberModel.getMemberName());
        i1.putExtra("MemberMnoSetttle",memberModel.getMemberMno());

        context.startActivity(i1);

    }
});
//        holder.expenseTime.setText(expensesModel.getTimeStamp());
        //    mTotalAmount+=Integer.parseInt(expensesModel.getAmount_expense());
        //   holder.group_id.setText(memberModel.getGroup_id());
        // holder.expense_amount.setText(mTotalAmount);




    }

    @Override
    public int getItemCount() {
        return allArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //        TextView expense_name, expense_amount,expenseTime,member_id;
        TextView settle_member,member_id;
        ImageView arrow_forword;

        public MyViewHolder(View itemView) {
            super(itemView);
            member_id=itemView.findViewById(R.id.newtxt_settlement_member_id);
            settle_member=itemView.findViewById(R.id.total_settlement_Member);
            arrow_forword=itemView.findViewById(R.id.for_arr_set);

        }
    }
//    public int getTotalAmount(){
//        return mTotalAmount;
//    }
}