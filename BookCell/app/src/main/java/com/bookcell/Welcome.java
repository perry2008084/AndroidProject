package com.bookcell;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;

public class Welcome extends Activity {
    private static final String TAG = Welcome.class.getSimpleName();
    private boolean m_bIsFirstStartup = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "Welcome onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        m_bIsFirstStartup = sharedPref.getBoolean(getString(R.string.isFirstTimeStartup), true);
        if (!m_bIsFirstStartup) {
            Intent intent = new Intent();
            intent.setClass(Welcome.this, homepage.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startClick(View v) {
        Intent intent = new Intent();
        intent.setClass(Welcome.this, homepage.class);
        startActivity(intent);
    }

    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume()");

        if (!m_bIsFirstStartup) {
            finish();
            Log.v(TAG, "onResume() call finish()");
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause()");

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        if (m_bIsFirstStartup) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(getString(R.string.isFirstTimeStartup), false);
            editor.commit();
        }

    }
}
