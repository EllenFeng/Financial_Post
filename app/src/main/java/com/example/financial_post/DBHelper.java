package com.example.financial_post;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper{  //将数据库拷贝到系统数据库默认位置DBHelper.DB_PATH +"/"+DBHelper.DB_NAME
    private final int BUFFER_SIZE = 400000;
    public static final String DB_NAME = "fin_data.db"; //保存的数据库文件名
    public static final String PACKAGE_NAME = "com.example.financial_post";
    public static final String DB_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" +PACKAGE_NAME;
    //在手机里存放数据库的位置 （系统默认位置）
    private Context context;
    private SQLiteDatabase database=null;
    // com.example.financial_post是程序的包名
    // /data/data/com....目录是准备放 SQLite 数据库的地方，也是 Android 程序默认的数据库存储目录


    DBHelper(Context context)  {
        this.context=context;
    }
    public void openDatabase(){
        database=openDatabase(DB_PATH +"/"+DB_NAME);
    }


    private SQLiteDatabase openDatabase(String dbfile){
        try {
            if (!(new File(dbfile).exists())){
                InputStream is = context.getAssets().open(DB_NAME);
                FileOutputStream fos = new FileOutputStream(dbfile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile,
                    null);
            return db;
            }  catch (FileNotFoundException e) {
            Log.e("Database", "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Database", "IO exception");
            e.printStackTrace();
        }
        return null;
    }
    public void closeDatabase() {
        database.close();
    }

}
