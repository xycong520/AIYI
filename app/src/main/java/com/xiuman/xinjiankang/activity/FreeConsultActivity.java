package com.xiuman.xinjiankang.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingjiankang.R;
import com.xiuman.xinjiankang.adapter.CommonAdapter;
import com.xiuman.xinjiankang.adapter.ViewHolder;
import com.xiuman.xinjiankang.app.AppManager;
import com.xiuman.xinjiankang.app.MyApplication;
import com.xiuman.xinjiankang.base.BaseActivity;
import com.xiuman.xinjiankang.bean.Dministartive;
import com.xiuman.xinjiankang.bean.FreeConsultNum;
import com.xiuman.xinjiankang.constant.Constant;
import com.xiuman.xinjiankang.net.HttpTaskListener;
import com.xiuman.xinjiankang.net.Wrapper;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by PCPC on 2016/6/12.
 */
public class FreeConsultActivity extends BaseActivity implements OnClickListener {
    @Bind(R.id.rgSex)
    RadioGroup rgSex;
    //年龄
    @Bind(R.id.etAge)
    EditText etAge;
    //内容
    @Bind(R.id.etContent)
    EditText etContent;
    //选择照片
    @Bind(R.id.do_picture)
    ImageView doPicture;
    //是否匿名
    @Bind(R.id.iv_anonymity)
    ImageView ivAnonymity;
    //咨询的人数
    @Bind(R.id.consult_num)
    TextView consultNum;
    //已选择的科室
    @Bind(R.id.tv_administrative)
    TextView tv_administrative;
    @Bind(R.id.ivAddPic)
    ImageView ivAddPic;
    @Bind(R.id.llyt_picture)
    LinearLayout layoutPic;

    //动画
    private Animation animation_in;
    private Animation animation_out;
    //拍照路径
    private Uri fileUri;
    //图片数量
//    private ArrayList<ImageEntity> images = new ArrayList<>();
//    private VipPictureAdapter adapter;
//    private ImageEntity listEnty;
    private int isAnonymity = 1;
    private int Sex = 0;
    private int maxImageCount = 5;
    private String str = "%s用户已咨询";
    private ProgressDialog pd;
    private String dministartiveId = "";

    @Override
    protected void initView() {
        setupToolbar();
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbMan:
                        Sex = 0;
                        break;
                    case R.id.rbWoMan:
                        Sex = 1;
                        break;
                }
            }
        });
        animation_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.xjk_dialog_enter); // 初始化动画效果
        animation_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.xjk_dialog_exit);
        requestData();
        requestDepartment();
    }

    public void requestData() {
        AppManager.getUserRequest().getFreeListCount(mActivity, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                FreeConsultNum message = new Gson().fromJson(result, FreeConsultNum.class);
                if (message != null && message.isSuccess()) {
                    consultNum.setText(String.format(str, message.getDatasource().getCount()));
                }
            }

            @Override
            public void dataError(String result) {
                AppManager.showToast(mActivity, result);
            }
        });
    }

    //请求科室
    private void requestDepartment() {
        AppManager.getUserRequest().getDepartmentList(mActivity, new HttpTaskListener() {
            @Override
            public void dataSucceed(String result) {
                Wrapper<Dministartive> ScienceDetail = new Gson().fromJson(result, new TypeToken<Wrapper<Dministartive>>() {
                }.getType());
                if (ScienceDetail != null) {
                    initPop(ScienceDetail.getDatasource());
                }
            }

            @Override
            public void dataError(String result) {
                AppManager.showToast(mActivity, result);
            }
        });
    }

    @Override
    protected int getView() {
        return R.layout.activity_freeconsult;
    }

    @OnClick({R.id.ivAddPic, R.id.llty_see, R.id.llyt_isaniony, R.id.tv_administrative})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            //添加图片
            case R.id.ivAddPic:
                takePhoto();
                break;
            //选择图片弹窗空白位置
            case R.id.layoutPop:
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                break;
            //取消选择
            case R.id.btn_pop_photo_cancel:
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                break;
            //相机选择照片
            case R.id.btn_pop_photo_camera:
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                break;
            //相册选择照片
            case R.id.btn_pop_photo_album:
                Intent i = new Intent(mActivity, LocalAlbumActivity.class);
                i.putExtra(LocalAlbumActivity.MAX_IMAGE_COUNT, maxImageCount);// 最多3张图片
                startActivityForResult(i, Constant.GET_IMAGE_FROM_ALBUM);
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                break;
            //是否匿名
            case R.id.llyt_isaniony:
                if (isAnonymity == 1) {
                    isAnonymity = 0;
                    ivAnonymity.setImageResource(R.mipmap.xjk_anonymity_p);
                } else {
                    isAnonymity = 1;
                    ivAnonymity.setImageResource(R.mipmap.xjk_anonymity_n);
                }
                break;
            //咨询人数
            case R.id.llty_see:
                break;
            //选择科室
            case R.id.tv_administrative:
//                KeyBoardUtils.closeKeybord(v);
                showPop();
                break;
        }
    }


    PopupWindow popupWindow;

    private void takePhoto() {
        View view = mActivity.getLayoutInflater().inflate(R.layout.layout_pop_takephotot, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        view.findViewById(R.id.btn_pop_photo_camera).setOnClickListener(this);
        view.findViewById(R.id.btn_pop_photo_album).setOnClickListener(this);
        view.findViewById(R.id.layoutPop).setOnClickListener(this);
        view.findViewById(R.id.btn_pop_photo_cancel).setOnClickListener(this);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(findViewById(R.id.layoutFreeConsult), Gravity.BOTTOM, 0, 0);
    }


    private PopupWindow pop;
    private CommonAdapter myAdapter;

    //选择科室
    public void initPop(List<Dministartive> list) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.layout_pop_gridview, null);
        pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        final GridView my_gridview = (GridView) view.findViewById(R.id.my_gridview);
        //noinspection unchecked
        myAdapter = new CommonAdapter(mActivity, list, R.layout.item_consult_classify) {
            @Override
            public void convert(ViewHolder helper, Object item) {
                x.image().bind(((ImageView) helper.getView(R.id.iv_logo)), ((Dministartive) item).getIcon());
                ((TextView) helper.getView(R.id.item)).setText(((Dministartive) item).getName());
            }
        };
        my_gridview.setAdapter(myAdapter);
        my_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object itemAtPosition = my_gridview.getItemAtPosition(position);
                if (itemAtPosition instanceof Dministartive) {
                    Dministartive item = (Dministartive) itemAtPosition;
                    dministartiveId = item.getId();
                    tv_administrative.setText(item.getName());
                    pop.dismiss();
                }
            }
        });
        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
    }

    private void showPop() {
        if (pop != null) {
            pop.showAsDropDown(findViewById(R.id.appbar));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sure, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.GET_IMAGE_FROM_ALBUM:
                if (resultCode == Activity.RESULT_OK) {
                    ArrayList<String> imgs = data.getExtras().getStringArrayList("imagesPath");
                    if (imgs != null) {
                        //第一次选择时移除
                        findViewById(R.id.llyt_tv_message).setVisibility(View.GONE);
                        //加入选择的照片并将最大可选图片数减小
                        for (String url : imgs) {
                            layoutPic.addView(getImageView(url));
                            maxImageCount--;
                        }
                        if (layoutPic.getChildCount() >= 5) {
                            ivAddPic.setVisibility(View.GONE);
                        }else{
                            ivAddPic.setVisibility(View.VISIBLE);
                        }
                    }
                }
                break;
        }
    }

    private View getImageView(String url) {
        View v = LayoutInflater.from(mActivity).inflate(R.layout.item_imageview, null);
        ImageView iv = (ImageView) v.findViewById(R.id.ivImage);
        x.image().bind(iv, url, MyApplication.getOptionsRadius());
        return v;
    }
}