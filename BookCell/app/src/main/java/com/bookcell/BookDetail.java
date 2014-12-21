package com.bookcell;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    private Button btnEdit, btnSave;
    private EditText etBookPositionEdit;

    private static int m_nBookId = -1;

    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getWidget();

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

        btnEdit = (Button)findViewById(R.id.bookDetailEdit);
        btnSave = (Button)findViewById(R.id.bookDetailSave);

        etBookPositionEdit = (EditText)findViewById(R.id.etBookPositionEditText);
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
        etBookPositionEdit.setText(position);
        tvBookAuthor.setText(author);
        tvBookPub.setText(pub);
        tvBookDescribe.setText(describe);

        db.close();
    }

    public void onBtnBookDetailEditClick(View view) {
        btnEdit.setVisibility(View.GONE);
        btnSave.setVisibility(View.VISIBLE);
        etBookPositionEdit.setVisibility(View.VISIBLE);
        tvBookPosition.setVisibility(View.GONE);

        }

    public void onBtnBookDetailSaveClick(View view) {
        btnEdit.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.GONE);
        etBookPositionEdit.setVisibility(View.GONE);
        tvBookPosition.setVisibility(View.VISIBLE);

        String bookPosTmp = etBookPositionEdit.getText().toString();

        if (m_nBookId != -1) {
            db = new DatabaseHandler(this);
            BookInfo bookInfo = db.Get_BookInfo(m_nBookId);

            bookInfo.setBookPosition(bookPosTmp);

            db.Update_BookInfo(bookInfo);
            db.close();
        }

        Referash_Data(m_nBookId);
    }

    private void toast( String text )
    {
        Toast.makeText(getApplicationContext(),
                text, Toast.LENGTH_SHORT)
                .show();
    }
}
