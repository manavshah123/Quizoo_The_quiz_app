package com.app.quizoofinal;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.quizoofinal.adapter.Categoryadapter;
import com.app.quizoofinal.adapter.SubCategoryadapter;
import com.app.quizoofinal.model.Categorymodel;
import com.app.quizoofinal.model.SubCategorymodel;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firestore.v1.TargetChangeOrBuilder;

import java.util.ArrayList;

public class QuizCategory extends AppCompatActivity {

    private ArrayList<SubCategorymodel> subCategorymodels;
    private SubCategoryadapter subCategoryadapter;
    private DatabaseReference mDatabaseRef;
    ImageView img;
    String sessionId,nameimage;
    RecyclerView recyclerView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_category);
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.themedarkcolor));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.transperent));

        Toolbar toolbar = findViewById(R.id.toolbarcoll);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });


        sessionId = getIntent().getStringExtra("EXTRA_SESSION_ID");
        nameimage = getIntent().getStringExtra("EXTRA_SESSION_Image");

        img= findViewById(R.id.img1);
        Glide.with(getBaseContext()).load(nameimage).into(img);
        CollapsingToolbarLayout tv= findViewById(R.id.coll_tool);
        tv.setTitle(sessionId);


        /// Cardview for Category ////////////////////////////////////////

        recyclerView = findViewById(R.id.rv1);
        subCategorymodels = new ArrayList<SubCategorymodel>();
        categorydatachange();



    }

    public void categorydatachange(){
        subCategorymodels.clear();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("subcategory").child(sessionId);
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot zoneSnapshot: snapshot.getChildren()) {
                    SubCategorymodel cm=zoneSnapshot.getValue(SubCategorymodel.class);
                    subCategorymodels.add(cm);
                }

                subCategoryadapter= new SubCategoryadapter(QuizCategory.this,subCategorymodels);
                LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(new GridLayoutManager(QuizCategory.this, 2));
                recyclerView.setAdapter(subCategoryadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}