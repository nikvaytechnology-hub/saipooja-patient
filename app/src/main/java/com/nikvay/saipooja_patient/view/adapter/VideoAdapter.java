package com.nikvay.saipooja_patient.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.model.VideoListModule;
import com.nikvay.saipooja_patient.utils.Constants;
import com.nikvay.saipooja_patient.view.activity.VideoViewActivity;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class VideoAdapter  extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<VideoListModule> videoListModuleArrayList;


    public VideoAdapter(Context mContext, ArrayList<VideoListModule> videoListModuleArrayList) {
        this.mContext = mContext;
        this.videoListModuleArrayList = videoListModuleArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_item_video_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final VideoListModule videoModule = videoListModuleArrayList.get(position);

        String name = videoModule.getTitle();
        final String videoUrl = videoModule.getVideo_url();

        StringTokenizer st = new StringTokenizer(videoUrl, "=");
        final String aa = st.nextToken();
        final String finalLink = st.nextToken();

        holder.txt_video_name.setText(name);
        holder.video_thumbnail_image_view.initialize(Constants.API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {

                //when initialization is sucess, set the video id to thumbnail to load
                youTubeThumbnailLoader.setVideo(finalLink);
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        youTubeThumbnailLoader.release();
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                        //print or show error when thumbnail load failed
//                        Log.e(TAG, "Youtube Thumbnail Error");
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                //print or show error when initialization failed
//                Log.e(TAG, "Youtube Initialization Failure");

            }
        });
        holder.video_thumbnail_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, VideoViewActivity.class);
                intent.putExtra("video_id", finalLink);
                mContext.startActivity(intent);
/*
                mContext.startActivity(new Intent(mContext, VideoViewActivity.class).putExtra("video_id", finalLink));
*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return videoListModuleArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView txt_video_name, txt_video_desc;
        RelativeLayout rel_video_play;
        YouTubeThumbnailView video_thumbnail_image_view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_video_name = itemView.findViewById(R.id.txt_video_name);
            txt_video_desc = itemView.findViewById(R.id.txt_video_desc);
            rel_video_play = itemView.findViewById(R.id.rel_video_play);
            video_thumbnail_image_view = itemView.findViewById(R.id.video_thumbnail_image_view);

        }
    }
}
