package zhcw.lottery.znzd.com.selflottery.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.adapter.lottery_adapter.SelectGridViewAdapter;


/**
 * 作者：XPZ on 2018/3/13 15:29.
 */
public class SelectPicPopupWindow extends PopupWindow {


    private final View mMenuView;
    private final Activity mContext;
    private final SelectGridViewAdapter mAdapter;
    private final GridView mGridView;
    boolean canDismiss = true;
    int selectorPosition = 0;

    public SelectPicPopupWindow(final Activity context, final OnPopUpListener onPopUpListener) {
        super(context);
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.il_k3_item_pop, null);
        mGridView = mMenuView.findViewById(R.id.gv_type);

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));
        this.setAnimationStyle(R.style.AnimStyle);
        this.setTouchable(true);
        this.setOutsideTouchable(true);
        // 设置pop的大小
//        this.setWidth(CommonUtils.getScreenWidth(context));// dip--px
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框

        final String[] stringArray = mContext.getResources().getStringArray(R.array.ssqmsgList);
        mAdapter = new SelectGridViewAdapter(mContext, stringArray);
        mGridView.setAdapter(mAdapter);
        //gridView的点击事件
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //把点击的position传递到adapter里面去
                mAdapter.changeState(position);
                selectorPosition = position;
                onPopUpListener.onActionUp(view, stringArray[position]);
            }
        });

    }

    @Override
    public void dismiss() {
        if (canDismiss)
            dismiss2();
        else {
            StackTraceElement[] stackTrace = new Exception().getStackTrace();
            if (stackTrace.length >= 2 && "dispatchKeyEvent".equals(stackTrace[1].getMethodName())) {
                dismiss2();
            }
        }
    }

    public void dismiss2() {
        super.dismiss();
    }

    /**
     * 监听用户手指抬起
     *
     * @author Administrator
     */
    public interface OnPopUpListener {
        /**
         * @param view   ：当前手底下的view
         * @param string ：对应的文本
         */
        void onActionUp(View view, String string);
    }

}
