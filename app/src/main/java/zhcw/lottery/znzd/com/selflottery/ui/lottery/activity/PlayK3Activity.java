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
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

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
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.ShoppingCartK3;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.TicketK3;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.adapter.PoolK3DAdapter;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.adapter.PoolK3HAdapter;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.adapter.PoolK3PAdapter;
import zhcw.lottery.znzd.com.selflottery.util.CommonUtils;
import zhcw.lottery.znzd.com.selflottery.util.Logger;
import zhcw.lottery.znzd.com.selflottery.util.ShakeListener;
import zhcw.lottery.znzd.com.selflottery.util.ToastUtil;
import zhcw.lottery.znzd.com.selflottery.util.Utils;
import zhcw.lottery.znzd.com.selflottery.widgets.MyGridView;
import zhcw.lottery.znzd.com.selflottery.widgets.SelectK3PopupWindow;
import zhcw.lottery.znzd.com.selflottery.widgets.ViewBase;


public class PlayK3Activity extends BaseActivity {
    private static Random random;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.temp_dan)
    TextView tempDan;
    @BindView(R.id.gv_ks_hezhi_number_container)
    MyGridView hezhiContainer;
    @BindView(R.id.ii_bottom_game_choose_notice)
    TextView iiBottomGameChooseNotice;
    @BindView(R.id.shuliang)
    TextView shuliang;
    @BindView(R.id.tv_santh_tongxuan)
    TextView tvSanTHTX;
    @BindView(R.id.rl_sthtx_container)
    RelativeLayout rlSthtxContainer;                //三同号通选容器
    @BindView(R.id.gv_ks_santh_number_container)
    MyGridView gvSanth;                             //三同号单选
    @BindView(R.id.rl_santh_container)
    RelativeLayout rlSanthContainer;                //三同号单选容器
    @BindView(R.id.ii_ssq_random_red)
    Button mRandomRed;
    @BindView(R.id.gv_ks_sanbth_number_container)
    MyGridView gvKsSanbth;                          //三不同号
    @BindView(R.id.rl_sanbth_container)
    RelativeLayout rlSanbthContainer;               //三不同号
    @BindView(R.id.tv_slhtx_tongxuan)
    TextView tvSlhtxTongxuan;                       //三连号
    @BindView(R.id.rl_slhtx_container)
    RelativeLayout rlSlhtxContainer;                //三连号
    @BindView(R.id.gv_ks_rthfx_number_container)
    MyGridView gvRthfx;                             //二同号复选
    @BindView(R.id.rl_rthfx_container)
    RelativeLayout rlRthfxContainer;                //二同号复选
    @BindView(R.id.gv_ks_th_number_container)
    MyGridView gvKsTh;                              //二同号单选(同号)
    @BindView(R.id.gv_ks_bth_number_container)
    MyGridView gvKsBth;                             //二同号单选(不同号)
    @BindView(R.id.rl_rthdx_container)
    RelativeLayout rlRthdxContainer;                //二同号单选容器
    @BindView(R.id.gv_ks_erbth_number_container)
    MyGridView gvKsErbth;                           //二不同号
    @BindView(R.id.rl_erbth_container)
    RelativeLayout rlErbthContainer;                //二不同号容器
    /*旧 单选集合*/
    @BindView(R.id.ii_k3_number_bai)
    MyGridView mK3Bai;
    @BindView(R.id.ii_k3_number_shi)
    MyGridView mK3Shi;
    @BindView(R.id.ii_k3_number_ge)
    MyGridView mK3Ge;
    @BindView(R.id.zhixuan_rl)
    RelativeLayout zhixuanRl;//单式容器
    @BindView(R.id.ii_bottom_game_choose_add)
    ImageButton iiBottomGameChooseAdd;
    @BindView(R.id.ii_bottom_game_choose_ok)
    ImageButton iiBottomGameChooseOk;
    @BindView(R.id.play_game)
    LinearLayout playGame;


    private PoolK3HAdapter heZhiAdapter;
    private List<Integer> hezhiNums;
    private List<String> txuan;
    private SensorManager manager;
    private ShakeListener listener;
    private PopupWindow mPop;
    private String playName;
    private ArrayList<Integer> sthDxNums;
    private ArrayList<Integer> k3Nums;
    private PoolK3PAdapter sanThDxAdapter;
    private PoolK3PAdapter sanBthAdapter;
    private ArrayList<Integer> sBthNums;
    private PoolK3PAdapter rthFxAdapter;
    private PoolK3PAdapter rthDxAdapter;

    private ArrayList<Integer> redNumBai;
    private ArrayList<Integer> redNumShi;
    private ArrayList<Integer> redNumGe;
    private PoolK3DAdapter redBaiAdapter;
    private PoolK3DAdapter redShiAdapter;
    private PoolK3DAdapter redGeAdapter;

    DecimalFormat decimalFormat = new DecimalFormat("00");
    StringBuffer stringBuffer = new StringBuffer("");
    StringBuffer stringBufferJ = new StringBuffer("");

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
                randomK3();
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
        if (playName.equals(ConstantValue.gameK3Type_HZ)) {
            //和值
            if (hezhiNums.size() < 1) {
                notice = "您还需要选择" + 1 + "个和值";
            } else {
                notice = "共 " + calc() + "注 " + calc() * 2 + "元";
            }
        } else {
            notice = "共 " + calc() + "注 " + calc() * 2 + "元";
        }
        if (ShoppingCartK3.getInstance().getTicketK3().size() >= 1) {
            shuliang.setVisibility(View.VISIBLE);
            if (ShoppingCartK3.getInstance().getTicketK3().size() > 9) {
                shuliang.setText("9+");
            } else {
                shuliang.setText(ShoppingCartK3.getInstance().getTicketK3().size() + "");
            }
        } else {
            shuliang.setVisibility(View.GONE);
        }
        iiBottomGameChooseNotice.setText(notice);

        rthDxAdapter.notifyDataSetChanged();
        heZhiAdapter.notifyDataSetChanged();
        sanThDxAdapter.notifyDataSetChanged();
        sanBthAdapter.notifyDataSetChanged();
        rthFxAdapter.notifyDataSetChanged();
        if (playName.equals(ConstantValue.gameK3Type_DXZ)) {
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
        if (playName.equals(ConstantValue.gameK3Type_HZ)) {
            return hezhiNums.size();
        } else if (playName.equals(ConstantValue.gameK3Type_STHTX)) {
            return txuan.size();
        } else if (playName.equals(ConstantValue.gameK3Type_STHDX)) {
            return sthDxNums.size();
        } else if (playName.equals(ConstantValue.gameK3Type_SLHTX)) {
            return txuan.size();
        } else if (playName.equals(ConstantValue.gameK3Type_SBTH)) {
            return sBthNums.size() / 3;
        } else if (playName.equals(ConstantValue.gameK3Type_RTHFX)) {
            return sBthNums.size();
        } else if (playName.equals(ConstantValue.gameK3Type_RTHDX)) {
            return sBthNums.size() * k3Nums.size();
        } else if (playName.equals(ConstantValue.gameK3Type_RBTH)) {
            return sBthNums.size() / 2;
        } else {
            return redNumBai.size() * redNumShi.size() * redNumGe.size();
        }
    }

    /**
     * 机选区分
     *
     * @param random
     */
    private void setRandomK3(Random random) {
        if (playName.equals(ConstantValue.gameK3Type_HZ)) {
            hezhiNums.clear();
            int num = random.nextInt(13) + 4;
            hezhiNums.add(num);
            //界面更新
            heZhiAdapter.notifyDataSetChanged();
        } else if (playName.equals(ConstantValue.gameK3Type_STHDX)) {
            sthDxNums.clear();
            int num = random.nextInt(6) + 1;
            sthDxNums.add(num);
            sanThDxAdapter.notifyDataSetChanged();
        } else if (playName.equals(ConstantValue.gameK3Type_SBTH)) {
            sBthNums.clear();
            while (sBthNums.size() < 3) {
                int num = random.nextInt(6) + 1;
                if (sBthNums.contains(num)) {
                    continue;
                }
                sBthNums.add(num);
            }
            sanBthAdapter.notifyDataSetChanged();
        } else if (playName.equals(ConstantValue.gameK3Type_RTHFX)) {
            sBthNums.clear();
            int num = random.nextInt(6) + 1;
            sBthNums.add(num);
            rthFxAdapter.notifyDataSetChanged();
        } else if (playName.equals(ConstantValue.gameK3Type_RTHDX)) {
            k3Nums.clear();
            sBthNums.clear();
            int num = random.nextInt(6) + 1;
            k3Nums.add(num);
            while (sBthNums.size() < 1) {
                int numBth = random.nextInt(6) + 1;
                if (k3Nums.contains(numBth)) {
                    continue;
                }
                sBthNums.add(numBth);
            }
            rthFxAdapter.notifyDataSetChanged();
            sanBthAdapter.notifyDataSetChanged();
        } else if (playName.equals(ConstantValue.gameK3Type_RBTH)) {
            sBthNums.clear();
            while (sBthNums.size() < 2) {
                int num = random.nextInt(6) + 1;
                if (sBthNums.contains(num)) {
                    continue;
                }
                sBthNums.add(num);
            }
            sanBthAdapter.notifyDataSetChanged();
        } else if (playName.equals(ConstantValue.gameK3Type_DXZ)) {
            clear();
            int numBai = random.nextInt(6) + 1;
            redNumBai.add(numBai);
            int numShi = random.nextInt(6) + 1;
            redNumShi.add(numShi);
            int numGe = random.nextInt(6) + 1;
            redNumGe.add(numGe);
        }
        changeNotice();
    }

    /**
     * 随机1注
     */
    private void randomK3() {
        if (random == null) {
            random = new Random();
            setRandomK3(random);
        } else {
            setRandomK3(random);
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_play_k3;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        inti();
        intiListener();
        ViewBase.showNewbieGuide(this, iiBottomGameChooseAdd, iiBottomGameChooseOk, playGame, zhixuanRl);
    }

    private void intiListener() {

        //给容器监听,并添加到数组中
        hezhiContainer.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);
            // 判断当前点击的item是否被选中了
            if (!hezhiNums.contains(position + 4)) {
                //如果没有被选中 背景图片切换到红色
                view.setBackgroundResource(R.mipmap.ball_red);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.white));
                // 摇晃的动画
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
                hezhiNums.add(position + 4);
            } else {
                //被选中 还原背景图片
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.red2));
                view.setBackgroundResource(R.mipmap.ball_gray);
                hezhiNums.remove((Object) (position + 4));
            }
            changeNotice();
        });
        //三通号单选
        gvSanth.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);
            // 判断当前点击的item是否被选中了
            if (!sthDxNums.contains(position + 1)) {
                //如果没有被选中 背景图片切换到红色
                view.setBackgroundResource(R.mipmap.k3_r_bg_itme);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.white));
                // 摇晃的动画
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
                sthDxNums.add(position + 1);
            } else {
                //被选中 还原背景图片
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.red2));
                view.setBackgroundResource(R.mipmap.k3_h_gb_itme);
                sthDxNums.remove((Object) (position + 1));
            }
            changeNotice();
        });
        //三不同
        gvKsSanbth.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);
            // 判断当前点击的item是否被选中了
            if (!sBthNums.contains(position + 1)) {
                if (sBthNums.size() >= 3) {
                    ToastUtil.showShortToast(getString(R.string.is_k3sbt_default_prompt));
                    return;
                }
                //如果没有被选中 背景图片切换到红色
                view.setBackgroundResource(R.mipmap.k3_r_bg_itme);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.white));
                // 摇晃的动画
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
                sBthNums.add(position + 1);
            } else {
                // 被选中
                // 还原背景图片
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.red2));
                view.setBackgroundResource(R.mipmap.k3_h_gb_itme);
                sBthNums.remove((Object) (position + 1));
            }
            changeNotice();
        });
        //二同号复选
        gvRthfx.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);
            // 判断当前点击的item是否被选中了
            if (!sBthNums.contains(position + 1)) {
                //如果没有被选中 背景图片切换到红色
                view.setBackgroundResource(R.mipmap.k3_r_bg_itme);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.white));
                // 摇晃的动画
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
                sBthNums.add(position + 1);
            } else {
                //被选中 还原背景图片
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.red2));
                view.setBackgroundResource(R.mipmap.k3_h_gb_itme);
                sBthNums.remove((Object) (position + 1));
            }
            changeNotice();
        });
        //二同号单选(同号)
        gvKsTh.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);
            // 判断当前点击的item是否被选中了
            if (!k3Nums.contains(position + 1)) {
                k3Nums.clear();
                //如果没有被选中 背景图片切换到红色
                view.setBackgroundResource(R.mipmap.k3_r_bg_itme);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.white));
                // 摇晃的动画
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
                k3Nums.add(position + 1);
            } else {
                //被选中 还原背景图片
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.red2));
                view.setBackgroundResource(R.mipmap.k3_h_gb_itme);
                k3Nums.remove((Object) (position + 1));
            }
            if (sBthNums.contains(position + 1)) {
                sBthNums.remove((Object) (position + 1));
            }
            changeNotice();
        });
        //二同号单选(不同号)
        gvKsBth.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);
            // 判断当前点击的item是否被选中了
            if (!sBthNums.contains(position + 1)) {
                sBthNums.clear();
                //如果没有被选中 背景图片切换到红色
                view.setBackgroundResource(R.mipmap.k3_r_bg_itme);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.white));
                // 摇晃的动画
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
                sBthNums.add(position + 1);
            } else {
                //被选中 还原背景图片
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.red2));
                view.setBackgroundResource(R.mipmap.k3_h_gb_itme);
                sBthNums.remove((Object) (position + 1));
            }
            if (k3Nums.contains(position + 1)) {
                k3Nums.remove((Object) (position + 1));
            }
            changeNotice();
        });
        //二不同号
        gvKsErbth.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);
            // 判断当前点击的item是否被选中了
            if (!sBthNums.contains(position + 1)) {
                if (sBthNums.size() >= 2) {
                    ToastUtil.showShortToast("每注最多2个号码");
                    return;
                }
                //如果没有被选中 背景图片切换到红色
                view.setBackgroundResource(R.mipmap.k3_r_bg_itme);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.white));
                // 摇晃的动画
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
                sBthNums.add(position + 1);
            } else {
                //被选中 还原背景图片
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.red2));
                view.setBackgroundResource(R.mipmap.k3_h_gb_itme);
                sBthNums.remove((Object) (position + 1));
            }
            changeNotice();
        });
        //单选集合
        //给容器监听,并添加到数组中
        mK3Bai.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);

            // 判断当前点击的item是否被选中了
            if (!redNumBai.contains(position + 1)) {
                if (redNumBai.size() >= 1) {
                    redNumBai.clear();
                    redBaiAdapter.notifyDataSetChanged();
                }
                //如果没有被选中 背景图片切换到红色
                view.setBackgroundResource(R.mipmap.ball_red);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.white));
                // 摇晃的动画
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
                redNumBai.add(position + 1);
            } else {
                // 被选中
                // 还原背景图片
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.red2));
                view.setBackgroundResource(R.mipmap.ball_gray);
                redNumBai.remove((Object) (position + 1));
            }
            changeNotice();
        });
        //给容器监听,并添加到数组中
        mK3Shi.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);

            // 判断当前点击的item是否被选中了
            if (!redNumShi.contains(position + 1)) {
                if (redNumShi.size() >= 1) {
                    redNumShi.clear();
                    redShiAdapter.notifyDataSetChanged();
                }
                // 如果没有被选中
                // 背景图片切换到红色
                view.setBackgroundResource(R.mipmap.ball_red);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.white));
                // 摇晃的动画
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
                redNumShi.add(position + 1);
            } else {
                // 被选中
                // 还原背景图片
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.red2));
                view.setBackgroundResource(R.mipmap.ball_gray);
                redNumShi.remove((Object) (position + 1));
            }
            changeNotice();
        });
        //给容器监听,并添加到数组中
        mK3Ge.setOnActionUpListener((view, position) -> {
            TextView tv = view.findViewById(R.id.ball_tv);

            // 判断当前点击的item是否被选中了
            if (!redNumGe.contains(position + 1)) {
                if (redNumGe.size() >= 1) {
                    redNumGe.clear();
                    redGeAdapter.notifyDataSetChanged();
                }
                //如果没有被选中 背景图片切换到红色
                view.setBackgroundResource(R.mipmap.ball_red);
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.white));
                // 摇晃的动画
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .playOn(view);
                redNumGe.add(position + 1);
            } else {
                //被选中 还原背景图片
                tv.setTextColor(CommonUtils.getColor(view.getContext(), R.color.red2));
                view.setBackgroundResource(R.mipmap.ball_gray);
                redNumGe.remove((Object) (position + 1));
            }
            changeNotice();
        });
    }

    private void inti() {

        playName = ConstantValue.gameK3Type_DXZ;
        name.setText(playName);
        tempDan.setText(getString(R.string.is_k3_default_prompt));
        zhixuanRl.setVisibility(View.VISIBLE);

        mPop = new SelectK3PopupWindow(this, (view, string) -> {
            name.setText(string);
            playName = string;
            tvSanTHTX.setSelected(false);
            mRandomRed.setVisibility(View.VISIBLE);
            switch (string) {
                case ConstantValue.gameK3Type_HZ:
                    tempDan.setText(getString(R.string.is_k3hz_default_prompt));
                    hezhiContainer.setVisibility(View.VISIBLE);
                    rlSlhtxContainer.setVisibility(View.GONE);
                    rlRthdxContainer.setVisibility(View.GONE);
                    rlSthtxContainer.setVisibility(View.GONE);
                    rlSanthContainer.setVisibility(View.GONE);
                    rlSanbthContainer.setVisibility(View.GONE);
                    rlErbthContainer.setVisibility(View.GONE);
                    rlRthfxContainer.setVisibility(View.GONE);
                    zhixuanRl.setVisibility(View.GONE);
                    hiddenPop();
                    break;
                case ConstantValue.gameK3Type_STHTX:
                    rlSthtxContainer.setVisibility(View.VISIBLE);
                    rlSanbthContainer.setVisibility(View.GONE);
                    rlRthdxContainer.setVisibility(View.GONE);
                    rlErbthContainer.setVisibility(View.GONE);
                    hezhiContainer.setVisibility(View.GONE);
                    rlSanthContainer.setVisibility(View.GONE);
                    rlSlhtxContainer.setVisibility(View.GONE);
                    rlRthfxContainer.setVisibility(View.GONE);
                    zhixuanRl.setVisibility(View.GONE);
                    mRandomRed.setVisibility(View.INVISIBLE);
                    tempDan.setText(getString(R.string.is_k3sthtx_default_prompt));
                    hiddenPop();
                    break;
                case ConstantValue.gameK3Type_STHDX:
                    rlSanthContainer.setVisibility(View.VISIBLE);
                    rlSanbthContainer.setVisibility(View.GONE);
                    rlRthdxContainer.setVisibility(View.GONE);
                    rlSthtxContainer.setVisibility(View.GONE);
                    zhixuanRl.setVisibility(View.GONE);
                    rlErbthContainer.setVisibility(View.GONE);
                    hezhiContainer.setVisibility(View.GONE);
                    rlSlhtxContainer.setVisibility(View.GONE);
                    rlRthfxContainer.setVisibility(View.GONE);
                    tempDan.setText(getString(R.string.is_k3sthdx_default_prompt));
                    hiddenPop();
                    break;
                case ConstantValue.gameK3Type_SBTH:
                    rlSanbthContainer.setVisibility(View.VISIBLE);
                    rlSthtxContainer.setVisibility(View.GONE);
                    rlSanthContainer.setVisibility(View.GONE);
                    rlErbthContainer.setVisibility(View.GONE);
                    rlRthdxContainer.setVisibility(View.GONE);
                    zhixuanRl.setVisibility(View.GONE);
                    hezhiContainer.setVisibility(View.GONE);
                    rlSlhtxContainer.setVisibility(View.GONE);
                    rlRthfxContainer.setVisibility(View.GONE);
                    tempDan.setText(getString(R.string.is_k3sbtdx_default_prompt));
                    hiddenPop();
                    break;
                case ConstantValue.gameK3Type_SLHTX:
                    rlSlhtxContainer.setVisibility(View.VISIBLE);
                    rlSanbthContainer.setVisibility(View.GONE);
                    rlSanthContainer.setVisibility(View.GONE);
                    rlRthdxContainer.setVisibility(View.GONE);
                    rlErbthContainer.setVisibility(View.GONE);
                    zhixuanRl.setVisibility(View.GONE);
                    rlSthtxContainer.setVisibility(View.GONE);
                    hezhiContainer.setVisibility(View.GONE);
                    rlRthfxContainer.setVisibility(View.GONE);
                    mRandomRed.setVisibility(View.INVISIBLE);
                    tempDan.setText(getString(R.string.is_k3slhtx_default_prompt));
                    hiddenPop();
                    break;
                case ConstantValue.gameK3Type_RTHFX:
                    rlRthfxContainer.setVisibility(View.VISIBLE);
                    rlSlhtxContainer.setVisibility(View.GONE);
                    rlSanbthContainer.setVisibility(View.GONE);
                    rlSanthContainer.setVisibility(View.GONE);
                    rlRthdxContainer.setVisibility(View.GONE);
                    zhixuanRl.setVisibility(View.GONE);
                    rlErbthContainer.setVisibility(View.GONE);
                    rlSthtxContainer.setVisibility(View.GONE);
                    hezhiContainer.setVisibility(View.GONE);
                    tempDan.setText(getString(R.string.is_k3rthfx_default_prompt));
                    hiddenPop();
                    break;
                case ConstantValue.gameK3Type_RTHDX:
                    rlRthdxContainer.setVisibility(View.VISIBLE);
                    rlRthfxContainer.setVisibility(View.GONE);
                    zhixuanRl.setVisibility(View.GONE);
                    rlSlhtxContainer.setVisibility(View.GONE);
                    rlSanbthContainer.setVisibility(View.GONE);
                    rlErbthContainer.setVisibility(View.GONE);
                    rlSanthContainer.setVisibility(View.GONE);
                    rlSthtxContainer.setVisibility(View.GONE);
                    hezhiContainer.setVisibility(View.GONE);
                    tempDan.setText(getString(R.string.is_k3rth_default_prompt));
                    hiddenPop();
                    break;
                case ConstantValue.gameK3Type_RBTH:
                    rlErbthContainer.setVisibility(View.VISIBLE);
                    rlRthfxContainer.setVisibility(View.GONE);
                    rlSlhtxContainer.setVisibility(View.GONE);
                    rlSanbthContainer.setVisibility(View.GONE);
                    rlRthdxContainer.setVisibility(View.GONE);
                    rlSanthContainer.setVisibility(View.GONE);
                    rlSthtxContainer.setVisibility(View.GONE);
                    hezhiContainer.setVisibility(View.GONE);
                    zhixuanRl.setVisibility(View.GONE);
                    tempDan.setText(getString(R.string.is_k3rbt_default_prompt));
                    hiddenPop();
                    break;
                case ConstantValue.gameK3Type_DXZ:
                    zhixuanRl.setVisibility(View.VISIBLE);
                    rlErbthContainer.setVisibility(View.GONE);
                    rlRthfxContainer.setVisibility(View.GONE);
                    rlSlhtxContainer.setVisibility(View.GONE);
                    rlSanbthContainer.setVisibility(View.GONE);
                    rlRthdxContainer.setVisibility(View.GONE);
                    rlSanthContainer.setVisibility(View.GONE);
                    rlSthtxContainer.setVisibility(View.GONE);
                    hezhiContainer.setVisibility(View.GONE);
                    tempDan.setText(getString(R.string.is_k3_default_prompt));
                    hiddenPop();
                    break;
            }
        });

        hezhiNums = new ArrayList<>();
        sthDxNums = new ArrayList<>();
        sBthNums = new ArrayList<>();
        sthDxNums = new ArrayList<>();
        txuan = new ArrayList<>();
        k3Nums = new ArrayList<>();

        redNumBai = new ArrayList<Integer>();
        redNumShi = new ArrayList<Integer>();
        redNumGe = new ArrayList<Integer>();

        heZhiAdapter = new PoolK3HAdapter(this, 14, hezhiNums, R.mipmap.ball_red, R.color.red2);
        sanThDxAdapter = new PoolK3PAdapter(this, 6, sthDxNums, R.mipmap.k3_r_bg_itme, R.color.red2, 3);
        sanBthAdapter = new PoolK3PAdapter(this, 6, sBthNums, R.mipmap.k3_r_bg_itme, R.color.red2, 1);
        rthFxAdapter = new PoolK3PAdapter(this, 6, sBthNums, R.mipmap.k3_r_bg_itme, R.color.red2, 2);
        rthDxAdapter = new PoolK3PAdapter(this, 6, k3Nums, R.mipmap.k3_r_bg_itme, R.color.red2, 2);

        redBaiAdapter = new PoolK3DAdapter(this, 6, redNumBai, R.mipmap.ball_red, R.color.red2);
        redShiAdapter = new PoolK3DAdapter(this, 6, redNumShi, R.mipmap.ball_red, R.color.red2);
        redGeAdapter = new PoolK3DAdapter(this, 6, redNumGe, R.mipmap.ball_red, R.color.red2);


        hezhiContainer.setAdapter(heZhiAdapter);
        gvSanth.setAdapter(sanThDxAdapter);
        gvKsSanbth.setAdapter(sanBthAdapter);
        gvRthfx.setAdapter(rthFxAdapter);

        gvKsTh.setAdapter(rthDxAdapter);
        gvKsBth.setAdapter(sanBthAdapter);
        gvKsErbth.setAdapter(sanBthAdapter);

        mK3Bai.setAdapter(redBaiAdapter);
        mK3Shi.setAdapter(redShiAdapter);
        mK3Ge.setAdapter(redGeAdapter);

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    private void hiddenPop() {
        clear();
        if (mPop.isShowing()) {
            mPop.dismiss();
        }
        changeNotice();
    }

    @OnClick({R.id.fanhui, R.id.ii_ssq_random_red, R.id.ii_bottom_game_choose_clean,
            R.id.ii_bottom_game_choose_ok, R.id.ii_bottom_game_choose_add,
            R.id.tv_santh_tongxuan, R.id.tv_slhtx_tongxuan, R.id.play_game})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.ii_ssq_random_red: //机选选号
                randomK3();
                break;
            case R.id.ii_bottom_game_choose_clean: //清空
                clear();
                break;
            case R.id.ii_bottom_game_choose_ok:  //选好了
                startActivity(new Intent(this, ShoppingK3Activity.class));
                break;
            case R.id.ii_bottom_game_choose_add:  //添加到订单
                addToShiping();
                break;
            case R.id.play_game:  //选择购彩方式
                mPop.showAsDropDown(toolbar);
                break;
            case R.id.tv_santh_tongxuan:  //三同号通选
                if (tvSanTHTX.isSelected()) {
                    tvSanTHTX.setSelected(false);
                    txuan.clear();
                } else {
                    tvSanTHTX.setSelected(true);
                    txuan.add(ConstantValue.k3_540_sthtx_lottery_number);
                }
                changeNotice();
                break;
            case R.id.tv_slhtx_tongxuan:  //三连号通选
                if (tvSlhtxTongxuan.isSelected()) {
                    tvSlhtxTongxuan.setSelected(false);
                    txuan.clear();
                } else {
                    tvSlhtxTongxuan.setSelected(true);
                    txuan.add(ConstantValue.k3_550_slhtx_lottery_number);
                }
                changeNotice();
                break;
        }
    }

    /**
     * 清空数据
     */
    public void clear() {
        hezhiNums.clear();
        txuan.clear();
        sthDxNums.clear();
        sBthNums.clear();
        k3Nums.clear();

        redNumBai.clear();
        redNumShi.clear();
        redNumGe.clear();

        tvSanTHTX.setSelected(false);
        tvSlhtxTongxuan.setSelected(false);
        changeNotice();
    }

    /**
     * 添加到购物车
     */
    private void addToShiping() {
        // ①判断：用户是否选择了一注投注
        if (calc() >= 1) {
            //// 一个购物车中，只能放置一个彩种，当前期的投注信息
            // ②判断：是否获取到了当前销售期的信息
            if (calc() * 2 <= ConstantValue.UPPER_LIMIT) {//投注不能超过10000
                lotteryInfo();
                // ③清除现有UI展示数据
                clear();
                ToastUtil.showShortToast("已添加到购票车,祝您好运...");
            } else {
                // 重新获取期次信息
                ToastUtil.showShortToast("为了健康投注,单票不能超过" + ConstantValue.UPPER_LIMIT + "元");
            }
        } else {
            // 提示：需要选择一注
            ToastUtil.showShortToast(R.string.add_ok_msg);
        }
    }

    /**
     * 封装用户投注信息
     */
    private void lotteryInfo() {
        // ③封装用户的投注信息：红球、蓝球、注数
        TicketK3 ticketK3 = new TicketK3();
        ticketK3.setNum(calc());

        Collections.sort(sthDxNums); //排序
        Collections.sort(hezhiNums); //排序
        Collections.sort(sBthNums); //排序
        redNumBai.addAll(redNumShi);
        redNumBai.addAll(redNumGe);
        Collections.sort(redNumBai); //排序

        Logger.i("注数: " + calc());
        if (playName.equals(ConstantValue.gameK3Type_HZ)) { //和值
            stringBuffer.setLength(0);
            stringBufferJ.setLength(0);
            for (Integer item : hezhiNums) {
                stringBuffer.append(" ").append(decimalFormat.format(item));
                stringBufferJ.append(",").append(decimalFormat.format(item));
            }
            ticketK3.setHeZhiNum(stringBuffer.substring(1));
            ticketK3.setJsonNum(stringBufferJ.substring(1));
//            ticketK3.setNum(hezhiNums.size());
            ticketK3.setLotteryid(ConstantValue.K3_HE);
            // ④创建彩票购物车，将投注信息添加到购物车中
            ShoppingCartK3.getInstance().getTicketK3().add(ticketK3);
        } else if (playName.equals(ConstantValue.gameK3Type_STHTX)) { //三同号通选
            ticketK3.setHeZhiNum(ConstantValue.gameK3Type_STHTX);
            ticketK3.setJsonNum(txuan.get(0));
            ticketK3.setLotteryid(ConstantValue.K3_STH_TX);
            ShoppingCartK3.getInstance().getTicketK3().add(ticketK3);
        } else if (playName.equals(ConstantValue.gameK3Type_STHDX)) {//三同号单选
            for (Integer item : sthDxNums) {
                TicketK3 _ticketK3 = new TicketK3();
                stringBuffer.setLength(0);
                stringBufferJ.setLength(0);
                stringBuffer.append(decimalFormat.format(item)).append(" ")
                        .append(decimalFormat.format(item)).append(" ")
                        .append(decimalFormat.format(item));
                stringBufferJ.append(decimalFormat.format(item)).append(",")
                        .append(decimalFormat.format(item)).append(",")
                        .append(decimalFormat.format(item));
                _ticketK3.setNum(1);
                _ticketK3.setHeZhiNum(stringBuffer.toString());
                _ticketK3.setJsonNum(stringBufferJ.toString());
                _ticketK3.setLotteryid(ConstantValue.K3_STH_DX);
                ShoppingCartK3.getInstance().getTicketK3().add(_ticketK3);
            }
        } else if (playName.equals(ConstantValue.gameK3Type_SBTH)) {//三不同号
            stringBuffer.setLength(0);
            stringBufferJ.setLength(0);
            for (Integer item : sBthNums) {
                stringBuffer.append(" ").append(decimalFormat.format(item));
                stringBufferJ.append(",").append(decimalFormat.format(item));
            }
            ticketK3.setHeZhiNum(stringBuffer.substring(1));
            ticketK3.setJsonNum(stringBufferJ.substring(1));
            ticketK3.setLotteryid(ConstantValue.K3_SBT);
            ShoppingCartK3.getInstance().getTicketK3().add(ticketK3);
        } else if (playName.equals(ConstantValue.gameK3Type_SLHTX)) {//三连号通选
            ticketK3.setHeZhiNum(ConstantValue.gameK3Type_SLHTX);
            ticketK3.setJsonNum(txuan.get(0));
//            ticketK3.setNum(txuan.size());
            ticketK3.setLotteryid(ConstantValue.K3_SLH_TX);
            ShoppingCartK3.getInstance().getTicketK3().add(ticketK3);
        } else if (playName.equals(ConstantValue.gameK3Type_RTHFX)) {//二同号复选
            stringBuffer.setLength(0);
            stringBufferJ.setLength(0);
            for (Integer item : sBthNums) {
                stringBuffer.append(" ").append(decimalFormat.format(item))
                        .append(",")
                        .append(decimalFormat.format(item));
                stringBufferJ.append(",").append(item).append(item);
            }
            ticketK3.setHeZhiNum(stringBuffer.substring(1));
            ticketK3.setJsonNum(stringBufferJ.substring(1));

            ticketK3.setLotteryid(ConstantValue.K3_RTH_FX);
            ShoppingCartK3.getInstance().getTicketK3().add(ticketK3);
        } else if (playName.equals(ConstantValue.gameK3Type_RTHDX)) {//二同号单选
            sBthNums.add(k3Nums.get(0));
            sBthNums.add(k3Nums.get(0));
            Collections.sort(sBthNums); //排序
            stringBuffer.setLength(0);
            stringBufferJ.setLength(0);
            for (Integer item : sBthNums) {
                stringBuffer.append(" ").append(decimalFormat.format(item));
                stringBufferJ.append(",").append(decimalFormat.format(item));
            }
            ticketK3.setHeZhiNum(stringBuffer.substring(1));
            ticketK3.setJsonNum(stringBufferJ.substring(1));
            ticketK3.setLotteryid(ConstantValue.K3_RTH_DX);
            ShoppingCartK3.getInstance().getTicketK3().add(ticketK3);
            Logger.i(ticketK3.toString());
        } else if (playName.equals(ConstantValue.gameK3Type_RBTH)) {//二不同号
            stringBuffer.setLength(0);
            stringBufferJ.setLength(0);
            stringBuffer.append(decimalFormat.format(sBthNums.get(0)))
                    .append(",")
                    .append(decimalFormat.format(sBthNums.get(1)));
            stringBufferJ.append(sBthNums.get(0)).append(sBthNums.get(1));
            ticketK3.setHeZhiNum(stringBuffer.toString());
            ticketK3.setJsonNum(stringBufferJ.toString());
            ticketK3.setLotteryid(ConstantValue.K3_RBT);
            ShoppingCartK3.getInstance().getTicketK3().add(ticketK3);
            Logger.i(ticketK3.toString());
        } else {
            stringBuffer.setLength(0);
            stringBufferJ.setLength(0);
            for (Integer item : redNumBai) {
                stringBuffer.append(" ").append(decimalFormat.format(item));
                stringBufferJ.append(",").append(decimalFormat.format(item));
            }
            ticketK3.setHeZhiNum(stringBuffer.substring(1));
            ticketK3.setJsonNum(stringBufferJ.substring(1));
            ticketK3.setLotteryid(Utils.Game_Type(stringBuffer.substring(1).split(" ")));
            ShoppingCartK3.getInstance().getTicketK3().add(ticketK3);
        }
        // ⑤设置彩种的标示，设置彩种期次
//                ShoppingCartSSQ.getInstance().setIssue(bundle.getString("issue"));
        ShoppingCartK3.getInstance().setLotteryid(ConstantValue.K3);
    }
}
