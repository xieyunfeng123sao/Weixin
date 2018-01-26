package com.ityun.weixin.myapplication.ui.adduser;

import com.ityun.weixin.myapplication.base.BasePresenter;
import com.ityun.weixin.myapplication.base.BaseView;
import com.ityun.weixin.myapplication.bean.User;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2018/1/19 0019.
 */

public interface AddUserContract {
    interface View extends BaseView<Presenter> {

        void selectSucess(Object object);

        void selectFail();

        void uploadSucess(String url);

        void uploadFail(BmobException e);

        void addSucess(Object object);

        void addError(BmobException e);
    }

    interface Presenter extends BasePresenter {
        void addUser(User user);
        void selectUser(String num);

        void addImage(String path);
    }

}
