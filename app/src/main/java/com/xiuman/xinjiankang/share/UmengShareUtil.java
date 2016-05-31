package com.xiuman.xinjiankang.share;/**
 * Created by PCPC on 2015/7/20.
 */

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.xiuman.xinjiankang.app.AppManager;

/**
 * 描述: TODO
 * 名称: UmengShareUtil
 * User: csx
 * Date: 07-20
 */
public class UmengShareUtil {
    public static final String WX = "WX";
    public static final String CIRCLE = "CIRCLE";
    public static final String SINA = "SINA";
    public static final String QZone = "QZone";

    private Activity context;

    public UmengShareUtil(Activity context) {
        this.context = context;
    }


    /**
     * 分享
     */
    public void share(String type, Share share) {
        switch (type) {
            case QZone:
                setContent(share, SHARE_MEDIA.QZONE);
                break;
            case WX:
                setContent(share, SHARE_MEDIA.WEIXIN);
                break;
            case CIRCLE:
                setContent(share, SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case SINA:
                setContent(share, SHARE_MEDIA.SINA);
                break;
        }
    }

    /**
     * 微信分享内容
     */
    private void setContent(Share shareEntry, SHARE_MEDIA plate) {
        UMImage urlImage = null;
        if (!TextUtils.isEmpty(shareEntry.getImgUrl())) {
            urlImage = new UMImage(context, shareEntry.getImgUrl());
        } else if (!TextUtils.isEmpty(shareEntry.getImgPath())) {
            urlImage = new UMImage(context, BitmapFactory.decodeFile(shareEntry.getImgPath()));
        }
        new ShareAction(context)
                .setPlatform(plate)
                .setCallback(shareListener)
                .withText(shareEntry.getContent())
                .withTitle(shareEntry.getTitle())
                .withTargetUrl(shareEntry.getContentUrl())
                .withMedia(urlImage)
                .share();
    }

    /**
     * 分享监听
     */
    UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            AppManager.showToast(context, " 分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            AppManager.showToast(context, " 分享失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            AppManager.showToast(context, " 分享取消");
        }
    };
}
