package com.ityun.weixin.myapplication.table;

import android.content.Context;

import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.conn.Dformat;
import com.ityun.weixin.myapplication.conn.TableName;
import com.ityun.weixin.myapplication.listener.BmobTableListener;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

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
     *
     * 添加用户
     * @param user
     */
    public void addUser(final User user,final  BmobTableListener listener) {
                user.setCreateTime(new BmobDate(new Date()));
                user.setUpdataTime(new BmobDate(new Date()));
                user.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null){
                            listener.onSucess(s);
                        }else{
                            listener.onFail(e);
                        }
                    }
                });

    }


    public  void  queryLoginName(String  loginName,final BmobTableListener listener)
    {
        BmobQuery query =new BmobQuery(TableName.userTable);
        query.addWhereEqualTo("loginName", loginName);
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if(e==null){
                    listener.onSucess(jsonArray);
                }else{
                    listener.onFail(e);
                }
            }
        });
    }


}
