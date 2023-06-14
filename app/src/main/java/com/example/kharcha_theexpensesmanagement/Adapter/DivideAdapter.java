package com.example.kharcha_theexpensesmanagement.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kharcha_theexpensesmanagement.Model.MemberModel;
import com.example.kharcha_theexpensesmanagement.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class DivideAdapter extends RecyclerView.Adapter<DivideAdapter.MyViewHolder> {
    double diviamt;

    Context context;

    private ArrayList<MemberModel> dataList;


    public DivideAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
        Intent intent = ((Activity) context).getIntent();

        // Retrieve the data from the intent
        double val = intent.getDoubleExtra("Divide_amt", 0.0);
        if (val != 0.0) {

        }
    }

    public void add(MemberModel mmDATA) {
        dataList.add(mmDATA);
        notifyDataSetChanged();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.devide_recycle, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MemberModel model = dataList.get(position);

        Intent intent = ((Activity) context).getIntent();
        diviamt = intent.getDoubleExtra("Divide_amt", 0.0);
        holder.member_id.setText(model.getMember_id());
        holder.Member_name.setText(model.getMemberName());
        if (diviamt != 0.0) {
            holder.Amount.setText(String.valueOf(diviamt));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Member_name;
        TextView Amount, member_id;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            Member_name = itemView.findViewById(R.id.divide_Member);
            Amount = itemView.findViewById(R.id.divide_amt);
            cardView = itemView.findViewById(R.id.divide_expense_card);
            member_id = itemView.findViewById(R.id.newtxtmember_id);

        }
    }
}

