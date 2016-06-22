package com.xiuman.xinjiankang.pay.wxapi;/**
 * Created by PCPC on 2015/8/5.
 */

import java.io.Serializable;

/**
 * 描述: 微信支付参数
 * 名称: WXPayParams
 * User: csx
 * Date: 08-05
 */
public class WXPayParams implements Serializable {


    /**
     * sign : 17BAE203FD1B7E7F8008F18ADB9DA91D
     * timestamp : 20150805175057
     * noncestr : qWTpL2x6J1WLmmhv
     * partnerid : 1243195402
     * prepayid : wx201508051750571c81817caf0278598224
     * package : Sign=WXPay
     * appid : wx4ed85372265e75f9
     */
    private String sign;
    private String timestamp;
    private String noncestr;
    private String partnerid;
    private String prepayid;
    private String packageValue;
    private String appid;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
