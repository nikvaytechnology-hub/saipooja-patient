package com.nikvay.saipooja_patient.view.activity;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.utils.Constants;

public class VideoViewActivity extends YouTubeBaseActivity {

    private String videoID;
    private YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            videoID = bundle.getString("video_id");
        }


        youTubePlayerView = findViewById(R.id.youtube_player_view);
        
        initializeYoutubePlayer();
    }

    private void initializeYoutubePlayer()
    {
        youTubePlayerView.initialize(Constants.API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {

                if (!wasRestored) {
                    //set the player style here: like CHROMELESS, MINIMAL, DEFAULT
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

                    //load the video
                    youTubePlayer.loadVideo(videoID);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

    }
}
