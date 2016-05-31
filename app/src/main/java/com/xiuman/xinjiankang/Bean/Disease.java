package com.xiuman.xinjiankang.bean;

import java.io.Serializable;

/**
 * 描述：相关案例
 * Created by hxy on 2015/9/25.
 */
public class Disease implements Serializable {
    private static final long serialVersionUID = -6757209579724220691L;

    /**
     * content : 发生龙啊的阿萨法单氯芬那酸的
     * sex : 女
     * age : 24
     * createDate : 1442645897000
     * consultingId : 402890234fcf1f1d014fcf9f71460001
     * userImg : null
     */

    private String content;
    //性别
    private String sex;
    //年龄
    private int age;
    //创建时间
    private long createDate;
    //案例id
    private String consultingId;
    //用户头像
    private String userImg;
    //医师头像
    private String doctorHead;
    //医师名称
    private String doctorName;

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDoctorHead() {
        return doctorHead;
    }

    public void setDoctorHead(String doctorHead) {
        this.doctorHead = doctorHead;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public void setConsultingId(String consultingId) {
        this.consultingId = consultingId;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getContent() {
        return content;
    }

    public String getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public long getCreateDate() {
        return createDate;
    }

    public String getConsultingId() {
        return consultingId;
    }

    public String getUserImg() {
        return userImg;
    }
}
