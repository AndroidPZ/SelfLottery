package zhcw.lottery.znzd.com.selflottery.ui;

import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;
import butterknife.OnClick;
import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.base.BaseActivity;
import zhcw.lottery.znzd.com.selflottery.util.WebViewUtil;

import static zhcw.lottery.znzd.com.selflottery.util.DemoJavaScriptInterface.release;


/**
 * Created by xpz on 2018/11/9.
 * web
 */

public class WebActivity extends BaseActivity {
    @BindView(R.id.web)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.iv_webview_gifloading)
    ImageView ivLoading;
    @BindView(R.id.ll_webview_gifloading)
    LinearLayout llLoading;
    @BindView(R.id.ll_webview_title_item)
    LinearLayout llTitleItem;

    @Override
    protected int getContentViewLayoutID() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//保持屏幕长亮
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        return R.layout.activity_web;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
//        EventBus.getDefault().register(this);
        String h5Url = getIntent().getStringExtra("h5Url");
        WebViewUtil webViewUtil = new WebViewUtil();
        webViewUtil.ClientWebView(webView, progressBar, h5Url.isEmpty() ? "" : h5Url, this, ivLoading, llLoading, llTitleItem);
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //如果想要屏蔽只需要返回ture 即可
                return true;
            }
        });
    }

    /*    @Subscribe(threadMode = ThreadMode.MainThread)
        public void onShowNumberEvent(H5UrlEvent h5UrlEvent){
            boolean isOK=h5UrlEvent.isOk();
            if (isOK){
                webView.reload();
            }else {
                String h5Url=h5UrlEvent.getH5Url();
                WebViewUtil webViewUtil = new WebViewUtil();
                webViewUtil.ClientWebView(webView, progressBar,h5Url, this,ivLoading,llLoading,llTitleItem);
            }

        }*/
    @OnClick({R.id.rl_webview_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_webview_back:
                if (webView.canGoBack()) {
                    webView.goBack();// 返回上一页面
                    webView.clearCache(true);
                    release();
                } else {
                    if (webView != null) {
                        webView.clearCache(true);
                    }
                    finish();
                    release();
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        release();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (webView != null) {
                webView.onPause();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) { //3.0版本 验证
            if (webView != null) {
                webView.onResume();
            }
        }
    }

    @Override
    public void onDestroy() {
        release();
        if (webView != null) {
            webView.clearHistory();
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView != null && webView.canGoBack()) {
                webView.goBack();// 返回上一页面
                webView.clearCache(true);
                release();
                return true;
            } else {
                if (webView != null) {
                    webView.clearCache(true);
                }
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
