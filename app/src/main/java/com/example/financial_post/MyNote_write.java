package com.example.financial_post;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MyNote_write extends AppCompatActivity{
    private long deleteTime=0;
    private EditText editText1;
    private EditText editText2;
    private String originalTitle="";  //原有标题，如果新建的则为空，如果是编辑则为原来的标题，以便查看最后是否修改了标题，相应update table 因为标题是主键
//    private int exist;  //若为0就是新建的，不需要查找相应笔记填充进scrollview
    private String noteContent;
    private String todayStr;
    DBManager_Note manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_write);
        SharedPreferences sharedPreferences = getSharedPreferences("date", Activity.MODE_PRIVATE);
        todayStr = sharedPreferences.getString("today_date","");  //当天时间
        editText1=findViewById(R.id.notetitle_write);
        editText2=findViewById(R.id.notecontent_write);
        originalTitle=getIntent().getStringExtra("notetitle");
        manager= new DBManager_Note();
//        exist=getIntent().getIntExtra("exist",0);
        if(originalTitle.length()<1){  //新建的笔记
        }
        else{  //原有的笔记
            editText1.setText(originalTitle);
            noteContent=manager.getNoteContent(originalTitle);
            editText2.setText(noteContent);
        }
    }

    public int save_note(View btn){
        String title= String.valueOf(editText1.getText());
        String content=String.valueOf(editText2.getText());
        NoteItem item1=new NoteItem(title,content,todayStr);
        if(title.length()<1){  //检查标题是否为空
            Toast.makeText(this,"请设置笔记标题！",Toast.LENGTH_SHORT).show();
            return 1;
        }

        if(originalTitle.length()<1){  //如果是新建的文档的第一次保存
            manager.addNote(item1);
            Toast.makeText(this,"已保存！",Toast.LENGTH_SHORT).show();
            originalTitle=title;
            return 1;
        }
        if(!manager.isNoteExisted(title)) {  //如果没有这个标题的记录  说明这不是第一次在这里点击保存 但是标题变了 drop原先标题记录add新标题记录
                                              // 操作之后修改originaltitle
            manager.deleteNote(originalTitle);
            manager.addNote(item1);
            Toast.makeText(this,"已更新记录！",Toast.LENGTH_SHORT).show();
            originalTitle=title;
        }
        else{  //有标题记录 检查是否更新了content
            String content_origin=manager.getNoteContent(title);
            if(content.equals(content_origin)){  //内容没有变化 不能用content==
                Toast.makeText(this,"已经保存过了哟！",Toast.LENGTH_SHORT).show();
            }
            else{  //内容变化了 更新
                NoteItem item=new NoteItem(title,content,todayStr);
                manager.updateNote(item);
                Toast.makeText(this,"已更新内容！",Toast.LENGTH_SHORT).show();
            }

        }
        return 1;
    }

    //返回要返回Mynote 避免回到Mynote_read 同时要监听是否保存 再按一次退出 返回Mynote之后更新列表显示
    @Override
    public void onBackPressed(){
        String title= String.valueOf(editText1.getText());
        String content=String.valueOf(editText2.getText());
        String content_origin=manager.getNoteContent(title);
        if(content.equals(content_origin) && title.equals(originalTitle)){  //内容和标题没有变动
            Intent intent = new Intent();
            intent.setClass(MyNote_write.this,MyNote.class);
            startActivity(intent);
            finish();
        }
        else{  //有变动
            if(System.currentTimeMillis()-deleteTime>5000){
                Toast.makeText(this,"您还没有保存修改哟！再按一次可直接返回。",Toast.LENGTH_SHORT).show();
                deleteTime=System.currentTimeMillis();
            }
            else{  //五秒内再次点击删除 确认不保存退出
                Intent intent = new Intent();
                intent.setClass(MyNote_write.this,MyNote.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
