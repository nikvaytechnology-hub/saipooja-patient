package com.nikvay.saipooja_patient.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.nikvay.saipooja_patient.R;

public class GallaryActivity extends AppCompatActivity {

    LinearLayout ll_photo_gallary,ll_video_gallary;
    String fName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallary);


        find_All_Id();

        event();
    }



    private void find_All_Id()
    {
        ll_photo_gallary = findViewById(R.id.ll_photo_gallary);
        ll_video_gallary = findViewById(R.id.ll_video_gallary);
    }

    private void event()
    {
        ll_photo_gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GallaryActivity.this, PhotoGallaryActivity.class);
                startActivity(intent);
            }
        });

        ll_video_gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(GallaryActivity.this, VideoGallaryActivity.class);
                startActivity(intent);
            }
        });
    }

}
