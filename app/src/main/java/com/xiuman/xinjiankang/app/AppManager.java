package com.xiuman.xinjiankang.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * @描述：Activity管理
 * @名称：AppManager.java
 * @author Administrator
 * @日期：2014-12-8
 */
public class AppManager {

	public static Stack<Activity> activityStack;
	private static AppManager instance;
	public int count = 0;

	private AppManager() {
	}

	/**
	 * @描述：获取单例
	 * @日期：2014-12-8
	 * @return
	 */
	public static AppManager getAppManager() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}

	/**
	 * @描述：添加Activity到堆栈
	 * @日期：2014-12-8
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
		count = count + 1;
	}

	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * @描述：结束当前Activity（堆栈中最后一个压入的）
	 * @日期：2014-12-8
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * @描述：结束指定的Activity
	 * @日期：2014-12-8
	 * @param activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * @描述：结束指定类名的Activity
	 * @日期：2014-12-8
	 * @param cls
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
	 * @描述：退出应用程序
	 * @日期：2014-12-8
	 * @param context
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
}