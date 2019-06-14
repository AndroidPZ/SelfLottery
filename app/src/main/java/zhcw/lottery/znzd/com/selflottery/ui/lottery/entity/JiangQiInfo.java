package zhcw.lottery.znzd.com.selflottery.ui.lottery.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：XPZ on 2018/10/23 19:18.
 */
public class JiangQiInfo implements Serializable {


    /**
     * return_code : SUCCESS
     * return_msg : 成功
     * nowTime : 1542103680857
     * issues : [{"gameName":"ssq","issue":"2018003","status":1,"startTime":1541988001000,"stopTime":1542592740000},{"gameName":"3d","issue":"2018003","status":1,"startTime":1541988001000,"stopTime":1542592740000},{"gameName":"k3","issue":"20181113045","status":3,"startTime":1542099686000,"stopTime":1542100226000},{"gameName":"nmssc","issue":"20181113045","status":3,"startTime":1542099369000,"stopTime":1542099909000},{"gameName":"307","issue":"2018003","status":1,"startTime":1541988001000,"stopTime":1542592740000}]
     */

    private String return_code;
    private String return_msg;
    private long nowTime;
    private List<IssuesBean> issues;

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

    public long getNowTime() {
        return nowTime;
    }

    public void setNowTime(long nowTime) {
        this.nowTime = nowTime;
    }

    public List<IssuesBean> getIssues() {
        return issues;
    }

    public void setIssues(List<IssuesBean> issues) {
        this.issues = issues;
    }

    public static class IssuesBean {
        /**
         * gameName : ssq
         * issue : 2018003
         * status : 1
         * startTime : 1541988001000
         * stopTime : 1542592740000
         */

        private String gameName;
        private String issue;
        private int status;
        private long startTime;
        private long stopTime;

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getStopTime() {
            return stopTime;
        }

        public void setStopTime(long stopTime) {
            this.stopTime = stopTime;
        }
    }
}
