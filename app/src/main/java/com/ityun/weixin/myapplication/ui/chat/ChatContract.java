package com.ityun.weixin.myapplication.ui.chat;

import com.hyphenate.chat.EMMessage;
import com.ityun.weixin.myapplication.base.BasePresenter;
import com.ityun.weixin.myapplication.base.BaseView;
import com.ityun.weixin.myapplication.bean.Friend;
import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.im.IMMessage;

import java.util.List;

/**
 * Created by Administrator on 2018/3/9 0009.
 */

public interface ChatContract {

    interface View extends BaseView<ChatContract.Presenter> {

    }
//
//    interface SendMeeageView extends BaseView<Presenter> {
//        void sendSucess(EMMessage emMessage);
//
//        void sendError(EMMessage emMessage);
//
//        void sendProgress(EMMessage emMessage);
//    }

    interface Presenter extends BasePresenter {
        EMMessage sendMessage(IMMessage imMessage);
    }
}
