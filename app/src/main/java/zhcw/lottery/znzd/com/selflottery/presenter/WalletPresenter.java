package zhcw.lottery.znzd.com.selflottery.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import zhcw.lottery.znzd.com.selflottery.base.BasePresenterImpl;
import zhcw.lottery.znzd.com.selflottery.config.Config;
import zhcw.lottery.znzd.com.selflottery.contact.WalletContact;
import zhcw.lottery.znzd.com.selflottery.http.HttpUtils;
import zhcw.lottery.znzd.com.selflottery.http.callback.AppStringCallback;
import zhcw.lottery.znzd.com.selflottery.http.callback.StringDialogCallback;
import zhcw.lottery.znzd.com.selflottery.ready_entity.bean_info.UserInfo;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.Wallet_Info;
import zhcw.lottery.znzd.com.selflottery.util.Debugger;
import zhcw.lottery.znzd.com.selflottery.util.GsonUtil;
import zhcw.lottery.znzd.com.selflottery.util.Logger;
import zhcw.lottery.znzd.com.selflottery.util.PreferenceUtil;

/**
 * Created by xpz on 2018/10/16.
 */

public class WalletPresenter extends BasePresenterImpl<WalletContact.IWalletView>
        implements WalletContact.WalletPresenter {
    private Context context;

    public WalletPresenter(Context context, WalletContact.IWalletView iWalletView) {
        this.context = context;
        attachView(iWalletView);
    }

    @Override
    public void getGetAccountRequest(String token) {
        // 请求余额
        HttpUtils.GetAccount(context, token, new AppStringCallback(context) {
            @Override
            public void onSuccess(String json) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(json);
                    String account = jsonObject.getString("account");
                    Logger.i("当前余额account: " + Float.valueOf(account));
                    PreferenceUtil.getInstance().putPreferences(Config.MONEY, Float.valueOf(account));
                    mView.setAccount(Float.valueOf(account));
                    UserInfo.refresh();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Response<String> response) {
                Debugger.handleError(response);
            }

            @Override
            public void onMessage(String message) {
            }
        });
    }

    @Override
    public void getDetailRequestRequest(String token, int start) {
        // 请求余额
        HttpUtils.getDetailRequest(context, token, start, new StringDialogCallback(context) {
            @Override
            public void onSuccess(String json) {
                Wallet_Info wallet_info = GsonUtil.GsonToBean(json, Wallet_Info.class);
                mView.setSucessLodeData(wallet_info.getList());
            }

            @Override
            public void onMessage(String message) {
                mView.setDefaultMessage(message);
            }

            @Override
            public void onError(Response<String> response) {
                Debugger.handleError(response);
                mView.setDefaultError();
            }
        });
    }

    @Override
    public void getWeAddPreOrder(String token, String amount) {

        StringDialogCallback stringDialogCallback = new StringDialogCallback(context) {
            @Override
            public void onSuccess(String json) {
            }

            @Override
            public void onSuccess_Obj(JSONObject object) {
                super.onSuccess_Obj(object);
                try {
                    mView.setDefaultMessage(object.getString("return_msg"));
                } catch (Exception e) {
                }
            }

            @Override
            public void onMessage(String message) {
                mView.setDefaultMessage(message);
            }
        };

        HttpUtils.getWeAddPreOrder(context, token, amount, stringDialogCallback);
    }
}
