package zhcw.lottery.znzd.com.selflottery.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;

import zhcw.lottery.znzd.com.selflottery.base.BasePresenterImpl;
import zhcw.lottery.znzd.com.selflottery.contact.SettingContact;
import zhcw.lottery.znzd.com.selflottery.http.HttpUtils;
import zhcw.lottery.znzd.com.selflottery.http.callback.AppStringCallback;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.entity.UpDataInfo;
import zhcw.lottery.znzd.com.selflottery.util.Debugger;
import zhcw.lottery.znzd.com.selflottery.util.GsonUtil;

/**
 * Created by xpz on 2019/1/7.
 */

public class SettingPresenter extends BasePresenterImpl<SettingContact.ISetting>
        implements SettingContact.SettingPresenter {
    private Context context;

    public SettingPresenter(Context context, SettingContact.ISetting ISetting) {
        this.context = context;
        attachView(ISetting);
    }


    @Override
    public void getVersionUpdateRequest() {

        HttpUtils.getVersionUpdateRequest(context, new AppStringCallback(context) {
            @Override
            public void onSuccess(String json) {
                UpDataInfo upDataInfo = GsonUtil.GsonToBean(json, UpDataInfo.class);
                mView.setVersionUpdate(upDataInfo.getData());
            }

            @Override
            public void onError(Response<String> response) {
                Debugger.handleError(response);
            }
        });
    }
}
