package zhcw.lottery.znzd.com.selflottery.ready_entity.send_json;

/**
 * 作者：XPZ on 2018/8/6 14:53.
 */
public class BonusInfo {

    private String token;
    private String certiCode;

    @Override
    public String toString() {
        return "BonusInfo{" +
                "token='" + token + '\'' +
                ", certiCode='" + certiCode + '\'' +
                '}';
    }

    public String getToken() {
        return token == null ? "" : token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCertiCode() {
        return certiCode == null ? "" : certiCode;
    }

    public void setCertiCode(String certiCode) {
        this.certiCode = certiCode;
    }


}
