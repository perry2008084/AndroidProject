package com.bookcell;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by perry on 2014/12/18.
 */
public class BookDetail extends Activity{
    private static final String TAG = BookDetail.class.getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }
}
