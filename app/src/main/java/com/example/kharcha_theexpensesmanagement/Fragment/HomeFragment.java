package com.example.kharcha_theexpensesmanagement.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.kharcha_theexpensesmanagement.Adapter.grpAdapter2;
import com.example.kharcha_theexpensesmanagement.Model.GroupModel;
import com.example.kharcha_theexpensesmanagement.R;
import com.example.kharcha_theexpensesmanagement.Adapter.SliderAdapter;
import com.example.kharcha_theexpensesmanagement.databinding.FragmentCommunityBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.eazegraph.lib.charts.PieChart;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    //FloatingActionButton fab;
    private Button click;
    private PieChart chart;
//    private int i1 = 15;
//    private int i2 = 25;
//    private int i3 = 35;
//    private int i4 = 45;

    LinearLayout mDotLayout;
    TextView[] dots;

    private ViewPager2 viewPager2;
    private Handler slideHandler = new Handler();
    List<GroupModel> grouplist = new ArrayList<>();
    FirebaseFirestore db;
    FirebaseAuth auth;

    grpAdapter2 grpAdapter;

    ProgressDialog progressDialog;

    FragmentCommunityBinding binding;
    RecyclerView recyclerView;
    CollectionReference collectionReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager2 = view.findViewById(R.id.viewpagerImage);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("LOADING...");
        progressDialog.show();



        recyclerView = view.findViewById(R.id.grp_recycle1);

        collectionReference = db.collection("group_info");
        grpAdapter = new grpAdapter2(getActivity());
        recyclerView.setAdapter(grpAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


//        sliderItems.add(new SliderItem(R.drawable.));
//        sliderItems.add(new SliderItem(R.drawable.imoji_two));
//        sliderItems.add(new SliderItem(R.drawable.imoji_one));


        dataInitialize();
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(sliderRunnable);
                slideHandler.postDelayed(sliderRunnable, 1600);
            }
        });
        Data();
        return view;

    }

    private void Data() {
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
    }

    private void dataInitialize() {
        grouplist.clear();
        db.collection("group_info").whereEqualTo("user_email",auth.getCurrentUser().getEmail()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> groupdetails = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot ds : groupdetails) {
                    GroupModel groupModel = ds.toObject(GroupModel.class);
                    grouplist.add(groupModel);
                }
                viewPager2.setAdapter(new SliderAdapter(grouplist, viewPager2, getActivity()));
                viewPager2.setClipToPadding(false);
                viewPager2.setClipChildren(false);
                viewPager2.setOffscreenPageLimit(3);
                viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {

            }
        });


    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };
//        fab=view.findViewById(R.id.fab_home);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i1=new Intent(getActivity(),Add_group.class);
//                startActivity(i1);
//            }
//        });

//        click = view.findViewById(R.id.btn_click);
//        chart = view.findViewById(R.id.pie_chart);
//
//        click.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//        addToPieChart();
//        mDotLayout = (LinearLayout) view.findViewById(R.id.indicator1);
//        setUpindicator(3);

}

//    @TargetApi(Build.VERSION_CODES.M)
//    private void setUpindicator(int position) {
//        dots = new TextView[4];
//        mDotLayout.removeAllViews();
//
//        for (int i = 0; i < dots.length; i++) {
//
//            dots[i] = new TextView(getActivity());
//            dots[i].setText(Html.fromHtml("&#8226"));
//            dots[i].setTextSize(35);
//            dots[i].setTextColor(getResources().getColor(R.color.purple_500, getActivity().getTheme()));
//            mDotLayout.addView(dots[i]);
//        }
//        dots[position].setTextColor(getResources().getColor(R.color.purple_200,getActivity().getTheme()));
//
//    }

//    private void addToPieChart() {
//        // add to pie chart
//
////        chart.addPieSlice(new PieModel("Integer 1", i1, Color.parseColor("#FFA726")));
////        chart.addPieSlice(new PieModel("Integer 2", i2, Color.parseColor("#66BB6A")));
////        chart.addPieSlice(new PieModel("Integer 3", i3, Color.parseColor("#EF5350")));
////        chart.addPieSlice(new PieModel("Integer 4", i4, Color.parseColor("#2986F6")));
////
////        chart.startAnimation();
////        click.setClickable(false);
//    }
