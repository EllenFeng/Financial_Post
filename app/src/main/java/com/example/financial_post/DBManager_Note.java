package com.example.financial_post;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager_Note {
    private SQLiteDatabase database;
    private String TBNAME;

    public DBManager_Note() {
        TBNAME = "note";
    }

    public List<NoteItem> listAllNote(){
        List<NoteItem> notedetail = null;
        database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        Cursor cursor = database.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            notedetail = new ArrayList<NoteItem>();
            while(cursor.moveToNext()){
                NoteItem item = new NoteItem();
                item.setTitle(cursor.getString(cursor.getColumnIndex("TITLE")));
                item.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
                notedetail.add(item);
            }
            cursor.close();
        }
        database.close();
        return notedetail;
    }

    public String getNoteContent(String title){
        database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        String[] title1={title};
        Cursor cursor = database.query(TBNAME,null,"TITLE"+"=?",title1,null,null,null,null);
        String content="";
        if(cursor!=null){
            while(cursor.moveToNext()){
                content=cursor.getString(cursor.getColumnIndex("CONTENT"));
            }
            cursor.close();
        }
        database.close();
        return content;
    }
    public void deleteAllNote(){
        database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        database.delete(TBNAME,null,null);
        database.close();
    }

    public void deleteNote(String title){
        database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        String[] title1={title};
        database.delete(TBNAME, "TITLE"+"=?", title1);
        database.close();
    }


    public boolean isNoteExisted(String title){//检查改名字是否已经存在，存在了要换个标题 因为标题为主键
        database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        String[] title1={title};
        Cursor cursor = database.query(TBNAME,null,"TITLE"+"=?",title1,null,null,null,null);
        if(cursor.getCount()==0)  //没有存在有这个标题的笔记
            return false;
        else
            return true;
    }


    public void addNote(NoteItem item){
        database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        ContentValues values = new ContentValues();
        values.put("title", item.getTitle());
        values.put("content", item.getContent());
        values.put("date", item.getDate());
        database.insert(TBNAME, null, values);
        database.close();
    }


    public void updateNote(NoteItem item){   //同时更新时间
        database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        String[] title1={item.getTitle()};
        ContentValues values = new ContentValues();
        values.put("title", item.getTitle());
        values.put("content", item.getContent());
        values.put("date", item.getDate());
        database.update(TBNAME, values, "TITLE"+"=?",title1);
        database.close();
    }
}
