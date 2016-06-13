package com.xiuman.xinjiankang.constant;

/**
 * 描述：常量类
 * Created by hxy on 2015/8/20.
 */
public class Constant {
    //    public static final String http = "http://120.55.242.236:8080/shopxx/app"; //测试
    public static final String http = "http://www.popodd.com/shopxx/app";   //线上

    //用户登录接口
//    public static final String userLoogin = "http://120.55.242.236:8080/shopxx/app";  //测试
    public static final String userLoogin = "http://www.popodd.com/shopxx/shop";  //线上

    public static final String httpDoctor = "http://www.popodd.com/shopxx/app/";
    //-----------------选取图片相关Request------------------------------//
    public static final int ALBUM_SELECT_PHOTOS = 10020;
    public static final int GET_IMAGE_BY_TAKE_PHOTO = 10050;
    public static final int GET_IMAGE_FROM_ALBUM = 10051;
    public static final int ALBUM_SELECT_PHOTOS_PRE = 10021;
    public static final int ALBUM_SELECT_PHOTOS_SCAN = 10022;
    //-----------------------------------------------------------------//
    public static final String APP_ID = "wx4ed85372265e75f9";
    public static final String KEY_USER_INFO = "LOGIN_INFO";
    public static final String KEY_LOGIN = "KEY_LOGIN";
    public static final int PAGE_SIZE = 10;

    //提交VIP咨询
    public static final String VIP_SEND = "/jk_vip_consulting!vipConsult.action";
    //发送文字回复到服务器
    public static final String CHAT_SEND_TXT = "/jk_vip_consulting!vipReply.action";
    //无环信，申请注册
    public static final String HX_REGISTER = "/member!HXregistara.action";

}
