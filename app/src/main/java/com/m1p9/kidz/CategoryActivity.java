package com.m1p9.kidz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.m1p9.kidz.model.Category;
import com.m1p9.kidz.ui.home.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<Category> categorieList;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        categorieList = new ArrayList<>();
        fetchCategories();
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
                    CategoryAdapter adapter = new CategoryAdapter(CategoryActivity.this,categorieList);

                    recyclerView.setAdapter(adapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CategoryActivity.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}