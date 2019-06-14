package zhcw.lottery.znzd.com.selflottery.widgets.DialogTask;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import zhcw.lottery.znzd.com.selflottery.util.CommonUtils;


/**
 * 作者：XPZ on 2018/3/9 10:40.
 */
public class CustomDialog extends Dialog {
    private static boolean isShow = true;
    private Context context;
    private int height, width;
    private boolean cancelTouchout;
    private View view;


    private CustomDialog(Builder builder) {
        super(builder.context);
        context = builder.context;
        height = builder.height;
        width = builder.width;
        cancelTouchout = builder.cancelTouchout;
        view = builder.view;
    }

    private CustomDialog(Builder builder, int resStyle) {
        super(builder.context, resStyle);
        context = builder.context;
        height = builder.height;
        width = builder.width;
        cancelTouchout = builder.cancelTouchout;
        view = builder.view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isShowDecorView(isShow);
        switchSystemUI(false);
        setContentView(view);
        setCanceledOnTouchOutside(cancelTouchout);
        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.CENTER;
        int screenWidth = CommonUtils.getScreenWidth();
        int screenHeight = CommonUtils.getScreenHeight();
        float scale = context.getResources().getDisplayMetrics().density;
        double valueheight = 0;
        double valuewidth = 0;
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            valuewidth = appInfo.metaData.getInt("design_width");
            valueheight = appInfo.metaData.getInt("design_height");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        double biliHeight = screenHeight / valueheight;
        double biliWidth = screenWidth / valuewidth;
        System.out.println(" app key : " + valuewidth + "  biliHeight:" + biliHeight);
        lp.height = (int) (height * biliHeight);
        lp.width = (int) (width * biliWidth);
        win.setAttributes(lp);
    }

    private void isShowDecorView(boolean isShow) {
        // 取消状态栏
        if (!isShow) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    /**
     * 隐藏系统底部状态栏
     *
     * @param visible 控制是否隐藏
     */
    protected void switchSystemUI(Boolean visible) {
        int flag = getWindow().getDecorView().getSystemUiVisibility();
        //int fullScreen=View.SYSTEM_UI_FLAG_SHOW_FULLSCREEN;
        int fullScreen = 0x8;
        if (visible) {
            if ((flag & fullScreen) != 0) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        } else {
            if ((flag & fullScreen) == 0) {
                getWindow().getDecorView().setSystemUiVisibility(flag | fullScreen);
            }
        }
    }

    public View getView() {
        return view;
    }

    public static final class Builder {
        private Context context;
        private int height, width;
        private boolean cancelTouchout;
        private View view;
        private int resStyle = -1;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder view(int resView) {
            view = LayoutInflater.from(context).inflate(resView, null);
            return this;
        }

        public Builder isShowDecorView(boolean show) {
            CustomDialog.isShow = show;
            return this;
        }

        public Builder heightpx(int val) {
            height = val;
            return this;
        }

        public Builder widthpx(int val) {
            width = val;
            return this;
        }

        public Builder heightdp(int val) {
            height = CommonUtils.dp2px(val);
            return this;
        }

        public Builder widthdp(int val) {
            width = CommonUtils.dp2px( val);
            return this;
        }

        public Builder heightDimenRes(int dimenRes) {
            height = context.getResources().getDimensionPixelOffset(dimenRes);
            return this;
        }

        public Builder widthDimenRes(int dimenRes) {
            width = context.getResources().getDimensionPixelOffset(dimenRes);
            return this;
        }

        public Builder style(int resStyle) {
            this.resStyle = resStyle;
            return this;
        }

        public Builder cancelTouchout(boolean val) {
            cancelTouchout = val;
            return this;
        }

        public Builder addViewOnclick(int viewRes, View.OnClickListener listener) {
            view.findViewById(viewRes).setOnClickListener(listener);
            return this;
        }

        public CustomDialog build() {
            if (resStyle != -1) {
                return new CustomDialog(this, resStyle);
            } else {
                return new CustomDialog(this);
            }
        }
    }
}

