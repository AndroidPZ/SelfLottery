package zhcw.lottery.znzd.com.selflottery.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.BindView;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.base.BaseActivity;

/**
 * 协议
 */
public class DisclaimerActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_disclaimer_msg)
    TextView tvDisclaimerMsg;
    @BindView(R.id.scroll_view_login)
    ScrollView scrollViewLogin;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_disclaimer;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvDisclaimerMsg.setText(Html.fromHtml(getString(R.string.login_disclaimer)));
        OverScrollDecoratorHelper.setUpOverScroll(scrollViewLogin);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //重写ToolBar返回按钮的行为，防止重新打开父Activity重走生命周期方法
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
