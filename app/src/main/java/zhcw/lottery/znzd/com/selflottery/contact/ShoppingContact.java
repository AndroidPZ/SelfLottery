package zhcw.lottery.znzd.com.selflottery.contact;


import zhcw.lottery.znzd.com.selflottery.base.BasePresenter;
import zhcw.lottery.znzd.com.selflottery.base.BaseView;

/**
 * Created by xpz on 2018/10/8.
 */

public interface ShoppingContact {

    interface IShoppingView extends BaseView {
        void setSucessLottery(String qrcode, Integer number, Integer money);

        void setAccount(Float many);

        void setDefaultMsg(String message);

        void setStatus(int status);
    }

    interface IShoppingPresenter extends BasePresenter {
        void getAccountRequest(String token); //余额

        void sendLotteryJsonRequest(); //获取账户明细

        void getJiangQiRequest(String type); //获取奖期
    }

}
