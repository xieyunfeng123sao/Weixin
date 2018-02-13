package com.ityun.weixin.myapplication.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ityun.weixin.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/2/12 0012.
 */

public class WeixinFragment extends Fragment {

    @BindView(R.id.weixin_message_list)
    RecyclerView weixin_message_list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_weixin,container,false);
        ButterKnife.bind(this,view);
        return view;
    }
}
