package com.xiuman.xinjiankang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.Request.UserRequest;
import com.xiuman.xinjiankang.activity.FragmentInActivity;
import com.xiuman.xinjiankang.activity.FreeConsultActivity;
import com.xiuman.xinjiankang.activity.ScientifitDetailActivity;
import com.xiuman.xinjiankang.activity.SelectDoctorActivity;
import com.xiuman.xinjiankang.adapter.CommonRecyclerViewAdapter;
import com.xiuman.xinjiankang.adapter.CommonViewHolder;
import com.xiuman.xinjiankang.adapter.HomepageAdapter;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.bean.BeanCommonViewType;
import com.xiuman.xinjiankang.bean.BeanHomeTitle;
import com.xiuman.xinjiankang.bean.ScienceDetail;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.net.Wrapper;

import org.xutils.x;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首页
 * Created by PCPC on 2016/5/24.
 */
public class FragmentHomepage extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    //当前视图
    View thisView;

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    CommonRecyclerViewAdapter mAdapter;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    List<BeanCommonViewType> viewTypes;
    FragmentAD fragmentAD;
    FragmentRecommendDoctor fragmentRecommendDoctor;
    FragmentRecommendHospitor fragmentRecommendHospitor;

    //用来控制是否刷新数据时资讯信息重复加入列表
    boolean isRefresh;

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
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void loadData() {
        new UserRequest().getScienceTechologyList2(getActivity(), new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                Wrapper<ScienceDetail> resultDatas = new Gson().fromJson(result, new TypeToken<Wrapper<ScienceDetail>>() {
                }.getType());
                //下拉刷新数据
                if (isRefresh) {
                    //如果已存在则清除
                    for (int i = 0; i < viewTypes.size(); i++) {
                        if (HomepageAdapter.VIEWTYPE_LISTITEM == viewTypes.get(i).getViewType()) {
                            viewTypes.remove(i);
                            //移除后下标i-1防止跳过
                            i--;
                        }
                    }
                }
                for (int i = 0; i < 3; i++) {
                    int random = new Random().nextInt(resultDatas.getDatasource().size());
                    ScienceDetail scienceDetail = resultDatas.getDatasource().get(random);
                    resultDatas.getDatasource().remove(random);
                    BeanCommonViewType newsItem = new BeanCommonViewType();
                    newsItem.setBeanObj(scienceDetail);
//                    newsItem.setViewType(HomepageAdapter.VIEWTYPE_LISTITEM);
                    newsItem.setViewType(R.layout.item_xjk_news);
                    viewTypes.add(6, newsItem);
                }

                mAdapter.setDatas(viewTypes);
                mAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void dataError(String result) {
                swipeRefreshLayout.setRefreshing(false);
                AppManager.showToast(getActivity(), result);
            }
        });
    }


    private void init() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mRecyclerView.setAdapter(mAdapter = new HomepageAdapter(viewTypes, this));
        viewTypes = new ArrayList<>();
        mRecyclerView.setAdapter(mAdapter = new CommonRecyclerViewAdapter(viewTypes) {
            @Override
            public void convert(CommonViewHolder holder, int position) {
                switch (getItemViewType(position)) {
                    //广告
                    case R.layout.layout_ad:
                        FragmentAD fragmentAD = (FragmentAD) getChildFragmentManager().findFragmentById(R.id.layoutAD);
                        if (fragmentAD == null) {
                            fragmentAD = (FragmentAD) viewTypes.get(position).getBeanObj();
                            getChildFragmentManager().beginTransaction().replace(R.id.layoutAD, fragmentAD).commit();
                            if (fragmentAD.getThisView() != null) {
                                ((FrameLayout) holder.getView(R.id.layoutAD)).removeAllViews();
                                ((FrameLayout) holder.getView(R.id.layoutAD)).addView(fragmentAD.getThisView());
                            }
                        } else {
                            ((FrameLayout) holder.getView(R.id.layoutAD)).removeAllViews();
                            ((FrameLayout) holder.getView(R.id.layoutAD)).addView(fragmentAD.getThisView());
                        }
                        break;
                    //免费咨询等布局
                    case R.layout.layout_zixun:
                        holder.getView(R.id.rela_jkzx).setOnClickListener(FragmentHomepage.this);
                        holder.getView(R.id.llyt_search_doctor).setOnClickListener(FragmentHomepage.this);
                        holder.getView(R.id.iv_know).setOnClickListener(FragmentHomepage.this);
                        holder.getView(R.id.my_consult).setOnClickListener(FragmentHomepage.this);
                        break;
                    //心理专栏
                    case R.layout.layout_xinli:
                        holder.getView(R.id.xinli).setOnClickListener(FragmentHomepage.this);
                        break;
                    //标题
                    case R.layout.layout_title_more:
                        BeanHomeTitle title = (BeanHomeTitle) viewTypes.get(position).getBeanObj();
                        ((TextView) holder.getView(R.id.tvMoreOrOther)).setText(title.getRightText());
                        ((TextView) holder.getView(R.id.tvTitle)).setText(title.getTitleName());
                        ((ImageView) holder.getView(R.id.ivIcon)).setImageResource(title.getImgID());
                        holder.itemView.setTag(title.getRightText());
                        holder.itemView.setOnClickListener(FragmentHomepage.this);
                        break;
                    //推荐医生
                    case R.layout.layout_recommend_doctor:
                        FragmentRecommendDoctor doctor;
                        doctor = (FragmentRecommendDoctor) getChildFragmentManager().findFragmentById(R.id.layoutRecommendDoctor);
                        if (doctor == null) {
                            doctor = (FragmentRecommendDoctor) viewTypes.get(position).getBeanObj();
                            getChildFragmentManager().beginTransaction().replace(R.id.layoutRecommendDoctor, doctor).commit();
                            if (doctor.getThisView() != null) {
                                ((FrameLayout) holder.getView(R.id.layoutRecommendDoctor)).removeAllViews();
                                ((FrameLayout) holder.getView(R.id.layoutRecommendDoctor)).addView(doctor.getThisView());
                            }
                        } else {
                            ((FrameLayout) holder.getView(R.id.layoutRecommendDoctor)).removeAllViews();
                            ((FrameLayout) holder.getView(R.id.layoutRecommendDoctor)).addView(doctor.getThisView());
                        }
                        break;
                    //推荐医院
                    case R.layout.layout_recommend_hospitor:
                        FragmentRecommendHospitor hospital;
                        hospital = (FragmentRecommendHospitor) getChildFragmentManager().findFragmentById(R.id.layoutRecommendHospitor);
                        if (hospital == null) {
                            hospital = (FragmentRecommendHospitor) viewTypes.get(position).getBeanObj();
                            getChildFragmentManager().beginTransaction().replace(R.id.layoutRecommendHospitor, hospital).commit();
                            if (hospital.getThisView() != null) {
                                ((FrameLayout) holder.getView(R.id.layoutRecommendHospitor)).removeAllViews();
                                ((FrameLayout) holder.getView(R.id.layoutRecommendHospitor)).addView(hospital.getThisView());
                            }
                        } else {
                            ((FrameLayout) holder.getView(R.id.layoutRecommendHospitor)).removeAllViews();
                            ((FrameLayout) holder.getView(R.id.layoutRecommendHospitor)).addView(hospital.getThisView());
                        }
                        break;
                    //资讯信息
                    case R.layout.item_xjk_news:
                        ScienceDetail detail = (ScienceDetail) viewTypes.get(position).getBeanObj();
                        ((TextView) holder.getView(R.id.title)).setText(detail.getTitle());
                        ((TextView) holder.getView(R.id.content)).setText(detail.getDescription());
                        x.image().bind((ImageView) holder.getView(R.id.ivIcon), detail.getIcon(), optionsRadius);
                        holder.itemView.setTag(detail.getId());
                        holder.itemView.setOnClickListener(FragmentHomepage.this);
                        break;
                }
            }
        });
        //添加广告Fragment
        BeanCommonViewType typeAD = new BeanCommonViewType();
//        typeAD.setViewType(HomepageAdapter.VIEWTYPE_AD);
        typeAD.setViewType(R.layout.layout_ad);
        typeAD.setBeanObj(fragmentAD = new FragmentAD());
        viewTypes.add(typeAD);
        //添加资讯布局
        BeanCommonViewType typeZIXUN = new BeanCommonViewType();
//        typeZIXUN.setViewType(HomepageAdapter.VIEWTYPE_ZIXUN);
        typeZIXUN.setViewType(R.layout.layout_zixun);
        viewTypes.add(typeZIXUN);
        //添加心理布局
        BeanCommonViewType typeXinli = new BeanCommonViewType();
//        typeXinli.setViewType(HomepageAdapter.VIEWTYPE_XINLI);
        typeXinli.setViewType(R.layout.layout_xinli);
        viewTypes.add(typeXinli);
        //添加医师推荐标题布局
        BeanCommonViewType typeTitleRecommed = new BeanCommonViewType();
//        typeTitleRecommed.setViewType(HomepageAdapter.VIEWTYPE_TITLE);
        typeTitleRecommed.setViewType(R.layout.layout_title_more);
        BeanHomeTitle title = new BeanHomeTitle();
        title.setImgID(R.mipmap.xjk_ic_recommed_doctor);
        title.setRightText("排行榜");
        title.setTitleName("推荐医师");
        typeTitleRecommed.setBeanObj(title);
        viewTypes.add(typeTitleRecommed);
        //添加医师推荐Fragment
        BeanCommonViewType typeRecommendDoctor = new BeanCommonViewType();
//        typeRecommendDoctor.setViewType(HomepageAdapter.VIEWTYPE_DOCTORLAYOUT);
        typeRecommendDoctor.setViewType(R.layout.layout_recommend_doctor);
        typeRecommendDoctor.setBeanObj(fragmentRecommendDoctor = new FragmentRecommendDoctor());
        viewTypes.add(typeRecommendDoctor);
        //添加资讯信息布局
        BeanCommonViewType titleNews = new BeanCommonViewType();
//        titleNews.setViewType(HomepageAdapter.VIEWTYPE_TITLE);
        titleNews.setViewType(R.layout.layout_title_more);
        title = new BeanHomeTitle();
        title.setImgID(R.mipmap.xjk_ic_news);
        title.setRightText("更多");
        title.setTitleName("资讯信息");
        titleNews.setBeanObj(title);
        viewTypes.add(titleNews);
        //添加推荐医院标题布局
        BeanCommonViewType titleHospitor = new BeanCommonViewType();
//        titleHospitor.setViewType(HomepageAdapter.VIEWTYPE_TITLE);
        titleHospitor.setViewType(R.layout.layout_title_more);
        title = new BeanHomeTitle();
        title.setTitleName("推荐医院");
        title.setRightText("附近");
        title.setImgID(R.mipmap.xjk_pic_hospital);
        titleHospitor.setBeanObj(title);
        viewTypes.add(titleHospitor);
        //添加推荐医院Fragment
        BeanCommonViewType typeHospitor = new BeanCommonViewType();
//        typeHospitor.setViewType(HomepageAdapter.VIEWTYPE_HOSPITALlAYOUT);
        typeHospitor.setViewType(R.layout.layout_recommend_hospitor);
        typeHospitor.setBeanObj(fragmentRecommendHospitor = new FragmentRecommendHospitor());
        viewTypes.add(typeHospitor);
        mAdapter.setDatas(viewTypes);
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

    @Override
    public void onRefresh() {
        fragmentAD.loadData();
        fragmentRecommendDoctor.loadData();
        fragmentRecommendHospitor.loadData();
        isRefresh = true;
        loadData();
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent();
        switch (v.getId()) {
            case R.id.rela_jkzx:
                i = new Intent(getActivity(), FreeConsultActivity.class);
                startActivity(i);
                break;
            case R.id.my_consult:
                i = new Intent(getActivity(), FragmentInActivity.class);
                i.putExtra(FragmentInActivity.SHOWTYPE, FragmentInActivity.showFragmentConsult);
                startActivity(i);
                break;
            case R.id.iv_know:
                i = new Intent(getActivity(), FragmentInActivity.class);
                i.putExtra(FragmentInActivity.SHOWTYPE, FragmentInActivity.showFragmentNews);
                startActivity(i);
                break;
            case R.id.llyt_search_doctor:
                i = new Intent(getActivity(), SelectDoctorActivity.class);
                i.putExtra(SelectDoctorActivity.HAS_FILTER, true);
                startActivity(i);
                break;
            case R.id.xinli:
                Toast.makeText(v.getContext(), "心理专栏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layoutNews:
                i = new Intent(v.getContext(), ScientifitDetailActivity.class);
                i.putExtra("id", String.valueOf(v.getTag()));
                startActivity(i);
                break;
            case R.id.layoutTitleMore:
                String tag = String.valueOf(v.getTag());
//                Toast.makeText(v.getContext(), tag, Toast.LENGTH_SHORT).show();
                if (tag.contains("附近")) {

                } else if (tag.contains("排行榜")) {
                    i = new Intent(getActivity(), SelectDoctorActivity.class);
                    i.putExtra(SelectDoctorActivity.HAS_FILTER, false);
                    startActivity(i);
                } else if (tag.contains("更多")) {
                    i = new Intent(getActivity(), FragmentInActivity.class);
                    i.putExtra(FragmentInActivity.SHOWTYPE, FragmentInActivity.showFragmentNews);
                    startActivity(i);
                }
                break;
        }
    }
}
