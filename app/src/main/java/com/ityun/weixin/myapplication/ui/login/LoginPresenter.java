package com.ityun.weixin.myapplication.ui.login;

import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.model.UserModel;
import com.ityun.weixin.myapplication.listener.BmobTableListener;

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
    public void login(final User user) {
        UserModel.getInstance().login(user, new BmobTableListener() {
            @Override
            public void onSucess(Object object) {
                view.loginSucess((User) object);
            }

            @Override
            public void onFail(BmobException e) {
                view.loginError(e);
            }
        });
    }
}
