package zhcw.lottery.znzd.com.selflottery.config;

/**
 * Created by XPZ on 2018/8/16.
 * 彩票业务相关配置
 */
public interface ConstantValue {

    /**
     * 单注票面限制金额上限
     */
    int UPPER_LIMIT = 20000;

    /**
     * 订单总限额
     */
    int UPPER_LIMIT_SHOPPING = 20000;

    /*  注 *型号为待完成的玩法   */

    //双色球的标识
    String SSQ = "ssq";
    String SSQ_P = "101"; //直选
    String SSQ_FS = "102"; //复试
    String SSQ_DT = "103"; //胆拖

    //3D的标识
    String SD = "3d";
    String SD_P = "201";//单选
    String SD_ZS = "202";//组三     *
    String SD_ZL = "203";//组六     *
    String SD_ZXHZ = "204";//直选和值     *
    String SD_ZSHZ = "205";//组三和值     *
    String SD_ZLHZ = "206";//组六和值     *
    String SD_ZF = "209";//直选复式     *
    String SD_ZSF = "210";//组三复式
    String SD_ZLF = "211";//组六复式
    String SD_ZX_NWBH = "208";//直选按位包号

    //k3玩法的标识
    String K3 = "k3";//k3彩种
    String K3_SBT = "500";//三不同号投注
    String K3_RTH_DX = "501";//二同号单选投注
    String K3_STH_DX = "502";//三同号单选投注
    String K3_HE = "510";//和值投注
    String K3_RBT = "520";//二不同号投注     *
    String K3_RTH_FX = "530";//二同号复选投注    *
    String K3_STH_TX = "540";//三同号通选投注    *
    String K3_SLH_TX = "550";//三连号通选投注    *

    //7乐彩的标示
    String T7LC = "307";//七乐彩
    String T7LC_P = "101";//七乐彩直选
    String T7LC_FS = "102";//七乐彩复试
    String T7LC_DT = "103";//七乐彩胆拖

    // 时时彩的标示
    String SSC = "nmssc";//时时彩
    String SSC_P = "301";//单式投注 *
    String SSC_F = "302";//复选投注 *
    String SSC_FW = "303";//复式投注(位选)    *
    String SSC_DXJO = "304";//大小奇偶 *最多5注    *
    String SSC_WXTX = "305";//五星通选 *最多5注    *


    /*  -------- 彩种本地存储标示 区分名  --------   */
    String SSQTYPE = "ssqtype";
    String K3TYPE = "k3type";
    String SDTYPE   = "3dtype";
    String T7LCTYPE = "7lctype";
    String SSCTYPE  = "ssctype";

    /* 本地 名称 */
    //双的球
    String gameType_ZX = "单式投注";
    String gameType_FS = "复式投注";
    String gameType_DT = "胆拖投注";

    //3D
    String gameSDType_ZX = "单选";
    String gameSDType_ZS = "组三";
    String gameSDType_ZL = "组六";
    String gameSDType_ZXHZ = "单选全包点";
    String gameSDType_ZSHZ = "组三包点";
    String gameSDType_ZLHZ = "组六包点";
    String gameSDType_ZXFS = "单选单复式";
    String gameSDType_ZSFS = "组三复选";
    String gameSDType_ZLFS = "组六复选";
    String gameSDType_ZXWX = "单选位选";

    //K3
    String gameK3Type_DXZ = "单式(集合)";
    String gameK3Type_RTHDX = "二同号单选";
    String gameK3Type_STHDX = "三同号单选";
    String gameK3Type_SBTH = "三不同单选";
    String gameK3Type_STHTX = "三同号通选";
    String gameK3Type_SLHTX = "三连号通选";
    String gameK3Type_RTHFX = "二同号复选";
    String gameK3Type_RBTH = "二不同";
    String gameK3Type_HZ = "和值";

    //七乐彩
    String game7LCType_ZX = "单式投注";
    String game7LCType_FS = "复式投注";
    String game7LCType_DT = "胆拖投注";

    //时时彩
    String gameSSCType_DS = "单式投注";
    String gameSSCType_FX = "复选投注";
    String gameSSCType_FS = "位选投注";
    String gameSSCType_DXJO = "大小单双";
    String gameSSCType_WXTX = "五星通选";

    /*------------------K3固定彩票信息----------------------*/
    String k3_540_sthtx_lottery_number = "111,222,333,444,555,666";
    String k3_550_slhtx_lottery_number = "123,234,345,456";

}
