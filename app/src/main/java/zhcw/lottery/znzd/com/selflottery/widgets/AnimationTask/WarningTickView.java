package zhcw.lottery.znzd.com.selflottery.widgets.AnimationTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import zhcw.lottery.znzd.com.selflottery.R;

/**
 * 作者：XPZ on 2018/5/24 16:48.
 */
public class WarningTickView extends View {

    private final int sweep1 = 360;
    private int sweep;
    private int endPoint;
    private int endPointP;

    private float mDensity = -1;
    private final float CONST_LEFT_RIGHT_Y1 = dip2px(6);
    private final float CONST_LEFT_RIGHT_Y1_BOTTOM = dip2px(13);
    private final float CONST_CIRCLE_BOTTOM = dip2px(10);

    private final float CONST_LEFT_RIGHT = dip2px(25);//圆的大小
    private final float CONST_TOP_BOTTOM = dip2px(25);//圆的大小

    private final float CONST_RADIUS = dip2px(1.8f); //半角半径
    private final float CONST_RECT_WEIGHT = dip2px(3);//线的宽度
    private final float CONST_LEFT_RECT_W = dip2px(-3); //竖线的 X轴位置
    private final float CONST_RIGHT_RECT_H = dip2px(15);//竖线的 Y轴位置
    private final float MAX_LEFT_RECT_W = dip2px(28);//最大长度
    private final float MIN_LEFT_RECT_W = dip2px(1.2f);
    private final float SM_RATIDS = dip2px(2); //红点半径

    private float mMaxLeftRectWidth;
    private float mLeftRectWidth;
    private boolean mLeftRectGrowMode;
    private Paint mPaint;
    private int totalW;
    private int totalH;
    private int endPointT;

    public WarningTickView(Context context) {
        super(context);
        init();

    }

    public WarningTickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*
         * 方法 说明 drawRect 绘制矩形 drawCircle 绘制圆形 drawOval 绘制椭圆 drawPath 绘制任意多边形
         * drawLine 绘制直线 drawPoin 绘制点
         */


        mPaint.setColor(getResources().getColor(R.color.warning_stroke));// 设置颜色
        mPaint.setStrokeWidth(10);//设置画笔的宽度
        mPaint.setStyle(Paint.Style.STROKE);//设置空心

        // 画弧，第一个参数是RectF：该类是第二个参数是角度的开始，第三个参数是多少度，第四个参数是真的时候画扇形，是假的时候画弧线
        @SuppressLint("DrawAllocation")
        RectF oval2 = new RectF((totalW >> 1) - CONST_LEFT_RIGHT,
                (totalH >> 1) - CONST_TOP_BOTTOM, (totalW >> 1) + CONST_LEFT_RIGHT,
                (totalH >> 1) + CONST_TOP_BOTTOM);// 设置个新的长方形，扫描测量
        canvas.drawArc(oval2, 270, sweep, false, mPaint);

        // 画红点
        if (endPoint >= endPointP) {
            mPaint.setStyle(Paint.Style.FILL);//设置空心
            mPaint.setColor(getResources().getColor(R.color.warning_stroke));// 设置颜色
            mPaint.setStrokeWidth(2);//设置画笔的宽度
            canvas.drawCircle((totalW >> 1), (totalH >> 1) + CONST_TOP_BOTTOM - CONST_CIRCLE_BOTTOM,
                    SM_RATIDS, mPaint);// 小圆
        }

        mMaxLeftRectWidth = (totalW + MAX_LEFT_RECT_W) / 2 + CONST_RECT_WEIGHT - 1;

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.warning_stroke));// 设置颜色
        RectF leftRect1 = new RectF();
        //画竖线
        if (mLeftRectGrowMode) {
            leftRect1.top = 50;
            leftRect1.bottom = leftRect1.top + mLeftRectWidth;
            leftRect1.left = (totalW + CONST_LEFT_RECT_W) / 2;
            leftRect1.right = leftRect1.left + CONST_RECT_WEIGHT;
        } else {
            leftRect1.top = (totalH + CONST_RIGHT_RECT_H) / 2 + CONST_RECT_WEIGHT - 1;
            leftRect1.bottom = leftRect1.top - mLeftRectWidth;
            leftRect1.left = (totalW + CONST_LEFT_RECT_W) / 2;
            leftRect1.right = leftRect1.left + CONST_RECT_WEIGHT;
        }
        canvas.drawRoundRect(leftRect1, CONST_RADIUS, CONST_RADIUS, mPaint);

        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        totalW = getMeasuredWidth();
        totalH = getMeasuredHeight();
        endPointT = (int) ((totalH >> 1) - CONST_TOP_BOTTOM + CONST_LEFT_RIGHT_Y1);
        endPointP = (int) ((totalH >> 1) + CONST_TOP_BOTTOM - CONST_CIRCLE_BOTTOM - CONST_LEFT_RIGHT_Y1_BOTTOM);
    }

    public float dip2px(float dpValue) {
        if (mDensity == -1) {
            mDensity = getResources().getDisplayMetrics().density;
        }
        return dpValue * mDensity + 0.5f;
    }

    public void startTickAnim() {
        // hide tick
        sweep = 0;
        endPoint = 0;
        invalidate();
        Animation tickAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                sweep = (int) (sweep1 * interpolatedTime);
                endPoint = (int) (endPointT + (endPointP - endPointT) * interpolatedTime);
                //之后算时间 顺序画
                if (0.54 < interpolatedTime && 0.7 >= interpolatedTime) {  // 左增长对右侧做出反应
                    mLeftRectGrowMode = true;
                    mLeftRectWidth = mMaxLeftRectWidth * ((interpolatedTime - 0.54f) / 0.16f);
                    invalidate();
                } else if (0.7 < interpolatedTime && 0.84 >= interpolatedTime) { //
                    mLeftRectGrowMode = false;
                    mLeftRectWidth = mMaxLeftRectWidth * (1 - ((interpolatedTime - 0.7f) / 0.14f));
                    mLeftRectWidth = mLeftRectWidth < MIN_LEFT_RECT_W ? MIN_LEFT_RECT_W : mLeftRectWidth;
                    invalidate();
                } else if (0.84 < interpolatedTime && 1 >= interpolatedTime) { // 恢复左矩形宽度，将下矩形缩短为const
                    mLeftRectGrowMode = false;
                    mLeftRectWidth = MIN_LEFT_RECT_W + (MAX_LEFT_RECT_W - MIN_LEFT_RECT_W) * ((interpolatedTime - 0.84f) / 0.16f);
                    invalidate();
                }
            }
        };
        tickAnim.setDuration(750);
        tickAnim.setStartOffset(100);
        startAnimation(tickAnim);
    }
}
