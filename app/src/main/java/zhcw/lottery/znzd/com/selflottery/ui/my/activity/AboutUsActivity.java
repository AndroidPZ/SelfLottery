package zhcw.lottery.znzd.com.selflottery.ui.my.activity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.header.FlyRefreshHeader;
import com.scwang.smartrefresh.header.flyrefresh.FlyView;
import com.scwang.smartrefresh.header.flyrefresh.MountainSceneView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import zhcw.lottery.znzd.com.selflottery.BuildConfig;
import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.base.BaseActivity;
import zhcw.lottery.znzd.com.selflottery.config.Config;
import zhcw.lottery.znzd.com.selflottery.util.ShareUtil;
import zhcw.lottery.znzd.com.selflottery.util.SpannableStringUtils;
import zhcw.lottery.znzd.com.selflottery.util.StatusBarUtil;
import zhcw.lottery.znzd.com.selflottery.util.SystemUtil;
import zhcw.lottery.znzd.com.selflottery.widgets.ViewBase;
import zhcw.lottery.znzd.com.selflottery.widgets.interpolator.ElasticOutInterpolator;

/**
 * 关于
 *
 * @author xpz
 * @date 2019/1/5
 */

public class AboutUsActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.about_us_mountain)
    MountainSceneView mAboutUsMountain;
    @BindView(R.id.about_us_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.about_us_toolbar_layout)
    CollapsingToolbarLayout mAboutUsToolbarLayout;
    @BindView(R.id.about_us_app_bar)
    AppBarLayout mAboutUsAppBar;
    @BindView(R.id.about_us_fly_refresh)
    FlyRefreshHeader mFlyRefreshHeader;
    @BindView(R.id.about_us_refresh_layout)
    SmartRefreshLayout mAboutUsRefreshLayout;
    @BindView(R.id.about_us_fab)
    FloatingActionButton mAboutUsFab;
    @BindView(R.id.about_us_fly_view)
    FlyView mAboutUsFlyView;
    @BindView(R.id.about_us_content)
    NestedScrollView mScrollView;
    @BindView(R.id.aboutContent)
    TextView mAboutContent;
    @BindView(R.id.aboutVersion)
    TextView mAboutVersion;
    @BindView(R.id.tv_customer_service_number)
    TextView tvCSNumber;
    @BindView(R.id.tv_tuiguang)
    TextView tvTuiguang;

    private View.OnClickListener mThemeListener;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initToolbar();
        initEventAndData();
    }

    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, mToolbar);
        //设置是否有返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    protected void initEventAndData() {
        tvCSNumber.setText(SpannableStringUtils.getBuilder("客服电话: ")
                .append(getString(R.string.about_consumer_hotline))
                .setForegroundColor(getResources().getColor(R.color.blue_800))
                .setProportion(1.2f)
                .setUnderline()
                .create());

        tvTuiguang.setText(SpannableStringUtils.getBuilder()
                .append("感谢推广: ")
                .setForegroundColor(getResources().getColor(R.color.grey_800))
                .setProportion(1.3f)
                .append("点击展示推广码")
                .setForegroundColor(getResources().getColor(R.color.red_900))
                .setProportion(1.2f)
                .create());


        showAboutContent();
        setSmartRefreshLayout();

        //进入界面时自动刷新
        mAboutUsRefreshLayout.autoRefresh();

        //点击悬浮按钮时自动刷新
        mAboutUsFab.setOnClickListener(v -> mAboutUsRefreshLayout.autoRefresh());

        //监听 AppBarLayout 的关闭和开启 给 FlyView（纸飞机） 和 ActionButton 设置关闭隐藏动画
        mAboutUsAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean misAppbarExpand = true;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int scrollRange = appBarLayout.getTotalScrollRange();
                float fraction = 1f * (scrollRange + verticalOffset) / scrollRange;
                double minFraction = 0.1;
                double maxFraction = 0.8;
                if (mScrollView == null || mAboutUsFab == null || mAboutUsFlyView == null) {
                    return;
                }
                if (fraction < minFraction && misAppbarExpand) {
                    misAppbarExpand = false;
                    mAboutUsFab.animate().scaleX(0).scaleY(0);
                    mAboutUsFlyView.animate().scaleX(0).scaleY(0);
                    ValueAnimator animator = ValueAnimator.ofInt(mScrollView.getPaddingTop(), 0);
                    animator.setDuration(300);
                    animator.addUpdateListener(animation -> {
                        if (mScrollView != null) {
                            mScrollView.setPadding(0, (int) animation.getAnimatedValue(), 0, 0);
                        }
                    });
                    animator.start();
                }
                if (fraction > maxFraction && !misAppbarExpand) {
                    misAppbarExpand = true;
                    mAboutUsFab.animate().scaleX(1).scaleY(1);
                    mAboutUsFlyView.animate().scaleX(1).scaleY(1);
                    ValueAnimator animator = ValueAnimator.ofInt(mScrollView.getPaddingTop(), DensityUtil.dp2px(25));
                    animator.setDuration(300);
                    animator.addUpdateListener(animation -> {
                        if (mScrollView != null) {
                            mScrollView.setPadding(0, (int) animation.getAnimatedValue(), 0, 0);
                        }
                    });
                    animator.start();
                }
            }
        });
    }

    private void setSmartRefreshLayout() {
        //绑定场景和纸飞机
        mFlyRefreshHeader.setUp(mAboutUsMountain, mAboutUsFlyView);
        mAboutUsRefreshLayout.setReboundInterpolator(new ElasticOutInterpolator());
        mAboutUsRefreshLayout.setReboundDuration(800);
        mAboutUsRefreshLayout.setOnRefreshListener(refreshLayout -> {
            updateTheme();
            refreshLayout.finishRefresh(1000);
        });

        //设置让Toolbar和AppBarLayout的滚动同步
        mAboutUsRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                refreshLayout.finishRefresh(2000);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                refreshLayout.finishLoadMore(3000);
            }

            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                super.onHeaderMoving(header, isDragging, percent, offset, headerHeight, maxDragHeight);
                if (mAboutUsAppBar == null || mToolbar == null) {
                    return;
                }
                mAboutUsAppBar.setTranslationY(offset);
                mToolbar.setTranslationY(-offset);
            }
        });
    }

    private void showAboutContent() {
        mAboutContent.setText(Html.fromHtml(getString(R.string.about_content)));
        mAboutContent.setMovementMethod(LinkMovementMethod.getInstance());
        String versionStr = getString(R.string.app_name)
                + " V" + SystemUtil.getVersionName(this) + (BuildConfig.IS_TEST ? "" : "_test");
        mAboutVersion.setText(versionStr);
    }

    /**
     * Update appbar theme
     */
    private void updateTheme() {
        if (mThemeListener == null) {
            mThemeListener = new View.OnClickListener() {
                int index = 0;
                int[] ids = new int[]{
                        R.color.colorPrimary,
                        android.R.color.holo_green_light,
                        android.R.color.holo_purple,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_blue_bright,
                };

                @Override
                public void onClick(View v) {
                    int color = ContextCompat.getColor(getApplication(), ids[index % ids.length]);
                    mAboutUsRefreshLayout.setPrimaryColors(color);
                    mAboutUsFab.setBackgroundColor(color);
                    mAboutUsFab.setBackgroundTintList(ColorStateList.valueOf(color));
                    mAboutUsToolbarLayout.setContentScrimColor(color);
                    index++;
                }
            };
        }
        mThemeListener.onClick(null);
    }

    @OnClick({R.id.tv_customer_service_number, R.id.tv_tuiguang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_customer_service_number://客服电话
                methodRequiresTwoPermission();
                break;
            case R.id.tv_tuiguang://推广
                ViewBase.tuiguangDialog(this);
                break;

        }
    }

    @AfterPermissionGranted(Config.CALL_PHONE)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            ShareUtil.callPhone(this, getString(R.string.about_consumer_hotline));
        } else {
            EasyPermissions.requestPermissions(this, "为了您的正常使用请打启读取权限!",
                    Config.CALL_PHONE, perms);
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == Config.CALL_PHONE) {
            ShareUtil.callPhone(this, getString(R.string.about_consumer_hotline));
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle("提示")
                    .setRationale("为了您的正常使用，请开启相应权限!")
                    .setPositiveButton("去设置")
                    .setNegativeButton("取消")
                    .setRequestCode(Config.CALL_PHONE)
                    .build()
                    .show();
        }
    }

}
