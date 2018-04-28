package com.ityun.weixin.myapplication.dao;

import com.ityun.weixin.myapplication.base.App;
import com.ityun.weixin.myapplication.bean.DaoMaster;
import com.ityun.weixin.myapplication.bean.DaoSession;
import com.ityun.weixin.myapplication.bean.NewFriend;
import com.ityun.weixin.myapplication.bean.NewFriendDao;
import com.ityun.weixin.myapplication.bean.User;

import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2018/4/28 0028.
 */

public class NewFriendUtil {

    private NewFriendDao newFriendDao;

    private static NewFriendUtil newFriendUtil = new NewFriendUtil();

    public static NewFriendUtil getInstance() {
        return newFriendUtil;
    }


    public void init() {
        User user = BmobUser.getCurrentUser(User.class);
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(App.context, "my-db"+user.getObjectId(), null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        newFriendDao = daoSession.getNewFriendDao();
    }


    public void insert(NewFriend newFriend) {
        newFriendDao.insert(newFriend);
    }

    public void updata(NewFriend newFriend) {
        NewFriend findUser = newFriendDao.queryBuilder().where(NewFriendDao.Properties.Name.eq(newFriend.getName())).build().unique();
        if (findUser != null) {
            newFriendDao.update(newFriend);
//            Toast.makeText(MyApplication.getContext(), "修改成功", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(MyApplication.getContext(), "用户不存在", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(NewFriend newFriend) {
        NewFriend findUser = newFriendDao.queryBuilder().where(NewFriendDao.Properties.Name.eq(newFriend.getName())).build().unique();
        if (findUser != null) {
            newFriendDao.deleteByKey(findUser.getId());
        }
    }


    public List<NewFriend> getAll() {
        List<NewFriend> userList = newFriendDao.loadAll();
        return userList;
    }

    public NewFriend getByName(String name) {
        List<NewFriend> userList = newFriendDao.queryBuilder()
                .where(NewFriendDao.Properties.Name.notEq(name))
                .limit(5)
                .build().list();
        if (userList != null && userList.size() != 0) {
            return userList.get(0);
        }
        return null;

    }

}
