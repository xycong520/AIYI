package com.xiuman.xinjiankang.adapter;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.activity.ScientifitDetailActivity;
import com.xiuman.xinjiankang.app.MyApplication;
import com.xiuman.xinjiankang.bean.BeanCommonViewType;
import com.xiuman.xinjiankang.bean.BeanHomeTitle;
import com.xiuman.xinjiankang.bean.ScienceDetail;
import com.xiuman.xinjiankang.fragment.FragmentAD;
import com.xiuman.xinjiankang.fragment.FragmentRecommendDoctor;
import com.xiuman.xinjiankang.fragment.FragmentRecommendHospitor;

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

    List<BeanCommonViewType> homeViews;
    int SPAN_COUNT = 1;
    Fragment fragment;
    ImageOptions options;

    public HomepageAdapter(List<BeanCommonViewType> viewsData, Fragment fragment) {
        homeViews = viewsData;
        this.fragment = fragment;
        if (options == null) {
            options = MyApplication.getOptionsRadius();/*new ImageOptions.Builder().setRadius(10).setImageScaleType(ImageView.ScaleType.FIT_XY).setUseMemCache(true).build()*/;
        }
    }

    public void setHomeViews(List<BeanCommonViewType> homeViews) {
        this.homeViews = homeViews;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEWTYPE_AD:
                return new ADViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_ad, null));
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
                return new HospitorViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_recommend_hospitor, parent, false));
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ADViewHolder) {
            FragmentAD fragmentAD = (FragmentAD) fragment.getChildFragmentManager().findFragmentById(R.id.layoutAD);
            if (fragmentAD == null) {
                fragmentAD = (FragmentAD) homeViews.get(position).getBeanObj();
                fragment.getChildFragmentManager().beginTransaction().replace(R.id.layoutAD, fragmentAD).commit();
                if (fragmentAD.getThisView()!=null){
                    ((ADViewHolder) holder).getLayoutAD().removeAllViews();
                    ((ADViewHolder) holder).getLayoutAD().addView(fragmentAD.getThisView());
                }
            } else {
                ((ADViewHolder) holder).getLayoutAD().removeAllViews();
                ((ADViewHolder) holder).getLayoutAD().addView(fragmentAD.getThisView());
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
                    ((AllpurposeViewHolder) holder).itemView.setTag(title.getRightText());
                    ((AllpurposeViewHolder) holder).itemView.setOnClickListener(this);
                    break;
                case VIEWTYPE_DOCTORLAYOUT:
                    FragmentRecommendDoctor doctor;
                    doctor = (FragmentRecommendDoctor) fragment.getChildFragmentManager().findFragmentById(R.id.layoutRecommendDoctor);
                    if (doctor == null) {
                        doctor = (FragmentRecommendDoctor) homeViews.get(position).getBeanObj();
                        fragment.getChildFragmentManager().beginTransaction().replace(R.id.layoutRecommendDoctor, doctor).commit();
                        if (doctor.getThisView()!=null){
                            ((FrameLayout) ((AllpurposeViewHolder) holder).getViewByID(R.id.layoutRecommendDoctor)).removeAllViews();
                            ((FrameLayout) ((AllpurposeViewHolder) holder).getViewByID(R.id.layoutRecommendDoctor)).addView(doctor.getThisView());
                        }
                    } else {
                        ((FrameLayout) ((AllpurposeViewHolder) holder).getViewByID(R.id.layoutRecommendDoctor)).removeAllViews();
                        ((FrameLayout) ((AllpurposeViewHolder) holder).getViewByID(R.id.layoutRecommendDoctor)).addView(doctor.getThisView());
                    }
                    break;
                case VIEWTYPE_LISTITEM:

                    ScienceDetail detail = (ScienceDetail) homeViews.get(position).getBeanObj();
                    ((TextView) ((AllpurposeViewHolder) holder).getViewByID(R.id.title)).setText(detail.getTitle());
                    ((TextView) ((AllpurposeViewHolder) holder).getViewByID(R.id.content)).setText(detail.getDescription());
                    x.image().bind((ImageView) ((AllpurposeViewHolder) holder).getViewByID(R.id.ivIcon), detail.getIcon(), options);
                    ((AllpurposeViewHolder) holder).itemView.setTag(detail.getId());
                    ((AllpurposeViewHolder) holder).itemView.setOnClickListener(this);
                    break;
            }
        } else if (holder instanceof HospitorViewHolder) {
            FragmentRecommendHospitor hospital;
            hospital = (FragmentRecommendHospitor) fragment.getChildFragmentManager().findFragmentById(R.id.layoutRecommendHospitor);
            if (hospital == null) {
                hospital = (FragmentRecommendHospitor) homeViews.get(position).getBeanObj();
                fragment.getChildFragmentManager().beginTransaction().replace(R.id.layoutRecommendHospitor, hospital).commit();
                if (hospital.getThisView()!=null){
                    (((HospitorViewHolder) holder).getLayoutHospitor()).removeAllViews();
                    (((HospitorViewHolder) holder).getLayoutHospitor()).addView(hospital.getThisView());
                }
            } else {
                (((HospitorViewHolder) holder).getLayoutHospitor()).removeAllViews();
                (((HospitorViewHolder) holder).getLayoutHospitor()).addView(hospital.getThisView());
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
    }

    @Override
    public int getItemCount() {
        return homeViews == null ? 0 : homeViews.size();
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
            case R.id.layoutNews:
                Intent i = new Intent(v.getContext(), ScientifitDetailActivity.class);
                i.putExtra("id",String.valueOf(v.getTag()));
                fragment.startActivity(i);
                break;
            case R.id.layoutTitleMore:
                String tag = String.valueOf(v.getTag());
                Toast.makeText(v.getContext(), tag, Toast.LENGTH_SHORT).show();
                if (tag.contains("附近")){

                }
                break;
        }
    }


    class ADViewHolder extends RecyclerView.ViewHolder {

        FrameLayout layoutAD;

        public ADViewHolder(View itemView) {
            super(itemView);
            layoutAD = (FrameLayout) itemView.findViewById(R.id.layoutAD);
        }

        public FrameLayout getLayoutAD() {
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

    class HospitorViewHolder extends RecyclerView.ViewHolder {

        FrameLayout layoutHospitor;

        public HospitorViewHolder(View itemView) {
            super(itemView);
            layoutHospitor = (FrameLayout) itemView.findViewById(R.id.layoutRecommendHospitor);
        }

        public FrameLayout getLayoutHospitor() {
            return layoutHospitor;
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
