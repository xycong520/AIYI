package com.xiuman.xinjiankang.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.xiuman.xinjiankang.app.MyApplication;
import com.xiuman.xinjiankang.bean.BeanCommonViewType;

import org.xutils.image.ImageOptions;

import java.util.List;

/**
 * Created by PCPC on 2016/5/26.
 */
public abstract class CommonRecyclerViewAdapter extends RecyclerView.Adapter {
    List<BeanCommonViewType> datas;
    public ImageOptions options,optionsRadius;

    public CommonRecyclerViewAdapter(List<BeanCommonViewType> datas) {
        this.datas = datas;
        options = MyApplication.getOptionsPhoto();
        optionsRadius = MyApplication.getOptionsRadius();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonViewHolder viewHolder = CommonViewHolder.get(parent.getContext(), parent, viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        convert((CommonViewHolder) holder, position);
    }

    public abstract void convert(CommonViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return datas.get(position).getViewType();
    }

    public void setDatas(List<BeanCommonViewType> datas) {
        this.datas = datas;
    }
}
