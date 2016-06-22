package com.xiuman.xinjiankang.bean;

import java.io.Serializable;

/**
 * 描述：免费咨询提问
 * Created by hxy on 2015/9/28.
 */
public class Result implements Serializable{

    /**
     * success : true
     * message : null
     * datasource : {"message":"提问成功"}
     * totaldatasource : null
     * page : 1
     * totalpage : 1
     */

    private boolean success;
    private Object message;
    private postFreeConsultEntity datasource;
    private Object totaldatasource;
    private int page;
    private int totalpage;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public void setDatasource(postFreeConsultEntity datasource) {
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

    public postFreeConsultEntity getDatasource() {
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

    public static class postFreeConsultEntity implements Serializable{
        /**
         * message : 提问成功
         */

        private String message;

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
