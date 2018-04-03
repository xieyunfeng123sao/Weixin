package com.ityun.weixin.myapplication.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.BaseFragment;
import com.ityun.weixin.myapplication.bean.Friend;
import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.im.IMModel;
import com.ityun.weixin.myapplication.listener.AdapterItemOnClickListener;
import com.ityun.weixin.myapplication.ui.chat.ChatActivity;
import com.ityun.weixin.myapplication.ui.fragment.adapter.WeixinAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;

/**
 * Created by Administrator on 2018/2/12 0012.
 */

public class WeixinFragment extends BaseFragment {

    @BindView(R.id.weixin_message_list)
    RecyclerView weixin_message_list;

    @BindView(R.id.weixin_load_view)
    RelativeLayout weixin_load_view;

    private List<BmobIMConversation> mlist;

    private WeixinAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weixin, container, false);
        ButterKnife.bind(this, view);
        mlist = new ArrayList();
        adapter = new WeixinAdapter(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        weixin_message_list.setLayoutManager(manager);
        weixin_message_list.setAdapter(adapter);
        weixin_message_list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter.setData(mlist);
        adapter.notifyDataSetChanged();
        adapter.setonItemOnClick(new AdapterItemOnClickListener() {
            @Override
            public void OnClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("c", mlist.get(position));
                startActivity(ChatActivity.class, bundle);
            }
        });
        return view;
    }

    @Override
    public void requestData() {
        List<BmobIMConversation> list = IMModel.getInstance().getAllConversation();
        if (list != null) {
            mlist.addAll(list);
            adapter.setData(mlist);
            adapter.notifyDataSetChanged();
        }
        weixin_load_view.setVisibility(View.GONE);
        weixin_message_list.setVisibility(View.VISIBLE);
    }

    /**
     * 启动指定Activity
     *
     * @param target
     * @param bundle
     */
    public void startActivity(Class<? extends Activity> target, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), target);
        if (bundle != null)
            intent.putExtra(getActivity().getPackageName(), bundle);
        getActivity().startActivity(intent);
    }
}
