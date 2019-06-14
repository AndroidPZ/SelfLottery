package zhcw.lottery.znzd.com.selflottery.ready_entity.send_json;

/**
 * 作者：XPZ on 2018/4/26 09:52.
 */
public class NumberInfo {

    /**
     * 玩法
     */
    private String playType;//玩法

    /**
     * 单次注数
     */
    private String lotteryStake; //注数

    /**
     * 金额
     */
    private String lotteryAmount; //金额

    /**
     * 彩票信息
     */
    private String lotteryNum; //彩票信息

    public String getPlayType() {
        return playType == null ? "" : playType;
    }

    public void setPlayType(String playType) {
        this.playType = playType;
    }

    public String getLotteryStake() {
        return lotteryStake == null ? "" : lotteryStake;
    }

    public void setLotteryStake(String lotteryStake) {
        this.lotteryStake = lotteryStake;
    }

    public String getLotteryAmount() {
        return lotteryAmount == null ? "" : lotteryAmount;
    }

    public void setLotteryAmount(String lotteryAmount) {
        this.lotteryAmount = lotteryAmount;
    }

    public String getLotteryNum() {
        return lotteryNum == null ? "" : lotteryNum;
    }

    public void setLotteryNum(String lotteryNum) {
        this.lotteryNum = lotteryNum;
    }

    @Override
    public String toString() {
        return "NumberInfo{" +
                "playType='" + playType + '\'' +
                ", lotteryStake='" + lotteryStake + '\'' +
                ", lotteryAmount='" + lotteryAmount + '\'' +
                ", lotteryNum='" + lotteryNum + '\'' +
                '}';
    }

}
