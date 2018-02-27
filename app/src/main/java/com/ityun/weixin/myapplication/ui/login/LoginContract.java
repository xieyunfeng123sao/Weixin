package com.ityun.weixin.myapplication.ui.login;

import com.ityun.weixin.myapplication.base.BasePresenter;
import com.ityun.weixin.myapplication.base.BaseView;
import com.ityun.weixin.myapplication.bean.UserInfo;

/**
 * Created by Administrator on 2018/1/26 0026.
 */

public interface LoginContract {

    interface View  extends BaseView<Presenter>
    {
         void loginSucess(UserInfo user);

         void  loginFail();

         void  loginError();
    }

    interface Presenter extends BasePresenter {
        void login(UserInfo usr);
    }
}
