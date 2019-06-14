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
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.ShoppingCartSSC;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.TicketSSC;
import zhcw.lottery.znzd.com.selflottery.util.Utils;


/**
 * 作者：XPZ on 2018/3/7 16:19.
 */
public class ShoppingAdapterSSC extends BaseQuickAdapter<TicketSSC, BaseViewHolder> {

    public ShoppingAdapterSSC(int layoutResId, @Nullable List<TicketSSC> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final TicketSSC ticketSSC) {

        TextView type = helper.getView(R.id.ii_shopping_item_type);
        helper.addOnClickListener(R.id.ii_shopping_item_delete);

        if (ConstantValue.SSC_P.equals(ticketSSC.getLotteryid())) {
            String redNumSeed = ticketSSC.getRedNumSeed();
            type.setText(String.format(" [%s直选]", xingType(redNumSeed)));
        } else if (ConstantValue.SSC_F.equals(ticketSSC.getLotteryid())) {
            String redNumSeed = ticketSSC.getRedNumSeed();
            type.setText(String.format(" [%s复选]", xingType(redNumSeed)));
        } else if (ConstantValue.SSC_FW.equals(ticketSSC.getLotteryid())) {
            String redNumSeed = ticketSSC.getRedNumSeed();
            type.setText(" [位选投注]");
        } else if (ConstantValue.SSC_DXJO.equals(ticketSSC.getLotteryid())) {
            type.setText(" [大小奇偶]");
        } else {
            type.setText(" [五星通选]");
        }

        TextView state = helper.getView(R.id.ii_shopping_item_state);
        if (ShoppingCartSSC.getInstance().getAppnumbers() * ticketSSC.getNum() * 2
                > ConstantValue.UPPER_LIMIT_SHOPPING) {
            state.setVisibility(View.VISIBLE);
            state.setText(R.string.is_lottery_money_list);
            YoYo.with(Techniques.Tada).duration(700).playOn(state);
            ticketSSC.setState(true);
        } else {
            state.setVisibility(View.GONE);
            ticketSSC.setState(false);
        }
        
        helper.setText(R.id.ii_shopping_item_money, String.format("共%s元", ticketSSC.getNum() * 2))
                .setText(R.id.ii_shopping_item_zhushu, String.format("%s注", ticketSSC.getNum()))
                .setText(R.id.ii_shopping_item_reds, String.format("%s", ticketSSC.getRedNumView()));
    }

    private String xingType(String redNumSeed) {
        switch (Utils.getXingType(redNumSeed)) {
            case 0: //五星
                return "五星";
            case 1: //四星
                return "四星";
            case 2: //三星
                return "三星";
            case 3: //二星
                return "二星";
            case 4: //一星
                return "一星";
            default:
                return "时时彩";
        }
    }
}