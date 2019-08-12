package com.nikvay.saipooja_patient.view.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.apiCallCommen.ApiClient;
import com.nikvay.saipooja_patient.apiCallCommen.ApiInterface;
import com.nikvay.saipooja_patient.interfaceutils.SelectPaymentInterface;
import com.nikvay.saipooja_patient.model.PatientModel;
import com.nikvay.saipooja_patient.model.PaymentDetailModel;
import com.nikvay.saipooja_patient.model.SuccessModel;
import com.nikvay.saipooja_patient.utils.ErrorMessageDialog;
import com.nikvay.saipooja_patient.utils.NetworkUtils;
import com.nikvay.saipooja_patient.utils.SharedUtils;
import com.nikvay.saipooja_patient.utils.ShowProgress;
import com.nikvay.saipooja_patient.utils.SuccessMessageDialog;
import com.nikvay.saipooja_patient.view.adapter.MyPaymentDialogAdapter;
import com.nikvay.saipooja_patient.view.adapter.PaymentAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    View view;
    RecyclerView recyclerView_payment;
    Context mContext;
    private ApiInterface apiInterface;
    String patient_id,TAG = getClass().getSimpleName();
    ErrorMessageDialog errorMessageDialog;
    SuccessMessageDialog successMessageDialog;
    ArrayList<PaymentDetailModel>paymentDetailModelArrayList = new ArrayList<>();
    ArrayList<PatientModel>patientModelArrayList = new ArrayList<>();

    PaymentAdapter paymentAdapter;
    ImageView iv_empty_list,iv_calender,iv_close;
    ProgressDialog pd;
    private EditText edt_search_service;


    private Dialog selectPatientDialog;
    MyPaymentDialogAdapter myPaymentDialogAdapter;
    public static PaymentDetailModel paymentDetailModel = null;
    RecyclerView recyclerDialogSC;
    LinearLayout linearService,linearPatient;
    TextView textServiceName;
    ShowProgress showProgress;
    SelectPaymentInterface selectPaymentInterface;
    AutoCompleteTextView textHospitalPatient;
    Button btnOkDialogSC,btnCancelDialogSC;
    EditText editSearchC;



    /*  date Wise */


    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    String date;
    ImageView iv_tool_calender;
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


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


        edt_search_service.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                paymentAdapter.getFilter().filter(edt_search_service.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void paymentListCall(String patient_id,String date)
    {
        pd = new ProgressDialog(PaymentActivity.this);
        pd.setMessage("Loading Please Wait...");
        pd.setCancelable(false);
        pd.show();
        Call<SuccessModel> call = apiInterface.patientPaymentDetails(patient_id,date);

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response)
            {
                pd.dismiss();

                String str_response = new Gson().toJson(response.body());
                Log.e(""+TAG,"Response>>>>>>>>>>"+str_response);

                try
                {
                    if (response.isSuccessful())
                    {
                        SuccessModel paymentModel = response.body();
                        String errorCode = null,msg= null;

                        if (paymentModel != null)
                        {
                            errorCode =paymentModel.getError_code();
                            msg = paymentModel.getMsg();

                            if (errorCode.equalsIgnoreCase("1"))
                            {
                                paymentDetailModelArrayList = paymentModel.getPaymentDetailModelArrayList();

                                if (paymentDetailModelArrayList.size()!= 0)
                                {

                                    Collections.reverse(paymentDetailModelArrayList);
                                    paymentAdapter = new PaymentAdapter(PaymentActivity.this,paymentDetailModelArrayList);
                                    recyclerView_payment.setAdapter(paymentAdapter);
                                    paymentAdapter.notifyDataSetChanged();

                                    recyclerView_payment.setVisibility(View.VISIBLE);
                                    iv_empty_list.setVisibility(View.GONE);
                                } else {
                                    recyclerView_payment.setVisibility(View.GONE);
                                    iv_empty_list.setVisibility(View.VISIBLE);

                                }
                            }
                        }
                    }
                }catch (Exception e)
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
            return new DatePickerDialog(PaymentActivity.this,
                    myDateListener, year, month, day);
        }
        return null;
    }
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(PaymentActivity.this, "",
                Toast.LENGTH_SHORT)
                .show();
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

                    if (NetworkUtils.isNetworkAvailable(PaymentActivity.this))
                        paymentListCall(patient_id,date);
                    else
                        NetworkUtils.isNetworkNotAvailable(PaymentActivity.this);

                }
            };


    private void find_All_Id() {
        iv_close = findViewById(R.id.iv_close);

/*

        selectPatientDialog = new Dialog(this);
        selectPatientDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectPatientDialog.setContentView(R.layout.dialog_select_patient);
        btnOkDialogSC = selectPatientDialog.findViewById(R.id.btnOkDialogSC);
        btnCancelDialogSC =  selectPatientDialog.findViewById(R.id.btnCancelDialogSC);
        editSearchC =  selectPatientDialog.findViewById(R.id.editSearchC);
        selectPatientDialog.setCancelable(false);
        recyclerDialogSC = selectPatientDialog.findViewById(R.id.recyclerDialogSC);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerDialogSC.setLayoutManager(manager);

*/




        recyclerView_payment = findViewById(R.id.recyclerView_payment);
        edt_search_service = findViewById(R.id.edt_search_service);
        iv_calender = findViewById(R.id.iv_calender);
        calendarView = findViewById(R.id.calendarView);

        errorMessageDialog = new ErrorMessageDialog(PaymentActivity.this);
        successMessageDialog = new SuccessMessageDialog(PaymentActivity.this);

        iv_empty_list = findViewById(R.id.iv_empty_list);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);



        patientModelArrayList = SharedUtils.getUserDetails(PaymentActivity.this);
        patient_id = patientModelArrayList.get(0).getPatient_id();


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(PaymentActivity.this);
        recyclerView_payment.setLayoutManager(linearLayoutManager);
        recyclerView_payment.setHasFixedSize(true);



        if (NetworkUtils.isNetworkAvailable(PaymentActivity.this))
        {
            date ="";
            paymentListCall(patient_id,date);
        }
        else
            NetworkUtils.isNetworkNotAvailable(PaymentActivity.this);
    }
}
