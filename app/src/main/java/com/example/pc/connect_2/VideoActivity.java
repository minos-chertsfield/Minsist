package com.example.pc.connect_2;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RadioGroup;

/*
视频播放模块
 */

public class VideoActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        final RadioGroup mNavigationBar = (RadioGroup) findViewById(R.id.navigation);   //单选按钮集

        final WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setMediaPlaybackRequiresUserGesture(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://101.132.130.60/Login/ChooseClass.jsp?type=1");
        mNavigationBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (mNavigationBar.getCheckedRadioButtonId())
                {
                    case R.id.seg1:
                        webView.loadUrl("http://101.132.130.60/Login/ChooseClass.jsp?type=1");
                        break;
                    case R.id.seg2:
                        webView.loadUrl("http://101.132.130.60/Login/ChooseClass.jsp?type=2");
                        break;
                    case R.id.seg3:
                        webView.loadUrl("http://101.132.130.60/Login/ChooseClass.jsp?type=3");
                        break;
                    case R.id.seg4:
                        webView.loadUrl("http://101.132.130.60/Login/ChooseClass.jsp?type=4");
                        break;
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                Intent intent = new Intent(VideoActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
