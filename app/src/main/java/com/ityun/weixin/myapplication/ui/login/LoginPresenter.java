package com.ityun.weixin.myapplication.ui.login;

import com.google.gson.Gson;
import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.listener.BmobTableListener;
import com.ityun.weixin.myapplication.table.UserHelper;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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
    public void login(User user) {
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
                        User user = gson.fromJson(obj.toString(), User.class);
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
