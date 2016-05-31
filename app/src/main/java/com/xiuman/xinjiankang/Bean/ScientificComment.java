package com.xiuman.xinjiankang.bean;

import java.io.Serializable;

/**
 * 描述：评论列表
 * Created by hxy on 2015/12/29.
 */
public class ScientificComment implements Serializable {

    /**
     * commentId : 5ad5147a51ec4fc40151eca0ca610006
     * articleId : 402890084f73150b014f7386e3dc0039
     * memberId : 8a04bc8b4d5674d6014d65d62d672cec
     * content : 刚刚给刚刚给个
     * floor : 0
     * parentId : null
     * createBy : 8a04bc8b4d5674d6014d65d62d672cec
     * createDate : 1451373939000
     * modifyDate : 1451373939000
     * modifyBy : 8a04bc8b4d5674d6014d65d62d672cec
     * remarks : null
     * delFlag : 0
     * avatar : /upload/userheadimg/1446098118374b7a2970c804b.jpg
     * gender : true
     * nickname : 肥洋洋
     */
    //评论id
    private String commentId;
    //科普知识Id
    private String articleId;
    //用户id
    private String memberId;
    //评论内容
    private String content;
    private int floor;
    //评论的楼层id
    private String parentId;
    private String createBy;
    //创建时间
    private long createDate;
    private long modifyDate;
    private String modifyBy;
    private String remarks;
    private int delFlag;
    //用户头像
    private String avatar;
    //性别
    private boolean gender;
    //昵称
    private String nickname;

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public void setModifyDate(long modifyDate) {
        this.modifyDate = modifyDate;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getArticleId() {
        return articleId;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getContent() {
        return content;
    }

    public int getFloor() {
        return floor;
    }

    public String getParentId() {
        return parentId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public long getCreateDate() {
        return createDate;
    }

    public long getModifyDate() {
        return modifyDate;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public String getAvatar() {
        return avatar;
    }

    public boolean isGender() {
        return gender;
    }

    public String getNickname() {
        return nickname;
    }
}
