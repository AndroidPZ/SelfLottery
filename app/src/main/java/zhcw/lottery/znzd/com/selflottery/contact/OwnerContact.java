package zhcw.lottery.znzd.com.selflottery.contact;


import zhcw.lottery.znzd.com.selflottery.base.BasePresenter;
import zhcw.lottery.znzd.com.selflottery.base.BaseView;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.OwnerUser_Info;

/**
 * Created by xpz on 2018/10/16.
 */

public interface OwnerContact {

    interface IOwnerView extends BaseView {
        void setSucessLodeData(OwnerUser_Info.TerminalBean lodeData);

        void setChangeView(String status);

        void setDefaultMessge(String message);

        void setBindQRcode(String QRcode);
    }

    interface OwnerPresenter extends BasePresenter {

        void getOwnerRequest(String token); //获取业主认证信息

        void getOwnerBindRequest(String token, String name, String stationNum, String idNumber, String phone, String address, String city); //获取业主认证信息

        void getBindQrcodeRequest(String token); //获取业主认证信息
    }

}
