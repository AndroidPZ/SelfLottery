package zhcw.lottery.znzd.com.selflottery.util;

import android.Manifest;
import android.app.Activity;
import android.support.annotation.NonNull;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

/**
 * Created by xpz on 2018/10/15.
 */

public class PermissionUtil {
    private static boolean isShow=false;
    public static void init(final Activity activity) {
        AndPermission.with(activity)
                .requestCode(0)
                .permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        // 权限申请成功回调。
//                        if (requestCode == 100) {
//                            ToastUtil.showShortToast("权限申请成功");
//                        }
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(activity, deniedPermissions)) {
                            AndPermission.defaultSettingDialog(activity).show();
                        }
                        // 权限申请失败回调。
                        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
                        if (AndPermission.hasAlwaysDeniedPermission(activity, deniedPermissions)) {
                            // 第二种：用自定义的提示语。
                            if (!isShow){
                                AndPermission.defaultSettingDialog(activity, 300)
                                        .setTitle("权限申请失败")
                                        .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                                        .setPositiveButton("好，去设置")
                                        .show();
                                isShow=true;
                            }

                        }
                    }
                }).rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        AndPermission.rationaleDialog(activity, rationale);
                    }
                }).start();
    }
}
