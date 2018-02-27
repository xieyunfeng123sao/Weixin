package com.ityun.weixin.myapplication.ui.adduser;

import android.support.annotation.NonNull;

import com.ityun.weixin.myapplication.bean.UserInfo;
import com.ityun.weixin.myapplication.listener.BmobTableListener;
import com.ityun.weixin.myapplication.table.UserHelper;
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
    public void addUser(UserInfo user) {
        UserHelper.getInstance().addUser(user, new BmobTableListener() {
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
    public void selectUser(String num) {
        UserHelper.getInstance().queryLoginName(num, new BmobTableListener() {
            @Override
            public void onSucess(Object object) {
                view.selectSucess(object);
            }
            @Override
            public void onFail(BmobException e) {
                view.selectFail();
            }
        });
    }

    @Override
    public void addImage(String path) {
        UserHelper.getInstance().addFile(path, new BmobTableListener() {
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
