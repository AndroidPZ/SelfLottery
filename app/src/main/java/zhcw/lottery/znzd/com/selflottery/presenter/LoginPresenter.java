package zhcw.lottery.znzd.com.selflottery.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;

import java.util.SortedMap;
import java.util.TreeMap;

import zhcw.lottery.znzd.com.selflottery.BuildConfig;
import zhcw.lottery.znzd.com.selflottery.base.BasePresenterImpl;
import zhcw.lottery.znzd.com.selflottery.config.Config;
import zhcw.lottery.znzd.com.selflottery.contact.LoginContact;
import zhcw.lottery.znzd.com.selflottery.http.HttpUtils;
import zhcw.lottery.znzd.com.selflottery.http.callback.StringDialogCallback;
import zhcw.lottery.znzd.com.selflottery.ready_entity.bean_info.Login_Info;
import zhcw.lottery.znzd.com.selflottery.ready_entity.bean_info.UserInfo;
import zhcw.lottery.znzd.com.selflottery.ready_entity.send_json.LoginInfo;
import zhcw.lottery.znzd.com.selflottery.util.Debugger;
import zhcw.lottery.znzd.com.selflottery.util.GsonUtil;
import zhcw.lottery.znzd.com.selflottery.util.Logger;
import zhcw.lottery.znzd.com.selflottery.util.PreferenceUtil;
import zhcw.lottery.znzd.com.selflottery.util.SignUtil;

/**
 * Created by GXZ on 2018/10/03.
 */

public class LoginPresenter extends BasePresenterImpl<LoginContact.ILoginView> implements LoginContact.LoginPresenter {
    private Context context;
    SortedMap<String, String> p = new TreeMap<String, String>();

    public LoginPresenter(Context context, LoginContact.ILoginView iLoginView) {
        this.context = context;
        attachView(iLoginView);
    }


    @Override
    public void getSecurityCode(String phone) {

        StringDialogCallback callback = new StringDialogCallback(context) {

            @Override
            public void onSuccess(String json) {
                // 发送验证码成功
                mView.setSecurityCodeSucess();
            }

            @Override
            public void onError(Response<String> response) {
                Debugger.handleError(response);
            }

            @Override
            public void onMessage(String message) {
                mView.setSecurityCodeError(message);
            }
        };

        // 向服务器发送请求
        HttpUtils.getTextCode(context, phone, callback);
    }

    @Override
    public void sendLogin(String phone, String number) {

        StringDialogCallback callback = new StringDialogCallback(context) {
            @Override
            public void onSuccess(String json) {
                Logger.i(json);
                Login_Info login_info = GsonUtil.GsonToBean(json, Login_Info.class);
                Login_Info.UserBean user = login_info.getUser();

                PreferenceUtil.getInstance().putPreferences(Config.TOKEN, login_info.getToken());
                PreferenceUtil.getInstance().putPreferences(Config.SOCKET_IP, login_info.getSocket_ip());
                PreferenceUtil.getInstance().putPreferences(Config.SOCKET_PORT, login_info.getSocket_port());
                PreferenceUtil.getInstance().putPreferences(Config.IS_LOGIN, true);
                PreferenceUtil.getInstance().putPreferences(Config.USERHEADIMGURL, user.getHeadurl());
                PreferenceUtil.getInstance().putPreferences(Config.USER_NAME, user.getNickname());
                PreferenceUtil.getInstance().putPreferences(Config.USER_PHONE, user.getPhone());
                PreferenceUtil.getInstance().putPreferences(Config.ISOWNER, user.getIsOwner());
                PreferenceUtil.getInstance().putPreferences(Config.EMAIL, user.getEmail());
                PreferenceUtil.getInstance().putPreferences(Config.PASSWORD, user.getPassword());
                UserInfo.refresh();
                mView.setLoginSucess();
            }

            @Override
            public void onMessage(String message) {
                mView.setLoginError(message);
            }

            @Override
            public void onError(Response<String> response) {
                Debugger.handleError(response);
            }
        };

        p.clear();
        p.put("phone", phone);
        p.put("code", number);
        HttpUtils.sendLogingJson(context, getJson(phone, number,
                SignUtil.getSign(p, BuildConfig.ORDER_KEY)), callback);
    }

    private String getJson(String strPhone, String strPwd, String Sign) {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setPhone(strPhone);
        loginInfo.setCode(strPwd);
        loginInfo.setSign(Sign);
        Logger.i(GsonUtil.GsonString(loginInfo));
        return GsonUtil.GsonString(loginInfo);
    }
}
