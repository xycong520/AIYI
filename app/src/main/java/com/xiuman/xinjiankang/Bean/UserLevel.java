package com.xiuman.xinjiankang.bean;

import java.io.Serializable;

/**
 * @author danding
 * @名称：UserLevel.java
 * @描述：用户等级积分头衔
 * @时间 2014-11-18
 */
public class UserLevel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5872913797951190086L;
    // 当前等级
    private int groupId;
    // 下一等级
    private int nextgroupId;
    // 用户论坛id
    private String id;
    // 当前经验值
    private int point;
    // 当前等级的满值经验
    private int groupPoint;
    // 用户积分
    private int prestige;
    // 当前等级头衔
    private String groupName;
    // 用户性别
    private boolean sex;
    // 用户昵称
    private String nickname;
    // 下一等级头衔
    private String nextgroupName;
    // 用户名
    private String userName;
    // 下一等级满值经验
    private int nextgroupPoint;

    public UserLevel() {
        super();
    }

    public UserLevel(int groupId, int nextgroupId, String id, int point,
                     int groupPoint, int prestige, String groupName, boolean sex,
                     String nickname, String nextgroupName, String userName,
                     int nextgroupPoint) {
        super();
        this.groupId = groupId;
        this.nextgroupId = nextgroupId;
        this.id = id;
        this.point = point;
        this.groupPoint = groupPoint;
        this.prestige = prestige;
        this.groupName = groupName;
        this.sex = sex;
        this.nickname = nickname;
        this.nextgroupName = nextgroupName;
        this.userName = userName;
        this.nextgroupPoint = nextgroupPoint;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getNextgroupId() {
        return nextgroupId;
    }

    public void setNextgroupId(int nextgroupId) {
        this.nextgroupId = nextgroupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getGroupPoint() {
        return groupPoint;
    }

    public void setGroupPoint(int groupPoint) {
        this.groupPoint = groupPoint;
    }

    public int getPrestige() {
        return prestige;
    }

    public void setPrestige(int prestige) {
        this.prestige = prestige;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNextgroupName() {
        return nextgroupName;
    }

    public void setNextgroupName(String nextgroupName) {
        this.nextgroupName = nextgroupName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getNextgroupPoint() {
        return nextgroupPoint;
    }

    public void setNextgroupPoint(int nextgroupPoint) {
        this.nextgroupPoint = nextgroupPoint;
    }

}
