package com.ityun.weixin.myapplication.ui.login;

import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.listener.BmobTableListener;
import com.ityun.weixin.myapplication.table.UserHelper;

import org.json.JSONArray;
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
                List<User> list = (List<User>) object;
                if (list.size() == 0) {
                    view.loginFail();
                } else {
                    view.loginSucess(list.get(0));
                }
            }
            @Override
            public void onFail(BmobException e) {
                view.loginError();
            }
        });
    }
}
