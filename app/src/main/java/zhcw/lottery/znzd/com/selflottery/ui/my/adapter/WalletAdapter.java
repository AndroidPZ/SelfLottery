package zhcw.lottery.znzd.com.selflottery.ui.my.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.Wallet_Info;

/**
 * 作者：XPZ on 2018/10/15 14:45.
 */
public class WalletAdapter extends BaseQuickAdapter<Wallet_Info.ListBean, BaseViewHolder> {
    public WalletAdapter(@Nullable List<Wallet_Info.ListBean> data) {
        super(R.layout.layout_wallet_adapetr, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder helper, Wallet_Info.ListBean item) {
        helper.setText(R.id.tweetName, item.getBalanceFrom())
                .setText(R.id.tweetDate, item.getCreateTime());

        TextView mTextView = helper.getView(R.id.tweetText);
        mTextView.setText(("0".equals(item.getBalanceType()) ? "+" : "-") + item.getBalance());
        mTextView.setTextColor("0".equals(item.getBalanceType()) ?
                Color.parseColor("#ffb20000") : Color.parseColor("#4CAF50"));
    }
}
