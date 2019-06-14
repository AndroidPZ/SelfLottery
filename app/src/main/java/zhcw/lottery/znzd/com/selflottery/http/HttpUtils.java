package zhcw.lottery.znzd.com.selflottery.http;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;

import java.io.File;

import zhcw.lottery.znzd.com.selflottery.http.callback.AppStringCallback;
import zhcw.lottery.znzd.com.selflottery.http.callback.StringDialogCallback;
import zhcw.lottery.znzd.com.selflottery.util.Logger;

/**
 * Created by XPZ on 2017/10/10.
 */

public class HttpUtils {
    /*
     * 版本更新
     * @param context
     * @param appVersionNum
     * @param callback
     */
    public static void getVersionUpdateRequest(Context context, AppStringCallback callback) {
        OkGo.<String>post(Urls.VersionUpdate).tag(context)
                .params("type", "Android")
                .execute(callback);
    }

    /**
     * 登陆
     *
     * @param context
     * @param json
     * @param callback
     */
    public static void sendLogingJson(Context context, String json, StringDialogCallback callback) {
        OkGo.<String>post(Urls.login).tag(context)
                .params("jsonparams", json)
                .execute(callback);
    }

    /**
     * 首页轮播图接口
     *
     * @param context
     * @param cityId
     * @param callback
     */
    public static void getBannerDataRequest(Context context, int cityId, StringDialogCallback callback) {
        OkGo.<String>post(Urls.HomeBanner).tag(context)
                .params("cityId", cityId)
                .execute(callback);
    }


    /**
     * 充值
     *
     * @param context
     * @param json
     * @param callback
     */
    public static void sendRechargeJson(Context context, String json, StringDialogCallback callback) {
        OkGo.<String>post(Urls.Recharge).tag(context)
                .params("jsonparams", json)
                .execute(callback);
    }

    /**
     * 兑奖
     *
     * @param context
     * @param json
     * @param callback
     */
    public static void sendBonusRechargeJson(Context context, String json, StringDialogCallback callback) {
        OkGo.<String>post(Urls.BonusRecharge).tag(context)
                .params("jsonparams", json)
                .execute(callback);
    }

    /**
     * 查询余额
     *
     * @param context
     * @param token
     * @param callback
     */
    public static void GetAccount(Context context, String token, AppStringCallback callback) {
        OkGo.<String>post(Urls.Get_Account).tag(context)
                .params("token", token)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(callback);
    }


    /**
     * 上传彩票的JSON数据
     *
     * @param context
     * @param json
     * @param callback
     */
    public static void sendLotteryJson(Context context, String json, StringDialogCallback callback) {
        OkGo.<String>post(Urls.lotteryInfo).tag(context)
                .params("jsonparams", json)
                .execute(callback);
    }

    /**
     * 获取短信验证码
     *
     * @param context
     * @param number
     * @param callback
     */
    public static void getTextCode(Context context, String number, StringDialogCallback callback) {
        OkGo.<String>post(Urls.GetCode).tag(context)
                .params("phone", number)
                .params("type", "0")
                .execute(callback);
    }


    /**
     * 请求订单详情数据
     *
     * @param context
     * @param callback
     */
    public static void sendDetailsJson(Context context, String token, StringDialogCallback callback) {
        OkGo.<String>post(Urls.Verification).tag(context)
                .params("jsonparams", String.format("{\"token\":\"%s\"}", token))
                .execute(callback);
    }

    /**
     * 删除单详情数据
     *
     * @param context
     * @param callback
     */
    public static void deleteOrder(Context context, String token, String orderNums, StringDialogCallback callback) {
        OkGo.<String>post(Urls.DeleteOrder).tag(context)
                .params("jsonparams", String.format("{\"token\":\"%s\",\"orderNums\":\"%s\"}", token, orderNums))
                .execute(callback);
    }

    /**
     * 请求奖期
     *
     * @param context
     * @param type     ALL代表获取所有彩种、ssq、3d、k3、307、nmssc
     * @param callback
     */
    public static void getJiangQi(Context context, String type, StringDialogCallback callback) {
        OkGo.<String>post(Urls.Get_JiangQi).tag(context)
                .params("gameName", type)
                .execute(callback);
    }

    /**
     * 请求用户信息(是否实名 , 是否认证业主 , 是否微信授权等)
     *
     * @param context
     * @param token
     * @param callback
     */
    public static void getUserInfo(Context context, String token, StringDialogCallback callback) {
        OkGo.<String>post(Urls.UserInfo).tag(context)
                .params("jsonparams", String.format("{\"token\":\"%s\"}", token))
                .execute(callback);
    }


    /**
     * 实名认证数据查询
     *
     * @param context
     * @param token
     * @param callback
     */
    public static void getDefaultDataRequest(Context context, String token, StringDialogCallback callback) {
        OkGo.<String>post(Urls.Certification_default).tag(context)
                .params("token", token)
                .execute(callback);
    }

    /**
     * 实名认证
     *
     * @param context
     * @param token
     * @param pic_z
     * @param pic_f
     */
    public static void getCertificationDataRequest(Context context, String token, File pic_z, File pic_f, StringDialogCallback callback) {
        OkGo.<String>post(Urls.Certification).tag(context)
                .params("token", token)
                .params("pic_z", pic_z)
                .params("pic_f", pic_f)
                .execute(callback);
    }

    /**
     * 获取账户明细
     *
     * @param context
     * @param token
     * @param start   第几页 需要累加
     */
    public static void getDetailRequest(Context context, String token, int start, StringDialogCallback callback) {
        OkGo.<String>post(Urls.Detail).tag(context)
                .cacheMode(CacheMode.NO_CACHE)
                .params("token", token)
                .params("start", start)
                .execute(callback);
    }

    /**
     * 业主佣金(机器销售情况查询)
     *
     * @param context
     * @param callback
     */
    public static void getSaleRecordJson(Context context, String token, StringDialogCallback callback) {
        OkGo.<String>post(Urls.SaleRecord).tag(context)
                .params("jsonparams", String.format("{\"token\":\"%s\"}", token))
                .cacheMode(CacheMode.NO_CACHE)
                .execute(callback);
    }


    /**
     * 业主佣金提现
     *
     * @param context
     * @param callback
     */
    public static void getApplyJson(Context context, String token, StringDialogCallback callback) {
        OkGo.<String>post(Urls.Apply).tag(context)
                .params("jsonparams", String.format("{\"token\":\"%s\"}", token))
                .execute(callback);
    }

    /**
     * 获取业主认证信息
     *
     * @param context
     * @param callback
     */
    public static void getOwnerJson(Context context, String token, StringDialogCallback callback) {
        OkGo.<String>post(Urls.OwnerInfo).tag(context)
                .params("jsonparams", String.format("{\"token\":\"%s\"}", token))
                .execute(callback);
    }

    /**
     * 业主提交认证信息接口(认证)
     *
     * @param context
     * @param callback
     */
    public static void getOwnerBindJson(Context context, String json, StringDialogCallback callback) {
        OkGo.<String>post(Urls.OwnerBind).tag(context)
                .params("jsonparams", json)
                .execute(callback);
    }

    /**
     * 业主获取绑定二维码接口
     *
     * @param context
     * @param callback
     */
    public static void getBindQrcode(Context context, String token, StringDialogCallback callback) {
        OkGo.<String>post(Urls.BindQrcode).tag(context)
                .params("jsonparams", String.format("{\"token\":\"%s\"}", token))
                .execute(callback);
    }

    /**
     * 充值下单接口
     *
     * @param context
     * @param callback
     */
    public static void getUnifiedOrder(Context context, String json, StringDialogCallback callback) {
        OkGo.<String>post(Urls.UnifiedOrder).tag(context)
                .params("jsonparams", json)
                .execute(callback);
    }

    /**
     * 查询微信支付订单状态接口
     *
     * @param context
     * @param token
     * @param out_trade_no
     * @param callback
     */
    public static void getQueryOrder(Context context, String token, String out_trade_no, StringDialogCallback callback) {
        String format = String.format("{ \"token\":\"%s\",\"out_trade_no\":\"%s\"}", token, out_trade_no);
        Logger.i(format);
        OkGo.<String>post(Urls.QueryOrder).tag(context)
                .params("jsonparams", format)
                .execute(callback);
    }

    /**
     * 回传code
     *
     * @param context
     * @param token
     * @param code
     * @param callback
     */
    public static void getWechatCode(Context context, String token, String code, StringDialogCallback callback) {
        String format = String.format("{ \"token\":\"%s\",\"code\":\"%s\"}", token, code);
        Logger.i(format);
        OkGo.<String>post(Urls.WechatCode).tag(context)
                .params("jsonparams", format)
                .execute(callback);
    }

    /**
     * 微信申请提现接口
     *
     * @param context
     * @param token
     * @param amount
     * @param callback
     */
    public static void getWeAddPreOrder(Context context, String token, String amount, StringDialogCallback callback) {
        String format = String.format("{ \"token\":\"%s\",\"amount\":\"%s\"}", token, amount);
        Logger.i(format);
        OkGo.<String>post(Urls.WeAddPreOrder).tag(context)
                .params("jsonparams", format)
                .execute(callback);
    }

/*__________________________________分割线_____________________________________*/

    /**
     * 站点管理(管理员) 不需要缓存
     *
     * @param context
     * @param callback
     */
    public static void sendSiteNoCache(Context context, String userId, int page, StringDialogCallback callback) {
        OkGo.<String>post(Urls.Welcome).tag(context)
                .cacheMode(CacheMode.NO_CACHE)
                .params("userId", userId)
                .params("jsonparams", page)
                .execute(callback);
    }

    /**
     * 消息(业主) 需要缓存
     *
     * @param context
     * @param callback
     */
    public static void sendYzMsgCache(Context context, String siteID, int page, StringDialogCallback callback) {
        OkGo.<String>post(Urls.HOST).tag(context)
                .cacheKey("MsgOwnerFragment")
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .headers("aaa", "aaa")
                .params("siteID", siteID)
                .params("page", page)
                .execute(callback);
    }

}
