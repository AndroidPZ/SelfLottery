package zhcw.lottery.znzd.com.selflottery.ui.my.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import butterknife.BindView;
import butterknife.OnClick;
import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.base.BaseActivity;
import zhcw.lottery.znzd.com.selflottery.contact.CommissonContact;
import zhcw.lottery.znzd.com.selflottery.presenter.CommissiontPresenter;
import zhcw.lottery.znzd.com.selflottery.ready_entity.bean_info.UserInfo;
import zhcw.lottery.znzd.com.selflottery.ui.my.adapter.CommissonAdapter;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.Commisson_Info;
import zhcw.lottery.znzd.com.selflottery.util.SpannableStringUtils;
import zhcw.lottery.znzd.com.selflottery.util.ToastUtil;
import zhcw.lottery.znzd.com.selflottery.widgets.AnimationTask.CountNumberView;

/**
 * 佣金提现
 * @author XPZ
 */
public class CommissionActivity extends BaseActivity implements CommissonContact.ICommissonView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_balance)
    CountNumberView mTvBalance;
    @BindView(R.id.tb_ti_xian)
    Button mTBTiXian;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.amount)
    TextView mAmount;
    @BindView(R.id.textView)
    TextView mLilLvTV;
    private CommissonAdapter mAdapter;
    private CommissiontPresenter commissiontPresenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_commission;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        commissiontPresenter = new CommissiontPresenter(this, this);
        toolbar.setTitle(R.string.Commission_title);
        setSupportActionBar(toolbar);
        //设置是否有返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData();
    }


    private void initData() {
        commissiontPresenter.getSaleRecordRequest(UserInfo.getToken());
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

    @OnClick(R.id.tb_ti_xian)
    public void onViewClicked() {
        //提现
        commissiontPresenter.getApplyRequest(UserInfo.getToken());
    }

    @Override
    public void setSucessLodeData(Commisson_Info lodeData) {
        Commisson_Info.SaleInfoBean saleInfo = lodeData.getSaleInfo();
        mAmount.setText(SpannableStringUtils.getBuilder("总销售金额:")
                .append(saleInfo.getAmount())
                .setForegroundColor(getResources().getColor(R.color.red_500))
                .append("元").create());
        mLilLvTV.setText(SpannableStringUtils.getBuilder("(佣金比例")
                .append(saleInfo.getPercent())
                .setForegroundColor(getResources().getColor(R.color.red_700))
                .append(")").create());//saleInfo.getPercent()
        mTvBalance.showNumberWithAnimation(Float.valueOf(saleInfo.getCommission()), CountNumberView.FLOATREGEX); //保留两位小数
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CommissonAdapter(saleInfo.getLt());
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN); //载入动画
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setDefaultMessage(String message) {
        ToastUtil.showShortToast(message);
    }
}
