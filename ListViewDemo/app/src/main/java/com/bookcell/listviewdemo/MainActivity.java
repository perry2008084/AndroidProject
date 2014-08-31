package com.bookcell.listviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.SimpleAdapter;

import com.bookcell.listviewdemo.MyAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends Activity {

    String [] myStringArray = {"笑傲江湖", "连城诀", "天龙八部"};

    private TextView describe;
    private ListView listView;
    private String[] userName = {"User 1","User 2","User 3","User 4"};
    private String[] userDescribe = {"China","Japan","Norge","Sweden"};
    private int[] userIcon = { R.drawable.icon_1, R.drawable.icon_2,R.drawable.icon_3, R.drawable.icon_4 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initAll_arrayAdapter();
        //initAll_simpleAdapter();
        initAll_myAdapter();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    // Create a message handling object as an anonymous class.
    private OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            // Do something in response to the click
            Toast.makeText(getApplicationContext(), myStringArray[position], Toast.LENGTH_SHORT).show();
        }
    };

    public void initAll_arrayAdapter(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, myStringArray);

        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(mMessageClickedHandler);
    }

    public void initAll_simpleAdapter(){
        describe = (TextView)findViewById(R.id.list_view_describe);
        listView = (ListView)findViewById(R.id.listview);
        describe.setText("使用了SimpleAdapter填充的ListView");
        List<Map<String, Object>> dateLists = new ArrayList<Map<String, Object>>();
        for(int i = 0; i < userName.length ; i++)
        { Map<String, Object> dateItem = new HashMap<String, Object>();
            dateItem.put("icon",userIcon[i]);
            dateItem.put("name",userName[i]);
            dateItem.put("describe",userDescribe[i]);
            dateLists.add(dateItem);
        }
        String[] keysOfMap = {"icon","name","describe"};
        int [] keyOfLayout = { R.id.item_simple_adapter_icon, R.id.item_simple_adapter_name, R.id.item_simple_adapter_describe};
        SimpleAdapter simpleAdapter = new SimpleAdapter (this,dateLists,R.layout.simple_list_item_1,keysOfMap,keyOfLayout);
        listView.setAdapter(simpleAdapter);
    }

    public void initAll_myAdapter() {
        describe = (TextView)findViewById(R.id.list_view_describe);
        listView = (ListView)findViewById(R.id.listview);
        describe.setText("user myAdapter fill ListView");
        ArrayList<com.bookcell.listviewdemo.BookInfo> bookInfoArrayList = new ArrayList<com.bookcell.listviewdemo.BookInfo>();
        bookInfoArrayList.add(new com.bookcell.listviewdemo.BookInfo("阿潘", "China", R.drawable.icon_1));
        bookInfoArrayList.add(new com.bookcell.listviewdemo.BookInfo("鱼仔", "China", R.drawable.icon_2));
        bookInfoArrayList.add(new com.bookcell.listviewdemo.BookInfo("Totoro", "Japan", R.drawable.icon_3));
        MyAdapter myAdapter = new MyAdapter(this,bookInfoArrayList);
        listView.setAdapter(myAdapter);

    }
}
