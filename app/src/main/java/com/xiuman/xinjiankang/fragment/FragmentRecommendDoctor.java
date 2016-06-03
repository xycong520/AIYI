package com.xiuman.xinjiankang.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.bean.RecommendDoctor;
import com.xiuman.xinjiankang.Request.UserRequest;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.net.Wrapper;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 推荐医师
 * Created by PCPC on 2016/5/24.
 */
public class FragmentRecommendDoctor extends Fragment {

    private Activity mActivity;
    View thisView;
    @Bind(R.id.recyclerview_horizontal)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (thisView == null) {
            thisView = inflater.inflate(R.layout.fragment_recommend_, container, false);
            ButterKnife.bind(this, thisView);
            //初始化
            {
                //设置布局管理器
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                mActivity = getActivity();
                loadData();
            }
        }
        //因为共用一个Fragment视图，所以当前这个视图已被加载到Activity中，必须先清除后再加入Activity
        ViewGroup parent = (ViewGroup) thisView.getParent();
        if (parent != null) {
            parent.removeView(thisView);
        }

        return thisView;
    }

    public View getThisView() {
        return thisView;
    }

    public void loadData() {
        new UserRequest().getRecommendDoctorList(mActivity, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                Wrapper<RecommendDoctor> doctorlist = new Gson().fromJson(result, new TypeToken<Wrapper<RecommendDoctor>>() {
                }.getType());
                //设置适配器
                GalleryDoctorAdapter mAdapter = new GalleryDoctorAdapter(mActivity, doctorlist);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void dataError(String result) {
            }
        });

    }

    class GalleryDoctorAdapter extends RecyclerView.Adapter<GalleryDoctorAdapter.ViewHolder> {

        private LayoutInflater mInflater;
        Wrapper<RecommendDoctor> dataList;
        ImageOptions options;

        public GalleryDoctorAdapter(Context context, Wrapper<RecommendDoctor> doctorlist) {
            mInflater = LayoutInflater.from(context);
            this.dataList = doctorlist;
            options = new ImageOptions.Builder().setCircular(true).setUseMemCache(true)
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP).build();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View arg0) {
                super(arg0);
            }

            ImageView mImg;
            TextView tvName;
        }

        @Override
        public int getItemCount() {
            return dataList.getDatasource().size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = mInflater.inflate(R.layout.item_recom_doctor,
                    viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.mImg = (ImageView) view
                    .findViewById(R.id.ivIcon);
            viewHolder.tvName = (TextView) view.findViewById(R.id.name);
            ;
            return viewHolder;
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.showToast(v.getContext(), v.getTag().toString());
            }
        };


        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, int i) {
            x.image().bind(viewHolder.mImg, (dataList.getDatasource().get(i)).getHeadimgurl(), options);
            viewHolder.tvName.setText((dataList.getDatasource().get(i)).getName());
            viewHolder.itemView.setTag(dataList.getDatasource().get(i).getDoctorId());
            viewHolder.itemView.setOnClickListener(onClickListener);
        }
    }
}
