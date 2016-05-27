package com.xiuman.xinjiankang.Bean;

import java.io.Serializable;

/**
 * 描述：科普知识劣币表
 * Created by hxy on 2015/8/20.
 */
public class ScienceItemDetail implements Serializable{

    private static final long serialVersionUID = -5128034339342120884L;
    /**
     * message : null
     * totaldatasource : null
     * page : 1
     * totalpage : 1
     * success : true
     * datasource : {"id":"402890094eaa548b014eaa563bd00000","content":"dfafefdsa","icon":null,"title":"123","createDate":1437376854000}
     */
    private String message;
    private String totaldatasource;
    private int page;
    private int totalpage;
    private boolean success;

    private ScienceItemDetailEntity datasource;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTotaldatasource(String totaldatasource) {
        this.totaldatasource = totaldatasource;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setDatasource(ScienceItemDetailEntity datasource) {
        this.datasource = datasource;
    }

    public String getMessage() {
        return message;
    }

    public String getTotaldatasource() {
        return totaldatasource;
    }

    public int getPage() {
        return page;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public boolean isSuccess() {
        return success;
    }

    public ScienceItemDetailEntity getDatasource() {
        return datasource;
    }

    public class ScienceItemDetailEntity implements Serializable{
        /**
         * id : 402890094eaa548b014eaa563bd00000
         * content : dfafefdsa
         * icon : null
         * title : 123
         * createDate : 1437376854000
         */
        private String id;
        private String content;
        private String icon;
        private String title;
        private String guideImg;
        private long createDate;
        //点赞列表
        private int praiseCount;
        //评论列表
        private int commentCount;
        private String label;
        private boolean isPraise;
        //是否收藏
        private boolean isCollect;

        public boolean isCollect() {
            return isCollect;
        }

        public void setCollect(boolean collect) {
            isCollect = collect;
        }

        public boolean isPraise() {
            return isPraise;
        }

        public void setPraise(boolean praise) {
            isPraise = praise;
        }

        public int getPraiseCount() {
            return praiseCount;
        }

        public void setPraiseCount(int praiseCount) {
            this.praiseCount = praiseCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
        public String getGuideImg() {
            return guideImg;
        }

        public void setGuideImg(String guideImg) {
            this.guideImg = guideImg;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public String getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        public String getIcon() {
            return icon;
        }

        public String getTitle() {
            return title;
        }

        public long getCreateDate() {
            return createDate;
        }
    }
}
