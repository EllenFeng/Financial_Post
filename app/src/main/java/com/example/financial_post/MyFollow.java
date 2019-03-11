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

public class MyFollow extends AppCompatActivity implements AdapterView.OnItemLongClickListener{
    private SimpleAdapter adapter1;
    private List<HashMap<String, String>> detailList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.origin_mine_layout);
        TextView tv=findViewById(R.id.title_page1);
        ListView listView = (ListView) findViewById(R.id.mark_list);
        listView.setEmptyView(findViewById(R.id.nodata));
        tv.setText("关注");
        DBManager_company manager=new DBManager_company();
        List<CompanyItem> followlist=manager.listAllfollow();    //关注数据列表
        detailList = new ArrayList<HashMap<String, String>>();
        for(CompanyItem i:followlist){
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
        adapter1 = new SimpleAdapter(MyFollow.this,detailList, // listItems数据源
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
                intent.setClass(MyFollow.this, CompanyContent.class);
                intent.putExtra("ID",ID);
                intent.putExtra("Name",name);
                intent.putExtra("FullName",fullname);
                intent.putExtra("Link",link);
                intent.putExtra("Add",add);
                intent.putExtra("City",city);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(this);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
        Log.i("","longclick");
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final DBManager_company manager=new DBManager_company();
        TextView text1 = (TextView) view.findViewById(R.id.itemID);
        final String ID = String.valueOf(text1.getText());
        builder.setTitle("提示").setMessage("确认是否要删除当前数据：").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                detailList.remove(position);
                manager.deleteFollow(ID);
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
        final DBManager_company manager=new DBManager_company();
        if(detailList.size()<1) {
            builder.setTitle("提示").setMessage("您还没有关注记录哟！");
            builder.create().show();
        }
        else{
            builder.setTitle("提示").setMessage("确认是否要删除所有关注记录：").setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    detailList.clear();
                    manager.deleteAllFollow();
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
