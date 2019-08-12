package com.nikvay.saipooja_patient.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.model.DoctorListModel;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.MyViewHolder> implements Filterable {

    Context mContext;
    ArrayList<DoctorListModel> doctorListModelArrayList;
    ArrayList<DoctorListModel> arrayListFiltered;

    public DoctorAdapter(Context mContext, ArrayList<DoctorListModel> doctorListModelArrayList)
    {
       this.doctorListModelArrayList = doctorListModelArrayList;
       this.mContext = mContext;
        this.arrayListFiltered=doctorListModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_find_doctor_adapetr, parent, false);
        return new DoctorAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        DoctorListModel txtDoctorName = doctorListModelArrayList.get(position);

        holder.txtDoctorName.setText(txtDoctorName.getName());
        holder.txtEmail.setText(txtDoctorName.getEmail());
        holder.txthospitalName.setText(txtDoctorName.getHospital_name());
        holder.txtPhoneNo.setText(txtDoctorName.getPhone_no());
        holder.txtProfile.setText(txtDoctorName.getProfile());
        holder.txtAddress.setText(txtDoctorName.getAddress());


    }

    @Override
    public int getItemCount() {
        return doctorListModelArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().replaceAll("\\s","").toLowerCase().trim();
                if (charString.isEmpty() || charSequence.equals("")) {
                    doctorListModelArrayList = arrayListFiltered;
                } else {
                    ArrayList<DoctorListModel> filteredList = new ArrayList<>();
                    for (int i = 0; i < doctorListModelArrayList.size(); i++) {

                        String serviceName=doctorListModelArrayList.get(i).getName().replaceAll("\\s","").toLowerCase().trim();
                        if (serviceName.contains(charString)) {
                            filteredList.add(doctorListModelArrayList.get(i));
                        }
                    }
                    if (filteredList.size() > 0) {
                        doctorListModelArrayList = filteredList;
                    } else {
                        doctorListModelArrayList = arrayListFiltered;
                    }

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = doctorListModelArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                doctorListModelArrayList = (ArrayList<DoctorListModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtDoctorName,txtEmail,txthospitalName,txtProfile,txtAddress,txtPhoneNo;

        public MyViewHolder(@NonNull View view) {
            super(view);


            txtDoctorName = view.findViewById(R.id.txtDoctorName);
            txtEmail = view.findViewById(R.id.txtEmail);
            txthospitalName = view.findViewById(R.id.txthospitalName);
            txtProfile = view.findViewById(R.id.txtProfile);
            txtAddress = view.findViewById(R.id.txtAddress);
            txtPhoneNo = view.findViewById(R.id.txtPhoneNo);
        }
    }
}
