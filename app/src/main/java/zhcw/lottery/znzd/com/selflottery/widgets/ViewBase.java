package zhcw.lottery.znzd.com.selflottery.widgets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.InputFilter;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.model.HighLight;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.bingoogolapple.qrcode.zxing.ZXingView;
import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.ui.LoginActivity;
import zhcw.lottery.znzd.com.selflottery.ui.my.activity.CertificationActivity;
import zhcw.lottery.znzd.com.selflottery.util.Logger;
import zhcw.lottery.znzd.com.selflottery.util.SocketUtil;
import zhcw.lottery.znzd.com.selflottery.util.SpannableStringUtils;
import zhcw.lottery.znzd.com.selflottery.util.ToastUtil;
import zhcw.lottery.znzd.com.selflottery.widgets.DialogTask.BottomDialog;
import zhcw.lottery.znzd.com.selflottery.widgets.DialogTask.CertificationDialog;
import zhcw.lottery.znzd.com.selflottery.widgets.DialogTask.CustomDialog;
import zhcw.lottery.znzd.com.selflottery.widgets.pswkeyboard.EditInputFilter;
import zhcw.lottery.znzd.com.selflottery.widgets.pswkeyboard.widget.VirtualKeyboardBeiView;

/**
 * 作者：XPZ on 2018/3/30 11:07.
 */
public class ViewBase {

    /**
     * 自定义按键的初始化
     *
     * @param num                  倍数限制
     * @param mVirtualKeyboardView 自定义键盘对象
     * @param appnumbersInfo       需要展示倍数的控件
     * @param onListener           用于关闭自定义按键的回调
     */
    public static void initKeyboardView(int num, VirtualKeyboardBeiView mVirtualKeyboardView,
                                        TextView appnumbersInfo, View.OnClickListener onListener) {

        ArrayList<Map<String, String>> valueList = mVirtualKeyboardView.getValueList();
        GridView gridView = mVirtualKeyboardView.getGridView();
        TextView beiMsg = mVirtualKeyboardView.getBeiMsg();
        gridView.setOnItemClickListener((adapterView, view, position, l) -> {

            if (position < 14 && position != 12) {    //点击0~9按钮
                String amount = appnumbersInfo.getText().toString().trim();
                amount = amount + valueList.get(position).get("name");
                if (!amount.isEmpty()) {
                    int integer = Integer.parseInt(amount);
                    if (integer <= num) {
                        if (integer == 0) {
                            ToastUtil.showShortToast("投注倍数最少1倍");
                            appnumbersInfo.setText("1");
                            beiMsg.setText("1");
                        } else {
                            appnumbersInfo.setText(amount);
                            beiMsg.setText(amount);
                        }
                    } else {
                        ToastUtil.showShortToast(String.format("投注倍数最多%s倍", num));
                        appnumbersInfo.setText(String.valueOf(num));
                        beiMsg.setText(String.valueOf(num));
                    }
                }
            } else {
                if (position == 14) {      //点击退格键
                    String amount = appnumbersInfo.getText().toString().trim();
                    if (amount.length() > 0) {
                        amount = amount.substring(0, amount.length() - 1);
                        appnumbersInfo.setText(amount);
                        beiMsg.setText(amount);
                    }
                }
            }
        });
        mVirtualKeyboardView.getLayoutBack().setOnClickListener(onListener);
    }

    private static SocketUtil.ExampleAsyncTask exampleAsyncTask = null;

    /**
     * Show message
     *
     * @param activity Activity
     * @param msg      message
     */
    public static void showSnackMessage(Activity activity, String msg) {
        Logger.i("showSnackMessage ：" + msg);
        //去掉虚拟按键
        activity.getWindow().getDecorView().setSystemUiVisibility(
                //隐藏虚拟按键栏 | 防止点击屏幕时,隐藏虚拟按键栏又弹了出来
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE
        );
        final Snackbar snackbar = Snackbar.make(activity.getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(ContextCompat.getColor(activity, R.color.white));
        snackbar.setAction("知道了", v -> {
            snackbar.dismiss();
            //隐藏SnackBar时记得恢复隐藏虚拟按键栏,不然屏幕底部会多出一块空白布局出来,很难看
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }).show();
        snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        });
    }


    /**
     * 引导蒙层
     *
     * @param activity
     * @param v1
     * @param v2
     */
    public static void showNewbieGuide(Activity activity, View v1, View v2, View v3, View v4) {

        Animation enterAnimation = new AlphaAnimation(0f, 1f);
        enterAnimation.setDuration(400);
        enterAnimation.setFillAfter(true);

        Animation exitAnimation = new AlphaAnimation(1f, 0f);
        exitAnimation.setDuration(500);
        exitAnimation.setFillAfter(true);

        NewbieGuide.with(activity)
                .setLabel("guide")
                .alwaysShow(false)//重复显示
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(v3, HighLight.Shape.ROUND_RECTANGLE, 20)//高亮区域
                        .setLayoutRes(R.layout.view_guide_1, R.id.bt)
                        .setEverywhereCancelable(false)//是否点击任意地方跳转下一页或者消失引导层，默认true
                        .setEnterAnimation(enterAnimation)//进入动画
                        .setExitAnimation(exitAnimation)//退出动画
                ).addGuidePage(GuidePage.newInstance()
                .addHighLight(v4, HighLight.Shape.ROUND_RECTANGLE, 20)//高亮区域
                .setLayoutRes(R.layout.view_guide_2, R.id.bt)//引导页布局，点击跳转下一页或者消失引导层的控件id
                .setEverywhereCancelable(false)//是否点击任意地方跳转下一页或者消失引导层，默认true
                .setEnterAnimation(enterAnimation)//进入动画
                .setExitAnimation(exitAnimation))//退出动画
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(v1, HighLight.Shape.ROUND_RECTANGLE, 20)//高亮区域
                        .setLayoutRes(R.layout.view_guide_3, R.id.bt)//引导页布局，点击跳转下一页或者消失引导层的控件id
                        .setEverywhereCancelable(false)//是否点击任意地方跳转下一页或者消失引导层，默认true
                        .setEnterAnimation(enterAnimation)//进入动画
                        .setExitAnimation(exitAnimation))//退出动画
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(v2, HighLight.Shape.ROUND_RECTANGLE, 20)//高亮区域
                        .setLayoutRes(R.layout.view_guide_4, R.id.bt)//引导页布局，点击跳转下一页或者消失引导层的控件id
                        .setEverywhereCancelable(false)//是否点击任意地方跳转下一页或者消失引导层，默认true
                        .setEnterAnimation(enterAnimation)//进入动画
                        .setExitAnimation(exitAnimation))//退出动画
                .show();
    }

    public static void CommonDialog(@NotNull final Context context,
                                    final String msg, final OnWeAddDialogClickListener onListener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context)
                .cancelTouchout(false)
                .view(R.layout.fanhui_prompt)
                .heightDimenRes(R.dimen.dialog_fanhui_height)
                .widthDimenRes(R.dimen.dialog_fanhui_width)
                .style(R.style.FanhuiDialog);
        final CustomDialog dialog = builder.build();

        Button mDetermine = dialog.getView().findViewById(R.id.fanhui_determine);
        Button mCancel = dialog.getView().findViewById(R.id.fanhui_cancel);
        TextView msG = dialog.getView().findViewById(R.id.tv_msg);
        msG.setText(msg);

        mDetermine.setOnClickListener(v -> onListener.onClick(v, dialog));
        mCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }


    public static void tuiguangDialog(@NotNull final Context context) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context)
                .cancelTouchout(false)
                .view(R.layout.tui_prompt)
                .heightpx(1000)
                .widthpx(800)
                .style(R.style.FanhuiDialog);
        final CustomDialog dialog = builder.build();
        Button mCancel = dialog.getView().findViewById(R.id.guanbi);
        mCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    public static void fanhuiDialog(@NotNull final Context context, final String Lotteryid) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context)
                .cancelTouchout(false)
                .view(R.layout.fanhui_prompt)
                .heightDimenRes(R.dimen.dialog_fanhui_height)
                .widthDimenRes(R.dimen.dialog_fanhui_width)
                .style(R.style.FanhuiDialog);
        final CustomDialog dialog = builder.build();

        Button mDetermine = dialog.getView().findViewById(R.id.fanhui_determine);
        Button mCancel = dialog.getView().findViewById(R.id.fanhui_cancel);
        TextView msg = dialog.getView().findViewById(R.id.tv_msg);

        mDetermine.setOnClickListener(v -> {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            ((Activity) context).finish();
        });
        mCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    /**
     * 二维码弹窗
     *
     * @param bitmap
     * @param money
     * @param number
     */
    public static void qrCodeDialog(Bitmap bitmap, Integer money, Integer number,
                                    final UpdateShowFace face, final Context context) {
        Logger.i("money: " + money + "- number :" + number);
        exampleAsyncTask = new SocketUtil.ExampleAsyncTask();
        exampleAsyncTask.execute();

        CustomDialog.Builder builder = new CustomDialog.Builder(context)
                .cancelTouchout(false)
                .view(R.layout.qrcode_img)
                .heightDimenRes(R.dimen.dialog_loginerror_height)
                .widthDimenRes(R.dimen.dialog_loginerror_width)
                .style(R.style.Dialog);

        final CustomDialog dialog = builder.build();
        ImageView imageView = dialog.getView().findViewById(R.id.qrcode_img);
        ImageView tvView = dialog.getView().findViewById(R.id.fanhui_detail_dialog);
        final TextView title = dialog.getView().findViewById(R.id.text);
        final TextView lottery_msg = dialog.getView().findViewById(R.id.tv_msg);
        imageView.setImageBitmap(bitmap);
        dialog.show();

        String moneyStr = context.getResources().getString(R.string.is_qrcode_dialog_msg);
        moneyStr = StringUtils.replaceEach(moneyStr, new String[]{"MONEY1", "MONEY2"},
                new String[]{number + "", money.toString()});
        lottery_msg.setText(Html.fromHtml(moneyStr));

        exampleAsyncTask.setListener(value -> {
            if ("CLOSE".equals(value)) {
                face.handUpdateShow();
            } else {
                title.setText(value);
            }
        });
        SocketUtil.SendToken(title.getContext());
        tvView.setOnClickListener(v -> {
            if (dialog.isShowing()) {
                try {
                    if (SocketUtil.clientSocket != null) {
                        SocketUtil.clientSocket.sendUrgentData(0xFF);//心跳包
                        SocketUtil.clientSocket.close();
                    }//Logger.i("仍然连接1");
                } catch (IOException e) {//Logger.i("连接断开1");
                    e.printStackTrace();
                }
                if (exampleAsyncTask != null && exampleAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
                    exampleAsyncTask.cancel(true);
                    exampleAsyncTask = null;
                }
                try {
                    if (SocketUtil.clientSocket != null) {
                        SocketUtil.clientSocket.sendUrgentData(0xFF);//心跳包
                        SocketUtil.clientSocket.close();
                    } //Logger.i("仍然连接2");
                } catch (IOException e) {//Logger.i("连接断开2");
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });

    }

    /**
     * 订单信息的二维码弹窗
     *
     * @param bitmap
     */
    public static void DetailCodeDialog(String title, Bitmap bitmap, Context context) {
        exampleAsyncTask = new SocketUtil.ExampleAsyncTask();
        exampleAsyncTask.execute();

        CustomDialog.Builder builder = new CustomDialog.Builder(context)
                .cancelTouchout(false)
                .view(R.layout.detail_qrcode_img)
                .heightDimenRes(R.dimen.dialog_detail_height)
                .widthDimenRes(R.dimen.dialog_detail_width)
                .style(R.style.DetailDialog);

        final CustomDialog dialog = builder.build();
        ImageView imageView = dialog.getView().findViewById(R.id.qrcode_img);
        final TextView text = dialog.getView().findViewById(R.id.text);
        TextView mFanhuiDetailDialog = dialog.getView().findViewById(R.id.fanhui_detail_dialog);
        imageView.setImageBitmap(bitmap);
        text.setText(title);
        dialog.show();

        exampleAsyncTask.setListener(value -> {
            if ("CLOSE".equals(value)) {
            } else {
                text.setText(value);
            }
        });
        SocketUtil.SendToken(text.getContext());
        mFanhuiDetailDialog.setOnClickListener(v -> {
            if (dialog.isShowing()) {
                try {
                    if (SocketUtil.clientSocket != null) {
                        SocketUtil.clientSocket.sendUrgentData(0xFF);//心跳包
                        SocketUtil.clientSocket.close();
                    }
                    Logger.i("仍然连接1");
                } catch (IOException e) {
                    Logger.i("连接断开1");
                    e.printStackTrace();
                }

                if (exampleAsyncTask != null && exampleAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
                    Logger.i("exampleAsyncTask");
                    exampleAsyncTask.cancel(true);
                    exampleAsyncTask = null;
                }

                try {
                    if (SocketUtil.clientSocket != null) {
                        SocketUtil.clientSocket.sendUrgentData(0xFF);//心跳包
                        SocketUtil.clientSocket.close();
                    }
                    Logger.i("仍然连接2");
                } catch (IOException e) {
                    Logger.i("连接断开2");
                    e.printStackTrace();
                }

                dialog.dismiss();
            }
        });

    }

    /**
     * 兑奖非中奖的Dialog
     *
     * @param string     显示的数据
     * @param mZXingView
     */
    public static CustomDialog BonusDialog(String string, Context context, final ZXingView mZXingView) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context)
                .cancelTouchout(false)
                .view(R.layout.bonusl_str_dialog)
                .heightDimenRes(R.dimen.dialog_bonus_height)
                .widthDimenRes(R.dimen.dialog_bonus_width)
                .style(R.style.AlertDialogStyle);

        final CustomDialog dialog = builder.build();
        TextView mTV = dialog.getView().findViewById(R.id.title_msg);
        TextView mTvConfirm = dialog.getView().findViewById(R.id.tv_confirm);
        dialog.setCancelable(false);
        mTV.setText(string);
        dialog.show();
        mTvConfirm.setOnClickListener(v -> {
            if (dialog.isShowing()) {
                if (mZXingView != null) {
                    mZXingView.startCamera();
                    mZXingView.startSpot(); // 延迟0.5秒后开始识别
                    mZXingView.showScanRect(); // 显示扫描框
                }
                dialog.dismiss();
            }
        });
        return dialog;
    }

    /**
     * 兑奖中小奖的Dialog
     *
     * @param string     显示的数据
     * @param mZXingView
     */
    public static CustomDialog BonusSmallDialog(String string, Context context, final ZXingView mZXingView) {
        ;
        CustomDialog.Builder builder = new CustomDialog.Builder(context)
                .cancelTouchout(false)
                .view(R.layout.bonusl_small_prize_dialog)
                .heightDimenRes(R.dimen.dialog_bonus_small_height)
                .widthDimenRes(R.dimen.dialog_bonus_small_width)
                .style(R.style.AlertDialogStyle);
        final CustomDialog dialog = builder.build();
        dialog.setCancelable(false);
        TextView mTV = dialog.getView().findViewById(R.id.tv_bomb_msg);
        ImageView mImBonus = dialog.getView().findViewById(R.id.bottom_im_bonus);

        //旋转动画
        final ImageView rotate = dialog.getView().findViewById(R.id.guang_rotate);
        Animation operatingAnim = AnimationUtils.loadAnimation(context, R.anim.rotate);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        rotate.startAnimation(operatingAnim);

        mTV.setText(string);
        dialog.show();
        mImBonus.setOnClickListener(v -> {
            if (dialog.isShowing()) {
                if (mZXingView != null) {
                    mZXingView.startCamera();
                    mZXingView.startSpot(); // 延迟0.5秒后开始识别
                    mZXingView.showScanRect(); // 显示扫描框
                }
//                    rotate.clearAnimation();
                dialog.dismiss();
            }
        });
        return dialog;
    }

    /**
     * 兑奖中大奖的Dialog(需要到福彩中心兑奖)
     *
     * @param string     显示的数据
     * @param mZXingView
     */
    public static CustomDialog BonusBombDialog(String string, Context context, final ZXingView mZXingView) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context)
                .cancelTouchout(false)
                .view(R.layout.bonusl_bomb_prize_dialog)
                .heightDimenRes(R.dimen.dialog_bonus_bomb_height)
                .widthDimenRes(R.dimen.dialog_bonus_bomb_width)
                .style(R.style.AlertDialogStyle);

        final CustomDialog dialog = builder.build();
        dialog.setCancelable(false);
        TextView mTV = dialog.getView().findViewById(R.id.tv_bomb_msg);
        TextView mMsg = dialog.getView().findViewById(R.id.txt_msg);
        ImageView mImConfirm = dialog.getView().findViewById(R.id.bottom_bt_bonus);

        //旋转动画
        final ImageView rotate = dialog.getView().findViewById(R.id.guang_rotate);
        Animation operatingAnim = AnimationUtils.loadAnimation(context, R.anim.rotate);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        rotate.startAnimation(operatingAnim);

        mTV.setText(string);
        mMsg.setText(SpannableStringUtils.getBuilder("请在规定时间内到相应的福彩中心兑奖")
                .append("\n否则视为弃奖").setForegroundColor(Color.RED).create());
        dialog.show();
        mImConfirm.setOnClickListener(v -> {
            if (dialog.isShowing()) {
                if (mZXingView != null) {
                    mZXingView.startCamera();
                    mZXingView.startSpot(); // 延迟0.5秒后开始识别
                    mZXingView.showScanRect(); // 显示扫描框
                }
                dialog.dismiss();
            }
        });
        return dialog;
    }

    /**
     * 微信提现的Dialog
     */
    public static void WeAddPreOrderDialog(Context context, final String many,
                                           final OnWeAddDialogClickListener listenter) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context)
                .cancelTouchout(true)
                .view(R.layout.weadd_preorder_dialog)
                .heightDimenRes(R.dimen.dialog_wexin_add_height)
                .widthDimenRes(R.dimen.dialog_wexin_add_width)
                .style(R.style.alert_dialog);
        final CustomDialog dialog = builder.build();
        dialog.setCancelable(false);

        TextView mTitle = dialog.getView().findViewById(R.id.title_msg_many);
        Button mBt_Cancel = dialog.getView().findViewById(R.id.cancel_button);
        Button mBt_confirm = dialog.getView().findViewById(R.id.confirm_button);
        TextView mTvAll = dialog.getView().findViewById(R.id.tv_all);
        final ClearEditText editMany = dialog.getView().findViewById(R.id.et_add_many);
        InputFilter[] filters = {new EditInputFilter(many)};
        editMany.setFilters(filters);

        editMany.setFocusable(true);
        editMany.setFocusableInTouchMode(true);
        editMany.requestFocus();
        //延时吊起键盘(由于 页面没能及时构建 所以延时等待)
        final Timer[] timer = {new Timer()};
        final TimerTask timerTask = new TimerTask() {
            public void run() {
                InputMethodManager inputManager =
                        (InputMethodManager) editMany.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editMany, 0);
            }
        };
        timer[0].schedule(timerTask, 400);

        mTitle.setText(SpannableStringUtils.getBuilder("可提现金额:")
                .append(many).setForegroundColor(Color.RED).create().append("元"));
        mBt_Cancel.setOnClickListener(view -> {
            if (dialog.isShowing()) {
                dialog.dismiss();
                timerTask.cancel();
                timer[0] = null;
            }
        });
        mBt_confirm.setOnClickListener(view -> listenter.onClick(view, dialog));
        mTvAll.setOnClickListener(view -> {
            editMany.setText(many);
            editMany.setSelection(String.valueOf(many).length());
        });

        dialog.show();
    }

    /**
     * 以字符串 生成二维码的Bitmap
     *
     * @param str
     * @return 返回二维码图片
     */
    public static Bitmap encodeAsBitmap(String str) {
        Logger.i("xpz_要生成二维码的字符串: ", str);
        Bitmap bitmap = null;
        BitMatrix result = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, 200, 200);
            // 使用 ZXing Android Embedded 要写的代码
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(result);
//             bitmap = QRCodeEncoder.syncEncodeQRCode(str,
//                    200, Color.BLACK, Color.WHITE, null);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException iae) { // ?
            return null;
        }
        return bitmap;
    }

    /**
     * 显示支付弹窗
     *
     * @param context
     * @param smg
     * @param alertType
     */
    public static void showPayDialog(@NotNull final Context context, String smg, int alertType) {

        BottomDialog bottomDialog = new BottomDialog(context, R.style.BottomDialog)
                .setMsgText(smg)
                .changeAlertType(alertType, true)
                .setCancelClickListener((view, dialog) -> {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                        ((Activity) context).finish();
                    }
                });

        bottomDialog.show();
        bottomDialog.setCancelable(false);
    }

    /**
     * 提示登录的
     *
     * @param context
     * @param type    信息状态
     */
    public static void showISLogin(@NotNull final Context context, int type) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context)
                .cancelTouchout(true)
                .view(R.layout.dialog_islogin)
                .heightpx(350)
                .widthpx(850)
                .style(R.style.SuccessDialog);
        CustomDialog dialog = builder.build();
        TextView mTitle = dialog.getView().findViewById(R.id.tv_msg_login);
        dialog.getView().findViewById(R.id.tv_islogn_cancel)
                .setOnClickListener(v -> dialog.dismiss());
        dialog.getView().findViewById(R.id.tv_islogn_ok)
                .setOnClickListener(v -> {
                    context.startActivity(new Intent(context, LoginActivity.class));
                    dialog.dismiss();
                });
        if (type == 1) {
            mTitle.setText("您尚未登录请登录");
        } else {
            mTitle.setText("登录状态发生改变,请重新登录!");
        }
        dialog.show();
    }

    /**
     * 默认的Dialog
     *
     * @param context
     * @param str
     */
    public static void showDefault(@NotNull final Context context, String str,
                                   final OnWeAddDialogClickListener onListener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context)
                .cancelTouchout(true)
                .view(R.layout.dialog_islogin)
                .heightpx(350)
                .widthpx(850)
                .style(R.style.SuccessDialog);
        CustomDialog dialog = builder.build();
        TextView mTitle = dialog.getView().findViewById(R.id.tv_msg_login);
        dialog.getView().findViewById(R.id.tv_islogn_cancel)
                .setOnClickListener(v -> dialog.dismiss());
        dialog.getView().findViewById(R.id.tv_islogn_ok)
                .setOnClickListener(v -> onListener.onClick(v, dialog));
        mTitle.setText(str);
        dialog.show();
    }


    /**
     * 京东E卡实名认证
     *
     * @param context
     */
    public static void setJdCardCertificationDialog(@NotNull final Context context) {
        CertificationDialog dialog;
        dialog = new CertificationDialog(context, R.style.SuccessDialog, (view, dialog1) -> {
            switch (view.getId()) {
                case R.id.tv_jdcard_certification_next:
                    context.startActivity(new Intent(context, CertificationActivity.class));
                    dialog1.dismiss();
                    break;
                case R.id.iv_jdcard_certification_dialog_close:
                    dialog1.dismiss();
                    break;
            }
        });
        dialog.show();
        dialog.setCancelable(true);
    }

    /**
     * 更新
     *
     * @param context
     * @param url
     */
    public static void showMessageDialog(@NotNull final Context context, final String url) {
        MessageDialog dialog = new MessageDialog(context, R.style.SuccessDialog, (view, dialog1) -> {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
            dialog1.dismiss();
        });
        dialog.show();
        dialog.getTitle().setText("更新提示");
        dialog.getContent().setText("请务必更新不然可能影响正常运行");
        dialog.setCancelable(false);
    }

    public interface OnWeAddDialogClickListener {
        void onClick(View view, CustomDialog dialog);
    }

    public interface UpdateShowFace {
        public void handUpdateShow();
    }
}
