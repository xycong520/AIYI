package com.xiuman.xinjiankang.bean;

import java.io.Serializable;

/**
 * 描述：医师所在城市
 * Created by hxy on 2015/11/26.
 */
public class DoctorCity implements Serializable {

    /**
     * childId : 8a04bc8b509414b10150a306de78038a
     * childName : 额济纳旗
     */

    private String childId;
    private String childName;

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getChildId() {
        return childId;
    }

    public String getChildName() {
        return childName;
    }
}
