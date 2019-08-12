package com.nikvay.saipooja_patient.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.model.PrescriptionListModel;

import java.util.ArrayList;

public class PreScriptionTableChildAdapter extends RecyclerView.Adapter<PreScriptionTableChildAdapter.MyViewHolder> {

    ArrayList<PrescriptionListModel> prescriptionListModelArrayList;

    int count=0;


    public PreScriptionTableChildAdapter(ArrayList<PrescriptionListModel> prescriptionListModelArrayList) {
        this.prescriptionListModelArrayList = prescriptionListModelArrayList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_item_test_table_child_adapter, parent, false);

        return new PreScriptionTableChildAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final PrescriptionListModel prescriptionListModel = prescriptionListModelArrayList.get(position);

            String testName = prescriptionListModel.getTest_name();
            String testNote = prescriptionListModel.getTest_note();
            String medecineName = prescriptionListModel.getMedication_name();
            String medecineNote = prescriptionListModel.getMedication_note();

            count=count+1;
            String totalCount= String.valueOf(count);

            holder.tv_result_id.setText(totalCount);
            holder.tv_sub_test_name.setText(testName);
         //   holder.tv_sub_mark_one.setText(medecineName);
          //  holder.tv_sub_medicene_note.setText(medecineNote);
            holder.tv_sub_test_note.setText(testNote);
        holder.ll_medicine_table.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return prescriptionListModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_result_id, tv_sub_test_name, tv_sub_medicine_name, tv_sub_medicene_note,tv_sub_test_note;
        LinearLayout ll_medicine_table;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_result_id = itemView.findViewById(R.id.tv_result_id);
            ll_medicine_table = itemView.findViewById(R.id.ll_medicine_table);

            tv_sub_test_name = itemView.findViewById(R.id.tv_sub_test_name);
            tv_sub_medicine_name = itemView.findViewById(R.id.tv_sub_medicine_name);
            tv_sub_medicene_note = itemView.findViewById(R.id.tv_sub_medicene_note);
            tv_sub_test_note = itemView.findViewById(R.id.tv_sub_test_note);
        }
    }
}
