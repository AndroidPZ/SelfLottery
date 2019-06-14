package zhcw.lottery.znzd.com.selflottery.util;

import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.util.TypedValue;

import java.lang.reflect.Field;

import zhcw.lottery.znzd.com.selflottery.App;

/**
 * @author XPZ on 2017/5/25 10:30.
 */

public class CommonUtils {
    private static int widthPixels = -1;
    private static int heightPixels = -1;

    /**
     * 获取Resource中的color
     *
     * @param context
     * @param colorId
     * @return
     */
    public static int getColor(Context context, @ColorRes int colorId) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            //noinspection deprecation
            return context.getResources().getColor(colorId);
        } else {
            return context.getColor(colorId);
        }
    }

    /**
     * 获取屏幕的宽度
     *
     * @return
     */
    public static int getScreenWidth() {

        if (widthPixels <= 0) {
            widthPixels = App.getInstance().getResources().getDisplayMetrics().widthPixels;
        }
        return widthPixels;
    }


    /**
     * 获取屏幕的高度
     *
     * @return
     */
    public static int getScreenHeight() {

        if (heightPixels <= 0) {
            heightPixels = App.getInstance().getResources().getDisplayMetrics().heightPixels;
        }
        return heightPixels;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue
     * @return
     */
    public static int dp2px(float dpValue) {
        final float scale = App.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param pxValue
     * @return
     */
    public static int px2dp(float pxValue) {
        final float scale = App.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * px转sp
     */
    public static float px2sp(float pxVal) {
        return (pxVal / App.getInstance().getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * sp转px
     */
    public static int sp2px( float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal,
                App.getInstance().getResources().getDisplayMetrics());
    }

    /**
     * 获取状态栏的高度
     *
     * @return
     */
    public static int getBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 38;//默认为38，貌似大部分是这样的

        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = App.getInstance().getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }
}
