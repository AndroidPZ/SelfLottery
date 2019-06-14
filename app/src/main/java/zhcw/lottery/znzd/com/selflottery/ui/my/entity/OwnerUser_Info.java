package zhcw.lottery.znzd.com.selflottery.ui.my.entity;

/**
 * 作者：XPZ on 2018/10/16 09:57.
 */
public class OwnerUser_Info {
    /**
     * return_code : SUCCESS
     * return_msg : 成功
     * terminal : {"createTime":"2018-09-25 17:48:22","id":7,"idNumber":"130602199310100011","macAddress":"tcl2","ownerName":"北京技术中心","stationAddress":"北京市海淀区裕惠大厦","stationNum":"10235052","stationPhone":"15130285270","status":1,"terminalNum":"20000007","userId":"1fbdbbd8a24440b8a57a46c092096ebc"}
     * <p>
     * {"return_code":"SUCCESS","return_msg":"成功",
     * "terminal": {
     * "createTime": "2018-09-25 17:48:22", //提交时间
     * "id": 7, //记录id（不展示）
     * "idNumber": "130602199310100011", //业主身份证号
     * "macAddress": "tcl2", //机器mac地址
     * "ownerName": "北京技术中心", //业主姓名
     * "stationAddress": "北京市海淀区裕惠大厦", //站点地址
     * "stationNum": "10235052", //站点号
     * "stationPhone": "15130285270", //业主联系电话
     * "status": 1, //记录状态 0:业主信息未审核；1:机器已绑定并可用；2:业主认证成功但未绑定机器；3:机器信息未审核；4:业主认证失败；5:机器信息验证失败；9:机器禁用
     * "terminalNum": "20000007", //机器编号
     * "userId": "1fbdbbd8a24440b8a57a46c092096ebc" //用户id（不展示）
     * }}
     */
    private String return_code;
    private String return_msg;
    private TerminalBean terminal;

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

    public TerminalBean getTerminal() {
        return terminal;
    }

    public void setTerminal(TerminalBean terminal) {
        this.terminal = terminal;
    }

    public static class TerminalBean {
        /**
         * createTime : 2018-09-25 17:48:22
         * id : 7
         * idNumber : 130602199310100011
         * macAddress : tcl2
         * ownerName : 北京技术中心
         * stationAddress : 北京市海淀区裕惠大厦
         * stationNum : 10235052
         * stationPhone : 15130285270
         * status : 1
         * terminalNum : 20000007
         * userId : 1fbdbbd8a24440b8a57a46c092096ebc
         */

        private String createTime;
        private int id;
        private String idNumber;
        private String macAddress;
        private String ownerName;
        private String stationAddress;
        private String stationNum;
        private String stationPhone;
        private int status;
        private String terminalNum;
        private String userId;

        public String getCreateTime() {
            return createTime == null ? "" : createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIdNumber() {
            return idNumber == null ? "" : idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public String getMacAddress() {
            return macAddress == null ? "" : macAddress;
        }

        public void setMacAddress(String macAddress) {
            this.macAddress = macAddress;
        }

        public String getOwnerName() {
            return ownerName == null ? "" : ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public String getStationAddress() {
            return stationAddress == null ? "" : stationAddress;
        }

        public void setStationAddress(String stationAddress) {
            this.stationAddress = stationAddress;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTerminalNum() {
            return terminalNum == null ? "" : terminalNum;
        }

        public void setTerminalNum(String terminalNum) {
            this.terminalNum = terminalNum;
        }

        public String getUserId() {
            return userId == null ? "" : userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }


    }


}
