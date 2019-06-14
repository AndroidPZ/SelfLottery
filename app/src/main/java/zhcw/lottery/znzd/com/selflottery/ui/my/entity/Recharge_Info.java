package zhcw.lottery.znzd.com.selflottery.ui.my.entity;

/**
 * 作者：XPZ on 2018/10/15 15:21.
 */
public class Recharge_Info {//implements Serializable
    /**
     * return_code : SUCCESS
     * return_msg : 成功
     * appid : wxsfd43sa54f3sa
     * partnerid : 1512133535
     * prepayid : 5464354346
     * package : Sign=WXPay
     * noncestr : 546aaggrg4w34wrtrewe6twa
     * timestamp : 1564354346
     * out_trade_no : 132132464648
     */

    private String return_code;
    private String return_msg;
    private String appid;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;
    private String out_trade_no;
    private String sign;

    @Override
    public String toString() {
        return "Recharge_Info{" +
                "return_code='" + return_code + '\'' +
                ", return_msg='" + return_msg + '\'' +
                ", appid='" + appid + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    public String getSign() {
        return sign == null ? "" : sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

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

    public String getAppid() {
        return appid == null ? "" : appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid == null ? "" : partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid == null ? "" : prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr == null ? "" : noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp == null ? "" : timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getOut_trade_no() {
        return out_trade_no == null ? "" : out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

}
