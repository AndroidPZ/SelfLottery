package zhcw.lottery.znzd.com.selflottery.base;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import zhcw.lottery.znzd.com.selflottery.R;


/**
 * Created by xpz on 2018/4/11.
 * 加载动画
 */

public class SimpleBaseUIFactory  {

    private final Context mContext;

    public SimpleBaseUIFactory(Context context) {
        this.mContext = context;
    }

    
    public Dialog createLoadingDialog() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_base, null);
        AVLoadingIndicatorView avi = (AVLoadingIndicatorView) view.findViewById(R.id.aviLoading);
        avi.setIndicator("BallSpinFadeLoaderIndicator");
        avi.setIndicatorColor(mContext.getResources().getColor(R.color.white));
        TextView loadText = (TextView) view.findViewById(R.id.tvLoadingText);
        loadText.setText("请稍后");
        Dialog mDialog = new Dialog(mContext, R.style.loading_dialog_style);
        mDialog.setCancelable(true);
        mDialog.setContentView(view, new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        return mDialog;
    }
}
