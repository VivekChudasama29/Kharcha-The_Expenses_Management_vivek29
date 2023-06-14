package com.example.kharcha_theexpensesmanagement.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kharcha_theexpensesmanagement.Activity.Add_Member;
import com.example.kharcha_theexpensesmanagement.Activity.Add_group;
import com.example.kharcha_theexpensesmanagement.Activity.Logins;
import com.example.kharcha_theexpensesmanagement.Activity.Settings;
import com.example.kharcha_theexpensesmanagement.Activity.expense_details;
import com.example.kharcha_theexpensesmanagement.Model.GroupModel;
import com.example.kharcha_theexpensesmanagement.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class grpAdapter extends RecyclerView.Adapter<grpAdapter.MyViewHolder> {

    Context context;
    ArrayList<GroupModel> allArrayList;
    FirebaseAuth auth;
    FirebaseFirestore db;

    public grpAdapter(Context context) {
        this.context = context;
        allArrayList=new ArrayList<>();
    }
    public void add(GroupModel GRPDATA){
        allArrayList.add(GRPDATA);
        notifyDataSetChanged();
    }
    @Override
    public grpAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.group_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        GroupModel grpData = allArrayList.get(position);
        holder.name.setText(grpData.getGroup_Name());

    //    holder.imageView.setImageResource(grpData.image);
        Glide.with(context).load(grpData.getGroup_img_url()).into(holder.imageView);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog=new AlertDialog.Builder(context)
                        .setTitle("Delete A Group")
                        .setMessage("Are You Sure?")
                        .setPositiveButton("Yes",null)
                        .setNegativeButton("No",null)
                        .show();

                Button positive=dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.collection("group_info").document(grpData.getGroup_id()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                allArrayList.remove(grpData.getGroup_id());
                                notifyDataSetChanged();
                                Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                                Intent i1 =new Intent(context, Add_group.class);
                                context.startActivity(i1);
                                ((Activity) context).finish();

                            }
                        });
                    }
                });

            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(),"Selected Groups : "+grpData.getGroup_Name(),Toast.LENGTH_SHORT).show();
                Intent i1 =new Intent(view.getContext(), Add_Member.class);
                i1.putExtra("GroupAddMember", grpData.getGroup_Name());
                i1.putExtra("GroupId",grpData.getGroup_id());
                //i1.putExtra("GroupId",grpData.get)
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
        Button delete;


        public MyViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageViewgrp);
            name=itemView.findViewById(R.id.grpName);
            cardView = itemView.findViewById(R.id.add_group_card);
            delete=itemView.findViewById(R.id.Delete);

        }
    }
}
