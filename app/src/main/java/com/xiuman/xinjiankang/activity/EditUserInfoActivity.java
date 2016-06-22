package com.xiuman.xinjiankang.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.app.MyApplication;
import com.xiuman.xinjiankang.base.BaseActivity;
import com.xiuman.xinjiankang.bean.User;
import com.xiuman.xinjiankang.bean.UserLevel;
import com.xiuman.xinjiankang.utils.AppSpUtil;
import com.xiuman.xinjiankang.widget.CustomDialog;

import org.xutils.x;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by PCPC on 2016/6/16.
 */
public class EditUserInfoActivity extends BaseActivity {

    public static final String parameName = "name";
    public static final String parameLevel = "level";
    public static final String parameSex = "sex";


    @Bind(R.id.iv_userinfo_user_head)
    ImageView ivPhoto;
    @Bind(R.id.tv_userinfo_user_name)
    TextView tvName;
    @Bind(R.id.tv_userinfo_user_rank)
    TextView tvLevel;
    @Bind(R.id.tv_userinfo_user_sex)
    TextView tvSex;
    @Bind(R.id.btn_userinfo_exit)
    TextView tvExit;

    @Override
    protected void initView() {
        setupToolbar();
        initUserInfo();
    }

    private void initUserInfo() {
        User user = AppSpUtil.getInstance().getUserInfo();
        if (user.isDoctor()) {
            tvName.setText(user.getDoctorName());
            x.image().bind(ivPhoto, user.getDoctorHead(), MyApplication.optionsCircularPhoto);
        } else {
            x.image().bind(ivPhoto, user.getAvatar(), MyApplication.optionsCircularPhoto);
            tvName.setText(user.getNickname());
        }
        UserLevel level = AppSpUtil.getInstance().getUserLevel();
        //等级信息如果为空。隐藏
        if (level == null) {
            tvLevel.setVisibility(TextView.INVISIBLE);
        } else {
            tvLevel.setText("LV" + level.getGroupId() + " "
                    + level.getGroupName());
            String user_sex = "";
            user_sex = user.getGender();
            tvSex.setText(user_sex);
            if (user_sex == null) {
                tvSex.setText("保密");
            } else if (user_sex.equals("male")) {
                tvSex.setText("男");
            } else if (user_sex.equals("female")) {
                tvSex.setText("女");
            }
        }
    }

    @Override
    protected int getView() {
        return R.layout.activity_userinfo;
    }

    @Override
    @OnClick({R.id.btn_userinfo_exit})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_userinfo_exit:
                showExitDialog();
                break;
        }
    }

    /**
     * 描述：显示退出登录对话框 2014-8-12
     */
    CustomDialog dialog_exit;

    private void showExitDialog() {

        dialog_exit = new CustomDialog(this, "退出登录", "确定退出？退出后将清空用户数据！");
        dialog_exit.dialog_message.setTextSize(14);
        dialog_exit.show();
        dialog_exit.btn_custom_dialog_sure
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog_exit.dismiss();
                        logoutHX();
                    }
                });
        dialog_exit.btn_custom_dialog_cancel
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog_exit.dismiss();
                    }
                });
    }

    /**
     * 退出环信
     */
    private void logoutHX() {
        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                AppManager.loginOut();
                finish();
            }

            @Override
            public void onError(int i, String s) {
                AppManager.showToast(mActivity, s);
            }

            @Override
            public void onProgress(int i, String s) {
            }
        });
    }
}
