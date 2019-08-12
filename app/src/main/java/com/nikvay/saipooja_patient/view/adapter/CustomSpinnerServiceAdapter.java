package com.nikvay.saipooja_patient.view.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikvay.saipooja_patient.Interface.SelectDoctorInterface;
import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.model.ServiceModel;

import java.util.ArrayList;

public class CustomSpinnerServiceAdapter extends ArrayAdapter<ServiceModel> {

    private Context context;
    private ArrayList<ServiceModel> data;
    public Resources res;
    ServiceModel serviceModel =null;
    LayoutInflater inflater;
    CardView card_view;
    private SelectDoctorInterface selectServiceId;
    String service_id;
    public CustomSpinnerServiceAdapter(Context mContext, int textViewResourceId, ArrayList<ServiceModel> serviceNameArrayList, Resources res)
    {
        super(mContext, textViewResourceId, serviceNameArrayList);


        /********** Take passed values **********/
        context = mContext;
        data     = serviceNameArrayList;
        res      = res;
        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(final int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.activity_spinner_custom_adapter, parent, false);

        /***** Get each Model object from Arraylist ********/
        serviceModel = null;
        serviceModel = (ServiceModel) data.get(position);

        TextView ServiceName        = (TextView)view.findViewById(R.id.textServiceName);
        TextView ServiceDesc          = (TextView)view.findViewById(R.id.textServiceDesc);
         card_view = view.findViewById(R.id.card_view);

        ImageView companyLogo = (ImageView)view.findViewById(R.id.image);

        if(position==0){

            card_view.setVisibility(View.GONE);
            // Default selected Spinner item
            ServiceName.setText("Please select Service");
            ServiceDesc.setText("");
        }
        else
        {
            // Set values for spinner each row
            ServiceName.setText(serviceModel.getS_name());
            ServiceDesc.setText(serviceModel.getDescription());


            card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    selectServiceId = (SelectDoctorInterface) context;

                    service_id =data.get(position).getService_id();

                    selectServiceId.selectedServiceId(service_id);
                }
            });
        }

        return  view;
    }

}
