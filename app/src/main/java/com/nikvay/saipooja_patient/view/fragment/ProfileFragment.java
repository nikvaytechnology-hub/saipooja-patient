package com.nikvay.saipooja_patient.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.apiCallCommen.ApiInterface;
import com.nikvay.saipooja_patient.model.PatientModel;
import com.nikvay.saipooja_patient.utils.SharedUtils;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private Context mContext;
    private TextView textName, textEmail, textPatientName, textDoctorEmail, textpatientPhone, textDoctorAddress;
    private ArrayList<PatientModel> patientModelArrayList = new ArrayList<>();
    private String TAG = getClass().getSimpleName();
    private ApiInterface apiInterface;
    private Button btn_update_profile;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        mContext = getActivity();

        find_All_IDs(view);
        events();

        return  view;
    }

    private void find_All_IDs(View view)
    {
       textName = view.findViewById(R.id.textName);
        textEmail = view.findViewById(R.id.textEmail);
        textPatientName = view.findViewById(R.id.textPatientName);
        textDoctorEmail = view.findViewById(R.id.textDoctorEmail);
        textpatientPhone = view.findViewById(R.id.textpatientPhone);
        textDoctorAddress = view.findViewById(R.id.textDoctorAddress);
        btn_update_profile = view.findViewById(R.id.btn_update_profile);

        patientModelArrayList = SharedUtils.getUserDetails(mContext);
        textName.setText(patientModelArrayList.get(0).getName());
        textEmail.setText(patientModelArrayList.get(0).getEmail());
        textPatientName.setText(patientModelArrayList.get(0).getName());
        textDoctorEmail.setText(patientModelArrayList.get(0).getEmail());
        textDoctorAddress.setText(patientModelArrayList.get(0).getAddress());
        textpatientPhone.setText(patientModelArrayList.get(0).getPhone_no());
    }


    private void events() {
    }


}
