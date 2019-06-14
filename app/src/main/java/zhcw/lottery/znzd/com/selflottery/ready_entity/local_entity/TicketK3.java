package zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity;

/**
 * Created by Administrator on 2016/3/5.
 */
//用户投注信息封装
public class TicketK3 {
    //和值
    private String heZhiNum;  //用于展示 01 02 03

    private int num;

    private String lotteryid;//玩法编号
    private String JsonNum; //用于发送数据 01,02,03
    private boolean state;//是否限额


    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }


    public String getHeZhiNum() {
        return heZhiNum == null ? "" : heZhiNum;
    }

    public void setHeZhiNum(String heZhiNum) {
        this.heZhiNum = heZhiNum;
    }


    public String getJosnNum() {
        return JsonNum == null ? "" : JsonNum;
    }

    public void setJsonNum(String JsonNum) {
        this.JsonNum = JsonNum;
    }


    /*注数*/
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    /*玩法id*/
    public String getLotteryid() {
        return lotteryid;
    }

    public void setLotteryid(String lotteryid) {
        this.lotteryid = lotteryid;
    }
}
