package zhcw.lottery.znzd.com.selflottery.ui.lottery.adapter.lottery_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;
import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.base.BaseActivity;
import zhcw.lottery.znzd.com.selflottery.ready_entity.bean_info.UserInfo;
import zhcw.lottery.znzd.com.selflottery.ui.MainAdminActivity;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.activity.Play7LCActivity;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.activity.PlayK3Activity;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.activity.PlaySDActivity;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.activity.PlaySSCActivity;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.activity.PlaySSQActivity;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.entity.JiangQiInfo;
import zhcw.lottery.znzd.com.selflottery.util.CommonUtils;
import zhcw.lottery.znzd.com.selflottery.util.Logger;
import zhcw.lottery.znzd.com.selflottery.util.SpannableStringUtils;
import zhcw.lottery.znzd.com.selflottery.util.ToastUtil;
import zhcw.lottery.znzd.com.selflottery.util.Utils;
import zhcw.lottery.znzd.com.selflottery.widgets.ViewBase;

import static android.os.SystemClock.uptimeMillis;

/**
 * 作者：XPZ on 2018/4/12 12:05.
 */
public class ListAdapter extends DelegateAdapter.Adapter<ListAdapter.ListViewHolder> {

    private long uptimeMillis;
    private final long MTime = 999;
    private JiangQiInfo issues;
    // 用于存放数据列表
    private Context context;
    private FragmentActivity mActivity;
    private LayoutHelper layoutHelper;

    public ListAdapter(Context context, FragmentActivity activity, LayoutHelper layoutHelper) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.mActivity = activity;
    }

    public void setData(JiangQiInfo issues) {
        this.issues = issues;
        uptimeMillis = uptimeMillis();
        notifyDataSetChanged();
    }

    // 把ViewHolder绑定Item的布局
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(context).
                inflate(R.layout.lottery_list_item, parent, false));
    }

    // 绑定Item的数据
    @Override
    public void onBindViewHolder(ListViewHolder holder, final int position) {
        if (issues != null) {
            JiangQiInfo.IssuesBean issuesBean = this.issues.getIssues().get(position);

            holder.logo.setImageResource(GAME_ICON.get(issuesBean.getGameName()));
            holder.summary.setText(GAME_NAME.get(issuesBean.getGameName()));

            String string = context.getResources().getString(GAME_NAME.get(issuesBean.getGameName()));
            if ("双色球".equals(string) || "3D".equals(string) || "七乐彩".equals(string)) {
                holder.countdownView.setVisibility(View.GONE);
                holder.countdownView_hour.setVisibility(View.VISIBLE);
            } else {
                holder.countdownView.setVisibility(View.VISIBLE);
                holder.countdownView_hour.setVisibility(View.GONE);
            }
            holder.setIsRecyclable(false);

            holder.msg.setText(SpannableStringUtils.getBuilder("第:")
                    .append(issuesBean.getIssue())
                    .setForegroundColor(CommonUtils.getColor(mActivity, R.color.red_700))
                    .append("期").create());
            holder.countdownView.setTag(issuesBean.getGameName());
            holder.bet.setTag(issuesBean.getGameName());

            long nowTime = issues.getNowTime() + (uptimeMillis() - uptimeMillis);

            if (nowTime < issuesBean.getStopTime() &&
                    issues.getNowTime() >= issuesBean.getStartTime()) {
                holder.starTime.setVisibility(View.GONE);
                holder.countdownView.start(issuesBean.getStopTime() - nowTime);
                holder.countdownView_hour.start(issuesBean.getStopTime() - nowTime);
            } else {
                if (nowTime < issuesBean.getStartTime()) {
                    holder.starTime.setVisibility(View.VISIBLE);
                    holder.starTime.setText(String.format("%s开始",
                            Utils.longFormatStr(issuesBean.getStartTime())));
                    holder.countdownView.setVisibility(View.GONE);
                    holder.countdownView_hour.setVisibility(View.GONE);
                }
                holder.countdownView.updateShow(0);
                holder.countdownView_hour.updateShow(0);
            }
        }

        holder.bet.setOnClickListener(v -> {
            if (isLoginAndStartLogin(MainAdminActivity.class)) {
                Logger.i(holder.summary.getText().toString());
                switch (holder.summary.getText().toString()) {
                    case "双色球":
                        if (holder.countdownView_hour.getRemainTime() > MTime) {
                            mActivity.startActivity(new Intent(mActivity, PlaySSQActivity.class));
                        } else {
                            ToastUtil.showLongToast(R.string.smg_show_toast);
                        }
                        break;
                    case "3D":
                        if (holder.countdownView_hour.getRemainTime() > MTime) {
                            mActivity.startActivity(new Intent(mActivity, PlaySDActivity.class));
                        } else {
                            ToastUtil.showLongToast(R.string.smg_show_toast);
                        }
                        break;
                    case "七乐彩":
                        if (holder.countdownView_hour.getRemainTime() > MTime) {
                            mActivity.startActivity(new Intent(mActivity, Play7LCActivity.class));
                        } else {
                            ToastUtil.showLongToast(R.string.smg_show_toast);
                        }
                        break;
                    case "时时彩":
                        if (holder.countdownView_hour.getRemainTime() > MTime) {
                            mActivity.startActivity(new Intent(mActivity, PlaySSCActivity.class));
                        } else {
                            ToastUtil.showLongToast(R.string.smg_show_toast);
                        }
                        break;
                    case "快3":
                        if (holder.countdownView_hour.getRemainTime() > MTime) {
                            mActivity.startActivity(new Intent(mActivity, PlayK3Activity.class));
                        } else {
                            ToastUtil.showLongToast(R.string.smg_show_toast);
                        }
                        break;
                }
            }
        });
    }

    // 返回Item数目
    @Override
    public int getItemCount() {
        return issues != null ? issues.getIssues().size() : 0;
    }

    // 此处的Adapter和普通RecyclerView定义的Adapter只相差了一个onCreateLayoutHelper()方法
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    /**
     * @param c 登录之后需要跳转的页面 , 默认是MainAdminActivity.class
     * @return 返回登录状态
     */
    private boolean isLoginAndStartLogin(Class<?> c) {
        if (UserInfo.isIsLogin()) {
            return true;
        } else {
            BaseActivity.activity = MainAdminActivity.class;
            ViewBase.showISLogin(mActivity, 1);
            return false;
        }
    }

    //定义标题Viewholder
    class ListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ii_hall_lottery_logo)
        ImageView logo;
        @BindView(R.id.ii_hall_lottery_summary)
        TextView summary;
        @BindView(R.id.countdown_view)
        CountdownView countdownView;
        @BindView(R.id.countdown_view_hour)
        CountdownView countdownView_hour;
        @BindView(R.id.star_time)
        TextView starTime;
        @BindView(R.id.ii_hall_lottery_summary_msg)
        TextView msg;
        @BindView(R.id.ii_hall_lottery_bet)
        ImageView bet;

        ListViewHolder(View root) {
            super(root);
            ButterKnife.bind(this, root);
        }
    }


    /**
     * 彩种图标
     */
    private final static Map<String, Integer> GAME_ICON = new HashMap<String, Integer>() {
        {
            put("ssq", R.mipmap.id_ssq);
            put("3d", R.mipmap.id_3d);
            put("k3", R.mipmap.id_k3);
            put("nmssc", R.mipmap.id_ssc);
            put("307", R.mipmap.id_qlc);
        }
    };

    /**
     * 彩种名称
     */
    private final static Map<String, Integer> GAME_NAME = new HashMap<String, Integer>() {
        {
            put("ssq", R.string.is_hall_ssq_title);
            put("3d", R.string.is_hall_3d_title);
            put("k3", R.string.is_hall_k3_title);
            put("nmssc", R.string.is_hall_ssc_title);
            put("307", R.string.is_hall_qlc_title);
        }
    };
}
