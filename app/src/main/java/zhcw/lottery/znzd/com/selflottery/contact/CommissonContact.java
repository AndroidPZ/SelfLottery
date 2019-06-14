package zhcw.lottery.znzd.com.selflottery.contact;


import zhcw.lottery.znzd.com.selflottery.base.BasePresenter;
import zhcw.lottery.znzd.com.selflottery.base.BaseView;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.Commisson_Info;

/**
 * Created by xpz on 2018/10/15.
 */

public interface CommissonContact {

    interface ICommissonView extends BaseView {
        void setSucessLodeData(Commisson_Info lodeData);

        void setDefaultMessage(String message);
    }

    interface CommissionPresenter extends BasePresenter {
        void getSaleRecordRequest(String token); //余额

        void getApplyRequest(String token); //获取账户明细
    }
}
