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
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.ShoppingCart7LC;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.Ticket7LC;


/**
 * 作者：XPZ on 2018/3/7 16:19.
 */
public class ShoppingAdapter7LC extends BaseQuickAdapter<Ticket7LC, BaseViewHolder> {

    public ShoppingAdapter7LC(int layoutResId, @Nullable List<Ticket7LC> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final Ticket7LC ticket7LC) {

        TextView redNum = helper.getView(R.id.ii_shopping_item_reds);
        TextView type = helper.getView(R.id.ii_shopping_item_type);
        helper.addOnClickListener(R.id.ii_shopping_item_delete);

        if (ConstantValue.T7LC_P.equals(ticket7LC.getLotteryid())) {
            type.setText("[单式]");
            redNum.setText(ticket7LC.getRedNum());
        } else if (ConstantValue.T7LC_FS.equals(ticket7LC.getLotteryid())) {
            type.setText("[复式]");
            redNum.setText(ticket7LC.getRedNum());
        } else if (ConstantValue.T7LC_DT.equals(ticket7LC.getLotteryid())) {
            type.setText("[胆拖]");
            redNum.setText(String.format("【%s】 %s", ticket7LC.getRedNum(), ticket7LC.getRedNumTuo()));
        }

        TextView state = helper.getView(R.id.ii_shopping_item_state);
        if (ShoppingCart7LC.getInstance().getAppnumbers() * ticket7LC.getNum() * 2
                > ConstantValue.UPPER_LIMIT_SHOPPING) {
            state.setVisibility(View.VISIBLE);
            state.setText(R.string.is_lottery_money_list);
            YoYo.with(Techniques.Tada).duration(700).playOn(state);
            ticket7LC.setState(true);
        } else {
            state.setVisibility(View.GONE);
            ticket7LC.setState(false);
        }
        
        helper.setText(R.id.ii_shopping_item_money, String.format("共%s元", ticket7LC.getNum() * 2))
                .setText(R.id.ii_shopping_item_zhushu, String.format("%s注", ticket7LC.getNum()));
    }
}