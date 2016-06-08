package com.xiuman.xinjiankang.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.app.AppConfig;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.base.BaseActivity;
import com.xiuman.xinjiankang.bean.SearchHistory;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.net.Wrapper;
import com.xiuman.xinjiankang.utils.SpUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by PCPC on 2016/6/8.
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {
    public final static String parameIsConsul = "isConsult";

    @Bind(R.id.search_content)
    EditText etSearch;
    @Bind(R.id.search)
    ImageView ivSearch;
    @Bind(R.id.host_search)
    GridLayout mGridLayout;
    @Bind(R.id.clean)
    TextView tvClean;
    @Bind(R.id.history_search)
    LinearLayout mHistoryListLayout;
    List<SearchHistory> historyKey;
    private boolean isConsult;


    @Override
    protected void initView() {
        setupToolbar();
        isConsult = getIntent().getBooleanExtra(parameIsConsul, false);
        setToolbarTitle("搜索");
        getHotSearchData();
        try {
            getHistoryList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ivSearch.setOnClickListener(this);
        tvClean.setOnClickListener(this);
    }

    @Override
    protected int getView() {
        return R.layout.activity_search;
    }

    public void getHotSearchData() {
        AppManager.getUserRequest().getSearchHistory(this, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                Wrapper<SearchHistory> wrapper = new Gson().fromJson(result, new TypeToken<Wrapper<SearchHistory>>() {
                }.getType());
                List<SearchHistory> mDatas = wrapper.getDatasource();
                for (int i = 0; i < mDatas.size(); i++) {
                    mGridLayout.addView(getText(i, mDatas.get(i))/*,params*/);
                }
            }

            @Override
            public void dataError(String result) {

            }
        });
    }

    private void getHistoryList() throws JSONException {
        String json = SpUtils.getString(AppConfig.KEY_SEARCH_LIST);
        Wrapper<SearchHistory> wrapper = new Gson().fromJson(json, new TypeToken<Wrapper<SearchHistory>>() {
        }.getType());
        if (wrapper != null && wrapper.getDatasource() != null) {
            historyKey = wrapper.getDatasource();
            for (int i = 0; i < historyKey.size(); i++) {
                View inflate = LayoutInflater.from(mActivity).inflate(R.layout.item_search_history, null);
                TextView tv_name = (TextView) inflate.findViewById(R.id.history_name);
                tv_name.setText(historyKey.get(i).getHostKey());
                mHistoryListLayout.addView(inflate);
                inflate.setTag(historyKey.get(i).getHostKey());
                inflate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tag = (String) v.getTag();
                        Intent intent = new Intent(mActivity, SearchResultActivity.class);
                        intent.putExtra(SearchResultActivity.parameKey, tag);
                        intent.putExtra(SearchResultActivity.parameIsConsult, isConsult);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    private View getText(int i, final SearchHistory s) {
        View inflate = LayoutInflater.from(mActivity).inflate(R.layout.item_search_history, null);
        TextView tv = (TextView) inflate.findViewById(R.id.history_name);
        inflate.findViewById(R.id.ivIcon).setVisibility(View.INVISIBLE);
        tv.setTag(i);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setText(s.getHostKey());
                Intent intent = new Intent(mActivity, SearchResultActivity.class);
                intent.putExtra(SearchResultActivity.parameKey, s.getHostKey());
                intent.putExtra(SearchResultActivity.parameIsConsult, isConsult);
                startActivity(intent);
            }
        });
        String text = s.getHostKey();
        tv.setText(text);
        tv.setTextColor(Color.rgb(8, 8, 8));
//        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
//        tv.setGravity(Gravity.CENTER);
//        int textPaddingV = SizeUtil.dip2px(this, 4);
//        int textPaddingH = SizeUtil.dip2px(this, 7);
//        tv.setPadding(textPaddingH, textPaddingV, textPaddingH, textPaddingV);
        return inflate;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.clean:
                break;
            case R.id.search:
                String trim = etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    AppManager.showToast(mActivity, "搜索内容不能为空");
                } else {
                    if (historyKey == null) {
                        historyKey = new ArrayList<>();
                    }
                    SearchHistory curKey = new SearchHistory();
                    curKey.setHostKey(trim);
                    historyKey.add(curKey);
                    Wrapper<SearchHistory> wrapper = new Wrapper<>();
                    wrapper.setDatasource(historyKey);
                    SpUtils.setString(AppConfig.KEY_SEARCH_LIST, new Gson().toJson(wrapper));
                    Intent intent = new Intent(mActivity, SearchResultActivity.class);
                    intent.putExtra(SearchResultActivity.parameKey, trim);
                    intent.putExtra(SearchResultActivity.parameIsConsult, isConsult);
                    startActivity(intent);
                }
                break;
        }
    }

}
