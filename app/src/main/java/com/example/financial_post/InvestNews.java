package com.example.financial_post;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InvestNews extends AppCompatActivity implements Runnable{
    private ArrayList<HashMap<String, String>> listItems; // 存放文字、图片信息
    private Handler handler;
    private TextView tv;
    private ListView listView;
    private String data[]={"正在加载中......  "};
    private String dateStr;
    private String resource;
    private PatternMatch match;
    private String todayStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.originalpage);
        match=new PatternMatch();
        SharedPreferences sharedPreferences = getSharedPreferences("date", Activity.MODE_PRIVATE);
        resource = sharedPreferences.getString("resource","");
//        SharedPreferences sharedPreferences = getSharedPreferences("date", Activity.MODE_PRIVATE);
//        todayStr = sharedPreferences.getString("today_date","");
        tv=(TextView)findViewById(R.id.title_page);
        tv.setText("保险新闻");
        ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        int msgWhat = 3;
        Thread t = new Thread(this);
        t.start();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 5){
//                    List<String> retList = (List<String>) msg.obj;
//                    ListAdapter adapter = new ArrayAdapter<String>(InternationalNews.this,R.layout.support_simple_spinner_dropdown_item,retList);
                    List<HashMap<String, String>> retList = (List<HashMap<String, String>>) msg.obj;
                    SimpleAdapter adapter = new SimpleAdapter(InvestNews.this, retList, // listItems数据源
                            R.layout.list_item, // ListItem的XML布局实现
                            new String[] { "ItemTitle", "ItemOrigin","ItemResource","ItemLink" },
                            new int[] { R.id.itemTitle, R.id.itemOrigin,R.id.itemResource , R.id.itemLink});
                    listView.setAdapter(adapter);
                    Log.i("handler","reset list...");
                }
                super.handleMessage(msg);
            }
        };
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView title = (TextView) view.findViewById(R.id.itemTitle);
                String title2 = String.valueOf(title.getText());
                HashMap<String,String> map = (HashMap<String, String>)listView.getItemAtPosition(position);
                String links=map.get("ItemLink");
//                TextView link = (TextView) view.findViewById(R.id.itemLink);
//                String link2 = String.valueOf(link.getText());
                TextView date = (TextView) view.findViewById(R.id.itemOrigin);
                String date2 = String.valueOf(date.getText());
                Intent intent = new Intent();
                intent.setClass(InvestNews.this, NewsContent.class);
                intent.putExtra("title",title2);
                intent.putExtra("link",links);
                intent.putExtra("date",date2);
                startActivity(intent);
            }
        });
    }

    public void back(View btn){
        Intent home = new Intent(this, MainActivity.class);
        home.putExtra("class",1);
        startActivity(home);
        finish();
    }

    @Override
    public void run() {//线程
        SharedPreferences sharedPreferences = getSharedPreferences("date", Activity.MODE_PRIVATE);
        todayStr = sharedPreferences.getString("today_date","");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("thread","run.....");
        List<HashMap<String, String>> rateList = new ArrayList<HashMap<String, String>>();
        try {
            Document doc = Jsoup.connect("http://finance.sina.com.cn/money/insurance/").get();
            Elements titles = doc.select(".blk04_cont a");
            Elements titles1 = doc.select(".blk05_cont a");
            for (int i = 0; i < titles.size(); i+=1) {
                Element newstitle = titles.get(i);
                String titleStr = newstitle.text();
                String linkStr = newstitle.attr("href");
                if(titleStr.length()<1)
                    continue;
                HashMap<String, String> map = new HashMap<String, String>();
                int index=match.matchPattern(linkStr);
                if(index==-1)
                    dateStr=todayStr;
                else
                    dateStr=linkStr.substring(index,index+10);
                map.put("ItemTitle", titleStr);  //标题
                map.put("ItemOrigin", dateStr);  //日期
                map.put("ItemLink", linkStr);  //存储链接
                map.put("ItemResource",resource);
                rateList.add(map);
            }
            for (int i = 0; i < titles1.size(); i+=1) {
                Element newstitle = titles1.get(i);
                String titleStr = newstitle.text();
                String linkStr = newstitle.attr("href");
                if(titleStr.length()<1)
                    continue;
                HashMap<String, String> map = new HashMap<String, String>();
                int index=match.matchPattern(linkStr);
                if(index==-1)
                    dateStr=todayStr;
                else
                    dateStr=linkStr.substring(index,index+10);
                map.put("ItemTitle", titleStr);  //标题
                map.put("ItemOrigin", dateStr);  //日期
                map.put("ItemLink", linkStr);  //存储链接
                map.put("ItemResource",resource);
                rateList.add(map);
            }
        } catch (MalformedURLException e) {
            Log.e("www", e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("www", e.toString());
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(5);
        msg.obj = rateList;
        handler.sendMessage(msg);
        Log.i("thread","sendMessage.....");
    }
    @Override
    public void onBackPressed() {
        Intent home = new Intent(this, MainActivity.class);
        home.putExtra("class",1);
        startActivity(home);
        finish();
    }
}
