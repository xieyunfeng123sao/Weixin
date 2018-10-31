package com.ityun.weixin.myapplication.model;


import com.ityun.weixin.myapplication.bean.Friend;
import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.db.Config;
import com.ityun.weixin.myapplication.listener.BmobTableListener;
import com.orhanobut.logger.Logger;
import java.io.File;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Administrator on 2018/3/16 0016.
 */

public class UserModel {

    private static UserModel userModel;

    public static UserModel getInstance() {
        if (userModel == null)
            userModel = new UserModel();
        return userModel;
    }


    public User getUser() {
        User user = BmobUser.getCurrentUser(User.class);
        return user;
    }


    /**
     * 注册用户
     *
     * @param user
     * @param listener
     */
    public void addUser(User user, final BmobTableListener listener) {
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    listener.onSucess(user);
                } else {
                    listener.onFail(e);
                }
            }
        });
    }

    /**
     * 上传头像
     *
     * @param path
     * @param listener
     */
    public void addFile(String path, final BmobTableListener listener) {
        final BmobFile file = new BmobFile(new File(path));
        file.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    listener.onSucess(file.getFileUrl());
                } else {
                    listener.onFail(e);
                }
            }
        });
    }

    /**
     * 登录
     *
     * @param user
     * @param listener
     */
    public void login(User user, final BmobTableListener listener) {
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    listener.onSucess(getUser());
                } else {
                    Logger.e(e.getErrorCode() + "====" + e.getMessage());
                    listener.onFail(e);
                }
            }
        });
    }

    /**
     * 根据号码查找用户
     *
     * @param num
     * @param listener
     */
    public void queryByNum(String num, final BmobTableListener listener) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("username", num);
        query.findObjects(
                new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (e == null) {
                            if (list != null && list.size() > 0) {
                                listener.onSucess(list.get(0));
                            } else {
//                                listener.done(null, new BmobException(000, "查无此人"));
                                listener.onFail(new BmobException(000, "查无此人"));
                            }
                        } else {
                            listener.onFail(e);
                        }
                    }
                });
    }

    /**
     * 根据id查找用户
     *
     * @param id
     * @param listener
     */
    public void queryById(String id, final BmobTableListener listener) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", id);
        query.findObjects(
                new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (e == null) {
                            if (list != null && list.size() > 0) {
                                listener.onSucess(list.get(0));
                            } else {
                                listener.onFail(new BmobException(000, "查无此人"));
                            }
                        } else {
                            listener.onFail(e);
                        }
                    }
                });
    }


    /**
     * 查询好友
     *
     * @param listener
     */
    //TODO 好友管理：9.2、查询好友
    public void queryFriends(final FindListener<Friend> listener) {
        BmobQuery<Friend> query = new BmobQuery<>();
        User user = BmobUser.getCurrentUser(User.class);
        query.addWhereEqualTo("user", user);
        query.include("friendUser");
        query.order("-updatedAt");
        query.findObjects(new FindListener<Friend>() {
            @Override
            public void done(List<Friend> list, BmobException e) {
                if (e == null) {
                    if (list != null && list.size() > 0) {
                        listener.done(list, e);
                    } else {
                        listener.done(list, new BmobException(0, "暂无联系人"));
                    }
                } else {
                    listener.done(list, e);
                }
            }
        });
    }


    public void addNewFriend(final User friend, final SaveListener<String> listener) {
        BmobQuery<Friend> query = new BmobQuery<>();
        final User user = BmobUser.getCurrentUser(User.class);
        query.addWhereEqualTo("user", user);
        query.addWhereEqualTo("friendUser", friend);
        query.findObjects(new FindListener<Friend>() {
            @Override
            public void done(List<Friend> list, BmobException e) {
                if (e == null && list.size() == 0) {
                    Friend sendFriend = new Friend();
                    sendFriend.setUser(user);
                    sendFriend.setFriendUser(friend);
                    sendFriend.save(listener);
                } else if (list.size() != 0) {
                    listener.done("已经是好友了", new BmobException(Config.STATUS_HAS_ADD));
                } else {
                    listener.done("请求错误", new BmobException(Config.STATUS_ERROR));
                }
            }
        });
    }

    public void searchFriend(final BmobTableListener listener) {
        BmobQuery<Friend> query = new BmobQuery<>();
        query.addWhereEqualTo("user", getUser());
        query.findObjects(new FindListener<Friend>() {
            @Override
            public void done(List<Friend> list, BmobException e) {
                if (e == null) {
                    listener.onSucess(list);
                } else {
                    listener.onFail(e);
                }
            }
        });
    }
}
