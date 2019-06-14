package zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/5.
 */
//用户投注信息封装
public class TicketSSQ implements Serializable {
    //SSQ
    private String redNum;
    private String redNumTuo;
    private String blueNum;
    private String redNumJ;
    private String redNumTuoJ;
    private String blueNumJ;
    private int num;
    private String lotteryid;//玩法编号
    private boolean state;//是否限额


    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "TicketSSQ{" +
                "redNum='" + redNum + '\'' +
                ", redNumTuo='" + redNumTuo + '\'' +
                ", blueNum='" + blueNum + '\'' +
                ", redNumJ='" + redNumJ + '\'' +
                ", redNumTuoJ='" + redNumTuoJ + '\'' +
                ", blueNumJ='" + blueNumJ + '\'' +
                ", num=" + num +
                ", lotteryid='" + lotteryid + '\'' +
                '}';
    }

    /*SSQ*/
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

    public String getBlueNum() {
        return blueNum;
    }

    public void setBlueNum(String blueNum) {
        this.blueNum = blueNum;
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

    public String getBlueNumJ() {
        return blueNumJ;
    }

    public void setBlueNumJ(String blueNumJ) {
        this.blueNumJ = blueNumJ;
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
