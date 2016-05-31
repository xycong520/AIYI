package com.xiuman.xinjiankang.bean;

import java.io.Serializable;

/**
 * 描述：评论科普知识返回
 * Created by hxy on 2015/12/29.
 */
public class ScientificCommentResult implements Serializable {

    /**
     * success : true
     * message : 评论成功
     * datasource : null
     * totaldatasource : null
     * page : null
     * totalpage : null
     */

    private boolean success;
    private String message;
    private Object datasource;
    private Object totaldatasource;
    private Object page;
    private Object totalpage;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDatasource(Object datasource) {
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

    public Object getDatasource() {
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
}
