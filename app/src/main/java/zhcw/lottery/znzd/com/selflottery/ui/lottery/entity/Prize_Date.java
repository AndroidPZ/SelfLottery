package zhcw.lottery.znzd.com.selflottery.ui.lottery.entity;

/**
 * 作者：XPZ on 2018/8/15 15:28.
 */
public class Prize_Date {

    /**
     * return_code : SUCCESS
     * return_msg : 成功
     * issue : {"bonusCode":"","gameName":"ssq","issue":"2018026","startTime":"2018-08-14 08:30:01.0","status":1,"stopTime":"2018-08-20 12:00:00.0"}
     */

    private String return_code;
    private String return_msg;
    private IssueBean issue;

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

    public IssueBean getIssue() {
        return issue;
    }

    public void setIssue(IssueBean issue) {
        this.issue = issue;
    }

    public static class IssueBean {
        /**
         * bonusCode :
         * gameName : ssq
         * issue : 2018026
         * startTime : 2018-08-14 08:30:01.0
         * status : 1
         * stopTime : 2018-08-20 12:00:00.0
         */

        private String bonusCode;
        private String gameName;
        private String issue;
        private String startTime;
        private int status;
        private String stopTime;

        public String getBonusCode() {
            return bonusCode == null ? "" : bonusCode;
        }

        public void setBonusCode(String bonusCode) {
            this.bonusCode = bonusCode;
        }

        public String getGameName() {
            return gameName == null ? "" : gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public String getIssue() {
            return issue == null ? "" : issue;
        }

        public void setIssue(String issue) {
            this.issue = issue;
        }

        public String getStartTime() {
            return startTime == null ? "" : startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStopTime() {
            return stopTime == null ? "" : stopTime;
        }

        public void setStopTime(String stopTime) {
            this.stopTime = stopTime;
        }


    }
}
