package com.nikvay.saipooja_patient.view.activity;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.apiCallCommen.ApiClient;
import com.nikvay.saipooja_patient.apiCallCommen.ApiInterface;
import com.nikvay.saipooja_patient.model.AppoinmentDateFilterModel;
import com.nikvay.saipooja_patient.model.PatientAptdetailstListModel;
import com.nikvay.saipooja_patient.model.PatientModel;
import com.nikvay.saipooja_patient.model.SuccessModel;
import com.nikvay.saipooja_patient.utils.ErrorMessageDialog;
import com.nikvay.saipooja_patient.utils.NetworkUtils;
import com.nikvay.saipooja_patient.utils.SharedUtils;
import com.nikvay.saipooja_patient.view.adapter.AppoinmentDateFilterAdapter;
import com.nikvay.saipooja_patient.view.adapter.AppointmentListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentListActivity extends AppCompatActivity
{

    private String label,appointmentName,patient_id,TAG = getClass().getSimpleName(),user_id,appointmentType = "";
    private ImageView iv_close,iv_no_data_found;
    private TextView textAppointmentTitleName;
    private RecyclerView recyclerViewAppointmentList;
    private ErrorMessageDialog errorMessageDialog;
    private ArrayList<PatientAptdetailstListModel> appoinmentListModelArrayList = new ArrayList<>();
    ArrayList<String> filter_name = new ArrayList<>();
    ArrayList<String> filter_name_duplicate_name;
    private AppointmentListAdapter appointmentListAdapter;
    private AppoinmentDateFilterAdapter appoinmentDateFilterAdapter;
    private ApiInterface apiInterface;
    ArrayList<PatientModel> patientModelArrayList=new ArrayList<>();
    private Spinner edt_search_service;


    /* ===========================================*/
    /* this is Filter For the Date Wise */

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    String date;
    ImageView iv_tool_calender,iv_empty_list;
    CalendarView calendarView;
    ProgressDialog pd;
    private ArrayList<AppoinmentDateFilterModel> appoinmentDateFilterModelArrayList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);

        find_All_IDs();
        events();


        if (NetworkUtils.isNetworkAvailable(AppointmentListActivity.this))
        {
              appoinmentListCall();
        }
        else
            NetworkUtils.isNetworkNotAvailable(AppointmentListActivity.this);

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

                    if (NetworkUtils.isNetworkAvailable(AppointmentListActivity.this))
                        AppoinmentDateListCall(patient_id,date);
                    else
                        NetworkUtils.isNetworkNotAvailable(AppointmentListActivity.this);

                }
            };

    private void AppoinmentDateListCall(String patient_id, String date)
    {
        Call<SuccessModel> call = apiInterface.patientAptDateFilterDetails(patient_id,date);

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {

                String str_response = new Gson().toJson(response.body());
                Log.e(""+TAG,"Response<<<<<<<<<<<< "+ str_response);

                try {
                    if (response.isSuccessful()) {

                            SuccessModel successModel = response.body();

                            String message = null, code = null;

                            if (successModel != null) {

                                message = successModel.getMsg();
                                code = successModel.getError_code();


                                if (code.equalsIgnoreCase("1")) {

                                    appoinmentDateFilterModelArrayList = successModel.getAppoinmentDateFilterModelArrayList();

                                    if (appoinmentDateFilterModelArrayList.size() != 0) {

                                        Collections.reverse(appoinmentDateFilterModelArrayList);
                                        appoinmentDateFilterAdapter = new AppoinmentDateFilterAdapter(AppointmentListActivity.this, appoinmentDateFilterModelArrayList);
                                        recyclerViewAppointmentList.setAdapter(appoinmentDateFilterAdapter);
                                        appoinmentDateFilterAdapter.notifyDataSetChanged();

                                        recyclerViewAppointmentList.setVisibility(View.VISIBLE);
                                        iv_empty_list.setVisibility(View.GONE);

                                    } else {
                                        recyclerViewAppointmentList.setVisibility(View.GONE);
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
                errorMessageDialog.showDialog("Response Not fond");

            }
        });

    }


    private void events() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        edt_search_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                appointmentListAdapter = new AppointmentListAdapter(AppointmentListActivity.this, appoinmentListModelArrayList);
                recyclerViewAppointmentList.setAdapter(appointmentListAdapter);
                appointmentListAdapter.notifyDataSetChanged();

                String selected_name = edt_search_service.getSelectedItem().toString();
                appointmentListAdapter.getFilter().filter(selected_name);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



/*
   Servise wise Search
        edt_search_service.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                appointmentListAdapter.getFilter().filter(edt_search_service.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

    }

    private void find_All_IDs() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        iv_close = findViewById(R.id.iv_close);
        textAppointmentTitleName = findViewById(R.id.textAppointmentTitleName);
        edt_search_service = findViewById(R.id.edt_search_service);
        iv_empty_list = findViewById(R.id.iv_empty_list);
        recyclerViewAppointmentList = findViewById(R.id.recyclerViewAppointmentList);

        iv_tool_calender = findViewById(R.id.iv_tool_calender);
        calendarView = findViewById(R.id.calendarView);

        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        errorMessageDialog = new ErrorMessageDialog(AppointmentListActivity.this);

        patientModelArrayList = SharedUtils.getUserDetails(AppointmentListActivity.this);
        patient_id = patientModelArrayList.get(0).getPatient_id();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(AppointmentListActivity.this);
        recyclerViewAppointmentList.setLayoutManager(linearLayoutManager);
        recyclerViewAppointmentList.setHasFixedSize(true);


    }

    private void appoinmentListCall() {
        pd = new ProgressDialog(AppointmentListActivity.this);
        pd.setMessage("Loading Please Wait...");
        pd.setCancelable(false);
        pd.show();

        Call<SuccessModel> call = apiInterface.patientAptDetails(patient_id);
        call.enqueue(new Callback<SuccessModel>() {@Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response)
        {
            pd.dismiss();
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);


                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();
                        appoinmentListModelArrayList.clear();
                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();


                            if (code.equalsIgnoreCase("1")) {

                                appoinmentListModelArrayList=successModel.getPatientAptdetailstListModelArrayList();



                                    Collections.reverse(appoinmentListModelArrayList);

                                filter_name.add("All");
                                for (int i=0;i<appoinmentListModelArrayList.size();i++)
                                {
                                    filter_name.add(appoinmentListModelArrayList.get(i).getName());
                                }

                                HashSet<String> listToSet = new HashSet<String>(filter_name);
                                filter_name_duplicate_name = new ArrayList<>(listToSet);

                                ArrayAdapter aa = new ArrayAdapter(AppointmentListActivity.this, android.R.layout.simple_spinner_item, filter_name_duplicate_name);
                                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                edt_search_service.setAdapter(aa);


                                appointmentListAdapter=new AppointmentListAdapter(AppointmentListActivity.this,appoinmentListModelArrayList);
                                    recyclerViewAppointmentList.setAdapter(appointmentListAdapter);
                                    appointmentListAdapter.notifyDataSetChanged();

                                if(appoinmentListModelArrayList.size()!=0) {

                                    recyclerViewAppointmentList.setVisibility(View.VISIBLE);
                                    iv_empty_list.setVisibility(View.GONE);
                                } else {
                                    recyclerViewAppointmentList.setVisibility(View.GONE);
                                    iv_empty_list.setVisibility(View.VISIBLE);

                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                errorMessageDialog.showDialog(t.getMessage());
            }
        });
   }


}
