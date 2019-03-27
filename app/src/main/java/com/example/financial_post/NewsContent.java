package com.example.financial_post;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class NewsContent extends AppCompatActivity implements Runnable {
    private String title;
    private String link;
    private String originaldate;  //新闻发布日期
    private String todayStr;  //当前日期，用于收藏时使用
    private TextView tv, tv2;
    private Handler handler;
    private String data = "请稍后......  ";
    private SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);
        title = getIntent().getStringExtra("title");
        link = getIntent().getStringExtra("link");
        originaldate = getIntent().getStringExtra("date");  //新闻发布日期
        Log.i("data", title);
        Log.i("link", link);
        tv = (TextView) findViewById(R.id.newstitle);
        tv2 = (TextView) findViewById(R.id.newscontent);
        tv.setText(data);
        int msgWhat = 3;
        Thread t = new Thread(NewsContent.this);
        t.start();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 5) {
//                    List<String> content = (List<String>) msg.obj;
//                    tv2.setText((CharSequence) content);
                    String str = (String) msg.obj;
                    if (str.length() < 1) {
                        tv.setText(Html.fromHtml("<u>"+"详情"+"</u>"));
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent newslink = new Intent();
                                newslink.setClass(NewsContent.this, CompanyLink.class);
                                newslink.putExtra("link",link);
                                startActivity(newslink);
                            }
                        });
//                        tv.setText(link);
//                        tv.setAutoLinkMask(Linkify.WEB_URLS);
       //                 tv.setText("哎呀，你要找的文章不见啦！");
                    } else {
                        tv.setText(title);
                        tv2.setText(str);
                    }
                }
                super.handleMessage(msg);
            }
        };
    }

    //以下为调试数据库内容
//        @RequiresApi(api = Build.VERSION_CODES.O)
//        public void  mark_news(View btn){
//            String str = getCity();
//            database.close();
//            if (new File(DBHelper.DB_PATH +"/"+DBHelper.DB_NAME).exists())
//                Log.i("..","这个数据库已经存在了");
//            Toast.makeText(this,DBHelper.DB_PATH,Toast.LENGTH_SHORT).show();
//            Log.i("..","gggggggggggggg"+str);
//            tv.setText(str);
//        }
//        private String getCity() {
//            database = SQLiteDatabase.openOrCreateDatabase(DBHelper.DB_PATH + "/" + DBHelper.DB_NAME, null);
//            Cursor cursor = database.query("financial_data_lirun_2014",null,null,null,null,null,"代码",null);
//            String city= "";
//            if (cursor != null) {
//                while(cursor.moveToNext()){
//                    String name = cursor.getString(cursor.getColumnIndex("所在省市"));
//                    city=name;
//                }
//            }
//            return city;
//        }
    //
    public void mark_news(View btn){
        SharedPreferences sharedPreferences = getSharedPreferences("date", Activity.MODE_PRIVATE);
        todayStr = sharedPreferences.getString("today_date","");
        NewsItem item1=new NewsItem(title,link,originaldate,todayStr);
        DBManager manager=new DBManager();
        if(!manager.isMarkExisted(title)) {  //如果没有收藏过
            if(manager.countExceeded()==1)
                Toast.makeText(this,"收藏太多了哟，请前往收藏夹进行清理！",Toast.LENGTH_SHORT).show();
            else{
                manager.add(item1);
                Toast.makeText(this,"收藏成功！",Toast.LENGTH_SHORT).show();
            }
        }
        else
            Toast.makeText(this,"已经在收藏夹啦！",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void run() {
        Log.i("thread", "run.....");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        List<String> constr = new ArrayList<String>();
            String content1 = "";
            Message msg = handler.obtainMessage(5);
            try {
                Document doc = Jsoup.connect(link).get();
                Elements content = doc.select(".article p");
                for (int i = 0; i < content.size(); i += 1) {
                    Element newsCon = content.get(i);
                    String contentStr = newsCon.text() + "\n";
                    if (contentStr.length() < 1)
                        continue;
//                constr.add(contentStr);  //添加正文
                    content1 = content1 + contentStr;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            msg.obj = content1;
            //       msg.obj=constr;
            handler.sendMessage(msg);
    }
}

