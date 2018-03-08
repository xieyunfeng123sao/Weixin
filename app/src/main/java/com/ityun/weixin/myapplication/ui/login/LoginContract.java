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

         //0 用户名不存在   1密码错误
         void  loginFail(int errorId);

         void  loginError();
    }

    interface Presenter extends BasePresenter {
        void login(UserInfo usr);
    }
}
