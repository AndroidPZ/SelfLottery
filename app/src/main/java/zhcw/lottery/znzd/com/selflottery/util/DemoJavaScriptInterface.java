package zhcw.lottery.znzd.com.selflottery.util;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.tencent.smtt.sdk.WebView;


/**
 * Created by xpz on 2018/11/11.
 * 接口
 */

public class DemoJavaScriptInterface {

    private static MediaPlayer mMediaPlayer;
    private static MediaPlayer player;
    private static MediaPlayer clickplayer;
    private Activity mContext;
    private WebView mWebView;
    private LinearLayout llTitleItem;
    private String fcdjlUrl = "";

    public DemoJavaScriptInterface(Activity context, WebView mWebView, LinearLayout llTitleItem) {
        this.mContext = context;
        this.mWebView = mWebView;
        this.llTitleItem = llTitleItem;
    }

    /**
     * 释放资源
     */
    public static void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
        if (clickplayer != null) {
            clickplayer.stop();
            clickplayer.release();
            clickplayer = null;
        }
    }

    /**
     * 日奖扫票
     */
    @JavascriptInterface
    public void openActiveScanCamera() {
        Intent intent = new Intent(mContext, CaptureActivity.class);
        intent.putExtra("type", "game");
        mContext.startActivity(intent);
    }

    /**
     * 摇一摇扫票签到
     */
    @JavascriptInterface
    public void openSignScanCamera(String key) {
        Intent intent = new Intent(mContext, CaptureActivity.class);
        intent.putExtra("type", "yao");
        intent.putExtra("key", key);
        mContext.startActivity(intent);
        mContext.startActivity(intent);
    }

    /**
     * 刷新界面
     */
    @JavascriptInterface
    public void refresh() {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.reload();
            }
        });
    }

    /**
     * 福彩大家乐返回上一页
     */
    @JavascriptInterface
    public void goBack() {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                    release();
                } else {
                    release();
                    ((Activity) mContext).finish();
                }
            }
        });
    }

    /**
     * 隐藏福彩大家乐头部
     */
    @JavascriptInterface
    public void fcdjlHideTitie() {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                llTitleItem.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 隐藏头部背景色
     */
    @JavascriptInterface
    public void hideTitie() {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                llTitleItem.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 状态栏隐藏显示(ture 隐藏 false 显示)
     *
     * @param enable
     */
    @JavascriptInterface
    public void setStatusBar(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            mContext.getWindow().setAttributes(lp);
            mContext.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = mContext.getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mContext.getWindow().setAttributes(attr);
            mContext.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * 设置背景音乐的音量的大小
     *
     * @param volume
     */
    @JavascriptInterface
    public void setBgVolume(String volume) {
        /* 设置音量的大小 */
        float mVolume = Float.parseFloat(volume);
//        mMediaPlayer.setVolume(mVolume, mVolume);
    }

    /**
     * 设置其他音乐的音量大小
     *
     * @param volume
     */
    @JavascriptInterface
    public void setOmVolume(String volume) {
        /* 设置其他音乐的音量的大小 */
        float mVolume = Float.parseFloat(volume);
//        player.setVolume(mVolume, mVolume);
    }

    /**
     * 开始播放游戏背景音乐
     *
     * @param index
     */
    @JavascriptInterface
    public void playMusic(final String index) {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mMediaPlayer == null) {
                        mMediaPlayer = new MediaPlayer();
                    }
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaPlayer.reset();
                    AssetManager assetManager = mContext.getAssets();
                    AssetFileDescriptor fileDescriptor;
                    fileDescriptor = assetManager.openFd("zbackground.mp3");
                    mMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                    mMediaPlayer.prepare();
                    mMediaPlayer.start();
                    mMediaPlayer.setLooping(true);
                    if (player == null) {
                        player = new MediaPlayer();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 播放摇一摇音乐
     *
     * @param yao
     */
    public void playYaoMusic(String yao) {
        try {
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.reset();
            AssetManager assetManager = mContext.getAssets();
            AssetFileDescriptor fileDescriptor;
            fileDescriptor = assetManager.openFd(yao);
            player.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            player.prepare();
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放其他音乐
     *
     * @param name
     */
    @JavascriptInterface
    public void playSecondMusic(final String name) {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    mMediaPlayer.setVolume(0.2f, 0.2f);
                    player.setVolume(1.0f, 1.0f);
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.reset();
                    AssetManager assetManager = mContext.getAssets();
                    AssetFileDescriptor fileDescriptor = assetManager.openFd(name);
                    player.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                    player.prepare();
                    player.start();
                    if (player.isPlaying()) {
                        if ("click_success.mp3".equals(name) || "click_fail.mp3".equals(name)) {
                            if (clickplayer == null) {
                                clickplayer = new MediaPlayer();
                            }
                            player.setVolume(0.5f, 0.5f);
                            clickplayer.setVolume(1.0f, 1.0f);
                            clickplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            clickplayer.reset();
                            AssetManager assetManager1 = mContext.getAssets();
                            AssetFileDescriptor fileDescriptor1 = assetManager1.openFd(name);
                            clickplayer.setDataSource(fileDescriptor1.getFileDescriptor(), fileDescriptor1.getStartOffset(), fileDescriptor1.getLength());
                            clickplayer.prepare();
                            clickplayer.start();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });
    }

    /**
     * 停止播放音乐
     */
    @JavascriptInterface
    public void stopMusic() {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mMediaPlayer != null) {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                }
                if (player != null) {
                    player.stop();
                    player.release();
                    player = null;
                }
                if (clickplayer != null) {
                    clickplayer.stop();
                    clickplayer.release();
                    clickplayer = null;
                }
            }
        });
    }
}