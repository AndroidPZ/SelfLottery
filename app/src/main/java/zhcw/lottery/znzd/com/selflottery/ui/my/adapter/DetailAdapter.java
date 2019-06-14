package zhcw.lottery.znzd.com.selflottery.ui.my.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.ChildDetail;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.PatrolGroup;
import zhcw.lottery.znzd.com.selflottery.util.CommonUtils;
import zhcw.lottery.znzd.com.selflottery.util.SpannableStringUtils;

/**
 * 作者：XPZ on 2018/9/3 10:53.
 */
public class DetailAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    //区分是否是编辑状态
    private boolean isSelect = false;
    private InquireNumListener mInquireNumListener;

    public DetailAdapter(List<MultiItemEntity> data,InquireNumListener inquireNumListener) {
        super(data);
        this.mInquireNumListener = inquireNumListener;
        addItemType(TYPE_LEVEL_0, R.layout.item_expand_order_title);
        addItemType(TYPE_LEVEL_1, R.layout.item_expand_order_sub);
    }

    private static String getStateDes(Context context, int state) {
        String des = context.getString(R.string.waiting_reception);
        switch (state) {
            case 0:
                des = context.getString(R.string.waiting_reception);
                break;
            case 1:
                des = context.getString(R.string.distributed);
                break;
            case 2:
                des = context.getString(R.string.progressing);
                break;
        }
        return des;
    }

    private static String getallPrint(Context context, int state) {
        String des = context.getString(R.string.allPrint_0);
        switch (state) {
            case 0:
                des = context.getString(R.string.allPrint_0);
                break;
            case 1:
                des = context.getString(R.string.allPrint_1);
                break;
        }
        return des;
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0: //头部
                final PatrolGroup lv0 = (PatrolGroup) item;
                helper.setText(R.id.lottery_type, lv0.getLotteryType())
                        .setText(R.id.title_order_num, lv0.getOrderNum())
                        .setText(R.id.title_order_time, SpannableStringUtils.getBuilder("第:")
                                .append(lv0.getIssue())
                                .setProportion(1.2f)
                                .setForegroundColor(CommonUtils.getColor(mContext, R.color.red_700))
                                .append("期").create())
                        .setText(R.id.title_money, lv0.getOrderAmount() + "元")
                        .setText(R.id.title_beishu, lv0.getMultiple() + "倍")
                        .setText(R.id.title_zhushu, lv0.getOrderStake() + "注")
                        .addOnClickListener(R.id.detail_qrcode)//添加点击事件(QRcode)
                        .setText(R.id.tv_all_print, SpannableStringUtils.getBuilder()
                                .append(getallPrint(mContext, lv0.getAllPrint()))
                                .setForegroundColor(CommonUtils.getColor(mContext, lv0.getAllPrint() == 0 ?
                                        R.color.green_600 : R.color.red_500))
                                .create());

                ImageView imgView = helper.getView(R.id.image_expand_flag);
                ImageView imgSelect = helper.getView(R.id.image_select_flag);

                if (isSelect) {
                    imgSelect.setVisibility(View.VISIBLE);
                    imgView.setVisibility(View.GONE);
                    imgSelect.setImageResource(lv0.isSelect() ?
                            R.mipmap.multiple_selection : R.mipmap.select_default);
                } else {
                    imgSelect.setVisibility(View.GONE);
                    imgView.setVisibility(View.VISIBLE);
                    imgView.setImageResource(lv0.isExpanded() ?
                            R.drawable.ic_up_indicate : R.drawable.ic_down_indicate);
                }

                helper.itemView.setOnClickListener(v -> {
                    int pos = helper.getAdapterPosition();
                    if (!isSelect) {
                        if (lv0.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    } else {
                        lv0.setSelect(!lv0.isSelect());
                        notifyItemChanged(pos);
                        mInquireNumListener.InquireSelectNum();
                    }
                });

                View mViewSpace = helper.getView(R.id.view_space);
                if (lv0.isExpanded()) {
                    mViewSpace.setVisibility(View.GONE);
                } else {
                    mViewSpace.setVisibility(View.VISIBLE);
                }
                break;
            case TYPE_LEVEL_1:
                ChildDetail lv1 = (ChildDetail) item;

                helper.setText(R.id.text_lottery_red_zhi, lv1.getNumRed());
                helper.setText(R.id.text_lottery_blue_zhi, lv1.getNumBlue());
                helper.setText(R.id.text_lottery_amount, lv1.getLotteryAmount() + "元")
                        .setText(R.id.text_lottery_stake, lv1.getLotteryStake() + "注")
                        .setText(R.id.text_play_type, lv1.getPlayType())
                        .setText(R.id.text_print_status, getStateDes(mContext, lv1.getPrintStatus()));
                break;
        }
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public interface InquireNumListener {
        void InquireSelectNum();
    }

}
