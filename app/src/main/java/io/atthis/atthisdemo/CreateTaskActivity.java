package io.atthis.atthisdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CreateTaskActivity extends AppCompatActivity {
    private WebView webview;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_task_detail);
        webview = (WebView) findViewById(R.id.webview1);
        webview.clearCache(true);
        Intent intent = getIntent();
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        webSettings.setAllowFileAccess(true); //设置可以访问文件
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        userInfo = new UserInfo(getIntent());
        String urls = "http://atthis.sushithedog.com?mode=Create&userid=" + userInfo.getId() + "&language=en" + "&authority=" + userInfo.getAuthority();
        webview.loadUrl(urls);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                setTitle("Loading");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                setTitle("Create A Task");
            }
        });
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
//                if (newProgress < 100) {
////                    setTitle(newProgress + "");
//                } else {
//
//                }
            }
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                if(message.equals("Close")){
                    finish();
                }else{
                    setTitle(message);
                }
                result.confirm();
                return true;
            }
        });
    }
}
