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
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.ShoppingCartSSQ;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.TicketSSQ;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.adapter.PoolSSQAdapter;
import zhcw.lottery.znzd.com.selflottery.util.CommonUtils;
import zhcw.lottery.znzd.com.selflottery.util.Logger;
import zhcw.lottery.znzd.com.selflottery.util.ShakeListener;
import zhcw.lottery.znzd.com.selflottery.util.ToastUtil;
import zhcw.lottery.znzd.com.selflottery.widgets.MyGridView;
import zhcw.lottery.znzd.com.selflottery.widgets.SelectPicPopupWindow;
import zhcw.lottery.znzd.com.selflottery.widgets.ViewBase;


public class PlaySSQActivity extends BaseActivity {

    @BindView(R.id.fanhui)
    AutoLinearLayout fanhui;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.ii_ssq_random_red)
    Button iiSsqRandomRed;
    @BindView(R.id.ii_ssq_red_number_container)
    MyGridView redContainer;
    @BindView(R.id.ii_ssq_blue_number_container)
    MyGridView blueContainer;
    @BindView(R.id.ii_bottom_game_choose_clean)
    ImageButton iiBottomGameChooseClean;
    @BindView(R.id.ii_bottom_game_choose_notice)
    TextView iiBottomGameChooseNotice;
    @BindView(R.id.ii_bottom_game_choose_ok)
    ImageButton iiBottomGameChooseOk;
    @BindView(R.id.ii_bottom_game_choose_add)
    ImageButton iiBottomGameChooseAdd;
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
    @BindView(R.id.red_temp)
    TextView redTemp;
    @BindView(R.id.shuliang)
    TextView shuliang;
    @BindView(R.id.play_game)
    LinearLayout playGame;
    @BindView(R.id.type)
    LinearLayout type;

    private PoolSSQAdapter redAdapter;
    private PoolSSQAdapter redAdapterTuo;
    private PoolSSQAdapter blueAdapter;

    private List<Integer> redNums;
    private List<Integer> redNumsTuo;
    private List<Integer> blueNums;
    private SensorManager manager;
    private ShakeListener listener;
    private PopupWindow mPop;
    private String playName;
    private int position;
    private Bundle info;

    @Override
    public void onPause() {
        manager.unregisterListener(listener);
        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logger.i("SSQ......onNewIntent........");
    }

    @Override
    public void onResume() {
        changeNotice();
        listener = new ShakeListener(this) {
            @Override
            public void randomCure() {
                randomSSQ();
            }
        };
        manager.registerListener(listener, manager.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER)
                , SensorManager.SENSOR_DELAY_FASTEST);
        super.onResume();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_play_ssq;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        inti();
        intiListener();
        ViewBase.showNewbieGuide(this, iiBottomGameChooseAdd, iiBottomGameChooseOk, playGame, type);
    }

    private void intiListener() {

        //给容器监听,并添加到数组中
        redContainer.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);
            // 判断当前点击的item是否被选中了
            if (!redNums.contains(position + 1)) {

                if (playName.equals(ConstantValue.gameType_ZX)) {
                    if (redNums.size() >= 6) {
                        ToastUtil.showShortToast("红球不可超过6个球");
                        return;
                    }
                } else if (playName.equals(ConstantValue.gameType_FS)) {
                    if (redNums.size() >= 16) {
                        ToastUtil.showShortToast("红球不可超过16个球");
                        return;
                    }
                } else if (playName.equals(ConstantValue.gameType_DT)) {
                    if (redNums.size() >= 5) {
                        ToastUtil.showShortToast("胆码不可超过5个球");
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

                if (playName.equals(ConstantValue.gameType_DT)) {
                    if (redNumsTuo.size() >= 20) {
                        ToastUtil.showShortToast("红球不可超过20个球");
                        return;
                    }
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

        blueContainer.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);
            // 判断当前点击的item是否被选中了
            if (!blueNums.contains(position + 1)) {

                if (playName.equals(ConstantValue.gameType_ZX)) {
                    if (blueNums.size() >= 1) {
                        blueNums.clear();
                        blueAdapter.notifyDataSetChanged();
                    }
                }

                // 如果没有被选中
                // 背景图片切换到红色
                view.setBackgroundResource(R.mipmap.ball_blue);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.white));
                // 摇晃的动画
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
                blueNums.add(position + 1);
            } else {
                // 被选中
                // 还原背景图片
                view.setBackgroundResource(R.mipmap.ball_gray);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.Blue_700));
                blueNums.remove((Object) (position + 1));
            }
            changeNotice();
        });

    }

    private void inti() {

        mPop = new SelectPicPopupWindow(this, (view, string) -> {
            name.setText(string);
            playName = string;
            switch (playName) {
                case ConstantValue.gameType_ZX:
                    reTuoMa.setVisibility(View.GONE);
                    tempDan.setText(getString(R.string.is_ssq_default_prompt));
                    redTemp.setText("红球区");
                    clear();
                    hiddenPop();
                    break;
                case ConstantValue.gameType_DT:
                    reTuoMa.setVisibility(View.VISIBLE);
                    tempDan.setText(getString(R.string.is_ssqdt_default_prompt));
                    redTemp.setText("胆码区");
                    clear();
                    hiddenPop();
                    break;
                case ConstantValue.gameType_FS:
                    reTuoMa.setVisibility(View.GONE);
                    tempDan.setText(getString(R.string.is_ssqfs_default_prompt));
                    redTemp.setText("红球区");
                    clear();
                    hiddenPop();
                    break;
            }
        });

        redNums = new ArrayList<>();
        redNumsTuo = new ArrayList<>();
        blueNums = new ArrayList<>();

        redAdapter = new PoolSSQAdapter(this, 33, redNums, R.mipmap.ball_red, R.color.red2);
        redAdapterTuo = new PoolSSQAdapter(this, 33, redNumsTuo, R.mipmap.ball_red, R.color.red2);
        blueAdapter = new PoolSSQAdapter(this, 16, blueNums, R.mipmap.ball_blue, R.color.Blue_700);

        playName = ConstantValue.gameType_ZX;
        tempDan.setText(getString(R.string.is_ssq_default_prompt));
        redTemp.setText("红球区");
        reTuoMa.setVisibility(View.GONE);

        Intent intent = getIntent();
        info = intent.getExtras();
        if (info != null) {
            position = info.getInt("position");
            TicketSSQ ticketSSQ = (TicketSSQ) info.getSerializable("info");
            Logger.i("Intent  info!=null " + ticketSSQ.toString());
            if (ConstantValue.SSQ_P.equals(ticketSSQ.getLotteryid())) {
                playName = ConstantValue.gameType_ZX;
            } else if (ConstantValue.SSQ_FS.equals(ticketSSQ.getLotteryid())) {
                playName = ConstantValue.gameType_FS;
            } else if (ConstantValue.SSQ_DT.equals(ticketSSQ.getLotteryid())) {
                playName = ConstantValue.gameType_DT;
            }

            switch (playName) {
                case ConstantValue.gameType_ZX:
                    reTuoMa.setVisibility(View.GONE);
                    tempDan.setText(getString(R.string.is_ssq_default_prompt));
                    redTemp.setText("红球区");
                    clear();
                    break;
                case ConstantValue.gameType_DT:
                    reTuoMa.setVisibility(View.VISIBLE);
                    tempDan.setText(getString(R.string.is_ssqdt_default_prompt));
                    redTemp.setText("胆码区");
                    clear();
                    break;
                case ConstantValue.gameType_FS:
                    reTuoMa.setVisibility(View.GONE);
                    tempDan.setText(getString(R.string.is_ssqfs_default_prompt));
                    redTemp.setText("红球区");
                    clear();
                    break;
            }
            String redNumJ = ticketSSQ.getRedNumJ();
            String[] split = redNumJ.split("\\,");
            for (String num : split) {
                redNums.add(Integer.valueOf(num));
            }
            String blueNumJ = ticketSSQ.getBlueNumJ();
            String[] splitb = blueNumJ.split("\\,");
            for (String num : splitb) {
                blueNums.add(Integer.valueOf(num));
            }
            String redNumTuoJ = ticketSSQ.getRedNumTuoJ();

            if (redNumTuoJ != null) {
                String[] splitbt = redNumTuoJ.split("\\,");
                for (String num : splitbt) {
                    redNumsTuo.add(Integer.valueOf(num));
                }
            }
        }

        name.setText(playName);

        redContainer.setAdapter(redAdapter);
        redContainerTuo.setAdapter(redAdapterTuo);
        blueContainer.setAdapter(blueAdapter);

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
                randomBlue(random);
                if (!ConstantValue.gameType_ZX.equals(playName)) {
                    randomRedTuo(random);
                }
                //界面更新
                changeNotice();
                break;
            case R.id.ii_bottom_game_choose_clean: //清空
                clear();
                break;
            case R.id.ii_bottom_game_choose_ok:  //选好了
                startActivity(new Intent(this, ShoppingSSQActivity.class));
                break;
            case R.id.ii_bottom_game_choose_add:  //添加到订单
                addToShiping();
                break;
            case R.id.play_game:  //选择购彩方式(直,胆拖)
                //设置宽高
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
        if (ConstantValue.gameType_ZX.equals(playName)) {
            temp = 6;
        } else if (ConstantValue.gameType_FS.equals(playName)) {
            temp = 7;
        } else {
            temp = 5;
        }

        while (redNums.size() < temp) {
            int num = random.nextInt(33) + 1;
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
            int num = random.nextInt(33) + 1;
            if (redNums.contains(num) || redNumsTuo.contains(num)) {
                continue;
            }
            redNumsTuo.add(num);
        }
        //界面更新
        redAdapterTuo.notifyDataSetChanged();
    }

    //机选一注
    private void randomSSQ() {
        Random random = new Random();
        randomRed(random);
        if (!ConstantValue.gameType_ZX.equals(playName))
            randomRedTuo(random);
        //机选蓝球
        randomBlue(random);
        changeNotice();
    }

    private void randomBlue(Random random) {
        blueNums.clear();
        int num = random.nextInt(16) + 1;
        blueNums.add(num);
        blueAdapter.notifyDataSetChanged();
    }

    /**
     * 底部导航提示信息
     */
    private void changeNotice() {
        String notice = "";
        if (playName.equals(ConstantValue.gameType_ZX)) {
            //普通投注
            if (redNums.size() < 6) {
                notice = "您还需要选择" + (6 - redNums.size()) + "个红球";
            } else if (blueNums.size() < 1) {
                notice = "您还需要选择" + 1 + "个蓝球";
            } else {
                notice = "共 " + calc() + "注 " + calc() * 2 + "元";
            }
        } else if (playName.equals(ConstantValue.gameType_FS)) {
            //复试
            if (redNums.size() < 6) {
                notice = "您还需要选择" + (6 - redNums.size()) + "个红球";
            } else if (redNums.size() == 6) {
                notice = "您还需要选择" + (2 - blueNums.size()) + "个蓝球";
            } else {
                notice = "您还需要选择" + 1 + "个蓝球";
            }
            if (redNums.size() == 6 && blueNums.size() >= 2 ||
                    redNums.size() > 6 && blueNums.size() >= 1) {
                notice = "共 " + calc() + "注 " + calc() * 2 + "元";
            }
        } else {
            if (playName.equals(ConstantValue.gameType_DT)) {
                //胆拖投注
                notice = "共 " + 0 + "注 " + 0 + "元";
                if (redNums.size() >= 1)
                    if (redNums.size() + redNumsTuo.size() < 6) {
                        notice = "共 " + 0 + "注 " + 0 + "元";
                    } else if (blueNums.size() == 0) {
                        notice = "您还需要选择" + 1 + "个蓝球";
                    } else if (blueNums.size() == 1) {
                        notice = "您还需要选择" + 1 + "个红球或篮球";
                    } else {
                        notice = "共 " + calcTuo() + "注 " + calcTuo() * 2 + "元";
                    }
                redAdapter.notifyDataSetChanged();
                redAdapterTuo.notifyDataSetChanged();
            }
        }

        if (ShoppingCartSSQ.getInstance().getTicketSSQS().size() >= 1) {
            shuliang.setVisibility(View.VISIBLE);
            if (ShoppingCartSSQ.getInstance().getTicketSSQS().size() > 9) {
                shuliang.setText("9+");
            } else {
                shuliang.setText(String.format("%s", ShoppingCartSSQ.getInstance().getTicketSSQS().size()));
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
        int redC = (int) (factorial(redNums.size()) / (factorial(6) * factorial(redNums.size() - 6)));
        //蓝球
        int blueC = blueNums.size();
        if (playName.equals(ConstantValue.gameType_FS) && redC * blueC == 1) {
            return 0;
        }
        return redC * blueC;
    }

    /**
     * 计算注数
     *
     * @return 注数(胆拖投注的)
     */
    private int calcTuo() {
        int redC = 0;
        int zongshu = redNumsTuo.size() + redNums.size();
        if (zongshu == 6) {
            redC = 1;
        } else if (zongshu > 6) {
            redC = (int) (factorial(redNumsTuo.size()) / (factorial(6 - redNums.size()) * factorial(zongshu - 6)));
        }
        //红球 蓝球
        int blueC = blueNums.size();
        return redC * blueC;
    }

    /**
     * 清空数据
     */
    public void clear() {
        redNums.clear();
        redNumsTuo.clear();
        blueNums.clear();
        changeNotice();

        redAdapter.notifyDataSetChanged();
        redAdapterTuo.notifyDataSetChanged();
        blueAdapter.notifyDataSetChanged();
    }

    /**
     * 添加到购物车
     */
    private void addToShiping() {
        // ①判断：用户是否选择了一注投注
        if (ConstantValue.gameType_FS.equals(playName) ||
                ConstantValue.gameType_DT.equals(playName)) {
            if (calc() >= 2 || calcTuo() >= 2) {
                // 一个购物车中，只能放置一个彩种，当前期的投注信息
                boolean b;
                if (ConstantValue.gameType_FS.equals(playName)) {
                    b = calc() * 2 <= ConstantValue.UPPER_LIMIT;
                } else {
                    b = calcTuo() * 2 <= ConstantValue.UPPER_LIMIT;
                }
                // ②判断：是否获取到了当前销售期的信息
                if (b) {//投注不能超过10000
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
                ToastUtil.showShortToast("请至少选择2注加入购物车");
            }
        } else {
            if (calc() >= 1) {
                // ②判断：是否获取到了当前销售期的信息
                if (calc() * 2 <= ConstantValue.UPPER_LIMIT) {//投注不能超过10000
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
        Collections.sort(blueNums);

        TicketSSQ ticketSSQ = new TicketSSQ();
        DecimalFormat decimalFormat = new DecimalFormat("00");
        StringBuffer redBuffer = new StringBuffer();
        StringBuffer redBufferJ = new StringBuffer();
        for (Integer item : redNums) {
            redBuffer.append(" ").append(decimalFormat.format(item));
            redBufferJ.append(",").append(decimalFormat.format(item));
        }
        ticketSSQ.setRedNum(redBuffer.substring(1));
        ticketSSQ.setRedNumJ(redBufferJ.substring(1));
        if (ConstantValue.gameType_DT.equals(playName)) {
            //拖码
            StringBuffer redTuoBuffer = new StringBuffer();
            StringBuffer redTuoBufferJ = new StringBuffer();
            for (Integer item : redNumsTuo) {
                redTuoBuffer.append(" ").append(decimalFormat.format(item));
                redTuoBufferJ.append(",").append(decimalFormat.format(item));
            }
            ticketSSQ.setRedNumTuo(redTuoBuffer.substring(1));
            ticketSSQ.setRedNumTuoJ(redTuoBufferJ.substring(1));
        }
        //篮球
        StringBuffer blueBuffer = new StringBuffer();
        StringBuffer blueBufferJ = new StringBuffer();
        for (Integer item : blueNums) {
            blueBuffer.append(" ").append(decimalFormat.format(item));
            blueBufferJ.append(",").append(decimalFormat.format(item));
        }
        ticketSSQ.setBlueNum(blueBuffer.substring(1));
        ticketSSQ.setBlueNumJ(blueBufferJ.substring(1));

        if (playName.equals(ConstantValue.gameType_ZX) || playName.equals(ConstantValue.gameType_FS))
            ticketSSQ.setNum(calc());
        else
            ticketSSQ.setNum(calcTuo());

        if (playName.equals(ConstantValue.gameType_ZX) || playName.equals(ConstantValue.gameType_FS))
            if (calc() > 1)
                ticketSSQ.setLotteryid(ConstantValue.SSQ_FS);
            else
                ticketSSQ.setLotteryid(ConstantValue.SSQ_P);
        else
            ticketSSQ.setLotteryid(ConstantValue.SSQ_DT);

        // ④创建彩票购物车，将投注信息添加到购物车中
        String lotteryid = ShoppingCartSSQ.getInstance().getLotteryid();
        if (lotteryid != null && lotteryid.isEmpty()) {
            ShoppingCartSSQ.getInstance().getTicketSSQS().add(ticketSSQ);
        } else {
            if (ConstantValue.SSQ.equals(lotteryid)) {
                if (position != -1 && info != null) {
                    ShoppingCartSSQ.getInstance().getTicketSSQS().remove(position);
                    ShoppingCartSSQ.getInstance().getTicketSSQS().add(position, ticketSSQ);
                } else {
                    ShoppingCartSSQ.getInstance().getTicketSSQS().add(ticketSSQ);
                }
            } else {
                ShoppingCartSSQ.getInstance().getTicketSSQS().clear();
                ShoppingCartSSQ.getInstance().getTicketSSQS().add(ticketSSQ);
            }
        }
        // ⑤设置彩种的标示，设置彩种期次
//                ShoppingCartSSQ.getInstance().setIssue(bundle.getString("issue"));
        ShoppingCartSSQ.getInstance().setLotteryid(ConstantValue.SSQ);

    }
}
