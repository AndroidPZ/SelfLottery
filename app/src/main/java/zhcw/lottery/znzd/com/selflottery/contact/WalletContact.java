package zhcw.lottery.znzd.com.selflottery.contact;


import java.util.List;

import zhcw.lottery.znzd.com.selflottery.base.BasePresenter;
import zhcw.lottery.znzd.com.selflottery.base.BaseView;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.Wallet_Info;

/**
 * Created by xpz on 2019/1/7.
 */

public interface WalletContact {

    interface IWalletView extends BaseView {
        void setSucessLodeData(List<Wallet_Info.ListBean> lodeData);
        void setAccount(Float many);
        void setDefaultMessage(String message);
        void setDefaultError();
    }

    interface WalletPresenter extends BasePresenter {
        void getGetAccountRequest(String token); //余额

        void getDetailRequestRequest(String token, int start); //获取账户明细

        void getWeAddPreOrder(String token, String amount); //提现
    }

}
