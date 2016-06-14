package com.xiuman.xinjiankang.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：医师详情
 * Created by hxy on 2015/8/26.
 */
public class DoctorDetail implements Serializable {

    private static final long serialVersionUID = -9059179849565369883L;

    /**
     * success : true
     * message : null
     * datasource : {"isFollow":false,"followNum":0,"position":"主任医师","opinionList":[{"count":2,"name":"意见很有帮助"},{"count":1,"name":"回复很及时"},{"count":1,"name":"非常敬业"},{"count":1,"name":"描述很清晰"}],"isCertification":false,"hopitalId":"402890194f77f348014f78237b490002","introduce":"长期从事心理精神科临床工作，具有丰富的临床经验，熟悉各种常见心理精神问题（如：失眠、焦虑、情感问题、精神问题等）及其诊治方法，擅长难治性精神疾病的治疗，尤其是难治性失眠及精神问题，并在创伤、人际关系、性问题困扰所致的心理问题的心理治疗上，形成了自己的独特风格。","lng":"113.293299","goodat":"心理咨询","satisfactionList":[{"count":6,"name":"很满意"},{"count":1,"name":"满意"}],"username":"18612345659","area":"广东省广州市","doctorId":"8a04bc8b506012a60150601c49400086","hopital":"广东省人民医院","comment":[{"content":"非常敬业。","satisfaction":"很满意","name":"读书少别骗我","opinions":"非常敬业;回复很及时"},{"content":"意见很有帮助。","satisfaction":"很满意","name":"零碎","opinions":"意见很有帮助;描述很清晰"},{"content":"很感谢医生，下次还来咨询你。","satisfaction":"很满意","name":"扛下去就是青春","opinions":"意见很有帮助;描述很清晰"},{"content":"我知道自己的困惑可就是说不出来，表达不清楚，给医生治疗增加了很多压力，但是医生很专业，慢慢引导我，咨询很有帮助。","satisfaction":"很满意","name":"张期","opinions":"描述很清晰;回复很及时"},{"content":"有些往事是不想去触碰的，医生在尊重我的隐私的前提下，给了我很多宝贵的意见，谢谢","satisfaction":"满意","name":"甜蜜蜜的美好","opinions":"非常敬业;意见很有帮助;描述很清晰;回复很及时"},{"content":"我本来以为做心理咨询是很羞人的事情，这样就会暴露自己的心理问题，其实每个人都会有心理问题，经过这次咨询我觉得做心理咨询还是很有必要的。","satisfaction":"很满意","name":"胡青青","opinions":"描述很清晰;回复很及时"},{"content":"是为不错的医生，解答的很有清晰明了，对我个人是很有帮助的，下次还想找她做咨询。","satisfaction":"很满意","name":"遇见就是一种幸福","opinions":"非常敬业;意见很有帮助"}],"doctorName":"宁布","headimgurl":"http://7xl8y7.com2.z0.glb.qiniucdn.com/20151013154157583","lat":"23.131456","popularity":651}
     * totaldatasource : null
     * page : 1
     * totalpage : 1
     */

    private boolean success;
    private Object message;
    /**
     * isFollow : false
     * followNum : 0
     * position : 主任医师
     * opinionList : [{"count":2,"name":"意见很有帮助"},{"count":1,"name":"回复很及时"},{"count":1,"name":"非常敬业"},{"count":1,"name":"描述很清晰"}]
     * isCertification : false
     * hopitalId : 402890194f77f348014f78237b490002
     * introduce : 长期从事心理精神科临床工作，具有丰富的临床经验，熟悉各种常见心理精神问题（如：失眠、焦虑、情感问题、精神问题等）及其诊治方法，擅长难治性精神疾病的治疗，尤其是难治性失眠及精神问题，并在创伤、人际关系、性问题困扰所致的心理问题的心理治疗上，形成了自己的独特风格。
     * lng : 113.293299
     * goodat : 心理咨询
     * satisfactionList : [{"count":6,"name":"很满意"},{"count":1,"name":"满意"}]
     * username : 18612345659
     * area : 广东省广州市
     * doctorId : 8a04bc8b506012a60150601c49400086
     * hopital : 广东省人民医院
     * comment : [{"content":"非常敬业。","satisfaction":"很满意","name":"读书少别骗我","opinions":"非常敬业;回复很及时"},{"content":"意见很有帮助。","satisfaction":"很满意","name":"零碎","opinions":"意见很有帮助;描述很清晰"},{"content":"很感谢医生，下次还来咨询你。","satisfaction":"很满意","name":"扛下去就是青春","opinions":"意见很有帮助;描述很清晰"},{"content":"我知道自己的困惑可就是说不出来，表达不清楚，给医生治疗增加了很多压力，但是医生很专业，慢慢引导我，咨询很有帮助。","satisfaction":"很满意","name":"张期","opinions":"描述很清晰;回复很及时"},{"content":"有些往事是不想去触碰的，医生在尊重我的隐私的前提下，给了我很多宝贵的意见，谢谢","satisfaction":"满意","name":"甜蜜蜜的美好","opinions":"非常敬业;意见很有帮助;描述很清晰;回复很及时"},{"content":"我本来以为做心理咨询是很羞人的事情，这样就会暴露自己的心理问题，其实每个人都会有心理问题，经过这次咨询我觉得做心理咨询还是很有必要的。","satisfaction":"很满意","name":"胡青青","opinions":"描述很清晰;回复很及时"},{"content":"是为不错的医生，解答的很有清晰明了，对我个人是很有帮助的，下次还想找她做咨询。","satisfaction":"很满意","name":"遇见就是一种幸福","opinions":"非常敬业;意见很有帮助"}]
     * doctorName : 宁布
     * headimgurl : http://7xl8y7.com2.z0.glb.qiniucdn.com/20151013154157583
     * lat : 23.131456
     * popularity : 651
     */

    private DatasourceEntity datasource;
    private Object totaldatasource;
    private int page;
    private int totalpage;

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

    public void setPage(int page) {
        this.page = page;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public boolean isSuccess() {
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

    public int getPage() {
        return page;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public static class DatasourceEntity implements Serializable{
        private boolean isFollow;
        private int followNum;
        private String position;
        private boolean isCertification;
        private String hopitalId;
        private String introduce;
        private String lng;
        private String goodat;
        private String username;
        private String area;
        private String doctorId;
        private String hopital;
        private String doctorName;
        private String headimgurl;
        private String lat;
        private int popularity;
        /**
         * count : 2
         * name : 意见很有帮助
         */

        private List<OpinionListEntity> opinionList;
        /**
         * count : 6
         * name : 很满意
         */

        private List<SatisfactionListEntity> satisfactionList;
        /**
         * content : 非常敬业。
         * satisfaction : 很满意
         * name : 读书少别骗我
         * opinions : 非常敬业;回复很及时
         */

        private List<CommentEntity> comment;

        public void setIsFollow(boolean isFollow) {
            this.isFollow = isFollow;
        }

        public void setFollowNum(int followNum) {
            this.followNum = followNum;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public void setIsCertification(boolean isCertification) {
            this.isCertification = isCertification;
        }

        public void setHopitalId(String hopitalId) {
            this.hopitalId = hopitalId;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public void setGoodat(String goodat) {
            this.goodat = goodat;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public void setDoctorId(String doctorId) {
            this.doctorId = doctorId;
        }

        public void setHopital(String hopital) {
            this.hopital = hopital;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
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

        public void setOpinionList(List<OpinionListEntity> opinionList) {
            this.opinionList = opinionList;
        }

        public void setSatisfactionList(List<SatisfactionListEntity> satisfactionList) {
            this.satisfactionList = satisfactionList;
        }

        public void setComment(List<CommentEntity> comment) {
            this.comment = comment;
        }

        public boolean isIsFollow() {
            return isFollow;
        }

        public int getFollowNum() {
            return followNum;
        }

        public String getPosition() {
            return position;
        }

        public boolean isIsCertification() {
            return isCertification;
        }

        public String getHopitalId() {
            return hopitalId;
        }

        public String getIntroduce() {
            return introduce;
        }

        public String getLng() {
            return lng;
        }

        public String getGoodat() {
            return goodat;
        }

        public String getUsername() {
            return username;
        }

        public String getArea() {
            return area;
        }

        public String getDoctorId() {
            return doctorId;
        }

        public String getHopital() {
            return hopital;
        }

        public String getDoctorName() {
            return doctorName;
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

        public List<OpinionListEntity> getOpinionList() {
            return opinionList;
        }

        public List<SatisfactionListEntity> getSatisfactionList() {
            return satisfactionList;
        }

        public List<CommentEntity> getComment() {
            return comment;
        }

        public static class OpinionListEntity  implements Serializable{
            private int count;
            private String name;

            public void setCount(int count) {
                this.count = count;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getCount() {
                return count;
            }

            public String getName() {
                return name;
            }
        }

        public static class SatisfactionListEntity  implements Serializable{
            private int count;
            private String name;

            public void setCount(int count) {
                this.count = count;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getCount() {
                return count;
            }

            public String getName() {
                return name;
            }
        }

        public static class CommentEntity  implements Serializable{
            private String content;
            private String satisfaction;
            private String name;
            private String opinions;

            public void setContent(String content) {
                this.content = content;
            }

            public void setSatisfaction(String satisfaction) {
                this.satisfaction = satisfaction;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setOpinions(String opinions) {
                this.opinions = opinions;
            }

            public String getContent() {
                return content;
            }

            public String getSatisfaction() {
                return satisfaction;
            }

            public String getName() {
                return name;
            }

            public String getOpinions() {
                return opinions;
            }
        }
    }
}
