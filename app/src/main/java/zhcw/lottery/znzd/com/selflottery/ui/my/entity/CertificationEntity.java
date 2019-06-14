package zhcw.lottery.znzd.com.selflottery.ui.my.entity;

import java.io.Serializable;

/**
 * Created by dell on 2018/10/3.
 */

public class CertificationEntity implements Serializable {
    private String realName;
    private String idNumber;
    private String idPosPic;
    private String idOthPic;
    private String invalidDate;
    private String appUserId;
    private int state;
    private String remark;
    private String updateTime;

    public String getRealName() {
        return realName == null ? "" : realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdNumber() {
        return idNumber == null ? "" : idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdPosPic() {
        return idPosPic == null ? "" : idPosPic;
    }

    public void setIdPosPic(String idPosPic) {
        this.idPosPic = idPosPic;
    }

    public String getIdOthPic() {
        return idOthPic == null ? "" : idOthPic;
    }

    public void setIdOthPic(String idOthPic) {
        this.idOthPic = idOthPic;
    }

    public String getInvalidDate() {
        return invalidDate == null ? "" : invalidDate;
    }

    public void setInvalidDate(String invalidDate) {
        this.invalidDate = invalidDate;
    }

    public String getAppUserId() {
        return appUserId == null ? "" : appUserId;
    }

    public void setAppUserId(String appUserId) {
        this.appUserId = appUserId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRemark() {
        return remark == null ? "" : remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdateTime() {
        return updateTime == null ? "" : updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }


}

