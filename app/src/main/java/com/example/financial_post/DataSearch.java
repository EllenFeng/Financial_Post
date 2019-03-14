package com.example.financial_post;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataSearch extends AppCompatActivity {
    private SimpleAdapter adapter1;
    private List<HashMap<String, String>> detailList;
    TextView textView,textView2;
    EditText editText;
    ImageView imageView;
    ListView listView;
    DBManager_company dbManager_company;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        textView=findViewById(R.id.title_search);
        textView.setText("搜索");
        dbManager_company=new DBManager_company();
        editText=findViewById(R.id.edittext_search);
        imageView=findViewById(R.id.imageView_remove);
        textView2=findViewById(R.id.textView_search);
        listView=findViewById(R.id.listview_search);
        init();
    }
    public void init(){
        imageView.setOnClickListener(new View.OnClickListener() {  //清空图标
            @Override
            public void onClick(View v) {
                editText.setText("");  //把EditText内容设置为空
                listView.setVisibility(View.GONE);  //把ListView隐藏
            }
        });

        editText.addTextChangedListener(new TextWatcher() {  //监听输入框
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}//文本改变之前执行
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  //文本改变的时候执行
                if ((s.length() <1 ) || s==null){ //如果长度为0
                    imageView.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);
                } else {  //长度不为0
                    imageView.setVisibility(View.VISIBLE);
                    showView();
                }
            }
            public void afterTextChanged(Editable s) { }  //文本改变之后执行
        });

        textView2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editText.getText().toString().trim())){   //没有输入
                    Toast.makeText(DataSearch.this,"请输入搜索内容！",Toast.LENGTH_SHORT).show();
                    return;
                }
                String text=String.valueOf(editText.getText());
                Log.i("edittext",text);
                List<CompanyItem> list=dbManager_company.listAllMatch(text);
                if(list.size()<1){  //没有匹配的记录
                    Toast.makeText(DataSearch.this,"对不起，找不到您搜索的内容！",Toast.LENGTH_SHORT).show();
                    return;
                }
                detailList = new ArrayList<HashMap<String, String>>();
                for(CompanyItem i:list){
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
                adapter1 = new SimpleAdapter(DataSearch.this,detailList, // listItems数据源
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
                        intent.setClass(DataSearch.this, CompanyContent.class);
                        intent.putExtra("ID",ID);
                        intent.putExtra("Name",name);
                        intent.putExtra("FullName",fullname);
                        intent.putExtra("Link",link);
                        intent.putExtra("Add",add);
                        intent.putExtra("City",city);
                        intent.putExtra("where",1);
                        startActivity(intent);
                    }
                });
            }
        });
    }
    public void showView(){
        String name1=String.valueOf(editText.getText());
        List<CompanyItem> companylist=dbManager_company.listLimitMatch(name1);    //匹配的部分数据列表
        detailList = new ArrayList<HashMap<String, String>>();
        for(CompanyItem i:companylist){
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
        adapter1 = new SimpleAdapter(DataSearch.this,detailList, // listItems数据源
                R.layout.list_item_company, // ListItem的XML布局实现
                new String[] { "ItemName","ItemID", "ItemFullName", "ItemLink", "ItemAdd", "ItemCity"},
                new int[] { R.id.itemName, R.id.itemID,R.id.itemFullName, R.id.comLink, R.id.comAdd, R.id.comCity });
        listView.setAdapter(adapter1);
        listView.setVisibility(View.VISIBLE);
        adapter1.notifyDataSetChanged();
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
                intent.setClass(DataSearch.this, CompanyContent.class);
                intent.putExtra("ID",ID);
                intent.putExtra("Name",name);
                intent.putExtra("FullName",fullname);
                intent.putExtra("Link",link);
                intent.putExtra("Add",add);
                intent.putExtra("City",city);
                intent.putExtra("where",1);
                startActivity(intent);
            }
        });
    }
    public void back(View btn){
        Intent home = new Intent(this, MainActivity.class);
        home.putExtra("class",2);
        startActivity(home);
        finish();
    }
    @Override
    public void onBackPressed() {
        Intent home = new Intent(this, MainActivity.class);
        home.putExtra("class",2);
        startActivity(home);
        finish();
    }
}