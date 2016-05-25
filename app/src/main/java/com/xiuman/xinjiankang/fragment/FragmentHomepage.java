package com.xiuman.xinjiankang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.Bean.BeanHomeTitle;
import com.xiuman.xinjiankang.Bean.BeanHomeView;
import com.xiuman.xinjiankang.Request.UserRequest;
import com.xiuman.xinjiankang.adapter.HomepageAdapter;
import com.xiuman.xinjiankang.base.ScienceDetail;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.net.Wrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by PCPC on 2016/5/24.
 */
public class FragmentHomepage extends Fragment {

    View thisView;

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    HomepageAdapter mAdapter;
    List<BeanHomeView> viewTypes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (thisView == null) {
            thisView = inflater.inflate(R.layout.fragment_home, container, false);
            ButterKnife.bind(this, thisView);
            init();
        }
        //因为共用一个Fragment视图，所以当前这个视图已被加载到Activity中，必须先清除后再加入Activity
        ViewGroup parent = (ViewGroup) thisView.getParent();
        if (parent != null) {
            parent.removeView(thisView);
        }
        return thisView;

    }

    private void loadData() {
        new UserRequest().getScienceTechologyList2(getActivity(), new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                Wrapper<ScienceDetail> ScienceDetail = new Gson().fromJson(result, new TypeToken<Wrapper<ScienceDetail>>() {
                }.getType());
                for (int i = 0; i < 3; i++) {
                    ScienceDetail scienceDetail = ScienceDetail.getDatasource().get(i);
                    BeanHomeView newsItem = new BeanHomeView();
                    newsItem.setBeanObj(scienceDetail);
                    newsItem.setViewType(HomepageAdapter.VIEWTYPE_LISTITEM);
                    viewTypes.add(newsItem);
                }
                BeanHomeView titleHospitor = new BeanHomeView();
                titleHospitor.setViewType(HomepageAdapter.VIEWTYPE_TITLE);
                BeanHomeTitle title = new BeanHomeTitle();
                title.setTitleName("推荐医院");
                title.setRightText("附近");
                title.setImgID(R.mipmap.xjk_pic_hospital);
                titleHospitor.setBeanObj(title);
                viewTypes.add(titleHospitor);
                BeanHomeView typeHospitor = new BeanHomeView();
                typeHospitor.setViewType(HomepageAdapter.VIEWTYPE_HOSPITALlAYOUT);
                viewTypes.add(typeHospitor);
            }

            @Override
            public void dataError(String result) {
            }
        });
    }

    private void init() {
        viewTypes = new ArrayList<>();
        BeanHomeView typeAD = new BeanHomeView();
        typeAD.setViewType(HomepageAdapter.VIEWTYPE_AD);
        viewTypes.add(typeAD);
        BeanHomeView typeZIXUN = new BeanHomeView();
        typeZIXUN.setViewType(HomepageAdapter.VIEWTYPE_ZIXUN);
        viewTypes.add(typeZIXUN);
        BeanHomeView typeXinli = new BeanHomeView();
        typeXinli.setViewType(HomepageAdapter.VIEWTYPE_XINLI);
        viewTypes.add(typeXinli);
        BeanHomeView typeTitleRecommed = new BeanHomeView();
        typeTitleRecommed.setViewType(HomepageAdapter.VIEWTYPE_TITLE);
        BeanHomeTitle title = new BeanHomeTitle();
        title.setImgID(R.mipmap.xjk_ic_recommed_doctor);
        title.setRightText("排行榜");
        title.setTitleName("推荐医师");
        typeTitleRecommed.setBeanObj(title);
        viewTypes.add(typeTitleRecommed);
        BeanHomeView typeRecommendDoctor = new BeanHomeView();
        typeRecommendDoctor.setViewType(HomepageAdapter.VIEWTYPE_DOCTORLAYOUT);
        viewTypes.add(typeRecommendDoctor);
        title = new BeanHomeTitle();
        title.setImgID(R.mipmap.xjk_ic_news);
        title.setRightText("更多");
        title.setTitleName("资讯信息");
        typeTitleRecommed.setBeanObj(title);
        viewTypes.add(typeTitleRecommed);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        mRecyclerView.setAdapter(mAdapter = new HomepageAdapter(viewTypes, this));
        mRecyclerView.setHasFixedSize(true);
        loadData();
    }
}
