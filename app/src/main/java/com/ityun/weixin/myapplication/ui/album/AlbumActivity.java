package com.ityun.weixin.myapplication.ui.album;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.BaseActivity;
import com.ityun.weixin.myapplication.conn.FileConstance;
import com.ityun.weixin.myapplication.ui.adduser.AddUserActivity;
import com.ityun.weixin.myapplication.ui.album.adapter.AlbumAdapter;
import com.ityun.weixin.myapplication.util.DataUtils;
import com.ityun.weixin.myapplication.util.PermissionUtil;
import com.ityun.weixin.myapplication.util.PhotoUtil;
import com.ityun.weixin.myapplication.util.addpic.LocalMedia;
import com.ityun.weixin.myapplication.util.addpic.LocalMediaFolder;
import com.ityun.weixin.myapplication.util.addpic.LocalMediaLoader;
import com.ityun.weixin.myapplication.view.AlbumPopwindow;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/22 0022.
 */

public class AlbumActivity extends BaseActivity {


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

    public int MY_PERMISSIONS_REQUEST_OPEN_CAMARE = 20;

    private AlbumAdapter adapter;

    private int REQUEST_CODE_PHONE_IMAGE = 22;

    private PopupWindow mPopupWindow;

    private AlbumPopwindow albumPopwindow;

    private boolean isShowTime;

    private Handler mHideHandler = new Handler();
    private Runnable mHide = new Runnable() {
        @Override
        public void run() {
            hideTime();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        ButterKnife.bind(this);
        adapter = new AlbumAdapter(this);
        picture_gridview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (PermissionUtil.readFile(this, MY_PERMISSIONS_REQUEST_OPEN_ALBUM) == 1) {
            initView();
        }
    }

    private void initView() {
        mediaFolderList = new ArrayList<>();
        localMediaList = new ArrayList<>();
        albumPopwindow = new AlbumPopwindow(this);
        albumPopwindow.setOnItemOnClick(new AlbumPopwindow.OnItemOnClick() {
            @Override
            public void onClick(int position) {
                sType = position;
                localMediaList.clear();
                localMediaList.addAll(mediaFolderList.get(sType).getImages());
                picture_file_name.setText(mediaFolderList.get(sType).getName());
                adapter.notifyDataSetChanged();
                mPopupWindow.dismiss();
            }
        });
        //获取本地所有图片
        new LocalMediaLoader(this, 0, false).loadAllImage(new LocalMediaLoader.LocalMediaLoadListener() {
            @Override
            public void loadComplete(List<LocalMediaFolder> folders) {
                localMediaList.clear();
                mediaFolderList.addAll(folders);
                LocalMedia media = new LocalMedia();
                media.setType(1);
                media.setLastUpdateAt(new Date().getTime()/1000);
                mediaFolderList.get(0).getImages().add(0, media);
                localMediaList.addAll(mediaFolderList.get(sType).getImages());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaFolderList.get(0).getImages() != null && mediaFolderList.get(0).getImages().size() > 1) {
                            picture_date.setText(DataUtils.longToString(mediaFolderList.get(0).getImages().get(1).getLastUpdateAt(), "yyyy-MM-dd"));
                        }
                        picture_file_name.setText(mediaFolderList.get(sType).getName());
                        adapter.setData(localMediaList);
                        mPopupWindow = albumPopwindow.create(mediaFolderList);
                    }
                });
            }
        });
        //设置下面文件名显示的背景高度
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
                changeTime();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                changeTime();
            }
        });


        picture_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (localMediaList.get(position).getType() == 1) {
                    //类型一 为拍照
                    if (PermissionUtil.openCamare(AlbumActivity.this, MY_PERMISSIONS_REQUEST_OPEN_CAMARE) == 1) {
                        PhotoUtil.getInstance().getImageFromPhone(AlbumActivity.this, REQUEST_CODE_PHONE_IMAGE);
                    }
                } else {
                    //其他的 返回头像
                    Intent intent = getIntent();
                    intent.putExtra("image", localMediaList.get(position).getPath());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    @OnClick(R.id.picture_file_name)
    public void PopShow() {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            int[] location = new int[2];
            picture_file_name.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            Display display = getWindowManager().getDefaultDisplay();
           int m= display.getHeight()-mPopupWindow.getHeight()-picture_file_name.getHeight();
            mPopupWindow.showAtLocation(picture_file_name, Gravity.NO_GRAVITY, 0,  m);
        }
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
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


    /**
     * 改变时间条显示的时间（显示图片列表中的第一个可见图片的时间）
     */
    private void changeTime() {
        int firstVisibleItem = getFirstVisibleItem();
        if (firstVisibleItem >= 0 && firstVisibleItem < localMediaList.size()) {
            String time =DataUtils.longToString(localMediaList.get(firstVisibleItem).getLastUpdateAt()*1000,"yyyy-MM-dd");
            picture_date.setText(time);
            showTime();
            mHideHandler.removeCallbacks(mHide);
            mHideHandler.postDelayed(mHide, 1500);
        }
    }

    private int getFirstVisibleItem() {
        return picture_gridview.getFirstVisiblePosition();
    }

    /**
     * 显示时间条
     */
    private void showTime() {
        if (!isShowTime) {
            ObjectAnimator.ofFloat(picture_date, "alpha", 0, 1).setDuration(300).start();
            isShowTime = true;
        }
    }

    /**
     * 隐藏时间条
     */
    private void hideTime() {
        if (isShowTime) {
            ObjectAnimator.ofFloat(picture_date, "alpha", 1, 0).setDuration(300).start();
            isShowTime = false;
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_OPEN_ALBUM) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //授权成功
                initView();
            } else {
                //授权失败
                Toast.makeText(this, R.string.error_album, Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == MY_PERMISSIONS_REQUEST_OPEN_CAMARE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //授权成功
                PhotoUtil.getInstance().getImageFromPhone(AlbumActivity.this, REQUEST_CODE_PHONE_IMAGE);
            } else {
                //授权失败
                Toast.makeText(this, R.string.error_camare_pe, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PHONE_IMAGE) {
            //拍照返回的数据
            if (resultCode == RESULT_OK) {
                Intent intent = getIntent();
                intent.putExtra("image", PhotoUtil.getInstance().getPath());
                setResult(RESULT_OK, intent);
                finish();
            } else {
                PhotoUtil.getInstance().deleteFile();
            }
        }
    }
}
