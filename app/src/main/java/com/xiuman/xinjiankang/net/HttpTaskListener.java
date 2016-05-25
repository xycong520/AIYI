package com.xiuman.xinjiankang.net;

/**
 * @author danding
 * 名称：HttpDataTask.java
 * 描述：HttpTask回调监听
 * date：2014-7-29
 */
public interface HttpTaskListener {
    /**
     * @param result
     * 描述：HttpTask请求成功之后进行数据处理
     * date：2014-7-29
     */
    void dataSucceed(String result);

    /**
     * @param result
     * 描述：HttpTask请求失败之后进行数据处理
     * date：2014-7-29
     */
    void dataError(String result);
}