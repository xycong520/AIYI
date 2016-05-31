package com.xiuman.xinjiankang;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.base.BaseActivity;
import com.xiuman.xinjiankang.fragment.FragmentConsult;
import com.xiuman.xinjiankang.fragment.FragmentHomepage;
import com.xiuman.xinjiankang.fragment.FragmentMe;
import com.xiuman.xinjiankang.fragment.FragmentNews;
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

    @Override
    protected void initView() {
        setupToolbar();
        List<Fragment> list = new ArrayList<Fragment>();
        FragmentHomepage fragmentHomepage = new FragmentHomepage();
        list.add(fragmentHomepage);
        FragmentConsult fragmentConsult = new FragmentConsult();
        list.add(fragmentConsult);
        FragmentNews fragmentNews = new FragmentNews();
        list.add(fragmentNews);
        FragmentMe fragmentMe = new FragmentMe();
        list.add(fragmentMe);
        viewPager.setAdapter(new DemoPagerAdapter(getSupportFragmentManager(), list));
        viewPager.addOnPageChangeListener(this);
        gradualRadioGroup.setViewPager(viewPager);
        setToolbarTitle("首页");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setSwipeBackEnable(false);
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
        switch (position) {
            case 0:
                setToolbarTitle("首页");
                break;
            case 1:
                setToolbarTitle("诊断");
                break;
            case 2:
                setToolbarTitle("资讯");
                break;
            case 3:
                setToolbarTitle("我的");
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class DemoPagerAdapter extends FragmentStatePagerAdapter {
        List<Fragment> mData;

        public DemoPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public DemoPagerAdapter(FragmentManager fm, List<Fragment> data) {
            super(fm);
            mData = data;
        }

        @Override
        public Fragment getItem(int position) {
            return mData.get(position);
        }

        @Override
        public int getCount() {
            return mData.size();
        }
    }
}
