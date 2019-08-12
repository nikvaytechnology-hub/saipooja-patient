package com.nikvay.saipooja_patient.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.model.PreScriptionModel;
import com.nikvay.saipooja_patient.model.PrescriptionListModel;
import com.nikvay.saipooja_patient.utils.StaticContent;
import com.nikvay.saipooja_patient.view.activity.PreScriptionDetailActivity;
import com.nikvay.saipooja_patient.view.activity.PreSriptionActivity;

import java.util.ArrayList;

public class PreScriptionAdapter extends RecyclerView.Adapter<PreScriptionAdapter.MyViewHolder> implements Filterable  {
    ArrayList<PreScriptionModel>preScriptionModelArrayList;
    ArrayList<PrescriptionListModel>prescriptionListModelArrayList;
    private ArrayList<PreScriptionModel> arrayListFiltered;


    PrescriptionListModel prescriptionListModel;
    Context mContext;
    public PreScriptionAdapter(PreSriptionActivity preSriptionActivity, ArrayList<PreScriptionModel> preScriptionModelArrsyList)
    {
        this.mContext = preSriptionActivity;
        this.preScriptionModelArrayList =preScriptionModelArrsyList;
        this.arrayListFiltered =preScriptionModelArrsyList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_prescrption_adapter,parent,false);

        return new  PreScriptionAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,  final int position) {


        PreScriptionModel preScriptionModel = preScriptionModelArrayList.get(position);

        prescriptionListModelArrayList= preScriptionModel.getPrescriptionListModelArrayList();

      //  PrescriptionListModel prescriptionListModel = prescriptionListModelArrayList.get(position);

        holder.txtDoctorName.setText(preScriptionModel.getName());
        holder.txtDate.setText(preScriptionModel.getDate());

        holder.rel_preHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PreScriptionDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.PRESCRIPTION, preScriptionModelArrayList.get(position));
                intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_PSCRIPTION_DATE_DETAILS);
                mContext.startActivity(intent);

            }
        });

    }


    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().replaceAll("\\s","").toLowerCase().trim();
                if (charString.isEmpty() || charSequence.equals("")) {
                    preScriptionModelArrayList = arrayListFiltered;
                } else {
                    ArrayList<PreScriptionModel> filteredList = new ArrayList<>();
                    for (int i = 0; i < preScriptionModelArrayList.size(); i++) {

                        String serviceName=preScriptionModelArrayList.get(i).getName().replaceAll("\\s","").toLowerCase().trim();
                        if (serviceName.contains(charString)) {
                            filteredList.add(preScriptionModelArrayList.get(i));
                        }
                    }
                    if (filteredList.size() > 0) {
                        preScriptionModelArrayList = filteredList;
                    } else {
                        preScriptionModelArrayList = arrayListFiltered;
                    }

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = preScriptionModelArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                preScriptionModelArrayList = (ArrayList<PreScriptionModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    @Override
    public int getItemCount() {
        return preScriptionModelArrayList.size() ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rel_preHistory;
        TextView txtDoctorName,txtDate,txtViewDetail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rel_preHistory = itemView.findViewById(R.id.rel_preHistory);
            txtDoctorName = itemView.findViewById(R.id.txtDoctorName);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtViewDetail = itemView.findViewById(R.id.txtViewDetail);
        }
    }
}
