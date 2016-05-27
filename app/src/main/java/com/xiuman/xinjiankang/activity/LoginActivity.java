package com.xiuman.xinjiankang.activity;

import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.base.BaseActivity;

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
        TextView tvLoading = (TextView) findViewById(R.id.tvLoading);
        tvLoading.setVisibility(View.VISIBLE);
        JumpingBeans jumpingBeans ;
        jumpingBeans = JumpingBeans.with(tvLoading).appendJumpingDots()
                .build();
        String user_name = et_login_user_name.getText().toString().trim();
        String user_psw = et_login_user_psw.getText().toString().trim();
        if (user_name.equals("")) {
            AppManager.showToast(this, "请输入您的用户名！");
            jumpingBeans.stopJumping();
            tvLoading.setVisibility(View.GONE);
            return;
        } else if (user_psw.equals("")) {
            AppManager.showToast(this, "请输入您的密码！");
            jumpingBeans.stopJumping();
            tvLoading.setVisibility(View.GONE);
            return;
        }

        ;
//        llyt_loading.setVisibility(View.VISIBLE);
//        ModelManager.getInstance().getUserRequest().getUserLogin(this, new TaskUserLoginBack(handler), URLConfig.USER_LOGION,
//                user_name, user_psw);
    }

}
