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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.zhy.autolayout.AutoLinearLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.base.BaseActivity;
import zhcw.lottery.znzd.com.selflottery.config.ConstantValue;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.ShoppingCartSD;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.TicketSD;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.adapter.Pool3DAdapter;
import zhcw.lottery.znzd.com.selflottery.util.CommonUtils;
import zhcw.lottery.znzd.com.selflottery.util.ShakeListener;
import zhcw.lottery.znzd.com.selflottery.util.ToastUtil;
import zhcw.lottery.znzd.com.selflottery.widgets.MyGridView;
import zhcw.lottery.znzd.com.selflottery.widgets.SelectTDPopupWindow;
import zhcw.lottery.znzd.com.selflottery.widgets.ViewBase;


public class PlaySDActivity extends BaseActivity {
    private static Random random;
    @BindView(R.id.fanhui)
    AutoLinearLayout fanhui;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    /**
     * 随机按钮
     */
    @BindView(R.id.button)
    Button randomBt;
    /**
     * 百位标示
     */
    @BindView(R.id.bai_tv)
    TextView mBaiTv;
    /**
     * 百位号码容器
     */
    @BindView(R.id.ii_3d_red_number_bai)
    MyGridView m3DBai;
    /**
     * 百位父容器
     */
    @BindView(R.id.bai)
    RelativeLayout mBai;
    /**
     * 十位标示
     */
    @BindView(R.id.shi_tv)
    TextView mShiTv;

    /**
     * 十位号码容器
     */
    @BindView(R.id.ii_3d_red_number_shi)
    MyGridView m3DShi;
    /**
     * 十位父容器
     */
    @BindView(R.id.shi)
    RelativeLayout mShi;
    /**
     * 个位号码容器
     */
    @BindView(R.id.ii_3d_red_number_ge)
    MyGridView m3DGe;
    /**
     * 各位父容器
     */
    @BindView(R.id.ge)
    RelativeLayout mGe;
    /**
     * 清空
     */
    @BindView(R.id.ii_bottom_game_choose_clean)
    ImageButton mClean;
    /**
     * 注数金额
     */
    @BindView(R.id.ii_bottom_game_choose_notice)
    TextView mNotice;
    /**
     * 选择好了
     */
    @BindView(R.id.ii_bottom_game_choose_ok)
    ImageButton mChooseOk;
    @BindView(R.id.ii_bottom_game_choose_add)
    ImageButton iiBottomGameChooseAdd;
    /**
     * 文本提示 (例:至少选1个号)
     */
    @BindView(R.id.prompt_tv)
    TextView promptTv;
    @BindView(R.id.shuliang)
    TextView shuliang;
    /**
     * 和值选号
     */
    @BindView(R.id.ii_3d_red_number_hezi)
    MyGridView m3DHezi;
    /**
     * 和值容器
     */
    @BindView(R.id.hezi)
    RelativeLayout hezi;
    @BindView(R.id.play_game)
    LinearLayout playGame;

    private SelectTDPopupWindow mPop;
    private SensorManager manager;
    private ArrayList<Integer> redNumBai;
    private ArrayList<Integer> redNumShi;
    private ArrayList<Integer> redNumGe;
    private Pool3DAdapter redBaiAdapter;
    private Pool3DAdapter redShiAdapter;
    private Pool3DAdapter redGeAdapter;

    private ShakeListener listener;
    private String nameType;
    private Pool3DAdapter redHeAdapter;

    @Override
    public void onPause() {
        manager.unregisterListener(listener);
        super.onPause();
    }

    @Override
    public void onResume() {
        changeNotice();
        listener = new ShakeListener(this) {
            @Override
            public void randomCure() {
                randomSD();
            }
        };
        manager.registerListener(listener, manager.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
        super.onResume();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_play_threed;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        init();
        intiListener();
        ViewBase.showNewbieGuide(this, iiBottomGameChooseAdd, mChooseOk, playGame, mGe);
    }

    private void intiListener() {

        //给容器监听,并添加到数组中
        m3DBai.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);
            // 判断当前点击的item是否被选中了
            if (!redNumBai.contains(position)) {
                if (nameType.equals(ConstantValue.gameSDType_ZLFS)) {
                    if (redNumBai.size() >= 9) {
                        ToastUtil.showShortToast("组六复试不能超过9个号码");
                        return;
                    }
                } else if (nameType.equals(ConstantValue.gameSDType_ZXFS)) {
                    if (redNumBai.size() >= 9) {
                        ToastUtil.showShortToast("单选单复式不能超过9个号码");
                        return;
                    }
                } else {
                    if (nameType.equals(ConstantValue.gameSDType_ZL)) {
                        if (redNumBai.size() >= 3) {
                            ToastUtil.showShortToast("组选六每注3个号码");
                            return;
                        }
                    } else if (nameType.equals(ConstantValue.gameSDType_ZS)
                            || nameType.equals(ConstantValue.gameSDType_ZX)) {
                        if (redNumBai.size() >= 1) {
                            redNumBai.clear();
                            redBaiAdapter.notifyDataSetChanged();
                        }
                    }
                }
                //如果没有被选中 背景图片切换到红色
                view.setBackgroundResource(R.mipmap.ball_red);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.white));
                // 摇晃的动画
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
                redNumBai.add(position);
            } else {
                //被选中 还原背景图片
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.red2));
                view.setBackgroundResource(R.mipmap.ball_gray);
                redNumBai.remove((Object) (position));
            }
            if (nameType.equals(ConstantValue.gameSDType_ZS) && redNumShi.contains(position)) {
                redNumShi.remove((Object) (position));
            }
            changeNotice();
        });
        //给容器监听,并添加到数组中
        m3DShi.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);
            // 判断当前点击的item是否被选中了
            if (!redNumShi.contains(position)) {
                if (nameType.equals(ConstantValue.gameSDType_ZS)
                        || nameType.equals(ConstantValue.gameSDType_ZX)) {
                    if (redNumShi.size() >= 1) {
                        redNumShi.clear();
                        redShiAdapter.notifyDataSetChanged();
                    }
                }
                //如果没有被选中 背景图片切换到红色
                view.setBackgroundResource(R.mipmap.ball_red);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.white));
                // 摇晃的动画
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
                redNumShi.add(position);
            } else {
                //被选中 还原背景图片
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.red2));
                view.setBackgroundResource(R.mipmap.ball_gray);
                redNumShi.remove((Object) (position));
            }
            if (nameType.equals(ConstantValue.gameSDType_ZS) && redNumBai.contains(position)) {
                redNumBai.remove((Object) (position));
            }
            changeNotice();
        });
        //给容器监听,并添加到数组中
        m3DGe.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);
            // 判断当前点击的item是否被选中了
            if (!redNumGe.contains(position)) {
                if (nameType.equals(ConstantValue.gameSDType_ZX)) {
                    if (redNumGe.size() >= 1) {
                        redNumGe.clear();
                        redShiAdapter.notifyDataSetChanged();
                    }
                }
                view.setBackgroundResource(R.mipmap.ball_red);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.white));
                // 摇晃的动画
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
                redNumGe.add(position);
            } else {
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.red2));
                view.setBackgroundResource(R.mipmap.ball_gray);
                redNumGe.remove((Object) (position));
            }
            changeNotice();
        });

        m3DHezi.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);
            // 判断当前点击的item是否被选中了
            if (!redNumBai.contains(position)) {
                if (nameType.equals(ConstantValue.gameSDType_ZXHZ) ||
                        nameType.equals(ConstantValue.gameSDType_ZLHZ) ||
                        nameType.equals(ConstantValue.gameSDType_ZSHZ)) {
                    if (redNumBai.size() >= 1) {
                        redNumBai.clear();
                        redBaiAdapter.notifyDataSetChanged();
                    }
                }
                //如果没有被选中 背景图片切换到红色
                view.setBackgroundResource(R.mipmap.ball_red);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.white));
                // 摇晃的动画
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
                redNumBai.add(position);
            } else {
                //被选中 还原背景图片
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.red2));
                view.setBackgroundResource(R.mipmap.ball_gray);
                redNumBai.remove((Object) (position));
            }
            changeNotice();
        });
    }

    private void init() {
        mPop = new SelectTDPopupWindow(this, (view, string) -> {
            name.setText(string);
            nameType = string;
            switch (string) {
                case ConstantValue.gameSDType_ZX://直选
                    promptTv.setText(getString(R.string.is_sdz_default_prompt));
                    mBai.setVisibility(View.VISIBLE);
                    mGe.setVisibility(View.VISIBLE);
                    mShi.setVisibility(View.VISIBLE);
                    hezi.setVisibility(View.GONE);
                    mBaiTv.setText("百位");
                    mShiTv.setText("十位");
                    hiddenPop();
                    break;
                case ConstantValue.gameSDType_ZS://组三
                    promptTv.setText(getString(R.string.is_sdzs_default_prompt));
                    mShi.setVisibility(View.VISIBLE);
                    mBai.setVisibility(View.VISIBLE);
                    mGe.setVisibility(View.GONE);
                    hezi.setVisibility(View.GONE);
                    mBaiTv.setText("对数");
                    mShiTv.setText("独数");
                    hiddenPop();
                    break;
                case ConstantValue.gameSDType_ZL://组六
                    promptTv.setText(getString(R.string.is_sdzl_default_prompt));
                    mBai.setVisibility(View.VISIBLE);
                    mGe.setVisibility(View.GONE);
                    mShi.setVisibility(View.GONE);
                    hezi.setVisibility(View.GONE);
                    mBaiTv.setText("选号");
                    hiddenPop();
                    break;
                case ConstantValue.gameSDType_ZXHZ://直选和值
                    promptTv.setText(getString(R.string.is_sdhz_default_prompt));
                    redHeAdapter.setEndNum(28, 0);
                    hezi.setVisibility(View.VISIBLE);
                    mGe.setVisibility(View.GONE);
                    mShi.setVisibility(View.GONE);
                    mBai.setVisibility(View.GONE);
                    mBaiTv.setText("选号");
                    hiddenPop();
                    break;
                case ConstantValue.gameSDType_ZSHZ://组三和值
                    promptTv.setText(getString(R.string.is_sdhz_default_prompt));
                    hezi.setVisibility(View.VISIBLE);
                    redHeAdapter.setEndNum(26, 1);
                    mGe.setVisibility(View.GONE);
                    mShi.setVisibility(View.GONE);
                    mBai.setVisibility(View.GONE);
                    mBaiTv.setText("选号");
                    hiddenPop();
                    break;
                case ConstantValue.gameSDType_ZLHZ://组六和值
                    promptTv.setText(getString(R.string.is_sdhz_default_prompt));
                    hezi.setVisibility(View.VISIBLE);
                    mGe.setVisibility(View.GONE);
                    redHeAdapter.setEndNum(22, 3);
                    mShi.setVisibility(View.GONE);
                    mBai.setVisibility(View.GONE);
                    mBaiTv.setText("选号");
                    hiddenPop();
                    break;
                case ConstantValue.gameSDType_ZXFS://直选复试
                    promptTv.setText(getString(R.string.is_sdzxfs_default_prompt));
                    mBai.setVisibility(View.VISIBLE);
                    mGe.setVisibility(View.GONE);
                    mShi.setVisibility(View.GONE);
                    hezi.setVisibility(View.GONE);
                    mBaiTv.setText("选号");
                    hiddenPop();
                    break;
                case ConstantValue.gameSDType_ZSFS://组三复试
                    promptTv.setText(getString(R.string.is_sdzsfs_default_prompt));
                    mBai.setVisibility(View.VISIBLE);
                    mGe.setVisibility(View.GONE);
                    mShi.setVisibility(View.GONE);
                    hezi.setVisibility(View.GONE);
                    mBaiTv.setText("选号");
                    hiddenPop();
                    break;
                case ConstantValue.gameSDType_ZLFS://组六复试
                    promptTv.setText(getString(R.string.is_sdzlf_default_prompt));
                    mBai.setVisibility(View.VISIBLE);
                    mGe.setVisibility(View.GONE);
                    mShi.setVisibility(View.GONE);
                    hezi.setVisibility(View.GONE);
                    mBaiTv.setText("选号");
                    hiddenPop();
                    break;
                case ConstantValue.gameSDType_ZXWX://直选位选
                    promptTv.setText(getString(R.string.is_sdzw_default_prompt));
                    mBai.setVisibility(View.VISIBLE);
                    mGe.setVisibility(View.VISIBLE);
                    mShi.setVisibility(View.VISIBLE);
                    hezi.setVisibility(View.GONE);
                    mBaiTv.setText("百位");
                    mShiTv.setText("十位");
                    hiddenPop();
                    break;
            }
        });

        nameType = ConstantValue.gameSDType_ZX;
        name.setText(nameType);
        promptTv.setText(getString(R.string.is_sdz_default_prompt));
        mGe.setVisibility(View.VISIBLE);
        mShi.setVisibility(View.VISIBLE);

        redNumBai = new ArrayList<Integer>();
        redNumShi = new ArrayList<Integer>();
        redNumGe = new ArrayList<Integer>();

        redBaiAdapter = new Pool3DAdapter(this, 10, redNumBai, R.mipmap.ball_red, R.color.red2, 1);
        redShiAdapter = new Pool3DAdapter(this, 10, redNumShi, R.mipmap.ball_red, R.color.red2, 1);
        redGeAdapter = new Pool3DAdapter(this, 10, redNumGe, R.mipmap.ball_red, R.color.red2, 1);
        redHeAdapter = new Pool3DAdapter(this, 28, redNumBai, R.mipmap.ball_red, R.color.red2, 1);

        m3DBai.setAdapter(redBaiAdapter);
        m3DShi.setAdapter(redShiAdapter);
        m3DGe.setAdapter(redGeAdapter);
        m3DHezi.setAdapter(redHeAdapter);
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @OnClick({R.id.fanhui, R.id.button, R.id.ii_bottom_game_choose_add,
            R.id.ii_bottom_game_choose_clean, R.id.ii_bottom_game_choose_ok, R.id.play_game})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.play_game:
                mPop.showAsDropDown(toolbar);
                break;
            case R.id.button://随机
                randomSD();
                break;
            case R.id.ii_bottom_game_choose_clean://清除
                clear();
                break;
            case R.id.ii_bottom_game_choose_ok:  //选好了
                startActivity(new Intent(this, ShoppingSDActivity.class));
                break;
            case R.id.ii_bottom_game_choose_add:  //添加到订单
                addToShiping();
                break;
        }
    }

    private void hiddenPop() {
        clear();
        if (mPop.isShowing()) {
            mPop.dismiss();
        }
    }

    /**
     * 区分玩法
     *
     * @param randomSD
     */
    public void setRandomSD(Random randomSD) {
        clear();
        if (nameType.equals(ConstantValue.gameSDType_ZX) ||
                nameType.equals(ConstantValue.gameSDType_ZXWX)) {
            randomBai(randomSD);
            randomShi(randomSD);
            randomGe(randomSD);
        } else if (nameType.equals(ConstantValue.gameSDType_ZS)) {
            randomBai(randomSD);
            randomShi(randomSD);
        } else if (nameType.equals(ConstantValue.gameSDType_ZXHZ) ||
                nameType.equals(ConstantValue.gameSDType_ZLHZ) ||
                nameType.equals(ConstantValue.gameSDType_ZSHZ)) {
            randomHeZhi(randomSD);
        } else {
            randomBai(randomSD);
        }
        changeNotice();
    }

    /**
     * 随机一注
     */
    private void randomSD() {
        if (random == null) {
            random = new Random();
            setRandomSD(random);
        } else {
            setRandomSD(random);
        }
    }

    private void randomHeZhi(Random random) {
        int numHeZhi = 0;
        if (nameType.equals(ConstantValue.gameSDType_ZXHZ)) {
            numHeZhi = random.nextInt(28);
        } else if (nameType.equals(ConstantValue.gameSDType_ZSHZ)) {
            numHeZhi = random.nextInt(26);
        } else if (nameType.equals(ConstantValue.gameSDType_ZLHZ)) {
            numHeZhi = random.nextInt(22);
        }
        redNumBai.add(numHeZhi);
    }

    /**
     * 百位
     *
     * @param random
     */
    private void randomBai(Random random) {
        int temp = 0;
        //机选红球
        if (nameType.equals(ConstantValue.gameSDType_ZX) ||
                nameType.equals(ConstantValue.gameSDType_ZS)) {
            temp = 1;
        } else if (nameType.equals(ConstantValue.gameSDType_ZSFS) ||
                nameType.equals(ConstantValue.gameSDType_ZXWX)) {
            temp = 2;
        } else if (nameType.equals(ConstantValue.gameSDType_ZL)) {
            temp = 3;
        } else if (nameType.equals(ConstantValue.gameSDType_ZLFS) ||
                (nameType.equals(ConstantValue.gameSDType_ZXFS))) {
            temp = 4;
        }

        while (redNumBai.size() < temp) {
            int numBai = random.nextInt(10);
            if (redNumBai.contains(numBai)) { //去重
                continue;
            }
            redNumBai.add(numBai);
        }
    }

    /**
     * 十位
     *
     * @param random
     */
    private void randomShi(Random random) {
        int temp = 0;
        //机选红球
        if (nameType.equals(ConstantValue.gameSDType_ZX) ||
                nameType.equals(ConstantValue.gameSDType_ZS)) {
            temp = 1;
        } else if (nameType.equals(ConstantValue.gameSDType_ZXWX)) {
            temp = 2;
        }

        while (redNumShi.size() < temp) {
            int numBai = random.nextInt(10);
            if (redNumShi.contains(numBai)) { //去重
                continue;
            }
            redNumShi.add(numBai);
        }
    }

    /**
     * 个位
     *
     * @param random
     */
    private void randomGe(Random random) {

        int temp = 0;
        //机选红球
        if (nameType.equals(ConstantValue.gameSDType_ZX)) {
            temp = 1;
        } else if (nameType.equals(ConstantValue.gameSDType_ZXWX)) {
            temp = 2;
        }
        while (redNumGe.size() < temp) {
            int numBai = random.nextInt(10);
            if (redNumGe.contains(numBai)) { //去重
                continue;
            }
            redNumGe.add(numBai);
        }
    }

    private void changeNotice() {
        String notice = "0注 0元";

        if (!nameType.equals(ConstantValue.gameSDType_ZLFS)) {
            notice = "共 " + calc() + "注 " + calc() * 2 + "元";
        } else {
            if (redNumBai.size() < 4) {
                notice = "您还需要选择" + (4 - redNumBai.size()) + "个号码";
            } else {
                notice = "共 " + calc() + "注 " + calc() * 2 + "元";
            }
        }

        mNotice.setText(notice);

        if (ShoppingCartSD.getInstance().getTicketSD().size() >= 1) {
            shuliang.setVisibility(View.VISIBLE);
            if (ShoppingCartSD.getInstance().getTicketSD().size() > 9) {
                shuliang.setText("9+");
            } else {
                shuliang.setText(String.format("%s", ShoppingCartSD.getInstance().getTicketSD().size()));
            }
        } else {
            shuliang.setVisibility(View.GONE);
        }
        redBaiAdapter.notifyDataSetChanged();
        redShiAdapter.notifyDataSetChanged();
        redGeAdapter.notifyDataSetChanged();
        redHeAdapter.notifyDataSetChanged();
    }

    /**
     * 计算注数
     *
     * @return 注数
     */
    private int calc() {
        int redC;
        switch (nameType) {
            case ConstantValue.gameSDType_ZX:
                //直选
                redC = redNumBai.size() * redNumShi.size() * redNumGe.size();
                return redC;
            case ConstantValue.gameSDType_ZXWX:
                //直选位选
                redC = redNumBai.size() * redNumShi.size() * redNumGe.size();
                return redC > 1 ? redC : 0;
            case ConstantValue.gameSDType_ZS:
                //组三
                redC = redNumBai.size() * redNumShi.size();
                return redC;
            case ConstantValue.gameSDType_ZSFS:
                //组复试
                redC = redNumBai.size() * (redNumBai.size() - 1);
                return redC;
            case ConstantValue.gameSDType_ZXHZ://直选和值
                int[] ZHXH = getResources().getIntArray(R.array._3DZXHZ_Num);
                if (redNumBai.size() > 0)
                    return ZHXH[redNumBai.get(0)];
            case ConstantValue.gameSDType_ZLHZ://直六和值
                int[] ZLH = getResources().getIntArray(R.array._3DZLHZ_Num);
                if (redNumBai.size() > 0)
                    return ZLH[redNumBai.get(0)];
            case ConstantValue.gameSDType_ZSHZ://直三和值
                int[] ZSH = getResources().getIntArray(R.array._3DZSHZ_Num);
                if (redNumBai.size() > 0)
                    return ZSH[redNumBai.get(0)];
            case ConstantValue.gameSDType_ZLFS:
                //组六复试
                if (redNumBai.size() >= 4) {
                    return (int) (factorial(redNumBai.size()) /
                            (factorial(3) * factorial(redNumBai.size() - 3)));
                } else {
                    return 0;
                }
            case ConstantValue.gameSDType_ZL:
                //组六
                return (int) (factorial(redNumBai.size()) /
                        (factorial(3) * factorial(redNumBai.size() - 3)));
            case ConstantValue.gameSDType_ZXFS:
                int[] intArray = getResources().getIntArray(R.array._3DZXFS_Num);
                return intArray[redNumBai.size()];
            default:
                return 0;
        }
    }


    /**
     * 清空数据
     */
    public void clear() {
        redNumBai.clear();
        redNumShi.clear();
        redNumGe.clear();
        changeNotice();
    }

    /**
     * 添加购物车
     */
    private void addToShiping() {
        // ①判断：用户是否选择了一注投注
        if (ConstantValue.gameSDType_ZXWX.equals(nameType)) {
            temp(2);
        } else {
            temp(1);
        }
    }

    private void temp(int i) {
        if (calc() >= i) {
            if (ConstantValue.gameSDType_ZXWX.equals(nameType) && calc() > 2 && calc() * 2 > 1200) {
                ToastUtil.showShortToast("直选位选-单倍投注金额不超过1200元");
                return;
            } else {
                //// 一个购物车中，只能放置一个彩种，当前期的投注信息
                // ②判断：是否获取到了当前销售期的信息
                lotteryInfo();
                // ③清除现有UI展示数据
                clear();
                // ⑥界面跳转——购物车展示
                ToastUtil.showShortToast("已添加到购票车,祝您好运...");
            }
        } else {
            // 提示：需要选择一注
            if (ConstantValue.gameSDType_ZXWX.equals(nameType)) {
                ToastUtil.showShortToast("请至少选择2注添加购物车");
            }
            if (nameType.equals(ConstantValue.gameSDType_ZLFS)) {
                ToastUtil.showShortToast("请示至少选择4个号码");
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
        Collections.sort(redNumBai);//排序
        Collections.sort(redNumShi);
        Collections.sort(redNumGe);

        TicketSD ticketSD = new TicketSD();
        DecimalFormat decimalFormat = new DecimalFormat("0");

        //存储注数信息
        ticketSD.setNum(calc());
        //玩法种类
        if (nameType.equals(ConstantValue.gameSDType_ZX)) {//直选
            StringBuffer jsonBuffer = new StringBuffer("");
            jsonBuffer.append(redNumBai.get(0)).append(",")
                    .append(redNumShi.get(0)).append(",")
                    .append(redNumGe.get(0));
            ticketSD.setJsonNum(jsonBuffer.toString());
            ticketSD.setViewNum(jsonBuffer.toString());
            ticketSD.setLotteryid(ConstantValue.SD_P);
        } else if (nameType.equals(ConstantValue.gameSDType_ZXWX)) {//直选按位包号
            StringBuffer jsonBuffer = new StringBuffer("");
            for (Integer item : redNumBai) {
                jsonBuffer.append(item);
            }
            jsonBuffer.append(",");
            for (Integer item : redNumShi) {
                jsonBuffer.append(item);
            }
            jsonBuffer.append(",");
            for (Integer item : redNumGe) {
                jsonBuffer.append(item);
            }
            ticketSD.setJsonNum(jsonBuffer.toString());
            ticketSD.setViewNum(jsonBuffer.toString());
            ticketSD.setLotteryid(ConstantValue.SD_ZX_NWBH);
        } else if (nameType.equals(ConstantValue.gameSDType_ZS)) {//组三
            StringBuffer jsonBuffer = new StringBuffer("");
            redNumShi.add(redNumBai.get(0));
            redNumShi.add(redNumBai.get(0));
            Collections.sort(redNumShi);
            for (Integer item : redNumShi) {
                jsonBuffer.append(",").append(decimalFormat.format(item));
            }
            ticketSD.setJsonNum(jsonBuffer.substring(1));
            ticketSD.setViewNum(jsonBuffer.substring(1));
            ticketSD.setLotteryid(ConstantValue.SD_ZS);
        } else if (nameType.equals(ConstantValue.gameSDType_ZL)) {//组六
            StringBuffer jsonBuffer = new StringBuffer("");
            for (Integer item : redNumBai) {
                jsonBuffer.append(",").append(decimalFormat.format(item));
            }
            ticketSD.setJsonNum(jsonBuffer.substring(1));
            ticketSD.setViewNum(jsonBuffer.substring(1));
            ticketSD.setLotteryid(ConstantValue.SD_ZL);
        } else if (nameType.equals(ConstantValue.gameSDType_ZXFS)) {//直选复试
            StringBuffer jsonBuffer = new StringBuffer("");
            for (Integer item : redNumBai) {
                jsonBuffer.append(",").append(decimalFormat.format(item));
            }
            ticketSD.setJsonNum(jsonBuffer.substring(1));
            ticketSD.setViewNum(jsonBuffer.substring(1));
            ticketSD.setLotteryid(ConstantValue.SD_ZF);
        } else if (nameType.equals(ConstantValue.gameSDType_ZSFS)) {//组三复试
            StringBuffer jsonBuffer = new StringBuffer("");
            for (Integer item : redNumBai) {
                jsonBuffer.append(",").append(decimalFormat.format(item));
            }
            ticketSD.setJsonNum(jsonBuffer.substring(1));
            ticketSD.setViewNum(jsonBuffer.substring(1));
            ticketSD.setLotteryid(ConstantValue.SD_ZSF);
        } else if (nameType.equals(ConstantValue.gameSDType_ZLFS)) {//组六复试
            StringBuffer jsonBuffer = new StringBuffer("");
            for (Integer item : redNumBai) {
                jsonBuffer.append(",").append(decimalFormat.format(item));
            }
            ticketSD.setJsonNum(jsonBuffer.substring(1));
            ticketSD.setViewNum(jsonBuffer.substring(1));
            ticketSD.setLotteryid(ConstantValue.SD_ZLF);
        } else {//和值
            Integer integer = redNumBai.get(0);
            if (nameType.equals(ConstantValue.gameSDType_ZXHZ)) {
                ticketSD.setJsonNum(integer + "");
                ticketSD.setViewNum(integer + "");
                ticketSD.setLotteryid(ConstantValue.SD_ZXHZ);
            } else if (nameType.equals(ConstantValue.gameSDType_ZSHZ)) {
                ticketSD.setJsonNum(integer + 1 + "");
                ticketSD.setViewNum(integer + 1 + "");
                ticketSD.setLotteryid(ConstantValue.SD_ZSHZ);
            } else if (nameType.equals(ConstantValue.gameSDType_ZLHZ)) {
                ticketSD.setJsonNum(integer + 3 + "");
                ticketSD.setViewNum(integer + 3 + "");
                ticketSD.setLotteryid(ConstantValue.SD_ZLHZ);
            }
        }

        // ④创建彩票购物车，将投注信息添加到购物车中
        String lotteryid = ShoppingCartSD.getInstance().getLotteryid();
        if (lotteryid != null && lotteryid.isEmpty()) {
            ShoppingCartSD.getInstance().getTicketSD().add(ticketSD);
        } else {
            if (lotteryid == ConstantValue.SD) {
                ShoppingCartSD.getInstance().getTicketSD().add(ticketSD);
            } else {
                ShoppingCartSD.getInstance().getTicketSD().clear();
                ShoppingCartSD.getInstance().getTicketSD().add(ticketSD);
            }
        }
        // ⑤设置彩种的标示，设置彩种期次
        ShoppingCartSD.getInstance().setLotteryid(ConstantValue.SD);
    }
}

