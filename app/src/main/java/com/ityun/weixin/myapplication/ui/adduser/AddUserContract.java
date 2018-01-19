package com.ityun.weixin.myapplication.ui.adduser;

import com.ityun.weixin.myapplication.base.BasePresenter;
import com.ityun.weixin.myapplication.base.BaseView;
import com.ityun.weixin.myapplication.bean.User;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2018/1/19 0019.
 */

public interface AddUserContract {
    interface View extends BaseView<Presenter>
    {
        void  addSucess();

        void  addFail();

        void  addError(BmobException e);

        void  toastHasAdd();
    }

    interface Presenter extends BasePresenter {
        void  addUser(User user);
    }

}
