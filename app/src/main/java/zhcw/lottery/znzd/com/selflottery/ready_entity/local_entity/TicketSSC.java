package zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity;

/**
 * Created by Administrator on 2016/3/5.
 */
//用户投注信息封装
public class TicketSSC {
    //时时彩
    private String redNumWan;
    private String redNumQian;
    private String redNumBai;
    private String redNumShi;
    private String redNumGe;

    private String redNumView; //用于展示
    private String redNumSeed; //用于传输
    private int num;
    private String lotteryid;//玩法编号

    private boolean state;//是否限额


    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
    

    public String getRedNumView() {
        return redNumView == null ? "" : redNumView;
    }

    public void setRedNumView(String redNumView) {
        this.redNumView = redNumView;
    }

    public String getRedNumSeed() {
        return redNumSeed == null ? "" : redNumSeed;
    }

    public void setRedNumSeed(String redNumSeed) {
        this.redNumSeed = redNumSeed;
    }

    public String getRedNumWan() {
        return redNumWan == null ? "" : redNumWan;
    }

    public void setRedNumWan(String redNumWan) {
        this.redNumWan = redNumWan;
    }

    public String getRedNumQian() {
        return redNumQian == null ? "" : redNumQian;
    }

    public void setRedNumQian(String redNumQian) {
        this.redNumQian = redNumQian;
    }

    public String getRedNumBai() {
        return redNumBai == null ? "" : redNumBai;
    }

    /*时时彩*/
    public void setRedNumBai(String redNumBai) {
        this.redNumBai = redNumBai;
    }

    public String getRedNumShi() {
        return redNumShi == null ? "" : redNumShi;
    }

    public void setRedNumShi(String redNumShi) {
        this.redNumShi = redNumShi;
    }

    public String getRedNumGe() {
        return redNumGe == null ? "" : redNumGe;
    }

    public void setRedNumGe(String redNumGe) {
        this.redNumGe = redNumGe;
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
