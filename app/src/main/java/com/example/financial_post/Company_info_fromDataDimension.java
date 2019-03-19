package com.example.financial_post;

import android.content.Intent;
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
import java.util.List;

public class Company_info_fromDataDimension extends AppCompatActivity implements Runnable{
    String dimension;
    MyGridView gridView;
    TextView textView;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_online_info);
        Button button=findViewById(R.id.onlineinfobtn); //不需要菜单键 之后可视化可以用菜单键
        button.setVisibility(View.GONE);
        dimension=getIntent().getStringExtra("dimension");
        gridView=(MyGridView)findViewById(R.id.gridview);
        textView=findViewById(R.id.company_info);
        textView.setText(dimension);
        int msgWhat = 3;
        Thread t = new Thread(Company_info_fromDataDimension.this);
        t.start();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 5){
                    final List<String> infodetail = (List<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(Company_info_fromDataDimension.this,R.layout.support_simple_spinner_dropdown_item,infodetail);
                    Log.i("handler","map...");
                    gridView.setAdapter(adapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String a=infodetail.get(position);
                            Toast.makeText(Company_info_fromDataDimension.this,a,Toast.LENGTH_SHORT).show();
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
        switch (dimension){
            case "业绩概览":
                yejigailan();
                break;
            case "盈利能力":
                yinglinengli();
                break;
            case "营运能力":
                yingyunnengli();
                break;
            case "资产负债表":
                zichanfuzhai();
                break;
            case "现金流量表":
                xianjinliuliang();
                break;
            case "利润表":
                lirun();
                break;
            case "偿债能力":
                changzhainengli();
                break;
            case "成长能力":
                chengzhangnengli();
                break;
        }
    }

    public void yejigailan(){
        gridView.setNumColumns(8);
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        final List<String> infodetail=new ArrayList<String>();
        infodetail.add("年份");
        infodetail.add("省市");
        infodetail.add("每股收益");
        infodetail.add("每股现金流");
        infodetail.add("主营收入（亿）");
        infodetail.add("同比增长（%）");
        infodetail.add("净利润（亿）");
        infodetail.add("同比增长（%）");
        Cursor cursor;
        String table="yejigailan";
        String sql="SELECT * FROM "+table+ " order by \"年份\"";
        cursor=database.rawQuery(sql,null);
        if(cursor==null){
            cursor.close();
            database.close();
            return;
        }
        else{
            while(cursor.moveToNext()){
                String data=cursor.getString(cursor.getColumnIndex("年份"));
                infodetail.add(data);
                data=cursor.getString(cursor.getColumnIndex("所在省市"));
                infodetail.add(data);
                data=cursor.getString(cursor.getColumnIndex("每股收益"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("每股现金流"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("主营收入亿"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("同比增长百分之"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("净利润亿"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("同比增长1百分之"));
                infodetail.add(data==null?"--":data);}
        }
        cursor.close();
        database.close();
        Message msg = handler.obtainMessage(5);
        msg.obj = infodetail;
        handler.sendMessage(msg);
        Log.i("thread","sendMessage.....");
    }
    public void zichanfuzhai(){
        gridView.setNumColumns(9);
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        final List<String> infodetail=new ArrayList<String>();
        infodetail.add("年份");
        infodetail.add("省市");
        infodetail.add("总资产（亿）");
        infodetail.add("货币资金（亿）");
        infodetail.add("流动资产（亿）");
        infodetail.add("总负债（亿）");
        infodetail.add("流动负债（亿）");
        infodetail.add("净资产（亿）");
        infodetail.add("比上期（亿）");
        Cursor cursor;
        String table="zichanfuzhai";
        String sql="SELECT * FROM "+table+ " order by \"年份\"";
        cursor=database.rawQuery(sql,null);
        if(cursor==null){
            cursor.close();
            database.close();
            return;
        }
        else{
            while(cursor.moveToNext()){
                String data=cursor.getString(cursor.getColumnIndex("年份"));
                infodetail.add(data);
                data=cursor.getString(cursor.getColumnIndex("所在省市"));
                infodetail.add(data);
                data=cursor.getString(cursor.getColumnIndex("总资产亿"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("货币资金亿"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("流动资产亿"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("总负债亿"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("流动负债亿"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("净资产亿"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("比上期亿"));
                infodetail.add(data==null?"--":data);
            }
        }
        cursor.close();
        database.close();
        Message msg = handler.obtainMessage(5);
        msg.obj = infodetail;
        handler.sendMessage(msg);
        Log.i("thread","sendMessage.....");}
    public void lirun(){
        gridView.setNumColumns(9);
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        final List<String> infodetail=new ArrayList<String>();
        infodetail.add("年份");
        infodetail.add("省市");
        infodetail.add("营业收入（亿）");
        infodetail.add("营业成本（亿）");
        infodetail.add("营业利润（亿）");
        infodetail.add("利润总额（亿）");
        infodetail.add("税后净利润（亿）");
        infodetail.add("同比增长（%）");
        infodetail.add("基本每股收益");
        Cursor cursor;
        String table="lirun";
        String sql="SELECT * FROM "+table+ " order by \"年份\"";
        cursor=database.rawQuery(sql,null);
        if(cursor==null){
            cursor.close();
            database.close();
            return;
        }
        else{
            while(cursor.moveToNext()){
                String data=cursor.getString(cursor.getColumnIndex("年份"));
                infodetail.add(data);
                data=cursor.getString(cursor.getColumnIndex("所在省市"));
                infodetail.add(data);
                data=cursor.getString(cursor.getColumnIndex("营业收入亿"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("营业成本亿"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("营业利润亿"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("利润总额亿"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("税后净利润亿"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("同比增长百分之"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("基本每股收益"));
                infodetail.add(data==null?"--":data);
            }
        }
        cursor.close();
        database.close();
        Message msg = handler.obtainMessage(5);
        msg.obj = infodetail;
        handler.sendMessage(msg);
        Log.i("thread","sendMessage.....");}
    public void xianjinliuliang(){
        gridView.setNumColumns(7);
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        final List<String> infodetail=new ArrayList<String>();
        infodetail.add("年份");
        infodetail.add("省市");
        infodetail.add("期末现金余额（亿）");
        infodetail.add("经营现金流（亿）");
        infodetail.add("投资现金流（亿）");
        infodetail.add("筹资现金流（亿）");
        infodetail.add("总计净现金流（亿）");
        Cursor cursor;
        String table="xianjinliuliang";
        String sql="SELECT * FROM "+table+ " order by \"年份\"";
        cursor=database.rawQuery(sql,null);
        if(cursor==null){
            cursor.close();
            database.close();
            return;
        }
        else{
            while(cursor.moveToNext()){
                String data=cursor.getString(cursor.getColumnIndex("年份"));
                infodetail.add(data);
                data=cursor.getString(cursor.getColumnIndex("所在省市"));
                infodetail.add(data);
                data=cursor.getString(cursor.getColumnIndex("期末现金余额亿"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("经营现金流亿"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("投资现金流亿"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("筹资现金流亿"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("总计净现金流亿"));
                infodetail.add(data==null?"--":data);
            }
        }
        cursor.close();
        database.close();
        Message msg = handler.obtainMessage(5);
        msg.obj = infodetail;
        handler.sendMessage(msg);
        Log.i("thread","sendMessage.....");}
    public void chengzhangnengli(){
        gridView.setNumColumns(6);
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        final List<String> infodetail=new ArrayList<String>();
        infodetail.add("年份");
        infodetail.add("省市");
        infodetail.add("主营业务收入增长率（%）");
        infodetail.add("净利润增长率（%）");
        infodetail.add("净资产增长率（%）");
        infodetail.add("总资产增长率（%）");
        Cursor cursor;
        String table="chengzhangnengli";
        String sql="SELECT * FROM "+table+ " order by \"年份\"";
        cursor=database.rawQuery(sql,null);
        if(cursor==null){
            cursor.close();
            database.close();
            return;
        }
        else{
            while(cursor.moveToNext()){
                String data=cursor.getString(cursor.getColumnIndex("年份"));
                infodetail.add(data);
                data=cursor.getString(cursor.getColumnIndex("所在省市"));
                infodetail.add(data);
                data=cursor.getString(cursor.getColumnIndex("主营业务收入增长率百分之"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("净利润增长率百分之"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("净资产增长率百分之"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("总资产增长率百分之"));
                infodetail.add(data==null?"--":data);
                }
        }
        cursor.close();
        database.close();
        Message msg = handler.obtainMessage(5);
        msg.obj = infodetail;
        handler.sendMessage(msg);
        Log.i("thread","sendMessage.....");}
    public void changzhainengli(){
        gridView.setNumColumns(7);
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        final List<String> infodetail=new ArrayList<String>();
        infodetail.add("年份");
        infodetail.add("省市");
        infodetail.add("流动比率（%）");
        infodetail.add("速动比率（%）");
        infodetail.add("现金比率（%）");
        infodetail.add("利息支付倍数");
        infodetail.add("资产负债率（%）");
        Cursor cursor;
        String table="changzhainengli";
        String sql="SELECT * FROM "+table+ " order by \"年份\"";
        cursor=database.rawQuery(sql,null);
        if(cursor==null){
            cursor.close();
            database.close();
            return;
        }
        else{
            while(cursor.moveToNext()) {
                String data = cursor.getString(cursor.getColumnIndex("年份"));
                infodetail.add(data);
                data = cursor.getString(cursor.getColumnIndex("所在省市"));
                infodetail.add(data);
                data = cursor.getString(cursor.getColumnIndex("流动比率百分之"));
                infodetail.add(data == null ? "--" : data);
                data = cursor.getString(cursor.getColumnIndex("速动比率百分之"));
                infodetail.add(data == null ? "--" : data);
                data = cursor.getString(cursor.getColumnIndex("现金比率百分之"));
                infodetail.add(data == null ? "--" : data);
                data = cursor.getString(cursor.getColumnIndex("利息支付倍数"));
                infodetail.add(data == null ? "--" : data);
                data = cursor.getString(cursor.getColumnIndex("资产负债率百分之"));
                infodetail.add(data == null ? "--" : data);
            }
        }
        cursor.close();
        database.close();
        Message msg = handler.obtainMessage(5);
        msg.obj = infodetail;
        handler.sendMessage(msg);
        Log.i("thread","sendMessage.....");
    }
    public void yinglinengli(){
        gridView.setNumColumns(8);
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        final List<String> infodetail=new ArrayList<String>();
        infodetail.add("年份");
        infodetail.add("省市");
        infodetail.add("主营业务利润率（%）");
        infodetail.add("销售毛利率（%）");
        infodetail.add("销售净利率（%）");
        infodetail.add("总资产收益率（%）");
        infodetail.add("净资产收益率（%）");
        infodetail.add("三项费用比重（%）");
        Cursor cursor;
        String table="yinglinengli";
        String sql="SELECT * FROM "+table+ " order by \"年份\"";
        cursor=database.rawQuery(sql,null);
        if(cursor==null){
            cursor.close();
            database.close();
            return;
        }
        else{
            while(cursor.moveToNext()){
                String data=cursor.getString(cursor.getColumnIndex("年份"));
                infodetail.add(data);
                data=cursor.getString(cursor.getColumnIndex("所在省市"));
                infodetail.add(data);
                data=cursor.getString(cursor.getColumnIndex("主营业务利润率百分之"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("销售毛利率百分之"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("销售净利率百分之"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("总资产收益率百分之"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("净资产收益率百分之"));
                infodetail.add(data==null?"--":data);
                data=cursor.getString(cursor.getColumnIndex("三项费用比重百分之"));
                infodetail.add(data==null?"--":data);}
        }
        cursor.close();
        database.close();
        Message msg = handler.obtainMessage(5);
        msg.obj = infodetail;
        handler.sendMessage(msg);
        Log.i("thread","sendMessage.....");
    }
    public void yingyunnengli(){
        gridView.setNumColumns(7);
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
        final List<String> infodetail=new ArrayList<String>();
        infodetail.add("年份");
        infodetail.add("省市");
        infodetail.add("应收账款周转天数");
        infodetail.add("存货周转天数");
        infodetail.add("经营现金流/净利润");
        infodetail.add("经营现金流/负债");
        infodetail.add("净现金流/流动负债");
        Cursor cursor;
        String table="yingyunnengli";
        String sql="SELECT * FROM "+table+ " order by \"年份\"";
        cursor=database.rawQuery(sql,null);
        if(cursor==null){
            cursor.close();
            database.close();
            return;
        }
        else{
            while(cursor.moveToNext()) {
                String data = cursor.getString(cursor.getColumnIndex("年份"));
                infodetail.add(data);
                data = cursor.getString(cursor.getColumnIndex("所在省市"));
                infodetail.add(data);
                data = cursor.getString(cursor.getColumnIndex("应收账款周转天数"));
                infodetail.add(data == null ? "--" : data);
                data = cursor.getString(cursor.getColumnIndex("存货周转天数"));
                infodetail.add(data == null ? "--" : data);
                data = cursor.getString(cursor.getColumnIndex("经营现金流净利润"));
                infodetail.add(data == null ? "--" : data);
                data = cursor.getString(cursor.getColumnIndex("经营现金流负债"));
                infodetail.add(data == null ? "--" : data);
                data = cursor.getString(cursor.getColumnIndex("净现金流流动负债"));
                infodetail.add(data == null ? "--" : data);
            }
        }
        cursor.close();
        database.close();
        Message msg = handler.obtainMessage(5);
        msg.obj = infodetail;
        handler.sendMessage(msg);
        Log.i("thread","sendMessage.....");
    }
    @Override
    public void onBackPressed() {
        Intent home = new Intent(this, DataDimension.class);
        startActivity(home);
        finish();
    }
}
