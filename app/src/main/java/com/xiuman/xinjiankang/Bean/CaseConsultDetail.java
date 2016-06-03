package com.xiuman.xinjiankang.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：咨询详情
 * Created by hxy on 2015/9/26.
 */
public class CaseConsultDetail implements Serializable {
    private static final long serialVersionUID = 4043094190804756298L;

    /**
     * success : true
     * message : null
     * datasource : {"counselId":"402890234fcf1f1d014fcf9f71460000","memberId":"40288e89486494a1014864961ebb0000","sex":true,"age":32,"avatar":"/upload/userheadimg/141188272177272fb8a00748e.jpg","content":"asdfsdfasdf","createDate":"2015-09-25 10:23:31.0","replyNum":4,"status":2,"name":"测试1","isAnonymous":true}
     * totaldatasource : null
     * page : null
     * totalpage : null
     */

    private boolean success;
    private Object message;
    private DatasourceEntity datasource;
    private Object totaldatasource;
    private Object page;
    private Object totalpage;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public void setDatasource(DatasourceEntity datasource) {
        this.datasource = datasource;
    }

    public void setTotaldatasource(Object totaldatasource) {
        this.totaldatasource = totaldatasource;
    }

    public void setPage(Object page) {
        this.page = page;
    }

    public void setTotalpage(Object totalpage) {
        this.totalpage = totalpage;
    }

    public boolean getSuccess() {
        return success;
    }

    public Object getMessage() {
        return message;
    }

    public DatasourceEntity getDatasource() {
        return datasource;
    }

    public Object getTotaldatasource() {
        return totaldatasource;
    }

    public Object getPage() {
        return page;
    }

    public Object getTotalpage() {
        return totalpage;
    }

    public static class DatasourceEntity implements Serializable{

        /**
         * sex : true
         * counselId : 8a04bc8b5013f3a001501408dff7007f
         * status : 0
         * avatar : http://www.xingduoduo.com/shopxx/upload/userheadimg/1439887580778af143d344067.jpg
         * isAnonymous : true
         * doctorUsername : 15396204800
         * replyNum : 0
         * content : 空的
         * username : 15396204800
         * doctorHead : http://7xl8y7.com2.z0.glb.qiniucdn.com/20150828171341359
         * doctorId : 402890084f73150b014f739580190040
         * age : 8
         * name : 咯咯YY
         * memberId : 8a04bc8b4ed33cfd014efbc7a35d5d53
         * doctorName : 黄秀敏
         * createDate : 2015-09-28 20:58:57.0
         */

        //性别
        private boolean sex;
        //咨询ID
        private String counselId;
        //状态
        private int status;
        //头像地址
        private String avatar;
        //是否匿名
        private boolean isAnonymous;
        //医师名称
        private String doctorUsername;
        //回复数量
        private int replyNum;
        //内容
        private String content;
        //咨询用户名
        private String username;
        //医师头像
        private String doctorHead;
        //医师ID
        private String doctorId;
        //订单id
        private String orderId;
        //年龄
        private int age;
        //姓名
        private String name;
        private String memberId;
        //医师名称
        private String doctorName;
        //创建时间
        private String createDate;
        //评论
        private String comment;
        //评论名称
        private String opinionName;

        private String satisfactionName;

        public List<String> getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(List<String> imgUrl) {
            this.imgUrl = imgUrl;
        }

        private List<String> imgUrl;



        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public boolean isAnonymous() {
            return isAnonymous;
        }

        public String getSatisfactionName() {
            return satisfactionName;
        }

        public void setSatisfactionName(String satisfactionName) {
            this.satisfactionName = satisfactionName;
        }

        public String getOpinionName() {
            return opinionName;
        }

        public void setOpinionName(String opinionName) {
            this.opinionName = opinionName;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public void setSex(boolean sex) {
            this.sex = sex;
        }

        public void setCounselId(String counselId) {
            this.counselId = counselId;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setIsAnonymous(boolean isAnonymous) {
            this.isAnonymous = isAnonymous;
        }

        public void setDoctorUsername(String doctorUsername) {
            this.doctorUsername = doctorUsername;
        }

        public void setReplyNum(int replyNum) {
            this.replyNum = replyNum;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setDoctorHead(String doctorHead) {
            this.doctorHead = doctorHead;
        }

        public void setDoctorId(String doctorId) {
            this.doctorId = doctorId;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public boolean isSex() {
            return sex;
        }

        public String getCounselId() {
            return counselId;
        }

        public int getStatus() {
            return status;
        }

        public String getAvatar() {
            return avatar;
        }

        public boolean isIsAnonymous() {
            return isAnonymous;
        }

        public String getDoctorUsername() {
            return doctorUsername;
        }

        public int getReplyNum() {
            return replyNum;
        }

        public String getContent() {
            return content;
        }

        public String getUsername() {
            return username;
        }

        public String getDoctorHead() {
            return doctorHead;
        }

        public String getDoctorId() {
            return doctorId;
        }

        public int getAge() {
            return age;
        }

        public String getName() {
            return name;
        }

        public String getMemberId() {
            return memberId;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public String getCreateDate() {
            return createDate;
        }
    }
}
