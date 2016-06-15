package com.xiuman.xinjiankang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.bean.BeanCommonViewType;
import com.xiuman.xinjiankang.bean.ScienceItemDetail;
import com.xiuman.xinjiankang.bean.ScientificComment;
import com.xiuman.xinjiankang.app.MyApplication;
import com.xiuman.xinjiankang.utils.DateUtils;

import net.frakbot.jumpingbeans.JumpingBeans;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by PCPC on 2016/5/26.
 */
public class ScientifitDetailAdapter extends RecyclerView.Adapter {
    //空
    private static final int VIEWTYPE_EMPTY = -1;
    //加载更多
    public static final int VIEWTYPE_LOADMORE = 0;
    //知识详情内容
    public static final int VIEWTYPE_CONTENT = 1;
    //评论
    public static final int VIEWTYPE_COMMENT = 2;
    //评论空
    public static final int VIEWTYPE_COMMENT_EMPTEY = 3;

    List<BeanCommonViewType> datas;
    Context mContext;
    ImageOptions options;

    public ScientifitDetailAdapter(List<BeanCommonViewType> datas) {
        this.datas = datas;
        options = new ImageOptions.Builder().setUseMemCache(true).setLoadingDrawableId(R.drawable.onloading).setImageScaleType(ImageView.ScaleType.FIT_XY).build();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEWTYPE_EMPTY) {
//            return new AllpurposeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout))
        } else if (viewType == VIEWTYPE_CONTENT) {
            return new ContentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_scientific_content, parent, false));
        } else if (viewType == VIEWTYPE_COMMENT) {
            return new CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scientifit_comment, parent, false));
        }else if(viewType == VIEWTYPE_COMMENT_EMPTEY){
            return new AllpurposeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_no_comment, parent, false));
        }else if(viewType == VIEWTYPE_LOADMORE){
            return new AllpurposeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loadmore, parent, false));
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEWTYPE_CONTENT:
                ScienceItemDetail detail = (ScienceItemDetail) datas.get(position).getBeanObj();
                ((ContentViewHolder) holder).getTvTitle().setText(detail.getDatasource().getTitle());
                TextView tvSupport = ((ContentViewHolder) holder).getTvSupport();
                TextView tvSupportNum = ((ContentViewHolder) holder).getTvSupporNum();
                TextView tvCollet = ((ContentViewHolder) holder).getTvCollet();
                TextView tvAskDoctor = ((ContentViewHolder) holder).getTvAskDoctor();
                tvSupportNum.setText(String.valueOf(detail.getDatasource().getPraiseCount()));
                ((ContentViewHolder) holder).getTvContent().setText(detail.getDatasource().getContent());
                x.image().bind(((ContentViewHolder) holder).getIvIcon(), detail.getDatasource().getGuideImg(), options);
                if (detail.getDatasource().isPraise()) {
                    tvSupport.setSelected(true);
                    tvSupportNum.setSelected(true);
                }
                if (detail.getDatasource().isCollect()) {
                    tvCollet.setSelected(true);
                }
                //放入tag便于在activity中使用
                tvSupport.setTag(R.id.tv_support_add, ((ContentViewHolder) holder).getTvAddSupport());
                tvSupport.setTag(R.id.tv_support_num, ((ContentViewHolder) holder).getTvSupporNum());
                tvSupport.setOnClickListener(onClickListener);
                tvCollet.setOnClickListener(onClickListener);
                tvAskDoctor.setOnClickListener(onClickListener);
                break;
            case VIEWTYPE_COMMENT:
                ScientificComment comment = (ScientificComment) datas.get(position).getBeanObj();
                CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
                commentViewHolder.getTvContent().setText(comment.getContent());
                commentViewHolder.getTvName().setText(comment.getNickname());
                x.image().bind(commentViewHolder.getIvIcon(), comment.getAvatar(), MyApplication.getOptionsCircularPhoto());
                commentViewHolder.getTvDate().setText(DateUtils.dateFormat2(comment.getCreateDate()));
                break;
            case VIEWTYPE_COMMENT_EMPTEY:
                break;
            case VIEWTYPE_LOADMORE:
                TextView tvLoading = (TextView) ((AllpurposeViewHolder)holder).getViewByID(R.id.tvLoading);
                tvLoading.setVisibility(View.VISIBLE);
                JumpingBeans jumpingBeans ;
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

    public void setDatas(List<BeanCommonViewType> datas) {
        this.datas = datas;
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {

        ImageView ivIcon;
        TextView tvTitle, tvContent, tvSupporNum, tvSupport, tvCollet, tvAskDoctor, tvAddSupport;

        public ContentViewHolder(View itemView) {
            super(itemView);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
            tvTitle = (TextView) itemView.findViewById(R.id.detailTitle);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
            tvSupporNum = (TextView) itemView.findViewById(R.id.tv_support_num);
            tvSupport = (TextView) itemView.findViewById(R.id.iv_support);
            tvCollet = (TextView) itemView.findViewById(R.id.iv_collect);
            tvAskDoctor = (TextView) itemView.findViewById(R.id.iv_ask_doctor);
            tvAddSupport = (TextView) itemView.findViewById(R.id.tv_support_add);
        }

        public TextView getTvAddSupport() {
            return tvAddSupport;
        }

        public ImageView getIvIcon() {
            return ivIcon;
        }

        public TextView getTvTitle() {
            return tvTitle;
        }

        public TextView getTvContent() {
            return tvContent;
        }

        public TextView getTvSupporNum() {
            return tvSupporNum;
        }

        public TextView getTvSupport() {
            return tvSupport;
        }

        public TextView getTvCollet() {
            return tvCollet;
        }

        public TextView getTvAskDoctor() {
            return tvAskDoctor;
        }
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvName, tvDate, tvContent;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
            tvName = (TextView) itemView.findViewById(R.id.tv_username);
            tvDate = (TextView) itemView.findViewById(R.id.tv_reply_time);
            tvContent = (TextView) itemView.findViewById(R.id.tv_reply_content);
        }

        public ImageView getIvIcon() {
            return ivIcon;
        }

        public TextView getTvName() {
            return tvName;
        }

        public TextView getTvDate() {
            return tvDate;
        }

        public TextView getTvContent() {
            return tvContent;
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
