package zhcw.lottery.znzd.com.selflottery.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhy.autolayout.AutoLinearLayout;

import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.util.CommonUtils;


/**
 * 号码容器
 */
public class MyGridView extends GridView {
    private PopupWindow pop;
    private TextView ball;

    private OnActionUpListener onActionUpListener;

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // ①手指按下
        // 显示放大的号码
        // ②手指滑动
        // 更新：号码内容+显示位置
        // ③手指抬起
        // 修改手指下面的球的背景

        View view = View.inflate(context, R.layout.il_gridview_item_pop, null);
        ball = (TextView) view.findViewById(R.id.ii_pretextView);

        pop = new PopupWindow(context);
        pop.setContentView(view);
        pop.setBackgroundDrawable(null);
        // animationStyle animation style to use when the popup appears and disappears. Set to -1 for the default animation, 0 for no animation, or a resource identifier for an
        // explicit animation.
        pop.setAnimationStyle(0);
        // 设置pop的大小
        pop.setWidth(CommonUtils.dp2px(55));// dip--px
        pop.setHeight(CommonUtils.dp2px(53));
    }

    public void setOnActionUpListener(OnActionUpListener onActionUpListener) {
        this.onActionUpListener = onActionUpListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // 当手指按下的时候，获取到点击那个球
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        //坐标转换成角标
        int position = pointToPosition(x, y);
        if (position == INVALID_POSITION) {
            hiddenPop();
            return false;
        }
        AutoLinearLayout child = (AutoLinearLayout) this.getChildAt(position);
        TextView child1 = (TextView) child.getChildAt(0);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 当手指按下的时候，接管ScrollView滑动
//                this.getParent();//获取到LinearLayout
                this.getParent().getParent().requestDisallowInterceptTouchEvent(true);
                showPop(child1);
                break;
            case MotionEvent.ACTION_MOVE:
                this.getParent().getParent().requestDisallowInterceptTouchEvent(true);
                updatePop(child1);
                break;
            case MotionEvent.ACTION_UP:
                // 当手指抬起的时候，放行，ScrollView滑动
                this.getParent().getParent().requestDisallowInterceptTouchEvent(false);
                hiddenPop();
                // 增加一个监听
                if (onActionUpListener != null)
                    onActionUpListener.onActionUp(child1, position);
                break;
            default:
                hiddenPop();
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void hiddenPop() {
        if (pop.isShowing())
            pop.dismiss();
    }

    /**
     * 显示泡泡
     *
     * @param child ：在头上显示
     */
    private void showPop(TextView child) {
        int yOffset = -(pop.getHeight() + child.getHeight());
        int xOffset = -(pop.getWidth() - child.getWidth()) / 2;
        ball.setText(child.getText());
        // Display the content view in a popup window anchored to the bottom-left corner of the anchor view.
        // pop.showAsDropDown(ChildDetail);
        pop.showAsDropDown(child, xOffset, yOffset);
    }

    private void updatePop(TextView child) {
        ball.setText(child.getText());
        int yOffset = -(pop.getHeight() + child.getHeight());
        int xOffset = -(pop.getWidth() - child.getWidth()) / 2;
        // width the new width, can be -1 to ignore
        // height the new height, can be -1 to ignore
        pop.update(child, xOffset, yOffset, -1, -1);
    }

    /**
     * 监听用户手指抬起
     *
     * @author Administrator
     */
    public interface OnActionUpListener {
        /**
         * 手指抬起
         *
         * @param view     ：当前手底下的球
         * @param position ：位置
         */
        void onActionUp(View view, int position);
    }

}
