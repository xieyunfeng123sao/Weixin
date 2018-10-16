package com.ityun.weixin.myapplication.ui.friend;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.BaseActivity;
import com.ityun.weixin.myapplication.bean.NewFriend;
import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.dao.NewFriendUtil;
import com.ityun.weixin.myapplication.db.Config;
import com.ityun.weixin.myapplication.event.IMNewFriendEvent;
import com.ityun.weixin.myapplication.im.IMModel;
import com.ityun.weixin.myapplication.listener.IMFriendCallBack;
import com.ityun.weixin.myapplication.model.UserModel;
import com.ityun.weixin.myapplication.ui.friend.adapter.NewFriendAdapter;
import com.ityun.weixin.myapplication.view.LoadDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class NewFriendActivity extends BaseActivity implements SearContract.View {

    @BindView(R.id.new_friend_msg_list)
    public RecyclerView new_friend_msg_list;

    NewFriendAdapter adapter;

    private List<NewFriend> mlist;

    private Dialog dialog;

    private SearchPrensenter prensenter;

    private int nowPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfriend);
        ButterKnife.bind(this);
        dialog = new LoadDialog(this).setText("正在操作").build();
        prensenter = new SearchPrensenter(this);
        adapter = new NewFriendAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        new_friend_msg_list.setLayoutManager(manager);
        new_friend_msg_list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        new_friend_msg_list.setAdapter(adapter);
        mlist = new ArrayList<>();
        adapter.setData(mlist);
        mlist.addAll(NewFriendUtil.getInstance().getAll());
        adapter.notifyDataSetChanged();
        adapter.setOnItemAgreeClickListener(new NewFriendAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                prensenter.searchById(mlist.get(position).getUid());
                nowPosition = position;
                dialog.show();
            }
        });
    }

    @Override
    public void searchSucess(final User user) {
        IMModel.getInstance().agreeFriend(user.getUsername(), new IMFriendCallBack() {
            @Override
            public void sendSucess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        UserModel.getInstance().addNewFriend(user, new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null || e.getErrorCode() == Config.STATUS_HAS_ADD) {
                                    NewFriend newFriend = mlist.get(nowPosition);
                                    newFriend.setStatus(1);
                                    NewFriendUtil.getInstance().updata(newFriend);
                                    mlist.clear();
                                    mlist.addAll(NewFriendUtil.getInstance().getAll());
                                    adapter.notifyDataSetChanged();
                                    EventBus.getDefault().post(new IMNewFriendEvent("hasAdd"));
                                }
                                dialog.dismiss();
                            }
                        });
                    }
                });
            }
            @Override
            public void sendFail() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast(R.string.error_add);
                        dialog.dismiss();
                    }
                });
            }
        });
    }


    @Override
    public void searchFail() {
        Toast(R.string.error_add);
        dialog.dismiss();
    }

    @Override
    public void searchError() {
        Toast(R.string.error_add);
        dialog.dismiss();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(IMNewFriendEvent event) {
        if (event.getResult().equals("notice")) {
            //接收到好友的请求通知
            mlist.clear();
            mlist.addAll(NewFriendUtil.getInstance().getAll());
            adapter.notifyDataSetChanged();
        }
    }
}
