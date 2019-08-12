package com.nikvay.saipooja_patient.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.nikvay.saipooja_patient.MainActivity;
import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.apiCallCommen.ApiClient;
import com.nikvay.saipooja_patient.apiCallCommen.ApiInterface;
import com.nikvay.saipooja_patient.model.ServiceModel;
import com.nikvay.saipooja_patient.model.SuccessModel;
import com.nikvay.saipooja_patient.utils.ErrorMessageDialog;
import com.nikvay.saipooja_patient.utils.NetworkUtils;
import com.nikvay.saipooja_patient.utils.ShowProgress;
import com.nikvay.saipooja_patient.utils.StaticContent;
import com.nikvay.saipooja_patient.view.adapter.ServiceListAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceListActivity extends AppCompatActivity {


    private RecyclerView recyclerViewServiceList;
    ArrayList<ServiceModel> serviceModelArrayList=new ArrayList<>();
    private ServiceListAdapter serviceListAdapter;
    private ImageView  iv_close,iv_no_data_found;
    private ApiInterface apiInterface;
    private ErrorMessageDialog errorMessageDialog;
    private String device_token,TAG = getClass().getSimpleName(),doctor_id,appointmentName="Service List";
    private TextView textTitleServiceName;
    private EditText edt_search_service;
    ShowProgress showProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);


        find_All_IDs();
        events();

        if (NetworkUtils.isNetworkAvailable(ServiceListActivity.this))
            callServiceList();
        else
            NetworkUtils.isNetworkNotAvailable(ServiceListActivity.this);

    }



    private void events() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        edt_search_service.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                serviceListAdapter.getFilter().filter(edt_search_service.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void find_All_IDs() {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        recyclerViewServiceList=findViewById(R.id.recyclerViewServiceList);
        iv_close=findViewById(R.id.iv_close);
        textTitleServiceName=findViewById(R.id.textTitleServiceName);
        iv_no_data_found=findViewById(R.id.iv_no_data_found);
        edt_search_service=findViewById(R.id.edt_search_service);



        errorMessageDialog= new ErrorMessageDialog(ServiceListActivity.this);
        showProgress=new ShowProgress(ServiceListActivity.this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            appointmentName = bundle.getString(StaticContent.IntentKey.APPOINTMENT);
            textTitleServiceName.setText("Select Service");
        }


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ServiceListActivity.this);
        recyclerViewServiceList.setLayoutManager(linearLayoutManager);
        recyclerViewServiceList.setHasFixedSize(true);



    }
    private void callServiceList() {
        showProgress.showDialog();

        Call<SuccessModel> call = apiInterface.serviceList();
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                showProgress.dismissDialog();
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);


                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();
                        serviceModelArrayList.clear();
                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();


                            if (code.equalsIgnoreCase("1")) {

                                serviceModelArrayList=successModel.getServiceModelArrayList();

                                if(serviceModelArrayList.size()!=0) {

                                    serviceListAdapter = new ServiceListAdapter(ServiceListActivity.this, serviceModelArrayList,appointmentName);
                                    recyclerViewServiceList.setAdapter(serviceListAdapter);
                                    serviceListAdapter.notifyDataSetChanged();
                                    recyclerViewServiceList.addItemDecoration(new DividerItemDecoration(ServiceListActivity.this, DividerItemDecoration.VERTICAL));

                                }
                                else
                                {
                                    iv_no_data_found.setVisibility(View.VISIBLE);
                                }

                            } else {
                                errorMessageDialog.showDialog("Response Not Working");
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                showProgress.dismissDialog();
                errorMessageDialog.showDialog(t.getMessage());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //callServiceList();
    }
}
