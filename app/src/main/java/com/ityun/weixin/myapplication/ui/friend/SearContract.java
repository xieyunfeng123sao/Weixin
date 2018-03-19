package com.ityun.weixin.myapplication.ui.friend;

import com.ityun.weixin.myapplication.base.BasePresenter;
import com.ityun.weixin.myapplication.base.BaseView;
import com.ityun.weixin.myapplication.bean.Friend;
import com.ityun.weixin.myapplication.bean.User;

import java.util.List;

/**
 * Created by Administrator on 2018/3/9 0009.
 */

public interface SearContract {

    interface View extends BaseView<SearContract.Presenter> {
        void searchSucess(User user);

        void searchFail();

        void searchError();
    }

    interface SearchFriendView extends BaseView<Presenter> {
        void searchSucess(List<Friend> mlist);
        void  searchError();
    }

    interface Presenter extends BasePresenter {
        void searchUser(String num);
        void  searchById(String uid);
        void searchFriend();
    }
}
