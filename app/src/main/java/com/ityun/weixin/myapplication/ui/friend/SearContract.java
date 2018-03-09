package com.ityun.weixin.myapplication.ui.friend;

import com.ityun.weixin.myapplication.base.BasePresenter;
import com.ityun.weixin.myapplication.base.BaseView;
import com.ityun.weixin.myapplication.bean.UserInfo;
import com.ityun.weixin.myapplication.ui.login.LoginContract;

/**
 * Created by Administrator on 2018/3/9 0009.
 */

public interface SearContract {

    interface View  extends BaseView<SearContract.Presenter>
    {
        void searchSucess(UserInfo user);

        void  searchFail(int errorId);

        void  searchError();
    }

    interface Presenter extends BasePresenter {
        void search(String num);
    }
}
