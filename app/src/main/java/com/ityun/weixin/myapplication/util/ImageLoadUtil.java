package com.ityun.weixin.myapplication.util;

import android.content.Context;
import android.media.Image;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ityun.weixin.myapplication.base.App;

import java.io.File;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class ImageLoadUtil {

    Context context;
    public ImageLoadUtil(Context context)
    {
        this.context=context;
    }

   private static ImageLoadUtil imageLoadUtil;

    public static ImageLoadUtil getInstance(Context context)
    {
        if(imageLoadUtil==null)
            imageLoadUtil=new ImageLoadUtil(context);
        return  imageLoadUtil;
    }

    public   void getResouce(int resId, ImageView imageView)
    {
        Glide.with(App.context).load(resId).skipMemoryCache(true).into(imageView);
    }


    public    void  getFile(String path,ImageView imageView)
    {
        Glide.with(App.context).load(new File(path)).into(imageView);
    }


}
