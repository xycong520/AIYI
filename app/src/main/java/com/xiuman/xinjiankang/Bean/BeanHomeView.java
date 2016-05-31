package com.xiuman.xinjiankang.bean;

/**
 * Created by PCPC on 2016/5/24.
 */
public class BeanHomeView<T> {
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
