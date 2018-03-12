package com.ityun.weixin.myapplication.ui.friend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.bean.NewFriend;

import java.util.List;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class NewFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Context context;

    public List<NewFriend> mlist;

    public  int TOP_TYPE=0;

    public int ITEM_TYPE=1;

    public NewFriendAdapter(Context context)
    {
        this.context=context;
    }

    public  void setData(List<NewFriend> mlist)
    {
        this.mlist=mlist;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if(viewType==TOP_TYPE)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.item_new_friend_top,parent,false);
            holder=new TopHolder(view);
        }
        else
        {
            View view= LayoutInflater.from(context).inflate(R.layout.item_new_friend_top,parent,false);
            holder=new ItemHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
        {
            return  TOP_TYPE;
        }
        return ITEM_TYPE;
    }

    @Override
    public int getItemCount() {
        return null!=mlist?(mlist.size()+1):1;
    }

    class TopHolder  extends  RecyclerView.ViewHolder
    {
        RelativeLayout new_friend_search_rl;
        public TopHolder(View itemView) {
            super(itemView);
            new_friend_search_rl=itemView.findViewById(R.id.new_friend_search_rl);
        }
    }

    class ItemHolder  extends  RecyclerView.ViewHolder
    {

        public ItemHolder(View itemView) {
            super(itemView);
        }
    }


}
