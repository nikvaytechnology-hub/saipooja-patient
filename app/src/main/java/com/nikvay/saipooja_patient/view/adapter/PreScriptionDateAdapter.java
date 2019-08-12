package com.nikvay.saipooja_patient.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.model.PreScriptionDateModel;
import com.nikvay.saipooja_patient.model.PrescriptionListDateModel;
import com.nikvay.saipooja_patient.utils.StaticContent;
import com.nikvay.saipooja_patient.view.activity.PreScriptionDateDetailsActivity;
import com.nikvay.saipooja_patient.view.activity.PreSriptionActivity;

import java.util.ArrayList;

public class PreScriptionDateAdapter extends RecyclerView.Adapter<PreScriptionDateAdapter.MyViewholder> {

    ArrayList<PreScriptionDateModel>preScriptionDateModelArrayList;
    ArrayList<PrescriptionListDateModel>preListDateModelArrayList;
    Context mContext;
    PrescriptionListDateModel prescriptionListDateModel ;

    public PreScriptionDateAdapter(PreSriptionActivity preSriptionActivity,
                                   ArrayList<PreScriptionDateModel> preScriptionDateModelArrayList) {
        this.preScriptionDateModelArrayList =preScriptionDateModelArrayList;
        this.mContext =preSriptionActivity;

    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_prescrption_adapter,parent,false);

        return new PreScriptionDateAdapter.MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, final int position) {

        PreScriptionDateModel preScriptionDateModel = preScriptionDateModelArrayList.get(position);

        preListDateModelArrayList = preScriptionDateModel.getPrescriptionListDateModelArrayList();





        holder.txtDoctorName.setText(preScriptionDateModel.getName());
        holder.txtDate.setText(preScriptionDateModel.getDate());
        holder.txtViewDetail.setText(preScriptionDateModel.getS_name());

        holder.rel_preHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PreScriptionDateDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.PRESCRIPTION_DATE, preScriptionDateModelArrayList.get(position));
                intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_PSCRIPTION_DATE_DETAILS);
                mContext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return preScriptionDateModelArrayList.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {

        RelativeLayout rel_preHistory;
        TextView txtDoctorName,txtDate,txtViewDetail;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            rel_preHistory = itemView.findViewById(R.id.rel_preHistory);
            txtDoctorName = itemView.findViewById(R.id.txtDoctorName);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtViewDetail = itemView.findViewById(R.id.txtViewDetail);

        }
    }
}
