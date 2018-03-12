package com.ityun.weixin.myapplication.ui.friend;

import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.media.MediaCodec;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.BaseActivity;
import com.ityun.weixin.myapplication.bean.UserInfo;
import com.ityun.weixin.myapplication.table.UserHelper;
import com.ityun.weixin.myapplication.ui.login.LoginContract;
import com.ityun.weixin.myapplication.ui.use.UserDetailActivity;
import com.ityun.weixin.myapplication.view.LoadDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public class SearchUserActivity extends BaseActivity implements SearContract.View {

    private TextView search_weixin_user;

    private ImageView search_top_finish;

    @BindView(R.id.input_search_view)
    public RelativeLayout input_search_view;

    @BindView(R.id.input_search_txt)
    public TextView input_search_txt;

    @BindView(R.id.search_no_user_ll)
    public LinearLayout search_no_user_ll;


    @BindView(R.id.input_search_onther_txt)
    public TextView input_search_onther_txt;

    private  SearContract.Presenter presenter;

    Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_weixin);
        ButterKnife.bind(this);
        initListener();
        presenter=new SearchPrensenter(this);
    }

    private void initListener() {
        input_search_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFriend();
            }
        });

        search_no_user_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_edittext);
        search_weixin_user = actionBar.getCustomView().findViewById(R.id.search_weixin_user);
        search_top_finish = actionBar.getCustomView().findViewById(R.id.search_top_finish);

        search_weixin_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changeSearchTxt(s.toString());
            }
        });

        search_weixin_user.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    searchFriend();
                    return true;
                }
                return false;
            }
        });

        search_top_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void changeSearchTxt(String inputString) {
        search_no_user_ll.setVisibility(View.GONE);
        if (inputString == null || (inputString.length() == 0)) {
            input_search_view.setVisibility(View.GONE);
            input_search_txt.setText("");
            input_search_onther_txt.setText("");
        } else {
            input_search_view.setVisibility(View.VISIBLE);
            input_search_txt.setText(inputString);
            input_search_onther_txt.setText(inputString);
        }
    }

    private void  searchFriend()
    {
        dialog = new LoadDialog(this).setText(R.string.searching_user).build();
        dialog.show();
        String  num= search_weixin_user.getText().toString();
        presenter.searchUser(num);
    }

    @Override
    public void searchSucess(final UserInfo userInfo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SearchUserActivity.this, UserDetailActivity.class);
                intent.putExtra("userInfo",userInfo);
                startActivity(intent);
                dialog.dismiss();
            }
        });
    }

    @Override
    public void searchFail(int errorId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                search_no_user_ll.setVisibility(View.VISIBLE);
                input_search_view.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });
    }

    @Override
    public void searchError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast(R.string.error_net);
                dialog.dismiss();
            }
        });

    }
}
