package zhcw.lottery.znzd.com.selflottery.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import zhcw.lottery.znzd.com.selflottery.base.BasePresenterImpl;
import zhcw.lottery.znzd.com.selflottery.contact.CommissonContact;
import zhcw.lottery.znzd.com.selflottery.http.HttpUtils;
import zhcw.lottery.znzd.com.selflottery.http.callback.StringDialogCallback;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.Commisson_Info;
import zhcw.lottery.znzd.com.selflottery.util.Debugger;
import zhcw.lottery.znzd.com.selflottery.util.GsonUtil;

/**
 * Created by xpz on 2018/10/15.
 */

public class CommissiontPresenter extends BasePresenterImpl<CommissonContact.ICommissonView> implements CommissonContact.CommissionPresenter {
    private Context context;

    public CommissiontPresenter(Context context, CommissonContact.ICommissonView iCommissonView) {
        this.context = context;
        attachView(iCommissonView);
    }

    /**
     * 查询
     *
     * @param token
     */
    @Override
    public void getSaleRecordRequest(String token) {
        StringDialogCallback stringDialogCallback = new StringDialogCallback(context) {
            @Override
            public void onSuccess(String json) {
                Commisson_Info commisson_info = GsonUtil.GsonToBean(json, Commisson_Info.class);
                mView.setSucessLodeData(commisson_info);
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
        HttpUtils.getSaleRecordJson(context, token, stringDialogCallback);
    }

    /**
     * 提现
     *
     * @param token
     */
    @Override
    public void getApplyRequest(String token) {
        StringDialogCallback stringDialogCallback = new StringDialogCallback(context) {
            @Override
            public void onSuccess(String json) {

            }

            @Override
            public void onSuccess_Obj(JSONObject object) {
                super.onSuccess_Obj(object);
                try {
                    String return_msg = (String) object.get("return_msg");
                    mView.setDefaultMessage(return_msg);
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
                mView.setDefaultMessage(message);
            }
        };
        HttpUtils.getApplyJson(context, token, stringDialogCallback);
    }
}
