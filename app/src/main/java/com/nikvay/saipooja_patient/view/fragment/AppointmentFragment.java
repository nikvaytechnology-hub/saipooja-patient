package com.nikvay.saipooja_patient.view.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nikvay.saipooja_patient.Interface.SelectDoctorInterface;
import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.apiCallCommen.ApiClient;
import com.nikvay.saipooja_patient.apiCallCommen.ApiInterface;
import com.nikvay.saipooja_patient.model.DoctorModel;
import com.nikvay.saipooja_patient.model.SelectDateTimeModel;
import com.nikvay.saipooja_patient.model.ServiceModel;
import com.nikvay.saipooja_patient.model.SuccessModel;
import com.nikvay.saipooja_patient.utils.CalenderUtil;
import com.nikvay.saipooja_patient.utils.NetworkUtils;
import com.nikvay.saipooja_patient.view.adapter.CustomSpinnerServiceAdapter;
import com.nikvay.saipooja_patient.view.adapter.SelectDateTimeAdapter;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentFragment extends Fragment  {

    private RecyclerView recyclerViewTime;
    private SelectDateTimeAdapter selectDateTimeAdapter;
    private ArrayList<SelectDateTimeModel> selectDateTimeModelArrayList = new ArrayList<>();
    private ImageView iv_close;
    Spinner spinnerService, spinnerDoctor;
    RadioButton radioButtonNewApt, radioButtonfollowUp;
    EditText timeApt;
    private Context mContext;
    private SelectDoctorInterface selectServiceId;
    ArrayList<String> doctorTypeModelArrayList = new ArrayList<>();
    ArrayList<ServiceModel> serviceModelArrayList = new ArrayList<>();
    ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();

    ArrayList<String> serviceNameArrayList = new ArrayList<>();
    String name,status,service_id;
    private String date;
    RadioGroup radioApt;
    CustomSpinnerServiceAdapter adapter;
    ServiceModel serviceModel;
    Resources res;
    TextView serviceSelect;
    int i;

    ApiInterface apiInterface;
    private String mTitle = "Service Details",  TAG = getClass().getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_appointment, container, false);

        mContext = getActivity();
        find_All_Id(view);


        event();

        return view;
    }

    private void event() {
        timeApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

       /* radioApt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioButtonNewApt:
                        callServiceList();
                        // do operations specific to this selection
                        break;
                    case R.id.radioButtonfollowUp:
                        // do operations specific to this selection
                        break;

                }
            }
        });*/
    }

    private void callServiceList() {
        Call<SuccessModel> call = apiInterface.serviceList();

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {

                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();
                   //     serviceModelArrayList.clear();
                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();
                            if (code.equalsIgnoreCase("1")) {

                               serviceModelArrayList = successModel.getServiceModelArrayList();
                                for (int i = 0; i < serviceModelArrayList.size(); i++) {
                                    serviceNameArrayList.add(serviceModelArrayList.get(i).getS_name() + "\n" + "Cost=" + serviceModelArrayList.get(i).getService_cost()+ "\n" + " Service ID "+serviceModelArrayList.get(i).getService_id());
                                    service_id =serviceModelArrayList.get(i).getService_id();
                                }
                                // Create custom adapter object ( see below CustomAdapter.java )
                                adapter = new CustomSpinnerServiceAdapter(mContext, R.layout.activity_spinner_custom_adapter,
                                        serviceModelArrayList, res);
                                spinnerService.setAdapter(adapter);
                                spinnerService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String textServiceName = ((TextView) view.findViewById(R.id.textServiceName)).getText().toString();
                                        String textServiceDesc = ((TextView) view.findViewById(R.id.textServiceDesc)).getText().toString();
                                        String OutputMsg =textServiceName + "\n" + textServiceDesc;


                                        Toast.makeText(
                                                mContext,OutputMsg, Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });

                            } else {
                                // errorMessageDialog.showDialog("Response Not Working");
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
                        doctorModelArrayList.clear();
                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();
                            if (code.equalsIgnoreCase("1")) {

                                doctorModelArrayList = successModel.getDoctorModelArrayList();
                        //        serviceNameArrayList.clear();

                                for (int i = 0; i < doctorModelArrayList.size(); i++) {

                                    serviceNameArrayList.add(doctorModelArrayList.get(i).getName());
                                }
                                // Set adapter to spinner

                                    ArrayAdapter<String> adapter =
                                            new ArrayAdapter<>(mContext, R.layout.spinner_dropdown_item, serviceNameArrayList);
                                    adapter.setDropDownViewResource( R.layout.spinner_dropdown_item);
                                spinnerDoctor.setAdapter(adapter);
                                spinnerDoctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                            } else {
                                // errorMessageDialog.showDialog("Response Not Working");
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




    private void find_All_Id(View view) {

        spinnerService = view.findViewById(R.id.spinnerService);
        spinnerDoctor = view.findViewById(R.id.spinnerDoctor);
        radioButtonNewApt = view.findViewById(R.id.radioButtonNewApt);
        radioButtonfollowUp = view.findViewById(R.id.radioButtonfollowUp);
        timeApt = view.findViewById(R.id.timeApt);
        radioApt = view.findViewById(R.id.radioGroup);
        serviceSelect = view.findViewById(R.id.serviceSelect);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        recyclerViewTime = view.findViewById(R.id.recyclerViewTime);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        recyclerViewTime.setLayoutManager(gridLayoutManager);

        callServiceList();

        // Resources passed to adapter to get image
        res = getResources();

        for (int i = 0; i <= 5; i++) {

            selectDateTimeModelArrayList.add(new SelectDateTimeModel("10 AM"));
        }
    //    selectDateTimeAdapter = new SelectDateTimeAdapter(mContext, selectDateTimeModelArrayList, serviceModel, doctorModel, date);
        recyclerViewTime.setAdapter(selectDateTimeAdapter);
        recyclerViewTime.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
        recyclerViewTime.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recyclerViewTime.setHasFixedSize(true);
    }


    private void showDatePickerDialog() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);

        DatePickerDialog datePickerDialog =
                new DatePickerDialog(mContext, new CalenderSelectDateListener(),
                        year,
                        month,
                        date);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }



    class CalenderSelectDateListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            timeApt.setText(CalenderUtil.convertDate11(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year));
            CharSequence strDate = null;
            Time chosenDate = new Time();
            chosenDate.set(dayOfMonth, monthOfYear, year);

            long dateAppointment = chosenDate.toMillis(true);
            strDate = DateFormat.format("yyyy-MM-dd", dateAppointment);
            date = (String) strDate;

            if (NetworkUtils.isNetworkAvailable(mContext))
            {

            }//   callTimeSlot();
            else
                NetworkUtils.isNetworkNotAvailable(mContext);
        }
    }


}
