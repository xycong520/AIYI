package com.xiuman.xinjiankang.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.adapter.CommonRecyclerViewAdapter;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.bean.BeanCommonViewType;
import com.xiuman.xinjiankang.bean.ConsultHistory;
import com.xiuman.xinjiankang.constant.Constant;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.net.Wrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by PCPC on 2016/5/24.
 */
public abstract  class BaseRefreshActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener{

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerview;

    @Bind(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    CommonRecyclerViewAdapter mAdapter;
    List<BeanCommonViewType> mDatas = new ArrayList<>();

    final int itemLayoutID = R.layout.item_question_history;
    int page = 1;
    BeanCommonViewType loadMore;
    int lastVisibleItem;
    LinearLayoutManager layoutManager;

    @Override
    protected void initView() {
        setupToolbar();
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        initAdapter();
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(layoutManager = new LinearLayoutManager(this));
        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == mAdapter.getItemCount() && loadMore != null) {
                    page += 1;
                    requestData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
        mRecyclerview.setHasFixedSize(true);
        requestData();
    }

    abstract public void  initAdapter();
    abstract public void  requestData();

    @Override
    protected int getView() {
        return R.layout.activity_base_refresh;
    }

    @Override
    public void onRefresh() {

    }


    HttpTaskListener myListener = new HttpTaskListener() {
        @Override
        public void dataSucceed(String result) {
            Wrapper<?> entity = null;
            entity = new Gson().fromJson(result, new TypeToken<Wrapper<ConsultHistory>>() {
            }.getType());
            if (entity.getDatasource() == null) {
                if (page == 1) {
                    BeanCommonViewType nodata = new BeanCommonViewType();
                    nodata.setViewType(R.layout.layout_empty_);
                    mDatas.add(nodata);
                    mAdapter.setDatas(mDatas);
                    mAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    mDatas.remove(loadMore);
                    loadMore = null;
                }
                return;
            }
            List<BeanCommonViewType> resultData = new ArrayList<BeanCommonViewType>();
            for (int i = 0; i < entity.getDatasource().size(); i++) {
                BeanCommonViewType oneData = new BeanCommonViewType();
                oneData.setViewType(itemLayoutID);
                oneData.setBeanObj(entity.getDatasource().get(i));
                resultData.add(oneData);
            }
            //如果是加载更多数据插入到loding前面
            if (mDatas.contains(loadMore)) {
                mDatas.addAll(mDatas.lastIndexOf(loadMore), resultData);
            } else {
                mDatas.addAll(resultData);
            }
            //每页返回10条数据，如果等于10允许加载下一页，添加加载更多item
            if (resultData.size() == Constant.PAGE_SIZE) {
                if (loadMore == null) {
                    loadMore = new BeanCommonViewType();
                    loadMore.setViewType(R.layout.item_loadmore);
                    mDatas.add(loadMore);
                }
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
            AppManager.showToast(mActivity, result);
        }
    };
}
