package com.xiuman.xinjiankang.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.adapter.ScientifitDetailAdapter;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.base.BaseActivity;
import com.xiuman.xinjiankang.bean.Attention;
import com.xiuman.xinjiankang.bean.BeanCommonViewType;
import com.xiuman.xinjiankang.bean.ScienceItemDetail;
import com.xiuman.xinjiankang.bean.ScientificComment;
import com.xiuman.xinjiankang.bean.ScientificCommentResult;
import com.xiuman.xinjiankang.bean.ScientificPriase;
import com.xiuman.xinjiankang.bean.ScientificShareUrl;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.net.Wrapper;
import com.xiuman.xinjiankang.share.Share;
import com.xiuman.xinjiankang.share.UmengShareUtil;
import com.xiuman.xinjiankang.utils.AppSpUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by PCPC on 2016/5/26.
 */
public class ScientifitDetailActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    ScientifitDetailAdapter mAdapter;
    @Bind(R.id.btn_reply)
    Button btReplay;
    @Bind(R.id.et_reply)
    EditText etReply;

    public static String parameID = "id";
    //文章id
    private String categoryId;
    List<BeanCommonViewType> data = new ArrayList<>();
    BeanCommonViewType content;
    BeanCommonViewType loadMore;
    List<BeanCommonViewType> comment;
    Animation animation;

    int lastVisibleItem;
    int page = 1;

    @Override
    protected void initView() {
        setupToolbar();
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this);
        categoryId = getIntent().getStringExtra(parameID);
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

        btReplay.setOnClickListener(this);
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
                if (scienceItemDetail != null) {
                    showPop();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    String shareUrl;

    //分享
    private void share(String plate) {
        if (pw != null) {
            pw.dismiss();
        }
        UmengShareUtil shareUtils = new UmengShareUtil(mActivity);
        shareUtils.share(plate, new Share(scienceItemDetail.getDatasource().getTitle(), scienceItemDetail.getDatasource().getTitle(),
                shareUrl, scienceItemDetail.getDatasource().getGuideImg(), ""));
    }

    PopupWindow pw;

    private void showPop() {
        View shareView = mActivity.getLayoutInflater().inflate(R.layout.layout_pop_share, null);
        pw = new PopupWindow(shareView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ImageView sina = (ImageView) shareView.findViewById(R.id.bt_sina);
        ImageView qzone = (ImageView) shareView.findViewById(R.id.bt_QZone);
        ImageView wechat = (ImageView) shareView.findViewById(R.id.bt_wechat);
        ImageView circle = (ImageView) shareView.findViewById(R.id.bt_circle);
        sina.setOnClickListener(this);
        wechat.setOnClickListener(this);
        circle.setOnClickListener(this);
        qzone.setOnClickListener(this);
        pw.setOutsideTouchable(true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.showAtLocation(findViewById(R.id.layoutScientificDetail), Gravity.BOTTOM, 0, 0);
    }

    ScienceItemDetail scienceItemDetail;

    private void loadData() {
        AppManager.getUserRequest().getScienceTechologyDetail(this, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                scienceItemDetail = new Gson().fromJson(result, ScienceItemDetail.class);
                content = new BeanCommonViewType();
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
                swipeRefreshLayout.setRefreshing(false);
//                AppManager.showToast(ScientifitDetailActivity.this,result);
            }
        }, categoryId);
        AppManager.getUserRequest().getScienceTechologyShareUrl(this, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                ScientificShareUrl scientificShareUrl = new Gson().fromJson(result, ScientificShareUrl.class);
                shareUrl = scientificShareUrl.getDatasource().get(0).getUrl();
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
                    comment = new ArrayList<BeanCommonViewType>();
                    for (int i = 0; i < ScienceDetail.getDatasource().size(); i++) {
                        BeanCommonViewType oneComent = new BeanCommonViewType();
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
                            loadMore = new BeanCommonViewType();
                            loadMore.setViewType(ScientifitDetailAdapter.VIEWTYPE_LOADMORE);
                            data.add(loadMore);
                        }
                    } else if (page == 1 && comment.size() == 0) {//还没有评论
                        BeanCommonViewType emptyComment = new BeanCommonViewType();
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
            //新浪分享
            case R.id.bt_sina:
                share(UmengShareUtil.SINA);
                break;
            //QQ空间分享
            case R.id.bt_QZone:
                share(UmengShareUtil.QZone);
                break;
            //微信分享
            case R.id.bt_wechat:
                share(UmengShareUtil.WX);
                break;
            //朋友圈分享
            case R.id.bt_circle:
                share(UmengShareUtil.CIRCLE);
                break;
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
            case R.id.btn_reply:
                if ("".equals(etReply.getText().toString().trim())) {
                    AppManager.showToast(mActivity, "评论内容不能为空");
                    return;
                }
                if (AppManager.isUserLogin()) {
                    btReplay.setEnabled(false);
                    postComment(etReply.getText().toString().trim());
                } else {
                    Intent intent = new Intent(mActivity, LoginActivity.class);
                    startActivity(intent);
                }
                etReply.getText().toString();
                break;
        }
    }

    private void postComment(String comment) {
        AppManager.getUserRequest().postScienceTechologyComment(this, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                ScientificCommentResult scientificCommentResult = new Gson().fromJson(result, ScientificCommentResult.class);
                if (scientificCommentResult != null && scientificCommentResult.getMessage().equals("评论成功")) {
                    ScientificComment comment = new ScientificComment();
                    comment.setContent(etReply.getText().toString());
                    comment.setAvatar(AppSpUtil.getInstance().getUserInfo().getAvatar());
                    comment.setCreateDate(System.currentTimeMillis());
                    comment.setNickname(AppSpUtil.getInstance().getUserInfo().getNickname());
                    BeanCommonViewType oneComment = new BeanCommonViewType();
                    oneComment.setViewType(ScientifitDetailAdapter.VIEWTYPE_COMMENT);
                    oneComment.setBeanObj(comment);
                    data.add(oneComment);
                    mAdapter.setDatas(data);
                    mAdapter.notifyDataSetChanged();
                    HideKeyboard(etReply);
                    etReply.setText("");
                    AppManager.showToast(mActivity, "评论成功");
                    sendBroadcast(new Intent("" + categoryId));
                }
                btReplay.setEnabled(true);
            }

            @Override
            public void dataError(String result) {

            }
        }, categoryId, comment);
    }

    //隐藏软件键盘
    public static void HideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }


    /**
     * 收藏
     */
    private void takeCollect(final TextView view) {
        if (AppManager.isUserLogin()) {
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
        if (AppManager.isUserLogin()) {
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

    @Override
    public void onRefresh() {
        loadMore = null;
        data.clear();
        loadData();
    }
}
