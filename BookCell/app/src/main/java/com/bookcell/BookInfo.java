package com.bookcell;

/**
 * Created by perry on 2014/8/31.
 */
public class BookInfo {
    private int _id;
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

    BookInfo(int id, String isbn, String Name, String author, String pub, String position, String Describe, String picPath)
    {
        _id = id;
        bookISBN = isbn;
        bookName = Name;
        bookAuthor = author;
        bookPub = pub;
        bookPosition = position;
        bookDescribe = Describe;
        bookPicturePath = picPath;
    }

    BookInfo()
    {

    }

    public int getID()
    {
        return _id;
    }
    public void setID(int id)
    {
        this._id = id;
    }

    public String getBookISBN()
    {
        return bookISBN;
    }
    public void setBookISBN(String bookISBN)
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
    public void setBookAuthor(String bookAuthor)
    {
        this.bookAuthor = bookAuthor;
    }

    public String getBookPub()
    {
        return bookPub;
    }
    public void setBookPub(String bookPub)
    {
        this.bookPub = bookPub;
    }

    public String getBookPosition()
    {
        return bookPosition;
    }
    public void setBookPosition(String bookPosition)
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
