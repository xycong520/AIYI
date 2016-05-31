package com.xiuman.xinjiankang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.bean.BeanHomeView;
import com.xiuman.xinjiankang.bean.Disease;

import net.frakbot.jumpingbeans.JumpingBeans;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by PCPC on 2016/5/26.
 */
public class DiseaseDetailAdapter extends RecyclerView.Adapter {
    //空
    private static final int VIEWTYPE_EMPTY = -1;
    //加载更多
    public static final int VIEWTYPE_LOADMORE = 0;
    //疾病详情内容
    public static final int VIEWTYPE_HEADVIEW = 1;
    //相关案例
    public static final int VIEWTYPE_CASE = 2;
    public static final int VIEWTYPE_CASE_EMPTY = 3;

    List<BeanHomeView> datas;
    Context mContext;
    ImageOptions options;

    public DiseaseDetailAdapter(List<BeanHomeView> datas) {
        this.datas = datas;
        options = new ImageOptions.Builder().setUseMemCache(true).setLoadingDrawableId(R.drawable.onloading3).setImageScaleType(ImageView.ScaleType.FIT_XY).build();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEWTYPE_EMPTY) {
//            return new AllpurposeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout))
        } else if (viewType == VIEWTYPE_CASE) {
            return new CaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_disease, parent, false));
        } else if (viewType == VIEWTYPE_HEADVIEW) {
            return new HeadViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_disrase_detail_head, parent, false));
        } else if (viewType == VIEWTYPE_LOADMORE) {
            return new AllpurposeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loadmore, parent, false));
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEWTYPE_HEADVIEW:
                ((HeadViewHolder) holder).getTvPatient().setOnClickListener(onClickListener);
                break;
            case VIEWTYPE_CASE:
                Disease disease = (Disease) datas.get(position).getBeanObj();
                ((CaseViewHolder) holder).getTvName().setText(disease.getDoctorName());
                ((CaseViewHolder) holder).getTvSex().setText(disease.getSex());
                ((CaseViewHolder) holder).getTvAge().setText(String.valueOf(disease.getAge()));
                ((CaseViewHolder) holder).getTvContent().setText(disease.getContent());
                x.image().bind(((CaseViewHolder) holder).getIvIcon(), disease.getUserImg(),options);
                break;
            case VIEWTYPE_LOADMORE:
                TextView tvLoading = (TextView) ((AllpurposeViewHolder) holder).getViewByID(R.id.tvLoading);
                tvLoading.setVisibility(View.VISIBLE);
                JumpingBeans jumpingBeans;
                jumpingBeans = JumpingBeans.with(tvLoading).appendJumpingDots()
                        .build();
                break;
        }
    }

    View.OnClickListener onClickListener;


    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (datas == null) {
            return VIEWTYPE_EMPTY;
        }
        return datas.get(position).getViewType();
    }

    public void setDatas(List<BeanHomeView> datas) {
        this.datas = datas;
    }

    class CaseViewHolder extends RecyclerView.ViewHolder {

        ImageView ivIcon;
        TextView tvName, tvSex, tvAge, tvContent;

        public CaseViewHolder(View itemView) {
            super(itemView);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvSex = (TextView) itemView.findViewById(R.id.tvSex);
            tvAge = (TextView) itemView.findViewById(R.id.tvAge);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
        }

        public TextView getTvContent() {
            return tvContent;
        }

        public ImageView getIvIcon() {
            return ivIcon;
        }

        public TextView getTvName() {
            return tvName;
        }

        public TextView getTvSex() {
            return tvSex;
        }

        public TextView getTvAge() {
            return tvAge;
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {
        TextView tvPatient;

        public HeadViewHolder(View itemView) {
            super(itemView);
            tvPatient = (TextView) itemView.findViewById(R.id.tv_patient);
        }

        public TextView getTvPatient() {
            return tvPatient;
        }
    }


    class AllpurposeViewHolder extends RecyclerView.ViewHolder {

        View itemView;

        public AllpurposeViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public View getViewByID(int id) {
            return itemView.findViewById(id);
        }
    }
}
