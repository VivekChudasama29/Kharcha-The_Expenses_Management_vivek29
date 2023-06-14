package com.example.kharcha_theexpensesmanagement.Adapter;

import android.annotation.SuppressLint;
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

import com.example.kharcha_theexpensesmanagement.Activity.Add_Expense_AMT;
import com.example.kharcha_theexpensesmanagement.Activity.Member_wise_Expenses;
import com.example.kharcha_theexpensesmanagement.Model.MemberModel;
import com.example.kharcha_theexpensesmanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class memberAdapter extends RecyclerView.Adapter<memberAdapter.MyViewHolder> {
    Context context;
    ArrayList<MemberModel> allArrayList;
    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser currentUser;
    FirebaseStorage st;
    StorageReference sr;
  //  private FloatingActionButton fab_ric;

    public memberAdapter(Context context) {
        this.context = context;
        allArrayList = new ArrayList<>();
    }

    public void add(MemberModel GRPDATA) {
        allArrayList.add(GRPDATA);
        notifyDataSetChanged();
    }

    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public memberAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.member_list, parent, false);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        st = FirebaseStorage.getInstance();
        sr = st.getReference();
     //   fab_ric=view.findViewById(R.id.add_mem_ex);
        return new memberAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MemberModel memberData = allArrayList.get(position);
        holder.name.setText(memberData.getMemberName());

        //    holder.imageView.setImageResource(grpData.image);
//        db.collection("user").document(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot doc = task.getResult();
//                    String imgurl = doc.getString("profileImageUrl");
//                    Glide.with(context).load(imgurl).into(holder.imageView);
//
//                }
//            }
//        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(view.getContext(),"Selected Groups : "+memberData.getGroup_Name(),Toast.LENGTH_SHORT).show();
                Intent i1 =new Intent(context, Member_wise_Expenses.class);
                i1.putExtra("GroupName",memberData.getGroup_Name());
                i1.putExtra("GroupId",memberData.getGroup_id());
                i1.putExtra("MemberId",memberData.getMember_id());
                i1.putExtra("MemberName",memberData.getMemberName());
                i1.putExtra("MemberMno",memberData.getMemberMno());

                //i1.putExtra("GroupId",grpData.get)
                context.startActivity(i1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;

        CardView cardView;


        public MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageViewmember);
            name = view.findViewById(R.id.memberName);
            cardView = view.findViewById(R.id.add_member_card);
        }
    }
}



