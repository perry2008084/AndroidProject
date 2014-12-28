package com.bookcell;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextWatcher;
import android.widget.Toast;


public class search extends Activity implements View.OnClickListener{
    public static final String TAG = search.class.getSimpleName();

    DatabaseHandler db;
    private Button btnSearchItem1,btnSearchItem2,btnSearchItem3;
    private EditText etSearch;

    private int m_nBookId = -1;
    String valid_title = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        getWidget();
    }

    public void getWidget()
    {
        btnSearchItem1 = (Button)findViewById(R.id.BtnSearchItem1);
        btnSearchItem2 = (Button)findViewById(R.id.BtnSearchItem2);
        btnSearchItem3 = (Button)findViewById(R.id.BtnSearchItem3);

        btnSearchItem1.setOnClickListener(this);
        btnSearchItem2.setOnClickListener(this);
        btnSearchItem3.setOnClickListener(this);

        etSearch = (EditText)findViewById(R.id.et_searchText);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                Log.d(TAG, "onTextChanged()");

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
                Log.d(TAG, "beforeTextChanged()");

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                Log.d(TAG, "EdtiText etSearch afterTextChanged()");
                Is_Valid_Title(etSearch);

                Log.d(TAG, "EdtiText etSearch afterTextChanged() valid_title: " + valid_title);

            }
        });
    }

    public void Is_Valid_Title(EditText edt) {
        if (edt.getText().toString() == null) {
            edt.setError("无效的书名");
            valid_title = null;
        }else {
            valid_title = edt.getText().toString();
        }
    }


    public void onClick(View v) {
        String strBookTitle = "";

        switch (v.getId()) {
            case R.id.BtnSearchItem1:
                strBookTitle = btnSearchItem1.getText().toString();

                break;
            case R.id.BtnSearchItem2:
                strBookTitle = btnSearchItem2.getText().toString();

                break;
            case R.id.BtnSearchItem3:
                strBookTitle = btnSearchItem3.getText().toString();

                break;
            default:
                break;
        }

        int nBookId = getBookIdFromDb(strBookTitle);
        showBookDetail(nBookId);
    }

    public int getBookIdFromDb(String strBookTitle) {
        int nBookId = -1;
        db = new DatabaseHandler(this);

        if (!strBookTitle.isEmpty()) {
            nBookId = db.get_BookInfoId_With_Title(strBookTitle);
            Log.d(TAG, "getBookIdFromDb() nBookId: " + nBookId);
        }

        db.close();

        return nBookId;
    }

    public void showBookDetail(int nBookId){
        Intent intent = new Intent();
        intent.setClass(search.this, BookDetail.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putInt("SEARCHED_BOOKID", nBookId);
        Log.d(TAG, "search clicked nBookId: " + nBookId);
        intent.putExtra("SEARCH_INTENT", bundle);
        startActivity(intent);
    }

    public void onBtnSearchConfirmClick(View v) {
        Log.d(TAG, "onBtnSearchConfirmClick() valid_title: " + valid_title);
        if (valid_title != null && !valid_title.isEmpty() && getBookIdFromDb(valid_title) != -1) {
            btnSearchItem1.setText(valid_title);
            btnSearchItem1.setVisibility(View.VISIBLE);
        }
        else {
            toast(getString(R.string.searchConfirm));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
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
        else if(item.getItemId() == android.R.id.home) {
            //Up按钮的点击事件
            Intent intent = new Intent();
            intent.setClass(search.this, homepage.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void Reset_Text() {
        etSearch.getText().clear();
    }

    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause()");

        Reset_Text();
        btnSearchItem1.setText("");
        btnSearchItem1.setVisibility(View.GONE);
    }

    private void toast( String text )
    {
        Toast.makeText(getApplicationContext(),
                text, Toast.LENGTH_SHORT)
                .show();
    }
}
