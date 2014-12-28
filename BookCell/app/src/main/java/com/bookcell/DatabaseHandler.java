package com.bookcell;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String TAG = DatabaseHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "bookCellManager";

    // Contacts table name
    private static final String TABLE_BOOKCELL = "bookCell";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_ISBN = "isbn";
    private static final String KEY_NAME = "name";
    private static final String KEY_AUTHER = "auther";
    private static final String KEY_PUB = "pub";
    private static final String KEY_POSITION = "position";
    private static final String KEY_DESCRIBE = "describe";
    private final ArrayList<BookInfo> bookInfo_list = new ArrayList<BookInfo>();

    public DatabaseHandler(Context context) {
	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
	String CREATE_BOOKCELL_TABLE = "CREATE TABLE " + TABLE_BOOKCELL + "("
		+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_ISBN + " TEXT," + KEY_NAME + " TEXT,"
        + KEY_AUTHER + " TEXT," + KEY_PUB + " TEXT," + KEY_POSITION + " TEXT,"
        + KEY_DESCRIBE + " TEXT" + ")";
	db.execSQL(CREATE_BOOKCELL_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	// Drop older table if existed
	db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKCELL);

	// Create tables again
	onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void Add_BookInfo(BookInfo bookInfo) {
	SQLiteDatabase db = this.getWritableDatabase();
	ContentValues values = new ContentValues();
    values.put(KEY_ISBN, bookInfo.getBookISBN()); // BookInfo ISBN
    values.put(KEY_NAME, bookInfo.getName()); // BookInfo Name
    values.put(KEY_AUTHER, bookInfo.getBookAuthor()); // BookInfo Author
    values.put(KEY_PUB, bookInfo.getBookPub()); // BookInfo PUB
    values.put(KEY_POSITION, bookInfo.getBookPosition()); // BookInfo Position
    values.put(KEY_DESCRIBE, bookInfo.getDescribe()); // BookInfo Describe

	// Inserting Row
	db.insert(TABLE_BOOKCELL, null, values);
    Log.d(TAG, "Add_BookInfo() bookInfo KEY_ISBN: " + bookInfo.getBookISBN());
	db.close(); // Closing database connection
    }

    // Getting single BookInfo
    BookInfo Get_BookInfo(int id) {
	SQLiteDatabase db = this.getReadableDatabase();

	Cursor cursor = db.query(TABLE_BOOKCELL, new String[] { KEY_ID
		,KEY_ISBN, KEY_NAME, KEY_AUTHER, KEY_PUB, KEY_POSITION, KEY_DESCRIBE }, KEY_ID + "=?",
		new String[] { String.valueOf(id) }, null, null, null, null);
	if (cursor != null)
	    cursor.moveToFirst();

        BookInfo bookInfo = new BookInfo(Integer.parseInt(cursor.getString(0)),
		cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
	cursor.close();
	db.close();

	return bookInfo;
    }

    int get_BookInfoId_With_Title(String title) {
        int nBookId = -1;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BOOKCELL, new String[] { KEY_ID
                        ,KEY_ISBN, KEY_NAME, KEY_AUTHER, KEY_PUB, KEY_POSITION, KEY_DESCRIBE }, KEY_NAME + "=?",
                new String[] { String.valueOf(title) }, null, null, null, null);
        Log.v(TAG, "get_BookInfoId_With_Title() cursor: " + cursor);
        if (cursor != null)
            cursor.moveToFirst();

        if (cursor.getCount() <= 0) {
            return -1;
        }

        BookInfo bookInfo = new BookInfo(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));

        nBookId = Integer.parseInt(cursor.getString(0));

        cursor.close();
        db.close();

        return nBookId;
    }

    // Getting All BookInfos
    public ArrayList<BookInfo> Get_BookInfos() {
	try {
	    bookInfo_list.clear();

	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + TABLE_BOOKCELL;

	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);

	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
		do {
            BookInfo bookInfo = new BookInfo();
            bookInfo.setID(Integer.parseInt(cursor.getString(0)));
            bookInfo.setBookISBN(cursor.getString(1));
            bookInfo.setName(cursor.getString(2));
            bookInfo.setBookAuthor(cursor.getString(3));
            bookInfo.setBookPub(cursor.getString(4));
            bookInfo.setBookPosition(cursor.getString(5));
            bookInfo.setDescribe(cursor.getString(6));
		    // Adding bookInfo to list
		    bookInfo_list.add(bookInfo);
		} while (cursor.moveToNext());
	    }

	    // return bookInfo list
	    cursor.close();
	    db.close();
	    return bookInfo_list;
	} catch (Exception e) {
	    // TODO: handle exception
	    Log.e("all_bookInfo", "" + e);
	}

	return bookInfo_list;
    }

    // Updating single bookInfo
    public int Update_BookInfo(BookInfo bookInfo) {
	SQLiteDatabase db = this.getWritableDatabase();

	ContentValues values = new ContentValues();
	values.put(KEY_ISBN, bookInfo.getBookISBN());
    values.put(KEY_NAME, bookInfo.getName());
    values.put(KEY_AUTHER, bookInfo.getBookAuthor());
    values.put(KEY_PUB, bookInfo.getBookPub());
    values.put(KEY_POSITION, bookInfo.getBookPosition());
    values.put(KEY_DESCRIBE, bookInfo.getDescribe());

	// updating row
	return db.update(TABLE_BOOKCELL, values, KEY_ID + " = ?",
		new String[] { String.valueOf(bookInfo.getID()) });
    }

    // Deleting single BookInfo
    public void Delete_BookInfo(int id) {
        Log.v(TAG, "Delete_BookInfo()");

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BOOKCELL, KEY_ID + " = ?",
                    new String[] { String.valueOf(id) });
        db.close();
    }

    // Getting BookInfo Count
    public int Get_Total_BookInfos() {
	String countQuery = "SELECT  * FROM " + TABLE_BOOKCELL;
	SQLiteDatabase db = this.getReadableDatabase();
	Cursor cursor = db.rawQuery(countQuery, null);
	cursor.close();

	// return count
	return cursor.getCount();
    }

    // Check book can add to db
    // If there is already the same book in db, just warn the user.
    public boolean CheckBookCanAdd(String isbn) {
        boolean isBookExist = false;
        Log.v(TAG, "CheckBookCanAdd().");
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BOOKCELL, new String[] { KEY_ID
                        ,KEY_ISBN}, KEY_ISBN + "=?",
                new String[] { String.valueOf(isbn) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            //isBookExist = true;
            //Log.v(TAG, "CheckBookCanAdd() book existed");
        }
        else {
            //Log.v(TAG, "CheckBookCanAdd() book not existed");
            //isBookExist = false;
        }

        if (cursor.getCount() <= 0) {
            isBookExist = false;
            Log.v(TAG, "CheckBookCanAdd() book not existed");
        }
        else{
            isBookExist = true;
            Log.v(TAG, "CheckBookCanAdd() book existed");
        }

        cursor.close();
        db.close();

        return isBookExist;
    }

}
