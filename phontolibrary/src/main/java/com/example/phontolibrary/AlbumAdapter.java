package com.example.phontolibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

/**
 * @user xie
 * @date 2018/11/12 0012
 * @email 773675907@qq.com.
 */

public class AlbumAdapter extends BaseAdapter {

    Context context;

    List<LocalMedia> mlist;

    private int count;

    public AlbumAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<LocalMedia> mlist) {
        this.mlist = mlist;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        int size = null != mlist ? mlist.size() : 0;
        return size;
    }

    public void setCount(int count) {
        this.count = count;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_add_img, null);
            holder = new Holder();
            holder.imageView = convertView.findViewById(R.id.item_img);
            holder.checkBox = convertView.findViewById(R.id.item_check_img);
            holder.relativeLayout = convertView.findViewById(R.id.item_img_select_rl);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.imageView.setPadding(0, 0, 0, 0);
        holder.checkBox.setChecked(mlist.get(position).getIsChecked());
        Glide.with(context).load(new File(mlist.get(position).getPath())).thumbnail(0.2f).into(holder.imageView);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count >= 9) {
                    Toast.makeText(context, "最多选择9张图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                mlist.get(position).setChecked(!mlist.get(position).getIsChecked());
                if (onCheckListener != null)
                    onCheckListener.onClick(position);
            }
        });
        return convertView;
    }

    class Holder {
        ImageView imageView;

        CheckBox checkBox;

        RelativeLayout relativeLayout;
    }

    OnCheckListener onCheckListener;

    public void setOnItemCheckListener(OnCheckListener onCheckListener) {
        this.onCheckListener = onCheckListener;
    }


    public interface OnCheckListener {
        void onClick(int position);
    }
}
