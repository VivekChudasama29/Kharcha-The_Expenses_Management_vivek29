package com.example.kharcha_theexpensesmanagement.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kharcha_theexpensesmanagement.Activity.Add_Expense_AMT;
import com.example.kharcha_theexpensesmanagement.Model.MemberModel;
import com.example.kharcha_theexpensesmanagement.R;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {

    Context context;
    ArrayList<MemberModel> allArrayList;

    public ExpenseAdapter(Context context) {
        this.context = context;
        allArrayList=new ArrayList<>();
    }
    public void add(MemberModel DATA){
        allArrayList.add(DATA);
        notifyDataSetChanged();
    }
//    @Override
//    public ExpenseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.group_list, parent, false);
//        return new MyViewHolder(view);
//    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        MemberModel grpData = allArrayList.get(position);
        holder.name.setText(grpData.getGroup_Name());
        holder.group_add_member_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Selected Groups : "+grpData.getGroup_Name(),Toast.LENGTH_SHORT).show();
                Intent i1 =new Intent(view.getContext(), Add_Expense_AMT.class);
                i1.putExtra("GroupName", grpData.getGroup_Name());
                view.getContext().startActivity(i1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,group_add_member_expense;
        CardView cardView;


        public MyViewHolder(View itemView) {
            super(itemView);
           // imageView=itemView.findViewById(R.id.imageViewgrp);
            name=itemView.findViewById(R.id.group_add_member_expense1);
            group_add_member_expense=itemView.findViewById(R.id.group_add_member_expense1);
            //cardView = itemView.findViewById(R.id.add_group_card);
        }
    }
}
