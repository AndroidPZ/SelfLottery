package zhcw.lottery.znzd.com.selflottery.widgets.DialogTask;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhcw.lottery.znzd.com.selflottery.R;

/**
 * Created by xpz on 2018/10/12.
 */

public class CertificationDialog extends Dialog implements View.OnClickListener{
    @BindView(R.id.tv_jdcard_certification_next)
    TextView tvNext;
    @BindView(R.id.iv_jdcard_certification_dialog_close)
    ImageView ivClose;
    private Context mContext;
    private MyDialogListener mListener;


    public interface MyDialogListener {
        public void onClick(View view, CertificationDialog dialog);
    }

    public CertificationDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public CertificationDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    public CertificationDialog(Context requestCallBack, int theme, MyDialogListener listener) {
        super(requestCallBack, theme);
        this.mContext = requestCallBack;
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        mListener.onClick(v, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certification_dialog);
        ButterKnife.bind(this);
        tvNext.setOnClickListener(this);
        ivClose.setOnClickListener(this);
    }
}
