package com.nikvay.saipooja_patient.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.model.PreScriptionDateModel;
import com.nikvay.saipooja_patient.model.PrescriptionListDateModel;
import com.nikvay.saipooja_patient.utils.ErrorMessageDialog;
import com.nikvay.saipooja_patient.utils.StaticContent;
import com.nikvay.saipooja_patient.utils.SuccessMessageDialog;
import com.nikvay.saipooja_patient.view.adapter.PreScriptionDateDetailsAdapter;

import java.util.ArrayList;

public class PreScriptionDateDetailsActivity extends AppCompatActivity {

    PreScriptionDateModel preScriptionModel;

    ArrayList<PreScriptionDateModel>preScriptionDateModelArrayList =new ArrayList<>();
    PrescriptionListDateModel prescriptionListModel;

    RecyclerView recyclerView_Prescription_Date;

    String patient_id,date,TAG =getClass().getSimpleName();
    ErrorMessageDialog errorMessageDialog;
    SuccessMessageDialog successMessageDialog;

    PreScriptionDateDetailsAdapter preScriptionDateDetailsAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_scription_date_details);

        find_All_Id();
        event();

    }
    private void event() {

    }

    private void find_All_Id() {
        recyclerView_Prescription_Date = findViewById(R.id.recyclerView_Prescription_Date);

        Bundle bundle = getIntent().getExtras();


        if (bundle != null) {

            preScriptionModel= (PreScriptionDateModel) bundle.getSerializable(StaticContent.IntentKey.PRESCRIPTION_DATE);
            preScriptionDateModelArrayList.add(preScriptionModel);

            //Toast.makeText(this, serviceModel.getS_name()+""+doctorModel.getPatient_id()+" "+date+" "+time, Toast.LENGTH_SHORT).show();

        }
        errorMessageDialog= new ErrorMessageDialog(PreScriptionDateDetailsActivity.this);
        successMessageDialog= new SuccessMessageDialog(PreScriptionDateDetailsActivity.this);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(PreScriptionDateDetailsActivity.this);
        recyclerView_Prescription_Date.setLayoutManager(linearLayoutManager);
        recyclerView_Prescription_Date.setHasFixedSize(true);



        showPreScriptionDateDetails(preScriptionModel);

    }


    private void showPreScriptionDateDetails(PreScriptionDateModel preScriptionModel)
    {
        preScriptionDateDetailsAdapter = new PreScriptionDateDetailsAdapter(PreScriptionDateDetailsActivity.this, preScriptionDateModelArrayList);
        recyclerView_Prescription_Date.setAdapter(preScriptionDateDetailsAdapter);
        preScriptionDateDetailsAdapter.notifyDataSetChanged();
        recyclerView_Prescription_Date.addItemDecoration(new DividerItemDecoration(PreScriptionDateDetailsActivity.this, DividerItemDecoration.VERTICAL));
    }
}
