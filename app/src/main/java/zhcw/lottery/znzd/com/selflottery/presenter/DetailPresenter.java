package zhcw.lottery.znzd.com.selflottery.presenter;

import android.content.Context;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;

import zhcw.lottery.znzd.com.selflottery.base.BasePresenterImpl;
import zhcw.lottery.znzd.com.selflottery.contact.DetailContact;
import zhcw.lottery.znzd.com.selflottery.http.HttpUtils;
import zhcw.lottery.znzd.com.selflottery.http.callback.StringDialogCallback;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.Detail_Info;
import zhcw.lottery.znzd.com.selflottery.util.Debugger;
import zhcw.lottery.znzd.com.selflottery.util.GsonUtil;
import zhcw.lottery.znzd.com.selflottery.util.ParesJson;

/**
 * Created by xpz on 2018/10/16.
 */

public class DetailPresenter extends BasePresenterImpl<DetailContact.IDetailView> implements DetailContact.DetailPresenter {
    private Context context;

    public DetailPresenter(Context context, DetailContact.IDetailView IDetailView) {
        this.context = context;
        attachView(IDetailView);
    }

    @Override
    public void getSendDetailsJson(String token) {
        StringDialogCallback callback = new StringDialogCallback(context) {

            @Override
            public void onSuccess(String Json) {
                Detail_Info detail_info = GsonUtil.GsonToBean(Json, Detail_Info.class);
                ArrayList<MultiItemEntity>
                        detailInforResult = ParesJson.getDetailInforResult_new(detail_info);
                mView.setSucessLodeData(detailInforResult);
            }

            @Override
            public void onError(Response<String> response) {
                mView.setDefaultErrorView();
                Debugger.handleError(response);
            }

            @Override
            public void onMessage(String message) {
                mView.setDefaultError(message);
            }


        };
        HttpUtils.sendDetailsJson(context, token, callback);
    }

    @Override
    public void deleteOrderJson(String token, String orderNums) {
        StringDialogCallback callback = new StringDialogCallback(context) {

            @Override
            public void onSuccess(String Json) {
                mView.setDeleteOrderView();
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

        HttpUtils.deleteOrder(context, token, orderNums, callback);
    }
}
