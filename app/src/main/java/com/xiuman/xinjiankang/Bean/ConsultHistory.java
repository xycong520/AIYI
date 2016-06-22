package com.xiuman.xinjiankang.bean;

import java.io.Serializable;

/**
 * 描述：咨询历史
 * Created by hxy on 2015/9/28.
 */
public class ConsultHistory implements Serializable {

    /**
     * content : 考虑图
     * id : 8a04bc8b5015233601501547d5670046
     * sex : true
     * status : 0
     * age : 22
     * counselType : 2
     * doctorSex : true
     * orderDuration : 0
     * doctorName : 黄秀敏
     * createDate : 2015-09-29 02:47:20.0
     * avatar : null
     */
    private String content;
    //医师名称
    private String doctorUser;
    //
    private String id;
    //性别
    private boolean sex;
    //状态
    private String status;
    //年龄
    private int age;
    //咨询类别
    private int counselType;
    //医师性别
    private boolean doctorSex;
    //订单时间
    private int orderDuration;
    //医师名称
    private String doctorName;
    //创建时间
    private String createDate;
    //头像
    private String avatar;
    //医师ID
    private String doctorId;
    //用户名
    private String username;

    public String getDoctorUser() {
        return doctorUser;
    }

    public void setDoctorUser(String doctorUser) {
        this.doctorUser = doctorUser;
    }

    public boolean isDoctorSex() {
        return doctorSex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCounselType(int counselType) {
        this.counselType = counselType;
    }

    public void setDoctorSex(boolean doctorSex) {
        this.doctorSex = doctorSex;
    }

    public void setOrderDuration(int orderDuration) {
        this.orderDuration = orderDuration;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public boolean isSex() {
        return sex;
    }

    public String getStatus() {
        return status!=null?status:"0";
    }

    public int getAge() {
        return age;
    }

    public int getCounselType() {
        return counselType;
    }

    public boolean getDoctorSex() {
        return doctorSex;
    }

    public int getOrderDuration() {
        return orderDuration;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getAvatar() {
        return avatar;
    }
}
