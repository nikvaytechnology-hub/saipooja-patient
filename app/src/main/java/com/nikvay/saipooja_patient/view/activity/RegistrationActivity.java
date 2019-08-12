package com.nikvay.saipooja_patient.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.apiCallCommen.ApiClient;
import com.nikvay.saipooja_patient.apiCallCommen.ApiInterface;
import com.nikvay.saipooja_patient.model.PatientModel;
import com.nikvay.saipooja_patient.model.SuccessModel;
import com.nikvay.saipooja_patient.utils.ErrorMessageDialog;
import com.nikvay.saipooja_patient.utils.NetworkUtils;
import com.nikvay.saipooja_patient.utils.SharedUtils;
import com.nikvay.saipooja_patient.utils.SuccessMessageDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    EditText textPassword,textEmail,textPhone,textName,textaddress,textAage;
    Button btn_login;
    TextView txtCreateAc;

    Spinner spinnerGenderType;

    String TAG = getClass().getSimpleName();
    String gender;

    ApiInterface apiInterface;
    ArrayList<PatientModel> registrationModelArrayList = new ArrayList<>();

    private ErrorMessageDialog errorMessageDialog;
    private SuccessMessageDialog successMessageDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        find_ALL_ID();
        event();
    }

    private void find_ALL_ID()
    {
        textName = findViewById(R.id.textName);
        textPassword = findViewById(R.id.textpassword);
        textEmail = findViewById(R.id.textemail);
        btn_login = findViewById(R.id.btn_login);
        txtCreateAc = findViewById(R.id.txtCreateAc);
        textPhone = findViewById(R.id.textphone);
        textaddress = findViewById(R.id.textaddress);
        textAage = findViewById(R.id.textAage);
        spinnerGenderType = findViewById(R.id.spinnerGenderType);

      //  apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        errorMessageDialog= new ErrorMessageDialog(RegistrationActivity.this);
        successMessageDialog = new SuccessMessageDialog(RegistrationActivity.this);

    }
    private void event()
    {

        spinnerGenderType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = spinnerGenderType.getSelectedItem().toString();
                Log.e(""+TAG,"Response >>>>>>>"+ gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = textEmail.getText().toString().trim();
                String password = textPassword.getText().toString().trim();
                String Phone = textPhone.getText().toString().trim();
                String Name = textName.getText().toString().trim();
                String address = textaddress.getText().toString().trim();
                String age = textAage.getText().toString().trim();

                if(Email.equalsIgnoreCase(""))
                {
                    errorMessageDialog.showDialog("Email ID Can't Be Empty");
                    // SnackbarCommon.displayValidation(v,"Email ID Can't Be Empty");
                }
                else if(Name.equalsIgnoreCase(""))
                {
                    errorMessageDialog.showDialog("Name Can't Be Empty");
                    // SnackbarCommon.displayValidation(v,"Email ID Can't Be Empty");
                }
                else if(Phone.equalsIgnoreCase("")||Phone.length()>10)
                {
                    errorMessageDialog.showDialog("Phone Can't Be Empty");
                    // SnackbarCommon.displayValidation(v,"Email ID Can't Be Empty");
                }
                else if(password.equalsIgnoreCase(""))
                {
                    errorMessageDialog.showDialog("Password Can't Be Empty");
                    //SnackbarCommon.displayValidation(v,"Password Can't Be Empty");
                }
                else if(address.equalsIgnoreCase(""))
                {
                    errorMessageDialog.showDialog("address Can't Be Empty");
                    // SnackbarCommon.displayValidation(v,"Email ID Can't Be Empty");
                }
                else if(password.length()>15||password.length()<5)
                {
                    errorMessageDialog.showDialog("Password Length Between 5 to 15");
                    //SnackbarCommon.displayValidation(v,"Password Length Between 5 to 15");
                }
                else if(gender.equalsIgnoreCase(""))
                {
                    errorMessageDialog.showDialog("Gender Can't Be Empty");
                    // SnackbarCommon.displayValidation(v,"Email ID Can't Be Empty");
                }else if(age.equalsIgnoreCase(""))
                {
                    errorMessageDialog.showDialog("Age Can't Be Empty");
                    // SnackbarCommon.displayValidation(v,"Email ID Can't Be Empty");
                }
                else
                {

                    if (NetworkUtils.isNetworkAvailable(RegistrationActivity.this))
                        callLogin(Name,Email,Phone,password,address,gender,age);
                    else
                        NetworkUtils.isNetworkNotAvailable(RegistrationActivity.this);
                }

            }
        });
    }

    private void callLogin(String name, String email, String phone, String password, String address, String gender, String age)
    {
        Call call = apiInterface.newPatientRegistration(name,email,address,phone,password,gender,age);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response)
            {
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" +   str_response);

                try {
                    if (response.isSuccessful()) {

                        SuccessModel registrationModel = (SuccessModel) response.body();
                        String message = null, errorCode = null;

                        if (registrationModel != null) {
                            message = registrationModel.getMsg();
                            errorCode = registrationModel.getError_code();

                            if (errorCode.equalsIgnoreCase("1")) {
                                registrationModelArrayList = registrationModel.getRagistrationdetailModelArrayList();

                                successMessageDialog.showDialog("Patient Add succesfully ");

                                SharedUtils.putSharedUtils(RegistrationActivity.this);
                                SharedUtils.addUserUtils(RegistrationActivity.this,registrationModelArrayList);
                            } else if (errorCode.equalsIgnoreCase("2")) {
                                successMessageDialog.showDialog("Patient is Already Sucessfully ");
                            }
                            else {
                                errorMessageDialog.showDialog("Parameter is missing");
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }
}
