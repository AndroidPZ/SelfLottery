package zhcw.lottery.znzd.com.selflottery.config;

import android.os.Environment;

import java.io.File;

import zhcw.lottery.znzd.com.selflottery.App;
import zhcw.lottery.znzd.com.selflottery.util.CommonUtils;

/**
 * Created by XPZ on 2018/9/30.
 * 处彩票业务之外的配置
 */

public interface Config {
    String BASE_SD_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/znzd";
    /*Glide 特殊加载 isStag 设置的宽度*/
    int WIDTH = ((CommonUtils.getScreenWidth() - CommonUtils.dp2px(20)) / 2);

    int SITE_ADD = 1;
    int IMAGE_ITEM_ADD = -1;
    int MAXIMGCOUNT = 4;

    int maxImgCount = 1;////允许选择图片最大数
    int REQUEST_CODE_SELECT = 100; //相册 选择图片的请求码
    int REQUEST_CODE_PREVIEW = 101;
    int REQUEST_CODE_CAMERA = 102; //实名认证 照相机扫描的请求码
    int CLIP_PHOTO_BY_SELF_REQUEST_CODE = 3;

    /**
     * Path , 缓存地址
     */
    String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "datas";

    String PATH_CACHE = PATH_DATA + "/NetCache";

    /**
     * 钱包详情中 每页的数量
     */
    int PAGE_SIZE = 20; //每页数量
    /**
     * 站点编号长度控制
     */
    int STATIONNUM_SIZE = 8; //每页数量

    //编码方式
    String ENCONDING = "UTF-8";
    //代理的ID
    String AGENTERID = "889931";
    //加密算法
    String COMPRESS = "DES";
    //自代理商密钥（.so）JNI
    String AGENTER_PASSWORD = "9ab62a694d8bf6ced1fab6acd48d02f8";
    //微信支付的APPId
    String APP_ID = "wx706f24d4d46ffe37";
    //des加密用密钥
    String DES_PASSWORD = "9b2648fcdfbad80f";
    /*百度省份证认证配置*/
    String API_KEY = "fCGdgS5qUZ7grFU2b0G72whN";//替换成自己的api_key
    String SECRET_KEY = "jDMSU2fWFstO5FcCbaGrcnjmPqXVjlGD";//请替换成你的Secret key
    String FilePash = "filePash";


    /*权限返回码*/
    int RP_CAMERA = 100; //相机权限
    int READ_EXTERNAL_STORAGE = 101; //读写权限
    int CALL_PHONE = 102; //读写权限

    /**
     * 是否登录
     */
    String IS_LOGIN = "isLogin";
    /**
     * 登录token
     **/
    String TOKEN = "token";
    /**
     * 推送Socket_ip(IP)
     */
    String SOCKET_IP = "Socket_ip";
    /**
     * 推送Socket_port(端口号)
     */
    String SOCKET_PORT = "Socket_port";
    /**
     * 金额
     **/
    String MONEY = "money";
    /**
     * 创建时间
     */
    String USERCREATETIME = "appUserCreateTime";
    /**
     * 头像
     */
    String USERHEADIMGURL = "appUserHeadImgUrl";
    /**
     * 用户id
     */
    String USER_ID = "appUserId";
    /**
     * 用户名
     */
    String USER_NAME = "appUserNickName";
    /**
     * 手机号
     */
    String USER_PHONE = "appUserPhone";
    /**
     * 二维码信息
     */
    String USERQRCODEURL = "appUserQrCodeUrl";
    /**
     * 用户实名认证标志
     **/
    String AUDIT = "audit";
    /**
     * 业主认证
     */
    String ISOWNER = "isOwner";
    /**
     * 用户的email
     */
    String EMAIL = "email";
    /**
     * 用户的密码 (暂时没用预留)
     */
    String PASSWORD = "password";
    /**
     * 微信授权登录
     **/
    String IS_BIND = "isbind";
    /**
     * 微信openId
     */
    String OPENID = "openId";
    /**
     * 充值订单id
     */
    String OUT_TRADE_NO = "out_trade_no";
    /**
     * 微信授权code
     **/
    String WEIXINCODE = "weixincode";
    /**
     * 微信提现弹框, 输入金额最小限制
     */
    Float WX_MANY_LIMIT = 0f;
    /***
     * 保存到本地图片的名字
     */
    String SAVE_PIC_NAME1 = "head1.jpg";
    /***
     * 保存到本地图片的名字
     */
    String SAVE_PIC_NAME2 = "head2.jpg";
    int REQUESTCODE = 102;

}
