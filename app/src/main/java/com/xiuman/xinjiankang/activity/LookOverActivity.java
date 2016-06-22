package com.xiuman.xinjiankang.activity;

import android.graphics.drawable.BitmapDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.adapter.CommonAdapter;
import com.xiuman.xinjiankang.adapter.CommonRecyclerViewAdapter;
import com.xiuman.xinjiankang.adapter.CommonViewHolder;
import com.xiuman.xinjiankang.adapter.ViewHolder;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.base.BaseActivity;
import com.xiuman.xinjiankang.bean.BeanCommonViewType;
import com.xiuman.xinjiankang.bean.Dministartive;
import com.xiuman.xinjiankang.bean.FreeConsultListEntity;
import com.xiuman.xinjiankang.bean.VipList;
import com.xiuman.xinjiankang.constant.Constant;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.net.Wrapper;
import com.xiuman.xinjiankang.utils.DateUtils;

import net.frakbot.jumpingbeans.JumpingBeans;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 查看
 * Created by PCPC on 2016/6/20.
 */
public class LookOverActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    public static final String ISFRESS = "isFree";
    boolean isFree = false;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerview;
    CommonRecyclerViewAdapter mAdapter;

    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.layoutSearch)
    RelativeLayout layoutSearch;
    //筛选
    @Bind(R.id.search_option)
    LinearLayout layoutOption;
    //科室筛选
    @Bind(R.id.tv_classify)
    TextView tvClassify;
    //时间筛选
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.et_search_input_keyword)
    EditText etSearch;
    //科室id
    private String administrativeId = "";
    //時間排序
    private int sortTypeApp = -1;
    String keyWord = "";
    List<BeanCommonViewType> mDatas = new ArrayList<>();
    int page = 1;
    BeanCommonViewType loadMore;
    int lastVisibleItem;
    LinearLayoutManager layoutManager;
    //科室数据
    Wrapper<Dministartive> classifyData;

    @Override
    protected void initView() {
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
                    case R.layout.item_yizhen_vip:
                        holder.itemView.setTag(position);
                        holder.itemView.setOnClickListener(LookOverActivity.this);
                        VipList vipEntity = (VipList) mDatas.get(position).getBeanObj();
                        TextView tvContent = holder.getView(R.id.tv_content);
                        TextView tvName = holder.getView(R.id.name);
                        ImageView ivIcon = holder.getView(R.id.iv_icon);
                        TextView tvDate = holder.getView(R.id.tv_date);
                        x.image().bind(ivIcon, vipEntity.getDoctorHead(), options);
                        tvDate.setText(DateUtils.dateFormat(vipEntity.getCreateDate()));
                        tvContent.setText(vipEntity.getContent());
                        tvName.setText(vipEntity.getDoctorName());
                        break;
                    case R.layout.item_yizhen_:
                        holder.itemView.setTag(position);
                        holder.itemView.setOnClickListener(LookOverActivity.this);
                        FreeConsultListEntity entity = (FreeConsultListEntity) mDatas.get(position).getBeanObj();
                        TextView age = holder.getView(R.id.age);
                        LinearLayout sexbg = holder.getView(R.id.sex_age);
                        TextView tv_content = holder.getView(R.id.tv_content);
                        TextView tv_reply_num = holder.getView(R.id.tv_reply_num);
                        TextView tv_date = holder.getView(R.id.tv_date);
                        TextView tv_administrative = holder.getView(R.id.tv_administrative);
                        ImageView icon = holder.getView(R.id.iv_icon);
                        ImageView iconSex = holder.getView(R.id.sex_icon);
                        age.setText(String.valueOf(entity.getAge()));
                        if ("男".equals(entity.getSex())) {
                            iconSex.setImageResource(R.mipmap.xjk_man);
                            sexbg.setBackgroundResource(R.drawable.xjk_man_bg);
                        } else {
                            iconSex.setImageResource(R.mipmap.xjk_woman);
                            sexbg.setBackgroundResource(R.drawable.xjk_woman_bg);
                        }
                        tv_content.setText(entity.getContent());
                        String format = entity.getReplyNum() + "条回复";
                        SpannableString ss = new SpannableString(format);
                        ss.setSpan(new ForegroundColorSpan(mActivity.getResources().getColor(R.color.xjk_title_text_color)), 0, format.indexOf("条"), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                        tv_reply_num.setText(ss);
                        tv_date.setText(DateUtils.dateFormat(entity.getCreateDate()));
                        tv_administrative.setText(entity.getDepartmentCategory());
                        x.image().bind(icon, entity.getAvatar(), options);
                        break;
                    case R.layout.layout_empty_:
                        break;
                }
            }
        });

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
        if (isFree) {
            initSortPop();
            requestDepartment();

        } else {
            tvTitle.setVisibility(View.VISIBLE);
            layoutSearch.setVisibility(View.GONE);
            layoutOption.setVisibility(View.GONE);
        }
        requestData();
    }

    @Override
    protected int getView() {
        isFree = getIntent().getBooleanExtra(ISFRESS, false);
        return R.layout.activity_lookover_free;
    }

    @Override
    @OnClick({R.id.tv_classify, R.id.back, R.id.btn_clear_search_input, R.id.tv_search, R.id.tv_time})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_clear_search_input:
                etSearch.setText("");
                break;
            case R.id.tv_search:
                keyWord = etSearch.getText().toString();
                if (TextUtils.isEmpty(keyWord)) {
                    AppManager.showToast(mActivity, "请输入搜索内容");
                    return;
                }
                showSwipe();
                requestData();
                break;
            case R.id.layoutYizhen:
                break;
            //科室
            case R.id.tv_classify:
                if (classifyPop != null) {
                    classifyPop.showAsDropDown(view);
                } else {
                    initClassifyPop();
                    classifyPop.showAsDropDown(view);
                }
                break;
            //时间
            case R.id.tv_time:
                if (sortPop != null) {
                    sortPop.showAsDropDown(view);
                } else {
                    initSortPop();
                    sortPop.showAsDropDown(view);
                }
                break;
            //智能排序
            case R.id.capacity:
                sortTypeApp = 0;
                tvTime.setText(((TextView) view).getText().toString());
                sortPop.dismiss();
                showSwipe();
                requestData();
                break;
            //最新提问
            case R.id.newAsk:
                sortTypeApp = 1;
                tvTime.setText(((TextView) view).getText().toString());
                sortPop.dismiss();
                showSwipe();
                requestData();
                break;
            //最新回复
            case R.id.newReply:
                sortTypeApp = 2;
                tvTime.setText(((TextView) view).getText().toString());
                sortPop.dismiss();
                showSwipe();
                requestData();
                break;
        }
    }

    //请求科室
    private void requestDepartment() {
        AppManager.getUserRequest().getDepartmentList(mActivity, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                classifyData = new Gson().fromJson(result, new TypeToken<Wrapper<Dministartive>>() {
                }.getType());
                initClassifyPop();
            }

            @Override
            public void dataError(String result) {
                tvClassify.setClickable(false);
                AppManager.showToast(mActivity, result);
            }
        });
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

    PopupWindow sortPop;

    //初始化时间排序的PopupWindow
    private void initSortPop() {
        View inflate = inflater.inflate(R.layout.layout_pop_time, null);
        sortPop = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout llty_sort_content = (LinearLayout) inflate.findViewById(R.id.llty_sort_content);
        TextView capacity = (TextView) inflate.findViewById(R.id.capacity);
        TextView assess = (TextView) inflate.findViewById(R.id.newAsk);
        TextView reply = (TextView) inflate.findViewById(R.id.newReply);
        llty_sort_content.setOnClickListener(this);
        capacity.setOnClickListener(this);
        assess.setOnClickListener(this);
        reply.setOnClickListener(this);
        sortPop.setOutsideTouchable(true);
        sortPop.setBackgroundDrawable(new BitmapDrawable());
        sortPop.setFocusable(true);
        sortPop.update();
        sortPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hintView();
            }
        });
    }

    //分类排序的POP
    private PopupWindow classifyPop;
    int curClassifyIndex = 0;

    //初始化科室的PopupWindow
    private void initClassifyPop() {
        View inflate = inflater.inflate(R.layout.layout_pop_classify, null);
        classifyPop = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout llyt_classify_content = (LinearLayout) inflate.findViewById(R.id.llyt_classify_content);
        ListView listview = (ListView) inflate.findViewById(R.id.listview);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int heightPixels = displayMetrics.heightPixels;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (heightPixels / 3f));
        listview.setLayoutParams(params);
        CommonAdapter adapter1 = new CommonAdapter<Dministartive>(mActivity, classifyData.getDatasource(), R.layout.item_provience_city) {
            @Override
            public void convert(ViewHolder holper, Dministartive item) {
                ((TextView) holper.getView(R.id.tvName)).setText(item.getName());
            }
        };
        listview.setAdapter(adapter1);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                curClassifyIndex = position;
                administrativeId = classifyData.getDatasource().get(position).getId();
                tvClassify.setText(classifyData.getDatasource().get(position).getName());
                classifyPop.dismiss();
                showSwipe();
                requestData();
            }
        });
        llyt_classify_content.setOnClickListener(this);
        classifyPop.setOutsideTouchable(true);
        classifyPop.setBackgroundDrawable(new BitmapDrawable());
        classifyPop.setFocusable(true);
        classifyPop.update();
        classifyPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hintView();
            }
        });
    }

    private void hintView() {
        tvTime.setSelected(false);
        tvClassify.setSelected(false);
    }


    private void requestData() {
        if (isFree) {
            AppManager.getUserRequest().getFreeConsultList(this, myListener, page, administrativeId, sortTypeApp, keyWord);
        } else {
            AppManager.getUserRequest().getVIPList(this, myListener, page);
        }
    }


    HttpTaskListener myListener = new HttpTaskListener() {
        @Override
        public void dataSucceed(String result) {
            Wrapper<?> entity = null;
            if (isFree) {
                entity = new Gson().fromJson(result, new TypeToken<Wrapper<FreeConsultListEntity>>() {
                }.getType());
            } else {
                entity = new Gson().fromJson(result, new TypeToken<Wrapper<VipList>>() {
                }.getType());
            }
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
                if (isFree) {
                    oneData.setViewType(R.layout.item_yizhen_);
                } else {
                    oneData.setViewType(R.layout.item_yizhen_vip);
                }
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
