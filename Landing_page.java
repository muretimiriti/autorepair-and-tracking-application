package com.example.garagevalley;

import static com.example.garagevalley.R.id.toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Landing_page extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView nav_view;
    private TextView nav_user_fullname,nav_user_email,nav_user_phonenumber;
    private DatabaseReference userRef;
    private TextView diagnostics,logout;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);


        toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("garage valley");

        drawerLayout =findViewById(R.id.drawerLayout);
        nav_view=findViewById(R.id.nav_view);


        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(Landing_page.this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav_user_email=nav_view.getHeaderView(0).findViewById(R.id.nav_user_email);
        nav_user_fullname=nav_view.getHeaderView(0).findViewById(R.id.nav_user_fullname);
        nav_user_phonenumber=nav_view.getHeaderView(0).findViewById(R.id.nav_user_phonenumber);

        userRef= FirebaseDatabase.getInstance().getReference().child("users").
                child(FirebaseAuth.getInstance().getCurrentUser().getUid());

       userRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if (snapshot.exists()){
                   String name=snapshot.child("name").getValue().toString();
                   nav_user_fullname.setText(name);

                   String email=snapshot.child("email").getValue().toString();
                   nav_user_email.setText(name);

                   String phonenumber=snapshot.child("phonenumber").getValue().toString();
                   nav_user_phonenumber.setText(name);
               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

        }




    }
