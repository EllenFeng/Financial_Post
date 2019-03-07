package com.example.financial_post;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DataReport extends AppCompatActivity {
    private String reports[];
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_origin_list);
        textView=findViewById(R.id.title_page2);
        textView.setText("报告");
        reports=new String[]{"2002报告","2003报告","2004报告","2005报告","2006报告","2007报告","2008报告","2009报告","2010报告",
                "2011报告","2012报告","2013报告","2014报告","2015报告","2016报告","2017报告"};
        ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,reports);
        ListView listView = (ListView) findViewById(R.id.list_view_data);
        listView.setAdapter(adapter);
    }
    public void back(View btn){
        Intent home = new Intent(this, MainActivity.class);
        home.putExtra("class",2);
        startActivity(home);
        finish();
    }
}
