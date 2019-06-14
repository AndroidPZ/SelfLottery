package zhcw.lottery.znzd.com.selflottery.ui.my.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：XPZ on 2018/8/15 14:08.
 */
public class Detail_Info {

    /**
     * return_code : SUCCESS
     * return_msg : 成功
     * orders : [{"orderNum":"20180815959999","orderTime":"2018-08-15 13:35:12","qrcodeStr":"LOTTERY_21d396811027e60243953ebb","lotteryType":"快3","multiple":1,"orderStake":5,"orderAmount":10,"lotterys":[{"playType":"和值投注","lotteryNum":"","lotteryStake":1,"lotteryAmount":2,"printStatus":0},{"playType":"和值投注","lotteryNum":"","lotteryStake":1,"lotteryAmount":2,"printStatus":0},{"playType":"和值投注","lotteryNum":"","lotteryStake":1,"lotteryAmount":2,"printStatus":0},{"playType":"和值投注","lotteryNum":"","lotteryStake":1,"lotteryAmount":2,"printStatus":0},{"playType":"三不同单选","lotteryNum":"02,03,05","lotteryStake":1,"lotteryAmount":2,"printStatus":0}]},{"orderNum":"20180815276080","orderTime":"2018-08-15 13:34:02","qrcodeStr":"LOTTERY_e89b2714a9f9a8b98f42d70e","lotteryType":"3D","multiple":1,"orderStake":5,"orderAmount":10,"lotterys":[{"lotteryNum":"9,1,0","lotteryStake":1,"lotteryAmount":2,"printStatus":0},{"lotteryNum":"2,3,9","lotteryStake":1,"lotteryAmount":2,"printStatus":0},{"lotteryNum":"6,1,3","lotteryStake":1,"lotteryAmount":2,"printStatus":0},{"lotteryNum":"4,7,8","lotteryStake":1,"lotteryAmount":2,"printStatus":0},{"lotteryNum":"1,8,9","lotteryStake":1,"lotteryAmount":2,"printStatus":0}]},{"orderNum":"20180815648401","orderTime":"2018-08-15 13:28:50","qrcodeStr":"LOTTERY_3760e9d6db1e0974927ec509","lotteryType":"双色球","multiple":1,"orderStake":5,"orderAmount":10,"lotterys":[{"playType":"单式","lotteryNum":"07,09,12,23,30,32#01|01 08 10 15 21 29#09|02 07 14 15 20 24#10|05 14 21 30 31 32#05|04 08 13 14 15 20#09","lotteryStake":5,"lotteryAmount":10,"printStatus":0}]},{"orderNum":"20180815859107","orderTime":"2018-08-15 13:25:07","qrcodeStr":"LOTTERY_7f0e17a1cad5b716cbcb4923","lotteryType":"快3","multiple":1,"orderStake":8,"orderAmount":16,"lotterys":[{"playType":"和值投注","lotteryNum":"04","lotteryStake":1,"lotteryAmount":2,"printStatus":0},{"playType":"和值投注","lotteryNum":"08,09,10,11","lotteryStake":4,"lotteryAmount":8,"printStatus":0},{"playType":"二不同单选","lotteryNum":"01,01,01","lotteryStake":1,"lotteryAmount":2,"printStatus":0},{"playType":"二不同单选","lotteryNum":"02,02,03","lotteryStake":1,"lotteryAmount":2,"printStatus":0},{"playType":"三不同单选","lotteryNum":"01,02,03","lotteryStake":1,"lotteryAmount":2,"printStatus":0}]},{"orderNum":"20180815813804","orderTime":"2018-08-15 13:24:39","qrcodeStr":"LOTTERY_e9d1498a47052ecca8bcd3f2","lotteryType":"3D","multiple":1,"orderStake":36,"orderAmount":72,"lotterys":[{"lotteryNum":"0,1,2","lotteryStake":1,"lotteryAmount":2,"printStatus":0},{"lotteryNum":"012,123,23","lotteryStake":18,"lotteryAmount":36,"printStatus":0},{"lotteryNum":"0,1,2","lotteryStake":6,"lotteryAmount":12,"printStatus":0},{"lotteryNum":"7,8,9","lotteryStake":1,"lotteryAmount":2,"printStatus":0},{"lotteryNum":"5,6,7,8,9","lotteryStake":10,"lotteryAmount":20,"printStatus":0}]},{"orderNum":"20180815701253","orderTime":"2018-08-15 13:23:54","qrcodeStr":"LOTTERY_ac293c6459c21f03ec829894","lotteryType":"双色球","multiple":1,"orderStake":26,"orderAmount":52,"lotterys":[{"playType":"复式","lotteryNum":"01,02,03,04,05,06,07#01 02 03","lotteryStake":21,"lotteryAmount":42,"printStatus":0},{"playType":"胆拖","lotteryNum":"01,02,03,04,05*21 22~01 02","lotteryStake":4,"lotteryAmount":8,"printStatus":0},{"playType":"单式","lotteryNum":"01,02,03,04,05,06#01","lotteryStake":1,"lotteryAmount":2,"printStatus":0}]}]
     */

    private String return_code;
    private String return_msg;
    private List<OrdersBean> orders;

    public String getReturn_code() {
        return return_code == null ? "" : return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg == null ? "" : return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public List<OrdersBean> getOrders() {
        if (orders == null) {
            return new ArrayList<>();
        }
        return orders;
    }

    public void setOrders(List<OrdersBean> orders) {
        this.orders = orders;
    }

    public static class OrdersBean {
        /**
         * orderNum : 20180815959999
         * orderTime : 2018-08-15 13:35:12
         * qrcodeStr : LOTTERY_21d396811027e60243953ebb
         * lotteryType : 快3
         * multiple : 1
         * orderStake : 5
         * orderAmount : 10
         * allPrint : 1 //0有未打印: 1已全打印
         * lotterys : [{"playType":"和值投注","lotteryNum":"","lotteryStake":1,"lotteryAmount":2,"printStatus":0},{"playType":"和值投注","lotteryNum":"","lotteryStake":1,"lotteryAmount":2,"printStatus":0},{"playType":"和值投注","lotteryNum":"","lotteryStake":1,"lotteryAmount":2,"printStatus":0},{"playType":"和值投注","lotteryNum":"","lotteryStake":1,"lotteryAmount":2,"printStatus":0},{"playType":"三不同单选","lotteryNum":"02,03,05","lotteryStake":1,"lotteryAmount":2,"printStatus":0}]
         */

        private String orderNum;
        private String orderTime;
        private String qrcodeStr;
        private String lotteryType;
        private int multiple;
        private int orderStake;
        private int orderAmount;
        private int allPrint;
        private List<LotterysBean> lotterys;
        private String issue;

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

        public List<LotterysBean> getLotterys() {
            if (lotterys == null) {
                return new ArrayList<>();
            }
            return lotterys;
        }

        public void setLotterys(List<LotterysBean> lotterys) {
            this.lotterys = lotterys;
        }

        public static class LotterysBean {
            /**
             * playType : 和值投注
             * lotteryNum :
             * lotteryStake : 1
             * lotteryAmount : 2
             * printStatus : 0
             */

            private String playType;
            private String lotteryNum;
            private int lotteryStake;
            private int lotteryAmount;
            private int printStatus;

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

            public int getPrintStatus() {
                return printStatus;
            }

            public void setPrintStatus(int printStatus) {
                this.printStatus = printStatus;
            }

        }
    }
}
