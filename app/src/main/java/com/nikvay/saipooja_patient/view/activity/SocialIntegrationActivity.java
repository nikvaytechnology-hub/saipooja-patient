package com.nikvay.saipooja_patient.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.nikvay.saipooja_patient.R;

public class SocialIntegrationActivity extends AppCompatActivity {

    RelativeLayout ll_relative_facebook,ll_relative_google,ll_relative_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_integration);

        find_All_ID();
        event();
    }
    private void find_All_ID() {
        ll_relative_email=findViewById(R.id.ll_relative_email);
    }

    private void event() {
        ll_relative_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocialIntegrationActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
