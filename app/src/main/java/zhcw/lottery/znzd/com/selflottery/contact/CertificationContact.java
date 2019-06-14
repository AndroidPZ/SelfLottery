package zhcw.lottery.znzd.com.selflottery.contact;


import java.io.File;

import zhcw.lottery.znzd.com.selflottery.base.BasePresenter;
import zhcw.lottery.znzd.com.selflottery.base.BaseView;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.CertificationEntity;

/**
 * Created by XPZ on 2018/10/3.
 */

public interface CertificationContact {

    interface ICertificationView extends BaseView {
        void setDefaultSucess(CertificationEntity entity);

        void setDefaultError(String message);

        void setCertificationSucess();

        void setCertificationError(String message);

    }

    interface CertificationPresenter extends BasePresenter {
        void getDefaultDataRequest(String token);

        void getCertificationDataRequest(String token, File pic_z, File pic_f);
    }

}
