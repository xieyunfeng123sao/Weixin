package com.ityun.weixin.myapplication.ui.friend;

import com.ityun.weixin.myapplication.bean.Friend;
import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.model.UserModel;
import com.ityun.weixin.myapplication.listener.BmobTableListener;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2018/3/9 0009.
 */

public class SearchPrensenter implements SearContract.Presenter {

    private SearContract.View view;

    private SearContract.SearchFriendView searchFriendView;

    public SearchPrensenter(SearContract.View view) {
        this.view = view;
    }

    public SearchPrensenter(SearContract.SearchFriendView searchFriendView) {
        this.searchFriendView = searchFriendView;
    }

    @Override
    public void start() {

    }

    @Override
    public void searchUser(String num) {
        UserModel.getInstance().queryByNum(num, new BmobTableListener() {
            @Override
            public void onSucess(Object object) {
                view.searchSucess((User) object);
            }

            @Override
            public void onFail(BmobException e) {
                if (e.getErrorCode() == 000) {
                    view.searchFail();
                } else {
                    view.searchError();
                }
            }
        });
    }

    @Override
    public void searchById(String uid) {
        UserModel.getInstance().queryById(uid, new BmobTableListener() {
            @Override
            public void onSucess(Object object) {
                view.searchSucess((User) object);
            }

            @Override
            public void onFail(BmobException e) {
                if (e.getErrorCode() == 000) {
                    view.searchFail();
                } else {
                    view.searchError();
                }
            }
        });
    }

    @Override
    public void searchFriend() {
        UserModel.getInstance().queryFriends(new FindListener<Friend>() {
            @Override
            public void done(List<Friend> list, BmobException e) {
                if (e == null) {
                    searchFriendView.searchSucess(list);
                } else {
                    searchFriendView.searchError();
                }

            }
        });
    }
}
