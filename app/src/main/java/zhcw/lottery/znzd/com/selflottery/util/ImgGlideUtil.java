package zhcw.lottery.znzd.com.selflottery.util;

import android.content.Context;
import android.widget.ImageView;

import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.widgets.ImageLoade.ImageConfigImpl;
import zhcw.lottery.znzd.com.selflottery.widgets.ImageLoade.ImageLoader;


/**
 * @ClassName: ImgGlideUtil
 * @Description: 图片加载工具类
 * @CreateDate: 2018/12/25 17:09
 * @Version: 1.0
 */
public class ImgGlideUtil {

    public static void imgLoade(Context context, String url, ImageView imageViewi) {
        ImageLoader imageLoader = new ImageLoader();
        imageLoader.loadImage(context,
                ImageConfigImpl
                        .builder()
                        .placeholder(R.mipmap.default_banner)
                        .url(url)
                        .imageView(imageViewi)
                        .build());
    }

    public static void imgLoade(Context context, String url, ImageView imageViewi,int placeholder) {
        ImageLoader imageLoader = new ImageLoader();
        imageLoader.loadImage(context,
                ImageConfigImpl
                        .builder()
                        .placeholder(placeholder)
                        .url(url)
                        .imageView(imageViewi)
                        .build());
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param url
     * @param imageViewi
     */
    public static void imgLoadeRound(Context context, String url, ImageView imageViewi) {
        ImageLoader imageLoader = new ImageLoader();
        imageLoader.loadImage(context,
                ImageConfigImpl
                        .builder()
                        .setRound(true)
                        .url(url)
                        .imageView(imageViewi)
                        .build());
    }


    /**
     * 加载模糊图片 建议模糊度14
     *
     * @param context
     * @param value
     * @param url
     * @param imageViewi
     */
    public static void imgLoadeBlur(Context context, int value, String url, ImageView imageViewi) {
        ImageLoader imageLoader = new ImageLoader();
        imageLoader.loadImage(context,
                ImageConfigImpl
                        .builder()
                        .placeholder(R.mipmap.default_banner)
                        .setBlur(true)
                        .blurValue(value)
                        .url(url)
                        .imageView(imageViewi)
                        .build());
    }

    public static void imgLoadeResourceBlur(Context context, int value, int resource, ImageView imageViewi) {
        ImageLoader imageLoader = new ImageLoader();
        imageLoader.loadImage(context,
                ImageConfigImpl
                        .builder()
                        .isResource(true)
                        .setBlur(true)
                        .blurValue(value)
                        .setResource(resource)
                        .imageView(imageViewi)
                        .build());
    }


    /**
     * 带有默认占位图   的 圆形图片
     *
     * @param context
     * @param url
     * @param imageView
     * @param errorImage
     */
    public static void imgLoadeRoundError(Context context, String url, ImageView imageView, int errorImage) {
        ImageLoader imageLoader = new ImageLoader();
        imageLoader.loadImage(context,
                ImageConfigImpl
                        .builder()
                        .setRound(true)
                        .placeholder(errorImage)
                        .url(url)
                        .imageView(imageView)
                        .build());
    }

    //特殊的图片加载处理
    public static void imgLoadeStag(Context context, String url, ImageView imageViewi) {
        ImageLoader imageLoader = new ImageLoader();
        imageLoader.loadImage(context,
                ImageConfigImpl
                        .builder()
                        .placeholder(R.mipmap.default_banner)
                        .url(url)
                        .setStag(true)
                        .imageView(imageViewi)
                        .build());
    }
}
