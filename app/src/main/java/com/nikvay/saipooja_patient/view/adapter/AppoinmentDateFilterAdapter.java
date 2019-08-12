package com.nikvay.saipooja_patient.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.model.AppoinmentDateFilterModel;
import com.nikvay.saipooja_patient.view.activity.AppointmentListActivity;

import java.util.ArrayList;

public class AppoinmentDateFilterAdapter extends RecyclerView.Adapter<AppoinmentDateFilterAdapter.MyViewHolder> {

    ArrayList<AppoinmentDateFilterModel> appoinmentDateFilterModelArrayList;
    String date;
    Context mContext;

    public AppoinmentDateFilterAdapter(AppointmentListActivity appointmentListActivity,
                                       ArrayList<AppoinmentDateFilterModel> appoinmentListModelArrayList) {
        this.appoinmentDateFilterModelArrayList =appoinmentListModelArrayList;
        this.mContext = appointmentListActivity;
        this.date = "";

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_patient_apt_adapter,parent,false);

        return new AppoinmentDateFilterAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        AppoinmentDateFilterModel appoinmentDateFilterModel = appoinmentDateFilterModelArrayList.get(position);

        holder.textDoctorName.setText(String.valueOf(appoinmentDateFilterModel.getName()));
        holder.textService.setText(String.valueOf(appoinmentDateFilterModel.getS_name()));
        holder.textDay.setText(String.valueOf(appoinmentDateFilterModel.getDate()));
        holder.textServiceDate.setText(appoinmentDateFilterModel.getDate());
        holder.textServiceTime.setText(String.valueOf(appoinmentDateFilterModel.getTime()));

        String label =String.valueOf(appoinmentDateFilterModel.getLabel());
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

    @Override
    public int getItemCount() {
        return appoinmentDateFilterModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textDay, textTime, textDoctorName, textServiceTime, textService, textServiceDate,serviceStatus;

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
