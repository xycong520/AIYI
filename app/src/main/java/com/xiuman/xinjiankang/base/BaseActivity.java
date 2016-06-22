package com.xiuman.xinjiankang.base;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.swipeback.SwipeBackLayout;
import com.xiuman.xinjiankang.utils.AnimDisplayMode;
import com.xiuman.xinjiankang.utils.UIHelper;

import butterknife.ButterKnife;


/**
 * Created by PCPC on 2016/5/24.
 */
public abstract class BaseActivity extends BaseSwipeActivity {
    Toolbar toolbar;
    AppBarLayout appBarLayout;

    private View view;
    protected LayoutInflater inflater;
    private SwipeBackLayout mSwipeBackLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.addActivity(this);
        setContentView(getView());
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setScrimColor(Color.TRANSPARENT);
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);// 右滑结束
        inflater = LayoutInflater.from(this);
        initView();
        initData();
        /*if (AppManager.currentActivity().getLocalClassName().startsWith("com.xiuman.xinjiankang.activity.ChatActivity")){
            //注册监听
            EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {

                @Override
                public void onMessageReceived(List<EMMessage> messages) {
                    //收到消息
                }

                @Override
                public void onCmdMessageReceived(List<EMMessage> messages) {
                    //收到透传消息
                }

                @Override
                public void onMessageReadAckReceived(List<EMMessage> messages) {
                    //收到已读回执
                }

                @Override
                public void onMessageDeliveryAckReceived(List<EMMessage> message) {
                    //收到已送达回执
                }

                @Override
                public void onMessageChanged(EMMessage message, Object change) {
                    //消息状态变动
                }
            });
        }*/
    }
    protected abstract void initView();

    protected abstract int getView();

    //设置Toolbar
    protected void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);

        if (toolbar != null) {
            //设置toolbar作为actionbar
            setSupportActionBar(toolbar);

            // Set up the action bar.
            final ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setHomeAsUpIndicator(R.mipmap.xjk_back_icon);
                ab.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //设置工具栏标题
    protected void setToolbarTitle(String title) {
        // Set up the action bar.
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }
    }


    @Override
    protected void setContentViewId(int layoutResId) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void findViewById() {

    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View view) {

    }
    @Override
    public void onBackPressed() {
        finish();
        UIHelper.animSwitchActivity(this, AnimDisplayMode.PUSH_RIGHT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.finishActivity();
    }

    public boolean isBackground() {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(2).get(1).topActivity;
        //当前界面如果不是在xinjiankang包下则判断为应用在后台
        if (cn.getClassName().startsWith("com.xiuman.xinjiankang")){
            return false;
        }
        return false;
    }
}
