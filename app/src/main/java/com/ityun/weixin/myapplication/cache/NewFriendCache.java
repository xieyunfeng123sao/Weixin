package com.ityun.weixin.myapplication.cache;


import com.ityun.weixin.myapplication.bean.NewFriend;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class NewFriendCache {

    private  static  NewFriendCache newFriendCache;

    public static NewFriendCache getInstance()
    {
        if(newFriendCache==null)
            newFriendCache=new NewFriendCache();
        return newFriendCache;
    }

    /**
     * 保存新好友
     * @param newFriend
     */
    public  void saveNewFriend(NewFriend newFriend)
    {
        newFriend.save();
    }

    /**
     * 更新好友状态
     * @param statue
     * @param id
     */
    public  void  upDataNewFriend(int statue,int id)
    {
        NewFriend newFriend = DataSupport.find(NewFriend.class,id);
        newFriend.setStatus(statue);
        newFriend.save();
    }

    /**
     * 获取所有好友信息
     * @return
     */
    public List<NewFriend> getAllFriend()
    {
        List<NewFriend> newFriendList=DataSupport.findAll(NewFriend.class);
        return  newFriendList;
    }


}
