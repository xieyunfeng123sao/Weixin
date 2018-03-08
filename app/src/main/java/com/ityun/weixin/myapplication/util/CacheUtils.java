package com.ityun.weixin.myapplication.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.ityun.weixin.myapplication.bean.UserInfo;

/**
 * Created by Administrator on 2018/1/26 0026.
 */

public class CacheUtils {

    private Context context;
    public static CacheUtils instance;

    private String CACHE_USER = "user";

    public UserInfo user;

    private SharedPreferences sharedPreferences;

    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "weixin";


    private SharedPreferences.Editor editor;


    public CacheUtils(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static CacheUtils getInstance(Context context) {
        if (instance == null) {
            instance = new CacheUtils(context);
        }
        return instance;
    }

    /**
     * 保存用户信息
     *
     * @param user
     */
    public void saveUser(UserInfo user) {
        Gson gson = new Gson();
        String userInfo = gson.toJson(user);
        editor.putString("userinfo", userInfo);
//        editor.commit();
        editor.apply();
        this.user = user;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public UserInfo getUser() {
        return user;
    }

    public UserInfo getCaCheUser() {
        Gson gson = new Gson();
        String userInfo = sharedPreferences.getString("userinfo", null);
        if (userInfo != null) {
            UserInfo user = gson.fromJson(userInfo, UserInfo.class);
            this.user = user;
            return user;
        }
        return null;
    }
}
