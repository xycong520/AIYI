package com.xiuman.xinjiankang.bean;

import java.io.Serializable;

/**
 * 描述：支付结果返回
 * Created by hxy on 2015/9/27.
 */
public class Order implements Serializable {


    /**
     * success : true
     * message : null
     * datasource : partner="2088511350305373"&out_trade_no="0000000053a8fb070153adfcd91c002f"&subject="%E5%92%A8%E8%AF%A2%E8%B4%B9%E7%94%A8"&body="%E5%92%A8%E8%AF%A2%E8%B4%B9%E7%94%A8"&total_fee="0.100"&notify_url="http%3A%2F%2Fwww.xingduoduo.com%2Fshopxx%2Fapp%2Fjk_order%21paynotifyUrl.action"&service="mobile.securitypay.pay"&_input_charset="utf-8"&payment_type="1"&seller_id="ahxy@showm.com"&it_b_pay="1d"&sign="ET8NkVwxLs7%2B6ZrM7UQsFUwstLb2%2FoTJn3UXg8CIjjy24bIrV7EW5KRVzNgNlQxSwKIiOX%2B5Snjht7AV04iLzLKGhzDl2aRdF%2FEFt9biy%2FSHtbOB3vE9mGcB4QMP6MPF0Tju1gSuQqv9jOH56xfe99I0dpC2swZJgk6VsboS%2BBE%3D"&sign_type="RSA"
     * totaldatasource : null
     * page : 1
     * totalpage : 1
     */

    private boolean success;
    private Object message;
    private String datasource;
    private Object totaldatasource;
    private int page;
    private int totalpage;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public Object getTotaldatasource() {
        return totaldatasource;
    }

    public void setTotaldatasource(Object totaldatasource) {
        this.totaldatasource = totaldatasource;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }
}
