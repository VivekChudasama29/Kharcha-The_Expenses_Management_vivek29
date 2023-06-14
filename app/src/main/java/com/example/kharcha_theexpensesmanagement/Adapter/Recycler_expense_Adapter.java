package com.example.kharcha_theexpensesmanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kharcha_theexpensesmanagement.Model.MemberModel;
import com.example.kharcha_theexpensesmanagement.R;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class Recycler_expense_Adapter extends RecyclerView.Adapter<Recycler_expense_Adapter.ViewHolder> {
//    Context context;
//    ArrayList<MemberModel> allArrayList;
//
//    TextView group_id;
//    FirebaseAuth firebaseAuth;
//    FirebaseFirestore firebaseFirestore;
//
//    private List<Selectmember_Model> memberList;
//    Selectmember_Model selectmember_model;
//
//
//    public Recycler_expense_Adapter(Context context) {
//        this.context = context;
//        allArrayList=new ArrayList<>();
//    }
//    public void add(MemberModel memberDATA){
//        allArrayList.add(memberDATA);
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public Recycler_expense_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.expense_member_recycle, parent, false);
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(Recycler_expense_Adapter.MyViewHolder holder, int position) {
//        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseFirestore = FirebaseFirestore.getInstance();
//        MemberModel memberModel = allArrayList.get(position);
//
//        holder.cb.setText(memberModel.getMemberName());
//        holder.member.setText(memberModel.getMember_id());
//
//     //   holder.group_id.setText(memberModel.getGroup_id());
//
////        firebaseFirestore.collection("expenses_info").whereEqualTo("group_id",memberModel.getGroup_id()).whereEqualTo("member_id",memberModel.getMember_id()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
////            @Override
////            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
////                holder.group_id.setText(expensesModel.getExpenses_id());
////            }
////        });
//
//
//
//
//
//        //  List<String> checkedItems = selectmember_model.getCheckedItems();
////        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
////            @Override
////            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                String select_id= UUID.randomUUID().toString();
////                if (isChecked) {
////                    Member_Selection_Model nn=new Member_Selection_Model(select_id,holder.group_id.getText().toString(),holder.member.getText().toString(),"3",holder.cb.getText().toString(),"100");
////                    firebaseFirestore.collection("expenses_settlement").document(select_id).set(nn);
////              //      checkedItems.add("Item name");
////                    Toast.makeText(context, "ADDED", Toast.LENGTH_SHORT).show();
////                } else {
////               //
////                    firebaseFirestore.collection("expenses_settlement").document(select_id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
////                        @Override
////                        public void onSuccess(Void unused) {
////                            Toast.makeText(context, "NOT_ADDED", Toast.LENGTH_SHORT).show();
////
////                        }
////                    });
////                    //     checkedItems.remove("Item name");
////                }
////            }
////        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return allArrayList.size();
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder {
//
//        TextView amount,member,group_id,expene_id;
//        TextView cb;
//
//        public MyViewHolder(View itemView) {
//            super(itemView);
//
//            //amount = itemView.findViewById(R.id.member_amount_rec);
//            cb = itemView.findViewById(R.id.member_checkbox_rec);
//            member=itemView.findViewById(R.id.member_id_rec);
//            group_id=itemView.findViewById(R.id.group_id_rec);
//            expene_id=itemView.findViewById(R.id.expense_id_rec);
//
//        }
//    }

    private ArrayList<MemberModel> dataList;
    private ArrayList<MemberModel> selectedList = new ArrayList<>();
    Context context;

    public Recycler_expense_Adapter(ArrayList<MemberModel> dataList) {
        this.dataList = dataList;
    }
//    public void add(MemberModel memberDATA){
//        dataList.add(memberDATA);
//        notifyDataSetChanged();
//    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_member_recycle, parent, false);
        return new Recycler_expense_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MemberModel model = dataList.get(position);
        holder.nameTextView.setText(model.getMemberName());
        holder.checkBox.setText(model.getMemberName());
        holder.checkBox.setChecked(selectedList.contains(model));
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedList.add(model);
            } else {
                selectedList.remove(model);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public ArrayList<MemberModel> getSelectedList() {
        return selectedList;
    }
    public int getSelectedListsize(ArrayList<MemberModel> selectedsize){
        return  selectedsize.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.member_id_rec);
            checkBox = itemView.findViewById(R.id.member_checkbox_rec);
        }
    }
}
