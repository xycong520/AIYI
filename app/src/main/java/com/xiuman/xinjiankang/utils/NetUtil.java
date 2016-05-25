package com.xiuman.xinjiankang.utils;/**
 * Created by PCPC on 2015/5/14.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * 描述: 网络连接
 * 名称: NetWorkUtil
 * User: csx
 * Date: 05-14
 */
public class NetUtil {
    /**
     * 判断是否有可用网络 无线和3G
     *
     * @param context
     * @return
     */
    public static boolean isHasNetAvailable(Context context) {
        if (isWifiAvailable(context)) {
            return true;
        } else return isNetAvailable(context);
    }

    /**
     * 判读是否Wifi可用
     *
     * @param context
     * @return
     */
    public static boolean isWifiAvailable(Context context) {
        try {
            WifiManager manager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = manager.getConnectionInfo();
            int wifiIP;
            if (info == null) {
                wifiIP = 0;
            } else {
                wifiIP = info.getIpAddress();
            }

            return manager.isWifiEnabled() && wifiIP != 0;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 除wifi外的网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetAvailable(Context context) {
        try {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (null == manager) {
                return false;
            } else {
                NetworkInfo info = manager.getActiveNetworkInfo();
                if (null == info) {
                    return false;
                } else {
                    if (info.isConnected()) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }

    }



    /**
     * @param ipInt
     * @return
     * @描述：ip格式化工具
     * @时间 2014-11-18
     */
    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }
}
