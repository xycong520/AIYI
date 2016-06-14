package com.xiuman.xinjiankang.bean;

import java.io.Serializable;

/**
 * 描述：
 * Created by hxy on 2016/3/4.
 */
public class DoctorService implements Serializable {

    /**
     * inquiryFee : 10.0
     * inquiryName : 图文问诊
     * doctorId : 4028e0e5533a54e201533a58ade30007
     * inquiryTypeId : a3c75fae301044b5bce3b6b4934da347
     * openStatus : true
     * inquiryType : inquiry_type
     */

    private double inquiryFee;
    private String inquiryName;
    private String doctorId;
    private String inquiryTypeId;
    private boolean openStatus;
    private String inquiryType;

    public void setInquiryFee(double inquiryFee) {
        this.inquiryFee = inquiryFee;
    }

    public void setInquiryName(String inquiryName) {
        this.inquiryName = inquiryName;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setInquiryTypeId(String inquiryTypeId) {
        this.inquiryTypeId = inquiryTypeId;
    }

    public void setOpenStatus(boolean openStatus) {
        this.openStatus = openStatus;
    }

    public void setInquiryType(String inquiryType) {
        this.inquiryType = inquiryType;
    }

    public double getInquiryFee() {
        return inquiryFee;
    }

    public String getInquiryName() {
        return inquiryName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getInquiryTypeId() {
        return inquiryTypeId;
    }

    public boolean isOpenStatus() {
        return openStatus;
    }

    public String getInquiryType() {
        return inquiryType;
    }
}
