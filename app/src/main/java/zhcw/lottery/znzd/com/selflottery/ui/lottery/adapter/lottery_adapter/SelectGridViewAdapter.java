package zhcw.lottery.znzd.com.selflottery.ui.lottery.adapter.lottery_adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import zhcw.lottery.znzd.com.selflottery.R;


/**
 * 作者：XPZ on 2018/11/1 10:57.
 */
public class SelectGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mList;
    private int selectorPosition;

    public SelectGridViewAdapter(Context context, String[] mList) {
        this.mContext = context;
        this.mList = mList;

    }

    @Override
    public int getCount() {
        return mList != null ? mList.length : 0;
    }

    @Override
    public Object getItem(int position) {
        return mList != null ? mList[position] : null;
    }

    @Override
    public long getItemId(int position) {
        return mList != null ? position : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(mContext, R.layout.item_gridview, null);
        RelativeLayout mRelativeLayout = convertView.findViewById(R.id.rl);
        TextView textView = convertView.findViewById(R.id.tv_number);
        textView.setText(mList[position]);
        //如果当前的position等于传过来点击的position,就去改变他的状态
        if (selectorPosition == position) {
            textView.setBackgroundResource(R.mipmap.id_pop_bg_new);
            textView.setTextColor(Color.parseColor("#C62828"));
        } else {
            //其他的恢复原来的状态
            textView.setBackgroundResource(R.mipmap.id_pop_bg_h_new);
            textView.setTextColor(Color.parseColor("#757575"));
        }
        return convertView;
    }


    public void changeState(int pos) {
        selectorPosition = pos;
        notifyDataSetChanged();
    }
}
