package com.xiuman.xinjiankang.base;/**
 * Created by danding on 2015/4/15.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.swipeback.SwipeBackActivity;
import com.xiuman.xinjiankang.swipeback.SwipeBackLayout;


/**
 * 描述: 侧滑结束BaseActivity
 * 名称: BaseSwipeBackActivity
 * User: csx
 * Date: 04-15
 */
public abstract class BaseSwipeActivity extends SwipeBackActivity {
    protected int layoutResId;
    public SwipeBackLayout mSwipeBackLayout;
    public BaseSwipeActivity mActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        AppManager.getAppManager().addActivity(this);
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setScrimColor(Color.TRANSPARENT);
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);// 右滑结束
        setContentViewId(getLayoutResId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    /**
     * 设置布局id
     *
     * @param layoutResId
     */
    protected abstract void setContentViewId(int layoutResId);

    /**
     * 获取界面布局id
     *
     * @return
     */
    protected int getLayoutResId() {
        return layoutResId;
    }

    /**
     * @描述：数据初始化
     * @日期：2014-12-8
     */
    protected abstract void initData();

    /**
     * @描述：渲染界面
     * @日期：2014-12-8
     */
    protected abstract void findViewById();

    /**
     * @描述：界面初始化
     * @日期：2014-12-8
     */
    protected abstract void initUI();

    /**
     * @描述：设置监听
     * @日期：2014-12-8
     */
    protected abstract void setListener();

    /**
     * 点击事件
     * @param view
     */
    public abstract void onClick(View view);

    @Override
    public void onBackPressed() {
        scrollToFinishActivity();
    }


}
