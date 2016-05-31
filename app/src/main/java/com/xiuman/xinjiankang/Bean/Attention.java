package com.xiuman.xinjiankang.bean;

import java.io.Serializable;

/**
 * 描述：关注医生返回
 * Created by hxy on 2015/11/26.
 */
public class Attention implements Serializable {

    /**
     * success : true
     * message : 关注成功
     * datasource : 1
     * totaldatasource : null
     * page : null
     * totalpage : null
     */

    private boolean success;
    private String message;
    private int datasource;
    private Object totaldatasource;
    private Object page;
    private Object totalpage;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDatasource(int datasource) {
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

    public int getDatasource() {
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
