package com.ityun.weixin.myapplication.ui.login;

import com.ityun.weixin.myapplication.bean.User;

/**
 * Created by Administrator on 2018/1/26 0026.
 */

public class LoginPresenter implements   LoginContract.Presenter {

    private  LoginContract.View view;

    public  LoginPresenter(LoginContract.View view)
    {
        this.view=view;
    }

    @Override
    public void start() {

    }

    @Override
    public void login(User usr) {

    }
}
