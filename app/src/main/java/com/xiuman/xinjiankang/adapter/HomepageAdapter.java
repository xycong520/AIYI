package com.xiuman.xinjiankang.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.Bean.BeanHomeTitle;
import com.xiuman.xinjiankang.Bean.BeanHomeView;
import com.xiuman.xinjiankang.base.ScienceDetail;
import com.xiuman.xinjiankang.fragment.FragmentAD;
import com.xiuman.xinjiankang.fragment.FragmentRecommendDoctorOrHospital;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by PCPC on 2016/5/24.
 */
public class HomepageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    public static final int VIEWTYPE_AD = 1;
    public static final int VIEWTYPE_ZIXUN = 2;
    public static final int VIEWTYPE_XINLI = 3;
    public static final int VIEWTYPE_TITLE = 4;
    public static final int VIEWTYPE_DOCTORLAYOUT = 5;
    public static final int VIEWTYPE_LISTITEM = 6;
    public static final int VIEWTYPE_HOSPITALlAYOUT = 7;

    List<BeanHomeView> homeViews;
    int SPAN_COUNT = 1;
    Fragment fragment;
    ImageOptions options;

    public HomepageAdapter(List<BeanHomeView> viewsData, Fragment fragment) {
        homeViews = viewsData;
        this.fragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEWTYPE_AD:
                return new ADViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_ad, parent, false));
            case VIEWTYPE_ZIXUN:
                return new ZIXUNViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_zixun, parent, false));
            case VIEWTYPE_XINLI:
                return new XinliViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_xinli, parent, false));
            case VIEWTYPE_TITLE:
                return new AllpurposeViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_title_more, parent, false));
            case VIEWTYPE_DOCTORLAYOUT:
                return new AllpurposeViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_recommend_doctor, parent, false));
            case VIEWTYPE_LISTITEM:
                return new AllpurposeViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_xjk_news, parent, false));
            case VIEWTYPE_HOSPITALlAYOUT:
                return new AllpurposeViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_recommend_hospitor, parent, false));
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ADViewHolder) {
            FragmentAD fragmentAD = (FragmentAD) fragment.getChildFragmentManager().findFragmentById(R.id.layoutAD);
            if (fragmentAD == null){
                fragment.getChildFragmentManager().beginTransaction().add(R.id.layoutAD, new FragmentAD()).commit();
            }
        } else if (holder instanceof ZIXUNViewHolder) {
            ((ZIXUNViewHolder) holder).getLayoutZX().setOnClickListener(this);
            ((ZIXUNViewHolder) holder).getLayoutDoctor().setOnClickListener(this);
            ((ZIXUNViewHolder) holder).getLayoutZD().setOnClickListener(this);
            ((ZIXUNViewHolder) holder).getLayoutKP().setOnClickListener(this);
        } else if (holder instanceof XinliViewHolder) {
            ((XinliViewHolder) holder).getLayoutXinli().setOnClickListener(this);
        } else if (holder instanceof AllpurposeViewHolder) {
            switch (getItemViewType(position)) {
                case VIEWTYPE_TITLE:
                    BeanHomeTitle title = (BeanHomeTitle) homeViews.get(position).getBeanObj();
                    ((TextView) ((AllpurposeViewHolder) holder).getViewByID(R.id.tvMoreOrOther)).setText(title.getRightText());
                    ((TextView) ((AllpurposeViewHolder) holder).getViewByID(R.id.tvTitle)).setText(title.getTitleName());
                    ((ImageView) ((AllpurposeViewHolder) holder).getViewByID(R.id.ivIcon)).setImageResource(title.getImgID());
                    break;
                case VIEWTYPE_DOCTORLAYOUT:
                    FragmentRecommendDoctorOrHospital doctor;
                    doctor = (FragmentRecommendDoctorOrHospital) fragment.getChildFragmentManager().findFragmentById(R.id.layoutRecommendDoctor);
                    if (doctor == null) {
                        doctor = new FragmentRecommendDoctorOrHospital();
                        Bundle bundle = new Bundle();
                        bundle.putInt(FragmentRecommendDoctorOrHospital.LOADTYPE, FragmentRecommendDoctorOrHospital.typeDoctor);
                        doctor.setArguments(bundle);
                        fragment.getChildFragmentManager().beginTransaction().add(R.id.layoutRecommendDoctor, doctor).commit();
                    }
                    break;
                case VIEWTYPE_LISTITEM:
                    if (options == null){
                        options = new ImageOptions.Builder().setRadius(20).build();
                    }
                    ScienceDetail detail = (ScienceDetail) homeViews.get(position).getBeanObj();
                    ((TextView) ((AllpurposeViewHolder) holder).getViewByID(R.id.title)).setText(detail.getTitle());
                    ((TextView) ((AllpurposeViewHolder) holder).getViewByID(R.id.content)).setText(detail.getDescription());
                    x.image().bind((ImageView) ((AllpurposeViewHolder) holder).getViewByID(R.id.icon), detail.getIcon(),options);
                    ((AllpurposeViewHolder) holder).itemView.setTag(detail.getTitle());
                    ((AllpurposeViewHolder) holder).itemView.setOnClickListener(this);
                    break;
                case VIEWTYPE_HOSPITALlAYOUT:
                    FragmentRecommendDoctorOrHospital hospital;
                    hospital = (FragmentRecommendDoctorOrHospital) fragment.getChildFragmentManager().findFragmentById(R.id.layoutRecommendHospitor);
                    if (hospital == null) {
                        hospital = new FragmentRecommendDoctorOrHospital();
                        Bundle bundle2 = new Bundle();
                        bundle2.putInt(FragmentRecommendDoctorOrHospital.LOADTYPE, FragmentRecommendDoctorOrHospital.typeHospital);
                        hospital.setArguments(bundle2);
                        fragment.getChildFragmentManager().beginTransaction().add(R.id.layoutRecommendHospitor, hospital).commit();
                    }
                    break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return homeViews.get(position).getViewType();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        ((GridLayoutManager) recyclerView.getLayoutManager()).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (getItemViewType(position)) {
                    default:
                        return SPAN_COUNT;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeViews.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rela_jkzx:
                Toast.makeText(v.getContext(), "免费咨询", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_consult:
                Toast.makeText(v.getContext(), "自我诊断", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_know:
                Toast.makeText(v.getContext(), "科普知识", Toast.LENGTH_SHORT).show();
                break;
            case R.id.llyt_search_doctor:
                Toast.makeText(v.getContext(), "找医生", Toast.LENGTH_SHORT).show();
                break;
            case R.id.xinli:
                Toast.makeText(v.getContext(), "心理专栏", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    class ADViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layoutAD;

        public ADViewHolder(View itemView) {
            super(itemView);
            layoutAD = (LinearLayout) itemView.findViewById(R.id.layoutAD);
        }

        public LinearLayout getLayoutAD() {
            return layoutAD;
        }
    }

    class ZIXUNViewHolder extends RecyclerView.ViewHolder {

        //咨询，诊断，找医生
        LinearLayout layoutZX, layoutZD, layoutDoctor;
        //科普
        RelativeLayout layoutKP;

        public ZIXUNViewHolder(View itemView) {
            super(itemView);
            layoutZX = (LinearLayout) itemView.findViewById(R.id.rela_jkzx);
            layoutZD = (LinearLayout) itemView.findViewById(R.id.my_consult);
            layoutDoctor = (LinearLayout) itemView.findViewById(R.id.llyt_search_doctor);
            layoutKP = (RelativeLayout) itemView.findViewById(R.id.iv_know);
        }

        public LinearLayout getLayoutZX() {
            return layoutZX;
        }

        public LinearLayout getLayoutZD() {
            return layoutZD;
        }

        public LinearLayout getLayoutDoctor() {
            return layoutDoctor;
        }

        public RelativeLayout getLayoutKP() {
            return layoutKP;
        }
    }

    class XinliViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout layoutXinli;

        public XinliViewHolder(View itemView) {
            super(itemView);
            layoutXinli = (RelativeLayout) itemView.findViewById(R.id.xinli);
        }

        public RelativeLayout getLayoutXinli() {
            return layoutXinli;
        }
    }

    class TitleMoreViewHolder extends RecyclerView.ViewHolder {

        ImageView ivIcon;
        TextView tvTitle, tvMore;

        public TitleMoreViewHolder(View itemView) {
            super(itemView);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvMore = (TextView) itemView.findViewById(R.id.tvMoreOrOther);
        }

        public ImageView getIvIcon() {
            return ivIcon;
        }

        public TextView getTvTitle() {
            return tvTitle;
        }

        public TextView getTvMore() {
            return tvMore;
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
