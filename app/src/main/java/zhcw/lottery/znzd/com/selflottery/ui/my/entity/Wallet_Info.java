package zhcw.lottery.znzd.com.selflottery.ui.my.entity;

import java.util.List;

/**
 * 作者：XPZ on 2018/10/15 15:21.
 */
public class Wallet_Info {


    /**
     * return_code : SUCCESS
     * return_msg : 成功
     * list : [{"balanceFrom":"彩票购买","balance":"2.00","balanceType":"1","createTime":"2018-10-05 21:11:02"},{"balanceFrom":"彩票购买","balance":"10.00","balanceType":"1","createTime":"2018-09-28 11:18:09"}]
     */

    private String return_code;
    private String return_msg;
    private List<ListBean> list;

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * balanceFrom : 彩票购买
         * balance : 2.00
         * balanceType : 1  //0：收入；1：支出
         * createTime : 2018-10-05 21:11:02
         */

        private String balanceFrom;
        private String balance;
        private String balanceType;
        private String createTime;

        public String getBalanceFrom() {
            return balanceFrom;
        }

        public void setBalanceFrom(String balanceFrom) {
            this.balanceFrom = balanceFrom;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getBalanceType() {
            return balanceType;
        }

        public void setBalanceType(String balanceType) {
            this.balanceType = balanceType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
