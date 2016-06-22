package com.xiuman.xinjiankang.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.ImageView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.umeng.socialize.PlatformConfig;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.constant.Constant;
import com.xiuman.xinjiankang.utils.AppInfoUtils;
import com.xiuman.xinjiankang.utils.SizeUtil;
import com.xiuman.xinjiankang.utils.logger.LogLevel;
import com.xiuman.xinjiankang.utils.logger.Logger;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Application
 * Created by csx on 15/4/15.
 */
public class MyApplication extends GlobalContext {
    //    //获取到主线程的handler
//    private static Handler mMainThreadHandler ;
//    //获取到主线程轮询器
//    private static Looper mMainThreadLooper;
//    //获取到主线程的Thread
//    private static Thread mMainThread;
//    //获取到主线程的id
//    private static int mMainThreadId;
    // 单例
    private static MyApplication instance;

    //图片配置器，圆形图和圆角图
    public static ImageOptions optionsCircularPhoto, optionsRadius;

    public static ImageOptions getOptionsCircularPhoto() {
        if (optionsCircularPhoto == null) {
            optionsCircularPhoto = new ImageOptions.Builder().setCircular(true).setConfig(Bitmap.Config.RGB_565).setUseMemCache(true).setImageScaleType(ImageView.ScaleType.FIT_XY).setFailureDrawableId(R.mipmap.xjk_pic_head_logo).setLoadingDrawableId(R.mipmap.xjk_pic_head_logo).build();
        }
        return optionsCircularPhoto;
    }

    public static ImageOptions getOptionsRadius() {
        if (optionsRadius == null) {
            optionsRadius = new ImageOptions.Builder().setRadius(SizeUtil.dip2px(instance, 10)).setConfig(Bitmap.Config.RGB_565).setUseMemCache(true).setImageScaleType(ImageView.ScaleType.FIT_XY).setFailureDrawableId(R.drawable.onloading).setLoadingDrawableId(R.drawable.onloading).build();
        }
        return optionsRadius;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //环信初始化-----------------------
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 设置是否需要已读回执
        options.setRequireAck(true);
        // 设置是否需要已送达回执
        options.setRequireDeliveryAck(false);
        //初始化
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
        //--------------------END-----------------------//
        //xUtils初始化
        x.Ext.init(this);
        instance = this;
        //微信 appid appsecret
        PlatformConfig.setWeixin(AppConfig.WX_APP_ID, AppConfig.WX_SECRET);
        //新浪微博 appkey appsecret
        PlatformConfig.setSinaWeibo(AppConfig.Sina_APP_ID, AppConfig.Sina_APP_SECRET);
        // QQ和Qzone appid appkey
        PlatformConfig.setQQZone(AppConfig.QQ_APP_ID, AppConfig.QQ_APP_KEY);
//        mMainThreadHandler = new Handler();
//        mMainThreadLooper = getMainLooper();
//        mMainThread  = Thread.currentThread();
//        mMainThreadId = android.os.Process.myTid();
        if (AppConfig.isDebug) {
            Logger.init(AppInfoUtils.getApplicationName()).setLogLevel(LogLevel.FULL);
        } else {
            Constant.http = "http://www.popodd.com/shopxx/app";
            Constant.userLoogin = "http://www.popodd.com/shopxx/shop";
            Logger.init(AppInfoUtils.getApplicationName()).setLogLevel(LogLevel.NONE);
        }
    }


    /**
     * return IMEI
     * 描述：获取手机唯一标识码
     * 时间 2014-11-3
     */
    public String getIMEI() {
        String IMEI = "";
        TelephonyManager tm = (TelephonyManager)
                getSystemService(Context.TELEPHONY_SERVICE);
        try {
            IMEI = tm.getDeviceId();
            String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            //000000000000000
            if (IMEI == null || IMEI.contains("000000000") || IMEI.equals("")) {
                if (androidId == null || androidId.equals("9774d56d682e549c") || androidId.equals("")) {//bug
                    //淘宝专用
//                    IMEI = UTDevice.getUtdid(GlobalContext.getInstance());
                } else {
                    IMEI = androidId;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
//            IMEI = UTDevice.getUtdid(GlobalContext.getInstance());
        }

        return IMEI;
    }

        /**
         * return 描述：获取友盟渠道信息
         * 时间 2014-12-10
         */
        public String getUmengChannel() {
            ApplicationInfo appInfo = null;
            try {
                appInfo = this.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String msg = appInfo.metaData.getString("UMENG_CHANNEL");
            return msg;

        }

        /**
         * 获取单例
         *
         * @return
         */
        public static MyApplication getInstance() {
            return instance;
        }

//    //对外暴露一个主线程的handelr
//    public static Handler getMainThreadHandler(){
//        return mMainThreadHandler;
//    }
//    //对外暴露一个主线程的Looper
//    public static Looper getMainThreadLooper(){
//        return mMainThreadLooper;
//    }
//    //对外暴露一个主线程
//    public static Thread getMainThread(){
//        return mMainThread;
//    }
//    //对外暴露一个主线程ID
//    public static int getMainThreadId(){
//        return mMainThreadId;
//    }
    }
