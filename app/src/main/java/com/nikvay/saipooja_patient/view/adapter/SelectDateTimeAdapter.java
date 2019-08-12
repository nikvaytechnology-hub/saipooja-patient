package com.nikvay.saipooja_patient.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.model.DoctorModel;
import com.nikvay.saipooja_patient.model.SelectDateTimeModel;
import com.nikvay.saipooja_patient.model.ServiceModel;
import com.nikvay.saipooja_patient.utils.ErrorMessageDialog;
import com.nikvay.saipooja_patient.utils.StaticContent;
import com.nikvay.saipooja_patient.view.activity.PatientAptmentDetailsActivity;

import java.util.ArrayList;

public class SelectDateTimeAdapter extends RecyclerView.Adapter<SelectDateTimeAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<SelectDateTimeModel> selectDateTimeModelArrayList;
    private ServiceModel serviceModel;
    private String date;
    DoctorModel doctorModel;
    private ErrorMessageDialog errorMessageDialog;

    public SelectDateTimeAdapter(Context mContext, ArrayList<SelectDateTimeModel> selectDateTimeModelArrayList,
                                 ServiceModel serviceModel, DoctorModel doctorModel, String date) {
        this.mContext=mContext;
        this.selectDateTimeModelArrayList=selectDateTimeModelArrayList;
        this.serviceModel = serviceModel;
        this.date = date;
        this.doctorModel = doctorModel;
        errorMessageDialog = new ErrorMessageDialog(mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_time_adapter,viewGroup,false);
        return new SelectDateTimeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final SelectDateTimeModel selectDateTimeModel=selectDateTimeModelArrayList.get(position);


         if (selectDateTimeModel.getStatus().equals("1")) {
             holder.cardViewTime.setBackgroundColor(mContext.getResources().getColor(R.color.app_color));
        } else {
            holder.cardViewTime.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

        holder.textTime.setText(selectDateTimeModel.getTime());

        holder.cardViewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectDateTimeModel.getStatus().equals("1")) {
                    errorMessageDialog.showDialog("This slot is Already Booked");
                }
                else
                {
                    Intent intent = new Intent(mContext, PatientAptmentDetailsActivity.class);
                    intent.putExtra(StaticContent.IntentKey.SERVICE_DETAIL,serviceModel);
                    intent.putExtra(StaticContent.IntentKey.TIME,holder.textTime.getText());
                    intent.putExtra(StaticContent.IntentKey.DATE,date);
                    intent.putExtra(StaticContent.IntentKey.DOCTOR_DETAIL,doctorModel);
                    intent.putExtra(StaticContent.IntentValue.APPOINTMENT, StaticContent.IntentValue.APPOINTMENT);
                    mContext.startActivity(intent);
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return selectDateTimeModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

               TextView textTime;
                CardView cardViewTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textTime=itemView.findViewById(R.id.textTime);
            textTime=itemView.findViewById(R.id.textTime);
            cardViewTime=itemView.findViewById(R.id.cardViewTime);
        }
    }
}
