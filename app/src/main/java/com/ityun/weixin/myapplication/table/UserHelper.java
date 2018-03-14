package com.ityun.weixin.myapplication.table;

import com.google.gson.Gson;
import com.ityun.weixin.myapplication.bean.UserInfo;
import com.ityun.weixin.myapplication.conn.TableName;
import com.ityun.weixin.myapplication.listener.BmobTableListener;
import com.ityun.weixin.myapplication.listener.UpdateCacheListener;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.Date;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class UserHelper {

    private static UserHelper userHelper;

    public static UserHelper getInstance() {
        if (userHelper == null) {
            userHelper = new UserHelper();
        }
        return userHelper;
    }

    /**
     * 添加用户
     *
     * @param user
     */
    public void addUser(final UserInfo user, final BmobTableListener listener) {
        user.setCreateTime(new BmobDate(new Date()));
        user.setUpdataTime(new BmobDate(new Date()));
        user.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    listener.onSucess(s);
                } else {
                    listener.onFail(e);
                }
            }
        });
    }

    /**
     * 通过用户名查询
     *
     * @param loginName
     * @param listener
     */
    public void queryLoginName(String loginName, final BmobTableListener listener) {
        BmobQuery<UserInfo> query = new BmobQuery<>(TableName.userTable);
        query.addWhereEqualTo("loginName", loginName);
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if (e == null) {
                    listener.onSucess(jsonArray);
                } else {
                    listener.onFail(e);
                }
            }
        });
    }

    /**
     * 通过用户id查询
     *
     * @param uid
     * @param listener
     */
    public void queryLoginId(String uid, final BmobTableListener listener) {
        BmobQuery<UserInfo> query = new BmobQuery<>(TableName.userTable);
        query.addWhereEqualTo("objectId", uid);
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if (e == null) {
                    listener.onSucess(jsonArray);
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
    public void queryLoginUser(UserInfo user, final BmobTableListener listener) {
        BmobQuery<UserInfo> query = new BmobQuery<>(TableName.userTable);
        query.addWhereEqualTo("loginName", user.getLoginName());
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if (e == null) {
                    listener.onSucess(jsonArray);
                } else {
                    listener.onFail(e);
                }
            }
        });
    }

    /**
     * 上传图片
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
            queryLoginId(info.getUserId(), new BmobTableListener() {
                @Override
                public void onSucess(Object object) {
                    JSONArray array = (JSONArray) object;
                    if (array.length() != 0) {
                        Object obj = null;
                        try {
                            obj = array.get(0);
                            Gson gson = new Gson();
                            UserInfo userInfo = gson.fromJson(obj.toString(), UserInfo.class);
                            String name = userInfo.getUserName();
                            String avatar = userInfo.getUserPic();
                            conversation.setConversationIcon(avatar);
                            conversation.setConversationTitle(name);
                            info.setName(name);
                            info.setAvatar(avatar);
                            //TODO 用户管理：2.7、更新用户资料，用于在会话页面、聊天页面以及个人信息页面显示
                            BmobIM.getInstance().updateUserInfo(info);
                            //TODO 会话：4.7、更新会话资料-如果消息是暂态消息，则不更新会话资料
                            if (!msg.isTransient()) {
                                BmobIM.getInstance().updateConversation(conversation);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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


}
