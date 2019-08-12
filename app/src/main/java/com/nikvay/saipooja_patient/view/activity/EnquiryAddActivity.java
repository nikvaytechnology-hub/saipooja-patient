package com.nikvay.saipooja_patient.view.activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.nikvay.saipooja_patient.Interface.SelectDoctorInterface;
import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.apiCallCommen.ApiClient;
import com.nikvay.saipooja_patient.apiCallCommen.ApiInterface;
import com.nikvay.saipooja_patient.model.DoctorListModel;
import com.nikvay.saipooja_patient.model.PatientModel;
import com.nikvay.saipooja_patient.model.SuccessModel;
import com.nikvay.saipooja_patient.utils.AppointmentDialog;
import com.nikvay.saipooja_patient.utils.ErrorMessageDialog;
import com.nikvay.saipooja_patient.utils.SharedUtils;
import com.nikvay.saipooja_patient.utils.SuccessMessageDialog;
import com.nikvay.saipooja_patient.view.adapter.MyDoctorDialogAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnquiryAddActivity extends AppCompatActivity implements SelectDoctorInterface {

    EditText edt_desc_add_message,edt_title_name,editSearchDoctor;
    Button btn_enquiry_save,btnOkDialogDoctor,btnCancelDialogDoctor;
    ApiInterface apiInterface;

    private ErrorMessageDialog errorMessageDialog;
    private SuccessMessageDialog successMessageDialog;
    String TAG = getClass().getSimpleName(),doctorName, doctor_id,description,title;
    Dialog selectDoctorDialog;
    private ArrayList<DoctorListModel> doctorListModelArrayList;
    MyDoctorDialogAdapter myDoctorDialogAdapter;
    public static DoctorListModel doctorListModel = null;
    RecyclerView recyclerDialogDoctor;
    AutoCompleteTextView txtDoctorName;
    LinearLayout linearDoctor;
    ImageView iv_doctorName;
    private AppointmentDialog appointmentDialog;

    ArrayList<PatientModel> patientModelArrayList = new ArrayList<>();
    String patient_id;

    SelectDoctorInterface  selectDoctorInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry_add);

        find_All_Id();
        event();
    }

    private void event()
    {
        btn_enquiry_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid())
                {
                    callAddEnquiry(doctor_id,patient_id,title,description);
                }
            }
        });
        iv_doctorName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            callDoctorList();
            }
        });
        btnOkDialogDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearchDoctor.setText("");
                selectDoctorDialog.dismiss();
                if (doctorListModel != null) {
                    setDoctorData();
                    if (doctorListModelArrayList.size() > 0) {
                        /*editDiscountQuantity.setText("");
                        setDiscountData(mTotalAmount, mDiscountLimit, true);*/
                    }
                    linearDoctor.setVisibility(View.VISIBLE);
                } else {
                    //   clearDiscountData();
                    linearDoctor.setVisibility(View.GONE);
                }

            }
        });
        btnCancelDialogDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorListModel = null;
                linearDoctor.setVisibility(View.GONE);
                editSearchDoctor.setText("");
                selectDoctorDialog.dismiss();
            }
        });
    }

    private void callAddEnquiry(String doctor_id, String patient_id, String title, String description)
    {

        Call<SuccessModel> call = apiInterface.addNewEnquiry(doctor_id,patient_id,title,description);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {

                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" +   str_response);

                try {
                    if (response.isSuccessful()) {

                        SuccessModel registrationModel = (SuccessModel) response.body();
                        String message = null, code = null;

                        if (registrationModel != null) {
                            message = registrationModel.getMsg();
                            code = registrationModel.getError_code();


                            if (code.equalsIgnoreCase("1")) {

                                appointmentDialog.showDialog("Add Enquiry Successfully !");

                            } else {
                                errorMessageDialog.showDialog("Wrong Enquiry");
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {

            }
        });

    }


    private boolean isValid()
    {
         title = edt_title_name.getText().toString().trim();
         description =edt_desc_add_message.getText().toString().trim();

        if(title.equalsIgnoreCase(""))
        {
            errorMessageDialog.showDialog("Title ID Can't Be Empty");
            edt_title_name.requestFocus();
            // SnackbarCommon.displayValidation(v,"Email ID Can't Be Empty");
            return  false;
        }
        else if(description.equalsIgnoreCase(""))
        {
            errorMessageDialog.showDialog("Description ID Can't Be Empty");
            edt_desc_add_message.requestFocus();
            // SnackbarCommon.displayValidation(v,"Email ID Can't Be Empty");
            return  false;
        }

        return true;
    }

    private void find_All_Id()
    {
        edt_desc_add_message = findViewById(R.id.edt_desc_add_message);
        edt_title_name = findViewById(R.id.edt_title_name);
        btn_enquiry_save = findViewById(R.id.btn_enquiry_save);
        linearDoctor = findViewById(R.id.linearDoctor);
        txtDoctorName = findViewById(R.id.txtDoctorName);
        iv_doctorName = findViewById(R.id.iv_doctorName);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        selectDoctorDialog = new Dialog(this);
        selectDoctorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        selectDoctorDialog.setContentView(R.layout.dialog_select_doctor);
        btnOkDialogDoctor = selectDoctorDialog.findViewById(R.id.btnOkDialogDoctor);
        btnCancelDialogDoctor =  selectDoctorDialog.findViewById(R.id.btnCancelDialogDoctor);
        editSearchDoctor =  selectDoctorDialog.findViewById(R.id.editSearchDoctor);
        selectDoctorDialog.setCancelable(false);
        recyclerDialogDoctor = selectDoctorDialog.findViewById(R.id.recyclerDialogDoctor);
        LinearLayoutManager layoutManagerService = new LinearLayoutManager(getApplicationContext());
        recyclerDialogDoctor.setLayoutManager(layoutManagerService);

        errorMessageDialog = new ErrorMessageDialog(EnquiryAddActivity.this);
        successMessageDialog = new SuccessMessageDialog(EnquiryAddActivity.this);
        appointmentDialog= new AppointmentDialog(EnquiryAddActivity.this);

        patientModelArrayList = SharedUtils.getUserDetails(EnquiryAddActivity.this);
        patient_id = patientModelArrayList.get(0).getPatient_id();




    }

    private void setDoctorData() {

        doctorName = doctorListModel.getName();
        doctor_id = doctorListModel.getDoctor_id();
        txtDoctorName.setText(doctorName);
    }

    private void callDoctorList()
    {
        Call<SuccessModel> call = apiInterface.doctorList();

        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {

                //  showProgress.dismissDialog();
                String str_response = new Gson().toJson(response.body());
                Log.e("" + TAG, "Response >>>>" + str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModel successModel = response.body();
                        String message = null, code = null;
                        if (successModel != null) {
                            message = successModel.getMsg();
                            code = successModel.getError_code();


                            if (code.equalsIgnoreCase("1")) {

                                doctorListModelArrayList = successModel.getDoctorListModelArrayList();

                                if (doctorListModelArrayList.size() != 0) {

                                    myDoctorDialogAdapter = new MyDoctorDialogAdapter(getApplicationContext(), doctorListModelArrayList, true,selectDoctorInterface  );

                                    selectDoctorDialog.show();
                                    Window window = selectDoctorDialog.getWindow();
                                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    recyclerDialogDoctor.setAdapter(myDoctorDialogAdapter);
                                    myDoctorDialogAdapter.notifyDataSetChanged();

                                    // recyclerPatientList.addItemDecoration(new DividerItemDecoration(PatientActivity.this, DividerItemDecoration.VERTICAL));
                                } else {
                                    recyclerDialogDoctor.setVisibility(View.VISIBLE);
                                }

                            } else {
                                errorMessageDialog.showDialog("Response Not Working");
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void selectedServiceId(String mID) {

    }

    @Override
    public void selecteddoctorName(DoctorListModel doctorListModel)
    {

    }
}
