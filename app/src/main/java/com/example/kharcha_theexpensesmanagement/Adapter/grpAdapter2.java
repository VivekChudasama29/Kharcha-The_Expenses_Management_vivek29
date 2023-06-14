package com.example.kharcha_theexpensesmanagement.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kharcha_theexpensesmanagement.Activity.Final_Settlement2;
import com.example.kharcha_theexpensesmanagement.Model.GroupModel;
import com.example.kharcha_theexpensesmanagement.R;

import java.util.ArrayList;

public class grpAdapter2 extends RecyclerView.Adapter<grpAdapter2.MyViewHolder> {

    Context context;
    ArrayList<GroupModel> allArrayList;

    public grpAdapter2(Context context) {
        this.context = context;
        allArrayList=new ArrayList<>();
    }
    public void add(GroupModel GRPDATA){
        allArrayList.add(GRPDATA);
        notifyDataSetChanged();
    }
    @Override
    public grpAdapter2.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.group_list2, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        GroupModel grpData = allArrayList.get(position);
        holder.name.setText(grpData.getGroup_Name());
        //    holder.imageView.setImageResource(grpData.image);
        Glide.with(context).load(grpData.getGroup_img_url()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(view.getContext(),"Selected Groups : "+grpData.getGroup_Name(),Toast.LENGTH_SHORT).show();
                Intent i1 =new Intent(view.getContext(), Final_Settlement2.class);
                i1.putExtra("GroupNamedata2", grpData.getGroup_Name());
                i1.putExtra("GroupIddata2",grpData.getGroup_id());
               // i1.putExtra("GroupId",grpData.get)
                view.getContext().startActivity(i1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        CardView cardView;


        public MyViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageViewgrp);
            name=itemView.findViewById(R.id.grpName);
            cardView = itemView.findViewById(R.id.add_group_card);
        }
    }
}
