package zhcw.lottery.znzd.com.selflottery.ui.my.entity;

import java.util.List;

/**
 * 作者：XPZ on 2018/10/15 15:21.
 */
public class Commisson_Info {


    /**
     * saleInfo : {"amount":"14.00","percent":"7%","commission":"0.00","lt":[{"type":"k3","count":2,"sum":"14.00"},{"type":"ssq","count":0,"sum":"0.00"},{"type":"3d","count":0,"sum":"0.00"},{"type":"307","count":0,"sum":"0.00"},{"type":"nmssc","count":0,"sum":"0.00"}]}
     * return_code : SUCCESS
     * return_msg : 成功
     */

    private SaleInfoBean saleInfo;
    private String return_code;
    private String return_msg;

    public SaleInfoBean getSaleInfo() {
        return saleInfo;
    }

    public void setSaleInfo(SaleInfoBean saleInfo) {
        this.saleInfo = saleInfo;
    }

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

    public static class SaleInfoBean {
        /**
         * amount : 14.00
         * percent : 7%
         * commission : 0.00
         * lt : [{"type":"k3","count":2,"sum":"14.00"},{"type":"ssq","count":0,"sum":"0.00"},{"type":"3d","count":0,"sum":"0.00"},{"type":"307","count":0,"sum":"0.00"},{"type":"nmssc","count":0,"sum":"0.00"}]
         */

        private String amount;
        private String percent;
        private String commission;
        private List<LtBean> lt;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPercent() {
            return percent;
        }

        public void setPercent(String percent) {
            this.percent = percent;
        }

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
        }

        public List<LtBean> getLt() {
            return lt;
        }

        public void setLt(List<LtBean> lt) {
            this.lt = lt;
        }

        public static class LtBean {
            /**
             * type : k3
             * count : 2
             * sum : 14.00
             */

            private String type;
            private int count;
            private String sum;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getSum() {
                return sum;
            }

            public void setSum(String sum) {
                this.sum = sum;
            }
        }
    }
}
