package com.nikvay.saipooja_patient.apiCallCommen;

import com.nikvay.saipooja_patient.model.SuccessModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST(EndApi.PATIENT_REGISTRATION)
    @FormUrlEncoded
    Call<SuccessModel> newPatientRegistration(
                                     @Field("name") String name,
                                     @Field("email") String email,
                                     @Field("address") String address,
                                     @Field("phone_no") String phone_no,
                                     @Field("password") String password,
                                     @Field("gender") String gender,
                                     @Field("age") String age);

    @POST(EndApi.LOGIN)
    @FormUrlEncoded
    Call<SuccessModel> loginCall(@Field("email") String email,
                                 @Field("password") String password,
                                 @Field("device_token") String device_token
                                 );

    @POST(EndApi.APPOINTMENT_TIME_SLOT)
    @FormUrlEncoded
    Call<SuccessModel> appointmentTimeSlot(@Field("dayStatus") String dayStatusCode,
                                            @Field("doctor_id") String doctor_id,
                                           @Field("date") String date,
                                           @Field("user_id") String user_id);

    @POST(EndApi.ADD_NEW_ENQUIRY)
    @FormUrlEncoded
    Call<SuccessModel> addNewEnquiry(@Field("doctor_id") String doctor_id,
                                           @Field("patient_id") String patient_id,
                                           @Field("title") String title,
                                           @Field("description") String description);


    @POST(EndApi.DOCTORWISESERVICElIST)
    @FormUrlEncoded
    Call<SuccessModel> doctorsSrviceList(@Field("service_id") String service_id);


    @POST(EndApi.ENQUIRY_LIST)
    @FormUrlEncoded
    Call<SuccessModel> enquiryList(@Field("patient_id") String patient_id);


    @POST(EndApi.SERVICElIST)
    Call<SuccessModel> serviceList();


    @POST(EndApi.DOCTORlIST)
    Call<SuccessModel> doctorList();

    @POST(EndApi.PATIENTWISEAPPOINMENT)
    @FormUrlEncoded
    Call<SuccessModel> addAppointment(@Field("doctor_id") String doctor_id,
                                      @Field("user_id") String user_id,
                                      @Field("service_id") String service_id,
                                      @Field("patient_id") String patient_id,
                                      @Field("date") String date,
                                      @Field("time") String time,
                                      @Field("comment") String comment,
                                      @Field("label") String label,
                                      @Field("notification_type") String notification_type
                                      );

    @POST(EndApi.CLASSlIST)
    Call<SuccessModel> classList();

    @POST(EndApi.LIST_APPOINTMENT)
    @FormUrlEncoded
    Call<SuccessModel> patientAptDetails(@Field("patient_id") String patient_id);


    @POST(EndApi.LIST_PAYMENT)
    @FormUrlEncoded
    Call<SuccessModel> patientPaymentDetails(@Field("patient_id") String patient_id,
                                             @Field("date")  String date );


    @POST(EndApi.LIST_DATE_PRESCRIPTION)
    @FormUrlEncoded
    Call<SuccessModel> preScriptionDateDetail(@Field("patient_id") String patient_id,
                                              @Field("date")  String date );


    @POST(EndApi.LIST_PRESCRIPTION)
    @FormUrlEncoded
    Call<SuccessModel> preScriptionDetail(@Field("patient_id") String patient_id,
                                          @Field("date")  String date );

    @POST(EndApi.LIST_DATE_APPOINMENT)
    @FormUrlEncoded
    Call<SuccessModel> patientAptDateFilterDetails(@Field("patient_id") String patient_id,
                                              @Field("date")  String date );

    @POST(EndApi.CHANGEPASSWORD)
    @FormUrlEncoded
    Call<SuccessModel> changePassword(@Field("patient_id") String patient_id,
                                      @Field("email") String email,
                                      @Field("old_password") String old_password,
                                     @Field("new_password") String new_password  );

    @GET(EndApi.VIDEO_TUTORIALS)
    Call<SuccessModel> videoTutorialsListCall();

    @GET(EndApi.PHOTO_Gallery_LIST)
    Call<SuccessModel> galleryListCall();

    @POST(EndApi.DOCUMENT_PHOTO)
    @FormUrlEncoded
    Call<SuccessModel> documentList(@Field("patient_id") String patient_id);



}
