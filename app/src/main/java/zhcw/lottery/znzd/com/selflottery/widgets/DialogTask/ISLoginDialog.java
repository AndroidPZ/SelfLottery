package zhcw.lottery.znzd.com.selflottery.widgets.DialogTask;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhcw.lottery.znzd.com.selflottery.R;

/**
 * Created by xpz on 2019/1/7.
 */

public class ISLoginDialog extends Dialog implements View.OnClickListener {
    private int mType;
    @BindView(R.id.tv_islogn_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_islogn_ok)
    TextView tvOk;
    @BindView(R.id.tv_msg_login)
    TextView tvMsgLogin;
    private Context context;
    private LeaveListener mListener;
    private View customView;
    private String tvMsg;

    public interface LeaveListener {
        public void onClick(View view, ISLoginDialog dialog);
    }

    public ISLoginDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public ISLoginDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    public ISLoginDialog(@NonNull Context context, int theme, LeaveListener listener,int mType) {
        super(context, theme);
        this.context = context;
        this.mListener = listener;
        this.mType = mType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_islogin);

        ButterKnife.bind(this);
        init();
    }

    private void init() {
        tvCancel.setOnClickListener(this);
        tvOk.setOnClickListener(this);
        if (mType == 1) {
            tvMsgLogin.setText("您尚未登录请登录");
        } else {
            tvMsgLogin.setText("登录状态发生改变,请重新登陆!");
        }
    }

    @Override
    public void onClick(View v) {
        mListener.onClick(v, this);
    }
}
