package com.xiuman.xinjiankang.utils;/**
 * Created by PCPC on 2015/7/13.
 */

import com.google.gson.Gson;
import com.xiuman.xinjiankang.app.AppConfig;
import com.xiuman.xinjiankang.bean.User;
import com.xiuman.xinjiankang.bean.UserLevel;

/**
 * 描述: Sp 工具类
 * 名称: AppSpUtil
 * User: csx
 * Date: 07-13
 */
public class AppSpUtil {

    static AppSpUtil instance;

    public static AppSpUtil getInstance() {
        if (instance == null) {
            instance = new AppSpUtil();
        }
        return instance;
    }

    /*----------------------------------------用户信息---------------------------------------------*/

    /**
     * param json_usre_info 2014-8-12
     * 描述：将用户信息作为json保存起来,并将用户是否登录设置为true
     */
    public synchronized void saveUserInfo(String json_user_info) {
        SpUtils.setString(AppConfig.KEY_USER_INFO, json_user_info);
    }

   /**
     * @return 2014-8-12
     * 描述：获取用户信息
     */
    public User getUserInfo() {
        String json_user_info = SpUtils.getString(AppConfig.KEY_USER_INFO);
        User user = null;
        if (!json_user_info.equals("")) {
            user = new Gson().fromJson(json_user_info, User.class);
        }
        return user;
    }

    /**
     * 描述：删除用户信息,并设置用户登录为false(用于获取购物车信息) 2014-8-12
     */
    public void deleteUserInfo() {
        SpUtils.setString(AppConfig.KEY_USER_INFO, "");
        SpUtils.setBoolean(AppConfig.KEY_USER_LOGIN, false);
        // 删除用户等级信息
        saveUserLevel("");
    }




    /*----------------------------------------用户等级信息-----------------------------------------*/

    /**
     * @param user_level 描述：保存用户等级信息
     *                   时间 2014-11-18
     */
    public void saveUserLevel(String user_level) {
        SpUtils.setBoolean(AppConfig.KEY_USER_LOGIN, true);
        SpUtils.setString(AppConfig.KEY_USER_LEVEL, user_level);
    }

   /* *//**
     * @return 描述：获取用户等级头衔信息
     * 时间 2014-11-18
     */
    public UserLevel getUserLevel() {
        String json_user_level = SpUtils.getString(AppConfig.KEY_USER_LEVEL);
        UserLevel level = null;
        if (!json_user_level.equals("")) {
            level = new Gson().fromJson(json_user_level, UserLevel.class);
        }
        return level;
    }






/*--------------------------------------------保存应用版本信息--------------------------------------*/

    /**
     * @param version_name
     * @param version_cede 2014-10-11
     *                     描述：保存版本信息
     */
    public void saveVersion(String version_name, int version_cede) {
        SpUtils.setString(AppConfig.KEY_VERSION_NAME, version_name + "");
        SpUtils.setString(AppConfig.KEY_VERSION_CODE, version_cede + "");
    }

    /**
     * @return 2014-10-11
     * 描述：获取保存的版本信息
     */
    public String getLocalVersionName() {
        String version_name = SpUtils.getString(AppConfig.KEY_VERSION_NAME);
        return version_name;
    }
}
