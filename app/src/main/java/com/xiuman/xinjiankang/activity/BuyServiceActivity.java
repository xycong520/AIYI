package com.xiuman.xinjiankang.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMMessage;
import com.lidroid.xutils.http.RequestParams;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.app.AppConfig;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.base.BaseActivity;
import com.xiuman.xinjiankang.bean.ActionValue;
import com.xiuman.xinjiankang.bean.DoctorPrice;
import com.xiuman.xinjiankang.bean.HXUser;
import com.xiuman.xinjiankang.bean.Order;
import com.xiuman.xinjiankang.constant.Constant;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.pay.alipay.AliPayStatus;
import com.xiuman.xinjiankang.pay.alipay.Result;
import com.xiuman.xinjiankang.pay.wxapi.PayUtil;
import com.xiuman.xinjiankang.pay.wxapi.WXPayParams;
import com.xiuman.xinjiankang.utils.logger.Logger;
import com.xiuman.xinjiankang.widget.CustomDialog;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 购买服务
 * Created by PCPC on 2015/6/19.
 */
public class BuyServiceActivity extends BaseActivity {
    public static final String paramOrderID = "orderId";
    public static final String paramTpye = "type";
    public static final String paramQuestionId = "questionId";
    public static final String paramUsername = "username";
    public static final String paramDoctorName = "doctorName";
    public static final String paramIsFlower = "isFlower";
    public static final String paramIcon = "icon";
    public static final String paramDoctorId = "doctorId";
    public static final String paramContent = "content";
    public static final String paramPriceid = "priceid";
    public static final String paramRewardAmount = "rewardAmount";

    /*//返回键
    @Bind(R.id.back)
    TextView back;
    //标题
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.view)
    LinearLayout view;
    @Bind(R.id.share)
    ImageView share;*/
    //医生姓名
    @Bind(R.id.douctor_name)
    TextView douctorName;
    //支付宝
    @Bind(R.id.tv_order_pay_zhifubao_tag)
    TextView tvOrderPayZhifubaoTag;
    @Bind(R.id.zhifubao_cb)
    CheckBox cbOrderPayZhifubao;
    //立即支付
    @Bind(R.id.once_pay)
    Button oncePay;
    //支付宝
    @Bind(R.id.ll_zhifubao)
    LinearLayout ll_zhifubao;

    @Bind(R.id.wechat_cb)
    CheckBox wechatCb;
    @Bind(R.id.ll_wechat)
    //微信
            LinearLayout llWechat;
    /* @Bind(R.id.yinlian_cb)
     CheckBox yinlianCb;
     //银联
     @Bind(R.id.ll_yinlian)
     LinearLayout llYinlian;*/
    //银联
    @Bind(R.id.layoutLoading)
    LinearLayout llyt_loading;
    //价格
    @Bind(R.id.tv_cost_price)
    TextView tvCostPrice;
    @Bind(R.id.tv_service)
    TextView tv_service;
    private PopupWindow pw;
    // 支付宝线程返回 值类型
    private static final int RQF_PAY = 100;
    private String alipaySign = "";
    private IWXAPI wxapi;

    //支付订单类型（VIP咨询，电话咨询）
    private String type;
    //订单id
    String orderId = "";
    //问题id
    private String questionId = "";
    //是否支付成功
    private boolean isPaySuccess = false;
    private int payType = 1;
    //医生用户名
    private String doctorUserName;
    private CustomDialog dialog_cancel;
    String doctorName = "";
    private ActionValue<WXPayParams> value_wx;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AppManager.dismiss();
            if (msg.what == AppConfig.WX_PAY_SUCCESS) {
                value_wx = (ActionValue<WXPayParams>) msg.obj;
                if (value_wx.isSuccess()) {//发起支付
//                    Intent intent = new Intent(mActivity, WXPayEntryActivity.class);
//                    startActivityForResult(intent, AppConfig.REQUEST_CODE);
                    new PayUtil(mActivity, value_wx.getDatasource().get(0)).sendPayReq();
                } else {
                    AppManager.showToast(mActivity, "支付失败请重试");
                }
            }
            if (msg.what == RQF_PAY) {
                Result result = new Result((String) msg.obj);
                result.parseResult();
                try {
                    if (result.rs.equals("9000") && result.isSignOk) {
                        AppManager.showToast(mActivity, "支付成功");
                        isPaySuccess = true;
                        oncePay.setVisibility(View.GONE);
                        if (isFlower) {
                            AppManager.getUserRequest().saveOrderList(mActivity, new HttpTaskListener() {
                                @Override
                                public void dataSucceed(String result) {

                                }

                                @Override
                                public void dataError(String result) {

                                }
                            }, doctorId, rewardAmount, content, priceid, orderId);
                            finish();
                        } else {
                            if (type.equals("vip")) {
                                sendCMD();
                            }
                        }

                    } else {
                        AppManager.showToast(mActivity, result.getResult());
                    }
                } catch (Exception e) {

                }
            }
        }
    };
    private boolean isFlower;
    private String icon;
    private String doctorId;
    private String content;
    private String priceid;
    private String rewardAmount;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConfig.REQUEST_CODE) {
            Logger.e("result", data.getIntExtra("result", -1) + "");
            if (resultCode == 3) {
                int result = data.getIntExtra("result", -1);
                if (result == 0) {
                    getWXPayResult();
                } else if (result == -2) {
                    AppManager.showToast(mActivity, "取消支付");
                }
            }
        }
    }

    @Override
    protected void initView() {
        mActivity = this;
//        title.setText("支付页面");
        setupToolbar();
        wxapi = WXAPIFactory.createWXAPI(this, PayUtil.APP_ID);
        orderId = getIntent().getStringExtra(paramOrderID);
        type = getIntent().getStringExtra(paramTpye);
        questionId = getIntent().getStringExtra(paramQuestionId);
        doctorUserName = getIntent().getStringExtra(paramUsername);
        doctorName = getIntent().getStringExtra(paramDoctorName);
        isFlower = getIntent().getBooleanExtra(paramIsFlower, false);
        icon = getIntent().getStringExtra(paramIcon);
        doctorId = getIntent().getStringExtra(paramDoctorId);
        content = getIntent().getStringExtra(paramContent);
        priceid = getIntent().getStringExtra(paramPriceid);
        rewardAmount = getIntent().getStringExtra(paramRewardAmount);
        if (isFlower) {
            tv_service.setText("送心意");
        }
        getPrice(orderId);
    }

    private void getPrice(String orderId) {
        AppManager.getUserRequest().getDoctorPrice(mActivity, listener, orderId);
    }

    HttpTaskListener listener = new HttpTaskListener() {
        @Override
        public void dataSucceed(String result) {
            try {
                DoctorPrice doctorPrice = new Gson().fromJson(result, DoctorPrice.class);
                tvCostPrice.setText(doctorPrice.getDatasource().getPrice() + "元");
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
            llyt_loading.setVisibility(View.GONE);
        }

        @Override
        public void dataError(String result) {
            llyt_loading.setVisibility(View.GONE);
        }
    };

    @Override
    protected void initData() {
        cleanCheck();
        cbOrderPayZhifubao.setChecked(true);
        douctorName.setText(doctorName);
    }

    public void cleanCheck() {
        cbOrderPayZhifubao.setChecked(false);
        wechatCb.setChecked(false);
//        yinlianCb.setChecked(false);
    }

    //放弃支付
    private void cancelDialog() {
        dialog_cancel = new CustomDialog(this, "提示", "是否放弃支付？");
        dialog_cancel.show();
        dialog_cancel.btn_custom_dialog_cancel.setText("确定");
        dialog_cancel.btn_custom_dialog_sure.setText("取消");
        dialog_cancel.btn_custom_dialog_sure
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_cancel.dismiss();
                        dialog_cancel = null;
                    }
                });
        dialog_cancel.btn_custom_dialog_cancel
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        /*if (type.equals("vip")) {
                            ModelManager.getInstance().getUserRequest().cancleVIPConsult(mActivity, new TaskCancleVIPConsultBack(handler), questionId);
                        } else {
                            ModelManager.getInstance().getUserRequest().cancleTelConsult(mActivity,new TaskCancleTelConsultBack(handler), questionId);
                        }*/
                        dialog_cancel.dismiss();
                        finish();
                    }
                });
    }

    /**
     * 获取微信支付发起支付参数列表
     */
    private void getWXPayParams(String orderId) {
        AppManager.showDialog(this);
        AppManager.getUserRequest().getWXPayParams(mActivity, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                try {
                    AppManager.dismiss();
                    ActionValue<WXPayParams> value = new Gson().fromJson(result,
                            new TypeToken<ActionValue<WXPayParams>>() {
                            }.getType());
                    Message msg = Message.obtain();
                    msg.what = AppConfig.WX_PAY_SUCCESS;
                    msg.obj = value;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    Message msg = Message.obtain();
                    msg.what = AppConfig.WX_PAY_FAILD;// 无网络
                    handler.sendMessage(msg);
                }
            }

            @Override
            public void dataError(String result) {
                AppManager.dismiss();
                AppManager.showToast(mActivity,result);
                Message msg = Message.obtain();
                msg.what = AppConfig.WX_PAY_FAILD;// 无网络
                handler.sendMessage(msg);
            }
        }, orderId);
    }

    @OnClick({R.id.once_pay,/* R.id.back,*/ R.id.ll_zhifubao, R.id.ll_wechat/*, R.id.ll_yinlian*/})
    public void onClick(View v) {
        switch (v.getId()) {
            //立即支付
            case R.id.once_pay:
                if (payType == 1) {
                    getPayParams();
                } else {
                    getWXPayParams(orderId);
                }
                //sendCMD();
                break;
            case R.id.back:
                //onBackPressed();
                if (isPaySuccess) {
//                    Intent intent = new Intent(mActivity, MyConsultActiviy.class);
//                    startActivity(intent);
                    finish();
                } else {
                    cancelDialog();
                }
                break;
            case R.id.ll_zhifubao:
                cleanCheck();
                cbOrderPayZhifubao.setChecked(true);
                payType = 1;
                break;
            case R.id.ll_wechat:
                if (isWXAppInstalledAndSupported(mActivity, wxapi)) {
                    cleanCheck();
                    wechatCb.setChecked(true);
                    payType = 2;
                }
                break;
//            case R.id.ll_yinlian:
//                cleanCheck();
//                yinlianCb.setChecked(true);
//                break;
        }
    }

    /**
     * 按键点击事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // 返回键
            if (isPaySuccess) {
                if (!isFlower){
                    Intent intent2 = new Intent(mActivity, ChatActivity.class);
                    intent2.putExtra(ChatActivity.paramDoctorName, doctorName);
                    intent2.putExtra(ChatActivity.paramQuestionID, questionId);
                    startActivityForResult(intent2, 1);
                }
//                Intent intent = new Intent(mActivity, MyConsultActiviy.class);
//                startActivity(intent);
                finish();
            } else {
                cancelDialog();
            }
        }
        return true;
    }

    /**
     * 订单支付成功之后,向医生发送透传消息
     */
    private void sendCMD() {

        String action = "";//action可以自定义，在广播接收时可以收到
        if (type.equals("vip")) {
            action = Constant.ACTION_USER_VIP;
        } else if (type.equals("phone")) {
            action = Constant.ACTION_USER_PHONE;
        }
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(content, doctorUserName);
        message.setAttribute("questionId", questionId);
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                AppManager.showToast(mActivity, "发送成功");
            }

            @Override
            public void onError(int i, String s) {
                Logger.i(i + "\n" + s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
        //送心意支付成功
        if (isFlower) {
            AppManager.getUserRequest().saveOrderList(mActivity, new HttpTaskListener() {
                @Override
                public void dataSucceed(String result) {
                }

                @Override
                public void dataError(String result) {
                }
            }, doctorId, rewardAmount, content, priceid, orderId);
            finish();
        } else {
            HXUser hxUser = new HXUser(doctorName);
            hxUser.setNick(doctorUserName);
            hxUser.setIdentity(1);
            hxUser.setHeader("");
            hxUser.setUserid(doctorId);
            hxUser.setAvatar(icon);
            // 存入内存
//            ((XDDHXSDKHelper) HXSDKHelper.getInstance()).saveContact(hxUser);

            Intent intent2 = new Intent(mActivity, ChatActivity.class);
            intent2.putExtra(ChatActivity.paramDoctorName, doctorName);
            intent2.putExtra(ChatActivity.paramQuestionID, questionId);
            startActivityForResult(intent2, 1);
           /* Intent intent = new Intent(mActivity, MyConsultActiviy.class);
            intent.putExtra("isVip", true);
            startActivity(intent);*/
            finish();
        }
        //finish();
    }


    /**
     * 获取支付参数
     */
    private void getPayParams() {
        AppManager.getUserRequest().getPhoneOrderPay(this, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                try {
                    Order order = new Gson().fromJson(result, Order.class);
                    if (order.isSuccess()) {
                        alipaySign = order.getDatasource();
                        payAli();
                    }
                } catch (JsonSyntaxException e) {
                    AppManager.showToast(mActivity, getResources().getString(R.string.net_error));
                }
            }

            @Override
            public void dataError(String result) {

            }
        }, orderId);
    }

    /**
     * 发起支付
     */
    private void payAli() {
        try {
            Runnable run = new Runnable() {
                public void run() {
                    PayTask alipay = new PayTask(BuyServiceActivity.this);
                    String result = alipay.pay(alipaySign);
                    Message msg = new Message();
                    msg.what = 100;
                    msg.obj = result;
                    handler.sendMessage(msg);
                }
            };
            Thread payThread = new Thread(run);
            payThread.start();
        } catch (Exception ex) {
            ex.printStackTrace();
            AppManager.showToast(mActivity, "支付宝调用失败");
        }
    }

    /**
     * 检测微信是否安装
     *
     * @param context
     * @param api
     * @return
     */
    private static boolean isWXAppInstalledAndSupported(final Context context,
                                                        IWXAPI api) {
        boolean sIsWXAppInstalledAndSupported = false;
        sIsWXAppInstalledAndSupported = api.isWXAppInstalled()
                && api.isWXAppSupportAPI();
        if (!sIsWXAppInstalledAndSupported) {
            final CustomDialog dialog2 = new CustomDialog(context, "提示", "您还未安装微信，立即下载安装吧！");
            dialog2.show();
            dialog2.dialog_message.setTextSize(14);
            dialog2.btn_custom_dialog_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://weixin.qq.com/"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    dialog2.dismiss();
                }
            });
            dialog2.btn_custom_dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog2.dismiss();
                }
            });


//            ToastUtil.ToastView(context, "您没有安装微信客户端，不能使用微信支付！");
        }

        return sIsWXAppInstalledAndSupported;
    }

    private void initPop() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_pop_payresult_view, null);
        pw = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TextView content = (TextView) view.findViewById(R.id.content);
        String format = String.format("您已成功支付诊费%s元", 30);
        SpannableString ss = new SpannableString(format);
        ss.setSpan(new ForegroundColorSpan(Color.RED), 8, 11, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        content.setText(ss);
        pw.setFocusable(true);
        pw.setOutsideTouchable(true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


    /**
     * 客户端支付成功后去后台查询订单是否真的支付成功
     */
    private void getWXPayResult() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("orderId", orderId);
        AppManager.getUserRequest().payOrderStatuesWX(mActivity, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                Logger.json(result);
                Logger.e(result);
                try {
                    ActionValue<AliPayStatus> value = new Gson().fromJson(result, new TypeToken<ActionValue<AliPayStatus>>() {
                    }.getType());
                    AppManager.showToast(mActivity, value.getMessage());
                    payType = 2;
                    // 加入统计
                    HashMap<String, String> order_map = new HashMap<String, String>();
                    order_map.put("pay_way", "2");// 支付方式
                    order_map.put("channel", AppConfig.CHANNEL);// 购买渠道
//                    MobclickAgent.onEvent(mActivity, "Submit_Order", order_map);
                    // 跳转界面
                    //Logger.e("支付方式："+pay_tag);
                    //OrderCompleteActivity.actionStart(mActivity, pay_tag, value, goods_number + "", tv_order_submit_total.getText().toString(), cart_goods.get(0).getCommonLogoPath(), "");
                    if (isFlower) {
                        finish();
                    } else {
                        sendCMD();
                    }
                } catch (Exception e) {
                    AppManager.showToast(mActivity, "支付失败，请重试!");
                }
            }

            @Override
            public void dataError(String result) {

            }
        }, orderId);
        /*HttpUrlProvider.getIntance().send(HttpRequest.HttpMethod.POST, Constant.http + "/jk_order!payOrderStatusWX.action", params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("reselu", s);
            }
        });*/
    }


    @Override
    protected int getView() {
        return R.layout.activity_buy_service;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
