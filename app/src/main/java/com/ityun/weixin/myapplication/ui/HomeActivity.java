package com.ityun.weixin.myapplication.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RadioButton;

import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.App;
import com.ityun.weixin.myapplication.base.BaseActivity;
import com.ityun.weixin.myapplication.bean.UserInfo;
import com.ityun.weixin.myapplication.im.IMModel;
import com.ityun.weixin.myapplication.ui.fragment.FindFragment;
import com.ityun.weixin.myapplication.ui.fragment.FriendFragment;
import com.ityun.weixin.myapplication.ui.fragment.MeFragment;
import com.ityun.weixin.myapplication.ui.fragment.WeixinFragment;
import com.ityun.weixin.myapplication.ui.fragment.adapter.HomeFragmentAdapter;
import com.ityun.weixin.myapplication.util.CacheUtils;
import com.ityun.weixin.myapplication.util.DensityUtil;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.v3.exception.BmobException;

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

    private UserInfo userInfo;

    private PopupWindow popupWindow;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initFragment();
        userInfo = CacheUtils.getInstance(this).getCaCheUser();
        IMModel.getInstance().updataUser(userInfo);
        IMModel.getInstance().login(userInfo.getObjectId());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {

        } else if (item.getItemId() == R.id.action_add) {
            showPopuwindow(findViewById(R.id.action_add));
        }
        return super.onOptionsItemSelected(item);
    }

    public void initFragment() {
        weixinFragment = new WeixinFragment();
        friendFragment = new FriendFragment();
        findFragment = new FindFragment();
        meFragment = new MeFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(weixinFragment);
        fragmentList.add(friendFragment);
        fragmentList.add(findFragment);
        fragmentList.add(meFragment);
        adapter = new HomeFragmentAdapter(getSupportFragmentManager(), fragmentList);
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
            case R.id.radio_find:
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

    private void showPopuwindow(View v) {

        // TODO 这里是显示popupWindow的代码
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.home_popuwindow, null);
        int width = getWindow().getDecorView().getWidth() / 2;
        Log.i("宽度", width + "");
        popupWindow = new PopupWindow(view, width,
                WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(v,
                -DensityUtil.dip2px(HomeActivity.this, width/2), 0);
    }


}
