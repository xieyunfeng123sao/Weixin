package com.ityun.weixin.myapplication.ui.friend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.bean.NewFriend;
import com.ityun.weixin.myapplication.bean.UserInfo;
import com.ityun.weixin.myapplication.im.IMModel;
import com.ityun.weixin.myapplication.util.ImageLoadUtil;

import java.util.List;

import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class NewFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Context context;

    public List<NewFriend> mlist;

    public int TOP_TYPE = 0;

    public int ITEM_TYPE = 1;

    private OnClickListener onClickListener;

    public NewFriendAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<NewFriend> mlist) {
        this.mlist = mlist;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == TOP_TYPE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_new_friend_top, parent, false);
            holder = new TopHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_new_friend, parent, false);
            holder = new ItemHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position == 0) {
            ((TopHolder) holder).new_friend_search_rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else {
            ImageLoadUtil.getInstance().loadUrl(mlist.get(position - 1).getAvatar(), ((ItemHolder) holder).new_friend_img);
            ((ItemHolder) holder).new_friend_name.setText(mlist.get(position - 1).getName());
            ((ItemHolder) holder).new_friend_msg.setText(mlist.get(position - 1).getMsg());
            if (mlist.get(position - 1).getStatus() == 2) {
                ((ItemHolder) holder).new_friend_msg_agree.setEnabled(false);
                ((ItemHolder) holder).new_friend_msg_agree.setText("已添加");
                ((ItemHolder) holder).new_friend_msg_agree.setBackgroundColor(context.getResources().getColor(R.color.white));
                ((ItemHolder) holder).new_friend_msg_agree.setTextColor(context.getResources().getColor(R.color.txt_color));
            } else {
                ((ItemHolder) holder).new_friend_msg_agree.setEnabled(true);
                ((ItemHolder) holder).new_friend_msg_agree.setText("接受");
                ((ItemHolder) holder).new_friend_msg_agree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClickListener != null) {
                            onClickListener.onClick(position-1);
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TOP_TYPE;
        }
        return ITEM_TYPE;
    }

    @Override
    public int getItemCount() {
        return null != mlist ? (mlist.size() + 1) : 1;
    }

    class TopHolder extends RecyclerView.ViewHolder {
        RelativeLayout new_friend_search_rl;

        public TopHolder(View itemView) {
            super(itemView);
            new_friend_search_rl = itemView.findViewById(R.id.new_friend_search_rl);
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        ImageView new_friend_img;

        TextView new_friend_name;

        TextView new_friend_msg;

        TextView new_friend_msg_agree;

        public ItemHolder(View itemView) {
            super(itemView);
            new_friend_img = itemView.findViewById(R.id.new_friend_img);
            new_friend_name = itemView.findViewById(R.id.new_friend_name);
            new_friend_msg = itemView.findViewById(R.id.new_friend_msg);
            new_friend_msg_agree = itemView.findViewById(R.id.new_friend_msg_agree);
        }
    }

    public void setOnItemAgreeClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    public interface OnClickListener {
        void onClick(int  position);
    }


}
