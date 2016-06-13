package com.xiuman.xinjiankang.bean;

import java.io.Serializable;

/**
 * 描述：科室
 * Created by hxy on 2015/11/26.
 */
public class Dministartive implements Serializable {

    /**
     * id : 402890234f814be1014f81b0bbae001e
     * createDate : 1440989887000
     * modifyDate : 1440989887000
     * name : 男科
     */

    private String id;
    private long createDate;
    private long modifyDate;
    private String name;
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public void setModifyDate(long modifyDate) {
        this.modifyDate = modifyDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public long getCreateDate() {
        return createDate;
    }

    public long getModifyDate() {
        return modifyDate;
    }

    public String getName() {
        return name;
    }
}
