package com.ityun.weixin.myapplication.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.promeg.pinyinhelper.Pinyin;
import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.BaseFragment;
import com.ityun.weixin.myapplication.bean.Friend;
import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.listener.BmobTableListener;
import com.ityun.weixin.myapplication.model.UserModel;
import com.ityun.weixin.myapplication.ui.fragment.adapter.FriendAdapter;
import com.ityun.weixin.myapplication.ui.friend.SearContract;
import com.ityun.weixin.myapplication.ui.friend.SearchPrensenter;
import com.ityun.weixin.myapplication.view.SideBar;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2018/2/12 0012.
 */

public class FriendFragment extends BaseFragment implements SearContract.SearchFriendView {

    @BindView(R.id.touch_sidebar)
    TextView touch_sidebar;

    @BindView(R.id.friend_sidebar)
    SideBar friend_sidebar;

    @BindView(R.id.friend_recycle)
    RecyclerView friend_recycle;

    FriendAdapter adapter;

    private SearContract.Presenter prensenter;

    private List<Friend> mlist=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_friend,container,false);
        ButterKnife.bind(this,view);
        friend_sidebar.setTextView(touch_sidebar);
        prensenter=new SearchPrensenter(this);
        return view;
    }
    @Override
    public void requestData() {
        adapter=new FriendAdapter(getActivity());
        LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        friend_recycle.setLayoutManager(manager);
        friend_recycle.setAdapter(adapter);
        adapter.setData(mlist);
        adapter.notifyDataSetChanged();
        prensenter.searchFriend();
        mlist.clear();
    }

    @Override
    public void searchSucess(final List<Friend> mlistFriend) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < mlistFriend.size(); i++) {
                    Friend friend = mlistFriend.get(i);
                    String username = friend.getFriendUser().getNickname();
                    if (username != null) {
                        String pinyin = Pinyin.toPinyin(username.charAt(0));
                        friend.setPinyin(pinyin.substring(0, 1).toUpperCase());
                        mlist.add(friend);
                    }
                }
                adapter.setData(mlist);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void searchError() {
        adapter.setData(mlist);
        adapter.notifyDataSetChanged();
    }
}
