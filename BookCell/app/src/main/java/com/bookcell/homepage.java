package com.bookcell;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bookcell.AsyncImageLoader.ImageCallback;

public class homepage extends Activity implements View.OnClickListener {
    private static final String TAG = homepage.class.getSimpleName();

    private ListView lv_main_books;
    private LinearLayout ll_loading;
    private Button cancel,delete;
    private TextView txtcount;
    private List<BookInfo> list;
    private List<BookInfo> selectid;
    private RelativeLayout layout;
    private SubjectListAdapter adapter;
    DatabaseHandler db;

    private boolean isScrolling = false;
    private boolean isloading = false;
    private boolean isMulChoice = false; // 是否为多选

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        lv_main_books = (ListView) this.findViewById(R.id.listView_homepage);
        ll_loading = (LinearLayout) this.findViewById(R.id.ll_main_progress);
        layout = (RelativeLayout) this.findViewById(R.id.relative);
        txtcount = (TextView) this.findViewById(R.id.txtcount);
        cancel = (Button)findViewById(R.id.cancle);
        delete = (Button)findViewById(R.id.delete);
        cancel.setOnClickListener(this);
        delete.setOnClickListener(this);

        list = new ArrayList<BookInfo>();
        selectid = new ArrayList<BookInfo>();

        Log.v(TAG, "onCreate() Set_Referash_Data()");
        Set_Referash_Data();

        lv_main_books.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        isScrolling = true;
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        isScrolling = false;
                        int startindex = lv_main_books.getFirstVisiblePosition();
                        int count = lv_main_books.getChildCount();
                        for (int i = 0; i < count; i++) {
                            int currentpostion = startindex + i;
                            final BookInfo book = (BookInfo) lv_main_books
                                    .getItemAtPosition(currentpostion);
                            final View viewchildren = lv_main_books.getChildAt(i);
                            ImageView iv_icon = (ImageView) viewchildren.findViewById(R.id.iv_icon);
                            Drawable drawable = NetUtil.asyncImageLoader.loadDrawable(book.getBookPicturePath(),
                                    new ImageCallback() {
                                        public void imageLoaded(Drawable imageDrawable,
                                                                String imageUrl) {
                                            ImageView imageViewByTag = (ImageView) lv_main_books
                                                    .findViewWithTag(imageUrl);
                                            if (imageViewByTag != null) {
                                                imageViewByTag.setImageDrawable(imageDrawable);
                                            }
                                        }
                                    });

                            if (drawable != null) {
                                iv_icon.setImageDrawable(drawable);
                            } else {
                                iv_icon.setImageResource(R.drawable.ic_launcher);
                            }
                        }
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        isScrolling = true;
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (totalItemCount <= 0){
                    return;
                }
                if(firstVisibleItem+visibleItemCount ==totalItemCount ){
                    if(isloading){
                        return;
                    }
                    new AsyncTask<Void, String, List<BookInfo>>() {
                        protected List<BookInfo> doInBackground(Void... params) {
                            List<BookInfo> listNewBooks = null;
                            try {
                                //listNewBooks = NewBookDao.getBooksMessage("1231576");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return listNewBooks;
                        }

                        protected void onPreExecute() {
                           // ll_loading.setVisibility(View.VISIBLE);
                           // isloading = true;
                            super.onPreExecute();
                        }

                        protected void onPostExecute(List<BookInfo> result) {
                            /*
                            list.addAll(result);
                            ll_loading.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();

                            isloading = false;
                            super.onPostExecute(result);
                            */
                            isloading = false;
                        }

                        protected void onProgressUpdate(String... values) {
                            Toast.makeText(homepage.this, values[0], Toast.LENGTH_SHORT)
                                    .show();
                            super.onProgressUpdate(values);
                        }

                    }.execute();
                }

            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancle:
                isMulChoice = false;
                selectid.clear();
                Set_Referash_Data();
                layout.setVisibility(View.INVISIBLE);
                break;
            case R.id.delete:
                isMulChoice = false;
                for (int i = 0; i < selectid.size(); i++) {
                    for (int j = 0; j < list.size(); j++) {
                        if (selectid.get(i).getID() == list.get(j).getID()) {

                            // delete from database
                            db.Delete_BookInfo(list.get(j).getID());
                            list.remove(j);
                        }
                    }
                }
                selectid.clear();
                Set_Referash_Data();
                layout.setVisibility(View.INVISIBLE);
            default:
                break;
        }
    }

    private void getData(final String isbnStr) {
        Log.v(TAG, "getData()");
        new AsyncTask<Void, String, List<BookInfo>>() {
            protected List<BookInfo> doInBackground(Void... params) {
                List<BookInfo> listNewBooks = null;
                try {
                    Log.v(TAG, "NewBookDao.getBooksMessage() urlStr: " + isbnStr);
                    listNewBooks = NewBookDao.getBooksMessage(isbnStr);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "getData() doInBackground() listNewBooks: " + listNewBooks);
                return listNewBooks;
            }

            protected void onPreExecute() {
                ll_loading.setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            protected void onPostExecute(List<BookInfo> result) {
                /*
                list=result;
                ll_loading.setVisibility(View.GONE);
                lv_main_books.setAdapter(adapter);
                //System.out.println("һ����" + list.size() + "����");
                super.onPostExecute(result);
                */

                Log.d(TAG, "getData() result: " + result);
                if (result != null) {
                    Log.d(TAG, "getData() call onPostExecute() result.size(): " + result.size());
                    if (result.size() > 0) {
                        ll_loading.setVisibility(View.GONE);

                        // check only one book in database with the same ISBN
                        String bookTmpIsbn = result.get(0).getBookISBN();
                        Log.v(TAG, "getData() bookTmpIsbn" + bookTmpIsbn);
                        boolean isBookNotExisted = db.CheckBookCanAdd(bookTmpIsbn);
                        Log.v(TAG, "getData() isBookExisted: " + isBookNotExisted);
                        if (!isBookNotExisted) {
                            list.addAll(result);

                            Log.i(TAG, "getData() call add_BookInfo from db");
                            db.Add_BookInfo(result.get(0));
                            String Toast_msg = "Data inserted successfully";
                            toast(Toast_msg);

                            Set_Referash_Data();
                        }
                        else {
                            toast(getString(R.string.bookAlreadyInDB));
                        }
                    }
                    result.clear();
                }
                else {
                    toast(getString(R.string.networkDisconnet));
                }
                super.onPostExecute(result);
            }

            protected void onProgressUpdate(String... values) {
                Toast.makeText(homepage.this, values[0], Toast.LENGTH_SHORT)
                        .show();
                super.onProgressUpdate(values);
            }

        }.execute();
    }
    private class SubjectListAdapter extends BaseAdapter {
        private HashMap<Integer, View> mView;
        private HashMap<Integer, Integer> visiblecheck;  // 记录是否显示checkbox
        private HashMap<Integer, Boolean> ischeck;

        public SubjectListAdapter()
        {
            visiblecheck = new HashMap<Integer, Integer>();
            ischeck      = new HashMap<Integer, Boolean>();

            if (isMulChoice) {
                for(int i = 0; i < list.size(); i++) {
                    ischeck.put(i, false);
                    visiblecheck.put(i, CheckBox.VISIBLE);
                }
            }
            else {
                for(int i = 0; i < list.size(); i++) {
                    ischeck.put(i, false);
                    visiblecheck.put(i, CheckBox.INVISIBLE);
                }
            }
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Log.v(TAG, "getView() position: " + position);
            //View view = mView.get(position);
            View view = null;
            ViewCache viewCache;
            ViewHolder viewHolder = null;
            if (convertView == null) {
                Log.v(TAG, "getView() convertView null.");
                viewHolder = new ViewHolder();
                view = View.inflate(homepage.this, R.layout.main_item, null);
                viewCache = new ViewCache(view);
                view.setTag(R.id.tag_first,viewCache);
                viewHolder.tv_name = (TextView) view.findViewById(R.id.tv_name);
                viewHolder.tv_message = (TextView) view.findViewById(R.id.tv_message);
                viewHolder.tv_synopsis = (TextView) view.findViewById(R.id.tv_synopsis);
                viewHolder._checkbox = (CheckBox) view.findViewById(R.id.check);
                view.setTag(R.id.tag_second,viewHolder);

               // mView.put(position, view);
            } else {
                Log.v(TAG, "getView() convertView not null.");
                view = convertView;
                viewHolder = (ViewHolder) view.getTag(R.id.tag_second);
                viewCache = (ViewCache) view.getTag(R.id.tag_first);
            }

            final CheckBox ceb = viewHolder._checkbox;
            Log.w(TAG, "getView() position: " + position + " ischeck.size(): " + ischeck.size() + " ischeck.get(position): " + ischeck.get(position));
            if (position < ischeck.size()) {
                Log.d(TAG, "ceb.setChecked(): " + ischeck.get(position));
                ceb.setChecked(ischeck.get(position));
            }
            Log.w(TAG, "getView() position: " + position + " visiblecheck.size(): " + visiblecheck.size() + " visiblecheck.get(position): " + visiblecheck.get(position));
            if (position < visiblecheck.size()) {
                ceb.setVisibility(visiblecheck.get(position));
            }

            view.setOnLongClickListener(new Onlongclick());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isMulChoice) {
                        if(ceb.isChecked()) {
                            Log.d(TAG, "ceb.setChecked(): " + false);
                            ceb.setChecked(false);
                            ischeck.put(position,false);
                            selectid.remove(list.get(position));
                        }
                        else{
                            Log.d(TAG, "ceb.setChecked(): " + true);
                            ceb.setChecked(true);
                            ischeck.put(position,true);
                            selectid.add(list.get(position));
                        }

                        txtcount.setText( "共计 " + selectid.size() + " 项");
                    }
                    else {
                        //toast("点击了"+list.get(position));
                        int clickedBookId = -1;
                        Intent intent = new Intent();
                        intent.setClass(homepage.this, BookDetail.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_NEW_TASK);

                        Bundle bundle = new Bundle();
                        if (list.get(position) != null) {
                            clickedBookId = list.get(position).getID();
                        }
                        bundle.putInt("BOOK_DETAIL", clickedBookId);
                        Log.d(TAG, "Item clicked, clickedBookId: " + clickedBookId);
                        intent.putExtra("BOOK_DETAIL_INTENT", bundle);
                        startActivity(intent);
                    }
                }
            });

            BookInfo newBook = list.get(position);
            viewHolder.tv_name.setText(newBook.getName());
            viewHolder.tv_message.setText(newBook.getDescribe());
           // viewHolder.tv_synopsis.setText(newBook.getBookSynopsis());
            String imgUrl = newBook.getBookPicturePath();
            ImageView imgBook = viewCache.getImageView();
            imgBook.setTag(imgUrl);
            if(isScrolling){
                imgBook.setImageResource(R.drawable.ic_launcher);
            }else{
//                Drawable drawable = NetUtil.asyncImageLoader.loadDrawable(imgUrl,
//                        new ImageCallback() {
//                            public void imageLoaded(Drawable imageDrawable,
//                                                    String imageUrl) {
//                                ImageView imageViewByTag = (ImageView) lv_main_books
//                                        .findViewWithTag(imageUrl);
//                                if (imageViewByTag != null) {
//                                    imageViewByTag.setImageDrawable(imageDrawable);
//                                }
//                            }
//                        });
//
//                if (drawable != null) {
//                    imgBook.setImageDrawable(drawable);
//                } else {
//                    imgBook.setImageResource(R.drawable.ic_launcher);
//                }

                imgBook.setImageResource(R.drawable.ic_launcher);
            }

            return view;
        }

        class Onlongclick implements View.OnLongClickListener{
            public boolean onLongClick(View v) {
                Log.v(TAG, "onLongClick()");
                isMulChoice = true;
                selectid.clear();
                txtcount.setText( "共计 " + selectid.size() + " 项");
                layout.setVisibility(View.VISIBLE);
                for (int i = 0; i < list.size(); i++) {
                    adapter.visiblecheck.put(i, CheckBox.VISIBLE);
                }

                Set_Referash_Data();
                return true;
            }
        }

    }
    static class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_message;
        TextView tv_synopsis;
        CheckBox _checkbox;
    }

    public void onBtnScanClick(View view) {
        Intent intent = new Intent();
        intent.setClass(homepage.this, scan.class);
        startActivity(intent);
    }

    public void onBtnSearchClick(View view) {
        Intent intent = new Intent();
        intent.setClass(homepage.this, search.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume()");

        boolean isCameraRet = false;
        Bundle bunde = this.getIntent().getBundleExtra(getString(R.string.isbnIntent));

        if (bunde != null) {
            isCameraRet = bunde.getBoolean(getString(R.string.isCameraReturned));
            Log.i(TAG, "onResume isCameraRet: " + isCameraRet);
            if (isCameraRet) {
                Log.i(TAG, "onResume() Get (ISBN_INTENT) Intent: IS_CAMERA_RETURNED: " + isCameraRet);
                String isbnStr = bunde.getString(getString(R.string.isbnValue));
                Log.i(TAG, "onResume isbnStr: " + isbnStr);

                db = new DatabaseHandler(this);
                // check only one book in database with the same ISBN
                boolean isBookNotExisted = db.CheckBookCanAdd(isbnStr);
                Log.v(TAG, "onResume() isBookExisted: " + isBookNotExisted);

                if (!isBookNotExisted) {
                    Log.v(TAG, "onResume() call getData()");
                    getData(isbnStr);
                }
                else {
                    toast(getString(R.string.bookAlreadyInDB));
                }

                bunde.putBoolean(getString(R.string.isCameraReturned), false);

                db.close();
            }
        }

//        Log.v(TAG, "onResume() Set_Referash_Data()");
//        Set_Referash_Data();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause() click the return button?");

        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.bookcellPreferenceFile), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (isMulChoice) {
            editor.putBoolean(getString(R.string.isDeletingItem), true);
            editor.commit();
        }
        else {
            editor.putBoolean(getString(R.string.isDeletingItem), false);
            editor.commit();
        }
    }

    public void Set_Referash_Data() {
        list.clear();
        adapter = new SubjectListAdapter();
        lv_main_books.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        db = new DatabaseHandler(this);
        ArrayList<BookInfo> bookInfo_array_from_db = db.Get_BookInfos();

        for (int i = 0; i < bookInfo_array_from_db.size(); i++) {

            int tidno = bookInfo_array_from_db.get(i).getID();
            String isbn = bookInfo_array_from_db.get(i).getBookISBN();
            String name = bookInfo_array_from_db.get(i).getName();
            String author = bookInfo_array_from_db.get(i).getBookAuthor();
            String pub = bookInfo_array_from_db.get(i).getBookPub();
            String position = bookInfo_array_from_db.get(i).getBookPosition();
            String describe = bookInfo_array_from_db.get(i).getDescribe();
            BookInfo bInfo = new BookInfo();
            bInfo.setID(tidno);
            bInfo.setBookISBN(isbn);
            bInfo.setName(name);
            bInfo.setBookAuthor(author);
            bInfo.setBookPub(pub);
            bInfo.setBookPosition(position);
            bInfo.setDescribe(describe);

            list.add(bInfo);
        }
        db.close();
        Log.i(TAG, "Set_Referash_Data() new SubjectListAdapter()");
        adapter = new SubjectListAdapter();
        lv_main_books.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v(TAG, "onCreateOptionMenu()");
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.homepage, menu);
        return super.onCreateOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
//            case R.id.action_search:
//                openSearch();
//                return true;
            case R.id.action_about:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void openSettings()
    {
        Intent intent = new Intent();
        intent.setClass(homepage.this, AppAbout.class);
        startActivity(intent);
    }

    private void toast( String text )
    {
        Toast.makeText( getApplicationContext(),
                text, Toast.LENGTH_SHORT )
                .show();
    }

}
