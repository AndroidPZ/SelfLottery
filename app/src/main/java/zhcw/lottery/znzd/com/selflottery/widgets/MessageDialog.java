package zhcw.lottery.znzd.com.selflottery.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhcw.lottery.znzd.com.selflottery.R;

/**
 * Created by XPZ on 2018/11/13.
 */

public class MessageDialog extends Dialog implements View.OnClickListener {
    @BindView(R.id.bt_message_ok)
    Button mOk;
    @BindView(R.id.tv_message_title)
    TextView mTitle;
    @BindView(R.id.tv_message_content)
    TextView mContent;
    private Context mContext;
    private LeaveMyDisclaimerListener mListener;
    public interface LeaveMyDisclaimerListener {
        public void onClick(View view, MessageDialog dialog);
    }

    public TextView getTitle(){
        return mTitle;
    }

    public TextView getContent(){
        return mContent;
    }

    public MessageDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public MessageDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    public MessageDialog(Context context, int theme, LeaveMyDisclaimerListener listener) {
        super(context, theme);
        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_message);
        ButterKnife.bind(this);
        init();
    }
    private void init(){
        mOk.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        mListener.onClick(v,this);
    }
}
