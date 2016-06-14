package com.xiuman.xinjiankang.bean;

import java.io.Serializable;

/**
 * 描述：医师
 * Created by hxy on 2015/8/26.
 */
public class Doctor implements Serializable {

    private static final long serialVersionUID = -2552542794610573155L;
    /**
     * distance : 191.70179620161989
     * area : 福建省厦门市
     * doctorId : 402890eb4f3e8a83014f3ee8fb340009
     * name : 测试医生
     * doctorPost : 职称1
     * introduce : 测试
     * lng : 120
     * headimgurl : http://7xk6ko.com2.z0.glb.qiniucdn.com/20150818114458491
     * lat : 24
     * popularity : 90
     */
    //距离
    private String distance;
    //地点
    private String area;
    //医师id
    private String doctorId;
    //医师姓名
    private String name;
    //医师职称
    private String doctorPost;
    //医师简介
    private String introduce;
    private String lng;
    //医师头像
    private String headimgurl;
    private String lat;
    //医师热度
    private int popularity;
    //医师用户名
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDoctorPost(String doctorPost) {
        this.doctorPost = doctorPost;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getDistance() {
        return distance;
    }

    public String getArea() {
        return area;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getName() {
        return name;
    }

    public String getDoctorPost() {
        return doctorPost;
    }

    public String getIntroduce() {
        return introduce;
    }

    public String getLng() {
        return lng;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public String getLat() {
        return lat;
    }

    public int getPopularity() {
        return popularity;
    }
}
