package zhcw.lottery.znzd.com.selflottery.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.lzy.imagepicker.view.SystemBarTintManager;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.event.NetworkChangeEvent;
import zhcw.lottery.znzd.com.selflottery.ready_entity.bean_info.UserInfo;
import zhcw.lottery.znzd.com.selflottery.ui.MainAdminActivity;
import zhcw.lottery.znzd.com.selflottery.util.ActivityCollector;
import zhcw.lottery.znzd.com.selflottery.util.Utils;
import zhcw.lottery.znzd.com.selflottery.widgets.NetworkConnectChangedReceiver;
import zhcw.lottery.znzd.com.selflottery.widgets.ViewBase;

/**
 * activity基类
 */

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 用来 User页面 跳转的标示
     */
    public static Class<? extends Activity> activity = null;
    protected boolean mCheckNetWork = true; //默认检查网络状态
    protected Context mContext;
    private AutoLinearLayout mTipView;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private IntentFilter filter;
    private NetworkConnectChangedReceiver networkConnectChangedReceiver;
    protected Animation enterAnim;
    protected Animation exitAnim;

    /**
     * 获取布局ID
     *
     * @return 布局id
     */
    protected abstract int getContentViewLayoutID();

    /**
     * 初始化布局以及View控件
     */
    protected abstract void init(Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //注册网络转态的管广播
        filterReceiver();
        initSystemBarTint();
        ActivityCollector.getInstance().addActivity(this);
        EventBus.getDefault().register(this);
        initTipView();//初始化提示View
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
            init(savedInstanceState);
            
            enterAnim = AnimationUtils.loadAnimation(this, R.anim.push_bottom_in);
            exitAnim = AnimationUtils.loadAnimation(this, R.anim.push_bottom_out);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (networkConnectChangedReceiver != null) {
            unregisterReceiver(networkConnectChangedReceiver);
        }
        EventBus.getDefault().unregister(this);
    }

    private void filterReceiver() {
        //注册网络状态广播
        filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        networkConnectChangedReceiver = new NetworkConnectChangedReceiver();
        registerReceiver(networkConnectChangedReceiver, filter);
    }


    public void launchActivity(Class<?> c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    public void launchActivity(Class<?> c, Bundle bundle) {
        if (bundle != null) {
            Intent intent = new Intent(this, c);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public void launchActivityForResult(Class<?> c, int requestCode) {
        Intent intent = new Intent(this, c);
        startActivityForResult(intent, requestCode);
    }

    public void launchActivityForResult(Class<?> c, Bundle bundle, int requestCode) {
        if (bundle != null) {
            Intent intent = new Intent(this, c);
            intent.putExtras(bundle);
            startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 子类可以重写改变状态栏颜色
     */
    protected int setStatusBarColor() {
        return getColorPrimary();
    }

    /**
     * 子类可以重写决定是否使用透明状态栏
     */
    protected boolean translucentStatusBar() {
        return false;
    }

    /**
     * 设置状态栏颜色
     */
    protected void initSystemBarTint() {
        Window window = getWindow();
        if (translucentStatusBar()) {// 设置状态栏全透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// 沉浸式状态栏 5.0以上使用原生方法
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(setStatusBarColor());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManagers = new SystemBarTintManager(this);
            tintManagers.setStatusBarTintEnabled(true);
            tintManagers.setStatusBarTintColor(setStatusBarColor());
        }
    }

    /**
     * 获取主题色
     */
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在无网络情况下打开APP时，系统不会发送网络状况变更的Intent，需要自己手动检查
        hasNetWork(Utils.isNetworkConnected());
    }

    /**
     * @param Activityclass 登录之后需要跳转的页面的class , 默认是MainAdminActivity.class
     * @return 返回登录状态
     */
    public boolean isLoginAndStartLogin(@Nullable Class<? extends Activity> Activityclass) {
        if (UserInfo.isIsLogin()) {
            return true;
        } else {
            if (Activityclass != null) {
                activity = Activityclass;
                ViewBase.showISLogin(this,1);
            } else {
                activity = MainAdminActivity.class;
                ViewBase.showISLogin(this,1);
            }
            return false;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkChangeEvent(NetworkChangeEvent event) {
        hasNetWork(event.isConnected);
    }

    private void hasNetWork(boolean has) {
        if (isCheckNetWork()) {
            if (has) {
                if (mTipView != null && mTipView.getParent() != null) {
                    mWindowManager.removeView(mTipView);
                }
            } else {
                if (mTipView.getParent() == null) {
                    mWindowManager.addView(mTipView, mLayoutParams);
                    mTipView.setOnClickListener(v -> startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));
                }
            }
        }
    }

    public boolean isCheckNetWork() {
        return mCheckNetWork;
    }


    //计算一个数的阶乘（递归）
    protected double factorial(int num) {
        // num=7 7*6*5...*1
        if (num > 1) {
            return num * factorial(num - 1);
        } else if (num == 1 || num == 0) {
            return 1;
        } else {
            return 1;
        }
    }

    /**
     * 如果页面不需要网络状态的显示 , 重写并返回false
     *
     * @param checkNetWork
     */
    public void setCheckNetWork(boolean checkNetWork) {
        mCheckNetWork = checkNetWork;
    }

    /**
     * 全局的网路状态提示
     */
    private void initTipView() {
        LayoutInflater inflater = getLayoutInflater();
        //提示View布局
        mTipView = (AutoLinearLayout) inflater.inflate(R.layout.layout_network_tip, null);
        mWindowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                ,//| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                PixelFormat.TRANSLUCENT);
        //使用非CENTER时，可以通过设置XY的值来改变View的位置
        mLayoutParams.gravity = Gravity.TOP;
        mLayoutParams.x = 0;
        mLayoutParams.y = 165;
    }

    public static class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}
