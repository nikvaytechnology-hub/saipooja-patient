package com.nikvay.saipooja_patient.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.model.PatientModel;
import com.nikvay.saipooja_patient.model.PreScriptionDateModel;
import com.nikvay.saipooja_patient.model.PrescriptionListDateModel;
import com.nikvay.saipooja_patient.utils.SharedUtils;
import com.nikvay.saipooja_patient.view.activity.PreScriptionDateDetailsActivity;

import java.util.ArrayList;

public class PreScriptionDateDetailsAdapter extends RecyclerView.Adapter<PreScriptionDateDetailsAdapter.MyViewHolder> {

    Context mContext;
    String patient_name;
    ArrayList<PatientModel> patientModelArrayList = new ArrayList<>();

    PrescriptionListDateModel prescriptionListDateModel ;
    ArrayList<PrescriptionListDateModel>prescriptionListDateModelarraylist;

    ArrayList<PreScriptionDateModel>preListDateModelArrayList;

    public PreScriptionDateDetailsAdapter(PreScriptionDateDetailsActivity activity, ArrayList<PreScriptionDateModel> preListDateModelArrayList)
    {
        this.preListDateModelArrayList = preListDateModelArrayList;
        this.mContext = activity;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_prescription_details_date_adapter, parent, false);

        return new PreScriptionDateDetailsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        PreScriptionDateModel preScriptionDateModel = preListDateModelArrayList.get(position);

       prescriptionListDateModelarraylist  = preScriptionDateModel.getPrescriptionListDateModelArrayList();

      // PrescriptionListDateModel prescriptionListDateModel =prescriptionListDateModelarraylist.get(position);

        holder.txtPatientName.setText(patient_name);
        holder.txtDoctorName.setText(preScriptionDateModel.getName());
        holder.txtSymptomName.setText(preScriptionDateModel.getSymptoms());
        holder.txtServiceName.setText(preScriptionDateModel.getS_name());
        holder.txtDiagnosisName.setText(preScriptionDateModel.getDiagnosis());



    }

    @Override
    public int getItemCount() {
        return preListDateModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtDoctorName,txtPatientName,txtServiceName,txtSymptomName,txtDiagnosisName,txtSrNo,testName,txtNote,medecineName,medecineNote,txtMdSrNo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDoctorName = itemView.findViewById(R.id.txtDoctorName);
            txtPatientName = itemView.findViewById(R.id.txtPatientName);
            txtServiceName = itemView.findViewById(R.id.txtServiceName);
            txtSymptomName = itemView.findViewById(R.id.txtSymptomName);
            txtDiagnosisName = itemView.findViewById(R.id.txtDiagnosisName);


            patientModelArrayList = SharedUtils.getUserDetails(mContext);
            patient_name = patientModelArrayList.get(0).getName();
        }
    }
}
