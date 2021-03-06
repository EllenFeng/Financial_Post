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

import com.example.financial_post.MainActivity;
import com.example.financial_post.R;

import java.util.Arrays;
import java.util.Collections;

public class DataYear extends AppCompatActivity {
    private String years[];
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_origin_list);
        textView=findViewById(R.id.title_page2);
        textView.setText("年度");
        years=new String[]{"2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017"};
        Arrays.sort(years, Collections.<String>reverseOrder());            //顺序从2017-2002
        ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,years);
        ListView listView = (ListView) findViewById(R.id.list_view_data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String year=years[position];
                Intent intent = new Intent();
                intent.setClass(DataYear.this, Company_info_fromDataYear.class);
                intent.putExtra("year",year);
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
