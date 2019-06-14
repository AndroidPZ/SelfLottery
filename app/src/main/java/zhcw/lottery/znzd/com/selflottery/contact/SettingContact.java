package zhcw.lottery.znzd.com.selflottery.contact;


import zhcw.lottery.znzd.com.selflottery.base.BasePresenter;
import zhcw.lottery.znzd.com.selflottery.base.BaseView;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.entity.UpDataInfo;

/**
 * Created by xpz on 2019/1/7.
 */

public interface SettingContact {

    interface ISetting extends BaseView {
        void setVersionUpdate(UpDataInfo.DataBean message);
    }

    interface SettingPresenter extends BasePresenter {
        void getVersionUpdateRequest(); //获取版本
    }

}
