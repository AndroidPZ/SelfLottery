package zhcw.lottery.znzd.com.selflottery.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;

import java.util.List;

import zhcw.lottery.znzd.com.selflottery.base.BasePresenterImpl;
import zhcw.lottery.znzd.com.selflottery.contact.LottreyContact;
import zhcw.lottery.znzd.com.selflottery.http.HttpUtils;
import zhcw.lottery.znzd.com.selflottery.http.callback.AppStringCallback;
import zhcw.lottery.znzd.com.selflottery.http.callback.StringDialogCallback;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.entity.BannerInfo;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.entity.JiangQiInfo;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.entity.UpDataInfo;
import zhcw.lottery.znzd.com.selflottery.util.Debugger;
import zhcw.lottery.znzd.com.selflottery.util.GsonUtil;

/**
 * Created by xpz on 2018/10/16.
 */

public class LotteryPresenter extends BasePresenterImpl<LottreyContact.ILotteryFgmView>
        implements LottreyContact.LotteryPresenter {
    private Context context;

    public LotteryPresenter(Context context, LottreyContact.ILotteryFgmView ILotteryFgmView) {
        this.context = context;
        attachView(ILotteryFgmView);
    }

    @Override
    public void getJiangQiRequest(String type) {
        // 请求奖期
        HttpUtils.getJiangQi(context, type, new StringDialogCallback(context) {

            @Override
            public void onSuccess(String json) {
                JiangQiInfo jiangQiInfo = GsonUtil.GsonToBean(json, JiangQiInfo.class);
                mView.setSucessLodeData(jiangQiInfo);
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
    public void getBannerDataRequest(int cityId) {
        //  banner图
        StringDialogCallback callback = new StringDialogCallback(context) {
            @Override
            public void onError(Response<String> response) {
                Debugger.handleError(response);
            }

            @Override
            public void onSuccess(String json) {
                BannerInfo bannerInfo = GsonUtil.GsonToBean(json, BannerInfo.class);
                List<BannerInfo.BannerBean> banner = bannerInfo.getBanner();
                mView.setBannerData(banner);
            }

            @Override
            public void onMessage(String message) {
            }
        };
        HttpUtils.getBannerDataRequest(context, cityId, callback);
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
