package com.xiuman.xinjiankang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.bean.ScientificClassify;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.net.Wrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 资讯
 * Created by PCPC on 2016/5/31.
 */
public class FragmentNews extends Fragment {
    View thisView;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    MyAdapter mAdapter;
    @Bind(R.id.tabLayout)
    SlidingTabLayout tabLayout;

    private String[] titles;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (thisView == null) {
            thisView = inflater.inflate(R.layout.fragment_news, container, false);
            ButterKnife.bind(this, thisView);
            init();
        }else{
            mViewPager.setAdapter(mAdapter = new MyAdapter(getChildFragmentManager(),fragments));
            tabLayout.setViewPager(mViewPager, titles);
        }

        ViewGroup parent = (ViewGroup) thisView.getParent();
        if (parent != null) {
            parent.removeView(thisView);
        }
        return thisView;
    }

    private void init(){
        loadData();
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                ((FragmentScientifitChild)fragments.get(position)).loadDataFromParent();
            }

            @Override
            public void onTabReselect(int position) {
//                ((FragmentScientifitChild)fragments.get(position)).loadDataFromParent();
            }
        });
    }

    private void loadData() {
        AppManager.getUserRequest().getScienceTechologyClassify(getActivity(), new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                Wrapper<ScientificClassify> value = new Gson().fromJson(result, new TypeToken<Wrapper<ScientificClassify>>(){}.getType());
                if (value != null) {
                    List<ScientificClassify> datasource = value.getDatasource();
                    if (datasource!=null && datasource.size()!=0){
                        if (datasource != null) {
                            titles = new String[datasource.size()];
                            for (int i = 0; i < datasource.size(); i++) {
                                FragmentScientifitChild scientifitChild = new FragmentScientifitChild();
                                Bundle bundle = new Bundle();
                                bundle.putString("categoryId", datasource.get(i).getId());
                                titles[i] = datasource.get(i).getName();
                                scientifitChild.setArguments(bundle);
                                fragments.add(scientifitChild);
                            }
                            mViewPager.setAdapter(mAdapter = new MyAdapter(getChildFragmentManager(), fragments));
                            tabLayout.setViewPager(mViewPager, titles);
                        }
                    }
                }
            }

            @Override
            public void dataError(String result) {

            }
        });
    }

    private class MyAdapter extends FragmentPagerAdapter {
        private List<Fragment> list;

        public MyAdapter(FragmentManager fm, ArrayList<Fragment> list) {
            super(fm);
            this.list = list;
        }

//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            if (list.get(position).getView().getParent()==null){
//                container.addView(list.get(position).getView());
//            }else{
//                ((ViewGroup)list.get(position).getView().getParent()).removeView(list.get(position).getView());
//            }
//            container.addView(list.get(position).getView());
//            return list.get(position);
//        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return  list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        fragments.clear();
//        thisView = null;
//        titles = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        /*try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }*/
    }
}
