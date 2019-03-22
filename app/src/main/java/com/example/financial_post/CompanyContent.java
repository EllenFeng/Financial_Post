package com.example.financial_post;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyContent extends AppCompatActivity implements Runnable{
    private String ID,address,name,fullname,link,city;
    private TextView com_name,com_ID,stock_price,priec_change,price_change_per,jinkai,zuoshou,zuigao,zuidi;
    private TextView chengjiaoliang,chengjiaoe,liangbi,zhouzuigao,zhouzuidi;
    private TextView com_fullname,president,com_manager,phone,com_city,com_add,datalink,com_link,com_asset,issuedate;
    private TextView com_zongguben,com_liutongguben,mainjob,time;
    private ImageView price_updown;
    private LinearLayout price_change_layout;
    private ScrollView scrollview;
    private Handler handler;
    private int where;
    private String data = "请稍后......  ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_content);
        init();
        com_name.setText(data);  //请稍后
        ID = getIntent().getStringExtra("ID");
        name = getIntent().getStringExtra("Name");
        fullname = getIntent().getStringExtra("FullName");
        link = getIntent().getStringExtra("Link");
        address = getIntent().getStringExtra("Add");
        city = getIntent().getStringExtra("City");
        where = getIntent().getIntExtra("where",1);
        int msgWhat = 3;
        Thread t = new Thread(CompanyContent.this);
        t.start();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 5){
                    HashMap<String, String> map = (HashMap<String, String>) msg.obj;
                    Log.i("handler","map...");
                    setView(map);
                }
                super.handleMessage(msg);
            }
        };
    }
    public void init(){
        scrollview=findViewById(R.id.scrollView_comcontent);
        com_name=findViewById(R.id.com_name);
        com_ID=findViewById(R.id.company_ID);
        stock_price=findViewById(R.id.stock_price);
        price_updown=findViewById(R.id.price_updown_image);
        price_change_layout=findViewById(R.id.price_change_layout);
        priec_change=findViewById(R.id.price_change);
        price_change_per=findViewById(R.id.price_change_per);
        jinkai=findViewById(R.id.jinkai);
        zuoshou=findViewById(R.id.zuoshou);
        zuigao=findViewById(R.id.zuigao);
        zuidi=findViewById(R.id.zuidi);
        chengjiaoliang=findViewById(R.id.chengjiaoliang);
        chengjiaoe=findViewById(R.id.chengjiaoe);
        liangbi=findViewById(R.id.liangbi);
        zhouzuigao=findViewById(R.id.zhouzuigao);
        zhouzuidi=findViewById(R.id.zhouzuidi);
        time=findViewById(R.id.time);
        com_fullname=findViewById(R.id.com_fullname);
        mainjob=findViewById(R.id.mainjob);
        president=findViewById(R.id.com_president);
        com_manager=findViewById(R.id.com_manager);
        phone=findViewById(R.id.com_phone);
        com_city=findViewById(R.id.com_city);
        com_add=findViewById(R.id.com_address);
        datalink=findViewById(R.id.com_datalink);
        com_link=findViewById(R.id.com_link);
        com_asset=findViewById(R.id.com_asset);
        issuedate=findViewById(R.id.com_issuedate);
        com_zongguben=findViewById(R.id.com_zongguben);
        com_liutongguben=findViewById(R.id.com_liutongguben);

    }
    public void openmenu(View btn){
        final String[] str_menu=new String[]{"刷新","详情","数据","关注"};
        final String[] str_menu1=new String[]{"刷新","详情","数据","取消关注"};
        final DBManager_company manager1=new DBManager_company();

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setItems(manager1.isFollowExisted(ID)?str_menu1:str_menu,new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:  //刷新
                        Intent intent = new Intent();
                        intent.setClass(CompanyContent.this, CompanyContent.class);
                        intent.putExtra("ID",ID);
                        intent.putExtra("Name",name);
                        intent.putExtra("FullName",fullname);
                        intent.putExtra("Link",link);
                        intent.putExtra("Add",address);
                        intent.putExtra("City",city);
                        if(where==2)
                            intent.putExtra("where",2);
                        startActivity(intent);
                        finish();
                        break;
                    case 1:  //详情
                        Intent comlink = new Intent();
                        comlink.setClass(CompanyContent.this, CompanyLink.class);
                        comlink.putExtra("link",link);
                        startActivity(comlink);
                        break;
                    case 2:  //数据
                        Intent info=new Intent();
                        info.setClass(CompanyContent.this,company_online_info.class);
                        info.putExtra("ID",ID);
                        info.putExtra("link",link);
                        info.putExtra("name",name);
                        startActivity(info);
                        break;
                    case 3:  //关注or取消关注
                        CompanyItem item1=new CompanyItem(ID,name,fullname,city,link,address);
                        DBManager_company manager=new DBManager_company();
                        if(!manager.isFollowExisted(ID)){  //没有关注过
                            manager.addFollow(item1);
                            Toast.makeText(CompanyContent.this,"关注成功！",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            manager.deleteFollow(ID);
                            Toast.makeText(CompanyContent.this,"已取消关注！",Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });
        builder.create().show();

    }
    public void setView(HashMap<String, String> map){
        scrollview.setVisibility(View.VISIBLE);
        com_name.setText(map.get("com_name"));
        stock_price.setText(map.get("stock_price"));
        com_ID.setText(map.get("com_ID"));
        if(!((map.get("stock_price").equals("未上市"))||(map.get("stock_price").equals("已退市")))){ //上市公司
            price_change_layout.setVisibility(View.VISIBLE);
            price_updown.setVisibility(View.VISIBLE);
            priec_change.setText(map.get("price_change"));
            price_change_per.setText(map.get("price_change_per"));
            if(((map.get("price_change")).substring(0,1)).equals("-")){  //下跌
                price_updown.setBackgroundResource(R.drawable.down_arrow);
                stock_price.setTextColor(getColor(R.color.green));
                priec_change.setTextColor(getColor(R.color.green));
                price_change_per.setTextColor(getColor(R.color.green));
            }
            else{  //上涨
                price_updown.setBackgroundResource(R.drawable.up_arrow);
                stock_price.setTextColor(getColor(R.color.red));
                priec_change.setTextColor(getColor(R.color.red));
                price_change_per.setTextColor(getColor(R.color.red));
            }
        }
        jinkai.setText(map.get("jinkai"));
        zuoshou.setText(map.get("zuoshou"));
        zuigao.setText(map.get("zuigao"));
        zuidi.setText(map.get("zuidi"));
        chengjiaoliang.setText(map.get("chengjiaoliang"));
        chengjiaoe.setText(map.get("chengjiaoe"));
        liangbi.setText(map.get("liangbi"));
        zhouzuigao.setText(map.get("zhouzuigao"));
        zhouzuidi.setText(map.get("zhouzuidi"));
        time.setText(map.get("time"));
        mainjob.setText(map.get("mainJob"));
        com_fullname.setText(map.get("com_fullname"));
        com_city.setText(map.get("com_city"));
        com_add.setText(map.get("com_add"));
        datalink.setText(map.get("datalink"));
        president.setText(map.get("president"));
        com_manager.setText(map.get("com_manager"));
        phone.setText(map.get("phone"));
        com_link.setText(map.get("com_link"));
        com_asset.setText(map.get("com_asset"));
        issuedate.setText(map.get("issuedate"));
        com_zongguben.setText(map.get("com_zongguben"));
        com_liutongguben.setText(map.get("com_liutongguben"));
    }
    @Override
    public void run() {
        Log.i("thread", "run.....");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashMap<String, String> map = new HashMap<String, String>();
        String stock_price="--",price_change="--",price_change_per="--";
        String jinkai="今开：--",zuoshou="昨收：--",zuigao="最高：--",zuidi="最低：--",chengjiaoliang="成交量：--",chengjiaoe="成交额：--",liangbi="量比：--";
        String zhouzuigao="52周最高：--",zhouzuidi="52周最低：--",time="";
        String com_name="",com_ID="",com_fullname="",president="",com_manager="",phone="",com_city="",com_add="",datalink="";
        String com_link="",com_asset="",issuedate="",com_zongguben="",com_liutongguben="",mainJob="";
        try {
 //           String html=getHTML(link,"utf-8");
       //     Document doc = Jsoup.connect(link).maxBodySize(Integer.MAX_VALUE).get();
            Document doc=Jsoup.connect(link).get();
            Elements issue = doc.select(".stock_detail .price span");  //看是否上市了
            Elements com_info = doc.select(".col_r2 p");  //公司信息
            String is_issued = issue.get(0).className();
            Log.i("issuetext",issue.get(0).toString());  //html内容不全
            if(!((is_issued).equals("price_arrow"))){  //退市或未上市
                Log.i("issue","not issued");
                stock_price=issue.get(0).text();
            }
            else{  //如果上市了
                Log.i("issue","issued");
                String code=link.substring(28,35);//请求数据部分 因为原始link中数据为script src动态加载 直接获取html没有具体数据
                String link2="http://api.money.126.net/data/feed/"+code;
                String html=getHTML(link2,"utf-8").trim();
                String info_data=html.substring(21,html.length()-1);  //去除首尾不符合json格式的部分
                Log.i("data",info_data);
                DecimalFormat df=new DecimalFormat("##########.00");  //限制保存两位小数
                JSONObject jsonObject=new JSONObject(info_data);
                JSONObject info=jsonObject.getJSONObject(code);
                Elements data1 = doc.select(".stock_detail strong");
                Elements data3 = doc.select(".stock_detail .stock_bref span");
                stock_price=info.getString("price");
                float i=Float.parseFloat(stock_price);
                i=Float.parseFloat(df.format(i));
                stock_price=String.valueOf(i);
                Log.i("stock_price",stock_price);
                price_change=info.getString("updown");
                price_change_per=info.getString("percent");
                i=Float.parseFloat(price_change_per)*100;
                i=Float.parseFloat(df.format(i));   //限制保存两位小数
                price_change_per=String.valueOf(i)+"%";
                Log.i("i",String.valueOf(i)+"%");
                jinkai="今开："+info.getString("open");
                zuoshou="昨收："+info.getString("yestclose");
                zuigao="最高："+info.getString("high");
                zuidi="最低："+info.getString("low");
                chengjiaoliang=info.getString("volume");
                i=Float.parseFloat(chengjiaoliang)/1000000;  //一手为一百股
                i=Float.parseFloat(df.format(i));
                chengjiaoliang="成交量："+String.valueOf(i)+"万手";
                chengjiaoe=info.getString("turnover");
                i=Float.parseFloat(chengjiaoe)/100000000;
                i=Float.parseFloat(df.format(i));
                chengjiaoe="成交额："+String.valueOf(i)+"亿";
                liangbi="量比："+data1.get(7).text();
                zhouzuigao="52周最高："+data3.get(3).text();
                zhouzuidi="52周最低："+data3.get(4).text();
                time=info.getString("time");
            }
            com_ID=ID;
            com_fullname="公司全称："+fullname;
            com_name=name;
            com_city="所在城市："+city;
            com_add="办公地址："+address;
            datalink="数据链接："+link;
            mainJob=com_info.get(1).text();
            president=com_info.get(2).text();
            Log.i("president",president);
            com_manager=com_info.get(3).text();
            phone=com_info.get(4).text();
            com_link=com_info.get(6).text();
            com_asset=com_info.get(7).text();
            issuedate=com_info.get(8).text();
            com_zongguben=com_info.get(9).text();
            com_liutongguben=com_info.get(10).text();
            Log.i("com_liutongguben",com_liutongguben);
            map.put("stock_price",stock_price);
            map.put("price_change",price_change);
            map.put("price_change_per",price_change_per);
            map.put("jinkai",jinkai);
            map.put("zuoshou",zuoshou);
            map.put("zuigao",zuigao);
            map.put("zuidi",zuidi);
            map.put("chengjiaoliang",chengjiaoliang);
            map.put("chengjiaoe",chengjiaoe);
            map.put("liangbi",liangbi);
            map.put("zhouzuigao",zhouzuigao);
            map.put("zhouzuidi",zhouzuidi);
            map.put("com_ID","("+com_ID+")");
            map.put("com_fullname",com_fullname);
            map.put("com_name",com_name);
            map.put("com_city",com_city);
            map.put("com_add",com_add);
            map.put("datalink",datalink);
            map.put("president",president);
            map.put("com_manager",com_manager);
            map.put("phone",phone);
            map.put("com_link",com_link);
            map.put("com_asset",com_asset);
            map.put("issuedate",issuedate);
            map.put("com_zongguben",com_zongguben);
            map.put("com_liutongguben",com_liutongguben);
            map.put("mainJob",mainJob);
            map.put("time",time);
            Log.i("mainJob",mainJob);
        } catch (MalformedURLException e) {
            Log.e("www", e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("www", e.toString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(5);
        msg.obj = map;
        handler.sendMessage(msg);
        Log.i("thread","sendMessage.....");
    }
    public String getHTML(String aUrl, String aEncode) throws Exception {
        URL url = new URL(aUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200){
            InputStream inputStream = conn.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while( (len = inputStream.read(buffer)) != -1){
                outStream.write(buffer, 0, len);
            }
            String htmlStr = new String(outStream.toByteArray(), aEncode);
            inputStream.close();
            outStream.close();
            return htmlStr;
        }
        return null;
    }
    @Override
    public void onBackPressed() {
        if(where==2){
            Intent intent = new Intent(this, MyFollow.class);
            startActivity(intent);
            finish();
        }
        else
            finish();
//        else{
//            Intent intent = new Intent(this, DataSearch.class);
//            startActivity(intent);
//            finish();
//        }

    }
}
