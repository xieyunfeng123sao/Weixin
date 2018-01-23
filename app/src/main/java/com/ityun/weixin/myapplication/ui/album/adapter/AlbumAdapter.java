package com.ityun.weixin.myapplication.ui.album.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.util.ImageLoadUtil;
import com.ityun.weixin.myapplication.util.addpic.LocalMedia;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;

import static com.ityun.weixin.myapplication.base.App.context;

/**
 * Created by Administrator on 2018/1/23 0023.
 */

public class AlbumAdapter extends BaseAdapter {

    Context context;
    List<LocalMedia> mlist;

    public AlbumAdapter(Context context) {
        this.context=context;
    }

    public void setData(List<LocalMedia> mlist) {
        this.mlist=mlist;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        int size=null!=mlist?mlist.size():0;
        return  size;
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.item_add_user_img,null);
            holder=new Holder();
            holder.imageView=convertView.findViewById(R.id.item_img);
            convertView.setTag(holder);
        }
        else
        {
            holder= (Holder) convertView.getTag();
        }
        Glide.with(context).load(new File(mlist.get(position).getPath())).thumbnail(0.2f).into(holder.imageView);
        return convertView;
    }

    class Holder
    {
        ImageView imageView;
    }
}
