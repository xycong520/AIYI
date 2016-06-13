package com.xiuman.xinjiankang.activity;

import android.graphics.drawable.BitmapDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.xiuman.xinjiankang.adapter.ViewHolder;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.base.BaseActivity;
import com.xiuman.xinjiankang.bean.DoctorCity;
import com.xiuman.xinjiankang.bean.DoctorProvince;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.net.Wrapper;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by PCPC on 2016/6/13.
 */
public class SelectDoctorActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
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

    Wrapper<DoctorProvince> provinces;

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
        hasFilter = getIntent().getBooleanExtra(HAS_FILTER, false);
        if (!hasFilter) {
            layoutSearch.setVisibility(View.GONE);
            layoutOption.setVisibility(View.GONE);
            tvTitle.setVisibility(View.VISIBLE);
        } else {
            tvAre.setClickable(false);
            loadProvinces();
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

    @Override
    protected int getView() {
        return R.layout.activity_selectdoctor;
    }

    PopupWindow addressPop;
    //当前选择的省份和城市
    int curProIndex, curCityIndex;

    //初始化地区的PopupWindow
    private void initAddressPop() {
        View inflate = inflater.inflate(R.layout.layout_address_pop, null);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int heightPixels = displayMetrics.heightPixels;
        addressPop = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout llyt_address_content = (LinearLayout) inflate.findViewById(R.id.llyt_address_content);
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
                Log.i("cityAdapter position:", holper.getPosition() + "");
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
                tvAre.setText(provinces.getDatasource().get(curProIndex).getChildArea().get(curCityIndex).getChildName());
                cityAdapter.notifyDataSetChanged();
                addressPop.dismiss();
            }
        });
        addressPop.setOutsideTouchable(true);
        addressPop.setBackgroundDrawable(new BitmapDrawable());
        addressPop.setFocusable(true);
        addressPop.update();
    }

    @Override
    @OnClick({R.id.tv_area, R.id.tv_sort, R.id.tv_classify})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_area:
                if (addressPop != null) {
                    addressPop.showAsDropDown(view);
                }
                break;
            case R.id.tv_sort:
                break;
            case R.id.tv_classify:
                break;
        }
    }

    @Override
    public void onRefresh() {

    }
}
