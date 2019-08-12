package com.nikvay.saipooja_patient.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.model.PatientModel;
import com.nikvay.saipooja_patient.model.PreScriptionModel;
import com.nikvay.saipooja_patient.model.PrescriptionListModel;
import com.nikvay.saipooja_patient.utils.SharedUtils;
import com.nikvay.saipooja_patient.view.activity.PreScriptionDetailActivity;

import java.util.ArrayList;

public class PreScriptionDetailsAdapter extends RecyclerView.Adapter<PreScriptionDetailsAdapter.MyViewHolder> {

    PreScriptionModel preScriptionModel;
    ArrayList<PreScriptionModel>preScriptionModelArrayList;
    Context mContext;

    ArrayList<PatientModel> patientModelArrayList = new ArrayList<>();
    String patient_name;

    PreScriptionTableChildAdapter preScriptionTableChildAdapter;
    PreScriptionMedicalTableChildAdapter preScriptionMedicalTableChildAdapter;


    public PreScriptionDetailsAdapter(PreScriptionDetailActivity activity, ArrayList<PreScriptionModel>preScriptionModelArrayList) {
        this.mContext = activity;
        this.preScriptionModelArrayList = preScriptionModelArrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_prescription_details_date_adapter, parent, false);
        return new PreScriptionDetailsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        final PreScriptionModel preScriptionModel = preScriptionModelArrayList.get(position);


        holder.txtDoctorName.setText(preScriptionModel.getName());
        holder.txtSymptomName.setText(preScriptionModel.getSymptoms());
        holder.txtServiceName.setText(preScriptionModel.getS_name());
        holder.txtDiagnosisName.setText(preScriptionModel.getDiagnosis());

        holder.txtPatientName.setText(patient_name);

        try {

            for (PrescriptionListModel prescriptionListModel  : preScriptionModel.getPrescriptionListModelArrayList())
            {
                String testName = prescriptionListModel.getTest_name();
                String testNote = prescriptionListModel.getTest_note();
                String medecineName = prescriptionListModel.getMedication_name();
                String medecineNote = prescriptionListModel.getMedication_note();


            }

        }catch (Exception e){
            e.printStackTrace();
        }

        preScriptionTableChildAdapter = new PreScriptionTableChildAdapter(preScriptionModel.getPrescriptionListModelArrayList());
        holder.recycler_view_result_child.setAdapter(preScriptionTableChildAdapter);
        preScriptionTableChildAdapter.notifyDataSetChanged();

        preScriptionMedicalTableChildAdapter = new PreScriptionMedicalTableChildAdapter(preScriptionModel.getPrescriptionListModelArrayList());
        holder.recycler_view_medical_child.setAdapter(preScriptionMedicalTableChildAdapter);
        preScriptionMedicalTableChildAdapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return preScriptionModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtDoctorName,txtPatientName,txtServiceName,txtSymptomName,txtDiagnosisName,txtSrNo,testName,txtNote,medecineName,medecineNote,txtMdSrNo;
        RecyclerView recycler_view_result_child,recycler_view_medical_child;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDoctorName = itemView.findViewById(R.id.txtDoctorName);
            txtPatientName = itemView.findViewById(R.id.txtPatientName);
            txtServiceName = itemView.findViewById(R.id.txtServiceName);
            txtSymptomName = itemView.findViewById(R.id.txtSymptomName);
            txtDiagnosisName = itemView.findViewById(R.id.txtDiagnosisName);


            patientModelArrayList = SharedUtils.getUserDetails(mContext);
            patient_name = patientModelArrayList.get(0).getName();

            recycler_view_result_child = itemView.findViewById(R.id.recycler_view_result_child);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            recycler_view_result_child.setLayoutManager(layoutManager);
            recycler_view_result_child.setHasFixedSize(true);

            recycler_view_medical_child = itemView.findViewById(R.id.recycler_view_medical_child);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            recycler_view_medical_child.setLayoutManager(linearLayoutManager);
            recycler_view_medical_child.setHasFixedSize(true);


        }
    }
}
