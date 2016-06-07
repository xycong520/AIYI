package com.xiuman.xinjiankang.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.Request.UserRequest;
import com.xiuman.xinjiankang.activity.ScientifitDetailActivity;
import com.xiuman.xinjiankang.bean.Advertisiment;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.net.Wrapper;
import com.xiuman.xinjiankang.utils.SizeUtil;
import com.xiuman.xinjiankang.widget.viewpager.AutoScrollViewPager;
import com.xiuman.xinjiankang.widget.viewpager.JazzyViewPager;

import org.xutils.x;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 广告
 * Created by PCPC on 2016/5/24.
 */
public class FragmentAD extends Fragment {
    //广告
    @Bind(R.id.my_pager)
    AutoScrollViewPager myPager;
    //广告栏指示器
    @Bind(R.id.indicator)
    LinearLayout indicator;
    @Bind(R.id.rlyt_pager)
    RelativeLayout rlyt_pager;
    private List<Advertisiment> list;
    private ImageView[] indicators;
    private Activity mActivity;

    View thisView;
    Wrapper<Advertisiment> wrapper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if(thisView == null) {
            thisView = inflater.inflate(R.layout.fragment_ad, container, false);
            ButterKnife.bind(this, thisView);
            //初始化
            {
                int myPagerHeight = (int) (SizeUtil.getScreenHeight() * (372 / 1334f));
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(SizeUtil.getScreenWidth(), myPagerHeight);
                rlyt_pager.setLayoutParams(params2);
                mActivity = getActivity();
                loadData();
            }
        }
        //因为共用一个Fragment视图，所以当前这个视图已被加载到Activity中，必须先清除后再加入Activity
        ViewGroup parent = (ViewGroup)thisView.getParent();
        if(parent != null) {
            parent.removeView(thisView);
        }

        return thisView;
    }

    //初始化viewpager角标
    private void initPager(Wrapper<Advertisiment> wrapper) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 5, 5, 5);
        list = wrapper.getDatasource();
        indicators = new ImageView[list.size()];
        indicator.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            indicators[i] = new ImageView(mActivity);
            indicators[i].setLayoutParams(params);
            indicators[i].setBackgroundResource(R.mipmap.xjk_indicator_nor);
            if (i == 0) {
                indicators[i].setBackgroundResource(R.mipmap.xjk_indicator_now);
            }
            indicator.addView(indicators[i]);
        }
        if (list != null && list.size() != 0) {
            myPager.setAdapter(new AdPagerAdapter(mActivity, list, myPager));
            myPager.setTransitionEffect(JazzyViewPager.TransitionEffect.Tablet);
            myPager.setPageMargin(30);
            myPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    position = position % list.size();
                    for (int i = 0; i < list.size(); i++) {
                        indicators[i].setBackgroundResource(R.mipmap.xjk_indicator_nor);
                        if (i == position) {
                            indicators[i].setBackgroundResource(R.mipmap.xjk_indicator_now);
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            myPager.setInterval(3000);
            myPager.setAutoScrollDurationFactor(5);
            myPager.startAutoScroll();
        }
    }

    public void loadData() {
        if (wrapper!=null){
            initPager(wrapper);
        }
        new UserRequest().getAdvertisement(mActivity, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                wrapper = new Gson().fromJson(result, new TypeToken<Wrapper<Advertisiment>>() {}.getType());
                initPager(wrapper);
            }

            @Override
            public void dataError(String result) {
            }
        });
    }

    public View getThisView() {
        return thisView;
    }

    class AdPagerAdapter extends PagerAdapter {
        private List<Advertisiment> list;
        private Context context;
        private int count;
        private AutoScrollViewPager mpager;
        public AdPagerAdapter(Activity mActivity, List<Advertisiment> list,AutoScrollViewPager vp) {
            this.list = list;
            this.context = mActivity;
            this.mpager = vp;

        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object instantiateItem(ViewGroup container,int position) {
            count = position;
            position = position % list.size();
            View view = View.inflate(context, R.layout.item_ad_image, null);
            ImageView iv_ad = (ImageView) view.findViewById(R.id.iv_ad);
            container.addView(view);
            Advertisiment adBean = list.get(position);
            x.image().bind(iv_ad,adBean.getImgUrl());
            iv_ad.setTag(adBean);
            iv_ad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Advertisiment bean = (Advertisiment) v.getTag();
                    if ("app".equalsIgnoreCase(bean.getJumpType())) {
                        if (bean.getQueryUrlOrValue()!=null && !"".equals(bean.getQueryUrlOrValue())) {
                            Intent intent = new Intent(context, ScientifitDetailActivity.class);
                            intent.putExtra("id", bean.getQueryUrlOrValue());
                            context.startActivity(intent);
                        }
                   /* }else {
                        Intent intent = new Intent(context, WebActivity.class);
                        intent.putExtra("url",bean.getQueryUrlOrValue());
                        context.startActivity(intent);*/
                    }
//                    MobclickAgent.onEvent(context,"USER_AD");
                }
            });
            mpager.setObjectForPosition(view, count);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
       /* try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }


}
