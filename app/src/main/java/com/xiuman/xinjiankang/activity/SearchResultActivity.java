package com.xiuman.xinjiankang.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.adapter.CommonRecyclerViewAdapter;
import com.xiuman.xinjiankang.adapter.CommonViewHolder;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.base.BaseActivity;
import com.xiuman.xinjiankang.bean.BeanCommonViewType;
import com.xiuman.xinjiankang.bean.BeanConsulSearchResult;
import com.xiuman.xinjiankang.bean.ScienceDetail;
import com.xiuman.xinjiankang.constant.Constant;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.net.Wrapper;

import net.frakbot.jumpingbeans.JumpingBeans;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by PCPC on 2016/6/8.
 */
public class SearchResultActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    @Override
    protected int getView() {
        return R.layout.activity_search_result_;
    }

    //搜索key
    public static String parameKey = "KEY";
    String key;
    //标记搜索内容是诊断还是资讯
    public static String parameIsConsult = "isConsult";
    boolean isConsult = false;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerview;
    CommonRecyclerViewAdapter mAdapter;

    @Bind(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    List<BeanCommonViewType> mDatas = new ArrayList<>();
    int page = 1;
    BeanCommonViewType loadMore;
    int lastVisibleItem;

    @Override
    protected void initView() {
        setupToolbar();
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
        key = getIntent().getStringExtra(parameKey);
        isConsult = getIntent().getBooleanExtra(parameIsConsult, false);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(layoutManager);
        mRecyclerview.setAdapter(mAdapter = new CommonRecyclerViewAdapter(mDatas) {
            @Override
            public void convert(CommonViewHolder holder, int position) {
                switch (getItemViewType(position)) {
                    case R.layout.item_consult_data:
                        holder.itemView.setTag(position);
                        holder.itemView.setOnClickListener(SearchResultActivity.this);
                        TextView tvName = holder.getView(R.id.tvName);
                        tvName.setText(((BeanConsulSearchResult) mDatas.get(position).getBeanObj()).getName());
                        holder.getView(R.id.line).setVisibility(View.VISIBLE);
                        break;
                    case R.layout.item_scientific_child:
                        holder.itemView.setTag(position);
                        holder.itemView.setOnClickListener(SearchResultActivity.this);
                        ScienceDetail item = (ScienceDetail) mDatas.get(position).getBeanObj();
                        TextView tvTitle = holder.getView(R.id.tvTitle);
                        TextView tvContent = holder.getView(R.id.tvContent);
                        TextView tvConmment = holder.getView(R.id.tvComment);
                        TextView tvLabel = holder.getView(R.id.tvLabel);
                        TextView tvSupport = holder.getView(R.id.tvSupport);
                        tvTitle.setText(item.getTitle());
                        tvConmment.setText(item.getCommentCount() + "");
                        tvContent.setText(item.getDescription());
                        tvLabel.setText(item.getLabelName());
                        tvSupport.setText(String.valueOf(item.getPariseCount()));
                        ImageView ivPhoto = holder.getView(R.id.ivIcon);
                        x.image().bind(ivPhoto, item.getIcon(), optionsRadius);
                        break;
                    case R.layout.item_loadmore:
                        TextView tvLoading = holder.getView(R.id.tvLoading);
                        JumpingBeans.with(tvLoading).appendJumpingDots()
                                .build();
                        break;

                }
            }
        });
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == mAdapter.getItemCount() && loadMore != null) {
                    page += 1;
                    if (isConsult) {
                        loadCaseData();
                    } else {
                        searchData();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
        if (isConsult) {
            loadCaseData();
        } else {
            searchData();
        }
    }


    private void loadCaseData() {
        AppManager.getUserRequest().getCaseList(mActivity, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                Wrapper<BeanConsulSearchResult> entity = new Gson().fromJson(result, new TypeToken<Wrapper<BeanConsulSearchResult>>() {
                }.getType());
                List<BeanCommonViewType> resultData = new ArrayList<BeanCommonViewType>();
                for (int i = 0; i < entity.getDatasource().size(); i++) {
                    BeanCommonViewType oneData = new BeanCommonViewType();
                    oneData.setViewType(R.layout.item_consult_data);
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
            }
        }, key, page, Constant.PAGE_SIZE);
    }

    private void searchData() {
        AppManager.getUserRequest().getSearchResult(mActivity, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                Wrapper<ScienceDetail> entity = new Gson().fromJson(result, new TypeToken<Wrapper<ScienceDetail>>() {
                }.getType());
                List<BeanCommonViewType> resultData = new ArrayList<BeanCommonViewType>();
                for (int i = 0; i < entity.getDatasource().size(); i++) {
                    BeanCommonViewType viewReplay = new BeanCommonViewType();
                    viewReplay.setViewType(R.layout.item_scientific_child);
                    viewReplay.setBeanObj(entity.getDatasource().get(i));
                    resultData.add(viewReplay);
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
            }
        }, page, Constant.PAGE_SIZE, key);
    }

    @Override
    public void onRefresh() {
        page = 1;
        mDatas.clear();
        loadMore = null;
        if (isConsult) {
            loadCaseData();
        } else {
            searchData();
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int position = (int) view.getTag();
        switch (view.getId()) {
            case R.id.layoutConsultData:
                Intent intent = new Intent(mActivity, DiseaseDetailActivity.class);
                intent.putExtra("diseaseCategoryId", ((BeanConsulSearchResult) mDatas.get(position).getBeanObj()).getId());
                startActivity(intent);
                break;
            case R.id.layoutScientificItem:
                intent = new Intent(mActivity, ScientifitDetailActivity.class);
                intent.putExtra(ScientifitDetailActivity.parameID, ((ScienceDetail) mDatas.get(position).getBeanObj()).getId());
                startActivity(intent);
                break;
        }
    }
}
