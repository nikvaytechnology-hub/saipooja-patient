package com.nikvay.saipooja_patient.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nikvay.saipooja_patient.MainActivity;
import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.apiCallCommen.ApiClient;
import com.nikvay.saipooja_patient.apiCallCommen.ApiInterface;
import com.nikvay.saipooja_patient.model.PatientModel;
import com.nikvay.saipooja_patient.model.SuccessModel;
import com.nikvay.saipooja_patient.utils.AppointmentDialog;
import com.nikvay.saipooja_patient.utils.ErrorMessageDialog;
import com.nikvay.saipooja_patient.utils.NetworkUtils;
import com.nikvay.saipooja_patient.utils.SharedUtils;
import com.nikvay.saipooja_patient.utils.SuccessMessageDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText textPassword,textEmail;
    Button btn_login;
    TextView txtCreateAc;
    private ErrorMessageDialog errorMessageDialog;
    private SuccessMessageDialog successMessageDialog;
    AppointmentDialog appointmentDialog;

    String TAG = getClass().getSimpleName(),device_token;
    String is_login;

    ApiInterface apiInterface;
    RelativeLayout ll_relative_login;

    ArrayList<PatientModel> patientModelArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        find_ALL_ID();
        event();
    }

    private void find_ALL_ID()
    {
        textPassword = findViewById(R.id.password);
        textEmail = findViewById(R.id.email);
        btn_login = findViewById(R.id.btn_login);
        txtCreateAc = findViewById(R.id.txtCreateAc);
        ll_relative_login = findViewById(R.id.ll_relative_login);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        device_token = SharedUtils.getDeviceToken(LoginActivity.this);


        errorMessageDialog = new ErrorMessageDialog(LoginActivity.this);
        successMessageDialog = new SuccessMessageDialog(LoginActivity.this);
        appointmentDialog = new AppointmentDialog(LoginActivity.this);

    }

    private void event()
    {
        txtCreateAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=textEmail.getText().toString().trim();
                String password=textPassword.getText().toString().trim();
                
                if(email.equalsIgnoreCase(""))
                {
                    errorMessageDialog.showDialog("Email ID Can't Be Empty");
                    // SnackbarCommon.displayValidation(v,"Email ID Can't Be Empty");
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(textEmail.getText().toString()).matches())
                {
                    errorMessageDialog.showDialog("Enter valid Email");
                    // SnackbarCommon.displayValidation(v,"Enter valid Email");
                }
                else if(password.equalsIgnoreCase(""))
                {
                    errorMessageDialog.showDialog("Password Can't Be Empty");
                    //SnackbarCommon.displayValidation(v,"Password Can't Be Empty");
                }
                else if(password.length()>15||password.length()<5)
                {
                    errorMessageDialog.showDialog("Password Length Between 5 to 15");
                    //SnackbarCommon.displayValidation(v,"Password Length Between 5 to 15");
                }
                else
                {

                    if (NetworkUtils.isNetworkAvailable(LoginActivity.this))
                        callLogin(email,password);
                    else
                        NetworkUtils.isNetworkNotAvailable(LoginActivity.this);
                }
            }
        });
    }

    private void callLogin(String email, String password)
    {
        Call<SuccessModel> call = apiInterface.loginCall(email, password,device_token);

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

                                patientModelArrayList = successModel.getPatientModelArrayList();

                                SharedUtils.putSharedUtils(LoginActivity.this);
                                SharedUtils.addUserUtils(LoginActivity.this,patientModelArrayList);

                               loginIntent();
                                appointmentDialog.showDialog("Login Successfully");

                            } else {
                                errorMessageDialog.showDialog("User Not Registered");
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

    private void loginIntent()
    {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);

    }

}
