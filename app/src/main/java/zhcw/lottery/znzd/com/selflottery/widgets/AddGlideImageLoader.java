package zhcw.lottery.znzd.com.selflottery.widgets;

import android.app.Activity;
import android.widget.ImageView;

import com.lzy.imagepicker.loader.ImageLoader;

import zhcw.lottery.znzd.com.selflottery.util.ImgGlideUtil;

/**
 * Created by xpz on 2018/4/20.
 * 图片选择器
 */

public class AddGlideImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        ImgGlideUtil.imgLoade(activity,path,imageView);
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        ImgGlideUtil.imgLoade(activity,path,imageView);
    }

    @Override
    public void clearMemoryCache() {
    }
}
