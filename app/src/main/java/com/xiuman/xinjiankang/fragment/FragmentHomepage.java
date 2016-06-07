package com.xiuman.xinjiankang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.Request.UserRequest;
import com.xiuman.xinjiankang.adapter.HomepageAdapter;
import com.xiuman.xinjiankang.bean.BeanCommonViewType;
import com.xiuman.xinjiankang.bean.BeanHomeTitle;
import com.xiuman.xinjiankang.bean.ScienceDetail;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.net.Wrapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首页
 * Created by PCPC on 2016/5/24.
 */
public class FragmentHomepage extends Fragment {

    View thisView;

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    HomepageAdapter mAdapter;
    List<BeanCommonViewType> viewTypes;
    FragmentAD fragmentAD;
    FragmentRecommendDoctor fragmentRecommendDoctor;
    FragmentRecommendHospitor fragmentRecommendHospitor;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (thisView == null) {
            thisView = inflater.inflate(R.layout.fragment_home, container, false);
            ButterKnife.bind(this, thisView);
            init();
        }

        ViewGroup parent = (ViewGroup) thisView.getParent();
        if (parent != null) {
            parent.removeView(thisView);
        }
        return thisView;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter!=null){
            mAdapter.notifyDataSetChanged();
        }
    }

    private void loadData() {
        new UserRequest().getScienceTechologyList2(getActivity(), new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                Wrapper<ScienceDetail> ScienceDetail = new Gson().fromJson(result, new TypeToken<Wrapper<ScienceDetail>>() {
                }.getType());
                for (int i = 0; i < 3; i++) {
                    ScienceDetail scienceDetail = ScienceDetail.getDatasource().get(i);
                    BeanCommonViewType newsItem = new BeanCommonViewType();
                    newsItem.setBeanObj(scienceDetail);
                    newsItem.setViewType(HomepageAdapter.VIEWTYPE_LISTITEM);
                    viewTypes.add(6,newsItem);
                }
                mAdapter.setHomeViews(viewTypes);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void dataError(String result) {
            }
        });
    }


    private void init() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter = new HomepageAdapter(viewTypes, this));
        viewTypes = new ArrayList<>();
        //添加广告Fragment
        BeanCommonViewType typeAD = new BeanCommonViewType();
        typeAD.setViewType(HomepageAdapter.VIEWTYPE_AD);
        typeAD.setBeanObj(fragmentAD = new FragmentAD());
        viewTypes.add(typeAD);
        //添加资讯布局
        BeanCommonViewType typeZIXUN = new BeanCommonViewType();
        typeZIXUN.setViewType(HomepageAdapter.VIEWTYPE_ZIXUN);
        viewTypes.add(typeZIXUN);
        //添加心理布局
        BeanCommonViewType typeXinli = new BeanCommonViewType();
        typeXinli.setViewType(HomepageAdapter.VIEWTYPE_XINLI);
        viewTypes.add(typeXinli);
        //添加医师推荐标题布局
        BeanCommonViewType typeTitleRecommed = new BeanCommonViewType();
        typeTitleRecommed.setViewType(HomepageAdapter.VIEWTYPE_TITLE);
        BeanHomeTitle title = new BeanHomeTitle();
        title.setImgID(R.mipmap.xjk_ic_recommed_doctor);
        title.setRightText("排行榜");
        title.setTitleName("推荐医师");
        typeTitleRecommed.setBeanObj(title);
        viewTypes.add(typeTitleRecommed);
        //添加医师推荐Fragment
        BeanCommonViewType typeRecommendDoctor = new BeanCommonViewType();
        typeRecommendDoctor.setViewType(HomepageAdapter.VIEWTYPE_DOCTORLAYOUT);
        typeRecommendDoctor.setBeanObj(fragmentRecommendDoctor = new FragmentRecommendDoctor());
        viewTypes.add(typeRecommendDoctor);
        //添加资讯信息布局
        typeTitleRecommed = new BeanCommonViewType();
        typeTitleRecommed.setViewType(HomepageAdapter.VIEWTYPE_TITLE);
        title = new BeanHomeTitle();
        title.setImgID(R.mipmap.xjk_ic_news);
        title.setRightText("更多");
        title.setTitleName("资讯信息");
        typeTitleRecommed.setBeanObj(title);
        viewTypes.add(typeTitleRecommed);
        //添加推荐医院标题布局
        BeanCommonViewType titleHospitor = new BeanCommonViewType();
        titleHospitor.setViewType(HomepageAdapter.VIEWTYPE_TITLE);
        title = new BeanHomeTitle();
        title.setTitleName("推荐医院");
        title.setRightText("附近");
        title.setImgID(R.mipmap.xjk_pic_hospital);
        titleHospitor.setBeanObj(title);
        viewTypes.add(titleHospitor);
        //添加推荐医院Fragment
        BeanCommonViewType typeHospitor = new BeanCommonViewType();
        typeHospitor.setViewType(HomepageAdapter.VIEWTYPE_HOSPITALlAYOUT);
        typeHospitor.setBeanObj(fragmentRecommendHospitor = new FragmentRecommendHospitor());
        viewTypes.add(typeHospitor);
        mAdapter.setHomeViews(viewTypes);
        mAdapter.notifyDataSetChanged();
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mRecyclerView.setAdapter(mAdapter = new HomepageAdapter(viewTypes, this));
        mRecyclerView.setHasFixedSize(true);
        loadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
         try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
