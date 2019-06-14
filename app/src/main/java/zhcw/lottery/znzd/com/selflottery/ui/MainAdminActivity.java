package zhcw.lottery.znzd.com.selflottery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.base.BaseActivity;
import zhcw.lottery.znzd.com.selflottery.util.AndroidWorkaround;
import zhcw.lottery.znzd.com.selflottery.widgets.BottomTabHost;


/**
 * Created by xpz on 2018/4/23.
 * 框架
 */
public class MainAdminActivity extends BaseActivity {
    public static final String DEFAULT_PAGE = "default_page";
    public static MainAdminActivity adminActivity;
    private long mExitTime;
    private BottomTabHost mBottonTabHost;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        // 华为底部导航栏适配
        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
        }

        adminActivity = this;
        mBottonTabHost = new BottomTabHost(this);
        int defaultPage = getDefaultPage(getIntent());
        if (defaultPage != 0) {
            mBottonTabHost.setCurrentTab(defaultPage);
        }
    }

    @Override
    protected boolean translucentStatusBar() {
        return true;
    }

    private int getDefaultPage(Intent intent) {
        int page = intent.getIntExtra(DEFAULT_PAGE, 0);
        return page >= 0 && page <= 1 ? page : 0;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, R.string.exit_msg, Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
