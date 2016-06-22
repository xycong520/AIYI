package com.xiuman.xinjiankang.app;

/**
 * @author danding
 * @version 2014-6-12
 *          名称：AppConfig.java
 *          描述：常量信息的定义
 */
public class AppConfig {
    public static boolean isDebug = true;
    // 图片地址前缀
    public static final String IMG_IP = "http://www.popodd.com/shopxx";

    // 渠道标识
    public final static String CHANNEL = MyApplication.getInstance().getUmengChannel();
    // 发起临时会话的腾讯ID
    public final static String APP_ID = "1104742357";
    //友盟在线参数 debug false无限制，true>10min
    public final static boolean UMENG_DEBUG = false;

	/*---------------------网络连接--------------------*/
    /***
     * 成功
     */
    public final static int NET_SUCCED = 0;
    /***
     * 无网络
     */
    public final static int NET_ERROR_NOTNET = 2;
    /***
     * 结果出错
     */
    public final static int NET_ERROR_COMMON = 6;

    //公共区
    //成功
    public final static int COMMON_SUCCESS = 501;
    //失败
    public final static int COMMON_FAILD = 502;


    // bbs参数------------------------------------------
    // 女神导购列表返回
    public final static int GODDESS_GUIDE_BACK_SUCCESSED = 9013;
    // 女神导购返回失败数据解析失败
    public final static int GODDESS_GUIDE_BACK_FAILD = 9014;
    // 女神导购网络链接失败
    public final static int GODDESS_GUIDE_BACK_FAIL2 = 9015;
    // 板块列表返回
    public final static int BBS_PLATE_BACK_SUCCESSED = 9011;
    // 失败
    public final static int BBS_PLATE_BACK_FAILD = 9012;
    // 普通帖子列表返回
    public final static int BBS_POST_BACK = 9001;
    // 置顶帖子列表返回
    public final static int BBS_TOP_POST_BACK = 9002;
    // 置顶帖子列表返回失败
    public final static int BBS_TOP_POST_BACK_FAILD = 90013;
    // 帖子回复列表返回
    public final static int BBS_REPLY_POST_BACK = 9003;
    // 帖子回复返回
    public final static int BBS_REPLY_SEND_BACK = 9004;
    // 回复帖子失败
    public final static int BBS_REPLY_FAILD = 9009;
    // 帖子发表返回
    public final static int BBS_PUBLISH_BACK = 9005;
    // 帖子图片线程
    public final static int BBS_PUBLISH_RUN = 9006;
    // 上传头像线程
    public final static int UPLOAD_PORAIT_RUN = 9007;

    // 热门话题
    public final static int HOT_TOPIC_SUCCESSED = 9016;
    // 失败
    public final static int HOT_TOPIC_FALID = 9017;
    //未读消息数成功
    public final static int UNREAD_MESSAGE_COUNT_SUCCESS = 9018;
    //失败
    public final static int UNREAD_MESSAGE_COUNT_FAILD = 9019;
    //未读消息列表成功
    public final static int UNREAD_MESSAGE_LIST_SUCCESS = 9020;
    //失败
    public final static int UNREAD_MESSAGE_LIST_FAILD = 9021;

    //微信支付成功
    public final static int WX_PAY_SUCCESS = 9022;
    public final static int WX_PAY_FAILD = 9023;


    // 购物车Fragment Activity请求码与返回码-----------------------------
    public final static int REQUEST_CODE = 1;
    // 返回码1成功刷新
    public final static int RESULT_CODE_OK = 2;
    // 返回码2不刷新
    public final static int RESULT_CODE_CANCEL = 3;
    // 返回码3
    public final static int RESULT_CODE_OK_2 = 4;

    // 收货地址请求码与返回码----------------------------------------------------
    // 新增收货地址
    public final static int RESULT_CODE_ADD_ADDRESS = 4;
    // 修改（删除）收货地址
    public final static int RESULT_CODE_UPDATE_ADDRESS = 5;

    // 提交订单界面请求返回收货地址(用户登录状态下)----------------------------------------------
    public final static int ORDER_SUBMIT_ADDRESS = 6;
    // 提交订单界面请求返回收货地址(用户未登录状态下)----------------------------------------------
    public final static int ORDER_SUBMIT_ADDRESS_WITHOUT_LOGIN = 7;

    //获取未读消息数
    public final static int REQUEST_UNREADMESSAGE = 8;
    public final static int RESULT_UNREADMESSAGE = 9;


    /*----------------------------首页---------------------------*/
    // 首页商品
    // 获取首页置顶成功
    public static final int CENTER_GOODS_SUCCED = 13;
    // 失败
    public static final int CENTER_GOODS_FAILD = 14;

    // 商品详情页的相关推荐成功
    public static final int GOODS_RECOMMEND_SUCCESS = 8001;
    // 失败
    public static final int GOODS_RECOMMEND_FAILD = 8002;

    /*-----------------搜索界面--------------*/
    // 添加搜索历史
    public static final int SEARCH_ADD = 1001;
    // 删除某个搜索历史成功
    public static final int SEARCH_DELETE = 1002;

    /*-----------------更换用户头像----------*/
    // 打开相机
    public static final int OPEN_CAMERA = 2001;
    // 获取相机数据
    public static final int CUT_CAMERA_RESULT = 2002;
    // 获取相册
    public static final int CUT_GALLERY_RESULT = 2003;

    // 用户收货地址更新
    public static final int UPDATE_USER_ADDRESS = 3001;

    /*----------------------购物车更新------------------------*/
    // 添加商品到购物车
    public static final int ADD2Cart = 4004;
    // 添加购物车失败
    public static final int ADD2Cart_FAILD = 4007;
    // 更新购物车数据(结算的总价)
    public static final int UPDATE_SHOPPING_CART = 4001;
    // 更新购物车选择(结算)
    public static final int UPDATE_SHOPPING_CART_SELECT = 4002;
    // 更新购物车商品（列表）
    public static final int UPDATE_SHOPPING_CART_GOODS = 4003;
    // 请求删除购物车商品(请求接口)
    public static final int UPDATE_DELETE_SHOPPING_CART_GOODS = 4005;
    // 请求删除购物扯商品失败
    public static final int UPDATE_DELETE_SHOPPING_CART_GOODS_FALID = 4008;
    // 请求修改购物车商品数量
    public static final int UPDATE_SHOPPING_CART_GOODS_NUMBER = 4006;
    // 修改商品数量失败
    public static final int UPDATE_SHOPPING_CART_GOODS_NUMBER_FALID = 4009;

    /*--------------------------我的订单历史--------------------*/
    // 生成订单
    public static final int CREATE_ORDER = 5003;
    // 订单支付宝签名
    public static final int ALIPAY_BACK = 5005;
    // 删除订单(请求)
    public static final int UPDATE_ORDER = 5002;
    // 传递支付宝支付结果到服务
    public static final int SEND_STATUS_CODE = 5006;
    // 确认收货
    public static final int TAKE_ORDER = 5007;
    // 取消订单
    public static final int CANCEL_ORDER = 5008;

    /*--------------------------我的收藏列表--------------------*/
    // 删除收藏(接口)
    public static final int DELETE_COLLECTION = 6001;
    // 删除收藏失败
    public static final int DELETE_COLLECTION_FALID = 6005;
    // 添加收藏
    public static final int ADD_COLLECTION = 6002;
    // 添加收藏失败
    public static final int ADD_COLLECTION_FAILD = 6004;

    /*-------------------------商品详情--------------------------------*/
    public static final int GOODS_INFO = 6006;
    //图文详情
    public static final int GOODS_IMG_TXT = 6007;
    //失败
    public static final int GOODS_IMG_TXT_FALID = 6008;

    /*--------------------------用户信息------------------------*/
    // 获取用户信息成功
    public static final int SUCCESS_USER_INFO = 101;
    // 获取用户等级成功
    public static final int SUCCESS_USER_LEVEL = 102;
    // 获取用户等级失败
    public static final int FAILED_USER_LEVEL = -102;

    /*--------------------------用户每日登录------------------------------*/
    // 用户每日登录成功
    public static final int SUCCESS_DAY_LOGIN = 103;
    // 失败
    public static final int FAILD_DAY_LOGIN = 104;


    /*-------------------------用户密码重置---------------------------------*/
    //成功
    public static final int RESET_PSW_SUCCESS = 105;
    //失败
    public static final int RESET_PSW_FAILD = 106;

    /*-------------------------收货地址----------------------------*/
    // 删除收货地址
    public static final int GET_DELETE_ADDRESS = 301;
    // 修改收货地址
    public static final int GET_UPDATE_ADDRESS = 302;

    // 举报帖子
    public static final int SUCCESS_POST_REPORT = 403;
    // 举报失败
    public static final int FAILD_POST_REPORT = 404;

    // 获取快递单号，
    public static final int SUCCEDD_DEILVER_ID = 405;
    // 失败
    public static final int FALID_DEILVER_ID = 406;


    /*-----------------------------------商品分类-----------------------------------*/
    // file
    public static final String FILE_CATEGORY = "file_category";
    // key
    public static final String KEY_CATEGORY = "key_category";


    /*-------------------------------------------------------------------新的sp-----------------------------------------------------------------*/

    //首页广告
    public static final String KEY_CENTER_AD = "key_center_ad";
    //首页数据列表
    public static final String KEY_CENTER_GOODS = "key_center_goods";
    //新的分类界面的数据
    public static final String KEY_CATEGOEY_MODE = "key_category";
    //商品收藏
    public static final String KEY_COLLECT_GOODS = "key_collect_goods";
    //购物车商品数量
    public static final String KEY_GOODS_NUMBER = "key_goods_number";
    //泡吧未读消息数
    public static final String KEY_BBS_UNREAD_MSG = "key_message";
    //帖子收藏
    public static final String KEY_COLLECT_POST = "key_collect_post";
    //论坛广告
    public static final String KEY_BBS_AD = "key_bbs_ad";
    //论坛板块列表
    public static final String KEY_BBS_PLATES = "key_plates";
    //女神导购
    public static final String KEY_GODDESS_GUIDE = "key_goddess_guide";
    //热门话题
    public static final String KEY_HOT_TOPIC = "key_hot_topic";
    //当前版本号
    public static final String KEY_VERSION_CODE = "key_version_code";
    //当前版本名
    public static final String KEY_VERSION_NAME = "key_version_name";
    //用户上一次使用的收货地址
    public static final String KEY_DEFAULT_ADDRESS = "key_default_address";
    // 用户等级信息
    public static final String KEY_USER_LEVEL = "key_user_level";
    //用户信息
    public static final String KEY_USER_INFO = "key_user_info";
    //用户登录状态
    public static final String KEY_USER_LOGIN = "key_user_login";
    //用户的搜索记录
    public static final String KEY_SEARCH_LIST="key_search_list";

    public static final String WX_APP_ID = "wx8f7b132f5d7daf5c";
    public static final String WX_SECRET = "5e13b717092cb332408a224b3188f2f3";
    public static final String Sina_APP_ID = "2185425100";
    public static final String Sina_APP_SECRET = "9ce56993035cd8b2aad5b76ee5411cdc";
    public static final String QQ_APP_ID = "1104814861";
    public static final String QQ_APP_KEY = "MAkdxyJDgMz5vwbl";



}
