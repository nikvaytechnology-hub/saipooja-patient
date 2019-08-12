package com.nikvay.saipooja_patient.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.apiCallCommen.ApiClient;
import com.nikvay.saipooja_patient.apiCallCommen.ApiInterface;
import com.nikvay.saipooja_patient.model.DoctorListModel;
import com.nikvay.saipooja_patient.model.SuccessModel;
import com.nikvay.saipooja_patient.utils.ErrorMessageDialog;
import com.nikvay.saipooja_patient.utils.NetworkUtils;
import com.nikvay.saipooja_patient.utils.ShowProgress;
import com.nikvay.saipooja_patient.view.adapter.DoctorAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FindDoctorFragment extends Fragment {

    View view;
   Context mContext;
   RecyclerView recyclerViewDoctorList;
    ArrayList<DoctorListModel> doctorListModelArrayList=new ArrayList<>();
    private DoctorAdapter doctorAdapter;
    private ImageView iv_close,iv_no_data_found;
    private ApiInterface apiInterface;
    private ErrorMessageDialog errorMessageDialog;
    private String device_token,TAG = getClass().getSimpleName(),doctor_id,appointmentName="Service List";
    private TextView textTitleDoctorName;
    private EditText edt_search_doctor;
    ShowProgress showProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_find_doctor, container, false);
        mContext = getActivity();

        find_All_ID(view);
        event();
        return  view;

    }

    private void event()
    {

        edt_search_doctor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                doctorAdapter.getFilter().filter(edt_search_doctor.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void find_All_ID(View view)
    {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        recyclerViewDoctorList=view.findViewById(R.id.recyclerViewDoctorList);
        iv_close=view.findViewById(R.id.iv_close);
        textTitleDoctorName=view.findViewById(R.id.textTitleDoctorName);
        iv_no_data_found=view.findViewById(R.id.iv_no_data_found);
        edt_search_doctor=view.findViewById(R.id.edt_search_doctor);



        errorMessageDialog= new ErrorMessageDialog(mContext);
        showProgress=new ShowProgress(mContext);

        if (NetworkUtils.isNetworkAvailable(mContext))
            callDoctorList();
        else
            NetworkUtils.isNetworkNotAvailable(mContext);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        recyclerViewDoctorList.setLayoutManager(linearLayoutManager);
        recyclerViewDoctorList.setHasFixedSize(true);


    }

    private void callDoctorList()
    {
        showProgress.showDialog();

        Call<SuccessModel> call = apiInterface.doctorList();
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                showProgress.dismissDialog();
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);


                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();
                        doctorListModelArrayList.clear();
                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();


                            if (code.equalsIgnoreCase("1")) {

                                doctorListModelArrayList=successModel.getDoctorListModelArrayList();

                                if(doctorListModelArrayList.size()!=0) {

                                    doctorAdapter = new DoctorAdapter(mContext, doctorListModelArrayList);
                                    recyclerViewDoctorList.setAdapter(doctorAdapter);
                                    doctorAdapter.notifyDataSetChanged();
                                    recyclerViewDoctorList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

                                }
                                else
                                {
                                    iv_no_data_found.setVisibility(View.VISIBLE);
                                }

                            } else {
                                errorMessageDialog.showDialog("Response Not Working");
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                showProgress.dismissDialog();
                errorMessageDialog.showDialog(t.getMessage());
            }
        });
    }

}
