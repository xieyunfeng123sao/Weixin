package com.ityun.weixin.myapplication.ui.adduser;

import android.support.annotation.NonNull;

import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.model.UserModel;
import com.ityun.weixin.myapplication.listener.BmobTableListener;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2018/1/19 0019.
 */

public class AddUserPresenter implements AddUserContract.Presenter {

    private final AddUserContract.View view;


    public AddUserPresenter(@NonNull AddUserContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

    }


    @Override
    public void addUser(User user) {
        UserModel.getInstance().addUser(user, new BmobTableListener() {
            @Override
            public void onSucess(Object object) {
                view.addSucess(object);
            }

            @Override
            public void onFail(BmobException e) {
                view.addError(e);
            }
        });
    }

    @Override
    public void addImage(String path) {
        UserModel.getInstance().addFile(path, new BmobTableListener() {
            @Override
            public void onSucess(Object object) {
                view.uploadSucess(object.toString());
            }

            @Override
            public void onFail(BmobException e) {
                view.uploadFail(e);
            }
        });
    }
}
