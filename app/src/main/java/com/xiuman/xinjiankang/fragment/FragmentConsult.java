package com.xiuman.xinjiankang.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.activity.DiseaseDetailActivity;
import com.xiuman.xinjiankang.adapter.CommonAdapter;
import com.xiuman.xinjiankang.adapter.ViewHolder;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.bean.MyConsult;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.net.Wrapper;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 诊断
 * Created by PCPC on 2016/5/31.
 */
public class FragmentConsult extends Fragment {
    View thisView;
    //类型列表
    @Bind(R.id.lvType)
    ListView mListViewType;
    //分类列表
    @Bind(R.id.lvData)
    ListView mListViewData;
    @Bind(R.id.layoutLoading)
    View layoutLoading;
    @Bind(R.id.layout_network_error)
    View layoutError;
    CommonAdapter mTypeAdapter, mDataAdapter;
    Context mContext;
    private int[][] drawable = new int[][]{
            {R.mipmap.xjk_gynaecology_nor, R.mipmap.xjk_gynaecology_pre},
            {R.mipmap.xjk_urinary_nor, R.mipmap.xjk_urinary_pre},
            {R.mipmap.xjk_mental_health_nor, R.mipmap.xjk_mental_health_pre},
            {R.mipmap.xjk_manke_nor, R.mipmap.xjk_manke_pre},
            {R.mipmap.xjk_skin_diseases_nor, R.mipmap.xjk_skin_diseases_pre},

            {R.mipmap.xjk_reproductive_nor, R.mipmap.xjk_reproductive_pre},
            {R.mipmap.xjk_obstetrics_nor, R.mipmap.xjk_obstetrics_pre},};
    int count;
    Wrapper<MyConsult> consult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (thisView == null) {
            thisView = inflater.inflate(R.layout.fragment_consult, container, false);
            ButterKnife.bind(this, thisView);
            init();
//            loadTypeData();
        }

        ViewGroup parent = (ViewGroup) thisView.getParent();
        if (parent != null) {
            parent.removeView(thisView);
        }
        return thisView;
    }


    private void init() {
        mContext = getActivity();
        layoutError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutLoading.setVisibility(View.VISIBLE);
                loadTypeData();
            }
        });
    }

    public void loadTypeData() {
        if (consult != null && consult.getDatasource() != null) {
            return;
        }
        AppManager.getUserRequest().getSelfDiagnoseList(mContext, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                consult = new Gson().fromJson(result, new TypeToken<Wrapper<MyConsult>>() {
                }.getType());
                setConsultData();
                layoutLoading.setVisibility(View.GONE);
                layoutError.setVisibility(View.GONE);
            }

            @Override
            public void dataError(String result) {
                AppManager.showToast(mContext,result);
                layoutLoading.setVisibility(View.GONE);
                layoutError.setVisibility(View.VISIBLE);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void setConsultData() {
        //类型adapter
        mTypeAdapter = new CommonAdapter(mContext, consult.getDatasource(), R.layout.item_consult_type) {
            @Override
            public void convert(ViewHolder helper, Object item) {
                helper.setText(R.id.tvName, ((MyConsult) item).getName());
                int position = helper.getPosition();
                int[] draw = new int[6];
                if (position < 6) {
                    draw = drawable[position];
                }
                if (position == count) {
                    helper.getConvertView().setBackgroundColor(getResources().getColor(R.color.color_white));
                    ((ImageView) helper.getView(R.id.ivIcon)).setImageResource(draw[1]);
                } else {
                    helper.getConvertView().setBackgroundColor(getResources().getColor(R.color.color_bg));
                    ((ImageView) helper.getView(R.id.ivIcon)).setImageResource(draw[0]);
                }
            }
        };
        //数据adapter
        mDataAdapter = new CommonAdapter(mContext, consult.getDatasource().get(0).getDiseaseCategory(), R.layout.item_consult_data) {
            @Override
            public void convert(ViewHolder helper, Object item) {
                helper.setText(R.id.tvName, ((MyConsult.DiseaseCategoryEntity) item).getDiseaseCategoryName());
            }
        };

        mListViewType.setAdapter(mTypeAdapter);
        mListViewData.setAdapter(mDataAdapter);

        //类型列表点击监听
        mListViewType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (count == position) {
                    return;
                }
                count = position;
                mTypeAdapter.notifyDataSetChanged();
                mDataAdapter.setmDatas(consult.getDatasource().get(count).getDiseaseCategory());
                mListViewData.setAdapter(mDataAdapter);
            }
        });

        //数据列表点击监听
        mListViewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, DiseaseDetailActivity.class);
                intent.putExtra("diseaseCategoryId", consult.getDatasource().get(count).getDiseaseCategory().get(position).getDiseaseCategoryId());
                startActivity(intent);
            }
        });
    }
}
