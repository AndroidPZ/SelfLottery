package zhcw.lottery.znzd.com.selflottery.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import zhcw.lottery.znzd.com.selflottery.R;

/**
 * Created by xpz on 2018/11/13.
 */

public class WebViewUtil {
    @SuppressLint("JavascriptInterface")
    public void ClientWebView(WebView mWebView, final ProgressBar mProgress, String url, final Context xCont,
                              final ImageView ivLoading, final LinearLayout llLoading,
                              LinearLayout llTitleItem){
//        WebSettings webSettings = xCont.getSettings();
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); //支持js
        webSettings.setAllowFileAccess(true);// 是否可访问本地文件，默认值 true
        webSettings.setLoadWithOverviewMode(true);//自适应屏幕
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//动态更新内容
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setSupportZoom(false);// 是否支持缩放
        webSettings.setBuiltInZoomControls(true);// 设置出现缩放工具
        webSettings.setUseWideViewPort(true);//扩大比例的缩放
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //自适应屏幕
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);// 是否可用Javascript(window.open)打开窗口，默认值 false
        mWebView.setWebChromeClient(new WebChromeClient());// 设置可现实js的alert弹窗
        mWebView.addJavascriptInterface(new DemoJavaScriptInterface((Activity)xCont,  mWebView,llTitleItem), "android");


        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                try {
                    if (newProgress<80){
                        if (xCont!=null){
                            if (Util.isOnMainThread()) {
                                Glide.with(xCont)
                                        .asGif()
                                        .load(R.mipmap.webview_loading_start)
                                        .into(ivLoading);
                            }
                        }else {
                            return;
                        }

                    }else {
                        if (xCont!=null){
                            if (Util.isOnMainThread()){
                                Glide.with(xCont)
                                        .asGif()
                                        .load(R.mipmap.webview_loading_end)
                                        .into(ivLoading);
                            }
                        }else {
                            return;
                        }

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                mProgress.setProgress(newProgress);
                if (newProgress == 100) {
                    mProgress.setVisibility(View.GONE);
                    llLoading.setVisibility(View.GONE);
                }
            }});

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.loadUrl(url);
    }

    /**
     * webView显示标签
     * @param webView
     * @param progressBar
     * @param h5Url
     * @param context
     */
    public void onLabelling(WebView webView, final ProgressBar progressBar, String h5Url, Context context) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }});
        webView.loadDataWithBaseURL(null, h5Url, "text/html", "utf-8", null);
        Spanned fromHtml = android.text.Html.fromHtml(h5Url);
        TextView textView = new TextView(context);
        textView.setText(fromHtml);
    }
    @SuppressLint("JavascriptInterface")
    public void ClientWebView2(WebView mWebView, final ProgressBar mProgress, String url, final Context xCont,
                               final ImageView ivLoading, final LinearLayout llLoading){
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); //支持js
        webSettings.setAllowFileAccess(true);// 是否可访问本地文件，默认值 true
        webSettings.setLoadWithOverviewMode(true);//自适应屏幕
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//动态更新内容
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setSupportZoom(true);// 是否支持缩放
        webSettings.setBuiltInZoomControls(true);// 设置出现缩放工具
        webSettings.setUseWideViewPort(true);//扩大比例的缩放
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //自适应屏幕
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);// 是否可用Javascript(window.open)打开窗口，默认值 false
        mWebView.setWebChromeClient(new WebChromeClient());// 设置可现实js的alert弹窗
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress<80){
                    Glide.with(xCont)
                            .asGif()
                            .load(R.mipmap.webview_loading_start)
                            .into(ivLoading);
                }else {
                    Glide.with(xCont)
                            .asGif()
                            .load(R.mipmap.webview_loading_end)
                            .into(ivLoading);
                }
                mProgress.setProgress(newProgress);
                if (newProgress == 100) {
                    mProgress.setVisibility(View.GONE);
                    llLoading.setVisibility(View.GONE);
                }
            }});

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.loadUrl(url);
    }
}
