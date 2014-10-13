package com.bookcell;

/**
 * Created by perry on 2014/10/12.
 */

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

public class NewBookDao {
    public static List<BookInfo> getAllNewBooks() throws Exception{
        List<BookInfo> list = new ArrayList<BookInfo>();
        //URL url = new URL("http://book.douban.com/latest");
        URL url = new URL("http://book.douban.com/subject/1231576/");

        URLConnection conn = url.openConnection();
        Source source = new Source(conn);
        /*
        List<Element> liList = source.getAllElements("li");
        for(Element element:liList){
            List<Element>  childrenList = element.getChildElements();
            if(childrenList.size()==2&&"div".equals(childrenList.get(0).getName())&&"a".equals(childrenList.get(1).getName())){
                BookInfo newBook = new BookInfo();
                String name = childrenList.get(0).getAllElements().get(0).getTextExtractor().toString();
                String message = childrenList.get(0).getAllElements().get(1).getTextExtractor().toString();
                //String synopsis = childrenList.get(0).getAllElements().get(2).getTextExtractor().toString();
                String path = childrenList.get(1).getAllElements().get(1).getAttributeValue("src");
                newBook.setName(name);
                newBook.setDescribe(message);
                //newBook.setBookSynopsis(synopsis);
                newBook.setBookPicturePath(path);
                list.add(newBook);
            }
        }
        */

        BookInfo newBook = new BookInfo();
        List<Element> aList = source.getAllElements("a");
        for(Element element:aList)
        {
            //if(element.getClass().getName() == "nbg")
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
        }
        list.add(newBook);

        return list;
    }
}
