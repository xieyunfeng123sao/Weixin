package com.ityun.weixin.myapplication.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;

import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.App;
import com.ityun.weixin.myapplication.base.BaseActivity;
import com.ityun.weixin.myapplication.ui.fragment.FindFragment;
import com.ityun.weixin.myapplication.ui.fragment.FriendFragment;
import com.ityun.weixin.myapplication.ui.fragment.MeFragment;
import com.ityun.weixin.myapplication.ui.fragment.WeixinFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class HomeActivity extends BaseActivity {


    @BindView(R.id.activity_home_viewpager)
    public ViewPager activity_home_viewpager;

    @BindView(R.id.radio_weixin)
    public RadioButton radio_weixin;

    @BindView(R.id.radio_comm)
    public RadioButton radio_comm;


    @BindView(R.id.radio_find)
    public RadioButton radio_find;

    @BindView(R.id.radio_me)
    public RadioButton radio_me;

    private WeixinFragment weixinFragment;

    private FriendFragment friendFragment;


    private FindFragment findFragment;

    private MeFragment meFragment;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        weixinFragment=new WeixinFragment();
        friendFragment=new FriendFragment();
        findFragment=new FindFragment();
        meFragment=new MeFragment();
    }


    @OnClick({R.id.radio_weixin, R.id.radio_comm, R.id.radio_find, R.id.radio_me})
    public void radioOnClick(View view) {
        switch (view.getId()) {
            case R.id.radio_weixin:

                break;
            case R.id.radio_comm:

                break;
            case  R.id.radio_find:

                break;
            case R.id.radio_me:

                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        App.getInstance().finishOrActivity(this);
    }
}
