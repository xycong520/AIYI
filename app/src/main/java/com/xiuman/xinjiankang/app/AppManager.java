package com.xiuman.xinjiankang.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.xiuman.xinjiankang.Request.UserRequest;
import com.xiuman.xinjiankang.utils.AppSpUtil;

import java.util.Stack;

/**
 * @author Administrator
 * @描述：Activity管理
 * @名称：AppManager.java
 * @日期：2014-12-8
 */
public class AppManager {

    public static Stack<Activity> activityStack;
    private static AppManager instance;

    public static int count = 0;

    private AppManager() {
    }

    /**
     * @return
     * @描述：获取单例
     * @日期：2014-12-8
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * @param activity
     * @描述：添加Activity到堆栈
     * @日期：2014-12-8
     */
    public static void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
        count = count + 1;
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public static Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * @描述：结束当前Activity（堆栈中最后一个压入的）
     * @日期：2014-12-8
     */
    public static void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * @param activity
     * @描述：结束指定的Activity
     * @日期：2014-12-8
     */
    public static void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * @param cls
     * @描述：结束指定类名的Activity
     * @日期：2014-12-8
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * @描述：结束所有Activity
     * @日期：2014-12-8
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * @param context
     * @描述：退出应用程序
     * @日期：2014-12-8
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            android.os.Process.killProcess(android.os.Process.myPid());
            // restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }

    private static UserRequest request;

    public static UserRequest getUserRequest() {
        if (request == null) {
            return request = new UserRequest();
        }
        return request;
    }

    public static void showToast(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
    }

    static ProgressDialog pd;

    public static void showDialog(Context context) {
        pd = new ProgressDialog(context);
        pd.setMessage("请稍等...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
    }

    public static void dismiss() {
        if (pd!=null){
            pd.dismiss();
            pd = null;
        }
    }

    /**
     * @return 2014-8-12
     * 描述：查看用户是否登录
     */
    public static boolean isUserLogin() {
        if (AppSpUtil.getInstance().getUserInfo() != null)
            return true;
        else {
            AppSpUtil.getInstance().deleteUserInfo();
            return false;
        }
    }
    public static void loginOut(){
        AppSpUtil.getInstance().deleteUserInfo();
    }
}