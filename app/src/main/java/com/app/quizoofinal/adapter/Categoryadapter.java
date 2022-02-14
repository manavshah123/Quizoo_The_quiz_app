package com.app.quizoofinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.service.quickaccesswallet.QuickAccessWalletService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.quizoofinal.HomeScreen;
import com.app.quizoofinal.QuizCategory;
import com.app.quizoofinal.model.Categorymodel;
import com.app.quizoofinal.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Categoryadapter extends RecyclerView.Adapter<Categoryadapter.Viewholder> {

private Context context;
private ArrayList<Categorymodel> courseModelArrayList;

// Constructor
public Categoryadapter(Context context, ArrayList<Categorymodel> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
        }


    @NonNull
@Override
public Categoryadapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_list, parent, false);
        return new Viewholder(view);
        }

@Override
public void onBindViewHolder(@NonNull Categoryadapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        Categorymodel model = courseModelArrayList.get(position);
        holder.categoryname.setText(model.getName());
        Glide.with(context).load(model.getNameimage()).into(holder.categoryimage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuizCategory.class);
                intent.putExtra("EXTRA_SESSION_ID", model.getName());
                intent.putExtra("EXTRA_SESSION_Image", model.getNameimage());
                context.startActivity(intent);
            }
        });

        }

@Override
public int getItemCount() {
        return courseModelArrayList.size();
        }

public class Viewholder extends RecyclerView.ViewHolder {
    private ImageView categoryimage;
    private TextView categoryname;

    public Viewholder(@NonNull View itemView) {
        super(itemView);
        categoryname = itemView.findViewById(R.id.category_name);
        categoryimage = itemView.findViewById(R.id.category_image);
    }
}
}