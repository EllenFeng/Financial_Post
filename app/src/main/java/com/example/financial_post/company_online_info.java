package com.example.financial_post;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class company_online_info extends AppCompatActivity implements Runnable{
 //   GridView gridView;
    MyGridView gridView;
    TextView textView;
    private Handler handler;
    private String ID,link,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_online_info);
    //    gridView=new MyGridView(this);
        gridView=(MyGridView)findViewById(R.id.gridview);
        gridView.setNumColumns(6);
        textView=findViewById(R.id.company_info);
        textView.setText("加载中...");
        ID=getIntent().getStringExtra("ID");
        link=getIntent().getStringExtra("link");
        name=getIntent().getStringExtra("name");
        int msgWhat = 3;
        Thread t = new Thread(company_online_info.this);
        t.start();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 5){
                    textView.setText(name);
 //                   List<HashMap<String, String>> datalist = (List<HashMap<String, String>>) msg.obj;
                    final List<String> retList = (List<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(company_online_info.this,R.layout.support_simple_spinner_dropdown_item,retList);
                    Log.i("handler","map...");
//                    SimpleAdapter adapter = new SimpleAdapter(company_online_info.this,datalist, android.R.layout.simple_list_item_1,
//                            new String[] {"data"},new int[] {android.R.id.text1});;
                    gridView.setAdapter(adapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String a=retList.get(position);
                            Toast.makeText(company_online_info.this,a,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                super.handleMessage(msg);
            }
        };
    }
    public void openmenu(View btn){
        final String[] str_menu=new String[]{"更多","可视化"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setItems(str_menu,new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:  //更多
                        Intent intent = new Intent();
                        intent.setClass(company_online_info.this, Company_overall_info.class);
                        intent.putExtra("ID",ID);
                        intent.putExtra("Name",name);
                        startActivity(intent);
                        break;
                    case 1:  //可视化
                        break;

                }
            }
        });
        builder.create().show();

    }
    @Override
    public void run() {
        Log.i("thread", "run.....");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    //    List<HashMap<String, String>> data1 = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();
        List<String> data1=new ArrayList<String>();
        data1.add(" ");
        //map.put("data"," ");

  //      data1.add(map);
        try {
            Document doc = Jsoup.connect(link).get();
            Elements date = doc.select(".fund_analys th");
            Elements com_data = doc.select(".fund_analys tr td");
            for (int i = 1; i < date.size(); i+=1) {
    //            HashMap<String, String> map1 = new HashMap<String, String>();
                String a = date.get(i).text();
     //           map.put("data",a);
                Log.i("a",a);
                data1.add(a);
     //           data1.add(map1);
            }

            for (int i = 0; i < com_data.size(); i+=1) {
        //        HashMap<String, String> map1 = new HashMap<String, String>();
                String a = com_data.get(i).text();
//                if(a.length()>10){
//                    a=a.substring(0,10)+"\n"+a.substring(10);
//                }
          //      map.put("data",a);
                data1.add(a);
            //    data1.add(map1);
            }
        } catch (MalformedURLException e) {
            Log.e("www", e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("www", e.toString());
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(5);
        msg.obj = data1;
        handler.sendMessage(msg);
        Log.i("thread","sendMessage.....");


    }
}
