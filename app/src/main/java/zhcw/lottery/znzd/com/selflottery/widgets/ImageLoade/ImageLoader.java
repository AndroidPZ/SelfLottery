package zhcw.lottery.znzd.com.selflottery.widgets.ImageLoade;

import android.content.Context;


/**
 * @ClassName: ImageLoader
 * @Description: 类作用描述
 * @CreateDate: 2018/12/26 13:55
 * @Version: 1.0
 */
public final class ImageLoader {

    private BaseImageLoaderStrategy<ImageConfig> mStrategy;
    /**
     * 加载图片
     */
    public  void loadImage(Context context, ImageConfig config) {
        if(this.mStrategy==null){
            setLoadImgStrategy(new GlideImageLoaderStrategy());
        }
        this.mStrategy.displayImage(context,  config);
    }

    //设置上你的策略实现类对象，让它去调自己的displayImage()方法
    public void setLoadImgStrategy(BaseImageLoaderStrategy strategy) {
        this.mStrategy = strategy;
    }

    public BaseImageLoaderStrategy getLoadImgStrategy() {
        return mStrategy;
    }
}

