package com.example.phontolibrary;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 * @user xie
 * @date 2018/11/9 0009
 * @email 773675907@qq.com.
 */

public class PreviewActivity extends AppCompatActivity {

    private List<LocalMedia> mlist;

    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mlist = (List<LocalMedia>) getIntent().getSerializableExtra("imgs");
        position = getIntent().getIntExtra("position", 0);
    }
}
