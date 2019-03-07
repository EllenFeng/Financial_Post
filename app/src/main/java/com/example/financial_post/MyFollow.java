package com.example.financial_post;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MyFollow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.origin_mine_layout);
        TextView tv=findViewById(R.id.title_page1);
        tv.setText("关注");
    }
    public void back(View btn){
        Intent home = new Intent(this, MainActivity.class);
        home.putExtra("class",3);
        startActivity(home);
        finish();
    }
    public void openmenu(View btn){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("").setMessage("确认是否要删除当前所有数据：").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setNegativeButton("否",null);
        builder.create().show();
    }
}
