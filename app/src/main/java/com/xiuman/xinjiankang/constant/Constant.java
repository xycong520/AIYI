package com.xiuman.xinjiankang.constant;

/**
 * 描述：常量类
 * Created by hxy on 2015/8/20.
 */
public class Constant {
    public static String http = "http://120.55.242.236:8080/shopxx/app"; //测试
//    public static String http = "http://192.168.16.41:8080/shopxx/app"; //内网测试
//    public static final String http = "http://www.popodd.com/shopxx/app";   //线上

    //用户登录接口
    public static String userLoogin = "http://120.55.242.236:8080/shopxx/shop";  //测试
//    public static final String userLoogin = "http://www.popodd.com/shopxx/shop";  //线上

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

  	/*------------------------------------------论坛-------------------------------------------*/

    public static final String BBS_IP = "http://bbs.xingduoduo.com:8080";
    // 论坛帖子ip
    public static final String PRIVATE_IP = BBS_IP + "/bbs3/app";
    //用户等级头衔积分
    public static final String USER_LEVEL = "/new/getUserInfo.jhtml?";
    //-----------------------------------------------------------------//

    //发送文字回复到服务器
    public static final String CHAT_SEND_TXT = "/jk_vip_consulting!vipReply.action";
    //无环信，申请注册
    public static final String HX_REGISTER = "/member!HXregistara.action";

    //透传消息action定义
    //用户向医生发送透传消息（vip咨询）
    public static final String ACTION_USER_VIP = "user_send_vip";
    //用户向医生发送透传消息（vip咨询）
    public static final String ACTION_USER_DISCUSS = "user_send_discuss";
    //用户向医生发送头传消息
    public static final String ACTION_USER_PHONE = "user_send_phone";
    //医生向用户发送透传消息(vip评价)
    public static final String ACTION_DOCTOR_VIP = "doctor_send_vip";
    //医生向用户发送透传消息(电话评价)
    public static final String ACTION_DOCTOR_PHONE = "doctor_send_phone";
}
