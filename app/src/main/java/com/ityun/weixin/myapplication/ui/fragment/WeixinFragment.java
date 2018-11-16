package com.ityun.weixin.myapplication.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.App;
import com.ityun.weixin.myapplication.base.BaseFragment;
import com.ityun.weixin.myapplication.bean.Friend;
import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.event.IMLoginEvent;
import com.ityun.weixin.myapplication.event.IMRefreshEvent;
import com.ityun.weixin.myapplication.im.IMModel;
import com.ityun.weixin.myapplication.listener.AdapterItemOnClickListener;
import com.ityun.weixin.myapplication.listener.MyEMMessageListener;
import com.ityun.weixin.myapplication.ui.chat.ChatActivity;
import com.ityun.weixin.myapplication.ui.fragment.adapter.WeixinAdapter;
import com.ityun.weixin.myapplication.util.SpUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/2/12 0012.
 */

public class WeixinFragment extends BaseFragment {

    @BindView(R.id.weixin_message_list)
    RecyclerView weixin_message_list;

    @BindView(R.id.weixin_load_view)
    RelativeLayout weixin_load_view;

    private WeixinAdapter adapter;

    Map<String, EMConversation> map = new HashMap<>();

    private List<Friend> friendList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weixin, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        friendList = App.getInstance().getFriends();
        adapter = new WeixinAdapter(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        weixin_message_list.setLayoutManager(manager);
        weixin_message_list.setAdapter(adapter);
        weixin_message_list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter.setData(map);
        adapter.notifyDataSetChanged();
        adapter.setonItemOnClick(new AdapterItemOnClickListener() {
            @Override
            public void OnClick(int position) {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("friend", user);
//                startActivity(ChatActivity.class, bundle);
            }
        });
        loginIM();
        return view;
    }

    private void loginIM() {
        User user = SpUtil.getUser();
        IMModel.getInstance().login(user.getUsername(), "123456");
        IMModel.getInstance().addMessageListener(new MyEMMessageListener());
//        map.putAll(IMModel.getInstance().allConversation());
//        for (String string : map.keySet())
//        {
//            Log.e("Ceshhi",string);
//        }
//        adapter.notifyDataSetChanged();
    }


    @Override
    public void onStart() {

        super.onStart();
    }

    @Override
    public void onStop() {

        super.onStop();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void requestData() {
        weixin_load_view.setVisibility(View.GONE);
        weixin_message_list.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onEvent(IMLoginEvent event) {
        if (event.getLoginResult().equals("sucess")) {
            map.clear();
//            IMModel.getInstance().addConnectionListener();
//            IMModel.getInstance().setContactListener();
//            IMModel.getInstance().getAllFrind();
            Map<String, EMConversation> allMap = new ArrayMap<>();
            allMap.putAll(IMModel.getInstance().allConversation());
            //是好友才会显示
            for (String string : allMap.keySet()) {
                if (friendList != null && friendList.size() != 0) {
                    boolean isFriend = false;
                    for (Friend friend : friendList) {
                        if (friend.getFriendUser().getUsername().equals(string)) {
                            isFriend = true;
                        }
                    }
                    if (isFriend) {
                        map.put(string, allMap.get(string));
                    }
                }
            }
            adapter.notifyDataSetChanged();
        } else {

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageRefru(IMRefreshEvent event) {
        map.clear();
//        IMModel.getInstance().addConnectionListener();
//        IMModel.getInstance().setContactListener();
//        IMModel.getInstance().getAllFrind();
        map.putAll(IMModel.getInstance().allConversation());
        adapter.notifyDataSetChanged();
    }


}
