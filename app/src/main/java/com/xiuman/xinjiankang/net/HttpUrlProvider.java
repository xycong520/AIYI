package com.xiuman.xinjiankang.net;
/*
 *				 			         _ooOoo_
 *				 			        o8888888o
 *				 			        88" . "88
 *				 			        (| -_- |)
 *				 			        O\  =  /O
 *				 		         ____/`---'\____
 *				 	           .'  \\|     |//  `.
 *				 	          /  \\|||  :  |||//  \
 *				 	         /  _||||| -:- |||||-  \
 *				 	         |   | \\\  -  /// |   |
 *				 	         | \_|  ''\---/''  |   |
 *				 	         \  .-\__  `-`  ___/-. /
 *				           ___`. .'  /--.--\  `. . __
 *				 	    ."" '<  `.___\_<|>_/___.'  >'"".
 *				 	  | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *				 	  \  \ `-.   \_ __\ /__ _/   .-` /  /
 *				 ======`-.____`-.___\_____/___.-`____.-'======
 *				 					`=---='
 *				 ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *						 佛祖保佑       永无BUG
 *				 		佛曰:
 *					 写字楼里写字间，写字间里程序员；
 *					 程序人员写程序，又拿程序换酒钱。
 *					 酒醒只在网上坐，酒醉还来网下眠；
 *					 酒醉酒醒日复日，网上网下年复年。
 *					 但愿老死电脑间，不愿鞠躬老板前；
 *					 奔驰宝马贵者趣，公交自行程序员。
 *					 别人笑我忒疯癫，我笑自己命太贱；
 *					 不见满街漂亮妹，哪个归得程序员？
 */

import com.lidroid.xutils.HttpUtils;

/**
 * author danding
 * 名称：HttpUrlProvider.java
 * 描述：请求连接
 * date：2014-7-29
 */
public class HttpUrlProvider extends HttpUtils {
    // 实例
    private static HttpUrlProvider instance;

    /**
     * return
     * 描述：单例
     * date：2014-7-29
     */
    public static synchronized HttpUrlProvider getIntance() {
        if (instance == null) {
            instance = new HttpUrlProvider();
            instance.configUserAgent("gzip");
            instance.configRequestThreadPoolSize(5);
            instance.configHttpCacheSize(0);
        }
        return instance;
    }

    /**
     * 构造函数
     */
    public HttpUrlProvider() {
        super();
    }


}