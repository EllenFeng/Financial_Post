package com.example.financial_post;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class MyMark extends AppCompatActivity implements AdapterView.OnItemLongClickListener{
    private SimpleAdapter adapter1;
    private List<HashMap<String, String>> detailList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.origin_mine_layout);
        TextView tv=findViewById(R.id.title_page1);
        ListView listView = (ListView) findViewById(R.id.mark_list);
        listView.setEmptyView(findViewById(R.id.nodata));
        tv.setText("收藏");
        DBManager manager=new DBManager();
        List<NewsItem> newslist=manager.listAllNews();    //新闻数据列表
        detailList = new ArrayList<HashMap<String, String>>();
        for(NewsItem i:newslist){
            String url=i.getLink();
            String title=i.getNewsTitle();
            String mark=i.getMarkDate();
            String origin=i.getOriginalDate();
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", title);
            map.put("ItemUrl", url);
            map.put("ItemMark", mark);
            map.put("ItemOrigin",origin);
            detailList.add(map);  //数据放入detaillist
        }
        adapter1 = new SimpleAdapter(MyMark.this,detailList, // listItems数据源
                R.layout.list_item, // ListItem的XML布局实现
                new String[] { "ItemTitle", "ItemOrigin","ItemMark","ItemUrl" },
                new int[] { R.id.itemTitle, R.id.itemOrigin,R.id.itemResource, R.id.itemLink });
        listView.setAdapter(adapter1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView title = (TextView) view.findViewById(R.id.itemTitle);
                String title2 = String.valueOf(title.getText());
                TextView link = (TextView) view.findViewById(R.id.itemLink);
                String link2 = String.valueOf(link.getText());
                TextView date = (TextView) view.findViewById(R.id.itemOrigin);
                String date2 = String.valueOf(date.getText());
                Intent intent = new Intent();
                intent.setClass(MyMark.this, NewsContent.class);
                intent.putExtra("title",title2);
                intent.putExtra("link",link2);
                intent.putExtra("date",date2);
                startActivity(intent);

            }
        });
        listView.setOnItemLongClickListener(this);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
        Log.i("","longclick");
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final DBManager manager=new DBManager();
        TextView title = (TextView) view.findViewById(R.id.itemTitle);
        final String title2 = String.valueOf(title.getText());
        builder.setTitle("提示").setMessage("确认是否要删除当前数据：").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                detailList.remove(position);
                manager.deleteNews(title2);
                adapter1.notifyDataSetChanged();
            }
        }).setNegativeButton("否",null);
        builder.create().show();
        return true;
    }
    public void back(View btn){
        Intent home = new Intent(this, MainActivity.class);
        home.putExtra("class",3);
        startActivity(home);
        finish();
    }
    public void openmenu(View btn){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final DBManager manager=new DBManager();
        if(detailList.size()<1) {
            builder.setTitle("提示").setMessage("您还没有收藏记录哟！");
            builder.create().show();
        }
        else{
            builder.setTitle("提示").setMessage("确认是否要删除所有收藏记录：").setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    detailList.clear();
                    manager.deleteAllNews();
                    adapter1.notifyDataSetChanged();
                }
            }).setNegativeButton("否",null);
            builder.create().show();
        }

    }
    @Override
    public void onBackPressed() {
        Intent home = new Intent(this, MainActivity.class);
        home.putExtra("class",3);
        startActivity(home);
        finish();
    }


}
