package zhcw.lottery.znzd.com.selflottery.ui.my.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.kongzue.dialog.v2.MessageDialog;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.base.BaseActivity;
import zhcw.lottery.znzd.com.selflottery.config.Config;
import zhcw.lottery.znzd.com.selflottery.contact.WalletContact;
import zhcw.lottery.znzd.com.selflottery.presenter.WalletPresenter;
import zhcw.lottery.znzd.com.selflottery.ready_entity.bean_info.UserInfo;
import zhcw.lottery.znzd.com.selflottery.ui.my.adapter.WalletAdapter;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.Wallet_Info;
import zhcw.lottery.znzd.com.selflottery.util.Logger;
import zhcw.lottery.znzd.com.selflottery.util.PreferenceUtil;
import zhcw.lottery.znzd.com.selflottery.util.ToastUtil;
import zhcw.lottery.znzd.com.selflottery.util.Utils;
import zhcw.lottery.znzd.com.selflottery.widgets.AnimationTask.CountNumberView;
import zhcw.lottery.znzd.com.selflottery.widgets.ClearEditText;
import zhcw.lottery.znzd.com.selflottery.widgets.ViewBase;

/**
 * 我的钱包
 *
 * @author XPZ
 */
public class WalletActivity extends BaseActivity
        implements Toolbar.OnMenuItemClickListener, WalletContact.IWalletView {

    @BindView(R.id.tv_balance)
    CountNumberView tvBalance;
    @BindView(R.id.tb_chong_zhi)
    Button tbChongZhi;
    @BindView(R.id.tb_ti_xian)
    Button tbTiXian;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.info_null)
    AutoLinearLayout infoNull;
    private WalletPresenter walletPresenter;
    private WalletAdapter mAdapter;
    /**
     * 加载更多初始页数
     */
    private int mNextRequestPage = 0;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        walletPresenter = new WalletPresenter(this, this);
        toolbar.setTitle(R.string.wallet_title);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        //设置是否有返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        initRefreshLayout();
    }

    void initView() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WalletAdapter(new ArrayList<>());
        mAdapter.setOnLoadMoreListener(() ->
                walletPresenter.getDetailRequestRequest(UserInfo.getToken(), mNextRequestPage));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> refresh());
    }

    private void refresh() {
        mNextRequestPage = 0;
        mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        walletPresenter.getGetAccountRequest(UserInfo.getToken());
        walletPresenter.getDetailRequestRequest(UserInfo.getToken(), mNextRequestPage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSwipeRefreshLayout.setRefreshing(true);
        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 绑定toobar跟menu
        if (UserInfo.getIsOwner() != 0) { //如果没有认证 , 不展示
            getMenuInflater().inflate(R.menu.menu_wallet, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //重写ToolBar返回按钮的行为，防止重新打开父Activity重走生命周期方法
            case android.R.id.home:
                finish();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            //toolbar上menu的点击事件
            case R.id.action_edit:
                launchActivity(CommissionActivity.class);
                return true;
            default:
        }
        return false;
    }

    @OnClick({R.id.tb_chong_zhi, R.id.tb_ti_xian})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tb_chong_zhi:
                launchActivity(RechargeActivity.class);
                BaseActivity.activity = WalletActivity.class;
                break;
            case R.id.tb_ti_xian:
                if (UserInfo.isBind()) {
                    JudgeManyNum();
                } else {
                    Logger.i("发起微信登录");
                    Utils.SendWxAuth(WalletActivity.this, Config.APP_ID);
                }
                // TODO: 2019/5/23 去掉实名
             /*   if (UserInfo.getAudit() == 1) {
                } else {
                    ViewBase.setJdCardCertificationDialog(this);
                }*/
                break;
            default:
        }
    }

    /**
     * 判断金额的逻辑
     */
    @SuppressLint("DefaultLocale")
    private void JudgeManyNum() {
        float preferences = PreferenceUtil.getInstance().getPreferences(Config.MONEY, 0.00f);
        String format = String.format(CountNumberView.FLOATREGEX, preferences);
        if (preferences > 0) {
            ViewBase.WeAddPreOrderDialog(WalletActivity.this, format, (view, dialog) -> {
                ClearEditText editMany = dialog.getView().findViewById(R.id.et_add_many);
                String many = editMany.getText().toString().trim();
                Logger.i("提现金额: " + editMany.getText().toString().trim() + "元");
                if (!"".equals(many) && Float.valueOf(many) > Config.WX_MANY_LIMIT) { //提现单笔最少
                    walletPresenter.getWeAddPreOrder(UserInfo.getToken(), many);
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                } else {
                    ToastUtil.showShortToast("请正确输入提现金额");
                }
            });
        } else {
            MessageDialog.show(this, "您的余额不足!", "", "确定",
                    (dialog, which) -> dialog.dismiss());
        }
    }

    private void setData(boolean isRefresh, List<Wallet_Info.ListBean> data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            if (size > 0) {
                mAdapter.setNewData(data);
                mRecyclerView.setVisibility(View.VISIBLE);
                infoNull.setVisibility(View.GONE);
            } else {
                mRecyclerView.setVisibility(View.GONE);
                infoNull.setVisibility(View.VISIBLE);
            }
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        if (size < Config.PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void setSucessLodeData(List<Wallet_Info.ListBean> lodeData) {
        mSwipeRefreshLayout.setRefreshing(false);
        boolean isRefresh = mNextRequestPage == 0;
        setData(isRefresh, lodeData);
    }

    @Override
    public void setAccount(Float many) {
        tvBalance.showNumberWithAnimation(many, CountNumberView.FLOATREGEX); //保留两位小数
    }

    @Override
    public void setDefaultMessage(String message) {
        mSwipeRefreshLayout.setRefreshing(false);
        ToastUtil.showShortToast(message);
    }

    @Override
    public void setDefaultError() {
        mRecyclerView.setVisibility(View.GONE);
        infoNull.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setRefreshing(false);
        ToastUtil.showShortToast("网络连接异常请稍后重试~");
    }
}
