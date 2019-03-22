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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyNote extends AppCompatActivity implements AdapterView.OnItemLongClickListener{
    private TextView tv;
    private long deleteTime=0;
    private SimpleAdapter adapter1;
    private List<HashMap<String, String>> detailList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.origin_mine_layout);
        tv=findViewById(R.id.title_page1);
        ListView listView = (ListView) findViewById(R.id.mark_list);
        listView.setEmptyView(findViewById(R.id.nodata));
        tv.setText("笔记");
        final DBManager_Note manager= new DBManager_Note();
        List<NoteItem> notelist=manager.listAllNote();  //笔记数据列表
        detailList = new ArrayList<HashMap<String, String>>();
        for(NoteItem i:notelist){
            String title=i.getTitle();
            String date=i.getDate();
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", title);
            map.put("ItemDate", date);
            detailList.add(map);  //数据放入detaillist
        }
        adapter1 = new SimpleAdapter(MyNote.this,detailList, // listItems数据源
                R.layout.list_item, // ListItem的XML布局实现
                new String[] { "ItemTitle", "ItemDate" },
                new int[] { R.id.itemTitle, R.id.itemResource });
        listView.setAdapter(adapter1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView title = (TextView) view.findViewById(R.id.itemTitle);
                String title2 = String.valueOf(title.getText());
//                TextView date = (TextView) view.findViewById(R.id.itemResource);
 //               String date2 = String.valueOf(date.getText());
                Intent intent = new Intent();
                intent.setClass(MyNote.this, MyNote_Read.class);
                intent.putExtra("notetitle",title2);    //检查是否为空为空则为新建的，不为空则在read获取title显示并获取相应content
//                intent.putExtra("date",date2);  不需要传递时间 如果write里面有修改直接获取当天时间 否则不变
                startActivity(intent);
                finish();
            }
        });
        listView.setOnItemLongClickListener(this);
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
        Log.i("","longclick");
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final DBManager_Note manager=new DBManager_Note();
        TextView title = (TextView) view.findViewById(R.id.itemTitle);
        final String title2 = String.valueOf(title.getText());
        builder.setTitle("提示").setMessage("确认是否要删除当前数据：").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                detailList.remove(position);
                manager.deleteNote(title2);
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
        final String[] str_menu=new String[]{"新建","删除"};
        final DBManager_Note manager=new DBManager_Note();
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setItems(str_menu,new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){  //新建
                    Intent writenote=new Intent();
                    writenote.setClass(MyNote.this, MyNote_write.class);
                    writenote.putExtra("notetitle","");  //新建的时候标题为空，区别于编辑已有的笔记（标题不为空），并且考虑用户修改标题后update
                                                                          //table 因为标题是主键
                    startActivity(writenote);
                    finish();
                }
                else{  //删除
                    if(detailList.size()<1) {
                        Toast.makeText(MyNote.this,"您还没有笔记记录哟！",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(System.currentTimeMillis()-deleteTime>5000){
                            Toast.makeText(MyNote.this,"再按一次删除全部笔记",Toast.LENGTH_SHORT).show();
                            deleteTime=System.currentTimeMillis();
                        }
                        else{  //五秒内再次点击删除，确认删除所有记录
                            detailList.clear();
                            manager.deleteAllNote();
                            adapter1.notifyDataSetChanged();
                        }
                    }

                }
            }
        });
        builder.create().show();

    }
    @Override
    public void onBackPressed() {
        Intent home = new Intent(this, MainActivity.class);
        home.putExtra("class",3);
        startActivity(home);
        finish();
    }
}
