package zhcw.lottery.znzd.com.selflottery.util;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import zhcw.lottery.znzd.com.selflottery.R;


/**
 * Created by ttz on 2017/10/18.
 * 验证码工具类
 */

public class VerificationCodeUtil extends CountDownTimer {
    private TextView mText;
    private Context xContext;

    public VerificationCodeUtil(TextView mText, long millisInFuture, long countDownInterval, Context xContext) {
        super(millisInFuture, countDownInterval);
        this.mText = mText;
        this.xContext = xContext;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        // 按钮不可用
        mText.setEnabled(false);
        String showText = "重新获取 " + millisUntilFinished / 1000 + "s";
        mText.setText(showText);
//        mText.setBackgroundColor(xContext.getResources().getColor(R.color.count_time));
        mText.setTextColor(xContext.getResources().getColor(R.color.nine));
    }

    @Override
    public void onFinish() {
        // 按钮设置可用
        mText.setEnabled(true);
        mText.setText("重新获取");
//        mText.setBackgroundResource(R.drawable.code_button_bg);
        mText.setTextColor(xContext.getResources().getColor(R.color.comm_yz_bg));
    }

}
