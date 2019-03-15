package com.example.financial_post;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Data_company_city_list extends AppCompatActivity implements Runnable{
    String city;
    private SimpleAdapter adapter1;
    private List<HashMap<String, String>> detailList;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.origin_mine_layout);
        city=getIntent().getStringExtra("city");
        TextView tv=findViewById(R.id.title_page1);
        final ListView listView = (ListView) findViewById(R.id.mark_list);
        tv.setText(city);
        int msgWhat = 3;
        Thread t = new Thread(this);
        t.start();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 5){
                    List<HashMap<String, String>> list = (List<HashMap<String, String>>) msg.obj;
                    adapter1 = new SimpleAdapter(Data_company_city_list.this,detailList, // listItems数据源
                            R.layout.list_item_company, // ListItem的XML布局实现
                            new String[] { "ItemName","ItemID", "ItemFullName", "ItemLink", "ItemAdd", "ItemCity"},
                            new int[] { R.id.itemName, R.id.itemID,R.id.itemFullName, R.id.comLink, R.id.comAdd, R.id.comCity });
                    listView.setAdapter(adapter1);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            TextView text = (TextView) view.findViewById(R.id.itemName);
                            TextView text1 = (TextView) view.findViewById(R.id.itemID);
                            TextView text2 = (TextView) view.findViewById(R.id.itemFullName);
                            TextView text3 = (TextView) view.findViewById(R.id.comLink);
                            TextView text4 = (TextView) view.findViewById(R.id.comAdd);
                            TextView text5 = (TextView) view.findViewById(R.id.comCity);
                            String name = String.valueOf(text.getText());
                            String ID = String.valueOf(text1.getText());
                            String fullname = String.valueOf(text2.getText());
                            String link = String.valueOf(text3.getText());
                            String add = String.valueOf(text4.getText());
                            String city = String.valueOf(text5.getText());
                            Intent intent = new Intent();
                            intent.setClass(Data_company_city_list.this, CompanyContent.class);
                            intent.putExtra("ID",ID);
                            intent.putExtra("Name",name);
                            intent.putExtra("FullName",fullname);
                            intent.putExtra("Link",link);
                            intent.putExtra("Add",add);
                            intent.putExtra("City",city);
                            startActivity(intent);
                        }
                    });
                }
                super.handleMessage(msg);
            }
        };
    }
    public void back(View btn){
        Intent home = new Intent(this, DataPlace.class);
        startActivity(home);
        finish();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DBManager_company dbManager_company=new DBManager_company();
        List<CompanyItem> comlist=dbManager_company.listCompany_city(city);
        detailList = new ArrayList<HashMap<String, String>>();
        for(CompanyItem i:comlist){
            String ID=i.getID();
            String name=i.getName();
            String fullname=i.getFullName();
            String link=i.getLink();
            String city=i.getCity();
            String add=i.getAdd();
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemID", ID);
            map.put("ItemName", name);
            map.put("ItemFullName", fullname);
            map.put("ItemAdd", add);
            map.put("ItemCity", city);
            map.put("ItemLink", link);
            Log.i("ID",ID);
            Log.i("name",name);
            detailList.add(map);  //数据放入detaillist
        }
        Message msg = handler.obtainMessage(5);
        msg.obj = detailList;
        handler.sendMessage(msg);
    }
    @Override
    public void onBackPressed() {
        Intent home = new Intent(this, DataPlace.class);
        startActivity(home);
        finish();
    }
}
