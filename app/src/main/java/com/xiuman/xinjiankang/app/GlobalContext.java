package com.xiuman.xinjiankang.app;/**
 * Created by PCPC on 2015/6/1.
 */

import android.app.Application;


/**
 * 描述: BaseApplication
 * 名称: GlobalContext
 * User: csx
 * <p/>
 * zDate: 06-01
 */
public class GlobalContext extends Application {
    private static GlobalContext globalContext;

    @Override

    public void onCreate() {
        super.onCreate();
        globalContext = this;
        initLog();
    }

    /**
     * Logger初始化，FULL显示LOG，NONE屏蔽LOG
     */
    private void initLog() {
//        Logger.init(AppInfoUtils.getApplicationName()) // default PRETTYLOGGER or use just init()
//                .setMethodCount(3)            // default 2
//                .hideThreadInfo()             // default shown
//                .setLogLevel(LogLevel.NONE);  // default LogLevel.FULL;
    }

    public static GlobalContext getInstance() {
        return globalContext;
    }
}
