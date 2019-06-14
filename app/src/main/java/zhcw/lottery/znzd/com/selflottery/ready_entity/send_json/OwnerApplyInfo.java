package zhcw.lottery.znzd.com.selflottery.ready_entity.send_json;

/**
 * 作者：XPZ on 2018/10/16 09:52.
 */
public class OwnerApplyInfo {
    private String token;
    private String city;
    private String ownerName;
    private String idNumber;
    private String stationNum;
    private String stationPhone;
    private String stationAddress;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getToken() {
        return token == null ? "" : token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOwnerName() {
        return ownerName == null ? "" : ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getIdNumber() {
        return idNumber == null ? "" : idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getStationNum() {
        return stationNum == null ? "" : stationNum;
    }

    public void setStationNum(String stationNum) {
        this.stationNum = stationNum;
    }

    public String getStationPhone() {
        return stationPhone == null ? "" : stationPhone;
    }

    public void setStationPhone(String stationPhone) {
        this.stationPhone = stationPhone;
    }

    public String getStationAddress() {
        return stationAddress == null ? "" : stationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress;
    }
}
