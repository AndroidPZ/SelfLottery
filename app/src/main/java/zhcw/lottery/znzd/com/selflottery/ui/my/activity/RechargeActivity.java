package zhcw.lottery.znzd.com.selflottery.ui.my.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.base.BaseActivity;
import zhcw.lottery.znzd.com.selflottery.config.Config;
import zhcw.lottery.znzd.com.selflottery.contact.RechargeContact;
import zhcw.lottery.znzd.com.selflottery.presenter.RechargePresenter;
import zhcw.lottery.znzd.com.selflottery.ready_entity.bean_info.UserInfo;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.ItemModel;
import zhcw.lottery.znzd.com.selflottery.ui.my.adapter.KeyMoneyAdapter;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.Recharge_Info;
import zhcw.lottery.znzd.com.selflottery.util.Logger;
import zhcw.lottery.znzd.com.selflottery.util.PreferenceUtil;
import zhcw.lottery.znzd.com.selflottery.util.ToastUtil;
import zhcw.lottery.znzd.com.selflottery.util.Utils;
import zhcw.lottery.znzd.com.selflottery.widgets.pswkeyboard.widget.VirtualKeyboardView;

/**
 * 充值页面
 * @author XPZ
 */
public class RechargeActivity extends BaseActivity implements RechargeContact.IRechargeView {

    private static String mTemporary = "WeChar";
    final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recylerview)
    RecyclerView recyclerView;
    @BindView(R.id.tvPay)
    TextView tvPay;
    @BindView(R.id.recharge_title_yuan)
    TextView rechargeTitleYuan;
    @BindView(R.id.recharge_wechat_cb)
    ImageView mWechatCb;
    @BindView(R.id.m_wechat)
    RelativeLayout mWechat;
    @BindView(R.id.recharge_alipay_cb)
    ImageView mAlipayCb;
    @BindView(R.id.m_alipay)
    RelativeLayout mAlipay;
    @BindView(R.id.virtualKeyboardView)
    VirtualKeyboardView virtualKeyboardView;
    private KeyMoneyAdapter adapter;
    private RechargePresenter rechargePresenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        rechargePresenter = new RechargePresenter(this, this);

        toolbar.setTitle(R.string.recharge_title);
        setSupportActionBar(toolbar);
        //设置是否有返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new KeyMoneyAdapter(this, virtualKeyboardView);
        recyclerView.setAdapter(adapter);
        adapter.replaceAll(getData());
        mWechatCb.setImageResource(R.mipmap.btn_checked_t);
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

    public ArrayList<ItemModel> getData() {
        ArrayList<ItemModel> list = new ArrayList<>();
        String[] stringArray = getResources().getStringArray(R.array.monay);
        for (int i = 0; i < 5; i++) {
            String count = stringArray[i];
            list.add(new ItemModel(ItemModel.ONE, count));
        }
        list.add(new ItemModel(ItemModel.TWO, null));
        return list;
    }

    @OnClick({R.id.tvPay, R.id.m_wechat, R.id.m_alipay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_wechat:
                mWechatCb.setImageResource(R.mipmap.btn_checked_t);
                mAlipayCb.setImageResource(R.mipmap.btn_checked_f);
                mTemporary = "WeChar";
                break;
            case R.id.m_alipay:
                mAlipayCb.setImageResource(R.mipmap.btn_checked_t);
                mWechatCb.setImageResource(R.mipmap.btn_checked_f);
                mTemporary = "Alipay";
                break;
            case R.id.tvPay:
                if (!"".equals(KeyMoneyAdapter.getMoney()) && Integer.valueOf(KeyMoneyAdapter.getMoney()) > 0) {
                    if (mTemporary.equals("WeChar")) { //微信
                        rechargePresenter.getUnifiedOrderRequest(UserInfo.getToken(),
                                Integer.valueOf(KeyMoneyAdapter.getMoney()), Utils.getIP(this));
                    } else if (mTemporary.equals("Alipay")) { //支付宝
                        ToastUtil.showShortToast("该功能暂未开放,敬请期待");
                    }
                } else {
                    ToastUtil.showShortToast("请选择要充值的金额");
                }
                break;
        }
    }

    @Override
    public void setSucessData(Recharge_Info lodeData) {
        PreferenceUtil.getInstance().putPreferences(Config.OUT_TRADE_NO, lodeData.getOut_trade_no());
        UserInfo.refresh();
        Logger.i("订单请求成功");
        if (!msgApi.isWXAppInstalled()) {
            ToastUtil.showShortToast("您可能没有安装微信!");
        } else {
            Logger.i("开始吊起微信");
            Utils.SendPay(this, Config.APP_ID, lodeData.getPartnerid(), lodeData.getPrepayid(),
                    lodeData.getNoncestr(), lodeData.getTimestamp(), lodeData.getSign());
        }
    }

    @Override
    public void setDefaultMessage(String message) {
        ToastUtil.showShortToast(message);
    }
}
