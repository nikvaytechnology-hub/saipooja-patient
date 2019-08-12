package com.nikvay.saipooja_patient.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.apiCallCommen.ApiClient;
import com.nikvay.saipooja_patient.apiCallCommen.ApiInterface;
import com.nikvay.saipooja_patient.model.ClassModel;
import com.nikvay.saipooja_patient.model.SuccessModel;
import com.nikvay.saipooja_patient.utils.ErrorMessageDialog;
import com.nikvay.saipooja_patient.utils.NetworkUtils;
import com.nikvay.saipooja_patient.view.adapter.ClassListAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ClassListFragment extends Fragment {


    Context context;
    RecyclerView recyclerView_class;
    ApiInterface apiInterface;
    String TAG = getClass().getSimpleName();
    ArrayList<ClassModel>classModelArrayList = new ArrayList<>();
    ClassListAdapter classListAdapter;
    ErrorMessageDialog errorMessageDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_class_list, container, false);
        context = getActivity();

        find_all_Id(view);


        if (NetworkUtils.isNetworkAvailable(context))
            classListCall();
        else
            NetworkUtils.isNetworkNotAvailable(context);

        event();
        return  view;
    }

    private void classListCall() {

        Call<SuccessModel> call = apiInterface.classList();

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response)
            {
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);

                try
                {
                    if (response.isSuccessful())
                    {
                        SuccessModel classModel = response.body();
                        String message = null, code = null;
                        if (classModel != null) {
                            message = classModel.getMsg();
                            code = classModel.getError_code();

                            if (code.equalsIgnoreCase("1"))
                            {
                                classModelArrayList = classModel.getClassListModelArrayList();
                                if (classModelArrayList.size()!= 0)
                                {
                                    classListAdapter = new ClassListAdapter(context, classModelArrayList);
                                    recyclerView_class.setAdapter(classListAdapter);
                                    classListAdapter.notifyDataSetChanged();
                                    recyclerView_class.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
                                }
                                else
                                {
                                    errorMessageDialog.showDialog("List is Empty");
                                }
                            }
                            else
                            {
                                errorMessageDialog.showDialog("parametr is missing");
                            }
                        }
                        else
                        {
                            errorMessageDialog.showDialog("Response is null");
                        }
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t)
            {


            }
        });

    }

    private void find_all_Id(View view) {

        errorMessageDialog= new ErrorMessageDialog(context);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        recyclerView_class = view.findViewById(R.id.recyclerView_class);


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        recyclerView_class.setLayoutManager(linearLayoutManager);
        recyclerView_class.setHasFixedSize(true);

    }

    private void event() {


    }
}
