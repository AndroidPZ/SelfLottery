package zhcw.lottery.znzd.com.selflottery.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Decoder.BASE64Encoder;
import zhcw.lottery.znzd.com.selflottery.App;
import zhcw.lottery.znzd.com.selflottery.config.Config;
import zhcw.lottery.znzd.com.selflottery.config.ConstantValue;
import zhcw.lottery.znzd.com.selflottery.widgets.AddGlideImageLoader;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by XPZ on 2017/5/15.
 * 工具
 */

public class Utils {
    private static HashMap<Integer, String> mIntegerArrayMap;
    private static HashMap<String, String> mCodeArrayMap;
    private static Map<DecodeHintType, Object> hintMap;
    private static List<BarcodeFormat> formatList;

    /**
     * 获取矩阵转换类 (利用反射 , 获取方法)
     *
     * @param view
     * @return
     */
    public static Matrix getViewMatrix(View view) {
        try {
            @SuppressLint("PrivateApi")
            Method getInverseMatrix = View.class.getDeclaredMethod("getInverseMatrix");
            getInverseMatrix.setAccessible(true);
            return (Matrix) getInverseMatrix.invoke(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断坐标点是否在view上 (利用反射 , 获取方法)
     *
     * @param view
     * @param points
     * @return
     */
    public static boolean pointInView(View view, float[] points) {
        try {
            @SuppressLint("PrivateApi")
            Method pointInView = View.class.getDeclaredMethod("pointInView", float.class, float.class);
            pointInView.setAccessible(true);
            return (boolean) pointInView.invoke(view, points[0], points[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 判断是否需要跟新
     *
     * @param versionName 当前版本名称versionName 非versionCode
     * @param versionNew  服务器的版本号
     * @return 是否需要跟新 : true 需要
     */
    public static boolean isUpDate(String versionName, String versionNew) {

        if (!String.valueOf(versionName).equals(versionNew)) {
            String[] splitNum = versionNew.split("\\.");
            String[] splitName = versionName.split("\\.");

            return Integer.valueOf(splitNum[2]) > Integer.valueOf(splitName[2]) ||
                    Integer.valueOf(splitNum[1]) > Integer.valueOf(splitName[1]) ||
                    Integer.valueOf(splitNum[0]) > Integer.valueOf(splitName[0]);
        } else {
            return false;
        }
    }

    /**
     * 判断网络是否连接
     *
     * @return
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager connectivity = (ConnectivityManager) App.getInstance().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 获取扫码格式
     *
     * @return
     */
    public static Map<DecodeHintType, Object> getCodeType() {
        hintMap = new EnumMap<>(DecodeHintType.class);
        formatList = new ArrayList<>();
        formatList.add(BarcodeFormat.PDF_417);
//        formatList.add(BarcodeFormat.QR_CODE);
        hintMap.put(DecodeHintType.POSSIBLE_FORMATS, formatList); // 可能的编码格式
//        hintMap.put(DecodeHintType.TRY_HARDER, Boolean.TRUE); // 花更多的时间用于寻找图上的编码，优化准确性，但不优化速度
        hintMap.put(DecodeHintType.CHARACTER_SET, "utf-8"); // 编码字符集
        return hintMap;
    }

    public static HashMap<Integer, String> getIntegerArrayMap() {
        if (mIntegerArrayMap == null) {
            mIntegerArrayMap = new HashMap<Integer, String>() {
                {
                    put(1, "机器已绑定并可用");
                    put(2, "业主认证成功但未绑定机器");
                    put(3, "机器信息未审核");
                    put(4, "业主认证失败");
                    put(5, "机器信息验证失败");
                    put(9, "机器禁用");
                }
            };
        }
        return mIntegerArrayMap;
    }

    public final static HashMap<String, String> getCodeArrayMap() {
        if (mCodeArrayMap == null) {
            mCodeArrayMap = new HashMap<String, String>() {
                {
                    put(" 小小", "00");
                    put(" 小大", "01");
                    put(" 大小", "02");
                    put(" 大大", "03");
                    put(" 单单", "04");
                    put(" 单双", "05");
                    put(" 双单", "06");
                    put(" 双双", "07");
                    put(" 小单", "08");
                    put(" 小双", "09");
                    put(" 单小", "10");
                    put(" 双小", "11");
                    put(" 大单", "12");
                    put(" 大双", "13");
                    put(" 单大", "14");
                    put(" 双大", "15");
                }
            };
        }
        return mCodeArrayMap;
    }

    /**
     * 几星:0    最后坐标:-1
     * 几星:1    最后坐标:0
     * 几星:2    最后坐标:2
     * 几星:3    最后坐标:4
     * 几星:4    最后坐标:6
     * <p>
     * 0为5星 同事满足对应后边的数组则为一注
     *
     * @param lotteryNum 需要判断注数的 SSC 单式
     * @return
     */
    public static int getSscDsCalc(String lotteryNum) {
        int xingType = getXingType(lotteryNum);
        int i = lotteryNum.lastIndexOf("_");
        if (xingType == 0 && i == -1) {
            return 1;
        }
        if (xingType == 1 && i == 0) {
            return 1;
        }
        if (xingType == 2 && i == 2) {
            return 1;
        }
        if (xingType == 3 && i == 4) {
            return 1;
        }
        if (xingType == 4 && i == 6) {
            return 1;
        }
        return 0;
    }

    /**
     * @param lotteryNum
     * @return 返回星级别 0 是五星 (0~4)
     */
    public static int getXingType(String lotteryNum) {
        String replace = lotteryNum.replace("_", "");
        return lotteryNum.length() - replace.length();
    }

    /**
     * 区分K3玩法类别 , 比如 二不同 , 三同号 , 三不同
     *
     * @param strings
     * @return
     */
    public static String Game_Type(String[] strings) {
        if (strings.length >= 3) {
            String b = strings[0];
            String s = strings[1];
            String g = strings[2];
            if (b.equals(s) && s.equals(g)) { //三同号
                return ConstantValue.K3_STH_DX;
            } else if (b.equals(s) || s.equals(g) || b.equals(g)) { //二同号
                return ConstantValue.K3_RTH_DX;
            } else { //三不同
                return ConstantValue.K3_SBT;
            }
        }
        return "";
    }

    /**
     * 获取手机IP
     *
     * @param context
     * @return
     */
    public static String getIP(Context context) {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return "";
    }


    /**
     * 发送微信支付请求
     *
     * @param context
     * @param appId
     * @param partnerid
     * @param prepayid
     * @param noncestr
     * @param timestamp
     * @param sign
     */
    public static void SendPay(Context context, String appId,
                               String partnerid, String prepayid, String noncestr,
                               String timestamp, String sign) {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
        if (!msgApi.isWXAppInstalled()) {
            ToastUtil.showShortToast("您可能没有安装微信!");
        } else {
            msgApi.registerApp(Config.APP_ID);
            //发起支付请求 , 调起微信支付
            PayReq request = new PayReq();
            request.appId = appId;
            request.partnerId = partnerid;
            request.prepayId = prepayid;  //提供
            request.packageValue = "Sign=WXPay";                    //写死
            request.nonceStr = noncestr;
            request.timeStamp = timestamp;
//        request.signType = "MD5";
            request.sign = sign;//最好后台提供
            msgApi.sendReq(request);
        }
    }

    /**
     * @return String
     * @Description ping接口，获取信息
     * @Author xpz
     */
    public static String ping() {
        String result = null;

        try {
            String ip = "www.baidu.com";
            // String ip = "180.149.131.98";
            Process p = Runtime.getRuntime().exec("ping -c 1 -w 2 " + ip);// ping3次
            // 读取ping的内容，可不加。
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));

            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
            Log.i("lbx", "result content : " + stringBuffer.toString());
            // PING的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "successful~";
                return stringBuffer.toString();
            } else {
                result = "failed~ cannot reach the IP address";
                return result;
            }
        } catch (IOException e) {
            result = "failed~ IOException";
        } catch (InterruptedException e) {
            result = "failed~ InterruptedException";
        } finally {
        }
        return result;
    }

    /**
     * @return int 网络强度
     * @Description 获取去前网络的链接反应时间 如果断开则返回0
     * @Author xpz
     */
    public static int getState() {
        int stateNum;
        String ping = ping();
        if ("failed".equals(ping)) {
            stateNum = 0;
        } else {
            int beginIndex = ping.indexOf("time=") + 5;
            int endIndex = beginIndex + 4;
            if (ping.length() > 10) {
                String state = ping.substring(beginIndex, endIndex);
                Logger.i("XPZping", "状态是************* " + state);
                try {
                    stateNum = (int) Double.parseDouble(state);
                } catch (NumberFormatException e) {
                    stateNum = 0;
                }
            } else {
                stateNum = 0;
            }
        }
        return stateNum;
    }

    /**
     * 发起微信授权
     *
     * @param context
     * @param id
     */
    public static void SendWxAuth(Context context, String id) {
        IWXAPI api = WXAPIFactory.createWXAPI(context, id, true);
        if (!api.isWXAppInstalled()) {
            ToastUtil.showShortToast("您可能没有安装微信!");
        } else {
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat";
            api.sendReq(req);
        }
    }

    /**
     * 添加照片配置
     *
     * @param maxImgCount
     */
    public static void initCertificationImagePicker(int maxImgCount, boolean isCrop) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new AddGlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setMultiMode(!isCrop);                     //图片选着模式，单选/多选
        imagePicker.setCrop(isCrop);                          //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(960);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(608);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(960);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(608);                         //保存文件的高度。单位像素
    }

    /**
     * 加载圆角图片
     *
     * @param image
     * @param imageUrl
     */
    public static void setRoundImage(SimpleDraweeView image, String imageUrl) {
        /**初始化圆角圆形参数对象**/
        RoundingParams rp = new RoundingParams();
        /**设置图像是否为圆形**/
        rp.setRoundAsCircle(true);
        /**设置圆角半径**/
        //rp.setCornersRadius(20);
        /**分别设置左上角、右上角、左下角、右下角的圆角半径**/
        //rp.setCornersRadii(20,25,30,35);
        /**分别设置（前2个）左上角、(3、4)右上角、(5、6)左下角、(7、8)右下角的圆角半径**/
        //rp.setCornersRadii(new float[]{20,25,30,35,40,45,50,55});
        /**设置边框颜色及其宽度**/
        rp.setBorder(Color.WHITE, 2);
        /**设置叠加颜色**/
        rp.setOverlayColor(Color.GRAY);
        /**设置圆形圆角模式**/
        //rp.setRoundingMethod(RoundingParams.RoundingMethod.BITMAP_ONLY);
        /**设置圆形圆角模式**/
        rp.setRoundingMethod(RoundingParams.RoundingMethod.OVERLAY_COLOR);

        /**获取GenericDraweeHierarchy对象**/
        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(App.getInstance().getResources())
                /**设置圆形圆角参数**/
                .setRoundingParams(rp)
                /**设置圆角半径**/
                // .setRoundingParams(RoundingParams.fromCornersRadius(20))
                /**分别设置左上角、右上角、左下角、右下角的圆角半径**/
                //.setRoundingParams(RoundingParams.fromCornersRadii(20,25,30,35))
                /**分别设置（前2个）左上角、(3、4)右上角、(5、6)左下角、(7、8)右下角的圆角半径**/
                //.setRoundingParams(RoundingParams.fromCornersRadii(new float[]{20,25,30,35,40,45,50,55}))
                /**设置圆形圆角参数；RoundingParams.asCircle()是将图像设置成圆形**/
                .setRoundingParams(RoundingParams.asCircle())
                /**设置淡入淡出动画持续时间(单位：毫秒ms)**/
                .setFadeDuration(1000)
                .build();
        image.setHierarchy(hierarchy);
        /**构建Controller**/
        DraweeController controller = Fresco.newDraweeControllerBuilder().setUri(imageUrl)
                /**设置需要下载的图片地址**/
                /**设置点击重试是否开启**/
                .setTapToRetryEnabled(true)
                .build();
        /**设置Controller**/
        image.setController(controller);
    }

    /**
     * 加载本地圆角图片
     *
     * @param image
     * @param imageUrl
     */
    public static void setlocalRoundImage(SimpleDraweeView image, Uri imageUrl) {
        /**初始化圆角圆形参数对象**/
        RoundingParams rp = new RoundingParams();
        /**设置图像是否为圆形**/
        rp.setRoundAsCircle(true);
        /**设置圆角半径**/
        //rp.setCornersRadius(20);
        /**分别设置左上角、右上角、左下角、右下角的圆角半径**/
        //rp.setCornersRadii(20,25,30,35);
        /**分别设置（前2个）左上角、(3、4)右上角、(5、6)左下角、(7、8)右下角的圆角半径**/
        //rp.setCornersRadii(new float[]{20,25,30,35,40,45,50,55});
        /**设置边框颜色及其宽度**/
        rp.setBorder(Color.WHITE, 2);
        /**设置叠加颜色**/
        rp.setOverlayColor(Color.GRAY);
        /**设置圆形圆角模式**/
        //rp.setRoundingMethod(RoundingParams.RoundingMethod.BITMAP_ONLY);
        /**设置圆形圆角模式**/
        rp.setRoundingMethod(RoundingParams.RoundingMethod.OVERLAY_COLOR);

        /**获取GenericDraweeHierarchy对象**/
        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(App.getInstance().getResources())
                /**设置圆形圆角参数**/
                .setRoundingParams(rp)
                /**设置圆角半径**/
                // .setRoundingParams(RoundingParams.fromCornersRadius(20))
                /**分别设置左上角、右上角、左下角、右下角的圆角半径**/
                //.setRoundingParams(RoundingParams.fromCornersRadii(20,25,30,35))
                /**分别设置（前2个）左上角、(3、4)右上角、(5、6)左下角、(7、8)右下角的圆角半径**/
                //.setRoundingParams(RoundingParams.fromCornersRadii(new float[]{20,25,30,35,40,45,50,55}))
                /**设置圆形圆角参数；RoundingParams.asCircle()是将图像设置成圆形**/
                .setRoundingParams(RoundingParams.asCircle())
                /**设置淡入淡出动画持续时间(单位：毫秒ms)**/
                .setFadeDuration(1000)
                .build();
        image.setHierarchy(hierarchy);
        /**构建Controller**/
        DraweeController controller = Fresco.newDraweeControllerBuilder().setUri(imageUrl)
                /**设置需要下载的图片地址**/
                /**设置点击重试是否开启**/
                .setTapToRetryEnabled(true)
                .build();
        /**设置Controller**/
        image.setController(controller);
    }

    //授权文件（安全模式）
    //此种身份验证方案使用授权文件获得AccessToken，缓存在本地。建议有安全考虑的开发者使用此种身份验证方式。
    public static void initAccessToken() {
        OCR.getInstance().initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                // 调用成功，返回AccessToken对象
                String token = accessToken.getAccessToken();
                Logger.i("xpz", "token:-------->" + token);
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                Logger.i("xpz", "onError:licence方式获取token失败---->" + error.getMessage());
            }
        }, App.getInstance());
    }

    public static String replaceStr(String phonenum) {

        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(phonenum) && phonenum.length() > 6) {
            for (int i = 0; i < phonenum.length(); i++) {
                char c = phonenum.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 文件大小格式化
     *
     * @param size
     * @return
     */
    public static String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }


    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 手机号正则验证
     *
     * @param mobiles 电话号码
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][3456789]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    /**
     * 验证日期字符串是否是YYYY-MM-DD格式
     *
     * @param str
     * @return
     */
    public static boolean isDataFormat(String str) {
        boolean flag = false;
        //String regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
        String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        Pattern pattern1 = Pattern.compile(regxStr);
        Matcher isNo = pattern1.matcher(str);
        if (isNo.matches()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 将20140202 format为 2010年02月02日
     *
     * @param data
     * @return
     */
    public static String dataFormatStr(String data) {
        Date parse = null;
        String ds = "";
        try {
            parse = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).parse(data);
            ds = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault()).format(parse);
            return ds;
        } catch (Exception e) {
            e.printStackTrace();
            ds = data;
        }
        Logger.i(ds);
        return ds;
    }

    /**
     * 将时间的毫秒值 format为 2010年02月02日 05:30:15
     *
     * @param data
     * @return
     */
    public static String longFormatStr(Long data) {
        Date parse = null;
        String ds = "";
        try {
            Date d = new Date(data);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");//yyyy年MM月dd日 HH:mm:ss:SS
            ds = sdf.format(d);
            return ds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public static String longFormatStrYMD(Long data) {
        Date parse = null;
        String ds = "";
        try {
            Date d = new Date(data);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");//yyyy年MM月dd日 HH:mm:ss:SS
            ds = sdf.format(d);
            return ds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public static String longFormatStr(Date data) {
        String ds = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");//yyyy年MM月dd日 HH:mm:ss:SS
            ds = sdf.format(data);
            return ds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }


    /**
     * @param str
     * @return String
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @Description 对字符串进行MD5加密
     * @Exception
     * @Author lbx
     * @Date 2017-3-6 上午9:56:17
     */
    public static String EncoderByMd5(String str)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        // 加密后的字符串
        String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }

    // MD5加密，32位
    public static String MD5(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 判断添加token
     *
     * @param voteUrl
     * @param token
     * @return
     */
    public static String getAddTokenUrl(String voteUrl, String token) {
        String url = "";
        if (voteUrl.contains("?")) {
            url = voteUrl + "&token=" + token;
        } else {
            url = voteUrl + "?token=" + token;
        }
        return url;

    }

    public static boolean isTopActivity(Context context) {
        boolean isTop = false;
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        Logger.d("XPZ", "isTopActivity = " + cn.getClassName());
        if (cn.getClassName().contains("zhcw.lottery.bob.com.selflottery.ui.MainAdminActivity")) {
            isTop = true;
        }
        Logger.d("XPZ", cn.getClassName() + "--isTop = " + isTop);
        return isTop;
    }

    /**
     * 功能：身份证的有效验证
     *
     * @param IDStr 身份证号
     * @return 有效：返回 ""   ;  无效：返回 String 信息
     * @throws ParseException
     */
    public static String IDCardValidate(String IDStr) {
        String errorInfo = "";// 记录错误信息
        String[] ValCodeArr = {"1", "0", "x", "9", "8", "7", "6", "5", "4",
                "3", "2"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2"};
        String Ai = "";
        String Ai1 = "";
        String Ai2 = "";
        // ================ 号码的长度 15位或18位 ================
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            errorInfo = "身份证号码长度应该为15位或18位。";
            return errorInfo;
        }
        // =======================(end)========================

        // ================ 数字 除最后以为都为数字 ================
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else {//if (IDStr.length() == 15)
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (!isNumeric(Ai)) {
            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
            return errorInfo;
        }
        // =======================(end)========================

        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (!isDataFormat(strYear + "-" + strMonth + "-" + strDay)) {
            errorInfo = "身份证生日无效。";
            return errorInfo;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(
                    strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                errorInfo = "身份证生日不在有效范围。";
                return errorInfo;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            errorInfo = "身份证月份无效";
            return errorInfo;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            errorInfo = "身份证日期无效";
            return errorInfo;
        }
        // =====================(end)=====================

        // ================ 地区码时候有效 ================
//        Hashtable h = GetAreaCode();
        HashMap h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            errorInfo = "身份证地区编码错误。";
            return errorInfo;
        }
        // ==============================================

        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
//        Ai = Ai + strVerifyCode;
        Ai1 = Ai + strVerifyCode.toUpperCase();
        Ai2 = Ai + strVerifyCode.toLowerCase();

        if (IDStr.length() == 18) {
            if (!Ai1.equals(IDStr) && !Ai2.equals(IDStr)) {
                errorInfo = "身份证无效，不是合法的身份证号码";
                return errorInfo;
            }
        } else {
            return "";
        }
        // =====================(end)=====================
        return "";
    }

    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    private static HashMap<String, String> GetAreaCode() {
//        Hashtable hashtable = new Hashtable();
        HashMap<String, String> hashtable = new HashMap<>();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

}
