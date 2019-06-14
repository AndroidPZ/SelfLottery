package zhcw.lottery.znzd.com.selflottery.ready_entity.send_json;

/**
 * 作者：XPZ on 2018/8/6 14:53.
 */
public class LoginInfo {

    private String phone;
    private String code;

    public String getSign() {
        return sign == null ? "" : sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    private String sign;

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code == null ? "" : code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "phone='" + phone + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

}
