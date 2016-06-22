package com.xiuman.xinjiankang.bean;

import java.io.Serializable;

/**
 * 描述：VIP列表
 * Created by hxy on 2015/9/28.
 */
public class VipList implements Serializable {

    /**
     * content : asdfsdfasdf
     * sex : 男
     * doctorHead : http://7xl8y7.com2.z0.glb.qiniucdn.com/20150828171341359
     * age : 32
     * doctorName : 黄秀敏
     * createDate : 1442297377000
     * consultingId : 402890234fcf1f1d014fcf9f71460000
     */

    private String content;
    //性别
    private String sex;
    //医师头像
    private String doctorHead;
    //年龄
    private int age;
    //医师姓名
    private String doctorName;
    //创建时间
    private long createDate;
    //VIP咨询id
    private String consultingId;

    public void setContent(String content) {
        this.content = content;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setDoctorHead(String doctorHead) {
        this.doctorHead = doctorHead;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public void setConsultingId(String consultingId) {
        this.consultingId = consultingId;
    }

    public String getContent() {
        return content;
    }

    public String getSex() {
        return sex;
    }

    public String getDoctorHead() {
        return doctorHead;
    }

    public int getAge() {
        return age;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public long getCreateDate() {
        return createDate;
    }

    public String getConsultingId() {
        return consultingId;
    }
}
