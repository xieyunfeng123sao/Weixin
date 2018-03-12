package com.ityun.weixin.myapplication.ui.friend;

import com.google.gson.Gson;
import com.ityun.weixin.myapplication.bean.FriendInfo;
import com.ityun.weixin.myapplication.bean.UserInfo;
import com.ityun.weixin.myapplication.listener.BmobTableListener;
import com.ityun.weixin.myapplication.table.FriendHelper;
import com.ityun.weixin.myapplication.table.UserHelper;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2018/3/9 0009.
 */

public class SearchPrensenter implements SearContract.Presenter {

    private SearContract.View view;

    public SearchPrensenter(SearContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void searchUser(String num) {
        UserHelper.getInstance().queryLoginName(num, new BmobTableListener() {
            @Override
            public void onSucess(Object object) {
                JSONArray array = (JSONArray) object;
                if (array.length() == 0) {
                    view.searchFail(0);
                } else {
                    Object obj = null;
                    try {
                        obj = array.get(0);
                        Gson gson = new Gson();
                        UserInfo userInfo = gson.fromJson(obj.toString(), UserInfo.class);
                        view.searchSucess(userInfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFail(BmobException e) {
                view.searchError();
                Logger.e(e.toString());
            }
        });
    }

    @Override
    public void addFriend(FriendInfo friendInfo) {
        FriendHelper.getInstance().addFriend(friendInfo, new BmobTableListener() {
            @Override
            public void onSucess(Object object) {

            }

            @Override
            public void onFail(BmobException e) {

            }
        });
    }
}
