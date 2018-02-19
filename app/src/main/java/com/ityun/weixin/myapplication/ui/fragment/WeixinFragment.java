package com.ityun.weixin.myapplication.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.ui.fragment.adapter.WeixinAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/2/12 0012.
 */

public class WeixinFragment extends Fragment {

    @BindView(R.id.weixin_message_list)
    RecyclerView weixin_message_list;


    private List mlist;

    private WeixinAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_weixin,container,false);
        ButterKnife.bind(this,view);
        mlist=new ArrayList();
        for(int i=0;i<20;i++)
        {
            mlist.add(123);
        }
        adapter=new WeixinAdapter(getActivity());
        LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        weixin_message_list.setLayoutManager(manager);
        weixin_message_list.setAdapter(adapter);
        weixin_message_list.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        adapter.setData(mlist);
        adapter.notifyDataSetChanged();
        return view;
    }
}
