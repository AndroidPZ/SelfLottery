package zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity;

/**
 * Created by Administrator on 2016/3/5.
 */
//用户投注信息封装
public class TicketQLC {
    //3D
    private String redNumBai;

    private String redNumShi;

    private String redNumGe;

    private int num;
    private String lotteryid;//玩法编号
    private boolean state;//是否限额


    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
    /*3D*/
    public void setRedNumBai(String redNumBai) {
        this.redNumBai = redNumBai;
    }

    public String getRedNumBai() {
        return redNumBai == null ? "" : redNumBai;
    }

    public void setRedNumShi(String redNumShi) {
        this.redNumShi = redNumShi;
    }


    public String getRedNumShi() {
        return redNumShi == null ? "" : redNumShi;
    }

    public void setRedNumGe(String redNumGe) {
        this.redNumGe = redNumGe;
    }


    public String getRedNumGe() {
        return redNumGe == null ? "" : redNumGe;
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
