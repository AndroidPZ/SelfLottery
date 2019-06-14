package zhcw.lottery.znzd.com.selflottery.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import zhcw.lottery.znzd.com.selflottery.base.BasePresenterImpl;
import zhcw.lottery.znzd.com.selflottery.contact.OwnerContact;
import zhcw.lottery.znzd.com.selflottery.http.HttpUtils;
import zhcw.lottery.znzd.com.selflottery.http.callback.StringDialogCallback;
import zhcw.lottery.znzd.com.selflottery.ready_entity.send_json.OwnerApplyInfo;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.OwnerUser_Info;
import zhcw.lottery.znzd.com.selflottery.util.Debugger;
import zhcw.lottery.znzd.com.selflottery.util.GsonUtil;
import zhcw.lottery.znzd.com.selflottery.util.Logger;

/**
 * Created by xpz on 2018/10/16.
 */

public class OwnerPresenter extends BasePresenterImpl<OwnerContact.IOwnerView> implements OwnerContact.OwnerPresenter {
    private Context context;

    public OwnerPresenter(Context context, OwnerContact.IOwnerView iOwnertView) {
        this.context = context;
        attachView(iOwnertView);
    }

    @Override
    public void getOwnerRequest(String token) {

        StringDialogCallback stringDialogCallback = new StringDialogCallback(context) {
            @Override
            public void onSuccess(String json) {
                OwnerUser_Info ownerUser_info = GsonUtil.GsonToBean(json, OwnerUser_Info.class);
                OwnerUser_Info.TerminalBean terminal = ownerUser_info.getTerminal();
                mView.setChangeView(terminal.getStatus() + "");
                mView.setSucessLodeData(terminal);
            }
            @Override
            public void onError(Response<String> response) {
                Debugger.handleError(response);
            }
            @Override
            public void onMessage(String message, String state) {
                mView.setChangeView(state);
            }
        };

        HttpUtils.getOwnerJson(context, token, stringDialogCallback);

    }

    @Override
    public void getOwnerBindRequest(String token, String name, String stationNum, String idNumber, String phone, 
                                    String address , String city) {
        StringDialogCallback stringDialogCallback = new StringDialogCallback(context) {
            @Override
            public void onSuccess(String json) {

            }

            @Override
            public void onSuccess_Obj(JSONObject object) {
                super.onSuccess_Obj(object);
                try {
                    String return_msg = object.getString("return_msg");
                    mView.setChangeView("Enabled");
                    mView.setDefaultMessge(return_msg);
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
                mView.setDefaultMessge(message);
            }
        };
        String jsonData = getJsonData(token, name, stationNum, idNumber, phone, address ,city);
        Logger.i("XPZ_提交业主认证", jsonData);
        HttpUtils.getOwnerBindJson(context, jsonData, stringDialogCallback);

    }

    @Override
    public void getBindQrcodeRequest(String token) {
        StringDialogCallback stringDialogCallback = new StringDialogCallback(context) {
            @Override
            public void onSuccess(String json) {

            }

            @Override
            public void onSuccess_Obj(JSONObject object) {
                try {
                    String qrcode = (String) object.get("qrcode");
                    mView.setBindQRcode(qrcode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess_Obj(object);

            }
            @Override
            public void onError(Response<String> response) {
                Debugger.handleError(response);
            }
            @Override
            public void onMessage(String message) {
                mView.setDefaultMessge(message);
            }
        };
        HttpUtils.getBindQrcode(context, token, stringDialogCallback);
    }

    /**
     * 准备提交数据
     *
     * @param token
     * @param name
     * @param stationNum
     * @param idNumber
     * @param phone
     * @param address
     * @return
     */
    private String getJsonData(String token, String name, String stationNum, String idNumber, String phone, String address , String city) {

        OwnerApplyInfo ownerApplyInfo = new OwnerApplyInfo();
        ownerApplyInfo.setToken(TextUtils.isEmpty(token) ? "" : token);
        ownerApplyInfo.setCity(TextUtils.isEmpty(city) ? "" : city);
        ownerApplyInfo.setOwnerName(TextUtils.isEmpty(name) ? "" : name);
        ownerApplyInfo.setStationNum(TextUtils.isEmpty(stationNum) ? "" : stationNum);
        ownerApplyInfo.setIdNumber(TextUtils.isEmpty(idNumber) ? "" : idNumber);
        ownerApplyInfo.setStationPhone(TextUtils.isEmpty(phone) ? "" : phone);
        ownerApplyInfo.setStationAddress(TextUtils.isEmpty(address) ? "" : address);

        return GsonUtil.GsonString(ownerApplyInfo);
    }
}
