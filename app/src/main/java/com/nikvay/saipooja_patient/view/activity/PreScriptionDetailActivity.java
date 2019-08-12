package com.nikvay.saipooja_patient.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.model.PreScriptionModel;
import com.nikvay.saipooja_patient.model.PrescriptionListModel;
import com.nikvay.saipooja_patient.utils.ErrorMessageDialog;
import com.nikvay.saipooja_patient.utils.StaticContent;
import com.nikvay.saipooja_patient.utils.SuccessMessageDialog;
import com.nikvay.saipooja_patient.view.adapter.PreScriptionDetailsAdapter;

import java.util.ArrayList;

public class PreScriptionDetailActivity extends AppCompatActivity {

    PreScriptionModel preScriptionModel;
    ArrayList<PreScriptionModel> preScriptionModelArrayList=new ArrayList<>();
    PrescriptionListModel prescriptionListModel;

    RecyclerView recyclerView_Prescription;

    String patient_id,date,TAG =getClass().getSimpleName();
    ErrorMessageDialog errorMessageDialog;
    SuccessMessageDialog successMessageDialog;

    PreScriptionDetailsAdapter preScriptionDetailsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_detail);

        find_All_Id();
        event();

    }

    private void event() {

    }

    private void find_All_Id() {
        recyclerView_Prescription = findViewById(R.id.recyclerView_Prescription);


        Bundle bundle = getIntent().getExtras();


        if (bundle != null) {

            preScriptionModel = (PreScriptionModel) bundle.getSerializable(StaticContent.IntentKey.PRESCRIPTION);

            preScriptionModelArrayList.add(preScriptionModel);

            //Toast.makeText(this, serviceModel.getS_name()+""+doctorModel.getPatient_id()+" "+date+" "+time, Toast.LENGTH_SHORT).show();
        }

        errorMessageDialog= new ErrorMessageDialog(PreScriptionDetailActivity.this);
        successMessageDialog= new SuccessMessageDialog(PreScriptionDetailActivity.this);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(PreScriptionDetailActivity.this);
        recyclerView_Prescription.setLayoutManager(linearLayoutManager);
        recyclerView_Prescription.setHasFixedSize(true);

        showPreScriptionDateDetails(preScriptionModel);

    }

    private void showPreScriptionDateDetails(PreScriptionModel preScriptionModel)
    {
        preScriptionDetailsAdapter = new PreScriptionDetailsAdapter(PreScriptionDetailActivity.this, preScriptionModelArrayList);
        recyclerView_Prescription.setAdapter(preScriptionDetailsAdapter);
        preScriptionDetailsAdapter.notifyDataSetChanged();
        recyclerView_Prescription.addItemDecoration(new DividerItemDecoration(PreScriptionDetailActivity.this, DividerItemDecoration.VERTICAL));

    }
}
