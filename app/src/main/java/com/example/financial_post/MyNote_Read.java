package com.example.financial_post;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MyNote_Read extends AppCompatActivity {
    private String title;
    private String content;
    private TextView textView1;
    private TextView textView2;
    DBManager_Note manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_read);
        title = getIntent().getStringExtra("notetitle");
        textView1=findViewById(R.id.notetitle_read);
        textView2=findViewById(R.id.notecontent_read);
        manager= new DBManager_Note();
        content=manager.getNoteContent(title);
        textView1.setText(title);
        textView2.setText(content);
    }
    public void update_note(View btn){//传递标题
        Intent intent = new Intent();
        intent.setClass(MyNote_Read.this, MyNote_write.class);
        intent.putExtra("notetitle",title);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        Intent home = new Intent(this, MyNote.class);
        startActivity(home);
        finish();  //Mynote已finish
    }
}
