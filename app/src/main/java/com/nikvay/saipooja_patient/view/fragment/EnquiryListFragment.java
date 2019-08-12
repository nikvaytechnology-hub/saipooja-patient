package com.nikvay.saipooja_patient.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import com.google.gson.Gson;
import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.apiCallCommen.ApiClient;
import com.nikvay.saipooja_patient.apiCallCommen.ApiInterface;
import com.nikvay.saipooja_patient.model.EnquiryModel;
import com.nikvay.saipooja_patient.model.PatientModel;
import com.nikvay.saipooja_patient.model.SuccessModel;
import com.nikvay.saipooja_patient.utils.ErrorMessageDialog;
import com.nikvay.saipooja_patient.utils.NetworkUtils;
import com.nikvay.saipooja_patient.utils.SharedUtils;
import com.nikvay.saipooja_patient.utils.ShowProgress;
import com.nikvay.saipooja_patient.utils.SuccessMessageDialog;
import com.nikvay.saipooja_patient.view.activity.EnquiryAddActivity;
import com.nikvay.saipooja_patient.view.adapter.EnquiryAdapter;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnquiryListFragment extends Fragment {

RecyclerView recyclerViewEnquiryList;
    ArrayList<EnquiryModel> enquiryModelArrayList = new ArrayList<>();
    EnquiryAdapter enquiryAdapter;
    String TAG = getClass().getSimpleName();
    ApiInterface apiInterface;
    private ErrorMessageDialog errorMessageDialog;
    SuccessMessageDialog successMessageDialog;
    Context mContext;

    private FloatingActionButton fabAddNewEnquiry;

    ProgressDialog pd;
    EditText edt_search_doctor;

    ArrayList<PatientModel> patientModelArrayList = new ArrayList<>();
    String patient_id;

    ImageView iv_no_data_found;

    ShowProgress showProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_enquiry, container, false);

        mContext =getActivity();

        find_All_IDs(view);
        events();

        return  view;
    }

    private void find_All_IDs(View view)
    {
        recyclerViewEnquiryList = view.findViewById(R.id.recyclerViewEnquiryList);
        iv_no_data_found = view.findViewById(R.id.iv_no_data_found);
        edt_search_doctor = view.findViewById(R.id.edt_search_doctor);
        fabAddNewEnquiry = view.findViewById(R.id.fabAddNewEnquiry);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        errorMessageDialog= new ErrorMessageDialog(mContext);
        successMessageDialog = new SuccessMessageDialog(mContext);

        showProgress=new ShowProgress(mContext);

        patientModelArrayList = SharedUtils.getUserDetails(mContext);
        patient_id = patientModelArrayList.get(0).getPatient_id();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        recyclerViewEnquiryList.setLayoutManager(linearLayoutManager);
        recyclerViewEnquiryList.setHasFixedSize(true);


        if (NetworkUtils.isNetworkAvailable(mContext))
            callEnquiryList(patient_id);
        else
            NetworkUtils.isNetworkNotAvailable(mContext);


    }

    private void callEnquiryList(String patient_id)
    {
        showProgress.showDialog();
        Call<SuccessModel> call = apiInterface.enquiryList(patient_id);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                showProgress.dismissDialog();
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

                                enquiryModelArrayList=successModel.getEnquiryModelArrayList();

                                if(enquiryModelArrayList.size()!=0) {

                                    Collections.reverse(enquiryModelArrayList);
                                    enquiryAdapter = new EnquiryAdapter(mContext, enquiryModelArrayList);
                                    recyclerViewEnquiryList.setAdapter(enquiryAdapter);
                                    enquiryAdapter.notifyDataSetChanged();
                                    recyclerViewEnquiryList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

                                }
                                else
                                {
                                    iv_no_data_found.setVisibility(View.VISIBLE);
                                }

                            } else {
                                errorMessageDialog.showDialog("Response Null");
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


    private void events()
    {

        fabAddNewEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EnquiryAddActivity.class);
                startActivity(intent);
            }
        });

        edt_search_doctor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                enquiryAdapter.getFilter().filter(edt_search_doctor.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


}
