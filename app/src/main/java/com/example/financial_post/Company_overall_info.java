package com.example.financial_post;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Company_overall_info extends AppCompatActivity implements Runnable{   //业绩概览
    private String ID,name;
    MyGridView gridView;
    TextView textView;
    List<String> dataList=new ArrayList<String>();
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_online_info);
        ID=getIntent().getStringExtra("ID");
        name=getIntent().getStringExtra("Name");
        gridView=(MyGridView)findViewById(R.id.gridview);
        gridView.setNumColumns(8);
        textView=findViewById(R.id.company_info);
        Button btn=findViewById(R.id.onlineinfobtn);
        btn.setVisibility(View.GONE);
        int msgWhat = 3;
        Thread t = new Thread(Company_overall_info.this);
        t.start();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 5){
                    textView.setText(name);
                    final List<String> list = (List<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(Company_overall_info.this,R.layout.support_simple_spinner_dropdown_item,list);
                    Log.i("handler","map...");
                    gridView.setAdapter(adapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String a=list.get(position);
                            Toast.makeText(Company_overall_info.this,a,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                super.handleMessage(msg);
            }
        };
    }
    @Override
    public void run() {
        Log.i("thread", "run.....");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       // dataList.addAll(dbManager_company.company_overalldata(ID)) ;
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        List<String> companydetail=new ArrayList<String>();
        companydetail.add("年份");
        companydetail.add("代码");
        companydetail.add("每股收益");
        companydetail.add("每股现金流");
        companydetail.add("主营收入（亿）");
        companydetail.add("同比增长（%）");
        companydetail.add("净利润（亿）");
        companydetail.add("同比增长（%）");
        Cursor cursor;
        for(int i=2017;i>2001;i--){
            String table="financial_data_yejigailan_"+String.valueOf(i);
            String sql="SELECT * FROM "+table+" WHERE \"代码\" LIKE '%"+ID+"%'";
            Log.i("sql",sql);
            cursor=database.rawQuery(sql,null);
            if(cursor==null){
                cursor.close();
                continue;
            }
            else{
                while(cursor.moveToNext()){
                    Log.i("table",table);
                    companydetail.add(String.valueOf(i));
                    companydetail.add(ID);
                    String data=cursor.getString(cursor.getColumnIndex("每股收益"));
                    companydetail.add(data==null?"--":data);
                    data=cursor.getString(cursor.getColumnIndex("每股现金流"));
                    companydetail.add(data==null?"--":data);
                    data=cursor.getString(cursor.getColumnIndex("主营收入亿"));
                    companydetail.add(data==null?"--":data);
                    data=cursor.getString(cursor.getColumnIndex("同比增长百分之"));
                    companydetail.add(data==null?"--":data);
                    data=cursor.getString(cursor.getColumnIndex("净利润亿"));
                    companydetail.add(data==null?"--":data);
                    data=cursor.getString(cursor.getColumnIndex("同比增长1百分之"));
                    companydetail.add(data==null?"--":data);}
            }
            cursor.close();
        }
        database.close();
        Message msg = handler.obtainMessage(5);
        msg.obj = companydetail;
        handler.sendMessage(msg);
        Log.i("thread","sendMessage.....");
    }
}
