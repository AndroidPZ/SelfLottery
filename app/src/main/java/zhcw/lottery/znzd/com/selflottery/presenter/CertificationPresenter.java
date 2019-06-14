package zhcw.lottery.znzd.com.selflottery.presenter;

import android.app.Activity;
import android.content.Context;

import com.lzy.okgo.model.Response;

import java.io.File;

import zhcw.lottery.znzd.com.selflottery.base.BasePresenterImpl;
import zhcw.lottery.znzd.com.selflottery.contact.CertificationContact;
import zhcw.lottery.znzd.com.selflottery.http.HttpUtils;
import zhcw.lottery.znzd.com.selflottery.http.callback.StringDialogCallback;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.CertificationEntity;
import zhcw.lottery.znzd.com.selflottery.util.Debugger;
import zhcw.lottery.znzd.com.selflottery.util.GsonUtil;
import zhcw.lottery.znzd.com.selflottery.util.Logger;

/**
 * Created by xpz on 2018/10/14.
 * 实名
 */

public class CertificationPresenter extends BasePresenterImpl<CertificationContact.ICertificationView>
        implements CertificationContact.CertificationPresenter {
    private Context context;

    public CertificationPresenter(Context context, CertificationContact.ICertificationView iCertificationView) {
        this.context = context;
        attachView(iCertificationView);
    }

    @Override
    public void getDefaultDataRequest(String token) {
        StringDialogCallback callback=new StringDialogCallback((Activity) context) {
            
            @Override
            public void onSuccess(String json) {
                CertificationEntity bean = GsonUtil.GsonToBean(json, CertificationEntity.class);
                mView.setDefaultSucess(bean);
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
        HttpUtils.getDefaultDataRequest(context,token,callback);
    }

    @Override
    public void getCertificationDataRequest(String token, final File pic_z, final File pic_f) {
        StringDialogCallback callback=new StringDialogCallback((Activity) context) {
            @Override
            public void onSuccess(String json) {
                Logger.i("xpz","实名认证数据"+json);
                mView.setCertificationSucess();
            }

            @Override
            public void onMessage(String message) {
                mView.setCertificationError(message);
            }

            @Override
            public void onError(Response<String> response) {
                Debugger.handleError(response);
            }
        };
        HttpUtils.getCertificationDataRequest(context,token,pic_z,pic_f,callback);
    }
}
