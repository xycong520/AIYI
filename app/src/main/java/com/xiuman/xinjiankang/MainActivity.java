package com.xiuman.xinjiankang;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.NetUtils;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.activity.SearchActivity;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.base.BaseActivity;
import com.xiuman.xinjiankang.fragment.FragmentConsult;
import com.xiuman.xinjiankang.fragment.FragmentHomepage;
import com.xiuman.xinjiankang.fragment.FragmentMe;
import com.xiuman.xinjiankang.fragment.FragmentNews;
import com.xiuman.xinjiankang.utils.logger.Logger;
import com.xiuman.xinjiankang.widget.CustomDialog;
import com.xiuman.xinjiankang.widget.WechatRadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by PCPC on 2016/5/24.
 */
public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.radiogroup)
    WechatRadioGroup gradualRadioGroup;
    FragmentHomepage fragmentHomepage;
    FragmentConsult fragmentConsult;
    FragmentNews fragmentNews;
    FragmentMe fragmentMe;
    //标记搜索图表是否显示
    boolean isSearchShow;
    //标记是否是诊断页进入搜索
    boolean isConsul;

    @Override
    protected void initView() {
        setupToolbar();
        List<Fragment> list = new ArrayList<Fragment>();
        fragmentHomepage = new FragmentHomepage();
        list.add(fragmentHomepage);
        fragmentConsult = new FragmentConsult();
        list.add(fragmentConsult);
        fragmentNews = new FragmentNews();
        list.add(fragmentNews);
        fragmentMe = new FragmentMe();
        list.add(fragmentMe);
        viewPager.setAdapter(new DemoPagerAdapter(getSupportFragmentManager(), list));
        viewPager.addOnPageChangeListener(this);
        gradualRadioGroup.setViewPager(viewPager);
        setToolbarTitle("首页");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setSwipeBackEnable(false);
        //注册一个监听链接状态
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
    }

    @Override
    protected int getView() {
        return R.layout.activity_main;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    @Override
    public void onPageSelected(int position) {
        ActionBar actionBar = getSupportActionBar();
        switch (position) {
            case 0:
                actionBar.setTitle("首页");
                isSearchShow = false;
                break;
            case 1:
                actionBar.setTitle("诊断");
                if (fragmentConsult != null) {
                    fragmentConsult.loadTypeData();
                }
                isSearchShow = true;
                isConsul = true;
                break;
            case 2:
                actionBar.setTitle("资讯");
                isSearchShow = true;
                isConsul = false;
                break;
            case 3:
                actionBar.setTitle("我的");
                isSearchShow = false;
                break;
        }
        invalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isSearchShow) {
            menu.findItem(R.id.search).setVisible(true);
        } else {
            menu.findItem(R.id.search).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class DemoPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> mData;

        public DemoPagerAdapter(FragmentManager fm, List<Fragment> data) {
            super(fm);
            mData = data;
        }

        @Override
        public Fragment getItem(int position) {
            return mData.get(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public int getCount() {
            return mData.size();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search) {
            Intent intent = new Intent(mActivity, SearchActivity.class);
            intent.putExtra(SearchActivity.parameIsConsul,isConsul);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }
    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }

        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                        final CustomDialog dialog = new CustomDialog(mActivity,"安全提醒","您的账号在其他设备上登录，如果不是本人操作请尽快修改密码！");
                        dialog.show();
                        dialog.btn_custom_dialog_sure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppManager.loginOut();
                                EMClient.getInstance().logout(true);
                                if (fragmentMe!=null){
                                    fragmentMe.onResume();
                                }
                                dialog.dismiss();
                            }
                        });
                    } else {
                        if (NetUtils.hasNetwork(mActivity)) {
                           Logger.w("连接不到聊天服务器");
                        } else {
                            AppManager.showToast(mActivity,"当前网络不可用，请检查网络设置");
                        }
                    }
                }
            });
        }
    }
}
