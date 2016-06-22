package com.xiuman.xinjiankang.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.xiuman.xinjiankang.bean.CencelVipOrder;
import com.xiuman.xinjiankang.bean.VIPReply;
import com.xiuman.xinjiankang.constant.Constant;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.net.Wrapper;
import com.xiuman.xinjiankang.widget.CustomDialog;

import net.frakbot.jumpingbeans.JumpingBeans;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 案例详情
 * Created by PCPC on 2016/6/3.
 */
public class CaseInfoActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
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
    @Bind(R.id.layoutPay)
    LinearLayout layoutPay;
    @Bind(R.id.layoutAsk)
    LinearLayout layoutAsk;

    List<BeanCommonViewType> mDatas = new ArrayList<>();
    int page = 1;
    BeanCommonViewType loadMore;
    int lastVisibleItem;
    ArrayList<String> paths = new ArrayList<>();

    @Override
    protected void initView() {
        setupToolbar();
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);
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
                        GridLayout gridlayout = holder.getView(R.id.gridlayout);
                        if (gridlayout.getChildCount() != caseInfo.getDatasource().getImgUrl().size()) {
                            for (int i = 0; i < caseInfo.getDatasource().getImgUrl().size(); i++) {
                                paths.add(caseInfo.getDatasource().getImgUrl().get(i));
                                View view = LayoutInflater.from(mActivity).inflate(R.layout.item_imageview_gridlayout, null);
                                ImageView imageView = (ImageView) view.findViewById(R.id.ivPhoto);
                                x.image().bind(imageView, caseInfo.getDatasource().getImgUrl().get(i), optionsRadius);
                                imageView.setOnClickListener(onImageViewClick);
                                gridlayout.addView(view);
                            }
                        }
                        break;
                    case R.layout.item_recorder_left:
                        VIPReply reply = (VIPReply) mDatas.get(position).getBeanObj();
                        x.image().bind((ImageView) holder.getView(R.id.ivIcon), reply.getDoctorAvatar(), this.options);
                        TextView tvReplyContent = holder.getView(R.id.tvReplyContent);
                        tvDate = holder.getView(R.id.tvDate);
                        tvReplyContent.setText(reply.getContent());
                        tvDate.setText(reply.getCreateDate());
                        tvDate.setVisibility(View.VISIBLE);
                        break;
                    case R.layout.item_recorder_right:
                        reply = (VIPReply) mDatas.get(position).getBeanObj();
                        tvReplyContent = holder.getView(R.id.tvReplyContent);
                        tvDate = holder.getView(R.id.tvDate);
                        tvReplyContent.setText(reply.getContent());
                        tvDate.setText(reply.getCreateDate());
                        tvDate.setVisibility(View.VISIBLE);
                        break;
                    case R.layout.item_loadmore:
                        TextView tvLoading = holder.getView(R.id.tvLoading);
                        JumpingBeans.with(tvLoading).appendJumpingDots()
                                .build();
                        break;

                }
            }

            /**
             * 图片点击事件
             */
            View.OnClickListener onImageViewClick = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(mActivity, PhotoPagerActivity.class);
//                    intent.putExtra(PhotoPagerActivity.EXTRA_CURRENT_ITEM, current);
//                    intent.putExtra(PhotoPagerActivity.EXTRA_PHOTOS, paths);
//                    intent.putExtra(PhotoPagerActivity.EXTRA_SHOW_DELETE, false);
//                    intent.putExtra(PhotoPagerActivity.EXTRA_TYPE, 2);
//                    startActivity(intent);
                }
            };
        });
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == mAdapter.getItemCount() && loadMore != null) {
                    page += 1;
                    loadCommentData(page);
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

    CaseConsultDetail caseInfo;

    private void loadData() {
        AppManager.getUserRequest().getCaseConsultDetail(mActivity, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                caseInfo = new Gson().fromJson(result, CaseConsultDetail.class);
                BeanCommonViewType viewCaseInfo = new BeanCommonViewType();
                viewCaseInfo.setViewType(R.layout.layout_caseinfo);
                viewCaseInfo.setBeanObj(caseInfo);
                if (caseInfo.getDatasource().getStatus() == 5) {
                    layoutAsk.setVisibility(View.GONE);
                    layoutPay.setVisibility(View.VISIBLE);
                }
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
        page = 1;
        mDatas.clear();
        loadMore = null;
        loadData();
    }

    @Override
    @OnClick({R.id.tvAsk, R.id.goto_pay, R.id.cancel_order})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tvAsk:
                AppManager.showToast(mActivity, "咨询");
                break;
            case R.id.cancel_order:
                cancelDialog();
                break;
            case R.id.goto_pay:
                Intent intent = new Intent(mActivity, BuyServiceActivity.class);
                intent.putExtra(BuyServiceActivity.paramOrderID, caseInfo.getDatasource().getOrderId());
                intent.putExtra(BuyServiceActivity.paramQuestionId, casesID);
                intent.putExtra(BuyServiceActivity.paramUsername, caseInfo.getDatasource().getDoctorName());
                intent.putExtra(BuyServiceActivity.paramTpye, "vip");
                startActivity(intent);
                break;
        }
    }

    private void cancelDialog() {
        final CustomDialog dialog_cancel = new CustomDialog(this, "提示", "确定取消订单？");
        dialog_cancel.show();
        dialog_cancel.btn_custom_dialog_cancel.setText("取消");
        dialog_cancel.btn_custom_dialog_sure.setText("确定");
        dialog_cancel.btn_custom_dialog_sure
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_cancel.dismiss();
                        AppManager.showDialog(mActivity);
                        AppManager.getUserRequest().cancleVIPConsult(mActivity, listener, casesID);
                    }
                });
        dialog_cancel.btn_custom_dialog_cancel
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_cancel.dismiss();
                    }
                });
    }

    HttpTaskListener listener = new HttpTaskListener() {
        @Override
        public void dataSucceed(String result) {
            CencelVipOrder results = new Gson().fromJson(result, CencelVipOrder.class);
            if (results != null) {
                if (results.getSuccess()) {
                    if ("取消完成".equals(results.getDatasource())) {
                        setResult(RESULT_OK);
                        AppManager.showToast(mActivity, results.getDatasource());
                        AppManager.dismiss();
                        finish();
                    }
                }
            }
        }

        @Override
        public void dataError(String result) {
            AppManager.showToast(mActivity, result);
        }
    };
}
