package com.xiuman.xinjiankang.net;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * Created by hxy on 2015/8/5.
 */
public class Wrapper<T>  implements Serializable{


    private String message;
    private String totaldatasource;
    private int page;
    private int totalpage;
    private boolean success;
    private List<T> datasource = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTotaldatasource() {
        return totaldatasource;
    }

    public void setTotaldatasource(String totaldatasource) {
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<T> getDatasource() {
        return datasource;
    }

    public void setDatasource(List<T> datasource) {
        this.datasource = datasource;
    }
}
