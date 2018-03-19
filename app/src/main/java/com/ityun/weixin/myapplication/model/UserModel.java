package com.ityun.weixin.myapplication.model;

import android.util.Log;

import com.ityun.weixin.myapplication.bean.Friend;
import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.db.Config;
import com.ityun.weixin.myapplication.listener.BmobTableListener;
import com.ityun.weixin.myapplication.listener.UpdateCacheListener;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.event.MessageEvent;
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
     * 更新用户资料和会话资料
     *
     * @param event
     * @param listener
     */
    public void updateUserInfo(MessageEvent event, final UpdateCacheListener listener) {
        final BmobIMConversation conversation = event.getConversation();
        final BmobIMUserInfo info = event.getFromUserInfo();
        final BmobIMMessage msg = event.getMessage();
        String username = info.getName();
        String avatar = info.getAvatar();
        String title = conversation.getConversationTitle();
        String icon = conversation.getConversationIcon();
        //SDK内部将新会话的会话标题用objectId表示，因此需要比对用户名和私聊会话标题，后续会根据会话类型进行判断
        if (!username.equals(title) || (avatar != null && !avatar.equals(icon))) {
            queryById(info.getUserId(), new BmobTableListener() {
                @Override
                public void onSucess(Object object) {
                    User user = (User) object;
                    String name = user.getUsername();
                    String avatar = user.getAvatar();
                    conversation.setConversationIcon(avatar);
                    conversation.setConversationTitle(name);
                    info.setName(name);
                    info.setAvatar(avatar);
                    //TODO 用户管理：2.7、更新用户资料，用于在会话页面、聊天页面以及个人信息页面显示
                    BmobIM.getInstance().updateUserInfo(info);
                    //TODO 会话：4.7、更新会话资料-如果消息是暂态消息，则不更新会话资料
                    if (!msg.isTransient()) {
                        BmobIM.getInstance().updateConversation(conversation);
                    } else {
                    }
                    listener.done();
                }

                @Override
                public void onFail(BmobException e) {
                    listener.done();
                }
            });
        } else {
            listener.done();
        }
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
