package com.xiuman.xinjiankang.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.base.BaseActivity;
import com.xiuman.xinjiankang.bean.DiseaseDetail;
import com.xiuman.xinjiankang.net.HttpTaskListener;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 描述：病情详解
 * Created by hxy on 2015/9/11.
 */
public class PatientDetailActivity extends BaseActivity {
    public static final String parameId = "categoryId";

    @Bind(R.id.explain)
    TextView explain;
    @Bind(R.id.explain_detail)
    TextView explain_detail;
    @Bind(R.id.show)
    TextView show;
    @Bind(R.id.show_detail)
    TextView show_detail;
    @Bind(R.id.pathogeny)
    TextView pathogeny;
    @Bind(R.id.pathogeny_detail)
    TextView pathogeny_detail;
    @Bind(R.id.diagnose)
    TextView diagnose;
    @Bind(R.id.diagnose_detail)
    TextView diagnose_detail;
    @Bind(R.id.prevent)
    TextView prevent;
    @Bind(R.id.prevent_detail)
    TextView prevent_detail;
    private Activity mActivity;
    private Drawable drawable;
    private Drawable drawable2;

    @Override
    protected void initView() {
        mActivity = this;
        setupToolbar();
        String categoryId = getIntent().getStringExtra(parameId);
        AppManager.getUserRequest().getPatientDetail(mActivity, listener, categoryId);
        drawable = getResources().getDrawable(R.mipmap.xjk_icon_down_jiantou);
        drawable2 = getResources().getDrawable(R.mipmap.xjk_icon_up_jiantou);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight()); //设置边界
    }

    HttpTaskListener listener = new HttpTaskListener() {
        @Override
        public void dataSucceed(String result) {
            try {
                DiseaseDetail wraper = new Gson().fromJson(result, DiseaseDetail.class);
                if (wraper != null) {
                    DiseaseDetail.DiseaseDetailEnpty data = wraper.getDatasource();
                    if (data.getAlias() != null) {
                        explain_detail.setText(data.getAlias() + "\r\n" + data.getInstructions());
                    } else {
                        explain_detail.setText(data.getInstructions());
                    }
                    show_detail.setText(data.getManifestations());
                    pathogeny_detail.setText(data.getCause());
                    diagnose_detail.setText(data.getCheckDiagnosis());
                    prevent_detail.setText(data.getPrevention());
                }
            } catch (JsonSyntaxException e) {
                AppManager.showToast(mActivity, getResources().getString(R.string.net_error));
            }
        }

        @Override
        public void dataError(String result) {
            AppManager.showToast(mActivity, getResources().getString(R.string.load_error));
        }
    };


    @Override
    protected void initData() {
        explain.setBackgroundColor(getResources().getColor(R.color.xjk_title_text_color));
    }

    @Override
    protected int getView() {
        return R.layout.activity_patient_details;
    }

    private void hintText() {
        explain_detail.setVisibility(View.GONE);
        show_detail.setVisibility(View.GONE);
        pathogeny_detail.setVisibility(View.GONE);
        diagnose_detail.setVisibility(View.GONE);
        prevent_detail.setVisibility(View.GONE);
    }

    private void setPre() {
        explain.setBackgroundColor(getResources().getColor(R.color.color_white));
        show.setBackgroundColor(getResources().getColor(R.color.color_white));
        pathogeny.setBackgroundColor(getResources().getColor(R.color.color_white));
        diagnose.setBackgroundColor(getResources().getColor(R.color.color_white));
        prevent.setBackgroundColor(getResources().getColor(R.color.color_white));
        explain.setTextColor(getResources().getColor(R.color.color_black));
        show.setTextColor(getResources().getColor(R.color.color_black));
        pathogeny.setTextColor(getResources().getColor(R.color.color_black));
        diagnose.setTextColor(getResources().getColor(R.color.color_black));
        prevent.setTextColor(getResources().getColor(R.color.color_black));
        explain.setCompoundDrawables(null, null, drawable2, null);
        show.setCompoundDrawables(null, null, drawable2, null);
        pathogeny.setCompoundDrawables(null, null, drawable2, null);
        diagnose.setCompoundDrawables(null, null, drawable2, null);
        prevent.setCompoundDrawables(null, null, drawable2, null);
    }

    @OnClick({R.id.explain, R.id.show, R.id.pathogeny, R.id.diagnose, R.id.prevent})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.explain:
                if (explain_detail.getVisibility() == View.GONE) {
                    setPre();
                    explain.setBackgroundColor(getResources().getColor(R.color.xjk_title_text_color));
                    explain.setTextColor(getResources().getColor(R.color.color_white));
                    hintText();
                    explain_detail.setVisibility(View.VISIBLE);
                    explain.setCompoundDrawables(null, null, drawable, null);
                } else {
                    setPre();
                    explain_detail.setVisibility(View.GONE);
                    explain.setCompoundDrawables(null, null, drawable2, null);
                }
                break;
            case R.id.show:
                if (show_detail.getVisibility() == View.GONE) {
                    setPre();
                    show.setBackgroundColor(getResources().getColor(R.color.xjk_title_text_color));
                    hintText();
                    show.setTextColor(getResources().getColor(R.color.color_white));
                    show_detail.setVisibility(View.VISIBLE);
                    show.setCompoundDrawables(null, null, drawable, null);
                } else {
                    setPre();
                    show_detail.setVisibility(View.GONE);
                    show.setCompoundDrawables(null, null, drawable2, null);
                }
                break;
            case R.id.pathogeny:
                if (pathogeny_detail.getVisibility() == View.GONE) {
                    setPre();
                    pathogeny.setBackgroundColor(getResources().getColor(R.color.xjk_title_text_color));
                    hintText();
                    pathogeny.setTextColor(getResources().getColor(R.color.color_white));
                    pathogeny_detail.setVisibility(View.VISIBLE);
                    pathogeny.setCompoundDrawables(null, null, drawable, null);
                } else {
                    setPre();
                    pathogeny_detail.setVisibility(View.GONE);
                    pathogeny.setCompoundDrawables(null, null, drawable2, null);
                }
                break;
            case R.id.diagnose:
                if (diagnose_detail.getVisibility() == View.GONE) {
                    setPre();
                    diagnose.setBackgroundColor(getResources().getColor(R.color.xjk_title_text_color));
                    hintText();
                    diagnose.setTextColor(getResources().getColor(R.color.color_white));
                    diagnose_detail.setVisibility(View.VISIBLE);
                    diagnose.setCompoundDrawables(null, null, drawable, null);
                } else {
                    setPre();
                    diagnose_detail.setVisibility(View.GONE);
                    diagnose.setCompoundDrawables(null, null, drawable2, null);
                }
                break;
            case R.id.prevent:
                if (prevent_detail.getVisibility() == View.GONE) {
                    setPre();
                    prevent.setBackgroundColor(getResources().getColor(R.color.xjk_title_text_color));
                    hintText();
                    prevent.setTextColor(getResources().getColor(R.color.color_white));
                    prevent_detail.setVisibility(View.VISIBLE);
                    prevent.setCompoundDrawables(null, null, drawable, null);
                } else {
                    setPre();
                    prevent_detail.setVisibility(View.GONE);
                    prevent.setCompoundDrawables(null, null, drawable2, null);
                }
                break;
        }
    }

}
