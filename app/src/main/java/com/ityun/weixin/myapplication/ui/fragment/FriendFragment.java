package com.ityun.weixin.myapplication.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.view.SideBar;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/2/12 0012.
 */

public class FriendFragment extends Fragment {

    @BindView(R.id.touch_sidebar)
    TextView touch_sidebar;

    @BindView(R.id.friend_sidebar)
    SideBar friend_sidebar;

    @BindView(R.id.friend_recycle)
    RecyclerView friend_recycle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_friend,container,false);
        ButterKnife.bind(this,view);
        friend_sidebar.setTextView(touch_sidebar);
        return view;
    }
}
