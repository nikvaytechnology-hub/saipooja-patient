package com.nikvay.saipooja_patient.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nikvay.saipooja_patient.Interface.SelectDoctorInterface;
import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.model.DoctorListModel;
import com.nikvay.saipooja_patient.view.activity.EnquiryAddActivity;

import java.util.ArrayList;

 public class MyDoctorDialogAdapter extends RecyclerView.Adapter<MyDoctorDialogAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<DoctorListModel> doctorListModelArrayList;
    private boolean isDialog;
    private DoctorListModel shareModel;
    private SelectDoctorInterface selectDoctorInterface;
    View view;
     private boolean isNameSelect = false;

     public MyDoctorDialogAdapter(Context mContext, ArrayList<DoctorListModel> doctorListModelArrayList,
                                  boolean isDialog, SelectDoctorInterface selectDoctorInterface)
     {
         this.doctorListModelArrayList = doctorListModelArrayList;
         this.mContext = mContext;
         this.selectDoctorInterface = selectDoctorInterface;
         this.isNameSelect = false;
         this.isDialog = isDialog;
     }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_find_doctor_adapetr,parent,false);
        return new MyDoctorDialogAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        DoctorListModel txtDoctorName = doctorListModelArrayList.get(position);

        if (doctorListModelArrayList.get(position).isSelected()) {
            holder.ll_doctorDetail.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_green_light));
        } else {
            holder.ll_doctorDetail.setBackgroundColor(mContext.getResources().getColor(R.color.cardview_light_background));
        }

        holder.txtDoctorName.setText(txtDoctorName.getName());
        holder.txtEmail.setText(txtDoctorName.getEmail());
        holder.txthospitalName.setText(txtDoctorName.getHospital_name());
        holder.txtPhoneNo.setText(txtDoctorName.getPhone_no());
        holder.txtProfile.setText(txtDoctorName.getProfile());
        holder.txtAddress.setText(txtDoctorName.getAddress());

        holder.ll_doctorDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNameSelect) {
                    if (!doctorListModelArrayList.get(position).isSelected()) {
                        for (int i = 0; i < doctorListModelArrayList.size(); i++) {
                            doctorListModelArrayList.get(i).setSelected(false);
                        }
                        doctorListModelArrayList.get(position).setSelected(true);
                        shareModel = doctorListModelArrayList.get(position);
                        EnquiryAddActivity.doctorListModel = shareModel;
                    } else {
                        EnquiryAddActivity.doctorListModel =  null;
                        doctorListModelArrayList.get(position).setSelected(false);
                    }
                    notifyDataSetChanged();
                } else {
                    if (!doctorListModelArrayList.get(position).isSelected()) {
                        for (int i = 0; i < doctorListModelArrayList.size(); i++) {
                            doctorListModelArrayList.get(i).setSelected(false);
                        }
                        doctorListModelArrayList.get(position).setSelected(true);
                        selectDoctorInterface.selecteddoctorName(doctorListModelArrayList.get(position));
                    } else {
                        doctorListModelArrayList.get(position).setSelected(false);
                    }
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctorListModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtDoctorName,txtEmail,txthospitalName,txtProfile,txtAddress,txtPhoneNo;
        LinearLayout ll_doctorDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDoctorName = view.findViewById(R.id.txtDoctorName);
            txtEmail = view.findViewById(R.id.txtEmail);
            txthospitalName = view.findViewById(R.id.txthospitalName);
            txtProfile = view.findViewById(R.id.txtProfile);
            txtAddress = view.findViewById(R.id.txtAddress);
            txtPhoneNo = view.findViewById(R.id.txtPhoneNo);
            ll_doctorDetail = view.findViewById(R.id.ll_doctorDetail);
        }

       /* public ViewHolder(@NonNull View view) {
            super(view);

                txtDoctorName = view.findViewById(R.id.txtDoctorName);
                txtEmail = view.findViewById(R.id.txtEmail);
                txthospitalName = view.findViewById(R.id.txthospitalName);
                txtProfile = view.findViewById(R.id.txtProfile);
                txtAddress = view.findViewById(R.id.txtAddress);
                txtPhoneNo = view.findViewById(R.id.txtPhoneNo);
            ll_doctorDetail = view.findViewById(R.id.ll_doctorDetail);

        }*/
    }
}
