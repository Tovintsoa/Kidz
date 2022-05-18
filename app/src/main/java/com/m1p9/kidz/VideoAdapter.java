package com.m1p9.kidz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.m1p9.kidz.model.Video;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder>{

    private Context context;
    private List<Video> videosList;

    public VideoAdapter(Context context , List<Video> videos){
        this.context = context;
        this.videosList = videos;
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemvideo,parent,false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {
        Video video = videosList.get(position);

        //getLifecycle().addObserver(holder.youTubePlayerView);
        holder.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = video.getvUrl();
                youTubePlayer.cueVideo(videoId,0);
            }
        });
        holder.vName.setText(video.getvName());
    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }


    public class VideoHolder extends RecyclerView.ViewHolder{

        YouTubePlayerView youTubePlayerView;
        TextView vName;
        LinearLayout linearLayout;
        public VideoHolder(@NonNull View itemView){
            super(itemView);
            youTubePlayerView = itemView.findViewById(R.id.videoView);
            vName = itemView.findViewById(R.id.vName);
            linearLayout = itemView.findViewById(R.id.video_layout);
        }
    }
}
