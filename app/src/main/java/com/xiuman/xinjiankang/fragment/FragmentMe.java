package com.xiuman.xinjiankang.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.activity.EditUserInfoActivity;
import com.xiuman.xinjiankang.activity.LoginActivity;
import com.xiuman.xinjiankang.activity.QuestionHistoryActivity;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.app.MyApplication;
import com.xiuman.xinjiankang.bean.User;
import com.xiuman.xinjiankang.bean.UserLevel;
import com.xiuman.xinjiankang.utils.AppSpUtil;

import org.xutils.x;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的
 * Created by PCPC on 2016/5/31.
 */
public class FragmentMe extends Fragment implements View.OnClickListener {
    View thisView;

    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.iv_me_head)
    ImageView ivPhoto;
    @Bind(R.id.tv_me_user_name)
    TextView tvName;
    @Bind(R.id.tv_user_level)
    TextView tvLevel;
    @Bind(R.id.tv_user_duobi)
    TextView tvDuobi;
    @Bind(R.id.rlyt_login)
    RelativeLayout layoutLogined;
    @Bind(R.id.rlyt_unlogin)
    LinearLayout layoutUnLogined;
    @Bind(R.id.llyt_user_container)
    LinearLayout layoutUserContainer;
    User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (thisView == null) {
            thisView = inflater.inflate(R.layout.fragment_me, container, false);
            ButterKnife.bind(this, thisView);
            init();
        }

        ViewGroup parent = (ViewGroup) thisView.getParent();
        if (parent != null) {
            parent.removeView(thisView);
        }
        return thisView;
    }

    private void init() {

    }

    @Override
    @OnClick({R.id.tv_login,R.id.iv_me_head,R.id.llyt_my_questions})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                break;
            case R.id.iv_me_head:
                v.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.xjk_me_photo_check));
                i = new Intent(getActivity(), EditUserInfoActivity.class);
                startActivity(i);
                break;
            case R.id.llyt_my_questions:
                if (AppManager.isUserLogin()){
                    i = new Intent(getActivity(), QuestionHistoryActivity.class);
                }else{
                    i = new Intent(getActivity(),LoginActivity.class);
                }
                startActivity(i);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AppManager.isUserLogin()) {
            layoutUnLogined.setVisibility(View.GONE);
            layoutLogined.setVisibility(View.VISIBLE);
            user = AppSpUtil.getInstance().getUserInfo();
            initUserInfo();
        } else {
            layoutUnLogined.setVisibility(View.VISIBLE);
            layoutLogined.setVisibility(View.GONE);
        }
    }

    private void initUserInfo() {
        if (user.isDoctor()) {
            layoutUserContainer.setVisibility(View.GONE);
            tvName.setText(user.getDoctorName());
            x.image().bind(ivPhoto, user.getDoctorHead(), MyApplication.optionsCircularPhoto);
        } else {
            layoutUserContainer.setVisibility(View.VISIBLE);
            x.image().bind(ivPhoto, user.getAvatar(), MyApplication.optionsCircularPhoto);
            tvName.setText(user.getNickname());
        }
        UserLevel level = AppSpUtil.getInstance().getUserLevel();
        //等级信息如果为空。隐藏
        if (level == null) {
            tvLevel.setVisibility(TextView.INVISIBLE);
        } else {
            tvLevel.setText("LV" + level.getGroupId() + " "
                    + level.getGroupName());
            if (!level.isSex()) {
                tvLevel.setTextColor(Color.parseColor("#FB879E"));
            } else {
                tvLevel.setTextColor(Color.parseColor("#56D9FA"));
            }
        }
    }
}
