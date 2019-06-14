package zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity;

import java.util.ArrayList;
import java.util.List;

import zhcw.lottery.znzd.com.selflottery.util.Logger;

/**
 * Created by Administrator on 2016/3/5.
 */

//购物车 3D
public class ShoppingCartSSC {
    private static ShoppingCartSSC instance = new ShoppingCartSSC();

    private ShoppingCartSSC() {
    }

    public static ShoppingCartSSC getInstance() {
        return instance;
    }
    // lotteryid string * 玩法编号
    // issue string * 期号（当前销售期）
    // lotterycode string * 投注号码，注与注之间^分割
    // lotterynumber string 注数
    // lotteryvalue string 方案金额，以分为单位

    // appnumbers string 倍数
    // issuesnumbers string 追期
    // issueflag int * 是否多期追号 0否，1多期
    // bonusstop int * 中奖后是否停止：0不停，1停


    private String issue;//期号（当前销售期）
    private String lotteryid;//玩法编号


    public String getLotteryid() {
        return lotteryid;
    }

    public void setLotteryid(String lotteryid) {
        this.lotteryid = lotteryid;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }


    private List<TicketSSC> ticketSSC = new ArrayList<TicketSSC>();//投注号码，注与注之间^分割
    private Integer lotterynumber;// 计算，注数
    private Integer lotteryvalue;//方案金额，以分为单位


    public List<TicketSSC> getTicketSSC() {
        return ticketSSC;
    }

    /**
     * @return 金额
     */
    public Integer getLotteryvalue() {
        lotteryvalue = 2 * getLotterynumber(); //注数乘以2 为 金额数目
        return lotteryvalue;
    }

    /**
     * @return 注数
     */
    public Integer getLotterynumber() {
        lotterynumber = 0;
        for (TicketSSC item : ticketSSC) {
            lotterynumber += item.getNum();
        }
        return lotterynumber;
    }

    private Integer appnumbers = 1;
    private Integer issuesnumbers = 1;

    /**
     * 操作倍数
     */
    public boolean addAppnumbers(boolean isAdd) {
        if (isAdd) {
            appnumbers++;
            Logger.i(appnumbers + "-1");
            if (appnumbers > 50) {
                appnumbers--;
                return false;
            }

    /*        if (getLotteryvalue() > GlobalParams.MONEY) {
                appnumbers--;
                return false;
            }*/
        } else {
            appnumbers--;
            if (appnumbers == 0) {
                appnumbers++;
                return false;
            }
        }
        return true;
    }

    /**
     * 操作追期
     */
    public boolean addIssuesnumbers(boolean isAdd) {
        if (isAdd) {
            issuesnumbers++;
            if (issuesnumbers > 99) {
                issuesnumbers--;
                return false;
            }

           /* if (getLotteryvalue() > GlobalParams.MONEY) {
                issuesnumbers--;
                return false;
            }*/
        } else {
            issuesnumbers--;
            if (issuesnumbers == 0) {
                issuesnumbers++;
                return false;
            }
        }
        return true;
    }

    public void clear() {
        ticketSSC.clear();
        lotterynumber = 0;
        lotteryvalue = 0;

        appnumbers = 1;
        issuesnumbers = 1;
    }

    public Integer getAppnumbers() {
        return appnumbers;
    }

    public void setAppnumbers(Integer appnumbers) {
        this.appnumbers = appnumbers;
    }

    public Integer getIssuesnumbers() {
        return issuesnumbers;
    }
}
