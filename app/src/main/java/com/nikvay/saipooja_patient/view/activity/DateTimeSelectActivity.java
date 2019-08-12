package com.nikvay.saipooja_patient.view.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.apiCallCommen.ApiClient;
import com.nikvay.saipooja_patient.apiCallCommen.ApiInterface;
import com.nikvay.saipooja_patient.model.DoctorModel;
import com.nikvay.saipooja_patient.model.SelectDateTimeModel;
import com.nikvay.saipooja_patient.model.ServiceModel;
import com.nikvay.saipooja_patient.model.SuccessModel;
import com.nikvay.saipooja_patient.utils.ErrorMessageDialog;
import com.nikvay.saipooja_patient.utils.NetworkUtils;
import com.nikvay.saipooja_patient.utils.StaticContent;
import com.nikvay.saipooja_patient.view.adapter.SelectDateTimeAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DateTimeSelectActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private RecyclerView recyclerViewTime;
    private SelectDateTimeAdapter selectDateTimeAdapter;
    private ArrayList<SelectDateTimeModel> selectDateTimeModelArrayList=new ArrayList<>();
    private ImageView  iv_close,img_empty;
    private CalendarView calendarView;
    private String date,selectedDate;
    private ApiInterface apiInterface;
    private ErrorMessageDialog errorMessageDialog;
    private ServiceModel serviceModel;
    private DoctorModel doctorModel;
    private TextView tv_notdound;
    String dayArray[]={"Morning","Evening","Day"};
    private Spinner dayStatusSpinner;
    private ArrayAdapter arrayAdapter;
    private String dayStatusCode="1", dayStatus;

    private String mTitle="Service Details",service_id ,patient_id,user_id,doctor_id, TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time_select);
        find_All_Ids();
        events();
    }

    private void events() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {


                CharSequence strDate = null;
                Time chosenDate = new Time();
                chosenDate.set(dayOfMonth, month, year);

                long dateAppointment = chosenDate.toMillis(true);
                strDate = DateFormat.format("yyyy-MM-dd", dateAppointment);
                date= (String) strDate;

                if (NetworkUtils.isNetworkAvailable(DateTimeSelectActivity.this))
                    callTimeSlot();
                else
                    NetworkUtils.isNetworkNotAvailable(DateTimeSelectActivity.this);
                // Toast.makeText(DateTimeSelectActivity.this, date, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void find_All_Ids() {
        tv_notdound=findViewById(R.id.textSlotNotFound);
        dayStatusSpinner=findViewById(R.id.spinnerDaystatus);
        arrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,dayArray);
        dayStatusSpinner.setAdapter(arrayAdapter);

        dayStatusSpinner.setOnItemSelectedListener(this);
        recyclerViewTime=findViewById(R.id.recyclerViewTime);
        iv_close=findViewById(R.id.iv_close);
        calendarView=findViewById(R.id.calendarView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(DateTimeSelectActivity.this,3);
        recyclerViewTime.setLayoutManager(gridLayoutManager);
        recyclerViewTime.hasFixedSize();


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            doctorModel= (DoctorModel) bundle.getSerializable(StaticContent.IntentKey.APPOINTMENT);
            serviceModel= (ServiceModel) bundle.getSerializable(StaticContent.IntentKey.SERVICE_DETAIL);
            service_id=serviceModel.getService_id();
            doctor_id=doctorModel.getDoctor_id();
            user_id = doctorModel.getUser_id();


            mTitle = bundle.getString(StaticContent.IntentKey.ACTIVITY_TYPE);
        }

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        calendarView.setMinDate(System.currentTimeMillis() - 1000);
        errorMessageDialog= new ErrorMessageDialog(DateTimeSelectActivity.this);





        if (NetworkUtils.isNetworkAvailable(DateTimeSelectActivity.this))
            callTimeSlot();
        else
            NetworkUtils.isNetworkNotAvailable(DateTimeSelectActivity.this);


    }
    private void callTimeSlot()
    {
        Call<SuccessModel> call = apiInterface.appointmentTimeSlot(dayStatusCode,doctor_id,date,user_id);

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();
                        selectDateTimeModelArrayList.clear();

                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();

                            if (code.equalsIgnoreCase("1"))
                            {

                                selectDateTimeModelArrayList.clear();


                                selectDateTimeModelArrayList=successModel.getSelectDateTimeModelArrayList();
                                if(selectDateTimeModelArrayList.size()!=0)
                                {
                                    selectDateTimeAdapter = new SelectDateTimeAdapter(DateTimeSelectActivity.this, selectDateTimeModelArrayList, serviceModel,doctorModel, date);
                                  recyclerViewTime.setVisibility(View.VISIBLE);
                                    recyclerViewTime.setAdapter(selectDateTimeAdapter);
                                    selectDateTimeAdapter.notifyDataSetChanged();
                                    tv_notdound.setVisibility(View.GONE);
                                }
                                else
                                {
                                    errorMessageDialog.showDialog("Doctor is no Available");
                                }

                            } else {
                               recyclerViewTime.setVisibility(View.GONE);
                               tv_notdound.setVisibility(View.VISIBLE);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        dayStatus=dayArray[position];
        if (dayStatus.equalsIgnoreCase("Morning"))
        {
            dayStatusCode="1";
            callTimeSlot();
        }
        if (dayStatus.equalsIgnoreCase("Evening"))
        {
            dayStatusCode="2";
            callTimeSlot();
        }
        if (dayStatus.equalsIgnoreCase("Day"))
        {
            dayStatusCode="3";
            callTimeSlot();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
