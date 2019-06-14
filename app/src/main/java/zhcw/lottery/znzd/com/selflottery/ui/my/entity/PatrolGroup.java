package zhcw.lottery.znzd.com.selflottery.ui.my.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import static zhcw.lottery.znzd.com.selflottery.ui.my.adapter.DetailAdapter.TYPE_LEVEL_0;

/**
 * 作者：XPZ on 2018/4/27 15:35.
 */
public class PatrolGroup extends AbstractExpandableItem<ChildDetail> implements MultiItemEntity {

    public String orderNum;//订单号
    public String orderTime;//下单时间
    public String qrcodeStr;//二维码字符串
    public String lotteryType;//票种
    public int multiple;//倍数
    public int orderStake;//总注数
    public int orderAmount;//订单总金额
    public int allPrint;//订单打印状态 0有未打印: 1已全打印
    private String issue;//期号
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public String toString() {
        return "PatrolGroup{" +
                "orderNum='" + orderNum + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", qrcodeStr='" + qrcodeStr + '\'' +
                ", lotteryType='" + lotteryType + '\'' +
                ", multiple=" + multiple +
                ", orderStake=" + orderStake +
                ", orderAmount=" + orderAmount +
                ", allPrint=" + allPrint +
                ", issue='" + issue + '\'' +
                '}';
    }

    public int getAllPrint() {
        return allPrint;
    }

    public void setAllPrint(int allPrint) {
        this.allPrint = allPrint;
    }

    public String getIssue() {
        return issue == null ? "" : issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getOrderNum() {
        return orderNum == null ? "" : orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderTime() {
        return orderTime == null ? "" : orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getQrcodeStr() {
        return qrcodeStr == null ? "" : qrcodeStr;
    }

    public void setQrcodeStr(String qrcodeStr) {
        this.qrcodeStr = qrcodeStr;
    }

    public String getLotteryType() {
        return lotteryType == null ? "" : lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public int getOrderStake() {
        return orderStake;
    }

    public void setOrderStake(int orderStake) {
        this.orderStake = orderStake;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return TYPE_LEVEL_0;
    }
}
