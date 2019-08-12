package com.nikvay.saipooja_patient.view.activity;

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
import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.apiCallCommen.ApiClient;
import com.nikvay.saipooja_patient.apiCallCommen.ApiInterface;
import com.nikvay.saipooja_patient.model.DoctorModel;
import com.nikvay.saipooja_patient.model.ServiceModel;
import com.nikvay.saipooja_patient.model.SuccessModel;
import com.nikvay.saipooja_patient.utils.ErrorMessageDialog;
import com.nikvay.saipooja_patient.utils.NetworkUtils;
import com.nikvay.saipooja_patient.utils.StaticContent;
import com.nikvay.saipooja_patient.view.adapter.DoctorListAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorListActivity extends AppCompatActivity {


    String service_id,TAG = getClass().getSimpleName(),mTitle= "Doctor List";
    ServiceModel serviceModel;
    ArrayList<DoctorModel>doctorModelArrayList ;
    private ApiInterface apiInterface;
    DoctorListAdapter doctorListAdapter;
    RecyclerView recyclerViewDoctorList;
    TextView textTitleDoctorName;
    EditText edt_search_doctor;
    ImageView iv_close;
        ErrorMessageDialog errorMessageDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        find_All_Id();
        event();


    }

    private void event() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edt_search_doctor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                doctorListAdapter.getFilter().filter(edt_search_doctor.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void find_All_Id() {

        recyclerViewDoctorList=findViewById(R.id.recyclerViewDoctorList);
        edt_search_doctor=findViewById(R.id.edt_search_doctor);
        iv_close=findViewById(R.id.iv_close);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        errorMessageDialog= new ErrorMessageDialog(DoctorListActivity.this);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            serviceModel= (ServiceModel) bundle.getSerializable(StaticContent.IntentKey.DOCTOR_DETAIL);
            service_id=serviceModel.getService_id();
            mTitle = bundle.getString(StaticContent.IntentKey.ACTIVITY_TYPE);
        }
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(DoctorListActivity.this);
        recyclerViewDoctorList.setLayoutManager(linearLayoutManager);
        recyclerViewDoctorList.setHasFixedSize(true);


        if (NetworkUtils.isNetworkAvailable(DoctorListActivity.this))
            calldoctorsSrviceList(service_id);
        else
            NetworkUtils.isNetworkNotAvailable(DoctorListActivity.this);
    }



    private void calldoctorsSrviceList(String service_id) {
        Call<SuccessModel> call = apiInterface.doctorsSrviceList(service_id);

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {

                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();
                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();
                            if (code.equalsIgnoreCase("1")) {

                                doctorModelArrayList = successModel.getDoctorModelArrayList();
                                if(doctorModelArrayList.size()!=0) {
                                    doctorListAdapter = new DoctorListAdapter(DoctorListActivity.this, doctorModelArrayList, serviceModel);
                                    recyclerViewDoctorList.setAdapter(doctorListAdapter);
                                    doctorListAdapter.notifyDataSetChanged();
                                    recyclerViewDoctorList.addItemDecoration(new DividerItemDecoration(DoctorListActivity.this, DividerItemDecoration.VERTICAL));

                                }
                                else
                                {

                                }

                            } else {
                                errorMessageDialog.showDialog("List Not Found");




                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
            }
        });
    }

}
