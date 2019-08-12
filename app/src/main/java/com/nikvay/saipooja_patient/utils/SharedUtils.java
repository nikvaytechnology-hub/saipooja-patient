package com.nikvay.saipooja_patient.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.nikvay.saipooja_patient.model.PatientModel;

import java.util.ArrayList;

public class SharedUtils {


    private static final String IS_LOGIN="IS_LOGIN";
    private static final String MY_PREFERENCE="PATIENT_APPLICATION";
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static String DEVICE_TOKEN = "DEVICE_TOKEN";


    public static String getSharedUtils(Context mContext)
    {
        preferences=mContext.getSharedPreferences(MY_PREFERENCE,mContext.MODE_PRIVATE);
        return preferences.getString(IS_LOGIN,"");
    }

    public static void putSharedUtils(Context mContext) {
        preferences = mContext.getSharedPreferences(MY_PREFERENCE, mContext.MODE_PRIVATE);
        editor= preferences.edit();
        editor.putString(IS_LOGIN, "true");
        editor.commit();
    }

    public static void removeSharedUtils(Context mContext)
    {
        preferences = mContext.getSharedPreferences(MY_PREFERENCE, mContext.MODE_PRIVATE);
        editor= preferences.edit();
        editor.putString(IS_LOGIN, "false");
        editor.commit();
    }

    public static void putDeviceToken(Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(StaticContent.DeviceToken.DEVICE_TOKEN, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(DEVICE_TOKEN, token)
                .apply();
    }

    public static String getDeviceToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(StaticContent.DeviceToken.DEVICE_TOKEN, Context.MODE_PRIVATE);
        return sharedPreferences.getString(DEVICE_TOKEN, "");
    }

    public static void addUserUtils(Context mContext, ArrayList<PatientModel> doctorModelArrayList) {

        preferences = mContext.getSharedPreferences(StaticContent.UserData.MY_PREFERENCE, mContext.MODE_PRIVATE);
        editor = preferences.edit();

        editor.putString(StaticContent.UserData.PATIENT_ID,doctorModelArrayList.get(0).getPatient_id());
        editor.putString(StaticContent.UserData.NAME, doctorModelArrayList.get(0).getName());
        editor.putString(StaticContent.UserData.EMAIL, doctorModelArrayList.get(0).getEmail());
        editor.putString(StaticContent.UserData.ADDRESS, doctorModelArrayList.get(0).getAddress());
        editor.putString(StaticContent.UserData.PHONE_NO, doctorModelArrayList.get(0).getPhone_no());
       // editor.putString(StaticContent.UserData.PROFILE, doctorModelArrayList.get(0).getProfile());

        editor.commit();
    }

    public static ArrayList<PatientModel> getUserDetails(Context mContext) {
        ArrayList<PatientModel> userDetailsModuleArrayList = new ArrayList<>();
        PatientModel patientModel = new PatientModel();
        preferences = mContext.getSharedPreferences(StaticContent.UserData.MY_PREFERENCE, mContext.MODE_PRIVATE);
        patientModel.setPatient_id(preferences.getString(StaticContent.UserData.PATIENT_ID, ""));
        patientModel.setName(preferences.getString(StaticContent.UserData.NAME, ""));
        patientModel.setEmail(preferences.getString(StaticContent.UserData.EMAIL, ""));
        patientModel.setAddress(preferences.getString(StaticContent.UserData.ADDRESS, ""));
        patientModel.setPhone_no(preferences.getString(StaticContent.UserData.PHONE_NO, ""));
      //  patientModel.setProfile(preferences.getString(StaticContent.UserData.PROFILE, ""));

        userDetailsModuleArrayList.add(patientModel);
        return userDetailsModuleArrayList;
    }
}
