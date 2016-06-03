package com.xiuman.xinjiankang.app;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.ImageView;

import com.umeng.socialize.PlatformConfig;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.utils.AppSpUtil;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Application
 * Created by csx on 15/4/15.
 */
public class MyApplication extends GlobalContext {
    //获取到主线程的上下文
    private static MyApplication mContext;
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

    public static ImageOptions optionsPhoto;

    public static ImageOptions getOptionsPhoto() {
        if (optionsPhoto == null) {
            optionsPhoto = new ImageOptions.Builder().setUseMemCache(true).setImageScaleType(ImageView.ScaleType.FIT_XY).setCircular(true).setFailureDrawableId(R.drawable.onloading3).setLoadingDrawableId(R.drawable.onloading3).build();
        }
        return optionsPhoto;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        instance = this;
        mContext = this;
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
     * @return 2014-8-12
     * 描述：查看用户是否登录
     */
    public boolean isUserLogin() {
        if (AppSpUtil.getInstance().getUserInfo() != null)
            return true;
        else {
            AppSpUtil.getInstance().deleteUserInfo();
            return false;
        }
    }
    /**
     * return 描述：获取友盟渠道信息
     * 时间 2014-12-10
     */
    /*public String getUmengChannel() {
        ApplicationInfo appInfo = null;
        try {
            appInfo = this.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String msg = appInfo.metaData.getString("UMENG_CHANNEL");
        return msg;

    }*/

    /**
     * 获取单例
     *
     * @return
     */
    public static MyApplication getInstance() {
        return instance;
    }

    //对外暴露一个上下文
    public static MyApplication getApplication() {
        return mContext;
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
