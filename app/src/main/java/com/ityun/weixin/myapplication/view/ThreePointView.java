package com.ityun.weixin.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.ityun.weixin.myapplication.R;
import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2018/2/13 0013.
 */

public class ThreePointView extends View {

    Paint paint_one;
    Paint paint_two;
    Paint paint_three;


    private  int width;

    private int height;


    public ThreePointView(Context context) {
        super(context);
        initView();
    }

    public ThreePointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ThreePointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public ThreePointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }


    private void  initView()
    {
        paint_one=new Paint();
        paint_one.setColor(getResources().getColor(R.color.main_color));
        paint_one.setStrokeWidth(1);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(width/2,height/2,10,paint_one);
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height= MeasureSpec.getSize(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void setVisibility(int visibility) {
        if(visibility==View.GONE)
        {

        }
        super.setVisibility(visibility);
    }
}
