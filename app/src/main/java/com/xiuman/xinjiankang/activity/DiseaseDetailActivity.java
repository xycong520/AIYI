package com.xiuman.xinjiankang.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.adapter.DiseaseDetailAdapter;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.base.BaseActivity;
import com.xiuman.xinjiankang.bean.BeanHomeView;
import com.xiuman.xinjiankang.bean.Disease;
import com.xiuman.xinjiankang.constant.Constant;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.net.Wrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 描述：疾病详情
 * Created by hxy on 2015/8/10.
 */
public class DiseaseDetailActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerview;
    DiseaseDetailAdapter mAdapter;

    @Bind(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    List<BeanHomeView> mDatas = new ArrayList<>();
    BeanHomeView loadMore;
    int lastVisibleItem;
    String categoryId;
    int page = 1, pageSize = Constant.PAGE_SIZE;

    @Override
    protected int getView() {
        return R.layout.activity_disease_detail;
    }

    @Override
    protected void initView() {
        setupToolbar();

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(layoutManager);
        mRecyclerview.setAdapter(mAdapter = new DiseaseDetailAdapter(mDatas));
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setOnClickListener(this);
        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == mAdapter.getItemCount() && loadMore!=null) {
                    page += 1;
                    loadData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
        loadData();
        mAdapter.setOnClickListener(this);
    }

    private void loadData() {
        categoryId = getIntent().getStringExtra("diseaseCategoryId");
        AppManager.getUserRequest().getDiagnoseDetail(this, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                if (page == 1) {
                    BeanHomeView headView = new BeanHomeView();
                    headView.setViewType(DiseaseDetailAdapter.VIEWTYPE_HEADVIEW);
                    mDatas.add(headView);
                }
                Wrapper<Disease> wraper = new Gson().fromJson(result, new TypeToken<Wrapper<Disease>>() {
                }.getType());
                List<BeanHomeView> caseData = new ArrayList<BeanHomeView>();
                for (int i = 0; i < wraper.getDatasource().size(); i++) {
                    BeanHomeView oneDisease = new BeanHomeView();
                    oneDisease.setViewType(DiseaseDetailAdapter.VIEWTYPE_CASE);
                    oneDisease.setBeanObj(wraper.getDatasource().get(i));
                    caseData.add(oneDisease);
                }
                //如果是加载更多数据插入到loding前面
                if (mDatas.contains(loadMore)) {
                    mDatas.addAll(mDatas.lastIndexOf(loadMore), caseData);
                } else {
                    mDatas.addAll(caseData);
                }
                //每页返回10条数据，如果等于10允许加载下一页，添加加载更多item
                if (caseData.size() == pageSize) {
                    if (loadMore == null) {
                        loadMore = new BeanHomeView();
                        loadMore.setViewType(DiseaseDetailAdapter.VIEWTYPE_LOADMORE);
                        mDatas.add(loadMore);
                    }
                } else if (page == 1 && caseData.size() == 0) {//还没有案例
                    BeanHomeView emptyComment = new BeanHomeView();
                    emptyComment.setViewType(DiseaseDetailAdapter.VIEWTYPE_CASE_EMPTY);
                    mDatas.add(emptyComment);
                } else {
                    //移除加载更多item
                    if (mDatas.contains(loadMore)) {
                        mDatas.remove(loadMore);
                        loadMore = null;
                    }
                }
                mAdapter.setDatas(mDatas);
                mAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void dataError(String result) {
                swipeRefreshLayout.setRefreshing(false);
                mDatas.clear();
                BeanHomeView errorView = new BeanHomeView();
                errorView.setViewType(DiseaseDetailAdapter.VIEWTYPE_HEADVIEW);
                mDatas.add(errorView);
                AppManager.showToast(mActivity, result);
            }
        }, categoryId, page, pageSize);
    }


    @Override
    public void onRefresh() {
        mDatas.clear();
        page = 1;
        loadData();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_patient:
                Intent intent = new Intent(mActivity,PatientDetailActivity.class);
                intent.putExtra(PatientDetailActivity.parameId,categoryId);
                startActivity(intent);
                break;
            case R.id.layout_network_error:
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                    }
                });
                break;
            case R.id.layoutDoctorInfo:
                int position = (int) view.getTag();
                Intent intent2 = new Intent(mActivity, CaseInfoActivity.class);
                intent2.putExtra(CaseInfoActivity.parameId, ((Disease)mDatas.get(position).getBeanObj()).getConsultingId());
                intent2.putExtra(CaseInfoActivity.parameIsDis,true);
                startActivity(intent2);
                break;
        }
    }
}
