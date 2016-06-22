package com.xiuman.xinjiankang.bean;

import java.io.Serializable;

/**
 * 描述：获取医师出诊价格
 * Created by hxy on 2016/2/17.
 */
public class DoctorPrice implements Serializable {

    /**
     * success : true
     * message : null
     * datasource : {"price":30}
     * totaldatasource : null
     * page : null
     * totalpage : null
     */

    private boolean success;
    private Object message;
    /**
     * price : 30.0
     */

    private PriceEntity datasource;
    private Object totaldatasource;
    private Object page;
    private Object totalpage;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public void setDatasource(PriceEntity datasource) {
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

    public Object getMessage() {
        return message;
    }

    public PriceEntity getDatasource() {
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

    public static class PriceEntity implements Serializable{
        private double price;

        public void setPrice(double price) {
            this.price = price;
        }

        public double getPrice() {
            return price;
        }
    }
}
