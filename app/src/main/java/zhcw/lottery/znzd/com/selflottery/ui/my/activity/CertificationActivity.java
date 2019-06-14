package zhcw.lottery.znzd.com.selflottery.ui.my.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.lqm.roundview.RoundImageView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.base.BaseActivity;
import zhcw.lottery.znzd.com.selflottery.config.Config;
import zhcw.lottery.znzd.com.selflottery.contact.CertificationContact;
import zhcw.lottery.znzd.com.selflottery.presenter.CertificationPresenter;
import zhcw.lottery.znzd.com.selflottery.ready_entity.bean_info.UserInfo;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.CertificationEntity;
import zhcw.lottery.znzd.com.selflottery.util.CreateBitmap;
import zhcw.lottery.znzd.com.selflottery.util.File_Utils;
import zhcw.lottery.znzd.com.selflottery.util.ImgGlideUtil;
import zhcw.lottery.znzd.com.selflottery.util.Logger;
import zhcw.lottery.znzd.com.selflottery.util.OCRIntentUtils;
import zhcw.lottery.znzd.com.selflottery.util.PermissionUtil;
import zhcw.lottery.znzd.com.selflottery.util.PreferenceUtil;
import zhcw.lottery.znzd.com.selflottery.util.ToastUtil;
import zhcw.lottery.znzd.com.selflottery.util.Utils;
import zhcw.lottery.znzd.com.selflottery.widgets.DialogTask.ActionSheetDialog;
import zhcw.lottery.znzd.com.selflottery.widgets.imagepicker.MyImageGridActivity;

/**
 * 实名认证
 *
 * @author XPZ
 */
public class CertificationActivity extends BaseActivity
        implements ActionSheetDialog.OnSheetItemClickListener, CertificationContact.ICertificationView {
    @BindView(R.id.tv_title_item_title)
    TextView tvTitle;
    @BindView(R.id.certification_idcard1_iv)
    RoundImageView mIdCard1IV;
    @BindView(R.id.certification_idcard2_iv)
    RoundImageView mIdCard2IV;
    @BindView(R.id.tv_certification_deposit)
    TextView mSend;
    @BindView(R.id.certification_remark_tv)
    TextView mRemarkTV;
    @BindView(R.id.certification_sv)
    NestedScrollView mScrollView;
    @BindView(R.id.cell_certification_ocr_ll)
    LinearLayout mOCRll;
    @BindView(R.id.iv_ocr_idCard)
    RoundImageView mCardIV;
    @BindView(R.id.bt_ocr_again)
    Button btAgin;
    @BindView(R.id.bt_ocr_next)
    Button btNext;
    @BindView(R.id.cell_certification_name_tv)
    TextView mCellNameTv;
    @BindView(R.id.cell_certification_idcard_tv)
    TextView mCellIdCardTv;
    @BindView(R.id.cell_certification_time_tv)
    TextView mCellTimeTv;
    @BindView(R.id.cell_certification_ll)
    LinearLayout mCellCertificationLl;
    private ActionSheetDialog actionSheet;
    private boolean isSelect = false;   // 用来区分身份证显示的
    private boolean isButton = true;
    private ArrayList<ImageItem> selImageList = new ArrayList<>(); //当前选择的所有图片
    private ArrayList<ImageItem> images = null;
    private String path = Environment.getExternalStorageDirectory().getPath() + "/zym/" + Config.SAVE_PIC_NAME1;
    private String path2 = Environment.getExternalStorageDirectory().getPath() + "/zym/" + Config.SAVE_PIC_NAME2;
    private File tempFile = new File(path);
    private File tempFile2 = new File(path2);
    private String positivePath = "";
    private String reversePath = "";
    private int idTyp = 0; //身份证类型，0：正面，1：反面
    private CertificationPresenter certificationPresenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_certification;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        init();
        initData();
        Utils.initAccessToken();//授权文件、安全模式
        Utils.initCertificationImagePicker(Config.maxImgCount, true);
        certificationPresenter = new CertificationPresenter(this, this);
        certificationPresenter.getDefaultDataRequest(UserInfo.getToken());
    }


    private void init() {
        tvTitle.setText(R.string.certification_title);
    }

    private void initData() {
        mSend.setEnabled(false);
    }

    @OnClick({R.id.certification_idcard1_iv, R.id.certification_idcard2_iv, R.id.bt_ocr_again, R.id.bt_ocr_next, R.id.tv_certification_deposit, R.id.iv_title_item_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.certification_idcard1_iv:
                if (isButton) {
                    doSomeThing();
                    idTyp = 0;
                }
                isSelect = true;
                break;
            case R.id.certification_idcard2_iv:
                if (isButton) {
                    doSomeThing();
                    idTyp = 1;
                }
                isSelect = false;
                break;
            case R.id.bt_ocr_again:
                if (idTyp == 0) {
                    positivePath = File_Utils.getSaveFile(getApplication(), CameraActivity.CONTENT_TYPE_ID_CARD_FRONT).getAbsolutePath();
                    OCRIntentUtils.startPositive(CertificationActivity.this, positivePath);
                } else {
                    reversePath = File_Utils.getSaveFile(getApplication(), CameraActivity.CONTENT_TYPE_ID_CARD_BACK).getAbsolutePath();
                    OCRIntentUtils.startReverse(CertificationActivity.this, reversePath);
                }
                break;
            case R.id.bt_ocr_next:
                mScrollView.setVisibility(View.VISIBLE);
                mOCRll.setVisibility(View.GONE);
                if (idTyp == 0) {
                    ImgGlideUtil.imgLoade(this,positivePath,mIdCard1IV);
//                    GlideApp.with(CertificationActivity.this).load(positivePath).fitCenter().dontAnimate().into(mIdCard1IV);
                } else {
                    ImgGlideUtil.imgLoade(this,reversePath,mIdCard2IV);
//                    GlideApp.with(CertificationActivity.this).load(reversePath).fitCenter().dontAnimate().into(mIdCard2IV);
                }
                break;
            case R.id.tv_certification_deposit:
                mSend.setEnabled(false);
                if (new File(positivePath).length() != 0 && new File(reversePath).length() != 0) {
                    sendCheck_Img(new File(positivePath), new File(reversePath));
                } else {
                    if (tempFile.length() != 0 && tempFile2.length() != 0) {
                        sendCheck_Img(tempFile, tempFile2);
                    } else if (new File(positivePath).length() != 0 && tempFile2.length() != 0) {
                        sendCheck_Img(new File(positivePath), tempFile2);
                    } else if (tempFile.length() != 0 && new File(reversePath).length() != 0) {
                        sendCheck_Img(tempFile, new File(reversePath));
                    } else {
                        ToastUtil.showShortToast("请上传身份证");
                    }
                }
                break;
            case R.id.iv_title_item_back:
                finish();
                break;
        }
    }

    protected void doSomeThing() {
        mSend.setEnabled(true);
        actionSheet = new ActionSheetDialog(this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("扫描", ActionSheetDialog.SheetItemColor.Blue, CertificationActivity.this)
                .addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue, CertificationActivity.this);
        actionSheet.show();
    }

    @Override
    public void onClick(int which) {
        switch (which) {
            case 1:
                // 调用拍照
                if (AndPermission.hasPermission(this, Manifest.permission.CAMERA)) {
                    // 有权限，直接do anything.
                    if (idTyp == 0) {
                        positivePath = File_Utils.getSaveFile(getApplication(), CameraActivity.CONTENT_TYPE_ID_CARD_FRONT).getAbsolutePath();
                        OCRIntentUtils.startPositive(this, positivePath);
                    } else {
                        reversePath = File_Utils.getSaveFile(getApplication(), CameraActivity.CONTENT_TYPE_ID_CARD_BACK).getAbsolutePath();
                        OCRIntentUtils.startReverse(this, reversePath);
                    }
                } else {
                    // 申请权限。
                    PermissionUtil.init(CertificationActivity.this);
                    ActivityCompat.requestPermissions(CertificationActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA}, ImageGridActivity.REQUEST_PERMISSION_CAMERA);
                }
                break;
            case 2:
                // 调用相册
                /**打开选择,本次允许选择的数量**/
                ImagePicker.getInstance().setSelectLimit(Config.maxImgCount - selImageList.size());
                Intent intent = new Intent(CertificationActivity.this, MyImageGridActivity.class);
                startActivityForResult(intent, Config.REQUEST_CODE_SELECT);
                break;
        }
    }

    private void sendCheck_Img(File tempFile, File tempFile2) {
        int compressSize = 50;
        File imageFile = CreateBitmap.getCompressionImage(CertificationActivity.this, tempFile, Config.SAVE_PIC_NAME1, compressSize);
        File imageFile2 = CreateBitmap.getCompressionImage(CertificationActivity.this, tempFile2, Config.SAVE_PIC_NAME2, compressSize);
        certificationPresenter.getCertificationDataRequest(UserInfo.getToken(), imageFile, imageFile2);
        String size1 = Utils.getReadableFileSize(imageFile.length());
        String size2 = Utils.getReadableFileSize(imageFile2.length());
        Logger.i("身份证正面大小" + size1);
        Logger.i("身份证发面大小" + size2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放内存资源
        OCR.getInstance().release();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果拍摄类型是身份证
        if (requestCode == Config.REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                if (!TextUtils.isEmpty(contentType)) {
                    mScrollView.setVisibility(View.GONE);
                    mOCRll.setVisibility(View.VISIBLE);
                    //判断是身份证正面还是反面
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        ImgGlideUtil.imgLoade(this,positivePath,mCardIV);
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        ImgGlideUtil.imgLoade(this,reversePath,mCardIV);
                    }
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {//相册图片
            if (data != null && requestCode == Config.REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null && images.size() > 0) {
                    String filePath = images.get(0).path;
                    int degree = CreateBitmap.readPictureDegree(filePath);
                    Bitmap bmp = CreateBitmap.getimage(filePath);
                    final Bitmap rotaingBmp = CreateBitmap.rotaingImageView(degree, bmp);
                    if (isSelect) {
                        ImgGlideUtil.imgLoade(this,filePath,mIdCard1IV);
                        // 保存图片到本地
                        new Thread(() -> CreateBitmap.saveCropPic(rotaingBmp, tempFile)).start();
                    } else {
                        ImgGlideUtil.imgLoade(this,filePath,mIdCard2IV);
                        new Thread(() -> CreateBitmap.saveCropPic(rotaingBmp, tempFile2)).start();
                    }
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ImageGridActivity.REQUEST_PERMISSION_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ToastUtil.showShortToast("相机权限开启成功");
            } else {
                ToastUtil.showShortToast("权限被禁止，无法打开相机");
            }
        }
    }

    @Override
    public void setDefaultSucess(CertificationEntity entity) {
        PreferenceUtil.getInstance().putPreferences(Config.AUDIT, entity.getState());
        UserInfo.refresh();
        ImgGlideUtil.imgLoade(this,entity.getIdPosPic(),mIdCard1IV);
        ImgGlideUtil.imgLoade(this,entity.getIdOthPic(),mIdCard2IV);
        if (!"".equals(entity.getRemark())) {
            mRemarkTV.setText("失败原因:" + entity.getRemark() + "(*请重新上传)");
            mRemarkTV.setBackgroundColor(Color.parseColor("#CCCCCC"));
        }
        if (UserInfo.getAudit() == 1 || UserInfo.getAudit() == 0) {
            mIdCard1IV.setEnabled(false);
            mIdCard2IV.setEnabled(false);
            mSend.setVisibility(View.GONE);
            if (UserInfo.getAudit() == 1) {
                mCellCertificationLl.setVisibility(View.VISIBLE);
                mCellNameTv.setText(entity.getRealName());
                mCellIdCardTv.setText(entity.getIdNumber());
                mCellTimeTv.setText(Utils.dataFormatStr(entity.getInvalidDate()));
            }
        } else {
            mIdCard1IV.setEnabled(true);
            mIdCard2IV.setEnabled(true);
        }
    }

    @Override
    public void setDefaultError(String message) {
        ToastUtil.showShortToast(message);
    }

    @Override
    public void setCertificationSucess() {
        finish();
    }

    @Override
    public void setCertificationError(String message) {
        ToastUtil.showShortToast(message);
    }
}
