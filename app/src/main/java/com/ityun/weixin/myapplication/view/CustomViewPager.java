package com.ityun.weixin.myapplication.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2018/3/2 0002.
 */

public class CustomViewPager extends ViewPager {
    private boolean isCanScroll = true;
    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
// TODO Auto-generated constructor stub
    }
    public CustomViewPager(Context context) {
        super(context);
// TODO Auto-generated constructor stub
    }
    public void setCanScroll(boolean isCanScroll){
        this.isCanScroll = isCanScroll;
    }
    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
// TODO Auto-generated method stub
        if(isCanScroll){
            return super.onTouchEvent(arg0);
        }else{
            return false;
        }
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
// TODO Auto-generated method stub
        if(isCanScroll){
            return super.onInterceptTouchEvent(arg0);
        }else{
            return false;
        }
    }


    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }
    @Override
    public void setCurrentItem(int item) {

        super.setCurrentItem(item, false);
    }
}
