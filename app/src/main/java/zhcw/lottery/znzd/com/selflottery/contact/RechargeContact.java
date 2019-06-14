package zhcw.lottery.znzd.com.selflottery.contact;


import zhcw.lottery.znzd.com.selflottery.base.BasePresenter;
import zhcw.lottery.znzd.com.selflottery.base.BaseView;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.Recharge_Info;

/**
 * Created by xpz on 2018/10/8.
 */

public interface RechargeContact {

    interface IRechargeView extends BaseView {
        void setSucessData(Recharge_Info lodeData);

        void setDefaultMessage(String message);
    }

    interface RechargePresenter extends BasePresenter {
        void getUnifiedOrderRequest(String token, int Amount, String ip); //充值下单
    }

}
