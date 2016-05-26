package com.xiuman.xinjiankang.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.base.BaseActivity;
import com.xiuman.xinjiankang.net.HttpTaskListener;

import butterknife.Bind;

/**
 * Created by PCPC on 2016/5/26.
 */
public class ScientifitDetailActivity extends BaseActivity {

    @Bind(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    //文章id
    private String categoryId;


    @Override
    protected void initView() {
        setupToolbar();
//        setToolbarTitle("知识详情");
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        swipeRefreshLayout.setRefreshing(true);
        loadData();
    }

    @Override
    protected int getView() {
        return R.layout.activity_scientific_detail;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            //点击拨打客服热线
            case R.id.share:
                AppManager.showToast(this, "分享");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void loadData() {
        AppManager.getUserRequest().getScienceTechologyDetail(this, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void dataError(String result) {

            }
        }, categoryId);
        AppManager.getUserRequest().getScienceTechologyShareUrl(this, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {

            }

            @Override
            public void dataError(String result) {

            }
        }, categoryId);
    }


}
