package zhcw.lottery.znzd.com.selflottery.widgets.ImageLoade;

import android.widget.ImageView;

/**
 * @ClassName: ImageConfig
 * @Description: 图片加载的基类
 * @CreateDate: 2018/12/26 13:47
 * @Version: 1.0
 */
public class ImageConfig {
    protected String url;
    protected ImageView imageView;
    protected int placeholder;//占位符
    protected boolean isRound;//是否圆形 默认正常
    protected boolean isBlur;//是否模糊 默认正常
    protected boolean isStag;
    protected boolean isResource;
    protected int resource;
    public String getUrl() {
        return url;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getPlaceholder() {
        return placeholder;
    }
    public boolean isRound() {
        return isRound;
    }
    public boolean isBlur() {
        return isBlur;
    }
    public boolean isStag() {
        return isStag;
    }
    public boolean isResource() {
        return isResource;
    }
}
