package zhcw.lottery.znzd.com.selflottery.util;

import android.content.Intent;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.ui.camera.CameraActivity;

import zhcw.lottery.znzd.com.selflottery.config.Config;
import zhcw.lottery.znzd.com.selflottery.ui.my.activity.CertificationActivity;


/**
 * Created by XPZ on 2018/4/20.
 */

public class OCRIntentUtils {
    public static void startPositive(CertificationActivity activity, String positivePath) {
        Intent intent = new Intent(activity, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, positivePath);
        //使用本地质量控制能力需要授权
        intent.putExtra(CameraActivity.KEY_NATIVE_TOKEN, OCR.getInstance().getLicense());
        //设置本地质量使用开启
        intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE, true);
        //设置扫描的身份证的类型（正面front还是反面back）
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
        activity.startActivityForResult(intent, Config.REQUEST_CODE_CAMERA);
    }

    public static void startReverse(CertificationActivity activity, String reversePath) {
        Intent intent = new Intent(activity, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, reversePath);
        intent.putExtra(CameraActivity.KEY_NATIVE_TOKEN, OCR.getInstance().getLicense());
        intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE, true);
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
        activity.startActivityForResult(intent, Config.REQUEST_CODE_CAMERA);
    }
}
