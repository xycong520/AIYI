package com.xiuman.xinjiankang.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.adapter.CommonAdapter;
import com.xiuman.xinjiankang.adapter.ViewHolder;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.base.BaseActivity;
import com.xiuman.xinjiankang.bean.AlbumEntity;
import com.xiuman.xinjiankang.bean.ImageEntity;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

public class LocalAlbumPreActivity extends BaseActivity implements OnClickListener, OnItemClickListener {
    private GridView mGridView;
    private CommonAdapter mAdapter;
    private TextView selectedCountLabel;
    //预览 和确认
    private TextView btnSend;
    private View selectedCountBg;
    private ArrayList<ImageEntity> albumImages;
    private int maxImagesCount;
    private String hintFromat;

    private boolean IsFromHoutai;                           // 标记是不是从后台图片管理的上传按钮过来的
    private boolean isSetPhoto;                           // 标记是不是选择头像
    int selectCount = 0;
    ImageOptions options;

    @Override
    protected void initView() {
        setupToolbar();
        btnSend = (TextView) findViewById(R.id.media_send);
        selectedCountBg = findViewById(R.id.media_selected_count_bg);
        selectedCountLabel = (TextView) findViewById(R.id.media_selected_count);

        mGridView = (GridView) findViewById(R.id.media_in_folder_gv);
        hintFromat = getResources().getString(R.string.select_photo_at_most);
        btnSend.setOnClickListener(this);
        options = new ImageOptions.Builder().setConfig(Bitmap.Config.RGB_565).setUseMemCache(true).setImageScaleType(ImageView.ScaleType.CENTER_CROP).setFailureDrawableId(R.drawable.onloading).setLoadingDrawableId(R.drawable.onloading).build();
        obtainData();
    }

    @Override
    protected int getView() {
        return R.layout.activity_local_album_pre;
    }


    private void obtainData() {
        Intent intent = getIntent();
        final String albumName = intent.getStringExtra("name");
        IsFromHoutai = intent.getBooleanExtra("IsFromHoutai", false);
        isSetPhoto = intent.getBooleanExtra("isSetPhoto", false);
        maxImagesCount = intent.getIntExtra("maxImageCount", 9);

        AlbumEntity entity = LocalAlbumActivity.mData.get(albumName);
        setToolbarTitle(albumName);
        albumImages = entity.getImages();

        mAdapter = new CommonAdapter<ImageEntity>(this, albumImages, R.layout.item_grid_album_pre) {
            @Override
            public void convert(ViewHolder helper, ImageEntity item) {
                CheckBox mCheckBox = helper.getView(R.id.media_cbx);
                mCheckBox.setTag(helper.getPosition());
                ImageView iv = helper.getView(R.id.media_thumb);
                x.image().bind(iv,item.getPath(), options);
                if (item.isSelected()) {
                    mCheckBox.setChecked(true);
                } else {
                    mCheckBox.setChecked(false);
                }
                mCheckBox.setOnClickListener(checkBoxClick);
            }
            OnClickListener checkBoxClick = new OnClickListener() {
                @Override
                 public void onClick(View v) {
                    int index = (int) v.getTag();
                    if (selectCount>=maxImagesCount){
                        AppManager.showToast(mActivity,String.format("最多选取%d张",maxImagesCount));
                        notifyDataSetChanged();
                        return;
                    }
                    albumImages.get(index).setSelected(!albumImages.get(index).isSelected());
                    if (albumImages.get(index).isSelected()){
                        Animation anim= AnimationUtils.loadAnimation(mActivity,R.anim.xjk_photo_checked);
                        v.startAnimation(anim);
                        selectCount++;
                    }else {
                        selectCount--;
                    }
                    notifyDataSetChanged();
                    updataSelectCount();
                }
            };
        };


        mGridView.setAdapter(mAdapter/*new MyAdapter()*/);
        mGridView.setOnItemClickListener(this);
    }
    private void updataSelectCount() {
        if (selectCount>0){
            selectedCountBg.setVisibility(View.VISIBLE);
            selectedCountLabel.setVisibility(View.VISIBLE);
        }else{
            selectedCountBg.setVisibility(View.GONE);
            selectedCountLabel.setVisibility(View.GONE);
        }
        selectedCountLabel.setText(selectCount+"/"+maxImagesCount);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.media_send:
                ArrayList<String> list = new ArrayList<String>();
                for (ImageEntity obj : albumImages) {
                    ImageEntity entity =  obj;
                    if (entity.isSelected()) {
                        list.add(entity.getPath());
                    }
                }
                Intent intent = getIntent();
                intent.putExtra("imagesPath", list);
                if (!IsFromHoutai) {// 不是从后台跳转
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                break;
           /* case R.id.media_preview:
               *//* ArrayList<ImageEntity> prelist = new ArrayList<ImageEntity>();
                Object[] allItems = mAdapter.getAllItems();

                for (int i = 0; i < allItems.length; i++) {
                    ImageEntity entity = (ImageEntity) allItems[i];
                    if (entity.isSelected()) {
                        entity.setPosition(i);
                        prelist.add(entity);
                    }
                }
                intent = new Intent(this, AlbumPreGalleryActivity.class);
                intent.putExtra("datas", prelist.toArray());
                intent.putExtra("position", 0);
                intent.putExtra("maxImageCount", maxImagesCount);
                startActivityForResult(intent, RequestCoder.ALBUM_SELECT_PHOTOS_PRE);*//*

                break;*/
            default:
                break;
        }
    }

    public void photosSelected(int selectedCount) {
        /*if (selectedCount > maxImagesCount) {
            Use.showToast(LocalAlbumPreActivity.this, String.format(hintFromat, maxImagesCount));

        } else if (selectedCount > 0) {
            btnPreview.setEnabled(true);
            btnSend.setEnabled(true);
            selectedCountBg.setVisibility(View.VISIBLE);
            selectedCountLabel.setVisibility(View.VISIBLE);
            selectedCountLabel.setText(selectedCount + "/" + maxImagesCount);
        } else if (selectedCount == 0) {
            btnPreview.setEnabled(false);
            btnSend.setEnabled(false);
            selectedCountBg.setVisibility(View.GONE);
            selectedCountLabel.setVisibility(View.GONE);
        }*/

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent = new Intent(this, AlbumPreGalleryActivity.class);
//        intent.putExtra("datas", mAdapter.getAllItems());
//        intent.putExtra("position", position);
//        intent.putExtra("maxImageCount", maxImagesCount);
//        intent.putExtra("IsFromHoutai", IsFromHoutai);
//        startActivityForResult(intent, Constant.ALBUM_SELECT_PHOTOS_SCAN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*switch (requestCode) {
            case Constant.ALBUM_SELECT_PHOTOS_PRE:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        if (!data.getBooleanExtra("isSended", false)) {
                            ArrayList<ImageEntity> tempAlbumImages = (ArrayList<ImageEntity>) data.getSerializableExtra("datas");
                            int selectCount = data.getIntExtra("selectCount", 0);
                            for (ImageEntity entity : tempAlbumImages) {
                                albumImages.set(entity.getPosition(), entity);
                            }
                            mAdapter.setItems(albumImages);
                            mAdapter.setSelectedCount(selectCount);

                        } else {
                            albumImages = (ArrayList<ImageEntity>) data.getSerializableExtra("datas");
                            Intent intent = new Intent();
                            ArrayList<String> list = new ArrayList<String>();
                            for (ImageEntity entity : albumImages) {
                                if (entity.isSelected()) {
                                    list.add(entity.getPath());
                                }
                            }
                            intent.putExtra("imagesPath", list);
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }
                    }
                }
                break;
            case Constant.ALBUM_SELECT_PHOTOS_SCAN:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        if (!data.getBooleanExtra("isSended", false)) {
                            albumImages = (ArrayList<ImageEntity>) data.getSerializableExtra("datas");
                            int selectCount = data.getIntExtra("selectCount", 0);
                            mAdapter.setItems(albumImages);
                            mAdapter.setSelectedCount(selectCount);
                        } else {
                            albumImages = (ArrayList<ImageEntity>) data.getSerializableExtra("datas");
                            Intent intent = new Intent();
                            ArrayList<String> list = new ArrayList<String>();
                            for (ImageEntity entity : albumImages) {
                                if (entity.isSelected()) {
                                    list.add(entity.getPath());
                                }
                            }
                            intent.putExtra("imagesPath", list);
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }
                    }
                }
                break;
            default:
                break;
        }*/
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return albumImages.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (convertView == null){
                holder = new Holder();
                convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_grid_album_pre,null);
                holder.ivPic = (ImageView) convertView.findViewById(R.id.media_thumb);
                convertView.setTag(holder);
            }else{
                holder = (Holder) convertView.getTag();
            }
            x.image().bind(holder.ivPic,albumImages.get(position).getPath(),options);
            return convertView;
        }
        class Holder {
            ImageView ivPic;
        }
    }

}
