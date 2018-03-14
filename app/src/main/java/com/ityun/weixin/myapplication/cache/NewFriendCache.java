package com.ityun.weixin.myapplication.cache;


import com.ityun.weixin.myapplication.bean.NewFriend;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class NewFriendCache {

    private static NewFriendCache newFriendCache;

    public static NewFriendCache getInstance() {
        if (newFriendCache == null)
            newFriendCache = new NewFriendCache();
        return newFriendCache;
    }

    /**
     * 保存新好友
     *
     * @param newFriend
     */
    public boolean saveNewFriend(NewFriend newFriend) {
        List<NewFriend> newFriendList = getFriendById(newFriend.getUid());
        if (newFriendList == null || newFriendList.size() == 0) {
            newFriend.save();
            return true;
        } else {
            upDataNewFriend(newFriend,newFriendList.get(0).getId());
            return false;
        }

    }


    /**
     * 更新好友状态
     *
     * @param id
     */
    public void upDataNewFriend(NewFriend friend, long id) {
        NewFriend newFriend = DataSupport.find(NewFriend.class, id);
        newFriend.setStatus(friend.getStatus());
        newFriend.setTime(friend.getTime());
        newFriend.setName(friend.getName());
        newFriend.setAvatar(friend.getAvatar());
        newFriend.setMsg(friend.getMsg());
        newFriend.save();
    }

    /**
     * 获取所有好友信息
     *
     * @return
     */
    public List<NewFriend> getAllFriend() {
        List<NewFriend> newFriendList = DataSupport.findAll(NewFriend.class);
        return newFriendList;
    }


    public List<NewFriend> getFriendById(String uid) {
        List<NewFriend> hasFriend = DataSupport.where("uid = ?", uid).find(NewFriend.class);

        return hasFriend;
    }


}
