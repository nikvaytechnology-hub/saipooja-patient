package com.nikvay.saipooja_patient.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.model.PatientAptdetailstListModel;
import com.nikvay.saipooja_patient.view.activity.AppointmentListActivity;

import java.util.ArrayList;

public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.MyViewHolder> implements Filterable {

    ArrayList<PatientAptdetailstListModel>patientAptdetailstListModelArrayList;
    ArrayList<PatientAptdetailstListModel>arrayListFiltered;

    String date;

    Context mContext;

    public AppointmentListAdapter(AppointmentListActivity appointmentListActivity,
                                  ArrayList<PatientAptdetailstListModel> appoinmentListModelArrayList) {
        this.patientAptdetailstListModelArrayList =appoinmentListModelArrayList;
        this.arrayListFiltered = appoinmentListModelArrayList;
        this.mContext = appointmentListActivity;
       this.date = "";

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_patient_apt_adapter,parent,false);

        return new AppointmentListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {

        if (date.equalsIgnoreCase("DATEFILTER"))
        {
        }
        else
        {
            PatientAptdetailstListModel patientAptdetailstListModel = patientAptdetailstListModelArrayList.get(position);

            // holder.textDay.setText(patientAptdetailstListModel.getDate());
            holder.textDoctorName.setText(String.valueOf(patientAptdetailstListModel.getName()));
            holder.textService.setText(String.valueOf(patientAptdetailstListModel.getS_name()));
            holder.textServiceDate.setText(patientAptdetailstListModel.getDate());
            holder.textDay.setText(patientAptdetailstListModel.getDate());
            holder.textServiceTime.setText(String.valueOf(patientAptdetailstListModel.getTime()));

            String label =String.valueOf(patientAptdetailstListModel.getLabel());
            if (label.equals("0") )
            {
                holder.serviceStatus.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
                holder.serviceStatus.setText("Pending");
            }
            else if (label.equals("1"))
            {
                holder.serviceStatus.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                holder.serviceStatus.setText("Confirmed");
            }
            else if (label.equals("2"))
            {
                holder.serviceStatus.setBackgroundColor(mContext.getResources().getColor(R.color.red));
                holder.serviceStatus.setText("Cancelled");
            }
            else if (label.equals("3"))
            {
                holder.serviceStatus.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                holder.serviceStatus.setText("Completed");
            }

        }
    }

    @Override
    public int getItemCount() {
        return patientAptdetailstListModelArrayList.size();
    }



    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().replaceAll("\\s","").toLowerCase().trim();
                if (charString.isEmpty() || charSequence.equals("")) {
                    patientAptdetailstListModelArrayList = arrayListFiltered;
                } else {
                    ArrayList<PatientAptdetailstListModel> filteredList = new ArrayList<>();
                    for (int i = 0; i < patientAptdetailstListModelArrayList.size(); i++) {

                        String name=patientAptdetailstListModelArrayList.get(i).getName().replaceAll("\\s","").toLowerCase().trim();
                        if (name.contains(charString)) {
                            filteredList.add(patientAptdetailstListModelArrayList.get(i));
                        }
                    }
                    if (filteredList.size() > 0) {
                        patientAptdetailstListModelArrayList = filteredList;
                    } else {
                        patientAptdetailstListModelArrayList = arrayListFiltered;
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = patientAptdetailstListModelArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                patientAptdetailstListModelArrayList = (ArrayList<PatientAptdetailstListModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textDay, textTime, textDoctorName, textServiceTime, textService, textServiceDate,serviceStatus;
        LinearLayout ll_Patient_apt_details;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textDoctorName = itemView.findViewById(R.id.textDoctorName);
            textServiceTime = itemView.findViewById(R.id.textServiceTime);
            textService = itemView.findViewById(R.id.textService);
            textServiceDate = itemView.findViewById(R.id.textServiceDate);
            serviceStatus = itemView.findViewById(R.id.serviceStatus);
            textDay = itemView.findViewById(R.id.textDay);


        }
    }
}
