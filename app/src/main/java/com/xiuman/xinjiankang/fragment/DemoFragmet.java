package com.xiuman.xinjiankang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiuman.xingjiankang.R;


/**
 * Created by PCPC on 2016/5/24.
 */
public class DemoFragmet extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demo_fragment, container, false);
//        WebView gf= (WebView) view.findViewById(R.id.webview);
        int type = getArguments().getInt("type", 0);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(type == 0 ? "1st Fragment" : type == 1 ? "2nd Fragment" : type == 2 ? "3rd Fragment" : "4th Fragment");
//        String gifName = null;
//        switch (type){
//            case 0:
//                gifName = "kof1.gif";
//                break;
//            case 1:
//                gifName = "kof2.gif";
//                break;
//            case 2:
//                gifName = "kof3.gif";
//                break;
//            case 3:
//                gifName = "kof4.gif";
//                break;
//        }
//        String gifFilePath = "file:///android_asset/" + gifName;
//        String data = "<HTML><Div align=\"center\" margin=\"0px\"><IMG src=\"" + gifFilePath + "\" margin=\"0px\"/></Div>";
//        gf.loadDataWithBaseURL(gifFilePath, data, "text/html", "utf-8", null);
        return view;
    }
}