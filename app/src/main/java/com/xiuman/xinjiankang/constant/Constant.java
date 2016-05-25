package com.xiuman.xinjiankang.constant;

/**
 * 描述：常量类
 * Created by hxy on 2015/8/20.
 */
public class Constant {
    public static final String textHttp = "http://www.popodd.com/shoxpx/app";
//    public static final String http = "http://192.168.16.51:8080/shopxx/app"; //测试
        public static final String http = "http://www.popodd.com/shopxx/app";   //线上

    //用户登录接口
//    public static final String userLoogin = "http://192.168.16.51:8080/shopxx/shop";  //测试
    public static final String userLoogin = "http://www.popodd.com/shopxx/shop";  //线上




    public static final String httpDoctor = "http://www.popodd.com/shopxx/app/";
    public static final String APP_ID = "wx4ed85372265e75f9";
    public static final String KEY_USER_INFO = "LOGIN_INFO";
    public static final String KEY_LOGIN = "KEY_LOGIN";
    public static final int PAGE_SIZE = 10;
    public static final int REQUEST_SUCCEED = 1000;
    public static final int REQUEST_FAIL = 1100;
    public static final int REQUEST_NET_FAIL = 1110;
    public static final int REQUEST_DOCTOR_SUCCEED = 1200;
    public static final int REQUEST_HOSPITAL_SUCCEED = 1300;
    public static final int REQUEST_AD_SUCCEED = 1400;
    public static final int REQUEST_CASE_HEAD_SUCCEED = 1500;
    public static final int REQUEST_CASE_DETAIL_SUCCEED = 1600;
    public static final int REQUEST_CASE_DOCTOR_LIST_SUCCEED = 1610;
    public static final int REQUEST_CASE_USER_LIST_SUCCEED = 1620;
    public static final int REQUEST_GET_TIME_SUCCEED = 1700;
    public static final int REQUEST_SELECT_DOCTOR_SUCCEED = 1800;
    public static final int REQUEST_GET_MONEY_SUCCEED = 1900;
    public static final int REQUEST_POST_MONEY_SUCCEED = 2000;
    public static final int REQUEST_CONPLETE_SUCCEED = 2100;
    public static final int REQUEST_CENCEL_TEL_SUCCEED = 2200;
    public static final int REQUEST_TAKE_ATTENTION_SUCCEED = 2201;
    public static final int REQUEST_TAKE_DMINISTRATIVE_SUCCEED = 2202;
    public static final int REQUEST_TAKE_FREE_DMINISTRATIVE_SUCCEED = 2204;
    public static final int REQUEST_TAKE_AREA_SUCCEED = 2203;
    public static final int REQUEST_SCIENTIFIC_COMMENT_SUCCEED = 2205;
    public static final int REQUEST_SCIENTIFIC_PRAISE_SUCCEED = 2206;
    public static final int REQUEST_POST_SCIENTIFIC_COMMENT_SUCCEED = 2207;
    public static final int REQUEST_PSYCHOLOGY_DEGREE_SUCCEED = 2208;
    public static final int REQUEST_DOCTOR_DETAIL_FREEBACK_SUCCEED = 2209;
    public static final int REQUEST_DOCTOR_DETAIL_SERVICE_SUCCEED = 2210;
    public static final int REQUEST_DOCTOR_DETAIL_SEND_MIND_SUCCEED = 2211;

    //提交VIP咨询
    public static final String VIP_SEND = "/jk_vip_consulting!vipConsult.action";
    //发送文字回复到服务器
    public static final String CHAT_SEND_TXT = "/jk_vip_consulting!vipReply.action";
    //无环信，申请注册
    public static final String HX_REGISTER = "/member!HXregistara.action";

}
