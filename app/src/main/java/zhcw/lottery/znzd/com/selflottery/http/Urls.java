package zhcw.lottery.znzd.com.selflottery.http;

import zhcw.lottery.znzd.com.selflottery.BuildConfig;

/**
 * @author xpz
 * @version V1.0
 *          接口
 */

public class Urls {
    //切换正式和测试接口 false为测试
    private static UrlController urlController = UrlController.getInstance(BuildConfig.IS_TEST);
    static final String HOST = urlController.getUrl();
    /**
     * 实名认证--认证数据查询
     **/
    static String Certification_default = HOST + "/self_lottery/realname/queryRealName";
    /**
     * 实名认证
     **/
    static String Certification = HOST + "/self_lottery/realname/upload_img";
    /**
     * 版本更新
     **/
    static String VersionUpdate = HOST + "/self_lottery/version/versionByNum";
    /**
     * 首页轮播图接口
     */
    static String HomeBanner = HOST + "/self_lottery/banner/getBanner";
    /**
     * 请求登录
     **/
    static final String login = HOST + "/self_lottery/userinfo/login";
    /**
     * 获取验证码
     */
    static final String GetCode = HOST + "/self_lottery/message/sendMessage";
    /**
     * 充值
     */
    static final String Recharge = HOST + "/self_lottery/account/recharge";
    /**
     * 兑奖
     */
    static final String BonusRecharge = HOST + "/self_lottery/lotteryreward/reward";
    /**
     * 请求订单二维码的字符串
     **/
    static final String lotteryInfo = HOST + "/self_lottery/lotteryorder/placeOrder";
    /**
     * 请求订单详情数据
     **/
    static final String Verification = HOST + "/self_lottery/lotteryorder/getOrderByUser";
    /**
     * 删除订单
     */
    static final String DeleteOrder = HOST + "/self_lottery/lotteryorder/deleteOrder";
    /**
     * 获取用户信息
     */
    static final String UserInfo = HOST + "/self_lottery/userinfo/getUserInfo";
    /**
     * 获取账户明细
     */
    static final String Detail = HOST + "/self_lottery/account/getDetail";
    /**
     * 业主佣金(机器销售情况查询)
     */
    static final String SaleRecord = HOST + "/self_lottery/lotteryrecord/getSaleRecord";
    /**
     * 业主佣金提现
     */
    static final String Apply = HOST + "/self_lottery/withdrawal/apply";
    /**
     * 获取业主认证信息
     */
    static final String OwnerInfo = HOST + "/self_lottery/terminal/getInfo";
    /**
     * 业主提交认证信息接口(认证)
     */
    static final String OwnerBind = HOST + "/self_lottery/terminal/bind";
    /**
     * 业主获取绑定二维码接口
     */
    static final String BindQrcode = HOST + "/self_lottery/terminal/getQrcode";
    /**
     * 充值下单接口
     */
    static final String UnifiedOrder = HOST + "/self_lottery/paywechat/unifiedOrder";
    /**
     * 2、查询微信支付订单状态接口
     */
    static final String QueryOrder = HOST + "/self_lottery/paywechat/queryOrder";
    /**
     * 发送微信授权给后台
     */
    static final String WechatCode = HOST + "/self_lottery/userinfo/auth_wechat";
    /**
     * 微信申请提现接口
     */
    static final String WeAddPreOrder = HOST + "/self_lottery/withdrawal_wechat/addPreOrder";
    /**
     * 请求奖期号
     **/
    static final String Get_JiangQi = HOST + "/self_lottery/lotteryissue/getIssue";//?gameName=ssq
    /**
     * 获取账户余额
     **/
    static final String Get_Account = HOST + "/self_lottery/account/getAccount";
    /**
     * 欢迎页面
     **/
    static final String Welcome = HOST + "/zch/app/init/getScreenImage.do";
    /**
     * H5地址
     **/
    public static final String Yz_Help = HOST + "/zch/helpcenter.html";
    public static final String Yz_Declare = HOST + "/zch/declare.html";
    public static final String Host = HOST;
//    static String HomeBanner = "http://lj.zhcvideo.com/lottery_lj/banner/find_banner";
}
