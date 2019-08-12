package com.nikvay.saipooja_patient.view.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.apiCallCommen.ApiClient;
import com.nikvay.saipooja_patient.apiCallCommen.ApiInterface;
import com.nikvay.saipooja_patient.model.SuccessModel;
import com.nikvay.saipooja_patient.model.VideoListModule;
import com.nikvay.saipooja_patient.utils.ErrorMessageDialog;
import com.nikvay.saipooja_patient.utils.NetworkUtils;
import com.nikvay.saipooja_patient.utils.SuccessMessageDialog;
import com.nikvay.saipooja_patient.view.adapter.VideoAdapter;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoGallaryActivity extends AppCompatActivity {


    ImageView iv_empty_list;

    ArrayList<VideoListModule> videoListModuleArrayList = new ArrayList<>();
    VideoAdapter videoAdapter;
    RecyclerView recycler_view_video;
    ProgressDialog pd;
    String TAG = getClass().getSimpleName();
    ApiInterface apiInterface;
    private ErrorMessageDialog errorMessageDialog;
    SuccessMessageDialog successMessageDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_gallary);
        find_All_IDs();
    }

    private void find_All_IDs() {
        recycler_view_video = findViewById(R.id.recycler_view_video);
        iv_empty_list = findViewById(R.id.iv_empty_list);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        errorMessageDialog = new ErrorMessageDialog(VideoGallaryActivity.this);
        successMessageDialog = new SuccessMessageDialog(VideoGallaryActivity.this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(VideoGallaryActivity.this);
        recycler_view_video.setLayoutManager(linearLayoutManager);
        recycler_view_video.setHasFixedSize(true);


        //============ Api Call ================
        if (NetworkUtils.isNetworkAvailable(VideoGallaryActivity.this)) {
            doVideoList();
        }else
            NetworkUtils.isNetworkNotAvailable(VideoGallaryActivity.this);

    }

    private void doVideoList()
    {
        pd = new ProgressDialog(VideoGallaryActivity.this);
        pd.setMessage("Loading Please Wait...");
        pd.setCancelable(false);
        pd.show();
        Call<SuccessModel> call = apiInterface.videoTutorialsListCall();
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                pd.dismiss();
                String str_response = new Gson().toJson(response.body());
                Log.e(""+TAG,"Respnse>>>>>>>>>>>>"+str_response);
                try
                {
                    if (response.isSuccessful())
                    {
                        SuccessModel successModel = response.body();

                        String message = null, errorCode = null;
                        if (successModel !=null)
                        {
                            message = successModel.getMsg();
                            errorCode = successModel.getError_code();

                            if (errorCode.equalsIgnoreCase("1")) {

                                videoListModuleArrayList = successModel.getVideoListModuleArrayList();
                                Collections.reverse(videoListModuleArrayList);

                                videoAdapter = new VideoAdapter(VideoGallaryActivity.this, videoListModuleArrayList);
                                recycler_view_video.setAdapter(videoAdapter);
                                videoAdapter.notifyDataSetChanged();

                                if (videoListModuleArrayList.size() != 0) {
                                    recycler_view_video.setVisibility(View.VISIBLE);
                                    iv_empty_list.setVisibility(View.GONE);
                                } else {
                                    recycler_view_video.setVisibility(View.GONE);
                                    iv_empty_list.setVisibility(View.VISIBLE);
                                }

//                                Toasty.success(mContext, "Video List Successfully !!", Toast.LENGTH_SHORT,true).show();

                            }
                        }

                    }
                    else
                    {
                        errorMessageDialog.showDialog( "Service Unavailable !!");
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {

            }
        });
    }

}
