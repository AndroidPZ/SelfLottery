package zhcw.lottery.znzd.com.selflottery.ready_entity.send_json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/5.
 */
//用户投注信息封装
public class TicketService {

    /**
     * 彩种
     */
    private String lotteryType;//彩种
    /**
     * 倍数
     */
    private String multiple; //倍数

    /**
     * 总注数
     */
    private String orderStake; //总注数

    /**
     * 倍金额
     */
    private String orderAmount; //总金额
    /**
     * 号码信息的数组
     */
    private List<NumberInfo> lotterys;//号码信息


    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getMultiple() {
        return multiple == null ? "" : multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    public String getOrderStake() {
        return orderStake == null ? "" : orderStake;
    }

    public void setOrderStake(String orderStake) {
        this.orderStake = orderStake;
    }

    public String getOrderAmount() {
        return orderAmount == null ? "" : orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public List<NumberInfo> getLotterys() {
        if (lotterys == null) {
            return new ArrayList<>();
        }
        return lotterys;
    }

    public void setLotterys(List<NumberInfo> lotterys) {
        this.lotterys = lotterys;
    }

    @Override
    public String toString() {
        return "TicketService{" +
                "lotteryType=" + lotteryType +
                ", orderStake='" + orderStake + '\'' +
                ", multiple='" + multiple + '\'' +
                ", lotterys=" + lotterys +
                '}';
    }
}
