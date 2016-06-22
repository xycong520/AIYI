package com.xiuman.xinjiankang.activity;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.base.BaseActivity;
import com.xiuman.xinjiankang.bean.ActionValue;
import com.xiuman.xinjiankang.bean.User;
import com.xiuman.xinjiankang.bean.UserLevel;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.utils.AppSpUtil;
import com.xiuman.xinjiankang.utils.logger.Logger;

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
    @Bind(R.id.btn_login_login)
    Button btLogin;

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


        AppManager.showDialog(this);
        AppManager.getUserRequest().getUserLogin(this, new HttpTaskListener() {
                    @Override
                    public void dataSucceed(String result) {
                        try {
                            ActionValue<User> value = new Gson().fromJson(result,
                                    new TypeToken<ActionValue<User>>() {
                                    }.getType());
                            if (value.isSuccess()) {
                                String user_info = new Gson().toJson(value.getDatasource().get(0));
                                // 保存用户登录信息
                                AppSpUtil.getInstance().saveUserInfo(user_info);
                                User user = new Gson().fromJson(user_info,User.class);
                                getUserLevelInfo(user.getUser_id());
                            } else {
                                AppManager.dismiss();
                                AppManager.showToast(mActivity, value.getMessage());
                            }

                        } catch (JsonSyntaxException e) {
                            AppManager.dismiss();
                            AppManager.showToast(mActivity, "数据格式化错误");
                            e.printStackTrace();
                        }

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
                        AppManager.dismiss();
                    }
                },
                user_name, user_psw);

    }

    /**
     * 描述：获取用户等级信息
     */
    public void getUserLevelInfo(String user_name) {
        AppManager.getUserRequest().getBBSUserInfo(this,
                new HttpTaskListener() {
                    @Override
                    public void dataSucceed(String result) {
                        ActionValue<UserLevel> value = new Gson().fromJson(result,
                                new TypeToken<ActionValue<UserLevel>>() {
                                }.getType());
                        if (value != null && value.isSuccess()) {
                            String user_info = new Gson().toJson(value.getDatasource().get(
                                    0));
                            // 保存用户等级信息
                            AppSpUtil.getInstance().saveUserLevel(user_info);
                            AppManager.dismiss();
                            AppManager.showToast(mActivity, "登录成功");
                        }
                        login2HX();
                    }

                    @Override
                    public void dataError(String result) {
                        AppManager.dismiss();
                        AppManager.showToast(mActivity, result);
                        login2HX();
                    }
                }, user_name);
    }

    /**
     * 登录到环信
     */
    private void login2HX() {
        String username = et_login_user_name.getText().toString().trim();
        EMClient.getInstance().login(username, "123456", new EMCallBack() {
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Logger.i("login", "登录聊天服务器成功！");
                finish();
            }

            @Override
            public void onError(int i, String s) {
                Logger.i("login", "登录聊天服务器失败！" + s);
                finish();
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}
