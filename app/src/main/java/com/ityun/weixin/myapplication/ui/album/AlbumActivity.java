package com.ityun.weixin.myapplication.ui.album;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.BaseActivity;
import com.ityun.weixin.myapplication.ui.album.adapter.AlbumAdapter;
import com.ityun.weixin.myapplication.util.PermissionUtil;
import com.ityun.weixin.myapplication.util.addpic.LocalMedia;
import com.ityun.weixin.myapplication.util.addpic.LocalMediaFolder;
import com.ityun.weixin.myapplication.util.addpic.LocalMediaLoader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/22 0022.
 */

public class AlbumActivity extends BaseActivity {

    ActionBar actionBar;

    @BindView(R.id.picture_file_name)
    TextView picture_file_name;

    @BindView(R.id.picture_gridview)
    GridView picture_gridview;

    @BindView(R.id.picture_date)
    TextView picture_date;

    private List<LocalMediaFolder> mediaFolderList;

    private List<LocalMedia> localMediaList;
    private int sType = 0;

    public int MY_PERMISSIONS_REQUEST_OPEN_ALBUM = 10;

    private AlbumAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        ButterKnife.bind(this);
        adapter=new AlbumAdapter(this);
        picture_gridview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        initActionBar();
        if (PermissionUtil.readFile(this, MY_PERMISSIONS_REQUEST_OPEN_ALBUM) == 1) {
            initView();
        }
    }

    private void initView() {
        mediaFolderList = new ArrayList<>();
        localMediaList = new ArrayList<>();
        new LocalMediaLoader(this, 0, false).loadAllImage(new LocalMediaLoader.LocalMediaLoadListener() {
            @Override
            public void loadComplete(List<LocalMediaFolder> folders) {
                mediaFolderList.addAll(folders);
                localMediaList.addAll(mediaFolderList.get(sType).getImages());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        picture_file_name.setText(mediaFolderList.get(sType).getName());
                        adapter.setData(localMediaList);
                    }
                });

            }
        });

        picture_file_name.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params = picture_file_name.getLayoutParams();
                params.height = actionBar.getHeight();
                picture_file_name.setLayoutParams(params);
            }
        });
        picture_gridview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }


    private void initActionBar() {
        actionBar = getSupportActionBar();
        if (actionBar == null)
            return;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.pic);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_OPEN_ALBUM) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //授权成功
                initView();
            } else {
                //授权失败
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
