package zhcw.lottery.znzd.com.selflottery.widgets.ImageLoade;

import android.widget.ImageView;

/**
 * @ClassName: ImageConfigImpl
 * @Description: 类作用描述
 * @CreateDate: 2018/12/26 13:48
 * @Version: 1.0
 */
public class ImageConfigImpl extends ImageConfig {
    private int blurValue;//高斯模糊值, 值越大模糊效果越大

    //建造者模式来构建配置信息这个对象
    private ImageConfigImpl(Builder builder) {
        this.url = builder.url;
        this.imageView = builder.imageView;
        this.placeholder = builder.placeholder;
        this.blurValue = builder.blurValue;
        this.isRound = builder.isRound;
        this.isBlur = builder.isBlur;
        this.isStag = builder.isStag;
        this.isResource = builder.isResource;
        this.resource = builder.resource;
    }

    public int getBlurValue() {
        return blurValue;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String url;
        private ImageView imageView;
        private int blurValue;//高斯模糊值, 值越大模糊效果越大
        private int placeholder;
        private boolean isRound;
        private boolean isBlur;
        private boolean isStag;
        private boolean isResource;
        private int resource;

        private Builder() {
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder placeholder(int placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        /**
         * 是否加载圆形图片
         * @param isRound
         * @return
         */
        public Builder setRound(boolean isRound) {
            this.isRound = isRound;
            return this;
        }
        public Builder setBlur(boolean isBlur) {
            this.isBlur = isBlur;
            return this;
        }

        public Builder isResource(boolean isResource) {
            this.isResource = isResource;
            return this;
        }

        public Builder setResource(int resource) {
            this.resource = resource;
            return this;
        }


        public Builder setStag(boolean isStag) {
            this.isStag = isStag;
            return this;
        }

        public Builder imageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder blurValue(int blurValue) { //blurValue 建议设置为 15
            this.blurValue = blurValue;
            return this;
        }

        //构造出ImageConfigImpl对象
        public ImageConfigImpl build() {
            return new ImageConfigImpl(this);
        }
    }
}