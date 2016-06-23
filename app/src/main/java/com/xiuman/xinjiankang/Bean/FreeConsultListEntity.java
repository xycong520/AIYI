package com.xiuman.xinjiankang.bean;

import java.io.Serializable;

/**
 * 描述：免费咨询列表
 * Created by hxy on 2015/9/23.
 */
public class FreeConsultListEntity implements Serializable {
    /**
     * content : 大夫您好 是这样(曾经得过1年的焦虑症) 一年前我还有女友 做爱时间正常 但是后来手淫比较频繁 说来惭愧 经常会去找乐子 外面对那些妓女也没有什么感情(看了也没很大感觉) 他们逢场作戏 每次总是没抽动几下就会射了 很打击自信心 平时手淫一般可以5分钟左右 但是生殖器偏软请您帮帮我吧(是否手淫过度)，是不是得了早泄呢?
     * id : 8a04bc8b5016862a015016c0c5b00175
     * sex : 女
     * status : normal
     * name :
     * age : 25
     * avatar :
     * createDate : 1443490743000
     */

    private String content;
    private String id;
    private String sex;
    private String status;
    private String name;
    private int age;
    private String avatar;
    private long createDate;
    //回复人数
    private int replyNum;
    private String departmentCategory;

    public String getDepartmentCategory() {
        return departmentCategory;
    }

    public void setDepartmentCategory(String departmentCategory) {
        this.departmentCategory = departmentCategory;
    }

    public int getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getSex() {
        return sex;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getAvatar() {
        return avatar;
    }

    public long getCreateDate() {
        return createDate;
    }
}
