package com.xiuman.xinjiankang.pay.wxapi;/**
 * Created by PCPC on 2015/8/3.
 */

import android.content.Context;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 描述: 微信支付工具类
 * 名称: PayUtil
 * User: csx
 * Date: 08-03
 */
public class PayUtil {
    IWXAPI msgApi;
    PayReq req;
    //appid
    //请同时修改  androidmanifest.xml里面，.PayActivityd里的属性<data android:scheme="wxb4ba3c02aa476ea1"/>为新设置的appid
    public static final String APP_ID = "wx8f7b132f5d7daf5c";

    public PayUtil(Context context, WXPayParams wxPayParams) {
        msgApi = WXAPIFactory.createWXAPI(context, null);
        msgApi.registerApp(APP_ID);
        req = new PayReq();
        genPayReq(wxPayParams);
    }

    /**
     * 发起支付
     */
    public void sendPayReq() {
        msgApi.registerApp(APP_ID);
        msgApi.sendReq(req);
    }

    /**
     * 签名
     */
    public void genPayReq(WXPayParams wxPayParams) {
        req.appId = wxPayParams.getAppid();
        req.partnerId = wxPayParams.getPartnerid();
        req.prepayId = wxPayParams.getPrepayid();
        req.packageValue = wxPayParams.getPackageValue();
        req.nonceStr = wxPayParams.getNoncestr();
        req.timeStamp = wxPayParams.getTimestamp();

        req.sign = wxPayParams.getSign();
    }

}
