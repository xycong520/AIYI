package com.xiuman.xinjiankang.activity;

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
import com.xiuman.xinjiankang.bean.CaseConsultDetail;
import com.xiuman.xinjiankang.bean.VIPReply;
import com.xiuman.xinjiankang.constant.Constant;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.net.Wrapper;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 案例详情
 * Created by PCPC on 2016/6/3.
 */
public class CaseInfoActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener {
    //案例ID
    public static String parameId = "casesId";
    String casesID;
    //
    public static String parameIsDis = "isDis";
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerview;
    CommonRecyclerViewAdapter mAdapter;

    @Bind(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.tvAsk)
    TextView tvAsk;
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
        casesID = getIntent().getStringExtra(parameId);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(layoutManager);
        mRecyclerview.setAdapter(mAdapter = new CommonRecyclerViewAdapter(mDatas) {
            @Override
            public void convert(CommonViewHolder holder, int position) {
                switch (getItemViewType(position)) {
                    case R.layout.layout_caseinfo:
                        CaseConsultDetail caseInfo = (CaseConsultDetail) mDatas.get(position).getBeanObj();
                        TextView tvName = (TextView) holder.getView(R.id.doctor_name);
                        tvName.setText(caseInfo.getDatasource().getDoctorName());
                        TextView tvContetn = holder.getView(R.id.content);
                        TextView tvUserName = holder.getView(R.id.user_name);
                        TextView tvDoctorName = holder.getView(R.id.doctor_name);
                        TextView tvAge = holder.getView(R.id.age);
                        TextView sex = holder.getView(R.id.sex);
                        TextView tvDate = holder.getView(R.id.time);
                        tvContetn.setText(caseInfo.getDatasource().getContent());
                        tvAge.setText(String.valueOf(caseInfo.getDatasource().getAge()));
                        tvDoctorName.setText(caseInfo.getDatasource().getDoctorName());
                        tvUserName.setText(caseInfo.getDatasource().getName());
                        tvDate.setText(caseInfo.getDatasource().getCreateDate());
                        if (caseInfo.getDatasource().isSex()) {
                            sex.setText("男");
                        } else {
                            sex.setText("女");
                        }
                        x.image().bind((ImageView) holder.getView(R.id.ivIcon), caseInfo.getDatasource().getDoctorHead(), this.options);
                        break;
                    case R.layout.item_recorder_left:
                        VIPReply reply = (VIPReply) mDatas.get(position).getBeanObj();
                        x.image().bind((ImageView) holder.getView(R.id.ivIcon), reply.getDoctorAvatar(), this.options);
                        TextView tvReplyContent = (TextView) holder.getView(R.id.tvReplyContent);
                        tvDate = (TextView) holder.getView(R.id.tvDate);
                        tvReplyContent.setText(reply.getContent());
                        tvDate.setText(reply.getCreateDate());
                        break;
                    case R.layout.item_recorder_right:
                        reply = (VIPReply) mDatas.get(position).getBeanObj();
                        tvReplyContent = (TextView) holder.getView(R.id.tvReplyContent);
                        tvDate = (TextView) holder.getView(R.id.tvDate);
                        tvReplyContent.setText(reply.getContent());
                        tvDate.setText(reply.getCreateDate());
                        break;
                    case R.layout.item_loadmore:
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
                    loadData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });

        tvAsk.setOnClickListener(this);

        loadData();
    }

    private void loadData() {
        AppManager.getUserRequest().getCaseConsultDetail(mActivity, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                CaseConsultDetail caseInfo = new Gson().fromJson(result, CaseConsultDetail.class);
                BeanCommonViewType viewCaseInfo = new BeanCommonViewType();
                viewCaseInfo.setViewType(R.layout.layout_caseinfo);
                viewCaseInfo.setBeanObj(caseInfo);
                mDatas.add(viewCaseInfo);
                mAdapter.setDatas(mDatas);
                swipeRefreshLayout.setRefreshing(false);
                loadCommentData(page);

            }

            @Override
            public void dataError(String result) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, casesID);
    }

    private void loadCommentData(final int page) {
        AppManager.getUserRequest().getVIPConsultDetail(mActivity, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                Wrapper<VIPReply> entity = new Gson().fromJson(result, new TypeToken<Wrapper<VIPReply>>() {
                }.getType());
                List<BeanCommonViewType> replyData = new ArrayList<BeanCommonViewType>();
                for (int i = 0; i < entity.getDatasource().size(); i++) {
                    BeanCommonViewType viewReplay = new BeanCommonViewType();
                    if (entity.getDatasource().get(i).getType() == 1) {
                        viewReplay.setViewType(R.layout.item_recorder_left);
                    } else {
                        viewReplay.setViewType(R.layout.item_recorder_right);
                    }
                    viewReplay.setBeanObj(entity.getDatasource().get(i));
                    replyData.add(viewReplay);
                }
                //如果是加载更多数据插入到loding前面
                if (mDatas.contains(loadMore)) {
                    mDatas.addAll(mDatas.lastIndexOf(loadMore), replyData);
                } else {
                    mDatas.addAll(replyData);
                }
                //每页返回10条数据，如果等于10允许加载下一页，添加加载更多item
                if (replyData.size() == Constant.PAGE_SIZE) {
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
        }, casesID, page, Constant.PAGE_SIZE);
    }

    @Override
    protected int getView() {
        //复用布局，标题，recyclerview
        return R.layout.activity_caseinfo_;
    }

    @Override
    public void onRefresh() {
        page=1;
        mDatas.clear();
        loadMore = null;
        loadData();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.tvAsk:
                AppManager.showToast(mActivity,"咨询");
                break;
        }
    }
}
