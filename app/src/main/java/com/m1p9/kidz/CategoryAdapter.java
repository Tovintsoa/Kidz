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
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.m1p9.kidz.databinding.ActivityMainBinding;
import com.m1p9.kidz.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>{

    private Context context;
    private List<Category> categoriesList;
    private DrawerLayout mDrawer;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;


    public CategoryAdapter(Context context , List<Category> categories){
        this.context = context;
        categoriesList = categories;
    }
    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        mDrawer = (DrawerLayout) ((AppCompatActivity)context).findViewById(R.id.drawer_layout);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category category = categoriesList.get(position);
        holder.cName.setText(category.getcName().toString());
        holder.cDescription.setText(category.getcDescription().toString());
        Glide.with(context).load(category.getcImage()).into(holder.imageView);

       /* LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = ActivityMainBinding.inflate(li);*/
        //context.setContentView(binding.getRoot());


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





                /*Class fragmentClass = VideoFragment.class;
                Fragment fragment = null;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Bundle bundle = new Bundle();
                bundle.putString("id",category.getId());
                bundle.putString("cName", category.getcName());
                bundle.putString("cDescription", category.getcDescription());
                bundle.putString("cImage", category.getcImage());
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragment);
                fragmentManager.popBackStack();
                fragmentTransaction.commit();
                mDrawer.closeDrawers();*/

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
