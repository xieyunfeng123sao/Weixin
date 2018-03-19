package com.ityun.weixin.myapplication.ui.fragment.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.bean.Friend;
import com.ityun.weixin.myapplication.ui.friend.NewFriendActivity;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2018/3/2 0002.
 */

public class FriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;

    List<Friend> mlist;
    private LayoutInflater mLayoutInflater;

    public enum ITEM_TYPE {
        HEADVIEW,
        FOOTVIEW,
        SIDERVIEW,
        FRIENDVIEW
    }

    public FriendAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<Friend> mlist) {
        if(mlist!=null)
        {
            Collections.sort(mlist, new Comparator<Friend>() {
                @Override
                public int compare(Friend o1, Friend o2) {
                    return o1.getPinyin().compareTo(o2.getPinyin());
                }
            });
        }
        this.mlist = mlist;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == ITEM_TYPE.HEADVIEW.ordinal()) {
            return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.item_friend_header, parent, false));
        } else if (viewType == ITEM_TYPE.FOOTVIEW.ordinal()) {
            return new FooterViewHolder(mLayoutInflater.inflate(R.layout.item_friend_footer, parent, false));
        } else {
            return new FriendViewHolder(mLayoutInflater.inflate(R.layout.item_friend, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            ((HeaderViewHolder) holder).item_friend_newfriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NewFriendActivity.class);
                    context.startActivity(intent);
                }
            });

            ((HeaderViewHolder) holder).item_friend_groupim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            ((HeaderViewHolder) holder).item_friend_publicnum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        else if(position!=((null != mlist ? (mlist.size() + 2) : 2)-1))
        {
            ((FriendViewHolder) holder).item_friend_name.setText(mlist.get(position-1).getFriendUser().getNickname());
        }
    }

    @Override
    public int getItemCount() {
        return null != mlist ? (mlist.size() + 2) : 2;
    }

    @Override
    public int getItemViewType(int position) {
        int max = null != mlist ? mlist.size() : 0;
        if (position == 0) {
            return ITEM_TYPE.HEADVIEW.ordinal();
        } else if (position == (max + 1)) {
            return ITEM_TYPE.FOOTVIEW.ordinal();
        } else {
            return ITEM_TYPE.SIDERVIEW.ordinal();
        }
    }


    class HeaderViewHolder extends RecyclerView.ViewHolder {
        LinearLayout item_friend_newfriend;

        LinearLayout item_friend_publicnum;

        LinearLayout item_friend_groupim;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            item_friend_newfriend = itemView.findViewById(R.id.item_friend_newfriend);
            item_friend_publicnum = itemView.findViewById(R.id.item_friend_publicnum);
            item_friend_groupim = itemView.findViewById(R.id.item_friend_groupim);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    class SiderViewHolder extends RecyclerView.ViewHolder {
        public SiderViewHolder(View itemView) {
            super(itemView);
        }
    }

    class FriendViewHolder extends RecyclerView.ViewHolder {
        ImageView item_friend_img;
        TextView item_friend_name;

        public FriendViewHolder(View itemView) {
            super(itemView);
            item_friend_img=itemView.findViewById(R.id.item_friend_img);
            item_friend_name=itemView.findViewById(R.id.item_friend_name);
        }
    }

}
