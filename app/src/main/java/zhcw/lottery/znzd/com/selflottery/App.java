package zhcw.lottery.znzd.com.selflottery;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.multidex.MultiDexApplication;

import com.bumptech.glide.Glide;

import zhcw.lottery.znzd.com.selflottery.service.InitAppIntentService;

/**
 * Created by XPZ on 2018/9/1.
 * app
 */
public class App extends MultiDexApplication {

    private static App application;

    public static App getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        InitAppIntentService.start(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //解决系统文字大小被修改 , 恢复默认
        if (newConfig.fontScale != 1) {//非默认
            getResources();
            super.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public Resources getResources() {
        //解决系统文字大小被修改 , 恢复默认
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置为默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }
}
