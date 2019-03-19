package com.example.financial_post;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends FragmentActivity {
    public DBHelper dbHelper;
    private Button b1;
    private Button b2;
    private Button b3;
    private ListView lv;
    private HomeFragment h1;
    private DataFragment h2;
    private MineFragment h3;
    private SimpleAdapter adapter1;
    public static FragmentManager fm;
    private int from;
    private long deleteTime=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        from = getIntent().getIntExtra("class",1);
        Date today= Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        final String todayStr=sdf.format(today);
        SharedPreferences sharedPreferences = getSharedPreferences("date", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("today_date",todayStr);
        editor.putString("resource","新浪网");
        editor.commit();
        h1 = HomeFragment.newInstance();
        h2 = DataFragment.newInstance();
        h3 = MineFragment.newInstance();
        b1 = (Button) findViewById(R.id.btn1);
        b2 = (Button) findViewById(R.id.btn2);
        b3 = (Button) findViewById(R.id.btn3);
        dbHelper = new DBHelper(this);
        dbHelper.openDatabase();
        dbHelper.closeDatabase();
        DBManager dbManager=new DBManager();
        dbManager.creatTables();
//
//        DBManager_company manager=new DBManager_company();
//        CompanyItem item1=new CompanyItem("000004","国农科技","中国农大科技股份有限公司","广东","http://quotes.money.163.com/1000004.html#11a01","广东省深圳市南山区中心路(深圳湾段)3333号中铁南方总部大厦503室");
//        manager.addFollow(item1);

        if(from == 3)
            init3();
        else{
            if (from == 2)
                init2();
            else{
                init();
            }
        }


    }
    //    private void showFragment(HomeFragment frag){
//        fm = getSupportFragmentManager();
//        FragmentTransaction transaction = fm.beginTransaction();
//        if(cur!=frag){
//            transaction.hide(cur);
//            cur=frag;
//            if (!frag.isAdded()){
//                transaction.add(R.id.fragment_container,frag).commit();
//                transaction.show(frag);
//            }
//            else{
//                transaction.show(frag).commit();
//            }
//        }
//    }
    private void init() {
        b1.setBackgroundResource(R.drawable.shape3);
        b2.setBackgroundResource(R.drawable.shape3);
        b3.setBackgroundResource(R.drawable.shape3);
        b1.setBackgroundResource(R.drawable.shape2);
        fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        if (!h1.isAdded()) {
            transaction.add(R.id.fragment_container, h1).commit();
            transaction.show(h1);
        }else {
            transaction.show(h1).commit();
        }
    }

    private void init2() {
        b1.setBackgroundResource(R.drawable.shape3);
        b2.setBackgroundResource(R.drawable.shape3);
        b3.setBackgroundResource(R.drawable.shape3);
        b2.setBackgroundResource(R.drawable.shape2);
        fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        if (!h2.isAdded()) {
            transaction.add(R.id.fragment_container, h2).commit();
            transaction.show(h2);
        }else {
            transaction.show(h2).commit();
        }
    }

    private void init3() {
        b1.setBackgroundResource(R.drawable.shape3);
        b2.setBackgroundResource(R.drawable.shape3);
        b3.setBackgroundResource(R.drawable.shape3);
        b3.setBackgroundResource(R.drawable.shape2);
        fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        if (!h3.isAdded()) {
            transaction.add(R.id.fragment_container, h3).commit();
            transaction.show(h3);
        }else {
            transaction.show(h3).commit();
        }
    }

    public void onClick(View btn) {
        String tag="";
        switch (btn.getId()){
            case R.id.btn1:
                Log.i(tag,"按钮1");
                init();
                // showFragment(h1);
                break;
            case R.id.btn2:
                Log.i(tag,"按钮2");
                init2();
                //  showFragment(h2);
                break;
            case R.id.btn3:
                Log.i(tag,"按钮3");
                init3();
                //  showFragment(h3);
                break;
        }
    }
    private void hideFragment(FragmentTransaction transaction){
        String tag1="";
        if(h1 != null){
            Log.i(tag1,"隐藏1");
            transaction.hide(h1);
        }
        if(h2 != null){
            Log.i(tag1,"隐藏2");
            transaction.hide(h2);
        }
        if(h3 != null){
            Log.i(tag1,"隐藏3");
            transaction.hide(h3);
        }
    }
    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-deleteTime>5000){
            Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
            deleteTime=System.currentTimeMillis();
        }
        else{  //五秒内再次点击删除，确认退出

            finish();
        }
    }

}

