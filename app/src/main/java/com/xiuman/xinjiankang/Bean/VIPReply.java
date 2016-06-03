package com.xiuman.xinjiankang.bean;

import java.io.Serializable;

/**
 * 描述：VIP回复
 * Created by hxy on 2015/9/26.
 */
public class VIPReply implements Serializable {
    private static final long serialVersionUID = 6945577189865072070L;

    /**
     * memberAvatar : /upload/userheadimg/141188272177272fb8a00748e.jpg
     * doctorAvatar : null
     * content : 阿斯顿发生地方1
     * createDate : 2015-09-15 17:12:15.0
     * type : 1
     */
    //患者头像
    private String memberAvatar;
    //医师头像
    private String doctorAvatar;
    //回复内容
    private String content;
    //创建时间
    private String createDate;
    //类别
    private int type;

    public void setMemberAvatar(String memberAvatar) {
        this.memberAvatar = memberAvatar;
    }

    public void setDoctorAvatar(String doctorAvatar) {
        this.doctorAvatar = doctorAvatar;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMemberAvatar() {
        return memberAvatar;
    }

    public String getDoctorAvatar() {
        return doctorAvatar;
    }

    public String getContent() {
        return content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public int getType() {
        return type;
    }
}
