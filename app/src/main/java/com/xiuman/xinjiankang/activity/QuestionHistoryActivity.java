package com.xiuman.xinjiankang.activity;

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
import com.xiuman.xinjiankang.adapter.CommonRecyclerViewAdapter;
import com.xiuman.xinjiankang.adapter.CommonViewHolder;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.base.BaseActivity;
import com.xiuman.xinjiankang.bean.BeanCommonViewType;
import com.xiuman.xinjiankang.bean.ConsultHistory;
import com.xiuman.xinjiankang.constant.Constant;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.net.Wrapper;

import net.frakbot.jumpingbeans.JumpingBeans;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 提问历史
 * Created by PCPC on 2016/6/20.
 */
public class QuestionHistoryActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerview;
    CommonRecyclerViewAdapter mAdapter;

    @Bind(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
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
        mRecyclerview.setLayoutManager(layoutManager = new LinearLayoutManager(this));
        mRecyclerview.setAdapter(mAdapter = new CommonRecyclerViewAdapter(mDatas) {
            @Override
            public void convert(CommonViewHolder holder, int position) {
                switch (getItemViewType(position)) {
                    case R.layout.item_loadmore:
                        TextView tvLoading = holder.getView(R.id.tvLoading);
                        JumpingBeans.with(tvLoading).appendJumpingDots()
                                .build();
                        break;
                    case itemLayoutID:
                        holder.itemView.setTag(position);
                        holder.itemView.setOnClickListener(QuestionHistoryActivity.this);
                        ImageView tv_sex = holder.getView(R.id.tv_sex);
                        TextView name = holder.getView(R.id.name);
                        TextView state = holder.getView(R.id.state);
                        TextView sex = holder.getView(R.id.sex);
                        TextView age = holder.getView(R.id.age);
                        TextView type = holder.getView(R.id.type);
                        TextView content = holder.getView(R.id.content);
                        TextView date = holder.getView(R.id.date);
                        ConsultHistory consultHistory = (ConsultHistory) mDatas.get(position).getBeanObj();
                        if (consultHistory.getDoctorSex()) {
                            tv_sex.setImageResource(R.mipmap.xjk_doctor_man);
                        } else {
                            tv_sex.setImageResource(R.mipmap.xjk_doctor_woman);
                        }
                        name.setText(consultHistory.getDoctorName());
                        age.setText(consultHistory.getAge() + "");
                        type.setText(consultHistory.getCounselType() + "");
                        content.setText(consultHistory.getContent());
                        date.setText(consultHistory.getCreateDate());
                        if (consultHistory.isSex()) {
                            sex.setText("男，");
                        } else {
                            sex.setText("女，");
                        }
                        switch (consultHistory.getCounselType()) {
                            case 1:
                                type.setText("免费咨询");
                                switch (consultHistory.getStatus()) {
                                    case "0":
                                        state.setText("未审核");
                                        state.setTextColor(content.getResources().getColor(R.color.red));
                                        break;
                                    case "1":
                                        state.setText("已审核");
                                        state.setTextColor(content.getResources().getColor(R.color.xjk_title_text_color));
                                        break;
                                }
                                break;
                            case 2:
                                type.setText("VIP咨询");
                                switch (consultHistory.getStatus()) {
                                    case "0":
                                        state.setText("待回答");
                                        state.setTextColor(content.getResources().getColor(R.color.red));
                                        break;
                                    case "1":
                                        state.setText("回答中");
                                        state.setTextColor(content.getResources().getColor(R.color.red));
                                        break;
                                    case "2":
                                        state.setText("已完成");
                                        state.setTextColor(content.getResources().getColor(R.color.xjk_title_text_color));
                                        break;
                                    case "3":
                                        state.setText("待抢答");
                                        state.setTextColor(content.getResources().getColor(R.color.red));
                                        break;
                                    case "4":
                                        state.setText("待评价");
                                        state.setTextColor(content.getResources().getColor(R.color.red));
                                        break;
                                    case "5":
                                        state.setText("待支付");
                                        state.setTextColor(content.getResources().getColor(R.color.red));
                                        break;
                                    case "6":
                                        state.setText("已取消");
                                        state.setTextColor(content.getResources().getColor(R.color.red));
                                        break;
                                }
                                break;
                            case 3:
                                type.setText("电话咨询");
                                switch (consultHistory.getStatus()) {
                                    case "1":
                                        state.setText("进行中");
                                        state.setTextColor(content.getResources().getColor(R.color.red));
                                        break;
                                    case "2":
                                        state.setText("已取消");
                                        state.setTextColor(content.getResources().getColor(R.color.xjk_title_text_color));
                                        break;
                                    case "3":
                                        state.setText("退款中");
                                        state.setTextColor(content.getResources().getColor(R.color.red));
                                        break;
                                    case "4":
                                        state.setText("待支付");
                                        state.setTextColor(content.getResources().getColor(R.color.red));
                                        break;
                                    case "5":
                                        state.setText("待评价");
                                        state.setTextColor(content.getResources().getColor(R.color.red));
                                        break;
                                    case "6":
                                        state.setText("已完成");
                                        state.setTextColor(content.getResources().getColor(R.color.red));
                                        break;
                                }
                                break;
                        }
                        break;
                    case R.layout.layout_empty_:
                        break;
                }
            }
        });

        /*mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        });*/
        mRecyclerview.setHasFixedSize(true);
        requestData();
    }

    @Override
    protected int getView() {
        return R.layout.activity_disease_detail;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Intent intent = new Intent();
        int index = (int) view.getTag();
        ConsultHistory history = (ConsultHistory) mDatas.get(index).getBeanObj();
        if (history.getCounselType() == 1) {//免费咨询
            intent = new Intent(mActivity, CaseInfoActivity.class);
            intent.putExtra(CaseInfoActivity.parameId, history.getId());
            intent.putExtra(CaseInfoActivity.parameIsDis, false);
        } else if (history.getCounselType() == 2) {//vip咨询
            if ("1".equals(history.getStatus()) ||"0".equals(history.getStatus()) || "3".equals(history.getStatus())) {//待回答 和 回答中
                intent = new Intent(mActivity, ChatActivity.class);
                intent.putExtra(ChatActivity.paramDoctorName, history.getDoctorUser());
                intent.putExtra(ChatActivity.paramQuestionID, history.getId());
            }else{
                intent = new Intent(mActivity, CaseInfoActivity.class);
                intent.putExtra(CaseInfoActivity.parameId, history.getId());
                intent.putExtra(CaseInfoActivity.parameIsDis, false);
            }
        }
        startActivity(intent);
    }

    private void showSwipe() {
        page = 1;
        mDatas.clear();
        loadMore = null;
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }


    private void requestData() {
        AppManager.getUserRequest().getQuestionHistoryList(this, myListener);
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


    @Override
    public void onRefresh() {
        page = 1;
        mDatas.clear();
        loadMore = null;
        requestData();
    }
}
