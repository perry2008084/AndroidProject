package com.bookcell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import com.bookcell.BookInfo;

/**
 * Created by perry on 2014/8/31.
 */
public class BookAdapter extends BaseAdapter {
    private ArrayList<BookInfo> bookList;
    private LayoutInflater layoutInflater;
    private Context ctx;

    private class ListItem {
        private ImageView itemIcon;
        private TextView itemName;
        private TextView itemDescribe;
    }

    public BookAdapter(Context context, ArrayList<BookInfo> BookList) {
        bookList = BookList;
        layoutInflater = LayoutInflater.from(context);
        ctx = context;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        ListItem listItem = null;

        if(convertView == null) {
            listItem = new ListItem();
            convertView = layoutInflater.inflate(R.layout.main_item, null);
            listItem.itemDescribe = (TextView) convertView.findViewById(R.id.tv_message);
            listItem.itemName = (TextView) convertView.findViewById(R.id.tv_name);
            listItem.itemIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            convertView.setTag(listItem);
        }
        else {
            listItem = (ListItem)convertView.getTag();
        }

        BookInfo bookInfo = bookList.get(position);
        listItem.itemDescribe.setText(bookInfo.getDescribe());
        listItem.itemName.setText(bookInfo.getName());
        listItem.itemIcon.setBackgroundResource(bookInfo.getIcon());
        return convertView;
    }
}
