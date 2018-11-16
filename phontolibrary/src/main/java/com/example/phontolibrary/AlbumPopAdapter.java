package com.example.phontolibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

/**
 * @user xie
 * @date 2018/11/12 0012
 * @email 773675907@qq.com.
 */

public class AlbumPopAdapter extends BaseAdapter {

    private Context context;

    private int selectedPosition;

    private List<LocalMediaFolder> mediaFolderList;

    public AlbumPopAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<LocalMediaFolder> mediaFolderList) {
        this.mediaFolderList = mediaFolderList;
        notifyDataSetChanged();
    }

    public void setPostion(int postion) {
        this.selectedPosition = postion;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null != mediaFolderList ? mediaFolderList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mediaFolderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_album, null);
            holder = new Holder();
            holder.file_img = convertView.findViewById(R.id.item_album_first_img);
            holder.file_select = convertView.findViewById(R.id.item_album_first_img_selected);
            holder.file_name=convertView.findViewById(R.id.file_name);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.file_name.setText(mediaFolderList.get(position).getName());
        Glide.with(context).load(new File(mediaFolderList.get(position).getFirstImagePath())).into(holder.file_img);

        if (selectedPosition == position) {
            holder.file_select.setVisibility(View.VISIBLE);
        } else {
            holder.file_select.setVisibility(View.GONE);
        }

        return convertView;
    }

    class Holder {
        ImageView file_img;
        ImageView file_select;

        TextView file_name;
    }
}
