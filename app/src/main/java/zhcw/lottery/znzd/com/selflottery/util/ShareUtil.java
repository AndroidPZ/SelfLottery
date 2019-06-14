package zhcw.lottery.znzd.com.selflottery.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import zhcw.lottery.znzd.com.selflottery.App;
import zhcw.lottery.znzd.com.selflottery.R;

/**
 * Created by xpz on 2019/1/7.
 * <p>
 * 系统分享类
 */
public class ShareUtil {

    private static final String EMAIL_ADDRESS = App.getInstance()
            .getResources().getString(R.string.setting_email);

    /**
     * 分享文本(直接调用手机系统所有可分享的)
     *
     * @param context
     * @param text
     * @param title
     */
    public static void shareText(Context context, String text, String title) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(intent, title));
    }

    /**
     * 简单-调起邮箱
     *
     * @param context
     * @param title
     */
    public static void sendEmail(Context context, String title) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(
                String.format("mailto:%s", EMAIL_ADDRESS)));
        context.startActivity(Intent.createChooser(intent, title));
    }

    /**
     * 详细-邮件分享
     *
     * @param mContext
     * @param title    邮件的标题
     * @param text     邮件的内容
     * @return
     */
    public static void sendMail(Context mContext, String title, String text) {
        // 调用系统发邮件
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // 设置文本格式
        emailIntent.setType("text/plain");
        // 设置对方邮件地址
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "");
        // 设置标题内容
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        // 设置邮件文本内容
        emailIntent.putExtra(Intent.EXTRA_TEXT, text);
        mContext.startActivity(Intent.createChooser(emailIntent, "Choose Email Client"));
    }


    /**
     * 短信分享(不指定手机号)
     *
     * @param mContext
     * @param smstext  短信分享内容
     * @return
     */
    public static Boolean sendSms(Context mContext, String smstext) {
        Intent mIntent = new Intent(Intent.ACTION_SENDTO,
                Uri.parse("smsto:"));
        mIntent.putExtra("sms_body", smstext);
        mContext.startActivity(mIntent);
        return null;
    }

    /**
     * 短信分享(指定手机号)
     *
     * @param mContext
     * @param smstext  短信分享内容
     * @return
     */
    public static Boolean sendSms(Context mContext, String smstext, String phoneNumber) {
        Intent mIntent = new Intent(Intent.ACTION_SENDTO,
                Uri.parse(String.format("smsto:%s", phoneNumber)));
        mIntent.putExtra("sms_body", smstext);
        mContext.startActivity(mIntent);
        return null;
    }

    /**
     * 拨打电话(需要指定号码)
     *
     * @param mContext
     * @param phoneNumber
     */
    public static void callPhone(Context mContext, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL,
                Uri.parse(String.format("tel:%s", phoneNumber)));
        mContext.startActivity(intent);
    }

}
