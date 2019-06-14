package zhcw.lottery.znzd.com.selflottery.ready_entity.bean_info;


import zhcw.lottery.znzd.com.selflottery.config.Config;
import zhcw.lottery.znzd.com.selflottery.util.PreferenceUtil;

/**
 * Created by xpz on 2018/4/13.
 * 用户信息表
 */

public class UserInfo {

    private static String token;                  //登录token
    private static String appUserCreateTime;      //创建时间
    private static String appUserHeadImgUrl;      //头像
    private static String appUserId;              //用户Id
    private static String appUserNickName;        //昵称
    private static String appUserPhone;           //用户手机号
    private static String appUserEmail;           //用户的email
    private static String appUserPassword;        //用户密码
    private static String appUserQrCodeUrl;       //二维码信息
    private static String weixincode;             //微信授权code;
    private static String out_trade_no;           //微信授权code;
    private static float money;                   //余额
    private static int audit;                     //用户是否实名认证
    private static int isOwner;                   //是否认证业主
    private static boolean isLogin;               //是否登录
    private static boolean isbind;                //用户是否微信授权登录
    private static String socket_ip;              //推送Socket_ip(IP)
    private static int socket_port;            //推送Socket_port(端口号)

    static {
        init();
    }

    public static String getSocket_ip() {
        return socket_ip == null ? "" : socket_ip;
    }

    public static int getSocket_port() {
        return socket_port;
    }

    public static float getMoney() {
        return money;
    }

    public static String getOut_trade_no() {
        return out_trade_no == null ? "" : out_trade_no;
    }

    public static String getToken() {
        return token == null ? "" : token;
    }

    public static String getAppUserCreateTime() {
        return appUserCreateTime == null ? "" : appUserCreateTime;
    }

    public static String getAppUserHeadImgUrl() {
        return appUserHeadImgUrl == null ? "" : appUserHeadImgUrl;
    }

    public static String getAppUserId() {
        return appUserId == null ? "" : appUserId;
    }

    public static String getAppUserNickName() {
        return appUserNickName == null ? "" : appUserNickName;
    }

    public static String getAppUserPhone() {
        return appUserPhone == null ? "" : appUserPhone;
    }

    public static String getAppUserEmail() {
        return appUserEmail == null ? "" : appUserEmail;
    }

    public static String getAppUserPassword() {
        return appUserPassword == null ? "" : appUserPassword;
    }

    public static String getAppUserQrCodeUrl() {
        return appUserQrCodeUrl == null ? "" : appUserQrCodeUrl;
    }

    public static String getWeixincode() {
        return weixincode == null ? "" : weixincode;
    }

    public static boolean isIsLogin() {
        return isLogin;//isLogin
    }

    public static int getIsOwner() {
        return isOwner;
    }


    public static int getAudit() {
        return audit;
    }

    public static boolean isBind() {
        return isbind;
    }

    private static void init() {

        token = PreferenceUtil.getInstance().getPreferences(Config.TOKEN, "token=null");
        socket_ip = PreferenceUtil.getInstance().getPreferences(Config.SOCKET_IP, "");
        socket_port = PreferenceUtil.getInstance().getPreferences(Config.SOCKET_PORT, 0);
        appUserHeadImgUrl = PreferenceUtil.getInstance().getPreferences(Config.USERHEADIMGURL, "");
        appUserNickName = PreferenceUtil.getInstance().getPreferences(Config.USER_NAME, "");
        appUserPhone = PreferenceUtil.getInstance().getPreferences(Config.USER_PHONE, "");
        isLogin = PreferenceUtil.getInstance().getPreferences(Config.IS_LOGIN, false);
        appUserQrCodeUrl = PreferenceUtil.getInstance().getPreferences(Config.USERQRCODEURL, "");
        audit = PreferenceUtil.getInstance().getPreferences(Config.AUDIT, 3);
        appUserEmail = PreferenceUtil.getInstance().getPreferences(Config.EMAIL, "");
        appUserPassword = PreferenceUtil.getInstance().getPreferences(Config.PASSWORD, "");
        out_trade_no = PreferenceUtil.getInstance().getPreferences(Config.OUT_TRADE_NO, "");
        isOwner = PreferenceUtil.getInstance().getPreferences(Config.ISOWNER, 0);
        money = PreferenceUtil.getInstance().getPreferences(Config.MONEY, 0.00f);

        isbind = PreferenceUtil.getInstance().getPreferences(Config.IS_BIND, false);
        weixincode = PreferenceUtil.getInstance().getPreferences(Config.WEIXINCODE, "");
    }

    public static boolean isLogin() {
        if (isLogin) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 刷新数据 （获取）
     */
    public static void refresh() {
        init();
    }

    /**
     * 退出登录 清除数据
     */
    public static void logout() {
        PreferenceUtil.getInstance().putPreferences(Config.TOKEN, "");
        PreferenceUtil.getInstance().putPreferences(Config.USERCREATETIME, "");
        PreferenceUtil.getInstance().putPreferences(Config.USERHEADIMGURL, "");
        PreferenceUtil.getInstance().putPreferences(Config.USER_ID, "");
        PreferenceUtil.getInstance().putPreferences(Config.USER_NAME, "");
        PreferenceUtil.getInstance().putPreferences(Config.USER_PHONE, "");
        PreferenceUtil.getInstance().putPreferences(Config.IS_LOGIN, false);
        PreferenceUtil.getInstance().putPreferences(Config.USERQRCODEURL, "");
        PreferenceUtil.getInstance().putPreferences(Config.AUDIT, 3);
        PreferenceUtil.getInstance().putPreferences(Config.IS_BIND, false);
        PreferenceUtil.getInstance().putPreferences(Config.WEIXINCODE, "");
        PreferenceUtil.getInstance().putPreferences(Config.OPENID, "");
        PreferenceUtil.getInstance().putPreferences(Config.EMAIL, "");
        PreferenceUtil.getInstance().putPreferences(Config.PASSWORD, "");
        PreferenceUtil.getInstance().putPreferences(Config.ISOWNER, 0);
        PreferenceUtil.getInstance().putPreferences(Config.OUT_TRADE_NO, "");
        PreferenceUtil.getInstance().putPreferences(Config.MONEY, 0.00f);

        refresh();
    }
}
