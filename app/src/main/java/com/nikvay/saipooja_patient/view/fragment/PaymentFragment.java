package com.nikvay.saipooja_patient.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.apiCallCommen.ApiInterface;
import com.nikvay.saipooja_patient.model.PatientModel;
import com.nikvay.saipooja_patient.model.PaymentDetailModel;
import com.nikvay.saipooja_patient.utils.ErrorMessageDialog;
import com.nikvay.saipooja_patient.utils.SuccessMessageDialog;
import com.nikvay.saipooja_patient.view.adapter.PaymentAdapter;

import java.util.ArrayList;
import java.util.Calendar;


public class PaymentFragment extends Fragment {

   View view;
   RecyclerView recyclerView_payment;
   Context mContext;
    private ApiInterface apiInterface;
   String patient_id,TAG = getClass().getSimpleName();
    private ArrayList<PatientModel> patientModelArrayList=new ArrayList<>();
    ErrorMessageDialog errorMessageDialog;
    SuccessMessageDialog successMessageDialog;
    ArrayList<PaymentDetailModel>paymentDetailModelArrayList = new ArrayList<>();
    PaymentAdapter paymentAdapter;
    ImageView iv_empty_list;
    ProgressDialog pd;
    private EditText edt_search_service;

    /*  date Wise */


    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    String date;
    ImageView iv_tool_calender;
    CalendarView calendarView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view =  inflater.inflate(R.layout.fragment_payment, container, false);
       mContext = getActivity();


      // find_All_Id(view);
      // event();

       return view;
    }









}
