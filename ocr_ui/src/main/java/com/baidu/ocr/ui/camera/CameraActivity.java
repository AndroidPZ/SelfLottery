/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.ocr.ui.camera;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.baidu.idcardquality.IDcardQualityProcess;
import com.baidu.ocr.ui.R;
import com.baidu.ocr.ui.crop.CropView;
import com.baidu.ocr.ui.crop.FrameOverlayView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraActivity extends Activity {

    public static final String KEY_OUTPUT_FILE_PATH = "outputFilePath";
    public static final String KEY_CONTENT_TYPE = "contentType";
    public static final String KEY_NATIVE_TOKEN = "nativeToken";
    public static final String KEY_NATIVE_ENABLE = "nativeEnable";
    public static final String KEY_NATIVE_MANUAL = "nativeEnableManual";

    public static final String CONTENT_TYPE_GENERAL = "general";
    public static final String CONTENT_TYPE_ID_CARD_FRONT = "IDCardFront";
    public static final String CONTENT_TYPE_ID_CARD_BACK = "IDCardBack";

    private static final int PERMISSIONS_REQUEST_CAMERA = 800;
    private static final int PERMISSIONS_EXTERNAL_STORAGE = 801;

    private File outputFile;
    private String contentType;

    private boolean isNativeEnable;
    private boolean isNativeManual;

    private CameraView cameraView;//相机View
    private String token;
    private String outputPath;

    private PermissionCallback permissionCallback = new PermissionCallback() {
        @Override
        public boolean onRequestPermission() {
            ActivityCompat.requestPermissions(CameraActivity.this, new String[] {Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CAMERA);
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.bd_ocr_activity_camera);
        cameraView = (CameraView) findViewById(R.id.camera_view);
        cameraView.getCameraControl().setPermissionCallback(permissionCallback);

        setOrientation(getResources().getConfiguration());
        initParams();
        cameraView.setAutoPictureCallback(autoTakePictureCallback);

    }

    @Override
    protected void onStart() {
        super.onStart();
        cameraView.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cameraView.stop();
    }

    private void initParams() {
        outputPath = getIntent().getStringExtra(KEY_OUTPUT_FILE_PATH);
        token = getIntent().getStringExtra(KEY_NATIVE_TOKEN);//使用本地质量控制能力需要授权
        isNativeEnable = getIntent().getBooleanExtra(KEY_NATIVE_ENABLE, true);//设置本地质量使用开启
        isNativeManual = getIntent().getBooleanExtra(KEY_NATIVE_MANUAL, false);
        contentType = getIntent().getStringExtra(KEY_CONTENT_TYPE);//设置扫描的身份证的类型（正面front还是反面back）
        if (token == null && !isNativeManual) {
            isNativeEnable = false;
        }
        if (outputPath != null) {
            outputFile = new File(outputPath);
        }
        if (contentType == null) {
            contentType = CONTENT_TYPE_GENERAL;
        }
        int maskType = 0;
        switch (contentType) {
            case CONTENT_TYPE_ID_CARD_FRONT:
                maskType = MaskView.MASK_TYPE_ID_CARD_FRONT;
                break;
            case CONTENT_TYPE_ID_CARD_BACK:
                maskType = MaskView.MASK_TYPE_ID_CARD_BACK;
                break;
        }
        // 身份证本地能力初始化
        if (maskType == MaskView.MASK_TYPE_ID_CARD_FRONT || maskType == MaskView.MASK_TYPE_ID_CARD_BACK) {
            if (isNativeEnable && !isNativeManual) {
                initNative(token);
            }
        }
        cameraView.setEnableScan(isNativeEnable);
        cameraView.setMaskType(maskType, this);
    }

    private void initNative(final String token) {
        CameraNativeHelper.init(CameraActivity.this, token,
                new CameraNativeHelper.CameraNativeInitCallback() {
            @Override
            public void onError(int errorCode, Throwable e) {
                cameraView.setInitNativeStatus(errorCode);
            }
        });
    }
    /**
     * 自动识别结果处理
     */
    private CameraView.OnTakePictureCallback autoTakePictureCallback = new CameraView.OnTakePictureCallback() {
        @Override
        public void onPictureTaken(final Bitmap bitmap) {
            CameraThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                        bitmap.recycle();
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent();
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, contentType);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            });
        }
    };
    /**
     * 自动识别身份证
     * @param newConfig
     */
    private void setOrientation(Configuration newConfig) {
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int cameraViewOrientation = CameraView.ORIENTATION_PORTRAIT;
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                cameraViewOrientation = CameraView.ORIENTATION_PORTRAIT;
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_90) {
                    cameraViewOrientation = CameraView.ORIENTATION_HORIZONTAL;
                } else {
                    cameraViewOrientation = CameraView.ORIENTATION_INVERT;
                }
                break;
            default:
                cameraView.setOrientation(CameraView.ORIENTATION_PORTRAIT);
                break;
        }
        cameraView.setOrientation(cameraViewOrientation);
    }
    /**
     * 调用相机权限
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraView.getCameraControl().refreshPermission();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.camera_permission_required, Toast.LENGTH_LONG).show();
                }
                break;
            }
            case PERMISSIONS_EXTERNAL_STORAGE:
            default:
                break;
        }
    }
    /**
     * 做一些收尾工作
     *
     */
    private void doClear() {
        CameraThreadPool.cancelAutoFocusTimer();
        if (isNativeEnable && !isNativeManual) {
            IDcardQualityProcess.getInstance().releaseModel();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.doClear();
    }
}
