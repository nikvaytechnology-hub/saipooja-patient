package com.nikvay.saipooja_patient.view.activity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.apiCallCommen.ApiClient;
import com.nikvay.saipooja_patient.apiCallCommen.ApiInterface;
import com.nikvay.saipooja_patient.apiCallCommen.BaseApi;
import com.nikvay.saipooja_patient.model.GalleryModule;
import com.nikvay.saipooja_patient.model.SuccessModel;
import com.nikvay.saipooja_patient.utils.NetworkUtils;
import com.nikvay.saipooja_patient.view.adapter.GalleryAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoGallaryActivity extends AppCompatActivity {

    String TAG = getClass().getSimpleName();
    ApiInterface apiInterface;
    ProgressDialog pd;

    ArrayList<GalleryModule> galleryModuleArrayList = new ArrayList<>();
    RecyclerView recycler_view_gallery_image;
    GalleryAdapter galleryAdapter;
    ImageView iv_empty_list;
    String url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallary);
        find_All_IDs();
        
        
    }

    private void find_All_IDs() {
        recycler_view_gallery_image = findViewById(R.id.recycler_view_gallery_image);
        iv_empty_list = findViewById(R.id.iv_empty_list);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(PhotoGallaryActivity.this, 2, GridLayoutManager.VERTICAL, false);
        recycler_view_gallery_image.setLayoutManager(gridLayoutManager);
        recycler_view_gallery_image.setHasFixedSize(true);

        event();

        if (NetworkUtils.isNetworkAvailable(PhotoGallaryActivity.this)) {
            doGallery();
        } else {
            NetworkUtils.isNetworkNotAvailable(PhotoGallaryActivity.this);
        }

    }

    private void event() {
    }
    private void doGallery() {
        pd = new ProgressDialog(PhotoGallaryActivity.this);
        pd.setMessage("Loading Please Wait...");
        pd.setCancelable(false);
        pd.show();
        Call<SuccessModel> call = apiInterface.galleryListCall();

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                pd.dismiss();
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel loginModule = response.body();

                        String message = null, errorCode = null;
                        if (loginModule != null) {
                            message = loginModule.getMsg();
                            errorCode = loginModule.getError_code();
                            url = loginModule.getImg_base_url();

                            if (errorCode.equalsIgnoreCase("1")) {

                                galleryModuleArrayList = loginModule.getGalleryModuleArrayList();
                                Collections.reverse(galleryModuleArrayList);

                                galleryAdapter = new GalleryAdapter(PhotoGallaryActivity.this, url, galleryModuleArrayList);
                                recycler_view_gallery_image.setAdapter(galleryAdapter);
                                galleryAdapter.notifyDataSetChanged();

                                if (galleryModuleArrayList.size() != 0) {
                                    recycler_view_gallery_image.setVisibility(View.VISIBLE);
                                    iv_empty_list.setVisibility(View.GONE);
                                } else {
                                    recycler_view_gallery_image.setVisibility(View.GONE);
                                    iv_empty_list.setVisibility(View.VISIBLE);
                                }

//                                Toasty.success(mContext, "Gallery List Successfully !!", Toast.LENGTH_SHORT,true).show();

                                //========= adapter onClick ===============
                                adapterOnClick();
                            }
                        }
                    } else {
                        //Toasty.warning(PhotoGallaryActivity.this, "Service Unavailable !!", Toast.LENGTH_SHORT, true).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

           

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                pd.dismiss();
              
            }
        });
    }

    private void adapterOnClick() {
        galleryAdapter.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onAdapterClick(GalleryModule galleryModule, int position) {

                String imageName = galleryModule.getImage_name();
                String imageUrl = url + imageName;
                final String finalImageUrl = BaseApi.BASE_URL + imageUrl;


                final String id = galleryModule.getId();

                String fileTitleName = galleryModule.getTitle();


                AlertDialog.Builder builder = new AlertDialog.Builder(PhotoGallaryActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_dialog_gallery, null);
                builder.setView(dialogView);
                builder.setCancelable(true);

                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();

                final ImageView iv_gallery_image_dialog = dialogView.findViewById(R.id.iv_gallery_image_dialog);
                final TextView tv_file_name = dialogView.findViewById(R.id.tv_file_name);
                final TextView txt_date = dialogView.findViewById(R.id.textDate);
                final ImageView iv_gallery_image_download = dialogView.findViewById(R.id.iv_gallery_image_download);
                final ImageView iv_gallery_image_delete = dialogView.findViewById(R.id.iv_gallery_image_delete);

                Picasso.get()
                        .load(finalImageUrl)
                        .into(iv_gallery_image_dialog);

                tv_file_name.setText(fileTitleName);
/*
                if (isSelectUser.equalsIgnoreCase("1")) {
                    iv_gallery_image_delete.setVisibility(View.VISIBLE);
                    iv_gallery_image_download.setVisibility(View.GONE);
                }*/

                // two way the name of image
                @SuppressLint("DefaultLocale") final String fileName = String.format("%d", System.currentTimeMillis());
//                final String fileName = DateFormat.getDateTimeInstance().format(new Date());

                iv_gallery_image_download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      /*  iv_gallery_image_dialog.buildDrawingCache();

                        Bitmap bitmap = iv_gallery_image_dialog.getDrawingCache();
                        File storageLoc = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS); //context.getExternalFilesDir(null);

                        File file = new File(storageLoc, fileName + ".jpg");

                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                            fos.close();

                            scanFile(mContext, Uri.fromFile(file));
                            Toast.makeText(mContext, "Image Saved Successfully", Toast.LENGTH_LONG).show();

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                        DownloadManager downloadManager = (DownloadManager) PhotoGallaryActivity.this.getSystemService(Context.DOWNLOAD_SERVICE);
                        Uri uri = Uri.parse(finalImageUrl);
                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                      //  request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, currentDateTime + ".png");
                        Toast.makeText(PhotoGallaryActivity.this, "Start downloading...", Toast.LENGTH_SHORT).show();
                        Long reference = downloadManager.enqueue(request);
                        alertDialog.dismiss();
                    }
                });

                iv_gallery_image_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //======= Api Call ====
                        if (NetworkUtils.isNetworkAvailable(PhotoGallaryActivity.this)) {
                           // deletePhoto(id);
                        } else {
                            NetworkUtils.isNetworkNotAvailable(PhotoGallaryActivity.this);
                        }
                        alertDialog.dismiss();
                    }
                });
            }
        });

    }
}
