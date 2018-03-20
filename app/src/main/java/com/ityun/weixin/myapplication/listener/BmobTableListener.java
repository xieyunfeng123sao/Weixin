package com.ityun.weixin.myapplication.listener;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public interface BmobTableListener<T> {

    void  onSucess(T object);

    void onFail(BmobException e);
}
