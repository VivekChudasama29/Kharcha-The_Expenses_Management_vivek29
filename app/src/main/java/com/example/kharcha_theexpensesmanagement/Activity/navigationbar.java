package com.example.kharcha_theexpensesmanagement.Activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.kharcha_theexpensesmanagement.Fragment.CommunityFragment;
import com.example.kharcha_theexpensesmanagement.Fragment.HomeFragment;
import com.example.kharcha_theexpensesmanagement.Fragment.PersonFragment;
import com.example.kharcha_theexpensesmanagement.Fragment.Recent_ActivityFragment;
import com.example.kharcha_theexpensesmanagement.Fragment.SettingFragment;
import com.example.kharcha_theexpensesmanagement.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class navigationbar extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ImageView profile_show;
    HomeFragment homeFragment = new HomeFragment();
    CommunityFragment communityFragment = new CommunityFragment();
    Recent_ActivityFragment recentActivityFragment = new Recent_ActivityFragment();
    PersonFragment personFragment=new PersonFragment();
    SettingFragment settingFragment=new SettingFragment();
    FirebaseFirestore db;
    FirebaseAuth auth;
    String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigationbar);
        setSupportActionBar(toolbar);
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        navigationView = findViewById(R.id.nav);
        drawerLayout = findViewById(R.id.drawerLayout);
        profile_show=findViewById(R.id.header_pic);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        UID = auth.getCurrentUser().getUid();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent i1 = new Intent(navigationbar.this, navigationbar.class);
                        startActivity(i1);
                        finish();
                        break;
                    case R.id.rate:
                        // Toast.makeText(getApplicationContext(), "HELLO USER", Toast.LENGTH_SHORT).show();
                        Intent i2 = new Intent(navigationbar.this, RatingActivity.class);
                        startActivity(i2);
                        finish();
                        break;
                    case R.id.Community:
                        Intent i3 = new Intent(navigationbar.this, Add_group.class);
                        startActivity(i3);
                        finish();
                        break;
                    case R.id.person_:
                        Intent i4 = new Intent(navigationbar.this, ProfileActivity.class);
                        startActivity(i4);
                        finish();
                        break;

                    case R.id.userFeedback:
                        Intent i5 = new Intent(navigationbar.this, Feedback.class);
                        startActivity(i5);
                        finish();
                        break;

                    case R.id.Settings:
                        Intent i6 = new Intent(navigationbar.this, Settings.class);
                        startActivity(i6);
                        finish();
                        break;


                }
                return false;
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment).commit();

//        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.notification);
//        badgeDrawable.setVisible(true);
//        badgeDrawable.setNumber(8);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.Home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment).commit();
                        return true;

                    case R.id.community_:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, communityFragment).commit();
                        return true;

//                    case R.id.Recent_Activity:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, recentActivityFragment).commit();
//                        return true;

                    case R.id.Setting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, settingFragment).commit();
                        return true;
                }
                return true;
            }
        });
        update();
    }

    private void update() {
        navigationView = findViewById(R.id.nav);
        View view = navigationView.getHeaderView(0);
        TextView email = view.findViewById(R.id.header_email);
        TextView name = view.findViewById(R.id.header_name);
        ImageView image=view.findViewById(R.id.header_pic);
        DocumentReference documentReference = db.collection("user").document(UID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot value, FirebaseFirestoreException error) {
                String imageUrl = value.getString("profileImageUrl");
                Glide.with(navigationbar.this)
                        .load(imageUrl)
                        .centerCrop()
                        .placeholder(R.drawable.person_)
                        .transform(new CircleCrop())
                        .into(image);
                //image.setImageURI(value.getString("profileImageUrl"));
                email.setText(value.getString("user_email"));
                name.setText(value.getString("fullname"));
            }
        });

    }

    private void fragmentR(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}