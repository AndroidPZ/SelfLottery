package zhcw.lottery.znzd.com.selflottery.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;

import zhcw.lottery.znzd.com.selflottery.base.BasePresenterImpl;
import zhcw.lottery.znzd.com.selflottery.contact.BonusFgmContact;
import zhcw.lottery.znzd.com.selflottery.http.HttpUtils;
import zhcw.lottery.znzd.com.selflottery.http.callback.StringDialogCallback;
import zhcw.lottery.znzd.com.selflottery.ready_entity.send_json.BonusInfo;
import zhcw.lottery.znzd.com.selflottery.ui.bonus.entity.Bonus_Info;
import zhcw.lottery.znzd.com.selflottery.util.GsonUtil;
import zhcw.lottery.znzd.com.selflottery.util.Logger;

/**
 * Created by xpz on 2018/10/16.
 */

public class BonusFgmPresenter extends BasePresenterImpl<BonusFgmContact.IBonusFgmView>
        implements BonusFgmContact.BonusFgmPresenter {
    private Context context;

    public BonusFgmPresenter(Context context, BonusFgmContact.IBonusFgmView IBonusFgmView) {
        this.context = context;
        attachView(IBonusFgmView);
    }


    @Override
    public void getBonusRechargeRequest(String token, String certiCode) {
        StringDialogCallback callback = new StringDialogCallback(context) {
            @Override
            public void onSuccess(String json) {
                Bonus_Info bonus_info = GsonUtil.GsonToBean(json, Bonus_Info.class);
                mView.setSucessLodeData(bonus_info);
            }

            @Override
            public void onMessage(String message) {
                mView.setDefaultMsg(message);
            }

            @Override
            public void onError(Response<String> response) {
                mView.setDefaultError(response);
            }
        };

        HttpUtils.sendBonusRechargeJson(context, getJson(token, certiCode), callback);
    }

    private String getJson(String token, String certiCode) {
        BonusInfo loginInfo = new BonusInfo();
        loginInfo.setCertiCode(certiCode);
        loginInfo.setToken(token);
        Logger.i(GsonUtil.GsonString(loginInfo));
        return GsonUtil.GsonString(loginInfo);
    }
}
