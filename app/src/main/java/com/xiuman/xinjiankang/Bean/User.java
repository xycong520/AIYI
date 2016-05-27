package com.xiuman.xinjiankang.Bean;

import com.xiuman.xinjiankang.app.AppConfig;

import java.io.Serializable;

/**
 * 描述：
 * Created by hxy on 2015/9/21.
 */
public class User implements Serializable {

    private static final long serialVersionUID = -949042743802561116L;
    // 用户名
    private String userName;
    // 用户id
    private String userId;
    // 用户邮箱
    private String email;
    // 创建时间
    private String createDate;
    // 用户折扣
    private double preferntial;
    // 会员信息
    private String rankNmae;
    // 昵称
    private String nickname;
    // 生日
    private String birth;
    // 电话
    private String phone;
    // 地址
    private String address;
    // 地区
    private String areaStore;
    // 邮编
    private String zipCode;
    // 性别
    private String gender;
    // 姓名
    private String name;
    // 论坛id
    private String user_id;
    // 头像地址
    private String head_image;
    //七牛
    private String newHead_image;
    //是否医生
    private boolean isDoctor;
    //医生头像
    private String doctorHead;
    //医生id
    private String doctorId;
    //医生名字
    private String doctorName;
    //医生审核信息提示
    private String verifyMsg;
    //审核结果码审:核结果代码 10:已提交审核 11:审核中 20:审核通过 30:审核失败
    private int verify;

    public String getVerifyMsg() {
        return verifyMsg;
    }

    public void setVerifyMsg(String verifyMsg) {
        this.verifyMsg = verifyMsg;
    }

    public int getVerify() {
        return verify;
    }

    public void setVerify(int verify) {
        this.verify = verify;
    }

    public User() {
        super();
    }


    public boolean isDoctor() {
        return isDoctor;
    }

    public void setIsDoctor(boolean isDoctor) {
        this.isDoctor = isDoctor;
    }

    public String getDoctorHead() {
        return doctorHead != null ? doctorHead : getAvatar();
    }

    public void setDoctorHead(String doctorHead) {
        this.doctorHead = doctorHead;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName != null ? doctorName : getNickname();
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getNewHead_image() {
        return newHead_image;
    }

    public void setNewHead_image(String newHead_image) {
        this.newHead_image = newHead_image;
    }

    public String getHead_image() {
        return AppConfig.IMG_IP + head_image;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAreaStore() {
        return areaStore;
    }

    public void setAreaStore(String areaStore) {
        this.areaStore = areaStore;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public double getPreferntial() {
        return preferntial;
    }

    public void setPreferntial(double preferntial) {
        this.preferntial = preferntial;
    }

    public String getRankNmae() {
        return rankNmae;
    }

    public void setRankNmae(String rankNmae) {
        this.rankNmae = rankNmae;
    }

    /**
     * 骑牛头像
     *
     * @return
     */
    public String getAvatar() {
        return getNewHead_image() != null ? getNewHead_image() : getHead_image();
    }
}
