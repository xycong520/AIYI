package com.xiuman.xinjiankang.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
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
import com.xiuman.xinjiankang.bean.Doctor;
import com.xiuman.xinjiankang.bean.DoctorCity;
import com.xiuman.xinjiankang.bean.DoctorProvince;
import com.xiuman.xinjiankang.constant.Constant;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.net.Wrapper;
import com.xiuman.xinjiankang.utils.LocationUtils;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by PCPC on 2016/6/13.
 */
public class SelectDoctorActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, BDLocationListener {
    public static final String HAS_FILTER = "filter";
    boolean hasFilter = true;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerview;
    CommonRecyclerViewAdapter mAdapter;

    @Bind(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.layoutSearch)
    RelativeLayout layoutSearch;
    @Bind(R.id.search_option)
    LinearLayout layoutOption;
    @Bind(R.id.tvTitle)
    TextView tvTitle;

    @Bind(R.id.tv_area)
    TextView tvAre;
    @Bind(R.id.tv_sort)
    TextView tvSort;
    @Bind(R.id.tv_classify)
    TextView tvClassify;

    //省市数据
    Wrapper<DoctorProvince> provinces;
    //科室数据
    Wrapper<Dministartive> classifyData;
    int page = 1;
    //地区ID
    private String areaId = "";
    //分类
    private String sections = "";
    //搜索关键字
    private String keyWord = "";
    //排序
    private int sequence = 0;
    //类别
    private int docService = 2;

    List<BeanCommonViewType> mDatas = new ArrayList<>();
    BeanCommonViewType loadMore;
    int lastVisibleItem;
    LinearLayoutManager layoutManager;

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
                        break;
                    case R.layout.item_search_doctor:
                        holder.itemView.setTag(position);
                        holder.itemView.setOnClickListener(SelectDoctorActivity.this);
                        Doctor doctor = (Doctor) mDatas.get(position).getBeanObj();
                        TextView popularity = holder.getView(R.id.popularity);
                        TextView name = holder.getView(R.id.name);
                        TextView profession = holder.getView(R.id.profession);
                        TextView address = holder.getView(R.id.address);
                        TextView synopsis = holder.getView(R.id.synopsis);
                        ImageView icon = holder.getView(R.id.ivPhoto);
                        popularity.setText("" + doctor.getPopularity());
                        name.setText(doctor.getName());
                        profession.setText(doctor.getDoctorPost());
                        address.setText(doctor.getArea());
                        synopsis.setText("简介：" + doctor.getIntroduce());
                        x.image().bind(icon, doctor.getHeadimgurl(), options);
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
        hasFilter = getIntent().getBooleanExtra(HAS_FILTER, false);
        if (!hasFilter) {
            layoutSearch.setVisibility(View.GONE);
            layoutOption.setVisibility(View.GONE);
            tvTitle.setVisibility(View.VISIBLE);
            location();
        } else {
            tvAre.setClickable(false);
            requestDepartment();
            loadProvinces();
            location();
        }
    }

    /**
     * 加载省份城市数据
     */
    private void loadProvinces() {
        AppManager.getUserRequest().getAreaList(mActivity, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                provinces = new Gson().fromJson(result, new TypeToken<Wrapper<DoctorProvince>>() {
                }.getType());
                initAddressPop();
                tvAre.setClickable(true);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void dataError(String result) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
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

            }
        });
    }

    @Override
    protected int getView() {
        return R.layout.activity_selectdoctor;
    }

    PopupWindow addressPop;
    //当前选择的省份和城市
    int curProIndex, curCityIndex;

    //初始化地区的PopupWindow
    private void initAddressPop() {
        View inflate = inflater.inflate(R.layout.layout_pop_address, null);
        addressPop = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ListView listview1 = (ListView) inflate.findViewById(R.id.listview1);
        ListView listview2 = (ListView) inflate.findViewById(R.id.listview2);
        final CommonAdapter provinceAdapter = new CommonAdapter<DoctorProvince>(mActivity, provinces.getDatasource(), R.layout.item_provience_city) {
            @Override
            public void convert(ViewHolder holper, DoctorProvince item) {
                TextView tvPro = holper.getView(R.id.tvName);
                if (curProIndex == holper.getPosition()) {
                    holper.getConvertView().setBackgroundResource(R.color.color_white);
                    tvPro.setSelected(true);
                } else {
                    holper.getConvertView().setBackgroundResource(R.color.color_bg);
                    tvPro.setSelected(false);
                }
                tvPro.setText(item.getRootName());
            }
        };
        listview1.setAdapter(provinceAdapter);
        final CommonAdapter cityAdapter = new CommonAdapter<DoctorCity>(mActivity, provinces.getDatasource().get(0).getChildArea(), R.layout.item_provience_city) {
            @Override
            public void convert(ViewHolder holper, DoctorCity item) {
                TextView tvCity = holper.getView(R.id.tvName);
                if (curCityIndex == holper.getPosition()) {
                    holper.getConvertView().setBackgroundResource(R.color.color_white);
                    tvCity.setSelected(true);
                } else {
                    holper.getConvertView().setBackgroundResource(R.color.color_bg);
                    tvCity.setSelected(false);
                }
                tvCity.setText(item.getChildName());
            }
        };
        listview2.setAdapter(cityAdapter);
        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                curProIndex = position;
                curCityIndex = 0;
                cityAdapter.setmDatas(provinces.getDatasource().get(position).getChildArea());
                cityAdapter.notifyDataSetChanged();
                provinceAdapter.notifyDataSetChanged();
            }
        });
        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                curCityIndex = position;
                areaId = provinces.getDatasource().get(curProIndex).getChildArea().get(curCityIndex).getChildId();
                tvAre.setText(provinces.getDatasource().get(curProIndex).getChildArea().get(curCityIndex).getChildName());
                cityAdapter.notifyDataSetChanged();
                addressPop.dismiss();
                showSwipe();
                requestData();
            }
        });
        addressPop.setOutsideTouchable(true);
        addressPop.setBackgroundDrawable(new BitmapDrawable());
        addressPop.setFocusable(true);
        addressPop.update();
        addressPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hintView();
            }
        });
    }

    private void hintView() {
        tvAre.setSelected(false);
        tvSort.setSelected(false);
        tvClassify.setSelected(false);
    }

    PopupWindow sortPop;

    //初始化排序的PopupWindow
    private void initSortPop() {
        View inflate = inflater.inflate(R.layout.layout_pop_sort, null);
        sortPop = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout llty_sort_content = (LinearLayout) inflate.findViewById(R.id.llty_sort_content);
        TextView capacity = (TextView) inflate.findViewById(R.id.capacity);
        TextView assess = (TextView) inflate.findViewById(R.id.assess);
        TextView reply = (TextView) inflate.findViewById(R.id.reply);
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

    //初始化分类的PopupWindow
    private void initClassifyPop() {
        View inflate = inflater.inflate(R.layout.layout_pop_classify, null);
        classifyPop = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
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
                tvClassify.setText(sections = classifyData.getDatasource().get(position).getName());
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

    @Override
    @OnClick({R.id.tv_area, R.id.tv_sort, R.id.tv_classify})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.layoutSearchDoctor:
                int index = (int) view.getTag();
                Intent i = new Intent(mActivity, DoctorDetailActivity.class);
                i.putExtra(DoctorDetailActivity.parameID,((Doctor)mDatas.get(index).getBeanObj()).getDoctorId());
                startActivity(i);
                break;
            //选择地区
            case R.id.tv_area:
                hintView();
                if (addressPop != null) {
                    addressPop.showAsDropDown(view);
                }
                break;
            //排序
            case R.id.tv_sort:
                if (sortPop != null) {
                    sortPop.showAsDropDown(view);
                } else {
                    initSortPop();
                    sortPop.showAsDropDown(view);
                }
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
            //智能排序
            case R.id.capacity:
                sequence = 0;
                tvSort.setText(((TextView) view).getText().toString());
                sortPop.dismiss();
                showSwipe();
                requestData();
                break;
            //评价最多
            case R.id.assess:
                sequence = 1;
                tvSort.setText(((TextView) view).getText().toString());
                sortPop.dismiss();
                showSwipe();
                requestData();
                break;
            //回复最多
            case R.id.reply:
                sequence = 2;
                tvSort.setText(((TextView) view).getText().toString());
                sortPop.dismiss();
                showSwipe();
                requestData();
                break;
        }
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


    @Override
    public void onRefresh() {
        page = 1;
        mDatas.clear();
        loadMore = null;
        requestData();
    }


    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        latLng = bdLocation;
        requestData();
    }

    //获取定位
    LocationUtils locationUtils;

    private void location() {
        locationUtils = new LocationUtils(this);
        locationUtils.setListener(this);
        locationUtils.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationUtils.stop();
    }

    BDLocation latLng;

    private void requestData() {
        if (hasFilter) {
            AppManager.getUserRequest().
                    getDoctorList(mActivity,
                            taskListener, page, Constant.PAGE_SIZE,
                            latLng == null ? 0.0 : latLng.getLongitude(),
                            latLng == null ? 0.0 : latLng.getLatitude(), areaId, sequence, sections, docService, keyWord);
        } else {
            AppManager.getUserRequest().getDoctorList(mActivity, taskListener, page, Constant.PAGE_SIZE, latLng == null ? 0.0 : latLng.getLongitude(),
                    latLng == null ? 0.0 : latLng.getLatitude());
        }
    }


    HttpTaskListener taskListener = new HttpTaskListener() {
        @Override
        public void dataSucceed(String result) {
            Wrapper<Doctor> entity = new Gson().fromJson(result, new TypeToken<Wrapper<Doctor>>() {
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
                oneData.setViewType(R.layout.item_search_doctor);
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
