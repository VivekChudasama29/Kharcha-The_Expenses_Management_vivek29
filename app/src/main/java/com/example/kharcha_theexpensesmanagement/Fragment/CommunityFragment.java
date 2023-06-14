package com.example.kharcha_theexpensesmanagement.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kharcha_theexpensesmanagement.Adapter.grpAdapter2;
import com.example.kharcha_theexpensesmanagement.Adapter.grpAdapter3;
import com.example.kharcha_theexpensesmanagement.Model.GroupData;
import com.example.kharcha_theexpensesmanagement.Model.GroupModel;
import com.example.kharcha_theexpensesmanagement.R;
import com.example.kharcha_theexpensesmanagement.databinding.FragmentCommunityBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class CommunityFragment extends Fragment {
    ImageView addgroup;
    private ArrayList<GroupData> allArraylist;
    private String[] name;
    private String[] offer;
    private int[] imageRecource;

    FirebaseAuth auth;
    FirebaseFirestore db;

    grpAdapter3 grpAdapter;

    ProgressDialog progressDialog;

    FragmentCommunityBinding binding;
    RecyclerView recyclerView;
    CollectionReference collectionReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentCommunityBinding.inflate(getLayoutInflater());
        View view = inflater.inflate(R.layout.fragment_community, container, false);


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("LOADING...");
        progressDialog.show();
//        recyclerView = view.findViewById(R.id.grp_recycle1);
//
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView = view.findViewById(R.id.grp_recycle1);

        auth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();
        // allArraylist = new ArrayList<GroupData>();
        collectionReference = db.collection("group_info");
        grpAdapter = new grpAdapter3(getActivity());
        recyclerView.setAdapter(grpAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataInitialize();


//          rc=view.findViewById(R.id.whole);
//          rc.setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View view) {
//                  Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_SHORT).show();
//              }
//          });
//onButtonClick(view);
        return view;
    }
//    public void onButtonClick(View view) {
//        // Navigate to another fragment or activity here
//        // For example, to navigate to another fragment:
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        ShareFragment fragment = new ShareFragment();
//        fragmentTransaction.replace(R.id.fragment_share, fragment).commit();
//       // fragmentTransaction.addToBackStack(null);
//        //fragmentTransaction.commit();
//    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        dataInitialize();

    }

    private void dataInitialize() {
        collectionReference.whereEqualTo("user_email", auth.getCurrentUser().getEmail()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> dslist = queryDocumentSnapshots.getDocuments();

                for (DocumentSnapshot DS : dslist) {

                    GroupModel groupModel = DS.toObject(GroupModel.class);
                    grpAdapter.add(groupModel);
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                if (dslist.isEmpty()) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
//        db.collection("group_info").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if (error != null) {
//                    if (progressDialog.isShowing())
//                        progressDialog.dismiss();
//                    Log.e("Firestore error", error.getMessage());
//                    return;
//                }
//                for (DocumentChange dc : value.getDocumentChanges()) {
//                    if (dc.getType() == DocumentChange.Type.ADDED) {
//                        allArraylist.add(dc.getDocument().toObject(GroupData.class));
//                    }
//                    grpAdapter.notifyDataSetChanged();
//                    if (progressDialog.isShowing())
//                        progressDialog.dismiss();
//                }
//            }
//        });

//

    }
}