package com.m1p9.kidz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.m1p9.kidz.model.Video;
import com.m1p9.kidz.ui.home.VolleySingleton;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<Video> videoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#FF6200EE"));
        actionBar.setBackgroundDrawable(colorDrawable);

        // Set BackgroundDrawable

        //ImageView imageView = findViewById(R.id.poster_image);
        //TextView cName = findViewById(R.id.mTitle);
        //YouTubePlayerView ytb = findViewById(R.id.youtube_player_view);


        recyclerView = findViewById(R.id.recyclervideo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle bundle = getIntent().getExtras();

        String mCId = bundle.getString("id");
        String mCName = bundle.getString("cName");
        String mCDescription = bundle.getString("cDescription");
        String mCImage = bundle.getString("cImage");
        actionBar.setTitle(mCName);
        //Glide.with(this).load(mCImage).into(imageView);
        //cName.setText(mCName);
//        getLifecycle().addObserver(ytb);
//        ytb.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                String videoId = "S0Q4gqBUs7c";
//                youTubePlayer.loadVideo(videoId,0);
//            }
//        });

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        videoList = new ArrayList<>();
        fetchVideos(mCId);

    }
    private void fetchVideos(String id){
        String url = "https://api-kids.herokuapp.com/video/allVideosByCategory/"+id;
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
                    VideoAdapter adapter = new VideoAdapter(VideoActivity.this,videoList);

                    recyclerView.setAdapter(adapter);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VideoActivity.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}