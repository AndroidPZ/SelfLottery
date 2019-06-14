package zhcw.lottery.znzd.com.selflottery.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lzy.okgo.model.Response;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
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
import zhcw.lottery.znzd.com.selflottery.util.PreferenceUtil;
import zhcw.lottery.znzd.com.selflottery.util.ToastUtil;
import zhcw.lottery.znzd.com.selflottery.widgets.DialogTask.BottomDialog;
import zhcw.lottery.znzd.com.selflottery.widgets.ViewBase;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

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

        if (type == ConstantsAPI.COMMAND_SENDAUTH) {//授权登录
            //登录授权
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    // 授权成功
                    Logger.i("微信授权成功");
                    String code = ((SendAuth.Resp) baseResp).code;
                    PreferenceUtil.getInstance().putPreferences(Config.WEIXINCODE, code);
                    UserInfo.refresh();
                    sendCode();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    ViewBase.showPayDialog(WXEntryActivity.this, "未被授权", BottomDialog.WARNING_TYPE);
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    // 授权被拒绝
                    ViewBase.showPayDialog(WXEntryActivity.this, "拒绝授权", BottomDialog.ERROR_TYPE);
                    break;
                default:
                    ToastUtil.showShortToast("授权错误");
                    finish();
                    break;
            }
        } else if (type == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {//微信分享
            // 分享
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    ToastUtil.showShortToast("分享成功");
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    ToastUtil.showShortToast("取消分享");
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_SENT_FAILED:
                    ToastUtil.showShortToast("分享失败");
                    finish();
                    break;
                default:
                    ToastUtil.showShortToast("未知原因");
                    finish();
                    break;
            }
        }
    }

    private void sendCode() {
        Logger.i("向后台发送code");
        StringDialogCallback stringDialogCallback = new StringDialogCallback(this) {
            @Override
            public void onSuccess(String json) {
                PreferenceUtil.getInstance().putPreferences(Config.IS_BIND, true);
                UserInfo.refresh();
                ViewBase.showPayDialog(WXEntryActivity.this, "授权成功", BottomDialog.SUCCESS_TYPE);
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
        HttpUtils.getWechatCode(this,
                UserInfo.getToken(), UserInfo.getWeixincode(), stringDialogCallback);
    }

}
