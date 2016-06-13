/*
package com.xiuman.xinjiankang.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.base.BaseActivity;
import com.xiuman.xinjiankang.bean.ImageEntity;

import java.util.ArrayList;

*/
/**
 * 选择图片相关类
 *//*

public class AlbumPreGalleryActivity extends BaseActivity implements OnClickListener, OnPageChangeListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xjk_activity_albums);
    }

    @Override
    protected void initView() {
        obtainData();
    }

    @Override
    protected int getView() {
        return R.layout.xjk_activity_albums;
    }

    MyViewPager viewPager;
    ArrayList<ImageEntity> list;
    TextView btnBack, selectedCountLabel;
    ImageView btnSelect;
    int currentPosition;
    int maxImageCount;
    String hintFromat;
    Button btnSend;
    View selectedCountBg, headerBar, footerBar;
    int selectedCount;
    boolean isSelectedFull;
    boolean isShow = true;
    Resources resources;
    boolean IsFromHoutai;//用来标记是不是从后台管理的（上传图片  按钮）跳转过来的


    void initViews() {
        resources = getResources();

        headerBar = findViewById(R.id.photo_preview_title);
        footerBar = findViewById(R.id.album_footer_bar);
        viewPager = (MyViewPager) findViewById(R.id.viewpager);
        btnBack = (TextView) findViewById(R.id.albums_gallery_back);
        btnSelect = (ImageView) findViewById(R.id.albums_gallery_isselect);
        btnBack.setOnClickListener(this);
        btnSelect.setOnClickListener(this);

        btnSend = (Button) findViewById(R.id.media_send);
        selectedCountBg = findViewById(R.id.media_selected_count_bg);
        selectedCountLabel = (TextView) findViewById(R.id.media_selected_count);

        hintFromat = resources.getString(R.string.select_photo_at_most);

        btnSend.setOnClickListener(this);
    }

    void obtainData() {
        Object[] datas = (Object[]) getIntent().getSerializableExtra("datas");
        currentPosition = getIntent().getIntExtra("position", -1);
        maxImageCount = getIntent().getIntExtra("maxImageCount", 9);
        IsFromHoutai = getIntent().getBooleanExtra("IsFromHoutai", false);
        list = new ArrayList<ImageEntity>();
        for (Object obj : datas) {
            ImageEntity entity = (ImageEntity) obj;
            list.add(entity);
            if (entity.isSelected()) {
                selectedCount++;
            }
        }
        if (selectedCount == maxImageCount) {
            isSelectedFull = true;
        }
        photosSelected(selectedCount);

        AlbumPagerAdapter adapter = new AlbumPagerAdapter(this, list, new BarSwitch() {
            @Override
            public void switchOperateBlock() {
                if (isShow) {
                    headerBar.setVisibility(View.GONE);
                    footerBar.setVisibility(View.GONE);
                    isShow = false;
                } else {
                    headerBar.setVisibility(View.VISIBLE);
                    footerBar.setVisibility(View.VISIBLE);
                    isShow = true;
                }
            }
        });
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
        if (currentPosition != -1) {
            ImageEntity currentEnity = list.get(currentPosition);
            setBtnSelectImage(currentEnity.isSelected());
            viewPager.setCurrentItem(currentPosition);

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.albums_gallery_back:
                onBackPressed();
                break;
            case R.id.albums_gallery_isselect:
                ImageEntity currentEntity = list.get(currentPosition);
                if (currentEntity.isSelected()) {
                    currentEntity.setSelected(false);
                    setBtnSelectImage(currentEntity.isSelected());
                    selectedCount--;
                    photosSelected(selectedCount);
                    if (selectedCount < maxImageCount) {
                        isSelectedFull = false;
                    }
                } else {
                    if (!isSelectedFull) {
                        currentEntity.setSelected(true);
                        selectedCount++;
                        if (selectedCount >= maxImageCount) {
                            isSelectedFull = true;
                        }
                        photosSelected(selectedCount);
                        setBtnSelectImage(currentEntity.isSelected());
                        Animation anim = AnimationUtils.loadAnimation(this, R.anim.xjk_photo_checked_big);
                        btnSelect.startAnimation(anim);
                    } else {
                        photosSelected(10);
                    }
                }

                break;
            case R.id.media_send:
                Intent intent = new Intent();
                intent.putExtra("datas", list);
                intent.putExtra("isSended", true);
                if (!IsFromHoutai) {
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    void setBtnSelectImage(boolean isSelected) {
        if (isSelected) {
            btnSelect.setImageResource(R.drawable.xjk_sends_pictures_select_big_icon_selected_black);
        } else {
            btnSelect.setImageResource(R.drawable.xjk_sends_pictures_select_big_icon_unselected);
        }
    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        ImageEntity currentEnity = list.get(position);
        setBtnSelectImage(currentEnity.isSelected());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("datas", list);
        intent.putExtra("selectCount", selectedCount);
        setResult(Activity.RESULT_OK, intent);
        super.onBackPressed();
    }

    public void photosSelected(int selectedCount) {
        if (selectedCount > maxImageCount) {

        } else if (selectedCount > 0) {
            btnSend.setEnabled(true);
            selectedCountBg.setVisibility(View.VISIBLE);
            selectedCountLabel.setVisibility(View.VISIBLE);
            selectedCountLabel.setText(selectedCount + "/" + maxImageCount);
        } else if (selectedCount == 0) {
            btnSend.setEnabled(false);
            selectedCountBg.setVisibility(View.GONE);
            selectedCountLabel.setVisibility(View.GONE);
        }

    }

}
*/
