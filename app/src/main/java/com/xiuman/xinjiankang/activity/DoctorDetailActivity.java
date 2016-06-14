package com.xiuman.xinjiankang.activity;

import android.app.Dialog;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.app.MyApplication;
import com.xiuman.xinjiankang.base.BaseActivity;
import com.xiuman.xinjiankang.bean.Attention;
import com.xiuman.xinjiankang.bean.DoctorDetail;
import com.xiuman.xinjiankang.bean.DoctorFreeback;
import com.xiuman.xinjiankang.bean.DoctorSendMind;
import com.xiuman.xinjiankang.bean.DoctorService;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.net.Wrapper;
import com.xiuman.xinjiankang.utils.UIHelper;
import com.xiuman.xinjiankang.widget.StickyScrollView;

import org.xutils.x;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by PCPC on 2016/6/14.
 */
public class DoctorDetailActivity extends BaseActivity implements View.OnClickListener {
    public static final String parameID = "doctorID";
    @Bind(R.id.layout_network_error)
    LinearLayout layout_network_error;
    //评论
    @Bind(R.id.rlyt_comment)
    RelativeLayout rlyt_comment;
    @Bind(R.id.layoutLoading)
    LinearLayout layout_loading;
    @Bind(R.id.llyt_comment_gone)
    LinearLayout llyt_comment_gone;
    //医生头像
    @Bind(R.id.icon)
    ImageView icon;
    //医生姓名
    @Bind(R.id.name)
    TextView name;
    //用户反馈
    @Bind(R.id.tv_feedbackName1)
    TextView tv_feedbackName1;
    //用户反馈
    @Bind(R.id.tv_feedbackName2)
    TextView tv_feedbackName2;
    //用户反馈
    @Bind(R.id.tv_feedbackName3)
    TextView tv_feedbackName3;
    //医生职称
    @Bind(R.id.prefossion)
    TextView prefossion;
    @Bind(R.id.llyt_name)
    LinearLayout llytName;
    @Bind(R.id.view)
    View view;
    //医院名称
    @Bind(R.id.hospital_name)
    TextView hospitalName;
    //医院地址
    @Bind(R.id.address)
    TextView address;
    //擅长领域
    @Bind(R.id.special_zoom)
    TextView specialZoom;
    //关注
    @Bind(R.id.tv_attention)
    TextView tvAttention;
    //送心意
    @Bind(R.id.tv_send_flowers)
    TextView tvSendFlowers;
    //简介
    @Bind(R.id.tv_synopsis)
    TextView tvSynopsis;
    //推荐指数
    @Bind(R.id.tv_recommend_number)
    TextView tvRecommendNumber;
    //推荐指数
    @Bind(R.id.tv_recommend_number2)
    TextView tvRecommendNumber2;
    //推荐指数
    @Bind(R.id.tv_recommend_number3)
    TextView tvRecommendNumber3;
    //图文咨询
    @Bind(R.id.llyt_picture_consult)
    LinearLayout llytPictureConsult;
    //电话咨询
    @Bind(R.id.llyt_phone_consult)
    LinearLayout llytPhoneConsult;
    //视频咨询
    @Bind(R.id.llyt_video_consult)
    LinearLayout llytVideoConsult;

    @Bind(R.id.sslv)
    StickyScrollView sslv;
    //全部评论
    @Bind(R.id.tv_all_comment)
    TextView tvAllComment;
    //评论列表
    @Bind(R.id.llyt_comment)
    LinearLayout llytComment;
    //所有心意
    @Bind(R.id.tv_all_kindness)
    TextView tvAllKindness;
    //送心意列表
    @Bind(R.id.llyt_kindness)
    LinearLayout llytKindness;
    //用户评价数
    @Bind(R.id.tvCountComment)
    TextView tvCommentCount;
    //心意墙
    @Bind(R.id.tvCountXyq)
    TextView tvXyqCount;

    //内容
    private TextView tv_content;
    //立即咨询
    private TextView tv_once_consult;
    //弹出框
    private Dialog mDialog;
    //医师ID
    private String doctorID;
    //医师详情
    private DoctorDetail doctorDetail;
    private String[] split;
    private Wrapper<DoctorSendMind> doctorSendMind;

    @Override
    protected void initView() {
        setupToolbar();
        doctorID = getIntent().getStringExtra(parameID);
        requestData();
    }

    @Override
    protected int getView() {
        return R.layout.activity_doctor_detail;
    }

    //请求数据
    private void requestData() {
        if (doctorID != null) {
            //医生详情
            AppManager.getUserRequest().getDoctorDetail(mActivity, new HttpTaskListener() {
                @Override
                public void dataSucceed(String result) {
                    layout_loading.setVisibility(View.GONE);
                    doctorDetail = new Gson().fromJson(result, DoctorDetail.class);
                    sslv.setVisibility(View.VISIBLE);
                    setDate(doctorDetail.getDatasource());
                }

                @Override
                public void dataError(String result) {
                    AppManager.showToast(mActivity, result);
                    layout_network_error.setVisibility(View.VISIBLE);
                    layout_loading.setVisibility(View.GONE);
                }
            }, doctorID);
            //医生评价
            AppManager.getUserRequest().getDoctorInquiryList(mActivity, new HttpTaskListener() {
                @Override
                public void dataSucceed(String result) {
                    Wrapper<DoctorService> doctorService = new Gson().fromJson(result, new TypeToken<Wrapper<DoctorService>>() {
                    }.getType());
                    if (doctorService != null && doctorService.getDatasource() != null && doctorService.getDatasource().size() > 0) {
                        initDialog();
                        String format = "服务介绍：通过文字、图片进行一对一咨询。￥" + doctorService.getDatasource().get(0).getInquiryFee() + "元/次";
                        int i = format.indexOf("。");

                        SpannableString str = new SpannableString(format);
                        str.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), i + 1, format.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                        str.setSpan(new AbsoluteSizeSpan(UIHelper.dip2px(mActivity, 15)), i + 1, format.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                        tv_content.setText(str);
                    } else {
                        initDialog();
                        String format = "服务介绍：通过文字、图片进行一对一咨询。￥10元/次";
                        int i = format.indexOf("。");
                        SpannableString str = new SpannableString(format);
                        str.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), i + 1, format.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                        str.setSpan(new AbsoluteSizeSpan(UIHelper.dip2px(mActivity, 15)), i + 1, format.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                        tv_content.setText(str);
                    }
                }

                @Override
                public void dataError(String result) {

                }
            }, doctorID);
            //用户评论
            AppManager.getUserRequest().getDoctorUserFreeback(mActivity, new HttpTaskListener() {
                @Override
                public void dataSucceed(String result) {
                    Wrapper<DoctorFreeback> value = new Gson().fromJson(result, new TypeToken<Wrapper<DoctorFreeback>>() {
                    }.getType());
                    if (value != null && value.getDatasource() != null && value.getDatasource().size() > 0) {
                        DoctorFreeback doctorFreeback1 = value.getDatasource().get(0);
                        tv_feedbackName1.setText(doctorFreeback1.getFeedbackName());
                        tvRecommendNumber.setText(doctorFreeback1.getScore() + "");
                        DoctorFreeback doctorFreeback2 = value.getDatasource().get(1);
                        tv_feedbackName2.setText(doctorFreeback2.getFeedbackName());
                        tvRecommendNumber2.setText(doctorFreeback2.getScore() + "");
                        DoctorFreeback doctorFreeback3 = value.getDatasource().get(2);
                        tv_feedbackName3.setText(doctorFreeback3.getFeedbackName());
                        tvRecommendNumber3.setText(doctorFreeback3.getScore() + "");
                    } else {

                    }
                }

                @Override
                public void dataError(String result) {

                }
            }, doctorID);
            //心意墙
            AppManager.getUserRequest().getMindList(mActivity, new HttpTaskListener() {
                @Override
                public void dataSucceed(String result) {
                    Wrapper<DoctorSendMind> doctorSendMind = new Gson().fromJson(result, new TypeToken<Wrapper<DoctorSendMind>>() {
                    }.getType());
                    if (doctorSendMind != null) {
                        llytKindness.removeAllViews();
                        if (doctorSendMind.getDatasource() != null && doctorSendMind.getDatasource().size() > 0) {
                            if (doctorSendMind != null) {
                                for (int i = 0; i < doctorSendMind.getDatasource().size() && i < 3; i++) {
                                    DoctorSendMind doctorSendMind1 = doctorSendMind.getDatasource().get(i);
                                    View view = LayoutInflater.from(mActivity).inflate(R.layout.item_send_kindness, null);
                                    TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
                                    TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
                                    TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
                                    tv_name.setText(doctorSendMind1.getNickname());
                                    tv_content.setText(doctorSendMind1.getThankcontent());
                                    tv_time.setText(doctorSendMind1.getCreateDate());
                                    llytKindness.addView(view);
                                }
                                tvXyqCount.setText("心意墙(" + doctorSendMind.getDatasource().size() + ")");
                            }
                        }
                    }
                }

                @Override
                public void dataError(String result) {

                }
            }, doctorID);
        }
    }

    private void setDate(DoctorDetail.DatasourceEntity datasource) {

        if (datasource.isIsFollow()) {
            tvAttention.setSelected(true);
            tvAttention.setText("已关注");
        } else {
            tvAttention.setSelected(false);
            tvAttention.setText("关注ta");
        }

        x.image().bind(icon, datasource.getHeadimgurl(), MyApplication.getOptionsPhoto());

        name.setText(datasource.getDoctorName());
        prefossion.setText(datasource.getPosition());
        hospitalName.setText(datasource.getHopital());
        address.setText(datasource.getArea());
        specialZoom.setText(datasource.getGoodat());
        tvSynopsis.setText(datasource.getIntroduce());
        if (datasource.getComment() != null && datasource.getComment().size() > 0) {
            tvCommentCount.setText("用户评价(" + datasource.getComment().size() + ")");
            for (int i = 0; i < datasource.getComment().size(); i++) {
                DoctorDetail.DatasourceEntity.CommentEntity commentEntity = datasource.getComment().get(i);
                View view = LayoutInflater.from(this).inflate(R.layout.item_doctor_comment, null);
                String opinions = commentEntity.getOpinions();
                if (opinions != null && !opinions.isEmpty()) {
                    split = opinions.split(";");
                }
                if (split == null) {
                    split = new String[]{};
                }
                TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
                TextView tv_evaluate = (TextView) view.findViewById(R.id.tv_evaluate);
                TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
                TextView evaluate_1 = (TextView) view.findViewById(R.id.evaluate_1);
                TextView evaluate_2 = (TextView) view.findViewById(R.id.evaluate_2);
                TextView evaluate_3 = (TextView) view.findViewById(R.id.evaluate_3);
                TextView evaluate_4 = (TextView) view.findViewById(R.id.evaluate_4);
                if (split.length == 1) {
                    evaluate_1.setText(split[0]);
                    evaluate_2.setVisibility(View.GONE);
                    evaluate_3.setVisibility(View.GONE);
                    evaluate_4.setVisibility(View.GONE);
                } else if (split.length == 2) {
                    evaluate_1.setText(split[0]);
                    evaluate_2.setText(split[1]);
                    evaluate_3.setVisibility(View.GONE);
                    evaluate_4.setVisibility(View.GONE);
                } else if (split.length == 3) {
                    evaluate_1.setText(split[0]);
                    evaluate_2.setText(split[1]);
                    evaluate_3.setText(split[2]);
                    evaluate_4.setVisibility(View.GONE);
                } else if (split.length == 4) {
                    evaluate_1.setText(split[0]);
                    evaluate_2.setText(split[1]);
                    evaluate_3.setText(split[2]);
                    evaluate_4.setText(split[3]);
                } else {
                    evaluate_1.setVisibility(View.GONE);
                    evaluate_2.setVisibility(View.GONE);
                    evaluate_3.setVisibility(View.GONE);
                    evaluate_4.setVisibility(View.GONE);
                }
                tv_evaluate.setText(commentEntity.getSatisfaction());
                tv_name.setText(commentEntity.getName());
                tv_content.setText(commentEntity.getContent());
                llytComment.addView(view);
            }
        } else {
            llyt_comment_gone.setVisibility(View.GONE);
        }
    }

    //立即咨询弹出框
    private void initDialog() {
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        View view = inflater.inflate(R.layout.layout_dialog_consult, null);

        mDialog = new Dialog(mActivity, R.style.MyDialog);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);

        tv_content = (TextView) view.findViewById(R.id.tv_content);

        tv_once_consult = (TextView) view.findViewById(R.id.tv_once_consult);
        tv_once_consult.setOnClickListener(this);
    }


    @OnClick({R.id.layout_network_error, R.id.llyt_picture_consult, R.id.tv_synopsis, R.id.tv_send_flowers, R.id.tv_attention, R.id.tv_all_comment, R.id.tv_all_kindness})
    public void onClick(View v) {
        switch (v.getId()) {
            //网络错误
            case R.id.layout_network_error:
                requestData();
                sslv.setVisibility(View.GONE);
                layout_loading.setVisibility(View.VISIBLE);
                layout_network_error.setVisibility(View.GONE);
                break;
            //全部评论
            case R.id.tv_all_comment:
//                Intent AllAssessActivity = new Intent(mActivity, AllAssessActivity.class);
//                ArrayList list = (ArrayList) doctorDetail.getDatasource().getComment();
//                AllAssessActivity.putExtra("data", list);
//                startActivity(AllAssessActivity);
                break;
            //全部心意
            case R.id.tv_all_kindness:
//                Intent AllKindnessActivity = new Intent(mActivity, AllKindnessActivity.class);
//                ArrayList<DoctorSendMind> list2 = (ArrayList) doctorSendMind.getDatasource();
//
//                AllKindnessActivity.putExtra("data2", list2);
//                startActivity(AllKindnessActivity);
                break;
            //送心意
            case R.id.tv_send_flowers:
//                Intent sendFlower = new Intent(mActivity, SendFlowerActivity.class);
//                sendFlower.putExtra("doctorId", doctorID);
//                sendFlower.putExtra("icon", doctorDetail.getDatasource().getHeadimgurl());
//                sendFlower.putExtra("doctorName", doctorDetail.getDatasource().getDoctorName());
//                startActivity(sendFlower);
                break;
            //图文咨询
            case R.id.llyt_picture_consult:
                if (mDialog != null)
                    mDialog.show();
                break;
            //立即就诊
            case R.id.tv_once_consult:
                if (doctorDetail != null) {
//                    Intent vip = new Intent(mActivity, VIPActivity.class);
//                    vip.putExtra("doctorName", doctorDetail.getDatasource().getDoctorName());
//                    vip.putExtra("doctorID", doctorDetail.getDatasource().getDoctorId());
//                    vip.putExtra("username", doctorDetail.getDatasource().getUsername());
//                    vip.putExtra("icon", doctorDetail.getDatasource().getHeadimgurl());
//                    startActivity(vip);
//                    UIHelper.animSwitchActivity(mActivity, AnimDisplayMode.PUSH_LEFT);
                    mDialog.dismiss();
                }
                break;
            //简介
            case R.id.tv_synopsis:
//                Intent introduce = new Intent(mActivity, DoctorIntroduceActivity.class);
//                introduce.putExtra("position", doctorDetail.getDatasource().getGoodat());
//                introduce.putExtra("introduce", doctorDetail.getDatasource().getIntroduce());
//                startActivity(introduce);
                break;
            case R.id.back:
                onBackPressed();
                break;
            //关注
            case R.id.tv_attention:
                if (MyApplication.getInstance().isUserLogin()) {
                    takeAttention();
                } else {
                    Intent login = new Intent(mActivity, LoginActivity.class);
                    startActivity(login);
                }
                break;
        }
    }

    //关注
    public void takeAttention() {
        AppManager.getUserRequest().takeAttention(mActivity, new HttpTaskListener() {

            @Override
            public void dataSucceed(String result) {
                Attention entity = new Gson().fromJson(result, Attention.class);
                if ("关注成功".equals(entity.getMessage())) {
                    tvAttention.setSelected(true);
                    tvAttention.setText("已关注");
                    tvAttention.setSelected(true);
                } else {
                    tvAttention.setSelected(false);
                    tvAttention.setText("关注ta");
                    tvAttention.setSelected(false);
                }
                AppManager.showToast(mActivity, entity.getMessage());
            }

            @Override
            public void dataError(String result) {

            }
        }, doctorDetail.getDatasource().getDoctorId(), 1);
    }
}
