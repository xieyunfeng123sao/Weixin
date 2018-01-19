package com.ityun.weixin.myapplication.ui.adduser;

import android.support.annotation.NonNull;

import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.listener.BmobTableListener;
import com.ityun.weixin.myapplication.table.UserHelper;

import org.json.JSONArray;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2018/1/19 0019.
 */

public class AddUserPresenter implements AddUserContract.Presenter {

    private final AddUserContract.View view;


    public AddUserPresenter(@NonNull AddUserContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void addUser(User user) {
        UserHelper.getInstance().queryLoginName(user.getLoginName(), new BmobTableListener() {
            @Override
            public void onSucess(Object object) {
                JSONArray jsonArray = (JSONArray) object;
                if (jsonArray.length() == 0) {
                    //这个账号没有注册过
                    view.addSucess();
                } else {
                    //这个账号已经存在了
                }
            }

            @Override
            public void onFail(BmobException e) {
                view.addError(e);
            }
        });

    }
}
