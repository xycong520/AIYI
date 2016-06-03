package com.xiuman.xinjiankang.bean;

/**
 * Created by PCPC on 2016/6/3.
 */
public class BeanCommonViewType<T> {
    int viewType;
    T beanObj;

    public T getBeanObj() {
        return beanObj;
    }

    public void setBeanObj(T beanObj) {
        this.beanObj = beanObj;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

}
