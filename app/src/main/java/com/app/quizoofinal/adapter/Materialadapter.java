package com.app.quizoofinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.quizoofinal.Material_activity;
import com.app.quizoofinal.QuizCategory;
import com.app.quizoofinal.R;
import com.app.quizoofinal.model.Categorymodel;
import com.app.quizoofinal.model.Materialmodel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Materialadapter extends RecyclerView.Adapter<Materialadapter.Viewholder> {

private Context context;
private ArrayList<Materialmodel> courseModelArrayList;

public Materialadapter(Context context, ArrayList<Materialmodel> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
        }


    @NonNull
@Override
public Materialadapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_item_list, parent, false);
        return new Viewholder(view);
        }

@Override
public void onBindViewHolder(@NonNull Materialadapter.Viewholder holder, int position) {

        Materialmodel model = courseModelArrayList.get(position);
        Glide.with(context).load(model.getNameimage()).into(holder.material_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Material_activity.class);
                intent.putExtra("EXTRA_SESSION_ID", model.getPdfurl());
                context.startActivity(intent);
            }
        });

        }

@Override
public int getItemCount() {
        return courseModelArrayList.size();
        }

public class Viewholder extends RecyclerView.ViewHolder {
    private ImageView material_image;

    public Viewholder(@NonNull View itemView) {
        super(itemView);
        material_image = itemView.findViewById(R.id.material_image);
    }
}
}