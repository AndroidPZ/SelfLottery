package zhcw.lottery.znzd.com.selflottery.util;

import android.content.Context;

import com.easytools.tools.TimeUtil;

import java.io.File;

import static com.easytools.tools.TimeUtil.FORMAT_FULL_SN;

/**
 * Created by XPZ on 2018/9/29.
 */

public class File_Utils {
    /**
     * 用于表示临时存储的文件
     *
     * @param context
     * @param type
     * @return
     */
    public static File getSaveFile(Context context, String type) {
        String path = "pic_" + TimeUtil.getCurDateStr(FORMAT_FULL_SN) + type + ".jpg";
        File file = new File(context.getFilesDir(), path);
        return file;
    }

    /**
     * 遍历文件下所有文件
     *
     * @param fileAbsolutePath
     * @return
     */
//    public static Vector<String> getFileName(String fileAbsolutePath) {
//        Vector<String> vecFile = new Vector<String>();
//        File file = new File(fileAbsolutePath);
//        if (!file.exists()) {//判断文件夹是否存在
//            return null;
//        }
//        File[] subFile = file.listFiles();
//        if (subFile == null) {//判断权限
//            return null;
//        }
//        for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
//            // 判断是否为文件夹
//            if (!subFile[iFileLength].isDirectory()) {
//                String filename = subFile[iFileLength].getName();
//                vecFile.add(filename);
//                Logger.d("xpz", "文件名 ： " + filename);
//            }
//        }
//        return vecFile;
//    }
}
