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
import com.nikvay.saipooja_patient.model.EnquiryModel;

import java.util.ArrayList;

public class EnquiryAdapter extends RecyclerView.Adapter<EnquiryAdapter.MyViewHolder> implements Filterable {

    View view;
    ArrayList<EnquiryModel> enquiryModelArrayList;
    Context mContext;
    ArrayList<EnquiryModel> arrayListFiltered;

    public EnquiryAdapter(Context mContext, ArrayList<EnquiryModel> enquiryModelArrayList) {
        this.enquiryModelArrayList = enquiryModelArrayList;
        this.arrayListFiltered = enquiryModelArrayList;
        this.mContext =mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_enquiry_doctor,parent,false);
        return new EnquiryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        EnquiryModel enquiryModel = enquiryModelArrayList.get(position);

        holder.txtDoctorName.setText(enquiryModel.getName());
        holder.txtProfile.setText(enquiryModel.getProfile());
        holder.txtReplayDate.setText(enquiryModel.getReply_date());
        holder.txtReplayTime.setText(enquiryModel.getReply_time());
        holder.txtReplay.setText(enquiryModel.getReply());
        holder.txtQuestion.setText(enquiryModel.getDescription());
    }

    @Override
    public int getItemCount() {
        return enquiryModelArrayList.size();
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().replaceAll("\\s","").toLowerCase().trim();
                if (charString.isEmpty() || charSequence.equals("")) {
                    enquiryModelArrayList = arrayListFiltered;
                } else {
                    ArrayList<EnquiryModel> filteredList = new ArrayList<>();
                    for (int i = 0; i < enquiryModelArrayList.size(); i++) {

                        String serviceName=enquiryModelArrayList.get(i).getName().replaceAll("\\s","").toLowerCase().trim();
                        if (serviceName.contains(charString)) {
                            filteredList.add(enquiryModelArrayList.get(i));
                        }
                    }
                    if (filteredList.size() > 0) {
                        enquiryModelArrayList = filteredList;
                    } else {
                        enquiryModelArrayList = arrayListFiltered;
                    }

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = enquiryModelArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                enquiryModelArrayList = (ArrayList<EnquiryModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtDoctorName,txtProfile,txtReplayDate,txtReplayTime,txtReplay,txtQuestion;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDoctorName = itemView.findViewById(R.id.txtDoctorName);
            txtProfile = itemView.findViewById(R.id.txtProfile);
            txtReplayDate = itemView.findViewById(R.id.txtReplayDate);
            txtReplayTime = itemView.findViewById(R.id.txtReplayTime);
            txtReplay = itemView.findViewById(R.id.txtReplay);
            txtQuestion = itemView.findViewById(R.id.txtQuestion);

        }
    }
}
