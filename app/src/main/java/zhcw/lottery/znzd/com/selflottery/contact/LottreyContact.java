package zhcw.lottery.znzd.com.selflottery.contact;


import java.util.List;

import zhcw.lottery.znzd.com.selflottery.base.BasePresenter;
import zhcw.lottery.znzd.com.selflottery.base.BaseView;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.entity.BannerInfo;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.entity.JiangQiInfo;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.entity.UpDataInfo;

/**
 * Created by xpz on 2018/10/8.
 */

public interface LottreyContact {

    interface ILotteryFgmView extends BaseView {
        void setSucessLodeData(JiangQiInfo lodeData);
        void setDefaultMessage(String message);
        void setBannerData( List<BannerInfo.BannerBean> bannerInfo);
        void setVersionUpdate(UpDataInfo.DataBean message);
    }

    interface LotteryPresenter extends BasePresenter {
        void getJiangQiRequest(String type); //获取奖期
        void getBannerDataRequest(int cityId);
        void getVersionUpdateRequest(); //获取版本
    }

}
