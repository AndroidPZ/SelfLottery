package zhcw.lottery.znzd.com.selflottery.widgets.ImageLoade;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import zhcw.lottery.znzd.com.selflottery.config.Config;
import zhcw.lottery.znzd.com.selflottery.util.CreateBitmap;
import zhcw.lottery.znzd.com.selflottery.widgets.ImageLoade.blur.BlurTransformation;

/**
 * @ProjectName: nmgfc
 * @Package: com.zcsp.nmgfc.widget.ImageLoade
 * @ClassName: GlideImageLoaderStrategy
 * @Description: 类作用描述
 * @Author: 小七
 * @CreateDate: 2018/12/26 13:51
 * @Version: 1.0
 */
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy<ImageConfigImpl> {
    /**
     * 正常
     *
     * @param context
     * @param config
     */
    @Override
    public void displayImage(Context context, ImageConfigImpl config) {
        //这里实现Glide图片加载方法，也就是GlideApp.with(context)...那一串
        //config中拿出用户配置的参数 如 config.getBlurValue()
        if (config.isRound()) { //圆形
            RequestOptions options = RequestOptions.circleCropTransform();
            options.placeholder(config.getPlaceholder())
                    .error(config.getPlaceholder());
            Glide.with(context)
                    .load(config.getUrl())
                    .apply(options)
                    .into(config.getImageView());
        } else if (config.isBlur()) {  //模糊
            if(config.isResource()){ //模糊资源图片
                RequestOptions options = new RequestOptions();
                options.placeholder(config.getPlaceholder())
                        .transform(new BlurTransformation(config.getBlurValue()))
                        .error(config.getPlaceholder());
                Glide.with(context)
                        .load(config.resource)
                        .apply(options)
                        .into(config.getImageView());
            }else{ //模糊网络图片
                RequestOptions options = new RequestOptions();
                options.placeholder(config.getPlaceholder())
                        .transform(new BlurTransformation(config.getBlurValue()))
                        .error(config.getPlaceholder());
                Glide.with(context)
                        .load(config.getUrl())
                        .apply(options)
                        .into(config.getImageView());
            }
        }else if (config.isStag()) {
            RequestOptions options = new RequestOptions();
            options.placeholder(config.getPlaceholder())
                    .error(config.getPlaceholder());
            Glide.with(context)
                    .asBitmap()
                    .load(config.getUrl())
                    .apply(options)
                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                        @Override
                        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                            //这个bitmap就是你图片url加载得到的结果
                            //获取bitmap信息，可赋值给外部变量操作，也可在此时行操作。
                            //获取你要填充图片的布局的layoutParam
                            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) config.getImageView().getLayoutParams();
//                            layoutParams.height = (int) (((float) bitmap.getHeight()) / bitmap.getWidth() * ScreenUtils.getScreenWidth() / 2 );
                            //因为是2列,所以宽度是屏幕的一半,高度是根据bitmap的高/宽*屏幕宽的一半
                            layoutParams.width = Config.WIDTH;//这个是布局的宽度
                            layoutParams.height = (int) (((float) Config.WIDTH / bitmap.getWidth() * bitmap.getHeight()));
                            config.getImageView().setLayoutParams(layoutParams);//容器的宽高设置好了
                            bitmap = CreateBitmap.zoomImg(bitmap, layoutParams.width, layoutParams.height);
                            // 然后在改变一下bitmap的宽高
                            config.getImageView().setImageBitmap(bitmap);
                        }
                    });
        } else { // 默认
            if (context != null) {
                RequestOptions options = new RequestOptions();
                options.placeholder(config.getPlaceholder())
                        .error(config.getPlaceholder());
                Glide.with(context)
                        .load(config.getUrl())
                        .apply(options)
                        .into(config.getImageView());

            }

        }
    }
}
