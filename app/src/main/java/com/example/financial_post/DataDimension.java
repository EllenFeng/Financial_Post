package com.example.financial_post;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DataDimension extends AppCompatActivity {
    private String dims[];
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_origin_list);
        textView=findViewById(R.id.title_page2);
        textView.setText("维度");
        dims=new String[]{"业绩概览","资产负债表","利润表","现金流量表","盈利能力","偿债能力","成长能力","营运能力"};
        ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dims);
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
