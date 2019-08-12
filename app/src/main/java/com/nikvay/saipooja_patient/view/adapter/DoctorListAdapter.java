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
import com.nikvay.saipooja_patient.model.DoctorModel;
import com.nikvay.saipooja_patient.model.ServiceModel;
import com.nikvay.saipooja_patient.utils.ErrorMessageDialog;
import com.nikvay.saipooja_patient.utils.StaticContent;
import com.nikvay.saipooja_patient.view.activity.DateTimeSelectActivity;

import java.util.ArrayList;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<DoctorModel> doctorModelArrayList;
    private ArrayList<DoctorModel> arrayListFiltered;

    private ServiceModel serviceModel;
    private String date;
    private ErrorMessageDialog errorMessageDialog;
    public DoctorListAdapter(Context mContext, ArrayList<DoctorModel> doctorModelArrayList,
                             ServiceModel serviceModel) {

        this.serviceModel = serviceModel;
        errorMessageDialog=new ErrorMessageDialog(mContext);
       this.doctorModelArrayList = doctorModelArrayList;
       this.arrayListFiltered = doctorModelArrayList;
       this.mContext =mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_doctor_adapter, parent, false);
        return new DoctorListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        DoctorModel doctorModel = doctorModelArrayList.get(position);

        holder.textName.setText(doctorModel.getName());
        holder.textContact.setText(doctorModel.getPhone_no());
        holder.textqualification.setText(doctorModel.getProfile());

        holder.relativeLayoutDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DateTimeSelectActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.APPOINTMENT, doctorModelArrayList.get(position));
                intent.putExtra(StaticContent.IntentKey.SERVICE_DETAIL,serviceModel);
                intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_APPOINTMENT_DETAILS);
                mContext.startActivity(intent);

            }
        });





    }

    @Override
    public int getItemCount() {
        return doctorModelArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().replaceAll("\\s","").toLowerCase().trim();
                if (charString.isEmpty() || charSequence.equals("")) {
                    doctorModelArrayList = arrayListFiltered;
                } else {
                    ArrayList<DoctorModel> filteredList = new ArrayList<>();
                    for (int i = 0; i < doctorModelArrayList.size(); i++) {

                        String serviceName=doctorModelArrayList.get(i).getName().replaceAll("\\s","").toLowerCase().trim();
                        if (serviceName.contains(charString)) {
                            filteredList.add(doctorModelArrayList.get(i));
                        }
                    }
                    if (filteredList.size() > 0) {
                        doctorModelArrayList = filteredList;
                    } else {
                        doctorModelArrayList = arrayListFiltered;
                    }

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = doctorModelArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                doctorModelArrayList = (ArrayList<DoctorModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textName,textContact,textqualification;
        RelativeLayout relativeLayoutDoctor;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.textName);
            textContact = itemView.findViewById(R.id.textContact);
            textqualification = itemView.findViewById(R.id.textqualification);
            relativeLayoutDoctor = itemView.findViewById(R.id.relativeLayoutDoctor);
        }
    }
}
