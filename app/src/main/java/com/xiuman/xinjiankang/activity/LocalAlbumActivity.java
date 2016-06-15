package com.xiuman.xinjiankang.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.MediaColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.adapter.CommonAdapter;
import com.xiuman.xinjiankang.adapter.ViewHolder;
import com.xiuman.xinjiankang.base.BaseActivity;
import com.xiuman.xinjiankang.bean.AlbumEntity;
import com.xiuman.xinjiankang.bean.ImageEntity;
import com.xiuman.xinjiankang.constant.Constant;
import com.xiuman.xinjiankang.utils.UIHelper;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;


public class LocalAlbumActivity extends BaseActivity implements OnItemClickListener, OnClickListener {

    public static String MAX_IMAGE_COUNT = "maxImageCount";

    @Bind(R.id.media_gv)
    public GridView mGridView;
    public static HashMap<String, AlbumEntity> mData;
    List<AlbumEntity> mDatas;
    private CommonAdapter adapter;
    private int maxImageCount;

    private boolean IsFromHoutai;      // 标记是不是从后台管理的上传图片过来的
    private Uri fileUri;
    ImageOptions options;

    private static final String[] PROJECTION = {MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.BUCKET_DISPLAY_NAME, // file
            // name
            MediaStore.Images.Media.DATA // abs
            // path
    };


    @Override
    protected void initView() {
        setupToolbar();
        boolean isTakePhoto = getIntent().getBooleanExtra("isTakePhoto", false);
        options = new ImageOptions.Builder().setConfig(Bitmap.Config.RGB_565).setUseMemCache(true).setImageScaleType(ImageView.ScaleType.CENTER_CROP).setFailureDrawableId(R.drawable.onloading).setLoadingDrawableId(R.drawable.onloading).build();
        if (isTakePhoto) {
            //takePicture();
        } else {
            obtainData();
        }
        mGridView = (GridView) findViewById(R.id.media_gv);
        mGridView.setOnItemClickListener(this);
    }

    @Override
    protected int getView() {
        return R.layout.activity_local_album;
    }

    private void takePicture() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent2 = new Intent("android.media.action.IMAGE_CAPTURE");
            fileUri = UIHelper.getOutputMediaFileUri(UIHelper.MEDIA_TYPE_IMAGE);
            intent2.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent2,
                    Constant.GET_IMAGE_BY_TAKE_PHOTO);

        } else {
            Toast.makeText(LocalAlbumActivity.this, "SD卡不存在", Toast.LENGTH_SHORT).show();
        }
    }

    private void obtainData() {
        Intent intent = getIntent();
        IsFromHoutai = intent.getBooleanExtra("IsFromHoutai", false);
        maxImageCount = intent.getIntExtra(MAX_IMAGE_COUNT, 9);
        mData = find();
        if (mData != null) {
            mDatas = new ArrayList<>();
        }
        for (AlbumEntity entity : mData.values()) {
            mDatas.add(entity);
        }
        adapter = new CommonAdapter<AlbumEntity>(this, mDatas, R.layout.item_grid_album) {

            @Override
            public void convert(ViewHolder holder, AlbumEntity item) {
                ImageView iv = holder.getView(R.id.folder_thumb);
                TextView tvCount = holder.getView(R.id.media_files_count);
                TextView tvName = holder.getView(R.id.folder_name);
                x.image().bind(iv, item.getCoverUri(),options);
                tvCount.setText(item.getImages().size() + "");
                tvName.setText(item.getName());
                iv.setTag(item.getName());
            }
        };
        mGridView.setAdapter(adapter/*new MyAdapter()*/);
    }

    private HashMap<String, AlbumEntity> find() {
        HashMap<String, AlbumEntity> temp = new HashMap<String, AlbumEntity>();
        Cursor cursor = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, PROJECTION, null, null, null);
        while (cursor.moveToNext()) {
            String columnPath = cursor.getString(cursor.getColumnIndex(MediaColumns.DATA));
            String columnAlbumName = cursor.getString(cursor.getColumnIndex(ImageColumns.BUCKET_DISPLAY_NAME));
            String columnImageName = cursor.getString(cursor.getColumnIndex(MediaColumns.DISPLAY_NAME));
            columnPath = "file://" + columnPath;
            AlbumEntity entity = null;
            if (!temp.containsKey(columnAlbumName)) {
                entity = new AlbumEntity();
                entity.setName(columnAlbumName);
                entity.setCoverUri(columnPath);
                ArrayList<ImageEntity> list = new ArrayList<ImageEntity>();
                ImageEntity imageEntity = new ImageEntity();
                imageEntity.setName(columnImageName);
                imageEntity.setPath(columnPath);
                list.add(imageEntity);
                entity.setImages(list);
            } else {
                entity = temp.get(columnAlbumName);
                ArrayList<ImageEntity> list = entity.getImages();
                ImageEntity imageEntity = new ImageEntity();
                imageEntity.setName(columnImageName);
                imageEntity.setPath(columnPath);
                list.add(imageEntity);
            }
            temp.put(columnAlbumName, entity);
        }
        cursor.close();
        return temp;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageView imageView = (ImageView) view.findViewById(R.id.folder_thumb);
        String albumName = (String) imageView.getTag();
        Intent intent = new Intent(this, LocalAlbumPreActivity.class);
        intent.putExtra("maxImageCount", maxImageCount);
        intent.putExtra("name", albumName);
        if (!IsFromHoutai)// 不是从后台管理过来的走之前的代码
            startActivityForResult(intent, Constant.ALBUM_SELECT_PHOTOS);
        else {
            intent.putExtra("IsFromHoutai", IsFromHoutai);
            startActivity(intent);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.ALBUM_SELECT_PHOTOS:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        ArrayList<String> imagesPath = (ArrayList<String>) data
                                .getSerializableExtra("imagesPath");
                        if (imagesPath != null && imagesPath.size() != 0) {
                            Intent intent = new Intent();
                            intent.putExtra("imagesPath", imagesPath);
                            setResult(Activity.RESULT_OK, intent);
                            // startActivity(intent);
                            Intent intents = new Intent("ACTION_NAME_GET_IMAGE");
                            intents.putExtra("imagesPath", imagesPath);
                            sendBroadcast(intents);
                            finish();

                        }
                    }
                }
                break;
            case Constant.GET_IMAGE_BY_TAKE_PHOTO:

                if (fileUri != null) {
                    Intent intent2 = new Intent(
                            Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent2.setData(fileUri);
                    sendBroadcast(intent2);
                    ArrayList<String> imagesPath = new ArrayList<String>();
                    imagesPath.add(fileUri.toString());
                    Intent intents = new Intent("ACTION_NAME_GET_IMAGE");
                    intents.putExtra("imagesPath", imagesPath);
                    intents.putExtra("isTakePhoto", true);
                    sendBroadcast(intents);
                    finish();

                } else {
                    Toast.makeText(LocalAlbumActivity.this, "SD卡不存在", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }
    }
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size();
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
                convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_grid_album,null);
                holder.ivPic = (ImageView) convertView.findViewById(R.id.folder_thumb);
                convertView.setTag(holder);
            }else{
                holder = (Holder) convertView.getTag();
            }
            x.image().bind(holder.ivPic,mDatas.get(position).getCoverUri(),options);
            return convertView;
        }
        class Holder {
            ImageView ivPic;
        }
    }
}
