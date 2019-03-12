package com.example.financial_post;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager_company {
    private SQLiteDatabase database;
    private String TBNAME_company;
    private String TBNAME_follow;

    public DBManager_company() {
        TBNAME_company = "company";
        TBNAME_follow = "followcom";
    }

    public List<CompanyItem> listAllfollow(){  //关注的公司列表
        List<CompanyItem> followdetail = null;
        database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        Cursor cursor = database.query(TBNAME_follow, null, null, null, null, null, null);
        if(cursor!=null){
            followdetail = new ArrayList<CompanyItem>();
            while(cursor.moveToNext()){
                CompanyItem item = new CompanyItem();
                item.setID(cursor.getString(cursor.getColumnIndex("ID")));
                item.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                item.setFullName(cursor.getString(cursor.getColumnIndex("FULLNAME")));
                item.setLink(cursor.getString(cursor.getColumnIndex("LINK")));
                item.setAdd(cursor.getString(cursor.getColumnIndex("ADDRESS")));
                item.setCity(cursor.getString(cursor.getColumnIndex("CITY")));
                followdetail.add(item);
            }
            cursor.close();
        }
        database.close();
        return followdetail;
    }
    public List<CompanyItem> listAllMatch(String name){  //搜索出来的公司列表
        List<CompanyItem> companydetail = null;
        String sql="'%";
        int i = 0;
        while(i<name.length()){
            sql=sql+name.substring(i,i+1)+"%";
            i+=1;
        }
        sql=sql+"'";
        String[] sql1={sql};
        database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);

        Cursor cursor = database.query(TBNAME_company, null, "'代码名称' like ?", sql1, null, null, null);
        if(cursor!=null){
            companydetail = new ArrayList<CompanyItem>();
            while(cursor.moveToNext()){
                CompanyItem item = new CompanyItem();
                item.setID(cursor.getString(cursor.getColumnIndex("ID")));
                item.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                item.setFullName(cursor.getString(cursor.getColumnIndex("FULLNAME")));
                item.setLink(cursor.getString(cursor.getColumnIndex("LINK")));
                item.setAdd(cursor.getString(cursor.getColumnIndex("ADDRESS")));
                item.setCity(cursor.getString(cursor.getColumnIndex("CITY")));
                companydetail.add(item);
            }
            cursor.close();
        }
        database.close();
        return companydetail;
    }
    public List<CompanyItem> listLimitMatch(String name){  //匹配出来前8的公司列表进行提示
        List<CompanyItem> companydetail = null;
        String sql="'%";
        int i = 0;
        while(i<name.length()){
            sql=sql+name.substring(i,i+1)+"%";
            i+=1;
        }
        sql=sql+"'";
        String[] sql1={sql};
        database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        Cursor cursor = database.query(TBNAME_company, null, "'代码名称' like ?", sql1, null, null, null,"8");
        if(cursor!=null){
            companydetail = new ArrayList<CompanyItem>();
            while(cursor.moveToNext()){
                CompanyItem item = new CompanyItem();
                item.setID(cursor.getString(cursor.getColumnIndex("ID")));
                item.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                item.setFullName(cursor.getString(cursor.getColumnIndex("FULLNAME")));
                item.setLink(cursor.getString(cursor.getColumnIndex("LINK")));
                item.setAdd(cursor.getString(cursor.getColumnIndex("ADDRESS")));
                item.setCity(cursor.getString(cursor.getColumnIndex("CITY")));
                companydetail.add(item);
            }
            cursor.close();
        }
        database.close();
        return companydetail;
    }
    public void deleteAllFollow(){
        database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        database.delete(TBNAME_follow,null,null);
        database.close();
    }
    public void deleteFollow(String ID){
        database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        String[] ID1={ID};
        database.delete(TBNAME_follow, "ID"+"=?", ID1);
        database.close();
    }
    public void addFollow(CompanyItem item){
        database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        ContentValues values = new ContentValues();
        values.put("ID", item.getID());
        values.put("name", item.getName());
        values.put("fullname", item.getFullName());
        values.put("city", item.getCity());
        values.put("link", item.getLink());
        values.put("ADDRESS", item.getAdd());
        database.insert(TBNAME_follow, null, values);
        database.close();
    }
}
