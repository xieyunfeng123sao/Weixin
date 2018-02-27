package com.ityun.weixin.myapplication.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.RadioButton;

import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.App;
import com.ityun.weixin.myapplication.base.BaseActivity;
import com.ityun.weixin.myapplication.ui.fragment.FindFragment;
import com.ityun.weixin.myapplication.ui.fragment.FriendFragment;
import com.ityun.weixin.myapplication.ui.fragment.MeFragment;
import com.ityun.weixin.myapplication.ui.fragment.WeixinFragment;
import com.ityun.weixin.myapplication.ui.fragment.adapter.HomeFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    private List<Fragment> fragmentList;


    private HomeFragmentAdapter adapter;

    private ActionBar actionBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initFragment();
        initActionBar();
    }

    private void initActionBar() {
        actionBar=getSupportActionBar();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public  void initFragment()
    {
        weixinFragment=new WeixinFragment();
        friendFragment=new FriendFragment();
        findFragment=new FindFragment();
        meFragment=new MeFragment();
        fragmentList=new ArrayList<>();
        fragmentList.add(weixinFragment);
        fragmentList.add(friendFragment);
        fragmentList.add(findFragment);
        fragmentList.add(meFragment);
        adapter=new HomeFragmentAdapter(getSupportFragmentManager(), fragmentList);
        activity_home_viewpager.setAdapter(adapter);
        activity_home_viewpager.setOffscreenPageLimit(3);
        activity_home_viewpager.setCurrentItem(0);  //初始化显示第一个页面
        activity_home_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changePager(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    private void changePager(int position) {
        switch (position) {
            case 0:
                radio_weixin.setChecked(true);
                break;
            case 1:
                radio_comm.setChecked(true);
                break;
            case 2:
                radio_find.setChecked(true);
                break;
            case 3:
                radio_me.setChecked(true);
                break;
            default:
                break;
        }
    }


    @OnClick({R.id.radio_weixin, R.id.radio_comm, R.id.radio_find, R.id.radio_me})
    public void radioOnClick(View view) {
        switch (view.getId()) {
            case R.id.radio_weixin:
                activity_home_viewpager.setCurrentItem(0);
                break;
            case R.id.radio_comm:
                activity_home_viewpager.setCurrentItem(1);
                break;
            case  R.id.radio_find:
                activity_home_viewpager.setCurrentItem(2);
                break;
            case R.id.radio_me:
                activity_home_viewpager.setCurrentItem(3);
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
