package zhcw.lottery.znzd.com.selflottery.ready_entity.send_json;

/**
 * 作者：XPZ on 2018/4/26 17:35.
 */
public class OrderEntity {

    public String getToken() {
        return token == null ? "" : token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TicketService getOrder() {
        return order;
    }

    public void setOrder(TicketService order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "token='" + token + '\'' +
                ", order=" + order +
                '}';
    }

    /**
     * 用户校验码
     */
    private String token;
    /**
     * 封装彩票订单
     */
    private TicketService order;


}
