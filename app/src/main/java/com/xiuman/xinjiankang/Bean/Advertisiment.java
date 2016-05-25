package com.xiuman.xinjiankang.Bean;

import java.io.Serializable;

/**
 * 描述：首页广告
 * Created by hxy on 2015/8/31.
 */
public class Advertisiment implements Serializable{

    private static final long serialVersionUID = -8341503579467730318L;

    /**
     * imgUrl : http://7xpb64.com2.z0.glb.qiniucdn.com/anbang.png
     * jumpType : app
     * modulename : banner
     * sort : 3
     * queryUrlOrValue :
     * moduleposition : first_page
     * moduleLabel : 安邦保险
     */

    private String imgUrl;
    private String jumpType;
    private String modulename;
    private String sort;
    private String queryUrlOrValue;
    private String moduleposition;
    private String moduleLabel;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getJumpType() {
        return jumpType;
    }

    public void setJumpType(String jumpType) {
        this.jumpType = jumpType;
    }

    public String getModulename() {
        return modulename;
    }

    public void setModulename(String modulename) {
        this.modulename = modulename;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getQueryUrlOrValue() {
        return queryUrlOrValue;
    }

    public void setQueryUrlOrValue(String queryUrlOrValue) {
        this.queryUrlOrValue = queryUrlOrValue;
    }

    public String getModuleposition() {
        return moduleposition;
    }

    public void setModuleposition(String moduleposition) {
        this.moduleposition = moduleposition;
    }

    public String getModuleLabel() {
        return moduleLabel;
    }

    public void setModuleLabel(String moduleLabel) {
        this.moduleLabel = moduleLabel;
    }
}
