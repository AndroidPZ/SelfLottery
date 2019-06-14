package zhcw.lottery.znzd.com.selflottery.widgets.DialogTask;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.widgets.AnimationTask.OptAnimationLoader;
import zhcw.lottery.znzd.com.selflottery.widgets.AnimationTask.SuccessTickView;
import zhcw.lottery.znzd.com.selflottery.widgets.AnimationTask.WarningTickView;

/**
 * Created by xpz on 2018/10/19.
 */

public class BottomDialog extends Dialog implements View.OnClickListener {

    public static final int ERROR_TYPE = 1;
    public static final int SUCCESS_TYPE = 2;
    public static final int WARNING_TYPE = 3;
    @BindView(R.id.mask_right)
    View mSuccessRightMask;
    @BindView(R.id.mask_left)
    View mSuccessLeftMask;
    @BindView(R.id.success_tick)
    SuccessTickView mSuccessTick;
    @BindView(R.id.success_frame)
    FrameLayout mSuccessFrame;
    @BindView(R.id.error_x)
    ImageView mErrorX;
    @BindView(R.id.error_frame)
    FrameLayout mErrorFrame;
    @BindView(R.id.warning_t)
    ImageView warningT;
    @BindView(R.id.warning_tick)
    WarningTickView mWarningTick;
    @BindView(R.id.warning_frame)
    FrameLayout mWarningFrame;
    @BindView(R.id.msg_tv)
    TextView msgTv;
    @BindView(R.id.bt_wxpay)
    Button btWxpay;
    private Context mContext;
    private BottomDialogListener mListener;
    private Animation mSuccessBowAnim;
    private AnimationSet mSuccessLayoutAnimSet;
    private Animation mErrorInAnim;
    private AnimationSet mErrorXInAnim;
    private String mMsgText;
    private int mAlertType;
    private View mDialogView;


    public BottomDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public BottomDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;

        mSuccessBowAnim = OptAnimationLoader.loadAnimation(context, R.anim.success_bow_roate);
        mSuccessLayoutAnimSet = (AnimationSet) OptAnimationLoader.loadAnimation(context, R.anim.success_mask_layout);

        mErrorInAnim = OptAnimationLoader.loadAnimation(context, R.anim.error_frame_in);
        mErrorXInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(context, R.anim.error_x_in);
        // 2.3.x system don't support alpha-animation on layer-list drawable
        // remove it from animation set
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            List<Animation> childAnims = mErrorXInAnim.getAnimations();
            int idx = 0;
            for (; idx < childAnims.size(); idx++) {
                if (childAnims.get(idx) instanceof AlphaAnimation) {
                    break;
                }
            }
            if (idx < childAnims.size()) {
                childAnims.remove(idx);
            }
        }
    }

    public BottomDialog(Context context, int theme, BottomDialogListener listener) {
        super(context, theme);
    }

    @Override
    public void onClick(View v) {
        mListener.onClick(v, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_style);
        ButterKnife.bind(this);
        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        btWxpay.setOnClickListener(this);

        setMsgText(mMsgText);
        changeAlertType(mAlertType, true);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM; // 显示在底部
        params.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度填充满屏
        window.setAttributes(params);
        //设置 dialog 的背景为 null
        window.setBackgroundDrawable(null);
    }

    public BottomDialog setMsgText(String text) {
        mMsgText = text;
        if (msgTv != null && mMsgText != null) {
            msgTv.setText(mMsgText);
        }
        return this;
    }


    private void restore() {
        mErrorFrame.setVisibility(View.GONE);
        mSuccessFrame.setVisibility(View.GONE);
        mWarningFrame.setVisibility(View.GONE);

        mErrorFrame.clearAnimation();
        mErrorX.clearAnimation();
        mSuccessTick.clearAnimation();
        mSuccessLeftMask.clearAnimation();
        mSuccessRightMask.clearAnimation();
    }

    public BottomDialog changeAlertType(int alertType, boolean fromCreate) {
        mAlertType = alertType;
        // call after created views
        if (mDialogView != null) {
            if (!fromCreate) {
                // restore all of views state before switching alert type
                restore();
            }
            switch (mAlertType) {
                case ERROR_TYPE:
                    mErrorFrame.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS_TYPE:
                    mSuccessFrame.setVisibility(View.VISIBLE);
                    // initial rotate layout of success mask
                    mSuccessLeftMask.startAnimation(mSuccessLayoutAnimSet.getAnimations().get(0));
                    mSuccessRightMask.startAnimation(mSuccessLayoutAnimSet.getAnimations().get(1));
                    break;
                case WARNING_TYPE:
                    mWarningFrame.setVisibility(View.VISIBLE);
                    break;
            }
            if (fromCreate) {
                playAnimation();
            }
        }
        return this;
    }


    public BottomDialog setCancelClickListener(BottomDialogListener listener) {
        mListener = listener;
        return this;
    }
    private void playAnimation() {
        if (mAlertType == ERROR_TYPE) {
            mErrorFrame.startAnimation(mErrorInAnim);
            mErrorX.startAnimation(mErrorXInAnim);
        } else if (mAlertType == SUCCESS_TYPE) {
            mSuccessTick.startTickAnim();
            mSuccessRightMask.startAnimation(mSuccessBowAnim);
        } else if (mAlertType == WARNING_TYPE) {
            mWarningTick.startTickAnim();
        }

    }

    public interface BottomDialogListener {
        public void onClick(View view, BottomDialog dialog);
    }

}
