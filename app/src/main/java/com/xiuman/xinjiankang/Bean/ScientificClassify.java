package com.xiuman.xinjiankang.bean;

import java.io.Serializable;

/**
 * 描述：科普知识分类
 * Created by hxy on 2015/12/29.
 */
public class ScientificClassify implements Serializable {

    /**
     * id : 402890234f814be1014f81b0ddc3001f
     * createDate : 1440989896000
     * modifyDate : 1440989896000
     * name : 男科
     */

    private String id;
    private long createDate;
    private long modifyDate;
    private String name;

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
