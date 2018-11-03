package com.example.phontolibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import java.util.List;


/**
 * Created by Administrator on 2018/1/24 0024.
 */

public class AlbumPopwindow {

    private Context context;

    private PopupWindow mPopupWindow;

    private ListView album_file_list;


    private AlbumPopAdapter adapter;

    OnItemOnClick onItemOnClick;


    public AlbumPopwindow(Context context) {
        this.context = context;
    }


    public PopupWindow create(List<LocalMediaFolder> mediaFolderList) {
        View popupView = LayoutInflater.from(context).inflate(R.layout.layout_popupwindow, null);
        album_file_list = popupView.findViewById(R.id.album_file_list);
        adapter = new AlbumPopAdapter(context);
        album_file_list.setAdapter(adapter);
        adapter.setData(mediaFolderList);
        mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(context, 450), true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));
        mPopupWindow.getContentView().setFocusableInTouchMode(false);
        mPopupWindow.getContentView().setFocusable(false);
        mPopupWindow.setAnimationStyle(R.style.showPopupAnimation);
        mPopupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (mPopupWindow != null && mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
        album_file_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setPostion(position);
                if (onItemOnClick != null) {
                    onItemOnClick.onClick(position);
                }
            }
        });
        return mPopupWindow;
    }


    public PopupWindow setOnItemOnClick(OnItemOnClick onItemOnClick) {
        this.onItemOnClick = onItemOnClick;
        return mPopupWindow;
    }


    public interface OnItemOnClick {
        void onClick(int position);
    }
}
