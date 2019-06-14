package zhcw.lottery.znzd.com.selflottery.ui.lottery.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.util.CommonUtils;


//选号容器用Adapter（双色球的红球）
public class PoolSSQAdapter extends BaseAdapter {

    private Context context;

    private int endNum;

    private List<Integer> slectedNums;

    private int slectedBgResId;// 选中的背景图片的资源id
    private int textColorBgResId;// 选中的背景图片的资源id
    private DecimalFormat decimalFormat;


    public PoolSSQAdapter(Context context, int endNum, List<Integer> slectedNums, int slectedBgResId, int textColorBgResId) {
        super();
        this.context = context;
        this.endNum = endNum;
        this.slectedNums = slectedNums;
        this.slectedBgResId = slectedBgResId;
        this.textColorBgResId = textColorBgResId;
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
        if (decimalFormat == null) {
            decimalFormat = new DecimalFormat("00");
        }
        holder.ball.setText(decimalFormat.format(position + 1));
        holder.ball.setTextColor(CommonUtils.getColor(context, textColorBgResId));

        // 获取到用户已选号码的集合，判读集合中有，背景图片修改为红色
        if (slectedNums.contains(position + 1)) {
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
