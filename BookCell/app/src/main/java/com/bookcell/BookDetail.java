package com.bookcell;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by perry on 2014/12/18.
 */
public class BookDetail extends Activity{
    private static final String TAG = BookDetail.class.getSimpleName();

    DatabaseHandler db;

    private TextView tvBookTitle,tvBookPosition,tvBookAuthor,tvBookPub,tvBookDescribe;
    private Button btnSave;
    private EditText etBookPositionEdit;

    InputMethodManager manager;

    private static int m_nBookId = -1;

    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getActionBar().setIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        getWidget();

        // get the inputMethod manager
        manager  = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        Bundle bundle = this.getIntent().getBundleExtra("BOOK_DETAIL_INTENT");

        if (bundle != null) {
            m_nBookId = bundle.getInt("BOOK_DETAIL");
            if (m_nBookId != -1) {
                Referash_Data(m_nBookId);
            }
        }

        Bundle bundle_search = this.getIntent().getBundleExtra("SEARCH_INTENT");

        if (bundle_search != null) {
            m_nBookId = bundle_search.getInt("SEARCHED_BOOKID");
            if (m_nBookId != -1) {
                Referash_Data(m_nBookId);
            }
        }

    }

    public void getWidget() {
        tvBookTitle = (TextView)findViewById(R.id.tvBookTitleText);
        tvBookPosition = (TextView)findViewById(R.id.tvBookPositionText);
        tvBookAuthor = (TextView)findViewById(R.id.tvBookAuthorText);
        tvBookPub = (TextView)findViewById(R.id.tvBookPubText);
        tvBookDescribe = (TextView)findViewById(R.id.tvBookDescribeText);

        btnSave = (Button)findViewById(R.id.bookDetailSave);

        etBookPositionEdit = (EditText)findViewById(R.id.etBookPositionEditText);

        etBookPositionEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                applyChange();
                return false;
            }
        });
    }

    public void Referash_Data(int bookId) {
        if (bookId < 0) {
            toast("The Item you clicked is Invalid.");
            return;
        }

        Log.d(TAG, "Referash_Data() bookId: " + bookId);
        db = new DatabaseHandler(this);
        BookInfo bookInfo = db.Get_BookInfo(bookId);

        int tidno = bookInfo.getID();
        String isbn = bookInfo.getBookISBN();
        String name = bookInfo.getName();
        String author = bookInfo.getBookAuthor();
        String pub = bookInfo.getBookPub();
        String position = bookInfo.getBookPosition();
        String describe = bookInfo.getDescribe();

        tvBookTitle.setText(name);
        tvBookPosition.setText(position);
        tvBookAuthor.setText(author);
        tvBookPub.setText(pub);
        tvBookDescribe.setText(describe);

        db.close();
    }

    public void onBtnBookDetailSaveClick(View view) {
        applyChange();
    }

    public void applyChange() {
        String bookPosTmp = etBookPositionEdit.getText().toString();

        if (bookPosTmp.isEmpty()) {
            toast(getString(R.string.inputEmpty));
            manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            return;
        }

        if (m_nBookId != -1) {
            db = new DatabaseHandler(this);
            BookInfo bookInfo = db.Get_BookInfo(m_nBookId);

            bookInfo.setBookPosition(bookPosTmp);

            db.Update_BookInfo(bookInfo);
            db.close();
        }

        Referash_Data(m_nBookId);
        etBookPositionEdit.getText().clear();
        manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

        @Override  public boolean onTouchEvent(MotionEvent event) {
    // TODO Auto-generated method stub
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    private void toast( String text ) {
        Toast.makeText(getApplicationContext(),
                text, Toast.LENGTH_SHORT)
                .show();
    }
}
