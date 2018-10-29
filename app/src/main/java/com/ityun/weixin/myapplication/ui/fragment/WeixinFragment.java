package com.ityun.weixin.myapplication.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.BaseFragment;
import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.event.IMLoginEvent;
import com.ityun.weixin.myapplication.im.IMModel;
import com.ityun.weixin.myapplication.listener.AdapterItemOnClickListener;
import com.ityun.weixin.myapplication.ui.fragment.adapter.WeixinAdapter;
import com.ityun.weixin.myapplication.util.SpUtil;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weixin, container, false);
        ButterKnife.bind(this, view);
        adapter = new WeixinAdapter(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        weixin_message_list.setLayoutManager(manager);
        weixin_message_list.setAdapter(adapter);
        weixin_message_list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
//        adapter.setData(mlist);
        adapter.notifyDataSetChanged();
        adapter.setonItemOnClick(new AdapterItemOnClickListener() {
            @Override
            public void OnClick(int position) {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("c", mlist.get(position));
//                startActivity(ChatActivity.class, bundle);
            }
        });
        loginIM();
        return view;
    }

    private void loginIM() {
        User user = SpUtil.getUser();
        IMModel.getInstance().login(user.getUsername(), "123456");
    }


    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onDestroy() {
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
            IMModel.getInstance().addConnectionListener();
            IMModel.getInstance().setContactListener();
           IMModel.getInstance().getAllFrind();

        } else {

        }
    }


}
