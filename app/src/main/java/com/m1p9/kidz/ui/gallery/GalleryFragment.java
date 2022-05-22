package com.m1p9.kidz.ui.gallery;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.m1p9.kidz.CategoryAdapter;
import com.m1p9.kidz.R;
import com.m1p9.kidz.VideoAdapter;
import com.m1p9.kidz.databinding.FragmentGalleryBinding;
import com.m1p9.kidz.databinding.FragmentHomeBinding;
import com.m1p9.kidz.model.Category;
import com.m1p9.kidz.model.Video;
import com.m1p9.kidz.service.LoadingDialog;
import com.m1p9.kidz.ui.home.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<Video> videoList;
    private SearchView searchView;
    private boolean test;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery,container,false);
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState){
        super.onViewCreated(view,saveInstanceState);
        this.recyclerView = view.findViewById(R.id.videoViewGal);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        requestQueue = VolleySingleton.getmInstance(getActivity()).getRequestQueue();
        videoList = new ArrayList<>();

        searchView = view.findViewById(R.id.mySearchView);
        searchView.clearFocus();
        fetchVideo();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {


                    filterList(query);

                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                fetchVideo();
                return false;
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void fetchVideo() {
        String url = "https://api-kids.herokuapp.com/video/allVideos";
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoadingDialog();
        videoList.removeAll(videoList);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0 ; i < response.length() ; i++){

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String vName = jsonObject.getString("vName");
                        String vUrl = jsonObject.getString("vUrl");
                        Video video = new Video(id,vName,vUrl);
                        videoList.add(video);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    VideoAdapter adapter = new VideoAdapter(getActivity(),videoList);

                    recyclerView.setAdapter(adapter);
                }
                loadingDialog.dismissDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(),Toast.LENGTH_SHORT).show();
                loadingDialog.dismissDialog();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    private void filterList(String text){

        String url = "https://api-kids.herokuapp.com/video/searchVideo/"+text;
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoadingDialog();
        videoList.removeAll(videoList);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0 ; i < response.length() ; i++){

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String vName = jsonObject.getString("vName");
                        String vUrl = jsonObject.getString("vUrl");
                        Video video = new Video(id,vName,vUrl);

                        videoList.add(video);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    VideoAdapter adapter = new VideoAdapter(getActivity(),videoList);
                    recyclerView.setAdapter(adapter);
                }
                loadingDialog.dismissDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(),Toast.LENGTH_SHORT).show();
                loadingDialog.dismissDialog();

            }
        });
        requestQueue.add(jsonArrayRequest);

    }
}