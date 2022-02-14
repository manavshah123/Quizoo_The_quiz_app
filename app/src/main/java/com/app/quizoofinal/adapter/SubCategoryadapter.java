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

import com.app.quizoofinal.QuestionActivity;
import com.app.quizoofinal.QuizCategory;
import com.app.quizoofinal.R;
import com.app.quizoofinal.model.Categorymodel;
import com.app.quizoofinal.model.SubCategorymodel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SubCategoryadapter extends RecyclerView.Adapter<SubCategoryadapter.Viewholder>{

    private Context context;
    private ArrayList<SubCategorymodel> subCategoryadapters;

    public SubCategoryadapter(Context context, ArrayList<SubCategorymodel> subCategoryadapters) {
        this.context = context;
        this.subCategoryadapters = subCategoryadapters;
    }

    @NonNull
    @Override
    public SubCategoryadapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategory_item_list, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryadapter.Viewholder holder, int position) {
        SubCategorymodel model = subCategoryadapters.get(position);
        holder.subcategoryname.setText(model.getName());
        Glide.with(context).load(model.getNameimage()).into(holder.subcategoryimage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuestionActivity.class);
                intent.putExtra("EXTRA_SESSION_ID", model.getName());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  subCategoryadapters.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView subcategoryimage;
        private TextView subcategoryname;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            subcategoryname = itemView.findViewById(R.id.category_name);
            subcategoryimage = itemView.findViewById(R.id.category_image);
        }
    }

}
