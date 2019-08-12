package com.nikvay.saipooja_patient.view.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;


import com.google.gson.Gson;
import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.apiCallCommen.ApiClient;
import com.nikvay.saipooja_patient.apiCallCommen.ApiInterface;
import com.nikvay.saipooja_patient.model.PatientModel;
import com.nikvay.saipooja_patient.model.PreScriptionModel;
import com.nikvay.saipooja_patient.model.SuccessModel;
import com.nikvay.saipooja_patient.utils.ErrorMessageDialog;
import com.nikvay.saipooja_patient.utils.NetworkUtils;
import com.nikvay.saipooja_patient.utils.SharedUtils;
import com.nikvay.saipooja_patient.utils.SuccessMessageDialog;
import com.nikvay.saipooja_patient.view.adapter.PreScriptionAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreSriptionActivity extends AppCompatActivity {

    RecyclerView recyclerView_preScrption;
    ImageView iv_tool_calender;
    CalendarView calendarView;
    ApiInterface apiInterface;

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    ProgressDialog pd;

    private EditText edt_search_service;


    private ArrayList<PreScriptionModel> preScriptionModelArrsyList= new ArrayList<>();
    ArrayList<PatientModel> patientModelArrayList = new ArrayList<>();
    String patient_id,date,TAG =getClass().getSimpleName();
    ErrorMessageDialog errorMessageDialog;
    SuccessMessageDialog successMessageDialog;
    PreScriptionAdapter preScriptionAdapter;
    ImageView iv_empty_list,iv_back;


    /* by Date Wise */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_sription);

        find_All_IDs();
        events();
    }

    private void events()
    {

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edt_search_service.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                preScriptionAdapter.getFilter().filter(edt_search_service.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }




    private void preScriptionListCall(String patient_id,String date)
    {
        pd = new ProgressDialog(PreSriptionActivity.this);
        pd.setMessage("Loading Please Wait...");
        pd.setCancelable(false);
        pd.show();
        Call<SuccessModel>call = apiInterface.preScriptionDetail(patient_id,date);

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response)
            {
                pd.dismiss();

                String str_response = new Gson().toJson(response.body());

                Log.e(""+TAG,"Response <<<<<<<<<<"+str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel prescriptionModel = response.body();

                        String errorCode = null, msg = null;
                        if (prescriptionModel != null) {
                            errorCode = prescriptionModel.getError_code();
                            msg = prescriptionModel.getMsg();
                            if (errorCode.equalsIgnoreCase("1")) {
                                preScriptionModelArrsyList = prescriptionModel.getPreSriptionDetailModelArrayList();

                                if (preScriptionModelArrsyList.size() != 0) {

                                    Collections.reverse(preScriptionModelArrsyList);
                                    preScriptionAdapter = new PreScriptionAdapter(PreSriptionActivity.this, preScriptionModelArrsyList);
                                    recyclerView_preScrption.setAdapter(preScriptionAdapter);
                                    preScriptionAdapter.notifyDataSetChanged();

                                    recyclerView_preScrption.setVisibility(View.VISIBLE);
                                    iv_empty_list.setVisibility(View.GONE);
                                } else {
                                    recyclerView_preScrption.setVisibility(View.GONE);
                                    iv_empty_list.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    } else {
                        errorMessageDialog.showDialog("Response Unavailable");
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

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);

    }


    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int year, int month, int dayOfMonth) {


                    CharSequence strDate = null;
                    Time chosenDate = new Time();
                    chosenDate.set(dayOfMonth, month, year);

                    long dateAppointment = chosenDate.toMillis(true);
                    strDate = DateFormat.format("yyyy-MM-dd", dateAppointment);
                    date= (String) strDate;

                    if (NetworkUtils.isNetworkAvailable(PreSriptionActivity.this))
                        preScriptionListCall(patient_id,date);
                    else
                        NetworkUtils.isNetworkNotAvailable(PreSriptionActivity.this);

                }
            };



    private void find_All_IDs()
    {
        recyclerView_preScrption = findViewById(R.id.recyclerView_preScrption);
        iv_tool_calender = findViewById(R.id.iv_tool_calender);
        calendarView = findViewById(R.id.calendarView);
        iv_empty_list = findViewById(R.id.iv_empty_list);
        edt_search_service = findViewById(R.id.edt_search_service);
        iv_back = findViewById(R.id.iv_back);

        patientModelArrayList = SharedUtils.getUserDetails(PreSriptionActivity.this);
        patient_id = patientModelArrayList.get(0).getPatient_id();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        errorMessageDialog= new ErrorMessageDialog(PreSriptionActivity.this);
        successMessageDialog= new SuccessMessageDialog(PreSriptionActivity.this);


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(PreSriptionActivity.this);
        recyclerView_preScrption.setLayoutManager(linearLayoutManager);
        recyclerView_preScrption.setHasFixedSize(true);



        if (NetworkUtils.isNetworkAvailable(PreSriptionActivity.this))
        { date = "";
            preScriptionListCall(patient_id,date);
        }
        else
            NetworkUtils.isNetworkNotAvailable(PreSriptionActivity.this);


    }


}
