package com.example.financial_post;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DataPlace extends AppCompatActivity {
    private String places[];
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_origin_list);
        textView = findViewById(R.id.title_page2);
        textView.setText("地区");
        places = new String[]{"黑龙江", "内蒙古", "安徽", "北京",
                "湖北", "湖南", "吉林", "江苏", "江西", "辽宁", "宁夏", "青海", "福建", "山东", "山西", "陕西",
                "上海", "四川", "天津", "西藏", "新疆", "云南", "浙江", "甘肃",
                "重庆", "广东", "广西", "贵州", "海南", "河北", "河南","香港"};
        ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,places);
        ListView listView = (ListView) findViewById(R.id.list_view_data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String city=places[position];
                Intent intent = new Intent();
                intent.setClass(DataPlace.this, Data_company_city_list.class);
                intent.putExtra("city",city);
                startActivity(intent);
                finish();
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
