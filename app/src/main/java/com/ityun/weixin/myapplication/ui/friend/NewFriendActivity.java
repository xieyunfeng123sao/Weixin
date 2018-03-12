package com.ityun.weixin.myapplication.ui.friend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.BaseActivity;
import com.ityun.weixin.myapplication.ui.friend.adapter.NewFriendAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class NewFriendActivity extends BaseActivity {

    @BindView(R.id.new_friend_msg_list)
    public RecyclerView new_friend_msg_list;

    NewFriendAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfriend);
        ButterKnife.bind(this);
        adapter=new NewFriendAdapter(this);
        LinearLayoutManager manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        new_friend_msg_list.setLayoutManager(manager);
        new_friend_msg_list.setAdapter(adapter);
    }
}
