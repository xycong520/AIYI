package com.xiuman.xinjiankang.bean;

import java.io.Serializable;

/**
 * 描述：搜索历史
 * Created by hxy on 2015/8/20.
 */
public class SearchHistory implements Serializable{
    private static final long serialVersionUID = 1744301819445048328L;
    /**
     * id : 402890574f48c5c2014f48d1f57a0002
     * hostKey : 性多多
     */
    private String id;
    private String hostKey;

    public void setId(String id) {
        this.id = id;
    }

    public void setHostKey(String hostKey) {
        this.hostKey = hostKey;
    }

    public String getId() {
        return id;
    }

    public String getHostKey() {
        return hostKey;
    }
}
