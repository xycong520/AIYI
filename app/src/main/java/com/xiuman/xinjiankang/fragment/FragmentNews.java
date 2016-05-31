package com.xiuman.xinjiankang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiuman.xingjiankang.R;

import butterknife.ButterKnife;

/**
 * 资讯
 * Created by PCPC on 2016/5/31.
 */
public class FragmentNews extends Fragment {
    View thisView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (thisView == null) {
            thisView = inflater.inflate(R.layout.fragment_news, container, false);
            ButterKnife.bind(this, thisView);
            init();
        }

        ViewGroup parent = (ViewGroup) thisView.getParent();
        if (parent != null) {
            parent.removeView(thisView);
        }
        return thisView;
    }

    private void init(){

    }
}
