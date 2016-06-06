package com.xiuman.xinjiankang.net;

import android.content.Context;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.xiuman.xinjiankang.utils.NetUtil;
import com.xiuman.xinjiankang.utils.logger.Logger;

import java.io.File;
import java.util.Map;

/**
 * 数据获取AsyncTask
 *
 * @author admin
 */
public class HttpDataTask {
    private Context mContext = null;
    private HttpTaskListener httpTaskListener;
    public RequestParams httpParams = null;


    /**
     * 任务初始化
     *
     * @param context
     * @param listener 必须设置回调监听
     */
    public HttpDataTask(Context context, HttpTaskListener listener) {
        mContext = context;
        setHttpTaskListener(listener);
        httpParams = new RequestParams();
        httpParams.addQueryStringParameter("Connection", "Keep-Alive");
    }

    private void setHttpTaskListener(HttpTaskListener listener) {
        if (listener != null) {
            httpTaskListener = listener;
        } else {
            throw new IllegalArgumentException("Must Set HttpTaskListener");
        }
    }
    /**
     * 设置Post参数
     *
     * @param name
     * @param params
     */
    public void setParams(String name, String params) {
        if (httpParams == null) {
            httpParams = new RequestParams();
        }
        httpParams.addBodyParameter(name, params);
    }

    /**
     * 设置参数
     * @param name
     * @param params
     */
    public void setParams(String name, File params) {
        if (httpParams == null) {
            httpParams = new RequestParams();
        }
        httpParams.addBodyParameter(name, params);
    }


    /**
     * post 方式
     * @param url
     * @param parametersPair
     */
    synchronized  public void post(String url, Map<String, Object> parametersPair) {
        if (!NetUtil.isHasNetAvailable(mContext)) {
            httpTaskListener.dataError("你的网络貌似不给力，重新连接试试");
            return;
        }
        if (parametersPair != null && parametersPair.size() > 0) {
            for (String key : parametersPair.keySet()) {
                if (parametersPair.get(key) != null) {
                    setParams(key, parametersPair.get(key).toString());
                } else {
                    setParams(key, "");
                }
            }
        }

        if (parametersPair != null && parametersPair.size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(url);
            if (!sb.toString().endsWith("?")) {
                sb.append("?");
            }
            for (String key : parametersPair.keySet()) {
                sb.append(key).append("=").append(parametersPair.get(key)+"&");
            }
            Logger.i(sb.toString());
        }else
            Logger.i(url);


        HttpUtils httpDataProvide = HttpUrlProvider.getIntance();
        httpDataProvide.send(HttpMethod.POST, url, httpParams,
                new RequestCallBack<String>() {
                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        httpTaskListener.dataError(arg0 + "");
                        Logger.e(arg1);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        httpTaskListener.dataSucceed(arg0.result);
                        Logger.json(arg0.result);
                    }
                });
    }

    /**
     * get方式
     * @param url
     * @param parametersPair
     */
    public void get(String url, Map<String, Object> parametersPair) {
        if (!NetUtil.isHasNetAvailable(mContext)) {
            httpTaskListener.dataError("你的网络貌似不给力，重新连接试试");
            return;
        }
        StringBuilder sb = new StringBuilder(url);
        if (parametersPair != null && parametersPair.size() > 0) {
            if (!sb.toString().endsWith("?")) {
                sb.append("?");
            }
            for (String key : parametersPair.keySet()) {
                if (sb.length() != (url.length() + 1)) {
                    sb.append("&");
                }
                sb.append(key).append("=").append(parametersPair.get(key));
            }
        }
        Logger.i(sb.toString());
        HttpUtils httpDataProvide = HttpUrlProvider.getIntance();
        httpDataProvide.send(HttpMethod.GET, sb.toString(), new RequestCallBack<String>() {
            @Override
            public void onFailure(HttpException arg0, String arg1) {
                httpTaskListener.dataError(arg1);
                Logger.e(arg1);
            }
            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                Logger.json(arg0.result);
                httpTaskListener.dataSucceed(arg0.result);
            }
        });
    }
}
