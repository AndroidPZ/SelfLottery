package zhcw.lottery.znzd.com.selflottery.ui.lottery.adapter.lottery_adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.lqm.roundview.RoundImageView;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.view.BannerViewPager;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.widgets.GlideImageLoader;

/**
 * 作者：XPZ on 2018/4/12 12:05.
 */
public class TitleAdapter extends DelegateAdapter.Adapter<TitleAdapter.TitleViewHolder> {

    private static final GlideImageLoader glideImageLoader = new GlideImageLoader();
    private ArrayList<String> listItem;
    // 使用DelegateAdapter首先就是要自定义一个它的内部类Adapter，让LayoutHelper和需要绑定的数据传进去
// 此处的Adapter和普通RecyclerView定义的Adapter只相差了一个onCreateLayoutHelper()方法，其他的都是一样的做法.
    // 用于设置Item点击事件
    // 用于存放数据列表
    private Context context;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;
    private OnBannerItemClickListener onItemClickListener;
    private int count = 0;
    private TitleViewHolder mHolder;

    //构造函数(传入每个的数据列表 & 展示的Item数量)
    public TitleAdapter(Context context, LayoutHelper layoutHelper, int count, ArrayList<String> listItem) {
        this(context, layoutHelper, count, new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300), listItem);
    }

    public TitleAdapter(Context context, LayoutHelper layoutHelper, int count, RecyclerView.LayoutParams layoutParams, ArrayList<String> listItem) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.count = count;
        this.layoutParams = layoutParams;
        this.listItem = listItem;
    }

    // 把ViewHolder绑定Item的布局
    @Override
    public TitleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TitleViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.lottery_title_item, parent, false));
    }

    // 绑定Item的数据
    @Override
    public void onBindViewHolder(TitleViewHolder holder, int position) {
        this.mHolder = holder;
        holder.banner.setImages(listItem);
        holder.banner.start();
        //设置banner动画效果
        holder.banner.setBannerAnimation(Transformer.ZoomOutSlide);
        //设置图片加载器
        holder.banner.setImageLoader(glideImageLoader);
        holder.banner.setOnBannerListener(bannerPosition ->
                onItemClickListener.onItemClickListener(holder.banner, bannerPosition));
        holder.banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private Palette.Swatch lightVibrantSwatch;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                RelativeLayout view = (RelativeLayout) holder.banner.getChildAt(0);
                BannerViewPager ViewPager = (BannerViewPager) view.getChildAt(0);
                RoundImageView mImageView = (RoundImageView) ViewPager.getChildAt(1);
                if (mImageView != null) {
                    mImageView.setDrawingCacheEnabled(true);
                    Bitmap drawingCache = mImageView.getDrawingCache();
                    if (drawingCache != null) {
                        Bitmap bitmap = Bitmap.createBitmap(drawingCache);
                        Palette.Builder builder = new Palette.Builder(bitmap);
                        builder.maximumColorCount(16);
                        Palette generate = builder.generate();
                        lightVibrantSwatch = generate.getVibrantSwatch();
                    }
                    mImageView.setDrawingCacheEnabled(false);
                    int vibrantColor = getVibrantColor(lightVibrantSwatch);
                    holder.bgLayout.setBackgroundColor(vibrantColor);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    // 返回Item数目
    @Override
    public int getItemCount() {
        return count;
    }

    //    绑定MainActivity传进来的点击监听器
    public void setOnItemClickListener(OnBannerItemClickListener listener) {
        onItemClickListener = listener;
    }

    public void setData(ArrayList<String> imgList) {
        this.listItem = imgList;
        notifyDataSetChanged();
    }

    // 此处的Adapter和普通RecyclerView定义的Adapter只相差了一个onCreateLayoutHelper()方法
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    public Banner getBanner() {
        return mHolder.banner;
    }

    private int getVibrantColor(Palette.Swatch mVibrantSwatch) {
        return mVibrantSwatch != null ? mVibrantSwatch.getRgb() : Color.WHITE;
    }

    public interface OnBannerItemClickListener {
        void onItemClickListener(View view, int position);
    }

    //定义Viewholder
    class TitleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.banner)
        Banner banner;
        @BindView(R.id.bg)
        AutoRelativeLayout bgLayout;

        TitleViewHolder(View root) {
            super(root);
            ButterKnife.bind(this, root);
        }
    }
}
