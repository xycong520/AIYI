package com.xiuman.xinjiankang.activity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.base.BaseActivity;
import com.xiuman.xinjiankang.fragment.FragmentConsult;
import com.xiuman.xinjiankang.fragment.FragmentNews;

/**
 * 承载Fragment的activity，用于显示资讯和诊断
 */
public class FragmentInActivity extends BaseActivity {

    public static final String SHOWTYPE = "type";
    public static final int showFragmentNews = 1;
    public static final int showFragmentConsult = 2;
    boolean isConsult;

    @Override
    protected void initView() {
        int type = getIntent().getIntExtra(SHOWTYPE, showFragmentConsult);
        setupToolbar();
        if (type == showFragmentConsult) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentLayout, new FragmentConsult()).commit();
            setToolbarTitle("自我诊断");
            isConsult = true;
        } else if (type == showFragmentNews) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentLayout, new FragmentNews()).commit();
            setToolbarTitle("科普知识");
            isConsult = false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search) {
            Intent intent = new Intent(mActivity, SearchActivity.class);
            intent.putExtra(SearchActivity.parameIsConsul, isConsult);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }


    @Override
    protected int getView() {
        return R.layout.activity_fragment;
    }
}
