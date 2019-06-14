package zhcw.lottery.znzd.com.selflottery.ui.lottery.adapter.lottery_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;

import zhcw.lottery.znzd.com.selflottery.R;


/**
 * 作者：XPZ on 2018/4/12 12:05.
 */
public class MsgAdapter extends DelegateAdapter.Adapter<MsgAdapter.MsgViewHolder> {

    // 使用DelegateAdapter首先就是要自定义一个它的内部类Adapter，让LayoutHelper和需要绑定的数据传进去
// 此处的Adapter和普通RecyclerView定义的Adapter只相差了一个onCreateLayoutHelper()方法，其他的都是一样的做法.

    private String[] listItem;
    // 用于存放数据列表
    private Context context;
    private LayoutHelper layoutHelper;
    private int count = 0;

// 用于设置Item点击事件

    //构造函数(传入每个的数据列表 & 展示的Item数量)
  /*  public MsgAdapter(Context context, LayoutHelper layoutHelper, int count, String[] listItem) {
        this(context, layoutHelper, count, new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300), listItem);
    }*/

    public MsgAdapter(Context context, LayoutHelper layoutHelper, int count, String[] listItem) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.count = count;
        this.listItem = listItem;
    }

    // 把ViewHolder绑定Item的布局
    @Override
    public MsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MsgViewHolder(LayoutInflater.from(context).inflate(R.layout.lottery_msg_item, parent, false));
    }

    // 绑定Item的数据
    @Override
    public void onBindViewHolder(MsgViewHolder holder, int position) {
        /*数据*/
        int a = 1, b = 1;
        if (listItem.length == 1) {//一条数据不轮播
            holder.mViewFlipper.addView(getView(listItem[0]));
        } else {
            for (String msg : listItem) {
                holder.mViewFlipper.addView(getView(msg));
            }
        }
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

    private View getView(String str) {
        View view = LayoutInflater.from(context).inflate(R.layout.msgadapter_tv_item, null);
        TextView view1 = view.findViewById(R.id.temxta_item);
        view1.setText(str == null ? "" : str);
        return view;
    }

    // 此处的Adapter和普通RecyclerView定义的Adapter只相差了一个onCreateLayoutHelper()方法
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }


    //定义Viewholder
   public static class MsgViewHolder extends RecyclerView.ViewHolder {

        public static ViewFlipper mViewFlipper;

        public MsgViewHolder(View root) {
            super(root);
            mViewFlipper = root.findViewById(R.id.filpper_item1);
        }
    }
}
