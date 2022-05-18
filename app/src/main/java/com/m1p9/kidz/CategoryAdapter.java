package com.m1p9.kidz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.m1p9.kidz.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>{

    private Context context;
    private List<Category> categoriesList;

    public CategoryAdapter(Context context , List<Category> categories){
        this.context = context;
        categoriesList = categories;
    }
    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category category = categoriesList.get(position);
        holder.cName.setText(category.getcName().toString());
        holder.cDescription.setText(category.getcDescription().toString());
        Glide.with(context).load(category.getcImage()).into(holder.imageView);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id",category.getId());
                bundle.putString("cName", category.getcName());
                bundle.putString("cDescription", category.getcDescription());
                bundle.putString("cImage", category.getcImage());

                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView cName , cDescription;
        ConstraintLayout constraintLayout;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageview);
            cName = itemView.findViewById(R.id.title_tv);
            cDescription = itemView.findViewById(R.id.rating);
            constraintLayout = itemView.findViewById(R.id.main_layout);
        }
    }
}
