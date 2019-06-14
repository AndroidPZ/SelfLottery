package zhcw.lottery.znzd.com.selflottery.ui.lottery.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.config.ConstantValue;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.ShoppingCartK3;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.TicketK3;

/**
 * 作者：XPZ on 2018/3/7 16:19.
 */
public class ShoppingAdapterK3 extends BaseQuickAdapter<TicketK3, BaseViewHolder> {

    public ShoppingAdapterK3(int layoutResId, @Nullable List<TicketK3> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final TicketK3 ticketK3) {

        TextView type = helper.getView(R.id.ii_shopping_item_type);
        helper.addOnClickListener(R.id.ii_shopping_item_delete);
        String lotteryid = ticketK3.getLotteryid();
        if (ConstantValue.K3_HE.equals(lotteryid)) {
            type.setText(String.format("[%s]", ConstantValue.gameK3Type_HZ));
        } else if (ConstantValue.K3_STH_TX.equals(lotteryid)) {
            type.setText(String.format("[%s]", ConstantValue.gameK3Type_STHTX));
        } else if (ConstantValue.K3_SBT.equals(lotteryid)) {
            type.setText(String.format("[%s]", ConstantValue.gameK3Type_SBTH));
        } else if (ConstantValue.K3_RTH_DX.equals(lotteryid)) {
            type.setText(String.format("[%s]", ConstantValue.gameK3Type_RTHDX));
        } else if (ConstantValue.K3_STH_DX.equals(lotteryid)) {
            type.setText(String.format("[%s]", ConstantValue.gameK3Type_STHDX));
        } else if (ConstantValue.K3_SLH_TX.equals(lotteryid)) {
            type.setText(String.format("[%s]", ConstantValue.gameK3Type_SLHTX));
        } else if (ConstantValue.K3_RTH_FX.equals(lotteryid)) {
            type.setText(String.format("[%s]", ConstantValue.gameK3Type_RTHFX));
        } else if (ConstantValue.K3_RBT.equals(lotteryid)) {
            type.setText(String.format("[%s]", ConstantValue.gameK3Type_RBTH));
        }

        TextView state = helper.getView(R.id.ii_shopping_item_state);
        if (ShoppingCartK3.getInstance().getAppnumbers() * ticketK3.getNum() * 2
                > ConstantValue.UPPER_LIMIT_SHOPPING) {
            state.setVisibility(View.VISIBLE);
            state.setText(R.string.is_lottery_money_list);
            YoYo.with(Techniques.Tada).duration(700).playOn(state);
            ticketK3.setState(true);
        } else {
            state.setVisibility(View.GONE);
            ticketK3.setState(false);
        }
        
        helper.setText(R.id.ii_shopping_item_money, String.format("共%s元", ticketK3.getNum() * 2))
                .setText(R.id.ii_shopping_item_zhushu, String.format("%s注", ticketK3.getNum()))
                .setText(R.id.ii_shopping_item_reds, String.format("%s", ticketK3.getHeZhiNum()));
    }
}