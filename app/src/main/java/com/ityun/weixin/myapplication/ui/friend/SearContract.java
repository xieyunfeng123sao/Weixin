package com.ityun.weixin.myapplication.ui.friend;

import com.ityun.weixin.myapplication.base.BasePresenter;
import com.ityun.weixin.myapplication.base.BaseView;
import com.ityun.weixin.myapplication.bean.FriendInfo;
import com.ityun.weixin.myapplication.bean.UserInfo;
import com.ityun.weixin.myapplication.ui.login.LoginContract;

/**
 * Created by Administrator on 2018/3/9 0009.
 */

public interface SearContract {

    interface View extends BaseView<SearContract.Presenter> {
        void searchSucess(UserInfo user);

        void searchFail(int errorId);

        void searchError();
    }

    interface AddFriendView extends BaseView<Presenter> {
        void addSucess();
        void addFail();
    }

    interface Presenter extends BasePresenter {
        void searchUser(String num);
        void  searchById(String uid);
        void addFriend(FriendInfo friendInfo);
    }
}
