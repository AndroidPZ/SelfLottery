package zhcw.lottery.znzd.com.selflottery.util;

import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import zhcw.lottery.znzd.com.selflottery.BuildConfig;

/**
 * XPZ
 * Created by dell on 2017/4/26.
 */

public class Logger {

    //控制 所有自定log的显示
    private static final boolean showLog = BuildConfig.LOG_DEBUG;

    private static String path;
    private static File file;
    private static FileOutputStream outputStream;
    private static String pattern = "yyyy-MM-dd HH:mm:ss";

    static {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            path = externalStorageDirectory.getAbsolutePath() + "/zcw/";
            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            file = new File(new File(path), "log.txt");
            Log.i("SDCAEDTAG", path);
            try {
                outputStream = new FileOutputStream(file, true);
            } catch (FileNotFoundException e) {

            }
        } else {

        }
    }

    /**
     * 输出info级别的log信息，log中的tag和msg可以传任意对象
     * 默认的Tag为Logger.class 类名
     */
    public static void i(@Nullable Object objMsg) {
        if (showLog) {
            String tag = convertStringTag("xpz");
            String msg = convertStringMsg(objMsg);
            Log.i(tag, msg);
        }
    }

    /**
     * 把Object类型的tag转换为String类型的tag
     */
    private static String convertStringTag(Object objTag) {
        String tag;
        if (objTag == null) {
            tag = "xpz";
        } else if (objTag instanceof String) {
            tag = (String) objTag;
        } else if (objTag instanceof Class) {
            tag = ((Class<?>) objTag).getSimpleName();    // 如果objTag不是String，则取它的类名
        } else {
            tag = objTag.getClass().getSimpleName();    // 取类名
        }
        return tag;
    }

    /**
     * 把对象类型的Msg转换为String类型的Msg
     */
    private static String convertStringMsg(Object objMsg) {
        String msg;
        if (objMsg == null) {
            msg = "nullMsg";
        } else {
            msg = objMsg.toString();
        }
        return msg;
    }

    /**
     * 输出info级别的log信息，log中的tag和msg可以传任意对象
     */
    public static void i(@Nullable Object objTag, @Nullable Object objMsg) {
        if (showLog) {
            String tag = convertStringTag(objTag);
            String msg = convertStringMsg(objMsg);

            //信息太长,分段打印
            //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
            //  把4*1024的MAX字节打印长度改为2001字符数
            int maxStrLength = 2048 - tag.length();
            //大于4000时
            while (msg.length() > maxStrLength) {
                Log.i(tag, msg.substring(0, maxStrLength));
                msg = msg.substring(maxStrLength);
            }
            Log.i(tag, msg);
        }
    }

    public static void e(Object objTag, Object objMsg, Throwable e) {
        if (showLog) {
            String tag = convertStringTag(objTag);
            String msg = convertStringMsg(objMsg);

            //信息太长,分段打印
            //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
            //  把4*1024的MAX字节打印长度改为2001字符数
            int maxStrLength = 2048 - tag.length();
            //大于4000时
            while (msg.length() > maxStrLength) {
                Log.i(tag, msg.substring(0, maxStrLength));
                msg = msg.substring(maxStrLength);
            }
            Log.e(tag, msg, e);
        }
    }
    public static void d(Object objTag, Object objMsg) {
        if (showLog) {
            String tag = convertStringTag(objTag);
            String msg = convertStringMsg(objMsg);

            //信息太长,分段打印
            //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
            //  把4*1024的MAX字节打印长度改为2001字符数
            int maxStrLength = 2048 - tag.length();
            //大于4000时
            while (msg.length() > maxStrLength) {
                Log.i(tag, msg.substring(0, maxStrLength));
                msg = msg.substring(maxStrLength);
            }
            Log.d(tag, msg);
        }
    }

    /**
     * log写入SDcade
     *
     * @param clazz
     * @param msg
     */
    public static void infoToSdCade(Class clazz, String msg) {
        Date date = new Date();
        String time = DateFormatUtils.format(date, pattern);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (outputStream != null) {
                try {
                    outputStream.write(time.getBytes());
                    String className = "";
                    if (clazz != null) {
                        className = clazz.getSimpleName();
                    }
                    outputStream.write(("    " + className + "\r\n").getBytes());

                    outputStream.write(msg.getBytes());
                    outputStream.write("\r\n".getBytes());
                    outputStream.flush();
                } catch (IOException e) {

                }
            } else {
                Log.i("SDCAEDTAG", "file is null");
            }
        }
    }
}



