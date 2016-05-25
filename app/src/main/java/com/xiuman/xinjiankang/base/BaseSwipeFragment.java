package com.xiuman.xinjiankang.base;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Fragment基类
 * Created by csx on 15/4/16.
 */
public abstract class BaseSwipeFragment extends Fragment {
    private int layoutResId;
    /**
     * 获取界面布局id
     * @return
     */
    protected int getLayoutResId(){
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
    protected abstract void findViewById(View container);

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

}
