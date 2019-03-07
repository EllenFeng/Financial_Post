package com.example.financial_post;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private SQLiteDatabase database;
    private String TBNAME_mark;

    public DBManager() {
        TBNAME_mark = "mark";
    }

    public void creatTables(){
        database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS "+"note"+"(TITLE TEXT PRIMARY KEY,CONTENT TEXT,DATE TEXT)");  //笔记存储
        database.execSQL("CREATE TABLE IF NOT EXISTS "+"mark"+"(NEWSTITLE TEXT PRIMARY KEY,LINK TEXT,ORIGINALDATE TEXT,MARKDATE TEXT)");  //收藏存储
        database.execSQL("CREATE TABLE IF NOT EXISTS "+"follow"+"(ID TEXT PRIMARY KEY,NAME TEXT,FULLNAME TEXT, CITY TEXT,LINK TEXT)");  //关注存储
        database.close();
    }

    public boolean isMarkExisted(String title){
        database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        String[] title1={title};
        String news="NEWSTITLE";
        Cursor cursor = database.query(TBNAME_mark,null,news+"=?",title1,null,null,null,null);
        if(cursor.getCount()==0)  //没有添加过这个新闻
            return false;
        else
            return true;
    }
    public int countExceeded(){  //收藏数量限制
        database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        Cursor cursor = database.query(TBNAME_mark,null,null,null,null,null,null,null);
        if(cursor.getCount()>9) { //添加超过10条
            database.close();
            return 1;
        }
        else{
            database.close();
            return 0;
        }
    }

    public void add(NewsItem item){
        database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        ContentValues values = new ContentValues();
        values.put("newstitle", item.getNewsTitle());
        values.put("link", item.getLink());
        values.put("originaldate", item.getOriginalDate());
        values.put("markdate", item.getMarkDate());
        database.insert(TBNAME_mark, null, values);
        database.close();
    }
    public List<NewsItem> listAllNews(){
        List<NewsItem> newsdetail = null;
        database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        Cursor cursor = database.query(TBNAME_mark, null, null, null, null, null, null);
        if(cursor!=null){
            newsdetail = new ArrayList<NewsItem>();
            while(cursor.moveToNext()){
                NewsItem item = new NewsItem();
                item.setNewsTitle(cursor.getString(cursor.getColumnIndex("NEWSTITLE")));
                item.setLink(cursor.getString(cursor.getColumnIndex("LINK")));
                item.setOriginalDate(cursor.getString(cursor.getColumnIndex("ORIGINALDATE")));
                item.setMarkDate(cursor.getString(cursor.getColumnIndex("MARKDATE")));
                newsdetail.add(item);
            }
            cursor.close();
        }
        database.close();
        return newsdetail;
    }
    public void deleteAllNews(){
        database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        database.delete(TBNAME_mark,null,null);
        database.close();
    }

    public void deleteNews(String title){
        database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        String[] title1={title};
        String news="NEWSTITLE";
        database.delete(TBNAME_mark, news+"=?", title1);
        database.close();
    }
}
