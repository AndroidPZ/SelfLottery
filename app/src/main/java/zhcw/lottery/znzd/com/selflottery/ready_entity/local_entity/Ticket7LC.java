package zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity;

/**
 * Created by Administrator on 2016/3/5.
 */
//用户投注信息封装
public class Ticket7LC {
    //SSQ
    private String redNum; //展示格式
    private String redNumTuo;
    private String redNumJ; //发送格式
    private String redNumTuoJ;

    private int num;

    private String lotteryid;//玩法编号
    private boolean state;//是否限额


    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }


    /*7LC*/
    public String getRedNum() {
        return redNum;
    }

    public void setRedNum(String redNum) {
        this.redNum = redNum;
    }

    public String getRedNumTuo() {
        return redNumTuo;
    }

    public void setRedNumTuo(String redNumTuo) {
        this.redNumTuo = redNumTuo;
    }

    public String getRedNumJ() {
        return redNumJ;
    }

    public void setRedNumJ(String redNumJ) {
        this.redNumJ = redNumJ;
    }

    public String getRedNumTuoJ() {
        return redNumTuoJ;
    }

    public void setRedNumTuoJ(String redNumTuoJ) {
        this.redNumTuoJ = redNumTuoJ;
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
