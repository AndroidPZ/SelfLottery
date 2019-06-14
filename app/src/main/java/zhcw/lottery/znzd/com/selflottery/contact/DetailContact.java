package zhcw.lottery.znzd.com.selflottery.contact;


import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

import zhcw.lottery.znzd.com.selflottery.base.BasePresenter;
import zhcw.lottery.znzd.com.selflottery.base.BaseView;

/**
 * Created by xpz on 2018/10/8.
 */

public interface DetailContact {

    interface IDetailView extends BaseView {
        void setSucessLodeData(ArrayList<MultiItemEntity> lodeData);

        void setDefaultError(String message);

        void setDefaultErrorView();

        void setDeleteOrderView();
    }

    interface DetailPresenter extends BasePresenter {
        void getSendDetailsJson(String token); //余额

        void deleteOrderJson(String token, String orderNums); //删除订单
    }

}
