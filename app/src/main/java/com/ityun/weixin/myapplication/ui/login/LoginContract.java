package com.ityun.weixin.myapplication.ui.login;

import com.ityun.weixin.myapplication.base.BasePresenter;
import com.ityun.weixin.myapplication.base.BaseView;
import com.ityun.weixin.myapplication.bean.User;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2018/1/26 0026.
 */

public interface LoginContract {

    interface View  extends BaseView<Presenter>
    {
         void loginSucess(User user);

         void  loginError(BmobException e);
    }

    interface Presenter extends BasePresenter {
        void login(User usr);
    }
}
