package zhcw.lottery.znzd.com.selflottery.ui.my.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.base.BaseActivity;
import zhcw.lottery.znzd.com.selflottery.contact.SettingContact;
import zhcw.lottery.znzd.com.selflottery.presenter.SettingPresenter;
import zhcw.lottery.znzd.com.selflottery.ready_entity.bean_info.UserInfo;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.entity.UpDataInfo;
import zhcw.lottery.znzd.com.selflottery.util.ACache;
import zhcw.lottery.znzd.com.selflottery.util.ActivityCollector;
import zhcw.lottery.znzd.com.selflottery.util.Logger;
import zhcw.lottery.znzd.com.selflottery.util.ShareUtil;
import zhcw.lottery.znzd.com.selflottery.util.SystemUtil;
import zhcw.lottery.znzd.com.selflottery.widgets.ViewBase;

/**
 * 设置
 * Created by xpz on 2019/1/7.
 */
public class SettingActivity extends BaseActivity implements SettingContact.ISetting {

    @BindView(R.id.about_us_toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_setting_feedback)
    TextView mSettingFeedback;
    @BindView(R.id.tv_setting_clear)
    TextView mTvClear;
    @BindView(R.id.ll_setting_clear)
    LinearLayout mSettingClear;
    @BindView(R.id.ll_setting_about)
    TextView mSettingAbout;
    @BindView(R.id.tb_true)
    Button mBtTrue;
    @BindView(R.id.tv_setting_update)
    TextView mTvUpdate;
    private SettingPresenter settingPresenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        settingPresenter = new SettingPresenter(this, this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTvUpdate.setText(String.format("版本:V%s", SystemUtil.getVersionName(this)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTvClear.setText(ACache.getFormatSize(ACache.getFolderSize(getCacheDir())));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //重写ToolBar返回按钮的行为，防止重新打开父Activity重走生命周期方法
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.ll_setting_update, R.id.ll_setting_feedback, R.id.ll_setting_clear,
            R.id.ll_setting_about, R.id.tb_true})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_setting_update://检查版本
                settingPresenter.getVersionUpdateRequest();
                break;
            case R.id.ll_setting_feedback://问题反馈
                ShareUtil.sendEmail(this, getString(R.string.send_email));
                break;
            case R.id.ll_setting_clear://清理缓存
                clearCache();
                break;
            case R.id.ll_setting_about: //关于
                launchActivity(AboutUsActivity.class);
                break;
            case R.id.tb_true://退出
                isLogOut();
                break;
        }
    }

    private void isLogOut() {
        ViewBase.showDefault(this, getString(R.string.setting_out_dialog), (view, dialog) -> {
            dialog.dismiss();
            UserInfo.logout();
            ActivityCollector.getInstance().exitApp();
        });
    }

    private void clearCache() {
        ViewBase.showDefault(this, getString(R.string.setting_clear_dialog), (view, dialog) -> {
            dialog.dismiss();
            ACache.deleteDir(SettingActivity.this.getCacheDir());
            mTvClear.setText(ACache.getCacheSize(SettingActivity.this.getCacheDir()));
        });
    }

    @Override
    public void setVersionUpdate(UpDataInfo.DataBean data) {
        Logger.i("服务返回版名: " + data.getVersionNum() + "\n"
                + "当前版名: " + SystemUtil.getVersionName(this));
        if (!String.valueOf(SystemUtil.getVersionName(this)).equals(data.getVersionNum())) {
            String versionUrl = data.getVersionUrl();
            if (!"".equals(versionUrl)) {
                ViewBase.showMessageDialog(this, versionUrl);
            }
        } else {
            ViewBase.showSnackMessage(this, "已经是最新版本无需更新!");
        }
    }

}
