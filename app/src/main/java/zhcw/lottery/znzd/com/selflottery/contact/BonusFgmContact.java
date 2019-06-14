package zhcw.lottery.znzd.com.selflottery.contact;


import com.lzy.okgo.model.Response;

import zhcw.lottery.znzd.com.selflottery.base.BasePresenter;
import zhcw.lottery.znzd.com.selflottery.base.BaseView;
import zhcw.lottery.znzd.com.selflottery.ui.bonus.entity.Bonus_Info;

/**
 * @author xpz
 * @date 2018/10/27
 */

public interface BonusFgmContact {

    interface IBonusFgmView extends BaseView {
        void setSucessLodeData(Bonus_Info lodeData);

        void setDefaultMsg(String message);

        void setDefaultError(Response<String> response);
    }


    interface BonusFgmPresenter extends BasePresenter {
        void getBonusRechargeRequest(String token, String certiCode); //兑奖
    }

}
