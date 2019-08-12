package com.nikvay.saipooja_patient.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.model.ClassModel;
import com.nikvay.saipooja_patient.utils.StaticContent;

public class ClassListDetailActivity extends AppCompatActivity {


    ClassModel classModel;
    String mTitle= "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list_detail);

        find_All_Id();
        event();


    }

    private void event()
    {

    }

    private void find_All_Id()
    {


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            classModel= (ClassModel) bundle.getSerializable(StaticContent.IntentKey.CLASS_DETAIL);
            mTitle = bundle.getString(StaticContent.IntentKey.ACTIVITY_TYPE);
        }

    }
}
