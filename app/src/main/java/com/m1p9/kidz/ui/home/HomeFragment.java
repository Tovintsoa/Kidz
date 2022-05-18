package com.m1p9.kidz.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.m1p9.kidz.CategoryActivity;
import com.m1p9.kidz.CategoryAdapter;
import com.m1p9.kidz.R;
import com.m1p9.kidz.databinding.FragmentHomeBinding;
import com.m1p9.kidz.model.Category;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<Category> categorieList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState){
        super.onViewCreated(view,saveInstanceState);
        this.recyclerView = view.findViewById(R.id.favorisView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        requestQueue = VolleySingleton.getmInstance(getActivity()).getRequestQueue();
        categorieList = new ArrayList<>();
        fetchCategories();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void fetchCategories() {
        String url = "https://api-kids.herokuapp.com/category/allCategories";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0 ; i < response.length() ; i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String cName = jsonObject.getString("cName");
                        String cDescription = jsonObject.getString("cDescription");
                        String cImage = jsonObject.getString("cImage");

                        Category category = new Category(id,cName,cDescription,cImage);
                        categorieList.add(category);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    CategoryAdapter adapter = new CategoryAdapter(getActivity(),categorieList);

                    recyclerView.setAdapter(adapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

}