package com.m1p9.kidz.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.m1p9.kidz.R;
import com.m1p9.kidz.databinding.FragmentHomeBinding;
import com.m1p9.kidz.model.Category;


import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
      /*  homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);*/
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();
        String[] content = {"Try","Catch","finally"};
        GridView grid = view.findViewById(R.id.favorisView);
        System.out.print(grid);
        ArrayAdapter<String> gridViewAdapter = new ArrayAdapter<String>(
            getActivity(),
            android.R.layout.simple_list_item_1,
                content
        );
        grid.setAdapter(gridViewAdapter);
        //final TextView textView = binding.textHome;
       /* homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}