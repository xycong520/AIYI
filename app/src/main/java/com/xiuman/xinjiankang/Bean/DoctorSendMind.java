package com.xiuman.xinjiankang.bean;

import java.io.Serializable;

/**
 * 描述：送心意列表
 * Created by hxy on 2016/3/4.
 */
public class DoctorSendMind implements Serializable {


    /**
     * thankcontent : null
     * id : 00000000537fdb0401537fe6a7ba0016
     * doctorid : 402890084f73150b014f73bbee810064
     * nickname : helloword
     * createDate : 2016-03-16
     * rewardamount : 0.0
     */

    private String thankcontent;
    private String id;
    private String doctorid;
    private String nickname;
    private String createDate;
    private double rewardamount;

    public void setThankcontent(String thankcontent) {
        this.thankcontent = thankcontent;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setRewardamount(double rewardamount) {
        this.rewardamount = rewardamount;
    }

    public String getThankcontent() {
        return thankcontent;
    }

    public String getId() {
        return id;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public String getNickname() {
        return nickname;
    }

    public String getCreateDate() {
        return createDate;
    }

    public double getRewardamount() {
        return rewardamount;
    }
}
