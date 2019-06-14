package zhcw.lottery.znzd.com.selflottery.ui.lottery.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.zhy.autolayout.AutoLinearLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.base.BaseActivity;
import zhcw.lottery.znzd.com.selflottery.config.ConstantValue;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.ShoppingCart7LC;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.Ticket7LC;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.adapter.PoolSSQAdapter;
import zhcw.lottery.znzd.com.selflottery.util.CommonUtils;
import zhcw.lottery.znzd.com.selflottery.util.ShakeListener;
import zhcw.lottery.znzd.com.selflottery.util.ToastUtil;
import zhcw.lottery.znzd.com.selflottery.widgets.MyGridView;
import zhcw.lottery.znzd.com.selflottery.widgets.SelectPicPopupWindow;
import zhcw.lottery.znzd.com.selflottery.widgets.ViewBase;


public class Play7LCActivity extends BaseActivity {

    @BindView(R.id.fanhui)
    AutoLinearLayout fanhui;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.ii_ssq_random_red)
    Button iiSsqRandomRed;
    @BindView(R.id.ii_ssq_red_number_container)
    MyGridView redContainer;
    @BindView(R.id.ii_bottom_game_choose_clean)
    ImageButton iiBottomGameChooseClean;
    @BindView(R.id.ii_bottom_game_choose_notice)
    TextView iiBottomGameChooseNotice;
    @BindView(R.id.ii_bottom_game_choose_ok)
    ImageButton iiBottomGameChooseOk;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.ii_bottom_game)
    LinearLayout iiBottomGame;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ii_ssq_red_number_container_tuo)
    MyGridView redContainerTuo;
    @BindView(R.id.re_tuo_ma)
    RelativeLayout reTuoMa;
    @BindView(R.id.temp_dan)
    TextView tempDan;
    @BindView(R.id.temp_tuo)
    TextView tempTuo;
    @BindView(R.id.red_temp)
    TextView redTemp;
    @BindView(R.id.ii_bottom_game_choose_add)
    ImageButton iiBottomGameChooseAdd;
    @BindView(R.id.shuliang)
    TextView shuliang;
    @BindView(R.id.play_game)
    LinearLayout playGame;

    private PoolSSQAdapter redAdapter;
    private PoolSSQAdapter redAdapterTuo;

    private List<Integer> redNums;
    private List<Integer> redNumsTuo;
    private SensorManager manager;
    private ShakeListener listener;
    private PopupWindow mPop;
    private String playName;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_play_7lc;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        inti();
        intiListener();
        ViewBase.showNewbieGuide(this, iiBottomGameChooseAdd, iiBottomGameChooseOk, playGame, redContainer);
    }

    private void intiListener() {

        //给容器监听,并添加到数组中
        redContainer.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);
            // 判断当前点击的item是否被选中了
            if (!redNums.contains(position + 1)) {

                if (playName.equals(ConstantValue.game7LCType_ZX)) {
                    if (redNums.size() >= 7) {
                        ToastUtil.showShortToast("直选每注7个球");
                        return;
                    }
                } else if (playName.equals(ConstantValue.game7LCType_FS)) {
                    if (redNums.size() >= 15) {
                        ToastUtil.showShortToast("不可超过15个球");
                        return;
                    }
                } else if (playName.equals(ConstantValue.game7LCType_DT)) {
                    if (redNums.size() >= 6) {
                        ToastUtil.showShortToast("胆码不可超过6个球");
                        return;
                    }
                }
                // 如果没有被选中
                // 背景图片切换到红色
                view.setBackgroundResource(R.mipmap.ball_red);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.white));
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
                redNums.add(position + 1);
            } else {
                // 被选中
                // 还原背景图片
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.red2));
                view.setBackgroundResource(R.mipmap.ball_gray);
                redNums.remove((Object) (position + 1));
            }
            if (redNumsTuo.contains(position + 1)) {
                redNumsTuo.remove((Object) (position + 1));
            }
            changeNotice();
        });


        redContainerTuo.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);
            // 判断当前点击的item是否被选中了
            if (!redNumsTuo.contains(position + 1)) {
                if (redNumsTuo.size() >= 20) {
                    ToastUtil.showShortToast("拖码最多20个球");
                    return;
                }
                // 如果没有被选中
                // 背景图片切换到红色
                view.setBackgroundResource(R.mipmap.ball_red);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.white));
                // 摇晃的动画
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
                redNumsTuo.add(position + 1);
            } else {
                // 被选中
                // 还原背景图片
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.red2));
                view.setBackgroundResource(R.mipmap.ball_gray);
                redNumsTuo.remove((Object) (position + 1));
            }
            if (redNums.contains(position + 1)) {
                redNums.remove((Object) (position + 1));
            }
            changeNotice();
        });


    }

    private void inti() {

        mPop = new SelectPicPopupWindow(this, (view, string) -> {
            name.setText(string);
            playName = string;
            switch (string) {
                case ConstantValue.game7LCType_ZX:
                    reTuoMa.setVisibility(View.GONE);
                    tempDan.setText(getString(R.string.is_7lc_dx_default_prompt));
                    redTemp.setText("");
                    redTemp.setVisibility(View.GONE);
                    clear();
                    hiddenPop();
                    break;
                case ConstantValue.game7LCType_DT:
                    reTuoMa.setVisibility(View.VISIBLE);
                    tempDan.setText(getString(R.string.is_7lc_dt_default_prompt));
                    redTemp.setText("胆码区");
                    redTemp.setVisibility(View.VISIBLE);
                    clear();
                    hiddenPop();
                    break;
                case ConstantValue.game7LCType_FS:
                    reTuoMa.setVisibility(View.GONE);
                    tempDan.setText(getString(R.string.is_7lc_fs_default_prompt));
                    redTemp.setText("");
                    redTemp.setVisibility(View.GONE);
                    clear();
                    hiddenPop();
                    break;
            }
        });

        redNums = new ArrayList<Integer>();
        redNumsTuo = new ArrayList<Integer>();

        redAdapter = new PoolSSQAdapter(this, 30, redNums, R.mipmap.ball_red, R.color.red2);
        redAdapterTuo = new PoolSSQAdapter(this, 30, redNumsTuo, R.mipmap.ball_red, R.color.red2);

        playName = ConstantValue.game7LCType_ZX;
        name.setText(playName);
        tempDan.setText("请选择7个号码");
        redTemp.setText("红球区");
        reTuoMa.setVisibility(View.GONE);

        redContainer.setAdapter(redAdapter);
        redContainerTuo.setAdapter(redAdapterTuo);

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    private void hiddenPop() {
        if (mPop.isShowing())
            mPop.dismiss();
    }

    @OnClick({R.id.fanhui, R.id.ii_ssq_random_red, R.id.ii_bottom_game_choose_clean,
            R.id.ii_bottom_game_choose_ok, R.id.ii_bottom_game_choose_add, R.id.play_game})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.ii_ssq_random_red: //机选选号
                Random random = new Random();
                randomRed(random);
                if (!playName.equals(ConstantValue.game7LCType_ZX)) {
                    randomRedTuo(random);
                }
                //界面更新
                changeNotice();
                break;
            case R.id.ii_bottom_game_choose_clean: //清空
                clear();
                break;
            case R.id.ii_bottom_game_choose_ok:  //选好了
                startActivity(new Intent(this, Shopping7LCActivity.class));
                break;
            case R.id.ii_bottom_game_choose_add:  //添加到订单
                addToShiping();
                break;
            case R.id.play_game:  //选择购彩方式(直,胆拖)
                mPop.showAsDropDown(toolbar);
                break;
        }
    }

    /**
     * 普通
     *
     * @param random
     */
    private void randomRed(Random random) {
        redNums.clear();
        int temp;
        //机选红球
        if (playName.equals(ConstantValue.game7LCType_ZX)) {
            temp = 7;
        } else if (playName.equals(ConstantValue.game7LCType_FS)) {
            temp = 8;
        } else {
            temp = 6;
        }

        while (redNums.size() < temp) {
            int num = random.nextInt(30) + 1;
            if (redNums.contains(num)) {
                continue;
            }
            redNums.add(num);
        }
        //界面更新
        redAdapter.notifyDataSetChanged();
    }

    /**
     * 胆拖
     *
     * @param random
     */
    private void randomRedTuo(Random random) {
        redNumsTuo.clear();
        //机选红球
        while (redNumsTuo.size() < 2) {
            int num = random.nextInt(30) + 1;
            if (redNums.contains(num) || redNumsTuo.contains(num)) {
                continue;
            }
            redNumsTuo.add(num);
        }
        //界面更新
        redAdapterTuo.notifyDataSetChanged();
    }

    //机选一注
    private void random7LC() {
        Random random = new Random();
        randomRed(random);
        if (!playName.equals(ConstantValue.game7LCType_ZX))
            randomRedTuo(random);
        changeNotice();
    }


    @Override
    public void onPause() {
        manager.unregisterListener(listener);
        super.onPause();
    }

    @Override
    public void onResume() {
        changeNotice();
//        clear();
        listener = new ShakeListener(this) {
            @Override
            public void randomCure() {
                random7LC();
            }
        };
        manager.registerListener(listener, manager.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
        super.onResume();
    }

    /**
     * 底部导航提示信息
     */
    private void changeNotice() {
        String notice = "";
        if (playName.equals(ConstantValue.game7LCType_ZX)) {
            //普通投注
            if (redNums.size() < 7) {
                notice = "您还需要选择" + (7 - redNums.size()) + "个红球";
            } else {
                notice = "共 " + calc() + "注 " + calc() * 2 + "元";
            }
        } else if (playName.equals(ConstantValue.game7LCType_FS)) {
            //普通投注
            if (redNums.size() < 8) {
                notice = "您还需要选择" + (8 - redNums.size()) + "个红球";
            } else {
                notice = "共 " + calc() + "注 " + calc() * 2 + "元";
            }
        } else {
            if (playName.equals(ConstantValue.game7LCType_DT)) {
                //胆拖投注
                notice = "共 " + 0 + "注 " + 0 + "元";
                if (redNums.size() >= 1)
                    if (redNums.size() + redNumsTuo.size() < 7) {
                        notice = "共 " + 0 + "注 " + 0 + "元";
                    } else {
                        if (calcTuo() >= 2) {
                            notice = "共 " + calcTuo() + "注 " + calcTuo() * 2 + "元";
                        }
                    }
                redAdapter.notifyDataSetChanged();
                redAdapterTuo.notifyDataSetChanged();
            }
        }

        if (ShoppingCart7LC.getInstance().getTicket7LCS().size() >= 1) {
            shuliang.setVisibility(View.VISIBLE);
            if (ShoppingCart7LC.getInstance().getTicket7LCS().size() > 9) {
                shuliang.setText("9+");
            } else {
                shuliang.setText(ShoppingCart7LC.getInstance().getTicket7LCS().size() + "");
            }
        } else {
            shuliang.setVisibility(View.GONE);
        }
        iiBottomGameChooseNotice.setText(notice);
    }

    /**
     * 计算注数
     *
     * @return 注数(普通投注的)
     */
    private int calc() {
        //红球
        int redC = (int) (factorial(redNums.size()) / (factorial(7) * factorial(redNums.size() - 7)));
        return redC;
    }

    /**
     * 计算注数
     *
     * @return 注数(胆拖投注的)
     */
    private int calcTuo() {
        int redC = 0;
        int zongshu = redNumsTuo.size() + redNums.size();
        if (zongshu == 7) {
            redC = 1;
        } else if (zongshu > 7) {
            redC = (int) (factorial(redNumsTuo.size()) / (factorial(7 - redNums.size()) * factorial(zongshu - 7)));
        }
        //红球
        return redC;
    }

    /**
     * 清空数据
     */
    public void clear() {
        redNums.clear();
        redNumsTuo.clear();
        changeNotice();

        redAdapter.notifyDataSetChanged();
        redAdapterTuo.notifyDataSetChanged();
    }

    /**
     * 添加到购物车
     */
    private void addToShiping() {
        if (playName.equals(ConstantValue.game7LCType_FS) ||
                playName.equals(ConstantValue.game7LCType_DT)) {
            // ①判断：用户是否选择了一注投注
            if (calc() >= 2 || calcTuo() >= 2) {
                //// 一个购物车中，只能放置一个彩种，当前期的投注信息
                boolean b;
                if (playName.equals(ConstantValue.game7LCType_FS)) {
                    b = calc() * 2 <= ConstantValue.UPPER_LIMIT;
                } else {
                    b = calcTuo() * 2 <= ConstantValue.UPPER_LIMIT;
                }
                // ②判断：是否获取到了当前销售期的信息
                if (b) {//投注不能超过的金额
                    lotteryInfo();
                    // ③清除现有UI展示数据
                    clear();
                    // ⑥界面跳转——购物车展示
                    ToastUtil.showShortToast("已添加到购票车,祝您好运...");
                } else {
                    // 重新获取期次信息
                    ToastUtil.showShortToast("为了健康投注,单票不能超过" + ConstantValue.UPPER_LIMIT + "元....");
                }
            } else {
                if (playName.equals(ConstantValue.game7LCType_FS)) {
                    ToastUtil.showShortToast("请至少选择8个号码");
                } else {
                    ToastUtil.showShortToast("请至少选择2注加入购物车");
                }
            }
        } else {
            // ①判断：用户是否选择了一注投注
            if (calc() >= 1) {
                // ②判断：是否获取到了当前销售期的信息
                if (calc() * 2 <= ConstantValue.UPPER_LIMIT) {//投注不能超过的金额
                    lotteryInfo();
                    // ③清除现有UI展示数据
                    clear();
                    // ⑥界面跳转——购物车展示
                    ToastUtil.showShortToast("已添加到购票车,祝您好运...");
                } else {
                    // 重新获取期次信息
                    ToastUtil.showShortToast("为了健康投注,单票不能超过" + ConstantValue.UPPER_LIMIT + "元");
                }
            } else {
                ToastUtil.showShortToast(R.string.add_ok_msg);
            }
        }
    }

    /**
     * 封装用户投注信息
     */
    private void lotteryInfo() {
        // ③封装用户的投注信息：红球、蓝球、注数
        Collections.sort(redNums); //排序
        Collections.sort(redNumsTuo);

        Ticket7LC ticket7LC = new Ticket7LC();
        DecimalFormat decimalFormat = new DecimalFormat("00");
        StringBuffer redBuffer = new StringBuffer("");
        StringBuffer redBufferJ = new StringBuffer("");
        for (Integer item : redNums) {
            redBuffer.append(" ").append(decimalFormat.format(item));
            redBufferJ.append(",").append(decimalFormat.format(item));
        }
        ticket7LC.setRedNum(redBuffer.substring(1));
        ticket7LC.setRedNumJ(redBufferJ.substring(1));
        if (playName.equals(ConstantValue.game7LCType_DT)) {
            //拖码
            StringBuffer redTuoBuffer = new StringBuffer();
            StringBuffer redTuoBufferJ = new StringBuffer();
            for (Integer item : redNumsTuo) {
                redTuoBuffer.append(" ").append(decimalFormat.format(item));
                redTuoBufferJ.append(",").append(decimalFormat.format(item));
            }
            ticket7LC.setRedNumTuo(redTuoBuffer.substring(1));
            ticket7LC.setRedNumTuoJ(redTuoBufferJ.substring(1));
        }

        if (playName.equals(ConstantValue.game7LCType_ZX) || playName.equals(ConstantValue.game7LCType_FS))
            ticket7LC.setNum(calc());
        else
            ticket7LC.setNum(calcTuo());

        if (playName.equals(ConstantValue.game7LCType_ZX) || playName.equals(ConstantValue.game7LCType_FS))
            if (calc() > 1)
                ticket7LC.setLotteryid(ConstantValue.T7LC_FS);
            else
                ticket7LC.setLotteryid(ConstantValue.T7LC_P);
        else
            ticket7LC.setLotteryid(ConstantValue.T7LC_DT);

        // ④创建彩票购物车，将投注信息添加到购物车中
        String lotteryid = ShoppingCart7LC.getInstance().getLotteryid();
        if (lotteryid != null && lotteryid.isEmpty()) {
            ShoppingCart7LC.getInstance().getTicket7LCS().add(ticket7LC);
        } else {
            if (lotteryid == ConstantValue.T7LC) {
                ShoppingCart7LC.getInstance().getTicket7LCS().add(ticket7LC);
            } else {
                ShoppingCart7LC.getInstance().getTicket7LCS().clear();
                ShoppingCart7LC.getInstance().getTicket7LCS().add(ticket7LC);
            }
        }
        // ⑤设置彩种的标示，设置彩种期次
//                ShoppingCart7LC.getInstance().setIssue(bundle.getString("issue"));
        ShoppingCart7LC.getInstance().setLotteryid(ConstantValue.T7LC);

    }
}
