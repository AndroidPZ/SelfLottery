package zhcw.lottery.znzd.com.selflottery.ui.lottery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.zhy.autolayout.AutoLinearLayout;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.base.BaseActivity;
import zhcw.lottery.znzd.com.selflottery.config.Config;
import zhcw.lottery.znzd.com.selflottery.config.ConstantValue;
import zhcw.lottery.znzd.com.selflottery.contact.ShoppingContact;
import zhcw.lottery.znzd.com.selflottery.presenter.ShoppingPresenter;
import zhcw.lottery.znzd.com.selflottery.ready_entity.bean_info.UserInfo;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.ShoppingCartSD;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.TicketSD;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.adapter.ShoppingAdapterSD;
import zhcw.lottery.znzd.com.selflottery.ui.my.activity.RechargeActivity;
import zhcw.lottery.znzd.com.selflottery.util.Logger;
import zhcw.lottery.znzd.com.selflottery.util.PreferenceUtil;
import zhcw.lottery.znzd.com.selflottery.util.ToastUtil;
import zhcw.lottery.znzd.com.selflottery.widgets.ViewBase;
import zhcw.lottery.znzd.com.selflottery.widgets.pswkeyboard.widget.VirtualKeyboardBeiView;

public class ShoppingSDActivity extends BaseActivity
        implements ViewBase.UpdateShowFace, ShoppingContact.IShoppingView {

    @BindView(R.id.fanhui)
    AutoLinearLayout fanhui;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ii_add_optional)
    Button iiAddOptional;
    @BindView(R.id.ii_add_random)
    Button iiAddRandom;
    @BindView(R.id.ii_shopping_list)
    RecyclerView shoppingList;
    @BindView(R.id.ii_shopping_list_clear)
    ImageButton iiShoppingListClear;
    @BindView(R.id.ii_shopping_lottery_notice)
    TextView iiShoppingLotteryNotice;
    @BindView(R.id.ii_lottery_shopping_buy)
    Button iiLotteryShoppingBuy;
    @BindView(R.id.ii_shopping_list_betting_num)
    TextView bettingNum;
    @BindView(R.id.ii_shopping_list_betting_money)
    TextView bettingMoney;
    @BindView(R.id.ii_sub_appnumbers)
    Button iiSubAppnumbers;
    @BindView(R.id.ii_appnumbers)
    TextView appnumbersInfo;
    @BindView(R.id.ii_add_appnumbers)
    Button iiAddAppnumbers;
    @BindView(R.id.tv_beishi)
    TextView tvBeishi;
    @BindView(R.id.info_null)
    LinearLayout mNullInfo;
    @BindView(R.id.virtualKeyboardView)
    VirtualKeyboardBeiView mVirtualKeyboardView;
    private ShoppingAdapterSD adapter;
    private String lotteryid;
    private ShoppingPresenter shoppingPresenter;

    @Override
    public void onResume() {
        changeNotice();
        getAccountM();
        super.onResume();
    }

    //提示
    public void changeNotice() {
        Integer lotterynumber = ShoppingCartSD.getInstance().getLotterynumber();
        Integer lotteryvalue = ShoppingCartSD.getInstance().getLotteryvalue();
        if (lotterynumber > 0) {
            mNullInfo.setVisibility(View.GONE);
        } else {
            mNullInfo.setVisibility(View.VISIBLE);
            ShoppingCartSD.getInstance().setAppnumbers(1);
        }

        String number = getResources().getString(R.string.is_shopping_list_betting_num);
        String money = getResources().getString(R.string.is_shopping_list_betting_money);
        number = StringUtils.replace(number, "NUM", lotterynumber.toString());

        int i = lotteryvalue * ShoppingCartSD.getInstance().getAppnumbers() * ShoppingCartSD.getInstance().getIssuesnumbers();
        float mMoney = PreferenceUtil.getInstance().getPreferences(Config.MONEY, 0f);
        money = StringUtils.replaceEach(money, new String[]{"MONEY1", "MONEY2"}, new String[]{i + "", Float.toString(mMoney)});

        bettingNum.setText(Html.fromHtml(number));
        bettingMoney.setText(Html.fromHtml(money));

        String noticeInfo = getResources().getString(R.string.is_shopping_list_notice);
        noticeInfo = StringUtils.replaceEach(noticeInfo, new String[]{"NUM", "MONEY"}, new String[]{lotterynumber.toString(), lotteryvalue.toString()});
        // Html.fromHtml(notice):将notice里面的内容转换
        iiShoppingLotteryNotice.setText(Html.fromHtml(noticeInfo));
        appnumbersInfo.setText(ShoppingCartSD.getInstance().getAppnumbers().toString());
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_shopping;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        init();
        ViewBase.initKeyboardView(99, mVirtualKeyboardView, appnumbersInfo, v -> {
            mVirtualKeyboardView.startAnimation(exitAnim);
            mVirtualKeyboardView.setVisibility(View.GONE);
            ShoppingCartSD.getInstance()
                    .setAppnumbers(Integer.valueOf(!appnumbersInfo.getText().toString().isEmpty() ?
                            appnumbersInfo.getText().toString() : "1"));
            changeNotice();
        });
    }

    private void init() {
        shoppingPresenter = new ShoppingPresenter(this, this, ConstantValue.SDTYPE);

        name.setText("3D投注");
        tvBeishi.setText(R.string.is_beishu_99);
        appnumbersInfo.addTextChangedListener(new MyTextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.notifyDataSetChanged();
            }
        });
        //获取彩种 购物车总区分
        YoYo.with(Techniques.ZoomInUp)
                .duration(500)
                .playOn(shoppingList);
        lotteryid = ShoppingCartSD.getInstance().getLotteryid();
        ShoppingCartSD.getInstance().setAppnumbers(1);

        adapter = new ShoppingAdapterSD(R.layout.il_shopping, ShoppingCartSD.getInstance().getTicketSD());
        shoppingList.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            ShoppingCartSD.getInstance().getTicketSD().remove(position);
            adapter.notifyDataSetChanged();
            changeNotice();
        });
        shoppingList.setAdapter(adapter);
    }

    @OnClick({R.id.fanhui, R.id.ii_add_optional, R.id.ii_add_random, R.id.ii_shopping_list_clear,
            R.id.ii_lottery_shopping_buy, R.id.ii_add_appnumbers, R.id.ii_sub_appnumbers, R.id.ii_appnumbers})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.ii_appnumbers://修改倍数
                appnumbersInfo.setText("");
                mVirtualKeyboardView.getBeiMsg().setText("");
                if (mVirtualKeyboardView != null && mVirtualKeyboardView.getVisibility() != View.VISIBLE) {
                    mVirtualKeyboardView.setFocusable(true);
                    mVirtualKeyboardView.setFocusableInTouchMode(true);
                    mVirtualKeyboardView.startAnimation(enterAnim);
                    mVirtualKeyboardView.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ii_add_appnumbers:// 增加倍数
                if (ShoppingCartSD.getInstance().addAppnumbers(true)) {
                    changeNotice();
                }
                break;
            case R.id.ii_sub_appnumbers:// 减少倍数
                if (ShoppingCartSD.getInstance().addAppnumbers(false)) {
                    changeNotice();
                }
                break;
            case R.id.ii_add_optional://自主选号
                startActivity(new Intent(this, PlaySDActivity.class));
                break;
            case R.id.ii_add_random://随机选号
                addRandom();
                changeNotice();
                break;
            case R.id.ii_shopping_list_clear: //清空购物车
                // 清空2
                ShoppingCartSD.getInstance().getTicketSD().clear();
                adapter.notifyDataSetChanged();
                changeNotice();
                break;
            case R.id.ii_lottery_shopping_buy://购买
                // ①判断：购物车中是否有投注
                if (ShoppingCartSD.getInstance().getTicketSD().size() >= 1) {
                    int mTotal_Price = ShoppingCartSD.getInstance().getLotteryvalue() * ShoppingCartSD.getInstance().getAppnumbers();
                    ArrayList<String> strings = new ArrayList<>();
                    for (TicketSD ticketSD: ShoppingCartSD.getInstance().getTicketSD()) {
                        strings.add(String.valueOf(ticketSD.getState()));
                    }
                    //订单限额
                    if (!strings.contains("true")) {//mTotal_Price < ConstantValue.UPPER_LIMIT_SHOPPING
                        //验证余额
                        if (mTotal_Price <= (int) PreferenceUtil.getInstance().getPreferences(Config.MONEY, 0f)) {
                            shoppingPresenter.getJiangQiRequest("3d");
                            // TODO: 2019/5/23 去掉实名
                        /*    if (UserInfo.getAudit() == 1) {
                                //获取奖期
                            } else {
                                ViewBase.setJdCardCertificationDialog(this);
                            }*/
                        } else {
                            // 提示用户
                            ToastUtil.showShortToast("余额不足,请充值~");
                            BaseActivity.activity = ShoppingSDActivity.class;
                            launchActivity(RechargeActivity.class);
                        }
                    } else {
                        // 提示用户
                        ToastUtil.showShortToast(String.format("为了健康投注,单票不能超过%s元", ConstantValue.UPPER_LIMIT_SHOPPING));
                    }
                } else {
                    // 提示用户需要选择一注
                    ToastUtil.showShortToast("需要选择一注");
                }
                break;
        }
    }

    /**
     * 机选一注SD
     */
    private void addRandom() {
// 机选一注
        Random random = new Random();
        //机选百位
        int numBai = random.nextInt(10);
        //机选十位
        int numShi = random.nextInt(10);
        //机选个位
        int numGe = random.nextInt(10);

        TicketSD ticketSD = new TicketSD();
        StringBuffer jsonBuffer = new StringBuffer("");
        jsonBuffer.append(numBai).append(",")
                .append(numShi).append(",")
                .append(numGe);
        ticketSD.setJsonNum(jsonBuffer.toString());
        ticketSD.setViewNum(jsonBuffer.toString());
        ticketSD.setLotteryid(ConstantValue.SD_P);
        //存储注数信息
        ticketSD.setNum(1);
        ShoppingCartSD.getInstance().getTicketSD().add(0, ticketSD);
        // 更新界面
        adapter.notifyDataSetChanged();
    }

    @Override
    public void handUpdateShow() {
        // 请求余额
        getAccountM();
    }

    private void getAccountM() {
        // 请求余额
        shoppingPresenter.getAccountRequest(UserInfo.getToken());
    }

    @Override
    public void setSucessLottery(String qrcode, Integer number, Integer money) {
        ViewBase.qrCodeDialog(ViewBase.encodeAsBitmap(qrcode), money, number, ShoppingSDActivity.this, ShoppingSDActivity.this);
        // 清空2
        ShoppingCartSD.getInstance().getTicketSD().clear();
        adapter.notifyDataSetChanged();
        changeNotice();
    }

    @Override
    public void setAccount(Float many) {
        Logger.i("当前余额account: " + many);
        PreferenceUtil.getInstance().putPreferences(Config.MONEY, Float.valueOf(many));
        UserInfo.refresh();
        changeNotice();
    }

    @Override
    public void setDefaultMsg(String message) {
        ToastUtil.showShortToast(message);
    }

    @Override
    public void setStatus(int status) {
        Logger.i("彩票状态: " + status);
        switch (status) {
            case 0:
                ToastUtil.showShortToast("投注未开始");
                break;
            case 1:
                //post上传json
                shoppingPresenter.sendLotteryJsonRequest();
                break;
            case 3:
                ToastUtil.showShortToast("投注已截止");
                break;
        }
    }
}
