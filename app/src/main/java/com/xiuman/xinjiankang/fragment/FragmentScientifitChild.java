package com.xiuman.xinjiankang.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.activity.ScientifitDetailActivity;
import com.xiuman.xinjiankang.adapter.CommonRecyclerViewAdapter;
import com.xiuman.xinjiankang.adapter.CommonViewHolder;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.bean.BeanCommonViewType;
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
 * 描述：科普知识
 * Created by hxy on 2015/12/17.
 */
public class FragmentScientifitChild extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    //传入参数KEY
    public static String parameID = "categoryId";
    //查询ID
    String categoryId = "";
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    CommonRecyclerViewAdapter mAdapter;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    //错误页
    @Bind(R.id.layout_network_error)
    View layoutError;
    //数据集
    List<BeanCommonViewType> mDatas;
    //请求页码
    int page = 1;
    //加载更多
    BeanCommonViewType loadMore;
    //滑动加载底部Item位置记录值
    int lastVisibleItem;
    LinearLayoutManager layoutManager;
    Context mContext;

    @Override
    public int getViewID() {
        return R.layout.fragment_scientific;
    }

    @Override
    public void init() {
        layoutError.setOnClickListener(this);
        mContext = getActivity();
        categoryId = String.valueOf(getArguments().get(parameID));
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter = new CommonRecyclerViewAdapter(mDatas) {
            @Override
            public void convert(CommonViewHolder holder, int position) {
                switch (getItemViewType(position)) {
                    case R.layout.item_scientific_child:
                        holder.itemView.setTag(position);
                        holder.itemView.setOnClickListener(FragmentScientifitChild.this);
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
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        loadDataFromParent();
    }


    /**
     * 显示下拉刷新动画并请求数据
     */
    public void loadDataFromParent() {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
            requestData();
        } else {
            return;
        }
    }

    public void requestData() {
        AppManager.getUserRequest().getScienceList(getActivity(), new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                Wrapper<ScienceDetail> value = new Gson().fromJson(result, new TypeToken<Wrapper<ScienceDetail>>() {
                }.getType());
                List<BeanCommonViewType> ScienceData = new ArrayList<BeanCommonViewType>();
                for (int i = 0; i < value.getDatasource().size(); i++) {
                    BeanCommonViewType oneScience = new BeanCommonViewType();
                    oneScience.setViewType(R.layout.item_scientific_child);
                    oneScience.setBeanObj(value.getDatasource().get(i));
                    ScienceData.add(oneScience);
                }
                //如果是加载更多数据插入到loding前面
                if (mDatas.contains(loadMore)) {
                    mDatas.addAll(mDatas.lastIndexOf(loadMore), ScienceData);
                } else {
                    mDatas.addAll(ScienceData);
                }
                //每页返回10条数据，如果等于10允许加载下一页，添加加载更多item
                if (ScienceData.size() == Constant.PAGE_SIZE) {
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
                layoutError.setVisibility(View.GONE);
                mAdapter.setDatas(mDatas);
                mAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void dataError(String result) {
                swipeRefreshLayout.setRefreshing(false);
                layoutError.setVisibility(View.VISIBLE);
                AppManager.showToast(mContext, result);
            }
        }, page, Constant.PAGE_SIZE, categoryId);
    }

    @Override
    public void onRefresh() {
        page = 1;
        mDatas.clear();
        loadMore = null;
        requestData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layoutScientificItem:
                int position = (int) v.getTag();
                Intent intent = new Intent(getActivity(), ScientifitDetailActivity.class);
                intent.putExtra(ScientifitDetailActivity.parameID, ((ScienceDetail) mDatas.get(position).getBeanObj()).getId());
                startActivity(intent);
                break;
            case R.id.layout_network_error:
                loadDataFromParent();
                break;
        }
    }
}
