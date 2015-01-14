package com.bookcell;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by perry on 2015/1/6.
 */
public class feedback extends Activity {
    private static final String TAG = feedback.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "feedback onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        // 返回箭头（默认不显示）
        getActionBar().setDisplayHomeAsUpEnabled(false);
        // 左侧图标点击事件使能
        getActionBar().setHomeButtonEnabled(true);
        // 使左上角图标(系统)是否显示
        getActionBar().setDisplayShowHomeEnabled(false);
        // 显示标题
        getActionBar().setDisplayShowTitleEnabled(false);
        //显示自定义视图
        getActionBar().setDisplayShowCustomEnabled(true);
        View actionbarLayout = LayoutInflater.from(this).inflate(
                R.layout.actionbar, null);
        getActionBar().setCustomView(actionbarLayout);
    }

    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume()");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause()");
    }
}
