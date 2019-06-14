package zhcw.lottery.znzd.com.selflottery.contact;


import zhcw.lottery.znzd.com.selflottery.base.BasePresenter;
import zhcw.lottery.znzd.com.selflottery.base.BaseView;

/**
 * Created by XPZ on 2018/4/16.
 */

public interface LoginContact {

    interface ILoginView extends BaseView {
        void setSecurityCodeError(String message);

        void setSecurityCodeSucess();

        void setLoginError(String message);

        void setLoginSucess();
    }

    interface LoginPresenter extends BasePresenter {
        void getSecurityCode(String phone);

        void sendLogin(String phone, String number);

    }

}
