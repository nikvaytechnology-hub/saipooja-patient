package com.nikvay.saipooja_patient.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.nikvay.saipooja_patient.utils.ErrorMessageDialog;
import com.nikvay.saipooja_patient.utils.NetworkUtils;
import com.nikvay.saipooja_patient.utils.SharedUtils;
import com.nikvay.saipooja_patient.utils.SuccessMessageDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChangePasswordFragment extends Fragment {
    View view;
    EditText newPassword,oldPassword;
    TextView btn_login;
    TextView txtCreateAc;
    private ErrorMessageDialog errorMessageDialog;
    private SuccessMessageDialog successMessageDialog;

    String TAG = getClass().getSimpleName(),device_token;
    String is_login,patient_id,email;

    ApiInterface apiInterface;

    ArrayList<PatientModel> patientModelArrayList = new ArrayList<>();
    Context mContext;
    RelativeLayout ll_relative_login;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_password, container, false);
        mContext =getActivity();



        find_All_Ids(view);
        event();

        return view;
    }

    private void find_All_Ids(View view)
    {
        newPassword = view.findViewById(R.id.newPassword);
        oldPassword = view.findViewById(R.id.oldPassword);
        btn_login = view.findViewById(R.id.btn_login);
        txtCreateAc = view.findViewById(R.id.txtCreateAc);
        ll_relative_login = view.findViewById(R.id.ll_relative_login);


        patientModelArrayList = SharedUtils.getUserDetails(mContext);
        patient_id = patientModelArrayList.get(0).getPatient_id();
        email = patientModelArrayList.get(0).getEmail();


        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        device_token = SharedUtils.getDeviceToken(mContext);


        errorMessageDialog = new ErrorMessageDialog(mContext);
        successMessageDialog = new SuccessMessageDialog(mContext);
    }

    private void event()
    {
        ll_relative_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String NewPassword=newPassword.getText().toString().trim();
                String OldPassword=oldPassword.getText().toString().trim();

                if(OldPassword.equalsIgnoreCase(""))
                {
                    errorMessageDialog.showDialog("Old PasswordCan't Be Empty");
                    // SnackbarCommon.displayValidation(v,"Email ID Can't Be Empty");
                }

                else if(NewPassword.equalsIgnoreCase(""))
                {
                    errorMessageDialog.showDialog("Password Can't Be Empty");
                    //SnackbarCommon.displayValidation(v,"Password Can't Be Empty");
                }
                else if(NewPassword.length()>15||NewPassword.length()<5)
                {
                    errorMessageDialog.showDialog("Password Length Between 5 to 15");
                    //SnackbarCommon.displayValidation(v,"Password Length Between 5 to 15");
                }
                else
                {

                    if (NetworkUtils.isNetworkAvailable(mContext))
                        callLoginChangePassword(OldPassword,NewPassword);
                    else
                        NetworkUtils.isNetworkNotAvailable(mContext);
                }
            }
        });
    }

    private void callLoginChangePassword(String oldPassword, String newPassword)
    {
        Call<SuccessModel> call = apiInterface.changePassword(patient_id,email,oldPassword,newPassword);

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

                                successMessageDialog.showDialog("Change Password Successfully");
                                Intent intent = new Intent(mContext, MainActivity.class);
                                startActivity(intent);

                            } else {
                                errorMessageDialog.showDialog("Please Enter Correct Old Password ");
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
