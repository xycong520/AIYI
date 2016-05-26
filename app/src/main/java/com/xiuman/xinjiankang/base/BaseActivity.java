package com.xiuman.xinjiankang.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
        setContentView(getView());
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setScrimColor(Color.TRANSPARENT);
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);// 右滑结束
        inflater = LayoutInflater.from(this);
        initView();
        initData();
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
}
