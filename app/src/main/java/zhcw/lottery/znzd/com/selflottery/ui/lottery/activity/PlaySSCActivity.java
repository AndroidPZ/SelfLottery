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
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.base.BaseActivity;
import zhcw.lottery.znzd.com.selflottery.config.ConstantValue;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.ShoppingCartSSC;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.TicketSSC;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.adapter.Pool3DAdapter;
import zhcw.lottery.znzd.com.selflottery.util.CommonUtils;
import zhcw.lottery.znzd.com.selflottery.util.Logger;
import zhcw.lottery.znzd.com.selflottery.util.ShakeListener;
import zhcw.lottery.znzd.com.selflottery.util.ToastUtil;
import zhcw.lottery.znzd.com.selflottery.util.Utils;
import zhcw.lottery.znzd.com.selflottery.widgets.MyGridView;
import zhcw.lottery.znzd.com.selflottery.widgets.SelectSSCPopupWindow;
import zhcw.lottery.znzd.com.selflottery.widgets.ViewBase;


public class PlaySSCActivity extends BaseActivity {
    private static Random random;
    @BindView(R.id.fanhui)
    AutoLinearLayout fanhui;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.prompt_tv)
    TextView promptTv;
    @BindView(R.id.button)
    Button button;
    /**
     * 万位标示
     */
    @BindView(R.id.wan_tv)
    TextView mWanTv;
    /**
     * 万位号码容器
     */
    @BindView(R.id.ii_ssc_red_number_wan)
    MyGridView mSscWan;
    @BindView(R.id.wan)
    RelativeLayout mWan;
    @BindView(R.id.qian_tv)
    TextView qianTv;
    /**
     * 千位号码容器
     */
    @BindView(R.id.ii_ssc_red_number_qian)
    MyGridView mSscQian;
    @BindView(R.id.qian)
    RelativeLayout mQian;
    @BindView(R.id.bai_tv)
    TextView baiTv;
    /**
     * 百位号码容器
     */
    @BindView(R.id.ii_ssc_red_number_bai)
    MyGridView mSscBai;
    @BindView(R.id.bai)
    RelativeLayout mBai;
    @BindView(R.id.shi_tv)
    TextView shiTv;
    /**
     * 十位号码容器
     */
    @BindView(R.id.ii_ssc_red_number_shi)
    MyGridView mSscShi;
    @BindView(R.id.shi)
    RelativeLayout mShi;
    @BindView(R.id.ge_tv)
    TextView mGeTv;
    /**
     * 个位号码容器
     */
    @BindView(R.id.ii_ssc_red_number_ge)
    MyGridView mSscGe;
    @BindView(R.id.ge)
    RelativeLayout mGe;

    /**
     * 加入购物车
     */
    @BindView(R.id.ii_bottom_game_choose_add)
    ImageButton iiBottomGameChooseAdd;
    @BindView(R.id.shuliang)
    TextView shuliang;
    /**
     * 注数金额
     */
    @BindView(R.id.ii_bottom_game_choose_notice)
    TextView mNotice;
    /**
     * 选择好了
     */
    @BindView(R.id.ii_bottom_game_choose_ok)
    ImageButton iiBottomGameChooseOk;
    /**
     * 清空
     */
    @BindView(R.id.ii_bottom_game_choose_clean)
    ImageButton iiBottomGameChooseClean;
    @BindView(R.id.rl_container_pt)
    AutoRelativeLayout mRlContainerPt;
    @BindView(R.id.ii_number_dxjoshi)
    MyGridView mDxjoshi;
    @BindView(R.id.ii_number_dxjoge)
    MyGridView mDxjoge;
    @BindView(R.id.rl_container_dxjo)
    AutoRelativeLayout mRlContainerDxjo;
    @BindView(R.id.play_game)
    LinearLayout playGame;

    private SelectSSCPopupWindow mPop;
    private SensorManager manager;
    private ArrayList<Integer> redNumWan;
    private ArrayList<Integer> redNumQian;
    private ArrayList<Integer> redNumBai;
    private ArrayList<Integer> redNumShi;
    private ArrayList<Integer> redNumGe;
    private Pool3DAdapter redWanAdapter;
    private Pool3DAdapter redQianAdapter;
    private Pool3DAdapter redBaiAdapter;
    private Pool3DAdapter redShiAdapter;
    private Pool3DAdapter redGeAdapter;
    private ShakeListener listener;
    private String nameType;
    private StringBuffer redBufferSeed;
    private Random randomSSC;
    private Pool3DAdapter mDxjoShiAdapter;
    private Pool3DAdapter mDxjoGeAdapter;
    private String[] stringArray;

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
                randomSSC();
            }
        };
        manager.registerListener(listener, manager.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        redNumWan = null;
        redNumQian = null;
        redNumBai = null;
        redNumShi = null;
        redNumGe = null;

        redWanAdapter = null;
        redQianAdapter = null;
        redBaiAdapter = null;
        redShiAdapter = null;
        redGeAdapter = null;
        Logger.i("onDestroy");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_play_ssc;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
        intiListener();
        ViewBase.showNewbieGuide(this, iiBottomGameChooseAdd, iiBottomGameChooseOk, playGame, mSscBai);
    }

    private void initView() {
        promptTv.setText(getString(R.string.is_ssc_dxjo_default_prompt));
        mName.setText(ConstantValue.gameSSCType_DS);
        nameType = ConstantValue.gameSSCType_DS;
        stringArray = getResources().getStringArray(R.array.sscmsgNumberList);
        redNumWan = new ArrayList<Integer>();
        redNumQian = new ArrayList<Integer>();
        redNumBai = new ArrayList<Integer>();
        redNumShi = new ArrayList<Integer>();
        redNumGe = new ArrayList<Integer>();

        redWanAdapter = new Pool3DAdapter(this, 10, redNumWan, R.mipmap.ball_red, R.color.red2, 1);
        redQianAdapter = new Pool3DAdapter(this, 10, redNumQian, R.mipmap.ball_red, R.color.red2, 1);
        redBaiAdapter = new Pool3DAdapter(this, 10, redNumBai, R.mipmap.ball_red, R.color.red2, 1);
        redShiAdapter = new Pool3DAdapter(this, 10, redNumShi, R.mipmap.ball_red, R.color.red2, 1);
        redGeAdapter = new Pool3DAdapter(this, 10, redNumGe, R.mipmap.ball_red, R.color.red2, 1);

        mDxjoShiAdapter = new Pool3DAdapter(this, 4, redNumShi, R.mipmap.ball_red, R.color.red2, 0);
        mDxjoGeAdapter = new Pool3DAdapter(this, 4, redNumGe, R.mipmap.ball_red, R.color.red2, 0);

        mSscWan.setAdapter(redWanAdapter);
        mSscQian.setAdapter(redQianAdapter);
        mSscBai.setAdapter(redBaiAdapter);
        mSscShi.setAdapter(redShiAdapter);
        mSscGe.setAdapter(redGeAdapter);

        mDxjoshi.setAdapter(mDxjoShiAdapter);
        mDxjoge.setAdapter(mDxjoGeAdapter);
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @OnClick({R.id.fanhui, R.id.ii_bottom_game_choose_clean, R.id.button, 
            R.id.ii_bottom_game_choose_add, R.id.ii_bottom_game_choose_ok, R.id.play_game})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fanhui: //返回
                finish();
                break;
            case R.id.play_game:
                mPop.showAsDropDown(toolbar);
                break;
            case R.id.ii_bottom_game_choose_clean: //清除
                clear();
                break;
            case R.id.button: //机选
                randomSSC();
                break;
            case R.id.ii_bottom_game_choose_add: // 添加到购物车
                addToShiping();
                break;
            case R.id.ii_bottom_game_choose_ok:  // 选好了
                startActivity(new Intent(this, ShoppingSSCActivity.class));
                break;
        }
    }

    private void intiListener() {

        mPop = new SelectSSCPopupWindow(this, (view, string) -> {
            mName.setText(string);
            nameType = string;
            Is_visible(string);
            switch (string) {
                case ConstantValue.gameSSCType_DS:  //使用 个位 和十位 百位 万位
                    promptTv.setText(getString(R.string.is_ssc_dxjo_default_prompt));
                    hiddenPop();
                    break;
                case ConstantValue.gameSSCType_FX:  //复选  使用 个位 和十位 百位 万位
                    promptTv.setText(getString(R.string.is_ssc_fx_default_prompt));
                    hiddenPop();
                    break;
                case ConstantValue.gameSSCType_FS:  //复式(位选)  使用 个位 和十位 百位 万位
                    promptTv.setText(getString(R.string.is_ssc_wx_default_prompt));
                    hiddenPop();
                    break;
                case ConstantValue.gameSSCType_WXTX:  //五星通选(位选)  使用 个位 和十位 百位 万位
                case ConstantValue.gameSSCType_DXJO:  //大小奇偶
                    promptTv.setText(getString(R.string.is_ssc_wxtx_default_prompt));
                    hiddenPop();
                    break;
            }
        });

        //给容器监听,并添加到数组中
        mSscWan.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);
            // 判断当前点击的item是否被选中了
            if (!redNumWan.contains(position)) {
                if (!nameType.equals(ConstantValue.gameSSCType_FS)) {
                    if (redNumWan.size() >= 1) {
                        redNumWan.clear();
                        redWanAdapter.notifyDataSetChanged();
                        redWanAdapter.notifyDataSetInvalidated();
                    }
                }
                //如果没有被选中 背景图片切换到红色
                view.setBackgroundResource(R.mipmap.ball_red);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.white));
                // 摇晃的动画
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
                redNumWan.add(position);
            } else {
                //被选中 还原背景图片
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.red2));
                view.setBackgroundResource(R.mipmap.ball_gray);
                redNumWan.remove((Object) (position));
            }
            changeNotice();
        });
        //给容器监听,并添加到数组中
        mSscQian.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);
            // 判断当前点击的item是否被选中了
            if (!redNumQian.contains(position)) {
                if (!nameType.equals(ConstantValue.gameSSCType_FS)) {
                    if (redNumQian.size() >= 1) {
                        redNumQian.clear();
                        redQianAdapter.notifyDataSetChanged();
                    }
                }

                // 背景图片切换到红色
                view.setBackgroundResource(R.mipmap.ball_red);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.white));
                // 摇晃的动画
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
                redNumQian.add(position);
            } else {
                // 被选中 还原背景图片
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.red2));
                view.setBackgroundResource(R.mipmap.ball_gray);
                redNumQian.remove((Object) (position));
            }
            changeNotice();
        });
        //给容器监听,并添加到数组中
        mSscBai.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);
            // 判断当前点击的item是否被选中了
            if (!redNumBai.contains(position)) {
                if (!nameType.equals(ConstantValue.gameSSCType_FS)) {
                    if (redNumBai.size() >= 1) {
                        redNumBai.clear();
                        redBaiAdapter.notifyDataSetChanged();
                    }
                }
                // 背景图片切换到红色
                view.setBackgroundResource(R.mipmap.ball_red);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.white));
                // 摇晃的动画
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
                redNumBai.add(position);
            } else {
                // 还原背景图片
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.red2));
                view.setBackgroundResource(R.mipmap.ball_gray);
                redNumBai.remove((Object) (position));
            }
            changeNotice();
        });
        //给容器监听,并添加到数组中
        mSscShi.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);
            // 判断当前点击的item是否被选中了
            if (!redNumShi.contains(position)) {
                if (!nameType.equals(ConstantValue.gameSSCType_FS)) {
                    if (redNumShi.size() >= 1) {
                        redNumShi.clear();
                        redShiAdapter.notifyDataSetChanged();
                    }
                }

                // 背景图片切换到红色
                view.setBackgroundResource(R.mipmap.ball_red);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.white));
                // 摇晃的动画
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
                redNumShi.add(position);
            } else {
                // 还原背景图片
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.red2));
                view.setBackgroundResource(R.mipmap.ball_gray);
                redNumShi.remove((Object) (position));
            }
            changeNotice();
        });
        //给容器监听,并添加到数组中
        mSscGe.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);
            // 判断当前点击的item是否被选中了
            if (!redNumGe.contains(position)) {
                if (!nameType.equals(ConstantValue.gameSSCType_FS)) {
                    if (redNumGe.size() >= 1) {
                        redNumGe.clear();
                        redGeAdapter.notifyDataSetChanged();
                    }
                }

                // 背景图片切换到红色
                view.setBackgroundResource(R.mipmap.ball_red);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.white));
                // 摇晃的动画
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
                redNumGe.add(position);
            } else {
                // 被选中
                // 还原背景图片
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.red2));
                view.setBackgroundResource(R.mipmap.ball_gray);
                redNumGe.remove((Object) (position));
            }
            changeNotice();
        });

        //给容器监听,并添加到数组中
        mDxjoshi.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);
            // 判断当前点击的item是否被选中了
            if (!redNumShi.contains(position)) {
                if (redNumShi.size() >= 1) {
                    redNumShi.clear();
                    mDxjoShiAdapter.notifyDataSetChanged();
                }
                // 背景图片切换到红色
                view.setBackgroundResource(R.mipmap.ball_red);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.white));
                // 摇晃的动画
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
                redNumShi.add(position);
            } else {
                // 被选中还原背景图片
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.red2));
                view.setBackgroundResource(R.mipmap.ball_gray);
                redNumShi.remove((Object) (position));
            }
            changeNotice();
        });

        //给容器监听,并添加到数组中
        mDxjoge.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);
            // 判断当前点击的item是否被选中了
            if (!redNumGe.contains(position)) {
                if (redNumGe.size() >= 1) {
                    redNumGe.clear();
                    mDxjoGeAdapter.notifyDataSetChanged();
                }
                // 背景图片切换到红色
                view.setBackgroundResource(R.mipmap.ball_red);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.white));
                // 摇晃的动画
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
                redNumGe.add(position);
            } else {
                // 被选中还原背景图片
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.red2));
                view.setBackgroundResource(R.mipmap.ball_gray);
                redNumGe.remove((Object) (position));
            }
            changeNotice();
        });
    }

    /**
     * 隐藏和显示
     *
     * @param s
     */
    private void Is_visible(String s) {
        switch (s) {
            case ConstantValue.gameSSCType_DS:
            case ConstantValue.gameSSCType_FX:
            case ConstantValue.gameSSCType_FS:
            case ConstantValue.gameSSCType_WXTX:
                mRlContainerPt.setVisibility(View.VISIBLE);
                mRlContainerDxjo.setVisibility(View.GONE);
                break;
            case ConstantValue.gameSSCType_DXJO:
                mRlContainerPt.setVisibility(View.GONE);
                mRlContainerDxjo.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 清空数据
     */
    public void clear() {
        redNumWan.clear();
        redNumQian.clear();
        redNumBai.clear();
        redNumShi.clear();
        redNumGe.clear();
        changeNotice();
    }

    private void hiddenPop() {
        clear();
        if (mPop.isShowing())
            mPop.dismiss();
    }

    private void changeNotice() {
        String notice = "0注 0元";
        //普通投注
        int calc = calc();
        if (calc >= 1) {
            notice = "共 " + calc + " 注 " + calc * 2 + " 元";
        }
        mNotice.setText(notice);
        if (ShoppingCartSSC.getInstance().getTicketSSC().size() >= 1) {
            shuliang.setVisibility(View.VISIBLE);
            if (ShoppingCartSSC.getInstance().getTicketSSC().size() > 9) {
                shuliang.setText("9+");
            } else {
                shuliang.setText(ShoppingCartSSC.getInstance().getTicketSSC().size() + "");
            }
        } else {
            shuliang.setVisibility(View.GONE);
        }
        if (nameType.equals(ConstantValue.gameSSCType_DXJO)) {
            mDxjoGeAdapter.notifyDataSetChanged();
            mDxjoShiAdapter.notifyDataSetChanged();
        } else {
            redWanAdapter.notifyDataSetChanged();
            redQianAdapter.notifyDataSetChanged();
            redBaiAdapter.notifyDataSetChanged();
            redShiAdapter.notifyDataSetChanged();
            redGeAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 计算注数
     *
     * @return 注数(普通投注的)
     */
    private int calc() {
        if (nameType.equals(ConstantValue.gameSSCType_DS)) {
            addWQBSG_Num();
            return Utils.getSscDsCalc(redBufferSeed.toString());
        } else if (nameType.equals(ConstantValue.gameSSCType_FX)) {
            addWQBSG_Num();
            int xingType = Utils.getXingType(redBufferSeed.toString());
            int sscDsCalc = Utils.getSscDsCalc(redBufferSeed.toString());
            if (sscDsCalc == 1 && xingType != 4) {
                return 5 - xingType;
            }
        } else if (nameType.equals(ConstantValue.gameSSCType_FS)) {
            addWQBSG_Num();
            if (Utils.getSscDsCalc(redBufferSeed.toString()) == 1) {
                switch (Utils.getXingType(redBufferSeed.toString())) {
                    case 0:
                        return redNumWan.size() * redNumQian.size() * redNumBai.size() * redNumShi.size() * redNumGe.size();
                    case 1:
                        return redNumQian.size() * redNumBai.size() * redNumShi.size() * redNumGe.size();
                    case 2:
                        return redNumBai.size() * redNumShi.size() * redNumGe.size();
                    case 3:
                        return redNumShi.size() * redNumGe.size();
                    case 4:
                        return redNumGe.size();
                }
            }
        } else if (nameType.equals(ConstantValue.gameSSCType_WXTX)) {
            addWQBSG_Num();
            return Utils.getXingType(redBufferSeed.toString()) == 0 ? 1 : 0;
        } else if (nameType.equals(ConstantValue.gameSSCType_DXJO)) {
            return redNumShi.size() * redNumGe.size();
        }
        return 0;
    }

    /**
     * 号码拼接
     */
    private void addWQBSG_Num() {
        if (redBufferSeed == null) {
            redBufferSeed = new StringBuffer("");
        }
        redBufferSeed.setLength(0);//清空StringBuffer
        if (redNumWan.size() >= 1) {
            redBufferSeed.append(redNumWan.get(0));
        } else {
            redBufferSeed.append("_");
        }
        if (redNumQian.size() >= 1) {
            redBufferSeed.append(",").append(redNumQian.get(0));
        } else {
            redBufferSeed.append(",_");
        }
        if (redNumBai.size() >= 1) {
            redBufferSeed.append(",").append(redNumBai.get(0));
        } else {
            redBufferSeed.append(",_");
        }
        if (redNumShi.size() >= 1) {
            redBufferSeed.append(",").append(redNumShi.get(0));
        } else {
            redBufferSeed.append(",_");
        }
        if (redNumGe.size() >= 1) {
            redBufferSeed.append(",").append(redNumGe.get(0));
        } else {
            redBufferSeed.append(",_");
        }
    }

    /**
     * 添加购物车
     */
    private void addToShiping() {
        // ①判断：用户是否选择了一注投注
        if (nameType.equals(ConstantValue.gameSSCType_FS)) {
            temp(2);
        } else {
            temp(1);
        }
    }

    private void temp(int i) {
        if (calc() >= i) {
            //// 一个购物车中，只能放置一个彩种，当前期的投注信息
            // ②判断：是否获取到了当前销售期的信息
            if (calc() * 2 <= ConstantValue.UPPER_LIMIT) {//投注不能超过10000
                lotteryInfo();
                // ③清除现有UI展示数据
                clear();
                // ⑥界面跳转——购物车展示
                ToastUtil.showShortToast("已添加到购票车,祝您好运...");
//                startActivity(new Intent(this, ShoppingSSQActivity.class));
            } else {
                // 重新获取期次信息
                ToastUtil.showShortToast("为了健康投注,单票不能超过" + ConstantValue.UPPER_LIMIT + "元");
            }
        } else {
            // 提示：需要选择一注
            ToastUtil.showShortToast("请添加至少选择" + i + "注彩票添加购物车");
        }
    }

    /**
     * 封装用户投注信息
     */
    private void lotteryInfo() {

        // ③封装用户的投注信息：红球、蓝球、注数
        Collections.sort(redNumWan);//排序
        Collections.sort(redNumQian);
        Collections.sort(redNumBai);
        Collections.sort(redNumShi);
        Collections.sort(redNumGe);

        TicketSSC ticketSSC = new TicketSSC();

        StringBuffer redBufferSeed = new StringBuffer("");
        StringBuffer redBufferView = new StringBuffer("");

        if (!nameType.equals(ConstantValue.gameSSCType_DXJO)) {
            if (redNumWan.size() >= 1) {
                for (int number : redNumWan) {
                    redBufferSeed.append(number);
                }
            } else {
                redBufferSeed.append("_");
            }
            if (redNumQian.size() >= 1) {
                redBufferSeed.append(",");
                for (int number : redNumQian) {
                    redBufferSeed.append(number);
                }
            } else {
                redBufferSeed.append(",_");
            }
            if (redNumBai.size() >= 1) {
                redBufferSeed.append(",");
                for (int number : redNumBai) {
                    redBufferSeed.append(number);
                }
            } else {
                redBufferSeed.append(",_");
            }
            if (redNumShi.size() >= 1) {
                redBufferSeed.append(",");
                for (int number : redNumShi) {
                    redBufferSeed.append(number);
                }
            } else {
                redBufferSeed.append(",_");
            }
            if (redNumGe.size() >= 1) {
                redBufferSeed.append(",");
                for (int number : redNumGe) {
                    redBufferSeed.append(number);
                }
            }
            ticketSSC.setRedNumSeed(redBufferSeed.toString());
            ticketSSC.setRedNumView(" " + redBufferSeed.toString());
        } else { //大小奇偶
            redBufferView.setLength(0);
            redBufferView.append(" ").append(stringArray[redNumShi.get(0)])
                    .append(stringArray[redNumGe.get(0)]);
            ticketSSC.setRedNumView(redBufferView.toString());
            ticketSSC.setRedNumSeed(Utils.getCodeArrayMap()
                    .get(redBufferView.toString()));
        }

        //存储注数信息
        ticketSSC.setNum(calc());
        switch (nameType) {  //玩法种类
            case ConstantValue.gameSSCType_DS:
                ticketSSC.setLotteryid(ConstantValue.SSC_P);//直选
                break;
            case ConstantValue.gameSSCType_FX:
                ticketSSC.setLotteryid(ConstantValue.SSC_F);//复选投注
                break;
            case ConstantValue.gameSSCType_FS:
                ticketSSC.setLotteryid(ConstantValue.SSC_FW);//复式投注(位选)
                break;
            case ConstantValue.gameSSCType_DXJO:
                ticketSSC.setLotteryid(ConstantValue.SSC_DXJO);//大小奇偶
                break;
            case ConstantValue.gameSSCType_WXTX:
                ticketSSC.setLotteryid(ConstantValue.SSC_WXTX);//五星通选
                break;
        }

        // ④创建彩票购物车，将投注信息添加到购物车中
        String lotteryid = ShoppingCartSSC.getInstance().getLotteryid();
        if (lotteryid != null && lotteryid.isEmpty()) {
            ShoppingCartSSC.getInstance().getTicketSSC().add(ticketSSC);
        } else {
            if (ConstantValue.SSC.equals(lotteryid)) {
                ShoppingCartSSC.getInstance().getTicketSSC().add(ticketSSC);
            } else {
                ShoppingCartSSC.getInstance().getTicketSSC().clear();
                ShoppingCartSSC.getInstance().getTicketSSC().add(ticketSSC);
            }
        }
        // ⑤设置彩种的标示，设置彩种期次
        ShoppingCartSSC.getInstance().setLotteryid(ConstantValue.SSC);
    }

    /**
     * 随机一注时时彩
     */
    private void randomSSC() {
        if (random == null) {
            random = new Random();
            setRandomSSC(random);
        } else {
            setRandomSSC(random);
        }
    }

    /**
     * 玩法区分
     */
    public void setRandomSSC(Random randomSSC) {
        clear();
        if (nameType.equals(ConstantValue.gameSSCType_DS) ||
                nameType.equals(ConstantValue.gameSSCType_FX) ||
                nameType.equals(ConstantValue.gameSSCType_WXTX)) {
            getRandomNum(redNumWan, 1);
            getRandomNum(redNumQian, 1);
            getRandomNum(redNumBai, 1);
            getRandomNum(redNumShi, 1);
            getRandomNum(redNumGe, 1);
        } else if (nameType.equals(ConstantValue.gameSSCType_FS)) {
            getRandomNum(redNumWan, 2);
            getRandomNum(redNumQian, 2);
            getRandomNum(redNumBai, 2);
            getRandomNum(redNumShi, 2);
            getRandomNum(redNumGe, 2);
        } else if (nameType.equals(ConstantValue.gameSSCType_DXJO)) {
            redNumShi.add(randomSSC.nextInt(4));
            redNumGe.add(randomSSC.nextInt(4));
        }
        changeNotice();
    }

    public void getRandomNum(ArrayList<Integer> integers, int temp) {
        while (integers.size() < temp) {
            int numBai = random.nextInt(10);
            if (integers.contains(numBai)) { //去重
                continue;
            }
            integers.add(numBai);
        }
    }
}
