package com.ityun.weixin.myapplication.table;

import android.content.Context;

import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.conn.Dformat;
import com.ityun.weixin.myapplication.conn.TableName;
import com.ityun.weixin.myapplication.listener.BmobTableListener;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class UserHelper {

    private static UserHelper userHelper;

    public static UserHelper getInstance() {
        if (userHelper == null) {
            userHelper = new UserHelper();
        }
        return userHelper;
    }


    /**
     * 添加用户
     *
     * @param user
     */
    public void addUser(final User user, final BmobTableListener listener) {
        user.setCreateTime(new BmobDate(new Date()));
        user.setUpdataTime(new BmobDate(new Date()));
        user.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    listener.onSucess(s);
                } else {
                    listener.onFail(e);
                }
            }
        });

    }

    /**
     * 通过用户名查询
     * @param loginName
     * @param listener
     */
    public void queryLoginName(String loginName, final BmobTableListener listener) {
        BmobQuery query = new BmobQuery(TableName.userTable);
        query.addWhereEqualTo("loginName", loginName);
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if (e == null) {
                    listener.onSucess(jsonArray);
                } else {
                    listener.onFail(e);
                }
            }
        });
    }

    /**
     * 登录
     * @param user
     * @param listener
     */
    public void queryLoginUser(User user,final BmobTableListener listener)
    {
        BmobQuery query = new BmobQuery(TableName.userTable);
        query.addWhereEqualTo("loginName", user.getLoginName());
        query.addWhereEqualTo("password",user.getPassword());
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if (e == null) {
                    listener.onSucess(jsonArray);
                } else {
                    listener.onFail(e);
                }
            }
        });
    }




    /**
     * 上传图片
     * @param path
     * @param listener
     */
    public void addFile(String path, final BmobTableListener listener) {
        final BmobFile file = new BmobFile(new File(path));
        file.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    listener.onSucess(file.getFileUrl());
                } else {
                    listener.onFail(e);
                }
            }
        });
    }


}
