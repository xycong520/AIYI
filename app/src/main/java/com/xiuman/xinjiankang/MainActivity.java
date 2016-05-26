package com.xiuman.xinjiankang;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.base.BaseActivity;
import com.xiuman.xinjiankang.fragment.DemoFragmet;
import com.xiuman.xinjiankang.fragment.FragmentHomepage;
import com.xiuman.xinjiankang.widget.WechatRadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by PCPC on 2016/5/24.
 */
public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.radiogroup)
    WechatRadioGroup gradualRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupToolbar();
        List<Fragment> list = new ArrayList<Fragment>();
        FragmentHomepage fragmentHomepage = new FragmentHomepage();
        list.add(fragmentHomepage);
        for (int i = 1; i < 4; i++) {
            DemoFragmet fragment = new DemoFragmet();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            fragment.setArguments(bundle);
            list.add(fragment);
        }
        viewPager.setAdapter(new DemoPagerAdapter(getSupportFragmentManager(), list));
        viewPager.addOnPageChangeListener(this);
        gradualRadioGroup.setViewPager(viewPager);
        setToolbarTitle("首页");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        setSwipeBackEnable(false);
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
