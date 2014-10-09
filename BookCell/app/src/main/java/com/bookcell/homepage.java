package com.bookcell;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class homepage extends Activity {

    // for test
    private TextView describe;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        initAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homepage, menu);
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

    public void onBtnScanClick(View view) {
        Intent intent = new Intent();
        intent.setClass(homepage.this, scan.class);
        startActivity(intent);
    }

    public void onBtnSearchClick(View view) {
        Intent intent = new Intent();
        intent.setClass(homepage.this, search.class);
        startActivity(intent);
    }

    public void initAll() {
        describe = (TextView)findViewById(R.id.list_view_describe);
        listView = (ListView)findViewById(R.id.listView_homepage);
        describe.setText("书格清单");
        ArrayList<com.bookcell.BookInfo> bookInfoArrayList = new ArrayList<com.bookcell.BookInfo>();
        bookInfoArrayList.add(new com.bookcell.BookInfo("阿潘", "China", R.drawable.icon_1));
        bookInfoArrayList.add(new com.bookcell.BookInfo("鱼仔", "China", R.drawable.icon_2));
        bookInfoArrayList.add(new com.bookcell.BookInfo("Totoro", "Japan", R.drawable.icon_3));
        BookAdapter myAdapter = new BookAdapter(this,bookInfoArrayList);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(mMessageClickedHandler);
    }

    // Create a message handling object as an anonymous class.
    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            // Do something in response to the click
            TextView textView = (TextView) v.findViewById( R.id.tv_name );
            toast( (String) textView.getText());
        }
    };

    private void toast( String text )
    {
        Toast.makeText( getApplicationContext(),
                String.format( "Item clicked: %s", text ), Toast.LENGTH_SHORT )
                .show();
    }
}
