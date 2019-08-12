package com.nikvay.saipooja_patient.view.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.nikvay.saipooja_patient.R;

public class SaipoojaWebActivity extends AppCompatActivity {

    WebView saipoojaWebview;
    ProgressDialog progressDialog;
    private boolean doubleBackToExitPressedOnce = false;
    ImageView iv_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saipooja_web);

        find_All_Id();
        event();
    }

    private void event()
    {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void find_All_Id() {
        progressDialog = new ProgressDialog(this);
        saipoojaWebview = (WebView) findViewById(R.id.saipoojaWebview);
        iv_close =  findViewById(R.id.iv_close);

        saipoojaWebview.loadUrl("http://www.healyourhealth.in/");

        saipoojaWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressDialog.setMessage("Please wait page is loading..");
                progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
            }

        });
         saipoojaWebview.getSettings().setJavaScriptEnabled(true);
         saipoojaWebview.getSettings().setDisplayZoomControls(true);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
            Toast.makeText(getApplicationContext(), "back press",
                    Toast.LENGTH_LONG).show();

        return true;
        // Disable back button..............
    }


}
