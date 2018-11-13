package com.ityun.weixin.myapplication.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ityun.weixin.myapplication.base.App;
import com.ityun.weixin.myapplication.bean.Friend;
import com.ityun.weixin.myapplication.bean.User;

import java.util.List;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class SpUtil {

    private static String db_user = "User";


    public static void saveUser(User user) {
        SharedPreferences sp = App.context.getSharedPreferences(db_user, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        //通过editor对象写入数据
        Gson gson = new Gson();
        String strUser = gson.toJson(user, User.class);
        edit.putString("user", strUser);
        //提交数据存入到xml文件中
        edit.commit();
    }


    public static User getUser() {
        SharedPreferences sp = App.context.getSharedPreferences(db_user, Context.MODE_PRIVATE);
        String strUser = sp.getString("user", "");
        if (TextUtils.isEmpty(strUser)) {
            return null;
        }
        //通过editor对象写入数据
        Gson gson = new Gson();
        User user = gson.fromJson(strUser, User.class);
        return user;
    }


    public static void saveFriends(List<Friend> mlist) {
        SharedPreferences sp = App.context.getSharedPreferences(db_user, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        //通过editor对象写入数据
        Gson gson = new Gson();
        String strUser = gson.toJson(mlist);
        edit.putString("friends", strUser);
        //提交数据存入到xml文件中
        edit.commit();
    }

    public static List<Friend> getFriends() {
        SharedPreferences sp = App.context.getSharedPreferences(db_user, Context.MODE_PRIVATE);
        String strUser = sp.getString("friends", "");
        if (TextUtils.isEmpty(strUser)) {
            return null;
        }
        //通过editor对象写入数据
        Gson gson = new Gson();
        List<Friend> friends = gson.fromJson(strUser, new TypeToken<List<Friend>>() {
        }.getType());
        return friends;
    }

}
