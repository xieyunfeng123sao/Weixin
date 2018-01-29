package com.ityun.weixin.myapplication.util;

import android.content.Context;
import com.ityun.weixin.myapplication.bean.User;

/**
 * Created by Administrator on 2018/1/26 0026.
 */

public class CacheUtils {

    private Context context;
    public static CacheUtils instance;

    private ACache aCache;

    private String CACHE_USER = "user";

    public  User user;

    public CacheUtils(Context context) {
        this.context = context;
        aCache = ACache.get(context);
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
    public void saveUser(User user) {
        this.user=user;
        aCache.put(CACHE_USER, user);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public User getUser() {
        return user;
    }

    public User getCaCheUser()
    {
        User use= (User) aCache.getAsObject(CACHE_USER);
        return use;
    }
}
