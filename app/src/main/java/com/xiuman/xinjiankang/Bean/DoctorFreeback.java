package com.xiuman.xinjiankang.bean;

import java.io.Serializable;

/**
 * 描述：
 * Created by hxy on 2016/3/4.
 */
public class DoctorFreeback implements Serializable {

    /**
     * dictId : a565796518c743e5ab11fea0a0a8357f
     * doctorId : 4028e0e5533a54e201533a5646f60000
     * score : 43
     * feedbackName : 推荐指数
     * feedbackId : 4028e0e5533a54e201533a566ae90004
     */

    private String dictId;
    private String doctorId;
    private int score;
    private String feedbackName;
    private String feedbackId;

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setFeedbackName(String feedbackName) {
        this.feedbackName = feedbackName;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getDictId() {
        return dictId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public int getScore() {
        return score;
    }

    public String getFeedbackName() {
        return feedbackName;
    }

    public String getFeedbackId() {
        return feedbackId;
    }
}
