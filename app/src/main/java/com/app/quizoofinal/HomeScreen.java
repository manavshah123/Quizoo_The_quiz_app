package com.app.quizoofinal;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.quizoofinal.adapter.Materialadapter;
import com.app.quizoofinal.model.Categorymodel;
import com.app.quizoofinal.model.Materialmodel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import com.app.quizoofinal.adapter.Categoryadapter;

public class HomeScreen extends AppCompatActivity {

    private ArrayList<Categorymodel> categorymodels;
    private ArrayList<Materialmodel> materialmodels;
    private Categoryadapter categoryadapter;
    private Materialadapter materialadapter;
    private DatabaseReference mDatabaseRef;
    private ProgressBar pb;
    String username;
    TextView user;
    RecyclerView recyclerView,recyclerView2;
    SwipeRefreshLayout swipeRefreshLayout;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.themedarkcolor));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.transperent));

        // Set username ///
        username=getIntent().getStringExtra("usernamelogin");
        user=findViewById(R.id.username);
        user.setText("Hello,"+ username);

        //// Refresh Swipe //////////

        swipeRefreshLayout = findViewById(R.id.refreshswipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                categorydatachange();
               swipeRefreshLayout.setRefreshing(false);
            }
        });

        ///////////// Card View //////////////////////////////
        pb=findViewById(R.id.pb1);
        Drawable draw= getDrawable(R.drawable.customprogressbar);
// set the drawable as progress drawable
        pb.setProgressDrawable(draw);

        recyclerView = findViewById(R.id.rv1);
        recyclerView2 = findViewById(R.id.rv2);
        categorymodels = new ArrayList<Categorymodel>();
        materialmodels = new ArrayList<Materialmodel>();
        categorydatachange();
        materialdatachange();
    }

    public void categorydatachange(){
        categorymodels.clear();
        pb.setVisibility(View.VISIBLE);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("category");
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot zoneSnapshot: snapshot.getChildren()) {

                    Categorymodel cm=zoneSnapshot.getValue(Categorymodel.class);
                    categorymodels.add(cm);
                }


                categoryadapter= new Categoryadapter(HomeScreen.this,categorymodels);

                LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(categoryadapter);
                pb.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void materialdatachange(){
        materialmodels.clear();
        pb.setVisibility(View.VISIBLE);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("material");
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot zoneSnapshot: snapshot.getChildren()) {
                    Materialmodel cm=zoneSnapshot.getValue(Materialmodel.class);
                    materialmodels.add(cm);
                }


                materialadapter= new Materialadapter(HomeScreen.this,materialmodels);

                LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView2.setLayoutManager(linearLayoutManager);
                recyclerView2.setAdapter(materialadapter);
                pb.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}