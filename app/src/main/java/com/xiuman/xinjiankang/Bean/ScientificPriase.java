package com.xiuman.xinjiankang.bean;

import java.io.Serializable;

/**
 * 描述：科普知识点赞
 * Created by hxy on 2015/12/29.
 */
public class ScientificPriase implements Serializable{

    /**
     * success : true
     * message : 点赞成功
     * datasource : {"praiseCount":1}
     * totaldatasource : null
     * page : null
     * totalpage : null
     */

    private boolean success;
    private String message;
    /**
     * praiseCount : 1
     */

    private ScientificPriaseEntity datasource;
    private Object totaldatasource;
    private Object page;
    private Object totalpage;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDatasource(ScientificPriaseEntity datasource) {
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

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public ScientificPriaseEntity getDatasource() {
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

    public static class ScientificPriaseEntity implements Serializable{
        private int praiseCount;

        public void setPraiseCount(int praiseCount) {
            this.praiseCount = praiseCount;
        }

        public int getPraiseCount() {
            return praiseCount;
        }
    }
}
