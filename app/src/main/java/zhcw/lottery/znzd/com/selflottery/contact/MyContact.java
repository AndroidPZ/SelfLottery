package zhcw.lottery.znzd.com.selflottery.contact;


import zhcw.lottery.znzd.com.selflottery.base.BasePresenter;
import zhcw.lottery.znzd.com.selflottery.base.BaseView;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.User_Info;

/**
 * Created by xpz on 2018/10/8.
 */

public interface MyContact {

    interface IMyView extends BaseView {
        void setDefaultSucess(User_Info.UserBean entity);
        void setDefaultError(String message);
    }

    interface MyPresenter extends BasePresenter {
        void getDefaultDataRequest(String token);
    }
}
