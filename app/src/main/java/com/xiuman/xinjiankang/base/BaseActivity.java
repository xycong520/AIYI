package com.xiuman.xinjiankang.base;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.xiuman.xingjiankang.R;


/**
 * Created by PCPC on 2016/5/24.
 */
public class BaseActivity extends AppCompatActivity {
    Toolbar toolbar;
    AppBarLayout appBarLayout;

    //设置Toolbar
    protected void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);

        if (toolbar != null) {
            //设置toolbar作为actionbar
            setSupportActionBar(toolbar);

            // Set up the action bar.
            final ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
            }
        }

    }

    //设置工具栏标题
    protected void setToolbarTitle(String title) {
        // Set up the action bar.
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

    }


}
