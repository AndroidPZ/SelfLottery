package zhcw.lottery.znzd.com.selflottery.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lzy.okgo.model.Response;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.http.HttpUtils;
import zhcw.lottery.znzd.com.selflottery.http.callback.StringDialogCallback;
import zhcw.lottery.znzd.com.selflottery.ready_entity.bean_info.UserInfo;
import zhcw.lottery.znzd.com.selflottery.config.Config;
import zhcw.lottery.znzd.com.selflottery.util.Debugger;
import zhcw.lottery.znzd.com.selflottery.util.Logger;
import zhcw.lottery.znzd.com.selflottery.util.ToastUtil;
import zhcw.lottery.znzd.com.selflottery.widgets.DialogTask.BottomDialog;
import zhcw.lottery.znzd.com.selflottery.widgets.ViewBase;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_entry);
        api = WXAPIFactory.createWXAPI(this, Config.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {

        int type = baseResp.getType();
        Logger.i("微信回调类型: " + type);

        if (type == ConstantsAPI.COMMAND_PAY_BY_WX) { //baseResp instanceof PayResp微信支付
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK: //支付成功
                    Logger.i("微信支付成功");
                    sendQrder();
                    break;
                case BaseResp.ErrCode.ERR_COMM://支付错误
                    ViewBase.showPayDialog(WXPayEntryActivity.this, "支付失败", BottomDialog.ERROR_TYPE);
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL://支付取消
                    ViewBase.showPayDialog(WXPayEntryActivity.this, "支付取消", BottomDialog.WARNING_TYPE);
                    break;
                default:
                    ToastUtil.showShortToast("未知原因");
                    finish();
                    break;
            }
        }
    }

    private void sendQrder() {
        StringDialogCallback stringDialogCallback = new StringDialogCallback(this) {
            @Override
            public void onSuccess(String json) {
                ViewBase.showPayDialog(WXPayEntryActivity.this, "支付成功", BottomDialog.SUCCESS_TYPE);
            }

            @Override
            public void onMessage(String message) {
                ToastUtil.showShortToast(message);
                finish();
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Debugger.handleError(response);
                finish();
            }
        };

        HttpUtils.getQueryOrder(this,
                UserInfo.getToken(), UserInfo.getOut_trade_no(), stringDialogCallback);
    }
}
