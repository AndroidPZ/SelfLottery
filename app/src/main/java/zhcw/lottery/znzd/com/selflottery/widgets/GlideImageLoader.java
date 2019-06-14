package zhcw.lottery.znzd.com.selflottery.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;

import com.lqm.roundview.RoundImageView;
import com.youth.banner.loader.ImageLoader;

import zhcw.lottery.znzd.com.selflottery.util.ImgGlideUtil;

/**
 * 作者：XPZ on 2018/4/18 09:47.
 */
public class GlideImageLoader extends ImageLoader {
    private Bitmap mBitmapCover;

    @Override
    public void displayImage(Context context, Object path, final ImageView imageView) {
        //Glide 加载图片简单用法
        if (path.equals("")) {
            ImgGlideUtil.imgLoade(context, "", imageView);
        } else {
            ImgGlideUtil.imgLoade(context, path.toString(), imageView);
//            GlideApp.with(context)
//                    .load(path)//加载地址
////                    .asBitmap()
//                    .error(R.mipmap.default_banner)//加载错误
////                    .thumbnail( 0.5f )//缩略图
////                    .dontAnimate()//或者使用 dontAnimate() 关闭动画
//                    .placeholder(R.mipmap.default_banner)//展位图
//                    .into(imageView);
        }
    }

    //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public ImageView createImageView(Context context) {
        //需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
        RoundImageView imageView = new RoundImageView(context);
        imageView.setCornerRadius(10);
        return imageView;
    }
}
