package zhcw.lottery.znzd.com.selflottery.ui.lottery.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.util.CommonUtils;


//选号容器用Adapter（双色球的红球）
public class Pool3DAdapter extends BaseAdapter {

    private final int type;
    private final String[] stringArray;
    private Context context;
    private int endNum;
    private int addNum = 0;
    private List<Integer> slectedNums;
    private int slectedBgResId;// 选中的背景图片的资源id
    private int textColorBgResId;// 选中的背景图片的资源id

    public Pool3DAdapter(Context context, int endNum, List<Integer> slectedNums, int slectedBgResId,
                         int textColorBgResId, int type) {
        super();
        this.context = context;
        this.endNum = endNum;
        this.slectedNums = slectedNums;
        this.slectedBgResId = slectedBgResId;
        this.textColorBgResId = textColorBgResId;
        this.type = type;
        stringArray = context.getResources().getStringArray(R.array.sscmsgNumberList);
    }

    public void setEndNum(int num, int add) {
        endNum = num;
        addNum = add;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return endNum;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.ball_list_bg, null);
            holder.ball = convertView.findViewById(R.id.ball_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (type == 1) {
            holder.ball.setText(String.format("%s", position + addNum));
        } else if (type == 0) {
            holder.ball.setText(stringArray[position]);
        }
        holder.ball.setTextColor(CommonUtils.getColor(context, textColorBgResId));
        // 获取到用户已选号码的集合，判读集合中有，背景图片修改为红色
        if (slectedNums.contains(position)) {
            holder.ball.setBackgroundResource(slectedBgResId);
            holder.ball.setTextColor(CommonUtils.getColor(context, R.color.white));
        } else {
            holder.ball.setBackgroundResource(R.mipmap.ball_gray);
        }
        return convertView;
    }

    class ViewHolder {
        TextView ball;
    }
}
