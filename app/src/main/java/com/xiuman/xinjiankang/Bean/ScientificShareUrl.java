package com.xiuman.xinjiankang.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：分享链接
 * Created by hxy on 2016/1/18.
 */
public class ScientificShareUrl implements Serializable {

    /**
     * success : true
     * message : success
     * datasource : [{"url":"http://localhost:8080/shopxx/aiyishare.html?id=402890084f73150b014f739aad980044"}]
     * totaldatasource : null
     * page : null
     * totalpage : null
     */

    private boolean success;
    private String message;
    private Object totaldatasource;
    private Object page;
    private Object totalpage;
    /**
     * url : http://localhost:8080/shopxx/aiyishare.html?id=402890084f73150b014f739aad980044
     */

    private List<DatasourceEntity> datasource;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public void setDatasource(List<DatasourceEntity> datasource) {
        this.datasource = datasource;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
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

    public List<DatasourceEntity> getDatasource() {
        return datasource;
    }

    public static class DatasourceEntity {
        private String url;

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }
}
