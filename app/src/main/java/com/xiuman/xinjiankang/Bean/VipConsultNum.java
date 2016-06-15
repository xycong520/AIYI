package com.xiuman.xinjiankang.bean;

import java.io.Serializable;

/**
 * 描述：免费咨询人数
 * Created by hxy on 2015/9/22.
 */
public class VipConsultNum implements Serializable{

    /**
     * success : true
     * message : null
     * datasource : 230
     * totaldatasource : null
     * page : 1
     * totalpage : 1
     */

    private boolean success;
    private Object message;
    private int datasource;
    private Object totaldatasource;
    private int page;
    private int totalpage;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public void setDatasource(int datasource) {
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

    public boolean getSuccess() {
        return success;
    }

    public Object getMessage() {
        return message;
    }

    public int getDatasource() {
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
}
