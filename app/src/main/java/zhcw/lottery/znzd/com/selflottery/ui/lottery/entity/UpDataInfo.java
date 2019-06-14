package zhcw.lottery.znzd.com.selflottery.ui.lottery.entity;

/**
 * 作者：XPZ on 2018/11/13 17:20.
 */
public class UpDataInfo {

    /**
     * return_code : SUCCESS
     * return_msg : 请更新
     * data : {"versionNum":"1.0.0","versionUrl":"http://selftest.zhcvideo.com/self_lottery/download/apk/selflottery_Text_v1.0.apk"}
     */

    private String return_code;
    private String return_msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * versionNum : 1.0.0
         * versionUrl : http://selftest.zhcvideo.com/self_lottery/download/apk/selflottery_Text_v1.0.apk
         */

        private String versionNum;
        private String versionUrl;

        public String getVersionNum() {
            return versionNum;
        }

        public void setVersionNum(String versionNum) {
            this.versionNum = versionNum;
        }

        public String getVersionUrl() {
            return versionUrl;
        }

        public void setVersionUrl(String versionUrl) {
            this.versionUrl = versionUrl;
        }
    }
}
