package com.xiuman.xinjiankang.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.Bean.Attention;
import com.xiuman.xinjiankang.Bean.BeanHomeView;
import com.xiuman.xinjiankang.Bean.ScienceItemDetail;
import com.xiuman.xinjiankang.Bean.ScientificComment;
import com.xiuman.xinjiankang.Bean.ScientificPriase;
import com.xiuman.xinjiankang.adapter.ScientifitDetailAdapter;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.app.MyApplication;
import com.xiuman.xinjiankang.base.BaseActivity;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.net.Wrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by PCPC on 2016/5/26.
 */
public class ScientifitDetailActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    ScientifitDetailAdapter mAdapter;
    //文章id
    private String categoryId;
    List<BeanHomeView> data = new ArrayList<>();
    BeanHomeView content;
    BeanHomeView loadMore;
    List<BeanHomeView> comment;
    Animation animation;

    int lastVisibleItem;
    int page = 1;

    @Override
    protected void initView() {
        setupToolbar();
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
//        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        swipeRefreshLayout.setRefreshing(true);

        categoryId = getIntent().getStringExtra("id");
        loadData();
        mAdapter = new ScientifitDetailAdapter(null);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnClickListener(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                    page += 1;
                    loadCommentList(page);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
        animation = AnimationUtils.loadAnimation(mActivity, R.anim.xjk_zan_add_1);
    }

    @Override
    protected int getView() {
        return R.layout.activity_scientific_detail;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            //点击拨打客服热线
            case R.id.share:
                AppManager.showToast(this, "分享");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void loadData() {
        AppManager.getUserRequest().getScienceTechologyDetail(this, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                ScienceItemDetail scienceItemDetail = new Gson().fromJson(result, ScienceItemDetail.class);
                content = new BeanHomeView();
                content.setViewType(ScientifitDetailAdapter.VIEWTYPE_CONTENT);
                content.setBeanObj(scienceItemDetail);
                data.add(content);

                mAdapter.setDatas(data);
                mAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                loadCommentList(1);
            }

            @Override
            public void dataError(String result) {
//                AppManager.showToast(ScientifitDetailActivity.this,result);
            }
        }, categoryId);
        AppManager.getUserRequest().getScienceTechologyShareUrl(this, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {

            }

            @Override
            public void dataError(String result) {

            }
        }, categoryId);
    }

    private void loadCommentList(final int page) {
        AppManager.getUserRequest().getScienceTechologyCommentList(this, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                Wrapper<ScientificComment> ScienceDetail = new Gson().fromJson(result, new TypeToken<Wrapper<ScientificComment>>() {
                }.getType());
                if (ScienceDetail != null) {
                    comment = new ArrayList<BeanHomeView>();
                    for (int i = 0; i < ScienceDetail.getDatasource().size(); i++) {
                        BeanHomeView oneComent = new BeanHomeView();
                        oneComent.setViewType(ScientifitDetailAdapter.VIEWTYPE_COMMENT);
                        oneComent.setBeanObj(ScienceDetail.getDatasource().get(i));
                        comment.add(oneComent);
                    }
                    //如果是加载更多数据插入到loding前面
                    if (data.contains(loadMore)) {
                        data.addAll(data.lastIndexOf(loadMore), comment);
                    } else {
                        data.addAll(comment);
                    }
                    //每页返回10条数据，如果等于10允许加载下一页，添加加载更多item
                    if (comment.size() == 10) {
                        if (loadMore == null) {
                            loadMore = new BeanHomeView();
                            loadMore.setViewType(ScientifitDetailAdapter.VIEWTYPE_LOADMORE);
                            data.add(loadMore);
                        }
                    } else if (page == 1 && comment.size() == 0) {//还没有评论
                        BeanHomeView emptyComment = new BeanHomeView();
                        emptyComment.setViewType(ScientifitDetailAdapter.VIEWTYPE_COMMENT_EMPTEY);
                        data.add(emptyComment);
                    } else {
                        //移除加载更多item
                        if (data.contains(loadMore)) {
                            data.remove(loadMore);
                            loadMore = null;
                        }
                    }
                    mAdapter.setDatas(data);
                    mAdapter.notifyDataSetChanged();

                } else {

                }

            }

            @Override
            public void dataError(String result) {

            }
        }, categoryId, page);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_collect:
                takeCollect((TextView) view);
                break;
            case R.id.iv_ask_doctor:
//                Intent freeConsult = new Intent(mActivity, FreeConsultActivity.class);
//                startActivity(freeConsult);
                break;
            case R.id.iv_support:
                postSupport(view);
                break;
        }

    }

    /**
     * 收藏
     */
    private void takeCollect(final TextView view) {
        if (MyApplication.getInstance().isUserLogin()) {
            view.setEnabled(false);
            AppManager.getUserRequest().takeAttention(this, new HttpTaskListener() {
                @Override
                public void dataSucceed(String result) {
                    Attention entity = new Gson().fromJson(result, Attention.class);
                    if (entity != null) {
                        view.setEnabled(true);
                        if (entity.isSuccess()) {
                            view.setSelected(true);
                            view.setText("已收藏");
                            view.setSelected(true);
                            AppManager.showToast(mActivity, "收藏成功");
                        } else {
                            view.setSelected(false);
                            view.setText("收藏");
                            view.setSelected(false);
                            AppManager.showToast(mActivity, "取消收藏");
                        }

                    }
                }

                @Override
                public void dataError(String result) {

                }
            }, categoryId, 3);
        } else {
            Intent login = new Intent(mActivity, LoginActivity.class);
            startActivity(login);
        }
    }


    /**
     * 点赞
     *
     * @param view
     */
    private void postSupport(final View view) {
        if (MyApplication.getInstance().isUserLogin()) {
            AppManager.getUserRequest().getScienceTechologyPraise(this, new HttpTaskListener() {
                @Override
                public void dataSucceed(String result) {
                    ScientificPriase value = new Gson().fromJson(result, ScientificPriase.class);
                    final TextView tv_support_add = (TextView) view.getTag(R.id.tv_support_add);
                    final TextView tv_support_num = (TextView) view.getTag(R.id.tv_support_num);
                    if ("点赞成功".equals(value.getMessage())) {
                        tv_support_add.setVisibility(View.VISIBLE);
                        tv_support_add.setText("+1");
                        tv_support_add.startAnimation(animation);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                tv_support_add.setVisibility(View.GONE);
                            }
                        }, 1000);
                        view.setSelected(true);
                    } else {
                        tv_support_add.setVisibility(View.VISIBLE);
                        tv_support_add.setText("-1");
                        tv_support_add.startAnimation(animation);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                tv_support_add.setVisibility(View.GONE);
                            }
                        }, 1000);
                        view.setSelected(false);
                        tv_support_num.setSelected(false);
                    }
                    tv_support_num.setText(value.getDatasource().getPraiseCount() + "");
                }

                @Override
                public void dataError(String result) {

                }
            }, categoryId);
        } else {
            //登录
            Intent login = new Intent(mActivity, LoginActivity.class);
            startActivity(login);
        }

    }
}
