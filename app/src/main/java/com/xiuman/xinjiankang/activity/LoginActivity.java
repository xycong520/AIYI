package com.xiuman.xinjiankang.activity;

import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.bean.ActionValue;
import com.xiuman.xinjiankang.bean.User;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.base.BaseActivity;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.utils.AppSpUtil;

import net.frakbot.jumpingbeans.JumpingBeans;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by PCPC on 2016/5/27.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.et_login_user_name)
    MaterialEditText et_login_user_name;
    @Bind(R.id.et_login_user_psw)
    MaterialEditText et_login_user_psw;
    @Bind(R.id.tv_register)
    TextView tvRegister;
    @Bind(R.id.llyt_register)
    LinearLayout llytRegister;
    @Bind(R.id.llyt_forget_psw)
    LinearLayout llytForgetPsw;


    @Override
    protected void initView() {
        setupToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getView() {
        return R.layout.activity_login;
    }

    /**
     * 点击事件
     */
    @OnClick({R.id.btn_login_login, R.id.llyt_register, R.id.llyt_forget_psw})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_login:// 登陆
                login();
                break;
           /* case R.id.llyt_register:// 注册帐号
                startActivity(new Intent(this,UserRegisterGuideActivity.class));
                finish();
                overridePendingTransition(R.anim.translate_horizontal_start_in,
                        R.anim.translate_horizontal_start_out);
                break;

            case R.id.llyt_forget_psw://忘记密码
                Intent intent = new Intent(this, UserForgetPsdActivity.class);
                startActivity(intent);
                break;*/

        }
    }

    /**
     * @描述：用户登录 2014-8-12
     */
    private void login() {

        String user_name = et_login_user_name.getText().toString().trim();
        String user_psw = et_login_user_psw.getText().toString().trim();
        if (user_name.equals("")) {
            AppManager.showToast(this, "请输入您的用户名！");
            return;
        } else if (user_psw.equals("")) {
            AppManager.showToast(this, "请输入您的密码！");
            return;
        }
       final  TextView tvLoading = (TextView) findViewById(R.id.tvLoading);
        tvLoading.setVisibility(View.VISIBLE);
        final JumpingBeans jumpingBeans;
        jumpingBeans = JumpingBeans.with(tvLoading).appendJumpingDots()
                .build();
        AppManager.getUserRequest().getUserLogin(this, new HttpTaskListener() {
                    @Override
                    public void dataSucceed(String result) {
                        ActionValue<User> value = new Gson().fromJson(result,
                                new TypeToken<ActionValue<User>>() {
                                }.getType());
                        if (value.isSuccess()) {
                            String user_info = new Gson().toJson(value.getDatasource().get(0));
                            // 保存用户登录信息
                            AppSpUtil.getInstance().saveUserInfo(user_info);
                            AppManager.showToast(mActivity,"登录成功");
                        }
                        jumpingBeans.stopJumping();
                        tvLoading.setVisibility(View.GONE);
                        finish();
                        /*if(value.getDatasource().get(0).getVerify()!=20){
                            AppSpUtil.getInstance().deleteUserInfo();
                            UserApproveResultActivity.actionStart(UserLoginActivity.this,mUser);
                            ToastUtils.showToastShort(UserLoginActivity.this,mUser.getVerifyMsg());
                            finish();
                        }else {
                            //获取用户等级信息
                            getUserLevelInfo();
                        }*/
                    }

                    @Override
                    public void dataError(String result) {
                        jumpingBeans.stopJumping();
                        tvLoading.setVisibility(View.GONE);
                    }
                },
                user_name, user_psw);

    }

}
