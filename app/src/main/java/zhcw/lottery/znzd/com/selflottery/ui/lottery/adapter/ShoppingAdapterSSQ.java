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
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.ShoppingCartSSQ;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.TicketSSQ;


/**
 * 作者：XPZ on 2018/3/7 16:19.
 */
public class ShoppingAdapterSSQ extends BaseQuickAdapter<TicketSSQ, BaseViewHolder> {


    public ShoppingAdapterSSQ(int layoutResId, @Nullable List<TicketSSQ> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final TicketSSQ ticketSSQ) {

        TextView redNum = helper.getView(R.id.ii_shopping_item_reds);
        TextView type = helper.getView(R.id.ii_shopping_item_type);

        helper.addOnClickListener(R.id.ii_shopping_item_delete);

        if (ConstantValue.SSQ_P.equals(ticketSSQ.getLotteryid())) {
            type.setText("[单式]");
            redNum.setText(ticketSSQ.getRedNum());
        } else if (ConstantValue.SSQ_FS.equals(ticketSSQ.getLotteryid())) {
            type.setText("[复式]");
            redNum.setText(ticketSSQ.getRedNum());
        } else if (ConstantValue.SSQ_DT.equals(ticketSSQ.getLotteryid())) {
            type.setText("[胆拖]");
            redNum.setText(String.format("【%s】 %s", ticketSSQ.getRedNum(), ticketSSQ.getRedNumTuo()));
        }

        TextView state = helper.getView(R.id.ii_shopping_item_state);
        if (ShoppingCartSSQ.getInstance().getAppnumbers() * ticketSSQ.getNum() * 2
                > ConstantValue.UPPER_LIMIT_SHOPPING) {
            state.setVisibility(View.VISIBLE);
            state.setText(R.string.is_lottery_money_list);
            YoYo.with(Techniques.Tada).duration(700).playOn(state);
            ticketSSQ.setState(true);
        } else {
            state.setVisibility(View.GONE);
            ticketSSQ.setState(false);
        }

        helper.setText(R.id.ii_shopping_item_money, String.format("共%s元", ticketSSQ.getNum() * 2))
                .setText(R.id.ii_shopping_item_blues, ticketSSQ.getBlueNum())
                .setText(R.id.ii_shopping_item_zhushu, String.format("%s注", ticketSSQ.getNum()));
    }
}

