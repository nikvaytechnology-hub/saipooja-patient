package com.nikvay.saipooja_patient.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.model.ClassModel;
import com.nikvay.saipooja_patient.utils.StaticContent;
import com.nikvay.saipooja_patient.view.activity.ClassListDetailActivity;

import java.util.ArrayList;

public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.MyViewHolder> {

    ArrayList<ClassModel>classModelArrayList;
    Context mContext;
    public ClassListAdapter(Context context, ArrayList<ClassModel> classModelArrayList)
    {
       this.classModelArrayList = classModelArrayList;
        this.mContext =context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_list_adapter,parent,false);
        return new ClassListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        ClassModel classModel = classModelArrayList.get(position);

        holder.textClassName.setText(classModel.getName());
        holder.textCost.setText(classModel.getCost());
        holder.textDuration.setText(classModel.getDuration());

        holder.relativeClassList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ClassListDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(StaticContent.IntentKey.CLASS_DETAIL, classModelArrayList.get(position));
                intent.putExtra(StaticContent.IntentKey.ACTIVITY_TYPE, StaticContent.IntentValue.ACTIVITY_CLASS_DETAILS);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return classModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textClassName,textCost,textDuration;
        RelativeLayout relativeClassList;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textClassName = itemView.findViewById(R.id.textClassName);
            textCost =itemView.findViewById(R.id.textCost);
            textDuration = itemView.findViewById(R.id.textDuration);
            relativeClassList = itemView.findViewById(R.id.relativeClassList);

        }
    }
}
