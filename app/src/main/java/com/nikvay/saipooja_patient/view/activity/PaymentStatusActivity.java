package com.nikvay.saipooja_patient.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.model.PaymentDetailModel;
import com.nikvay.saipooja_patient.utils.StaticContent;

public class PaymentStatusActivity extends AppCompatActivity {


    PaymentDetailModel PaymentDetailModel;
    TextView textServiceName,txtHospitalCharges,textServiceCost,textDoctorName,textDate,textTime,txtComment,txtTotalPayment;
    String date,time,service_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_status);

        find_All_Id();
       // event();
    }

    private void event() {
    }

    private void find_All_Id() {

        textServiceName = findViewById(R.id.textServiceName);
        textDoctorName = findViewById(R.id.textDoctorName);
        txtComment = findViewById(R.id.txtComment);
        textServiceCost = findViewById(R.id.textServiceCost);
        txtHospitalCharges = findViewById(R.id.txtHospitalCharges);
        textTime = findViewById(R.id.textTime);
        textDate = findViewById(R.id.textDate);
        textServiceName = findViewById(R.id.textServiceName);
        txtTotalPayment = findViewById(R.id.txtTotalPayment);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            PaymentDetailModel= (PaymentDetailModel) bundle.getSerializable(StaticContent.IntentKey.APPOINMENT_DETAIL);

            textServiceName.setText(PaymentDetailModel.getS_name());
            textServiceCost.setText(PaymentDetailModel.getService_cost());
            txtComment .setText(PaymentDetailModel.getComment_payment());
            textDoctorName.setText(PaymentDetailModel.getName());
            txtHospitalCharges.setText(PaymentDetailModel.getHospital_charges());
            textDate.setText(PaymentDetailModel.getDate());
            txtTotalPayment.setText(PaymentDetailModel.getTotal_amount());

        }
    }
}
