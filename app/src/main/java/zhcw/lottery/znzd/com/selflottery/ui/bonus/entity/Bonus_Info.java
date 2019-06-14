package zhcw.lottery.znzd.com.selflottery.ui.bonus.entity;

/**
 * 作者：XPZ on 2018/9/7 17:39.
 */
public class Bonus_Info {

    /**
     * return_code : SUCCESS
     * return_msg : 恭喜您，中了快3三同号单选，奖金240元，已发放至您的账户
     * type : 快3
     * level : 三同号单选   //将等
     * money : 240  //金额
     * isBomb : N //小奖
     */

    private String return_code;
    private String return_msg;
    private String type;
    private String level;
    private int money;
    private String isBomb;

    @Override
    public String toString() {
        return "Bonus_Info{" +
                "return_code='" + return_code + '\'' +
                ", return_msg='" + return_msg + '\'' +
                ", type='" + type + '\'' +
                ", level='" + level + '\'' +
                ", money=" + money +
                ", isBomb='" + isBomb + '\'' +
                '}';
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

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level == null ? "" : level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getIsBomb() {
        return isBomb == null ? "" : isBomb;
    }

    public void setIsBomb(String isBomb) {
        this.isBomb = isBomb;
    }

}
