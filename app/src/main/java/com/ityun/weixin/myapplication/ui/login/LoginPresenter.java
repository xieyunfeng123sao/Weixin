package com.ityun.weixin.myapplication.ui.login;

import android.util.Log;

import com.google.gson.Gson;
import com.ityun.weixin.myapplication.bean.UserInfo;
import com.ityun.weixin.myapplication.listener.BmobTableListener;
import com.ityun.weixin.myapplication.table.UserHelper;

import org.json.JSONArray;
import org.json.JSONException;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2018/1/26 0026.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void login(UserInfo user) {
        UserHelper.getInstance().queryLoginUser(user, new BmobTableListener() {
            @Override
            public void onSucess(Object object) {
                JSONArray array = (JSONArray) object;
                if (array.length() == 0) {
                    view.loginFail();
                } else {
                    try {
                        Object obj = array.get(0);
                        Gson gson = new Gson();
                        UserInfo user = gson.fromJson(obj.toString(), UserInfo.class);
                        view.loginSucess(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFail(BmobException e) {
                view.loginError();
            }
        });
    }
}
