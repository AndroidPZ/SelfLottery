package zhcw.lottery.znzd.com.selflottery.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.base.BaseActivity;
import zhcw.lottery.znzd.com.selflottery.util.ImgGlideUtil;
import zhcw.lottery.znzd.com.selflottery.widgets.AnimationTask.CountDownView;


/**
 * Created by XPZ on 2018/8/14
 *
 * @Description 欢迎页面
 */
public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.countDownView)
    CountDownView countDownView;
    @BindView(R.id.ig_WelCome)
    ImageView igWelCome;
    @BindView(R.id.tvTiaoGuo)
    TextView tvJump;


    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                onLaunch();
            }
        }
    };

    @Override
    protected int getContentViewLayoutID() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        return R.layout.activity_welcome;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        onLoading();
        ImgGlideUtil.imgLoade(this, "", igWelCome, R.mipmap.welcome);
    }

    private void onLoading() {
        countDownView.start();
        countDownView.setOnLoadingFinishListener(() -> {
            Message message = Message.obtain();
            message.what = 1;
            handler.sendMessage(message);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.tvTiaoGuo)
    public void onViewClicked() {
        onLaunch();
    }

    private void onLaunch() {
        launchActivity(MainAdminActivity.class);
        finish();
    }
}
