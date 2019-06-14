package zhcw.lottery.znzd.com.selflottery.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;

import zhcw.lottery.znzd.com.selflottery.base.BasePresenterImpl;
import zhcw.lottery.znzd.com.selflottery.contact.RechargeContact;
import zhcw.lottery.znzd.com.selflottery.http.HttpUtils;
import zhcw.lottery.znzd.com.selflottery.http.callback.StringDialogCallback;
import zhcw.lottery.znzd.com.selflottery.ready_entity.send_json.RechargeInfo;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.Recharge_Info;
import zhcw.lottery.znzd.com.selflottery.util.Debugger;
import zhcw.lottery.znzd.com.selflottery.util.GsonUtil;

/**
 * Created by xpz on 2018/10/16.
 */

public class RechargePresenter extends BasePresenterImpl<RechargeContact.IRechargeView> implements RechargeContact.RechargePresenter {
    private Context context;

    public RechargePresenter(Context context, RechargeContact.IRechargeView iRechargeView) {
        this.context = context;
        attachView(iRechargeView);
    }


    @Override
    public void getUnifiedOrderRequest(String token, int Amount, String ip) {

        StringDialogCallback stringDialogCallback = new StringDialogCallback(context) {
            @Override
            public void onSuccess(String json) {
                Recharge_Info recharge_info = GsonUtil.GsonToBean(json, Recharge_Info.class);
                mView.setSucessData(recharge_info);
            }
            @Override
            public void onError(Response<String> response) {
                Debugger.handleError(response);
            }
            @Override
            public void onMessage(String message) {
                mView.setDefaultMessage(message);
            }
        };
        String sendJson = getSendJson(token, Amount, ip);
        HttpUtils.getUnifiedOrder(context, sendJson, stringDialogCallback);
    }

    private String getSendJson(String token, int amount, String ip) {
        RechargeInfo rechargeInfo = new RechargeInfo();
        rechargeInfo.setAmount(amount);
        rechargeInfo.setIp(ip);
        rechargeInfo.setToken(token);
        return GsonUtil.GsonString(rechargeInfo);
    }
}
