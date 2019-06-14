package zhcw.lottery.znzd.com.selflottery.widgets.ImageLoade;

import android.content.Context;

/**
 * @ProjectName: nmgfc
 * @Package: com.zcsp.nmgfc.widget.ImageLoade
 * @ClassName: BaseImageLoaderStrategy
 * @Description: t图片加载的抽象接口
 * @Author: 小七
 * @CreateDate: 2018/12/26 13:52
 * @Version: 1.0
 */
public interface BaseImageLoaderStrategy<T extends ImageConfig> {
    //定义供外部调用的显示图片的方法
    void displayImage(Context contex, T config);

}