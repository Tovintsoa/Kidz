package com.m1p9.kidz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.m1p9.kidz.databinding.FragmentHomeBinding;
import com.m1p9.kidz.databinding.FragmentVideoBinding;
import com.m1p9.kidz.model.Category;
import com.m1p9.kidz.model.Video;
import com.m1p9.kidz.service.LoadingDialog;
import com.m1p9.kidz.ui.home.HomeViewModel;
import com.m1p9.kidz.ui.home.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentVideoBinding binding;
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<Video> videoList;

    public VideoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoFragment newInstance(String param1, String param2) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_video, container, false);
        View view = inflater.inflate(R.layout.fragment_video,container,false);
        binding = FragmentVideoBinding.inflate(inflater, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState){
        super.onViewCreated(view,saveInstanceState);
        this.recyclerView = view.findViewById(R.id.recyclervideo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
       /* recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));*/

        Bundle bundle = this.getArguments();

        String mCId = bundle.getString("id");
        String mCName = bundle.getString("cName");
        String mCDescription = bundle.getString("cDescription");
        String mCImage = bundle.getString("cImage");

        requestQueue = VolleySingleton.getmInstance(getActivity()).getRequestQueue();

        videoList = new ArrayList<>();
        fetchVideos(mCId);

    }
    private void fetchVideos(String id){
        String url = "https://api-kids.herokuapp.com/video/allVideosByCategory/"+id;
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoadingDialog();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,url,null , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0 ; i < response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String vName = jsonObject.getString("vName");
                        String vUrl = jsonObject.getString("vUrl");

                        Video video = new Video(id,vName,vUrl);
                        videoList.add(video);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    VideoAdapter adapter = new VideoAdapter(getActivity(),videoList);
                    loadingDialog.dismissDialog();
                    recyclerView.setAdapter(adapter);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismissDialog();
                Toast.makeText(getActivity(), error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}