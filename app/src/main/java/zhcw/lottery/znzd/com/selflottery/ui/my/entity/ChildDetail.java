package zhcw.lottery.znzd.com.selflottery.ui.my.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import static zhcw.lottery.znzd.com.selflottery.ui.my.adapter.DetailAdapter.TYPE_LEVEL_1;

/**
 * 作者：XPZ on 2018/4/27 15:51.
 */
public class ChildDetail implements MultiItemEntity {

    /*
                  "playType":"SSQ_S",		//玩法
				"lotteryNum":"01-02-03-04-05-06^10",	//号码
				"lotteryStake":1,		//注数
				"lotteryAmount":2		//金额
    * */
    public String playType;//玩法
    public String lotteryNum;//号码
    public int lotteryStake;//注数
    public int lotteryAmount;//金额
    public int printStatus;//出票状态 0: 未出 , 1: 已出票
    //自定义的
    public String NumRed;//公共红球
    public String NumBlue;//公共篮球

    @Override
    public String toString() {
        return "ChildDetail{" +
                "playType='" + playType + '\'' +
                ", lotteryNum='" + lotteryNum + '\'' +
                ", lotteryStake=" + lotteryStake +
                ", lotteryAmount=" + lotteryAmount +
                ", printStatus=" + printStatus +
                ", NumRed='" + NumRed + '\'' +
                ", NumBlue='" + NumBlue + '\'' +
                '}';
    }

    public String getNumRed() {
        return NumRed == null ? "" : NumRed;
    }

    public void setNumRed(String numRed) {
        NumRed = numRed;
    }

    public String getNumBlue() {
        return NumBlue == null ? "" : NumBlue;
    }

    public void setNumBlue(String numBlue) {
        NumBlue = numBlue;
    }


    public int getPrintStatus() {
        return printStatus;
    }

    public void setPrintStatus(int printStatus) {
        this.printStatus = printStatus;
    }


    public String getPlayType() {
        return playType == null ? "" : playType;
    }

    public void setPlayType(String playType) {
        this.playType = playType;
    }

    public String getLotteryNum() {
        return lotteryNum == null ? "" : lotteryNum;
    }

    public void setLotteryNum(String lotteryNum) {
        this.lotteryNum = lotteryNum;
    }

    public int getLotteryStake() {
        return lotteryStake;
    }

    public void setLotteryStake(int lotteryStake) {
        this.lotteryStake = lotteryStake;
    }

    public int getLotteryAmount() {
        return lotteryAmount;
    }

    public void setLotteryAmount(int lotteryAmount) {
        this.lotteryAmount = lotteryAmount;
    }

    @Override
    public int getItemType() {
        return TYPE_LEVEL_1;
    }
}
