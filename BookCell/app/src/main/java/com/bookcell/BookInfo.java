package com.bookcell;

/**
 * Created by perry on 2014/8/31.
 */
public class BookInfo {
    private String bookISBN;
    private String bookName;
    private String bookAuthor;
    private String bookPub;
    private String bookPosition;
    private String bookDescribe;
    private int icon;
    private String bookPicturePath;

    BookInfo(String Name,String Describe,int Icon, String bookpicPath)
    {
        bookName = Name;
        bookDescribe = Describe;
        icon = Icon;
        bookPicturePath = bookpicPath;
        bookISBN = "";
        bookAuthor = "";
        bookPub = "";
        bookPosition = "";
    }

    BookInfo()
    {

    }

    public String getBookISBN()
    {
        return bookISBN;
    }
    public void setName(String bookISBN)
    {
        this.bookISBN = bookISBN;
    }

    public String getName()
    {
        return bookName;
    }
    public void setName(String bookName)
    {
        this.bookName = bookName;
    }

    public String getBookAuthor()
    {
        return bookAuthor;
    }
    public void setName(String bookAuthor)
    {
        this.bookAuthor = bookAuthor;
    }

    public String getBookPub()
    {
        return bookPub;
    }
    public void setName(String bookPub)
    {
        this.bookPub = bookPub;
    }

    public String getBookPosition()
    {
        return bookPosition;
    }
    public void setName(String bookPosition)
    {
        this.bookPosition = bookPosition;
    }

    public int getIcon()
    {
        return icon;
    }
    public void setIcon(int icon)
    {
        this.icon = icon;
    }

    public String getDescribe()
    {
        return bookDescribe;
    }
    public void setDescribe(String bookDescribe)
    {
        this.bookDescribe = bookDescribe;
    }

    public String getBookPicturePath() {
        return bookPicturePath;
    }
    public void setBookPicturePath(String bookPicturePath) {
        this.bookPicturePath = bookPicturePath;
    }

}
