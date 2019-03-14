package com.example.financial_post;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class CompanyLink extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weblayout);
        WebView myWebView = (WebView) findViewById(R.id.webView);
        myWebView.loadUrl(getIntent().getStringExtra("link"));
    }
}
