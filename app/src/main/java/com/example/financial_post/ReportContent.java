package com.example.financial_post;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;

public class ReportContent extends AppCompatActivity {
    String year;
//    Dialog dialog1,dialog2,dialog3,dialog4,dialog5,dialog6,dialog7,dialog8;
//    Dialog[] dialogs={dialog1,dialog2,dialog3,dialog4,dialog5,dialog6,dialog7,dialog8};
//    ImageView mImageView1,mImageView2,mImageView3,mImageView4,mImageView5,mImageView6,mImageView7,mImageView8;
//    ImageView[] mImageViews={mImageView1,mImageView2,mImageView3,mImageView4,mImageView5,mImageView6,mImageView7,mImageView8};
    int index;
    String[] years={"2013","2014","2015","2016","2017","历年数据"};
    TextView textView;
    ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8;
    ImageView[] imageViews={imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_content);
        year=getIntent().getStringExtra("year");
        textView=findViewById(R.id.title_report);
        textView.setText(year+"报告");
        init();

    }

    public  void init(){
        index = Arrays.binarySearch(years,year);
        Log.i("index", String.valueOf(index));
        Integer[] profits={R.drawable.profit2013,R.drawable.profit2014,R.drawable.profit2015,R.drawable.profit2016,R.drawable.profit2017,R.drawable.compare_city2016};  //利润
        Integer[] improvements={R.drawable.improvement2013,R.drawable.improvement2014,R.drawable.improvement2015,R.drawable.improvement2016,R.drawable.improvement2017,R.drawable.compare_city2013};  //成长
        Integer[] makemoneys={R.drawable.makemoney2013,R.drawable.makemoney2014,R.drawable.makemoney2015,R.drawable.makemoney2016,R.drawable.makemoney2017,R.drawable.pic20162017};  //盈利
        Integer[] managements={R.drawable.management2013,R.drawable.management2014,R.drawable.management2015,R.drawable.management2016,R.drawable.management2017,R.drawable.compare_city2014};  //营运
        Integer[] balances={R.drawable.balance2013,R.drawable.balance2014,R.drawable.balance2015,R.drawable.balance2016,R.drawable.balance2017,R.drawable.compare_city2017};  //资产负债
        Integer[] overalls={R.drawable.overall2013,R.drawable.overall2014,R.drawable.overall2015,R.drawable.overall2016,R.drawable.overall2017,R.drawable.pic2002_2017};  //业绩概览
        Integer[] cashflows={R.drawable.cashflow2013,R.drawable.cashflow2014,R.drawable.cashflow2015,R.drawable.cashflow2016,R.drawable.cashflow2017,R.drawable.pic20142015};  //现金流量
        Integer[] paybacks={R.drawable.payback2013,R.drawable.payback2014,R.drawable.payback2015,R.drawable.payback2016,R.drawable.payback2017,R.drawable.compare_city2015};  //偿债
        imageViews[0]=findViewById(R.id.report_1);
        imageViews[1]=findViewById(R.id.report_2);
        imageViews[2]=findViewById(R.id.report_3);
        imageViews[3]=findViewById(R.id.report_4);
        imageViews[4]=findViewById(R.id.report_5);
        imageViews[5]=findViewById(R.id.report_6);
        imageViews[6]=findViewById(R.id.report_7);
        imageViews[7]=findViewById(R.id.report_8);

        imageViews[0].setImageResource(overalls[index]);
        imageViews[1].setImageResource(makemoneys[index]);
        imageViews[2].setImageResource(cashflows[index]);
        imageViews[3].setImageResource(balances[index]);
        imageViews[4].setImageResource(profits[index]);
        imageViews[5].setImageResource(paybacks[index]);
        imageViews[6].setImageResource(managements[index]);
        imageViews[7].setImageResource(improvements[index]);



    }


    @Override
    public void onBackPressed() {
        Intent home = new Intent(this, DataReport.class);
        startActivity(home);
        finish();
    }
}
