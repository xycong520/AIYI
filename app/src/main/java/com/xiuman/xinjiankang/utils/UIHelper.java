package com.xiuman.xinjiankang.utils;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.xiuman.xingjiankang.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class UIHelper {
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static void animSwitchActivity(Activity mActivity, AnimDisplayMode mode) {
        switch (mode) {
            case PUSH_LEFT:
                mActivity.overridePendingTransition(R.anim.xjk_push_left_in, R.anim.xjk_push_left_out);
                break;
            case PUSH_RIGHT:
                mActivity.overridePendingTransition(R.anim.xjk_push_right_in, R.anim.xjk_push_right_out);
                break;
            case PUSH_TOP:
                mActivity.overridePendingTransition(R.anim.xjk_push_top_in, R.anim.xjk_push_top_out);
                break;
            case PUSH_LEFT_ENTER:
                mActivity.overridePendingTransition(R.anim.xjk_push_left_in, R.anim.xjk_no_anim);
                break;
            case PUSH_LEFT_EXIT:
                mActivity.overridePendingTransition(R.anim.xjk_no_anim, R.anim.xjk_push_right_out);
                break;
            case PUSH_TOP_ENTER:
                mActivity.overridePendingTransition(R.anim.xjk_push_top_in, R.anim.xjk_no_anim);
                break;
            case PUSH_TOP_EXIT:
                mActivity.overridePendingTransition(R.anim.xjk_no_anim, R.anim.xjk_push_top_out);
                break;
            case PUSH_BOTTOM_ENTER:
                mActivity.overridePendingTransition(R.anim.xjk_dialog_enter, R.anim.xjk_no_anim);
                break;
            case PUSH_BOTTOM_EXIT:
                mActivity.overridePendingTransition(R.anim.xjk_no_anim, R.anim.xjk_dialog_exit);
                break;
            case FADE:
                mActivity.overridePendingTransition(R.anim.xjk_fade_in, R.anim.xjk_fade_out);
                break;
            default:
                break;
        }
    }
    public static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    /** dip转换px */
    public static int dip2px(Context context,float dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }
}
