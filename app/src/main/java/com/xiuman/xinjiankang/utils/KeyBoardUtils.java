package com.xiuman.xinjiankang.utils;/**
 * Created by PCPC on 2015/6/3.
 */

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.xiuman.xinjiankang.app.MyApplication;

/**
 * 描述: 键盘管理工具
 * 名称: KeyBoardUtils
 * User: csx
 * Date: 06-03
 */
public class KeyBoardUtils {
    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     */
    public static void openKeybord(EditText mEditText) {
        InputMethodManager imm = (InputMethodManager) MyApplication.getInstance()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText 输入框
     */
    public static void closeKeybord(View mEditText) {
        InputMethodManager imm = (InputMethodManager) MyApplication.getInstance()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getApplicationWindowToken(), 0);
    }
}
