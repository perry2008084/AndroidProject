package com.bookcell;

/**
 * Created by perry on 2014/10/12.
 */

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.net.HttpURLConnection;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

public class NewBookDao {
    public static final String TAG = NewBookDao.class.getSimpleName();
    public static List<BookInfo> getBooksMessage(String isbnStr) throws Exception{
        List<BookInfo> list = new ArrayList<BookInfo>();
        BookInfo newBook = new BookInfo();

        newBook.setBookISBN(isbnStr);

        //urlStr = "http://book.douban.com/isbn/9787040100952/";
        //urlStr = "http://book.douban.com/isbn/9787121148750/";

        String urlStr = "http://book.douban.com/isbn/";
        urlStr += isbnStr;
        urlStr += "/";

        URL serverUrl = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) serverUrl
                .openConnection();
        conn.setRequestMethod("GET");
        // 必须设置false，否则会自动redirect到Location的地址
        conn.setInstanceFollowRedirects(false);
        conn.connect();
        String location = conn.getHeaderField("Location");

        serverUrl = new URL(location);
        conn = (HttpURLConnection) serverUrl.openConnection();
        conn.setRequestMethod("GET");

        URL url = new URL(location);

        URLConnection connBook = url.openConnection();
        Source source = new Source(connBook);

        List<Element> aList = source.getAllElements("a");
        for(Element element:aList)
        {
            if("nbg".equals(element.getAttributeValue("class")))
            {
                String name = element.getAttributeValue("title");
                newBook.setName(name);

                String path = element.getAttributeValue("href");
                newBook.setBookPicturePath(path);
            }
        }

        List<Element> divList = source.getAllElements("div");
        for(Element element:divList)
        {
            if("intro".equals(element.getAttributeValue("class")))
            {
                String message = element.getAllElements().get(0).getTextExtractor().toString();
                newBook.setDescribe(message);
            }

            if("info".equals(element.getAttributeValue("id"))) {
                Log.d(TAG, "get id for Book author");
                String author = element.getAllElements().get(3).getTextExtractor().toString();
                Log.d(TAG, "after get id for Book author, set the author to BookInfo");
                newBook.setBookAuthor(author);

                Log.d(TAG, "get id for Book pub");
                String pub = element.getAllElements().get(4).getTextExtractor().toString();
                Log.d(TAG, "after get id for Book pub, set pub to BookInfo");
                newBook.setBookPub(pub);
            }
        }
        list.add(newBook);

        return list;
    }
}
