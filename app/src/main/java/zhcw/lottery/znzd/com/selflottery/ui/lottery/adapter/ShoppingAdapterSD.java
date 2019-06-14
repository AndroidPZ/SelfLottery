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
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.ShoppingCartSD;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.TicketSD;


/**
 * 作者：XPZ on 2018/3/7 16:19.
 */
public class ShoppingAdapterSD extends BaseQuickAdapter<TicketSD, BaseViewHolder> {

    public ShoppingAdapterSD(int layoutResId, @Nullable List<TicketSD> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final TicketSD ticketSD) {

        TextView type = helper.getView(R.id.ii_shopping_item_type);
        helper.addOnClickListener(R.id.ii_shopping_item_delete);

        if (ConstantValue.SD_P.equals(ticketSD.getLotteryid())) {
            type.setText(String.format("[%s]", ConstantValue.gameSDType_ZX));
        } else if (ConstantValue.SD_ZS.equals(ticketSD.getLotteryid())) {
            type.setText(String.format("[%s]", ConstantValue.gameSDType_ZS));
        } else if (ConstantValue.SD_ZL.equals(ticketSD.getLotteryid())) {
            type.setText(String.format("[%s]", ConstantValue.gameSDType_ZL));
        } else if (ConstantValue.SD_ZF.equals(ticketSD.getLotteryid())) {
            type.setText(String.format("[%s]", ConstantValue.gameSDType_ZXFS));
        } else if (ConstantValue.SD_ZSF.equals(ticketSD.getLotteryid())) {
            type.setText(String.format("[%s]", ConstantValue.gameSDType_ZSFS));
        } else if (ConstantValue.SD_ZLF.equals(ticketSD.getLotteryid())) {
            type.setText(String.format("[%s]", ConstantValue.gameSDType_ZLFS));
        } else if (ConstantValue.SD_ZXHZ.equals(ticketSD.getLotteryid())) {
            type.setText(String.format("[%s]", ConstantValue.gameSDType_ZXHZ));
        } else if (ConstantValue.SD_ZSHZ.equals(ticketSD.getLotteryid())) {
            type.setText(String.format("[%s]", ConstantValue.gameSDType_ZSHZ));
        } else if (ConstantValue.SD_ZLHZ.equals(ticketSD.getLotteryid())) {
            type.setText(String.format("[%s]", ConstantValue.gameSDType_ZLHZ));
        } else if (ConstantValue.SD_ZX_NWBH.equals(ticketSD.getLotteryid())) {
            type.setText(String.format("[%s]", ConstantValue.gameSDType_ZXWX));
        }

        TextView state = helper.getView(R.id.ii_shopping_item_state);
        if (ShoppingCartSD.getInstance().getAppnumbers() * ticketSD.getNum() * 2
                > ConstantValue.UPPER_LIMIT_SHOPPING) {
            state.setVisibility(View.VISIBLE);
            state.setText(R.string.is_lottery_money_list);
            YoYo.with(Techniques.Tada).duration(700).playOn(state);
            ticketSD.setState(true);
        } else {
            state.setVisibility(View.GONE);
            ticketSD.setState(false);
        }
        
        helper.setText(R.id.ii_shopping_item_money, String.format("共%s元", ticketSD.getNum() * 2))
                .setText(R.id.ii_shopping_item_zhushu, String.format("%s注", ticketSD.getNum()))
                .setText(R.id.ii_shopping_item_reds, String.format("%s", ticketSD.getViewNum()));
    }
}