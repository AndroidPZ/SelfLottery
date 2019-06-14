package zhcw.lottery.znzd.com.selflottery.ui.bonus;


import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.base.BaseFragment;
import zhcw.lottery.znzd.com.selflottery.config.Config;
import zhcw.lottery.znzd.com.selflottery.contact.BonusFgmContact;
import zhcw.lottery.znzd.com.selflottery.presenter.BonusFgmPresenter;
import zhcw.lottery.znzd.com.selflottery.ready_entity.bean_info.UserInfo;
import zhcw.lottery.znzd.com.selflottery.ui.MainAdminActivity;
import zhcw.lottery.znzd.com.selflottery.ui.bonus.entity.Bonus_Info;
import zhcw.lottery.znzd.com.selflottery.util.Debugger;
import zhcw.lottery.znzd.com.selflottery.util.Logger;
import zhcw.lottery.znzd.com.selflottery.util.Utils;
import zhcw.lottery.znzd.com.selflottery.widgets.DialogTask.CustomDialog;
import zhcw.lottery.znzd.com.selflottery.widgets.ViewBase;
import zhcw.lottery.znzd.com.selflottery.widgets.imagepicker.MyImageGridActivity;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by xpz on 2018/9/6.
 * 兑奖
 */
public class BonusFragment extends BaseFragment implements QRCodeView.Delegate,
        EasyPermissions.PermissionCallbacks, BonusFgmContact.IBonusFgmView {

    @BindView(R.id.zxingview)
    ZXingView mZXingView;
    @BindView(R.id.flash)
    ImageView flash;
    @BindView(R.id.Album)
    ImageView Album;
    private CustomDialog customDialog;
    private boolean isOpen = false;
    private BonusFgmPresenter bonusFgmPresenter;
    private ArrayList<ImageItem> selImageList = new ArrayList<>(); //当前选择的所有图片

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_bonus;
    }

    @Override
    protected void init() {
        bonusFgmPresenter = new BonusFgmPresenter(getActivity(), this);
        Utils.initCertificationImagePicker(Config.maxImgCount, false);
        BGAQRCodeUtil.setDebug(true);
        mZXingView.setDelegate(this);
        mZXingView.showScanRect(); // 显示扫描框
        mZXingView.setType(BarcodeType.CUSTOM, Utils.getCodeType()); // 自定义识别的类型
        methodRequiresTwoPermission();
    }

    /**
     * 震动
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        if (isLoginAndStartLogin(MainAdminActivity.class)) {
            bonusFgmPresenter.getBonusRechargeRequest(UserInfo.getToken(), result);
            mZXingView.stopSpot(); // 停止识别
            mZXingView.stopCamera();
            mZXingView.hiddenScanRect(); // 隐藏扫描框
        }
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Logger.i("XPZ", "打开相机出错");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (mZXingView != null) {
                mZXingView.stopSpot(); // 关闭摄像头预览
                mZXingView.stopCamera();
            }
        } else {
            if (mZXingView != null) {
                mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
                mZXingView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
            }
            methodRequiresTwoPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean b = !BonusFragment.this.isHidden();
        if (mZXingView != null && !BonusFragment.this.isHidden()) {
            if (customDialog != null && customDialog.isShowing()) {
            } else {
                mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
                mZXingView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mZXingView != null) {
            mZXingView.onDestroy(); // 销毁二维码扫描控件
        }
        super.onDestroy();
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (requestCode == Config.RP_CAMERA && !BonusFragment.this.isHidden()) {
            mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
            mZXingView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
            mZXingView.setType(BarcodeType.CUSTOM, Utils.getCodeType()); // 识别所有类型的码
            mZXingView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle("提示")
                    .setRationale("为了您的正常使用，请开启相应权限!")
                    .setPositiveButton("去设置")
                    .setNegativeButton("取消")
                    .setRequestCode(Config.RP_CAMERA)
                    .build()
                    .show();
        }
    }

    @AfterPermissionGranted(Config.RP_CAMERA)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(mContext, perms) && !BonusFragment.this.isHidden()) {
            Logger.i("XPZ", "请求权限");
            mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
            mZXingView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
            mZXingView.setType(BarcodeType.CUSTOM, Utils.getCodeType()); // 识别所有类型的码
//            mZXingView.getScanBoxView().setOnlyDecodeScanBoxArea(true); // 仅识别扫描框中的码
            mZXingView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
        } else {
            EasyPermissions.requestPermissions(this, "兑奖需要相机、闪光灯、读取权限!",
                    Config.RP_CAMERA, perms);
        }
    }

    @OnClick({R.id.flash, R.id.Album})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.flash://闪光灯
                if (isOpen) {
                    mZXingView.closeFlashlight(); // 关闭闪光灯
                    isOpen = false;
                    flash.setBackground(getResources().getDrawable(R.mipmap.flash_off));
                } else {
                    mZXingView.openFlashlight(); // 打开闪光灯
                    flash.setBackground(getResources().getDrawable(R.mipmap.flash_no));
                    isOpen = true;
                }
                break;
            case R.id.Album://相册
                ImagePicker.getInstance().setSelectLimit(Config.maxImgCount - selImageList.size());
                Intent intent = new Intent(mContext, MyImageGridActivity.class);
                startActivityForResult(intent, Config.REQUEST_CODE_SELECT);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mZXingView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
        if (data != null && requestCode == Config.REQUEST_CODE_SELECT) {
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images != null && images.size() > 0) {
                String filePath = images.get(0).path;
                mZXingView.decodeQRCode(filePath);
            }
        }
    }

    @Override
    public void setSucessLodeData(Bonus_Info lodeData) {
        if ("N".equals(lodeData.getIsBomb())) { //小奖
            customDialog = ViewBase.BonusSmallDialog(lodeData.getReturn_msg(), getActivity(), mZXingView);
        } else { //大奖
            customDialog = ViewBase.BonusBombDialog(lodeData.getReturn_msg(), getActivity(), mZXingView);
        }
    }

    @Override
    public void setDefaultMsg(String message) {
        customDialog = ViewBase.BonusDialog(message, getActivity(), mZXingView);
    }

    @Override
    public void setDefaultError(Response<String> response) {
        mZXingView.startSpot(); // 延迟0.5秒后开始识别
        mZXingView.showScanRect(); // 显示扫描框
        Debugger.handleError(response);
    }
}
