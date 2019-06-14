package zhcw.lottery.znzd.com.selflottery.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;

import zhcw.lottery.znzd.com.selflottery.base.BasePresenterImpl;
import zhcw.lottery.znzd.com.selflottery.config.Config;
import zhcw.lottery.znzd.com.selflottery.contact.MyContact;
import zhcw.lottery.znzd.com.selflottery.http.HttpUtils;
import zhcw.lottery.znzd.com.selflottery.http.callback.StringDialogCallback;
import zhcw.lottery.znzd.com.selflottery.ready_entity.bean_info.UserInfo;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.User_Info;
import zhcw.lottery.znzd.com.selflottery.util.Debugger;
import zhcw.lottery.znzd.com.selflottery.util.GsonUtil;
import zhcw.lottery.znzd.com.selflottery.util.Logger;
import zhcw.lottery.znzd.com.selflottery.util.PreferenceUtil;

/**
 * Created by xpz on 2018/10/15.
 * 我的
 */

public class MyPresenter extends BasePresenterImpl<MyContact.IMyView> implements MyContact.MyPresenter {
    private Context context;

    public MyPresenter(Context context, MyContact.IMyView iMyView) {
        this.context = context;
        attachView(iMyView);
    }

    @Override
    public void getDefaultDataRequest(String token) {

        StringDialogCallback callback = new StringDialogCallback(context) {

            @Override
            public void onSuccess(String json) {
                Logger.i(json);
                User_Info bean = GsonUtil.GsonToBean(json, User_Info.class);
                PreferenceUtil.getInstance().putPreferences(Config.SOCKET_IP, bean.getSocket_ip());
                PreferenceUtil.getInstance().putPreferences(Config.SOCKET_PORT, bean.getSocket_port());
                PreferenceUtil.getInstance().putPreferences(Config.AUDIT, bean.getUser().getIsReal());
                PreferenceUtil.getInstance().putPreferences(Config.ISOWNER, bean.getUser().getIsOwner());
                PreferenceUtil.getInstance().putPreferences(Config.IS_BIND, bean.getUser().getIsAuth() == 1);
                UserInfo.refresh();
                mView.setDefaultSucess(bean.getUser());
            }

            @Override
            public void onMessage(String message) {
                mView.setDefaultError(message);
            }

            @Override
            public void onError(Response<String> response) {
                Debugger.handleError(response);
            }
        };
        HttpUtils.getUserInfo(context, token, callback);
    }
}
