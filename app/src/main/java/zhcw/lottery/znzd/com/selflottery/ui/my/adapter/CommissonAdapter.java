package zhcw.lottery.znzd.com.selflottery.ui.my.adapter;

import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.Commisson_Info;
import zhcw.lottery.znzd.com.selflottery.widgets.AnimationTask.CountNumberView;

/**
 * 作者：XPZ on 2018/10/15 14:45.
 */
public class CommissonAdapter extends BaseQuickAdapter<Commisson_Info.SaleInfoBean.LtBean, BaseViewHolder> {

    private final ArrayMap<String, Integer> imgArrayMap;

    public CommissonAdapter(@Nullable List<Commisson_Info.SaleInfoBean.LtBean> data) {
        super(R.layout.layout_commission_adapetr, data);
        imgArrayMap = new ArrayMap<>();
        imgArrayMap.put("k3", R.mipmap.id_k3);
        imgArrayMap.put("ssq", R.mipmap.id_ssq);
        imgArrayMap.put("3d", R.mipmap.id_3d);
        imgArrayMap.put("307", R.mipmap.id_qlc);
        imgArrayMap.put("nmssc", R.mipmap.id_ssc);
    }

    @Override
    protected void convert(BaseViewHolder helper, Commisson_Info.SaleInfoBean.LtBean item) {
        CountNumberView countNumberView = helper.getView(R.id.count);
        CountNumberView sumNumberView = helper.getView(R.id.sum);
        countNumberView.showNumberWithAnimation(Float.valueOf(item.getCount() + ""), CountNumberView.INTREGEX); //保留两位小数
        sumNumberView.showNumberWithAnimation(Float.valueOf(item.getSum()), CountNumberView.FLOATREGEX); //保留两位小数
        helper.setImageResource(R.id.icon_type, imgArrayMap.get(item.getType()));
    }
}
