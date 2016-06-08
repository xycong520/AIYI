package com.xiuman.xinjiankang.adapter;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.app.MyApplication;
import com.xiuman.xinjiankang.bean.BeanCommonViewType;
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

    List<BeanCommonViewType> datas;
    ImageOptions options;

    public DiseaseDetailAdapter(List<BeanCommonViewType> datas) {
        this.datas = datas;
        options = MyApplication.getOptionsPhoto();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEWTYPE_EMPTY) {
            return new AllpurposeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_network_error, parent, false));
        } else if (viewType == VIEWTYPE_CASE) {
            return new CaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_disease, parent, false));
        } else if (viewType == VIEWTYPE_HEADVIEW) {
            return new HeadViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_disrase_detail_head, parent, false));
        } else if (viewType == VIEWTYPE_LOADMORE) {
            return new AllpurposeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loadmore, parent, false));
        }else if (viewType==VIEWTYPE_CASE_EMPTY){
            return new AllpurposeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_no_comment, parent, false));
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEWTYPE_CASE_EMPTY:
                ((TextView)((AllpurposeViewHolder)holder).getViewByID(R.id.tvEmptyText)).setText("还没有相关案例~~~");
                break;
            case VIEWTYPE_HEADVIEW:
                ((HeadViewHolder) holder).getTvPatient().setOnClickListener(onClickListener);
                break;
            case VIEWTYPE_CASE:
                Disease disease = (Disease) datas.get(position).getBeanObj();
                ((CaseViewHolder) holder).getTvName().setText(disease.getDoctorName());
                ((CaseViewHolder) holder).getTvSex().setText(disease.getSex());
                ((CaseViewHolder) holder).getTvAge().setText(String.valueOf(disease.getAge()));
                final TextView tvContent = ((CaseViewHolder) holder).getTvContent();
                final TextView tvExpand = ((CaseViewHolder) holder).getTvExpand();
                tvContent.setText(disease.getContent());
                ViewTreeObserver vto = tvContent.getViewTreeObserver();
//                vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//                    @Override
//                    public boolean onPreDraw() {
//                        return true;
//                    }
//                });
                if (tvContent.getText().length()>60){
                    tvExpand.setVisibility(View.VISIBLE);
                    if (tvExpand.getTag()==null){
                        tvExpand.setTag(false);
                    }
                    tvExpand.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean isExpand = (boolean) v.getTag();
                            if (isExpand){
                                ((TextView)v).setText("显示更多");
                                tvContent.setMaxLines(3);
                            }else{
                                ((TextView)v).setText("收起");
                                tvContent.setMaxLines(99);
                            }
                            tvContent.postInvalidate();
                            notifyDataSetChanged();
                            v.setTag(!isExpand);
                        }
                    });
                }else{
                    tvExpand.setVisibility(View.GONE);
                }
                x.image().bind(((CaseViewHolder) holder).getIvIcon(), disease.getDoctorHead(), options);
                ((CaseViewHolder) holder).getLayoutDoctorInfo().setTag(position);
               ((CaseViewHolder) holder).getLayoutDoctorInfo().setOnClickListener(onClickListener);
                break;
            case VIEWTYPE_LOADMORE:
                TextView tvLoading = (TextView) ((AllpurposeViewHolder) holder).getViewByID(R.id.tvLoading);
                tvLoading.setVisibility(View.VISIBLE);
                JumpingBeans jumpingBeans;
                jumpingBeans = JumpingBeans.with(tvLoading).appendJumpingDots()
                        .build();
                break;
            case VIEWTYPE_EMPTY:
                holder.itemView.setOnClickListener(onClickListener);
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

    public void setDatas(List<BeanCommonViewType> datas) {
        this.datas = datas;
    }

    class CaseViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layoutDoctorInfo;
        ImageView ivIcon;
        TextView tvName, tvExpand, tvSex, tvAge, tvContent;

        public CaseViewHolder(View itemView) {
            super(itemView);
            layoutDoctorInfo = (LinearLayout) itemView.findViewById(R.id.layoutDoctorInfo);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
            tvExpand = (TextView) itemView.findViewById(R.id.tvExpand);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvSex = (TextView) itemView.findViewById(R.id.tvSex);
            tvAge = (TextView) itemView.findViewById(R.id.tvAge);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
        }

        public LinearLayout getLayoutDoctorInfo() {
            return layoutDoctorInfo;
        }

        public TextView getTvExpand() {
            return tvExpand;
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
