package com.nikvay.saipooja_patient.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SuccessModel {
    private  String  error_code;
    private  String  msg;
    String img_base_url;

    public String getImg_base_url() {
        return img_base_url;
    }

    public void setImg_base_url(String img_base_url) {
        this.img_base_url = img_base_url;
    }


    @SerializedName("patient_login")
    ArrayList<PatientModel> patientModelArrayList;

    public ArrayList<GalleryModule> getGalleryModuleArrayList() {
        return galleryModuleArrayList;
    }

    public void setGalleryModuleArrayList(ArrayList<GalleryModule> galleryModuleArrayList) {
        this.galleryModuleArrayList = galleryModuleArrayList;
    }

    @SerializedName("gallery_list")
    ArrayList<GalleryModule> galleryModuleArrayList;

    @SerializedName("service")
    ArrayList<ServiceModel> serviceModelArrayList;

    @SerializedName("servicePatient")
    ArrayList<DoctorModel> doctorModelArrayList;

    @SerializedName("time")
    ArrayList<SelectDateTimeModel> selectDateTimeModelArrayList;

    @SerializedName("ragistrationdetail")
    ArrayList<PatientModel> ragistrationdetailModelArrayList;


    @SerializedName("classList")
    ArrayList<ClassModel>classListModelArrayList;

    @SerializedName("patientAptDetails")
    ArrayList<PatientAptdetailstListModel>patientAptdetailstListModelArrayList;

    @SerializedName("listpPaymentHistory")
    ArrayList<PaymentDetailModel>paymentDetailModelArrayList ;


    @SerializedName("listPrescription")
    ArrayList<PreScriptionModel>preSriptionDetailModelArrayList ;


    @SerializedName("listPrescriptiondate")
    ArrayList<PreScriptionDateModel>preScriptionDateModelArrayList ;

    @SerializedName("patientAptDate")
    ArrayList<AppoinmentDateFilterModel>appoinmentDateFilterModelArrayList ;


    @SerializedName("doctor_list")
    ArrayList<DoctorListModel>doctorListModelArrayList ;

    @SerializedName("video_list")
    ArrayList<VideoListModule>videoListModuleArrayList ;

    public ArrayList<EnquiryModel> getEnquiryModelArrayList() {
        return enquiryModelArrayList;
    }

    public void setEnquiryModelArrayList(ArrayList<EnquiryModel> enquiryModelArrayList) {
        this.enquiryModelArrayList = enquiryModelArrayList;
    }

    @SerializedName("list_enquiry")
    ArrayList<EnquiryModel> enquiryModelArrayList;

    public ArrayList<DocumentModel> getDocumentModelArrayList() {
        return documentModelArrayList;
    }

    public void setDocumentModelArrayList(ArrayList<DocumentModel> documentModelArrayList) {
        this.documentModelArrayList = documentModelArrayList;
    }

    @SerializedName("document_list")
    ArrayList<DocumentModel> documentModelArrayList;



    public ArrayList<VideoListModule> getVideoListModuleArrayList() {
        return videoListModuleArrayList;
    }

    public void setVideoListModuleArrayList(ArrayList<VideoListModule> videoListModuleArrayList) {
        this.videoListModuleArrayList = videoListModuleArrayList;
    }



    public ArrayList<DoctorListModel> getDoctorListModelArrayList() {
        return doctorListModelArrayList;
    }

    public void setDoctorListModelArrayList(ArrayList<DoctorListModel> doctorListModelArrayList) {
        this.doctorListModelArrayList = doctorListModelArrayList;
    }


    public ArrayList<AppoinmentDateFilterModel> getAppoinmentDateFilterModelArrayList() {
        return appoinmentDateFilterModelArrayList;
    }

    public void setAppoinmentDateFilterModelArrayList(ArrayList<AppoinmentDateFilterModel> appoinmentDateFilterModelArrayList) {
        this.appoinmentDateFilterModelArrayList = appoinmentDateFilterModelArrayList;
    }


    public ArrayList<PreScriptionDateModel> getPreScriptionDateModelArrayList() {
        return preScriptionDateModelArrayList;
    }

    public void setPreScriptionDateModelArrayList(ArrayList<PreScriptionDateModel> preScriptionDateModelArrayList) {
        this.preScriptionDateModelArrayList = preScriptionDateModelArrayList;
    }



    public ArrayList<PreScriptionModel> getPreSriptionDetailModelArrayList() {
        return preSriptionDetailModelArrayList;
    }

    public void setPreSriptionDetailModelArrayList(ArrayList<PreScriptionModel> preSriptionDetailModelArrayList) {
        this.preSriptionDetailModelArrayList = preSriptionDetailModelArrayList;
    }



    public ArrayList<PaymentDetailModel> getPaymentDetailModelArrayList() {
        return paymentDetailModelArrayList;
    }

    public void setPaymentDetailModelArrayList(ArrayList<PaymentDetailModel> paymentDetailModelArrayList) {
        this.paymentDetailModelArrayList = paymentDetailModelArrayList;
    }


    public ArrayList<PatientAptdetailstListModel> getPatientAptdetailstListModelArrayList() {
        return patientAptdetailstListModelArrayList;
    }

    public void setPatientAptdetailstListModelArrayList(ArrayList<PatientAptdetailstListModel> patientAptdetailstListModelArrayList) {
        this.patientAptdetailstListModelArrayList = patientAptdetailstListModelArrayList;
    }



    public ArrayList<ClassModel> getClassListModelArrayList() {
        return classListModelArrayList;
    }

    public void setClassListModelArrayList(ArrayList<ClassModel> classListModelArrayList) {
        this.classListModelArrayList = classListModelArrayList;
    }





    public ArrayList<PatientModel> getRagistrationdetailModelArrayList() {
        return ragistrationdetailModelArrayList;
    }

    public void setRagistrationdetailModelArrayList(ArrayList<PatientModel> ragistrationdetailModelArrayList) {
        this.ragistrationdetailModelArrayList = ragistrationdetailModelArrayList;
    }


    public ArrayList<SelectDateTimeModel> getSelectDateTimeModelArrayList() {
        return selectDateTimeModelArrayList;
    }

    public void setSelectDateTimeModelArrayList(ArrayList<SelectDateTimeModel> selectDateTimeModelArrayList) {
        this.selectDateTimeModelArrayList = selectDateTimeModelArrayList;
    }


    public ArrayList<DoctorModel> getDoctorModelArrayList() {
        return doctorModelArrayList;
    }

    public void setDoctorModelArrayList(ArrayList<DoctorModel> doctorModelArrayList) {
        this.doctorModelArrayList = doctorModelArrayList;
    }




    public ArrayList<ServiceModel> getServiceModelArrayList() {
        return serviceModelArrayList;
    }

    public void setServiceModelArrayList(ArrayList<ServiceModel> serviceModelArrayList) {
        this.serviceModelArrayList = serviceModelArrayList;
    }

    public SuccessModel(ArrayList<ServiceModel> serviceModelArrayList) {
        this.serviceModelArrayList = serviceModelArrayList;
    }


    public ArrayList<PatientModel> getPatientModelArrayList() {
        return patientModelArrayList;
    }

    public void setPatientModelArrayList(ArrayList<PatientModel> patientModelArrayList) {
        this.patientModelArrayList = patientModelArrayList;
    }



    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
