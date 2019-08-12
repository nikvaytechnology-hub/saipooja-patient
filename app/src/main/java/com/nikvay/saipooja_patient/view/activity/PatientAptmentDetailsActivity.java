package com.nikvay.saipooja_patient.view.activity;

import android.app.Dialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.apiCallCommen.ApiClient;
import com.nikvay.saipooja_patient.apiCallCommen.ApiInterface;
import com.nikvay.saipooja_patient.model.DoctorModel;
import com.nikvay.saipooja_patient.model.PatientModel;
import com.nikvay.saipooja_patient.model.ServiceModel;
import com.nikvay.saipooja_patient.model.SuccessModel;
import com.nikvay.saipooja_patient.utils.AppointmentDialog;
import com.nikvay.saipooja_patient.utils.ErrorMessageDialog;
import com.nikvay.saipooja_patient.utils.NetworkUtils;
import com.nikvay.saipooja_patient.utils.SharedUtils;
import com.nikvay.saipooja_patient.utils.StaticContent;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientAptmentDetailsActivity extends AppCompatActivity {

    private ImageView iv_close;
    private TextView textDone,textDateDay,textTime,textPatientName,textEmail,textContact,textServiceName,textDuration,textServiceCost,textcommentName,textLabelName;
    private ServiceModel serviceModel;
    private DoctorModel doctorModel;
    private  String date="",time="",service_id,patient_id,TAG = getClass().getSimpleName(),user_id,doctor_id,comment="ok",label="0",notification_type ="0";
    private ApiInterface apiInterface;
    private RelativeLayout relativeLayoutComments,relativeLayoutCommentsHide,relativeLayoutLabelHide;
    private ErrorMessageDialog errorMessageDialog;
    private ArrayList<DoctorModel> doctorModelArrayList=new ArrayList<>();
    private ArrayList<PatientModel> patientModelArrayList=new ArrayList<>();
    private AppointmentDialog appointmentDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        find_all_id();
        event();
    }
    private void find_all_id()
    {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        iv_close=findViewById(R.id.iv_close);
        textDone=findViewById(R.id.textDone);
        textDateDay=findViewById(R.id.textDateDay);
        textTime=findViewById(R.id.textTime);
        textPatientName=findViewById(R.id.textPatientName);
        textEmail=findViewById(R.id.textEmail);
        textContact=findViewById(R.id.textContact);
        textServiceName=findViewById(R.id.textServiceName);
        textDuration=findViewById(R.id.textDuration);
        textServiceCost=findViewById(R.id.textServiceCost);
        relativeLayoutComments=findViewById(R.id.relativeLayoutComments);
        relativeLayoutCommentsHide=findViewById(R.id.relativeLayoutCommentsHide);
        relativeLayoutLabelHide=findViewById(R.id.relativeLayoutLabelHide);
        textcommentName=findViewById(R.id.textcommentName);
        textLabelName=findViewById(R.id.textLabelName);

        errorMessageDialog= new ErrorMessageDialog(PatientAptmentDetailsActivity.this);
        appointmentDialog= new AppointmentDialog(PatientAptmentDetailsActivity.this);

        patientModelArrayList = SharedUtils.getUserDetails(PatientAptmentDetailsActivity.this);
        patient_id=patientModelArrayList.get(0).getPatient_id();


        Bundle bundle = getIntent().getExtras();


        if (bundle != null) {

            serviceModel= (ServiceModel) bundle.getSerializable(StaticContent.IntentKey.SERVICE_DETAIL);
            doctorModel = (DoctorModel) bundle.getSerializable(StaticContent.IntentKey.DOCTOR_DETAIL);
            date=bundle.getString(StaticContent.IntentKey.DATE);
            time=bundle.getString(StaticContent.IntentKey.TIME);
            service_id=serviceModel.getService_id();
            user_id= doctorModel.getUser_id();
            doctor_id= doctorModel.getDoctor_id();

            textServiceName.setText(serviceModel.getS_name());
            textDuration.setText(serviceModel.getService_time());
            textServiceCost.setText(serviceModel.getService_cost());

            textPatientName.setText(doctorModel.getName());
            textEmail.setText(doctorModel.getEmail());
            textContact.setText(doctorModel.getPhone_no());

            textDateDay.setText(date);
            textTime.setText(time);

            //Toast.makeText(this, serviceModel.getS_name()+""+doctorModel.getPatient_id()+" "+date+" "+time, Toast.LENGTH_SHORT).show();

        }

    }
    private void event()
    {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        textDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.isNetworkAvailable(PatientAptmentDetailsActivity.this))
                    callAddAppoitment();
                else
                    NetworkUtils.isNetworkNotAvailable(PatientAptmentDetailsActivity.this);

            }
        });
        relativeLayoutComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentDialog();
            }
        });

    }

    private void commentDialog()
    {
        final Dialog dialog =new Dialog(PatientAptmentDetailsActivity.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.comment_add_dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final TextInputEditText textComment = dialog.findViewById(R.id.textComment);
        Button btn_done = dialog.findViewById(R.id.btn_done);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = textComment.getText().toString().trim();
                dialog.dismiss();
                relativeLayoutCommentsHide.setVisibility(View.VISIBLE);
                textcommentName.setText(comment);
            }
        });
        dialog.show();
    }

    private void callAddAppoitment()
    {
        Call<SuccessModel> call = apiInterface.addAppointment(doctor_id,user_id,service_id,patient_id,date,time,comment,label,notification_type);

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


                                appointmentDialog.showDialog("your appoinment is submitted Successfully  current status pending ");

                            } else {
                                errorMessageDialog.showDialog("Wrong Appointment");
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
