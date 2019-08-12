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
import com.nikvay.saipooja_patient.model.ServiceModel;
import com.nikvay.saipooja_patient.utils.StaticContent;
import com.nikvay.saipooja_patient.view.activity.DoctorListActivity;

import java.util.ArrayList;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<ServiceModel> serviceModelArrayList;
    private ArrayList<ServiceModel> arrayListFiltered;
    private String appointmentName;


    public ServiceListAdapter(Context mContext, ArrayList<ServiceModel> serviceModelArrayList, String appointmentName) {
        this.mContext = mContext;
        this.serviceModelArrayList = serviceModelArrayList;
        this.appointmentName=appointmentName;
        this.arrayListFiltered=serviceModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_service_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        ServiceModel serviceModel=serviceModelArrayList.get(position);

        holder.textServiceName.setText(serviceModel.getS_name());
        holder.textTime.setText(serviceModel.getService_time() + "Min");
        holder.textCost.setText(serviceModel.getService_cost());

        holder.relativeLayoutService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DoctorListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.DOCTOR_DETAIL, serviceModelArrayList.get(position));
                intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_DOCTOR_DETAILS);
                mContext.startActivity(intent);

            }
        });




      /*  else
        {
            holder.relativeLayoutService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ServiceDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(StaticContent.IntentKey.SERVICE_DETAIL, serviceModelArrayList.get(position));
                    intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_SERVICE_DETAILS);
                    mContext.startActivity(intent);
                }

            });

        }*/

    }




    @Override
    public int getItemCount() {
        return serviceModelArrayList.size();
    }



    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().replaceAll("\\s","").toLowerCase().trim();
                if (charString.isEmpty() || charSequence.equals("")) {
                    serviceModelArrayList = arrayListFiltered;
                } else {
                    ArrayList<ServiceModel> filteredList = new ArrayList<>();
                    for (int i = 0; i < serviceModelArrayList.size(); i++) {

                        String serviceName=serviceModelArrayList.get(i).getS_name().replaceAll("\\s","").toLowerCase().trim();
                        if (serviceName.contains(charString)) {
                            filteredList.add(serviceModelArrayList.get(i));
                        }
                    }
                    if (filteredList.size() > 0) {
                        serviceModelArrayList = filteredList;
                    } else {
                        serviceModelArrayList = arrayListFiltered;
                    }

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = serviceModelArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                serviceModelArrayList = (ArrayList<ServiceModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textServiceName, textTime, textCost;
        private RelativeLayout relativeLayoutService;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textServiceName = itemView.findViewById(R.id.textServiceName);
            textTime = itemView.findViewById(R.id.textTime);
            textCost = itemView.findViewById(R.id.textCost);
            relativeLayoutService = itemView.findViewById(R.id.relativeLayoutService);

        }
    }
}
