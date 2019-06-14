package zhcw.lottery.znzd.com.selflottery.widgets;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.ui.MainAdminActivity;
import zhcw.lottery.znzd.com.selflottery.ui.bonus.BonusFragment;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.LotteryFragment;
import zhcw.lottery.znzd.com.selflottery.ui.my.MyFragment;

/**
 * Created by xpz
 *
 * @Date: 2018/8/8.
 * @version: 1.0
 * @Description: 主界面模块
 */

public class BottomTabHost {

    private MainAdminActivity aParent = null;

    private LayoutInflater mLayoutInflater = null;
    private FragmentTabHost mButtomTabHost = null;

    private int aTabHostImageIds[] = {
            R.drawable.yz_sel_tab_product,
            R.drawable.yz_sel_tab_exchange_bonus,
            R.drawable.yz_sel_tab_my,
    };

    private int aTabHostTextIds[] = {
            R.string.tab_manager1,
            R.string.tab_manager3,
            R.string.tab_manager2,
    };

    public static Class aTabHostFragments[] = {
            LotteryFragment.class,
            BonusFragment.class,
            MyFragment.class
    };


    /**
     * 框架抽取
     * @param parent
     */
    public BottomTabHost(MainAdminActivity parent) {

        aParent = parent;
        mLayoutInflater = LayoutInflater.from(aParent);
        mButtomTabHost = (FragmentTabHost) aParent.findViewById(R.id.bm_tabHost);
        mButtomTabHost.setup(aParent, aParent.getSupportFragmentManager(), R.id.center_content);
        mButtomTabHost.getTabWidget().setDividerDrawable(null);

        int count = aTabHostImageIds.length;

        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec = mButtomTabHost.newTabSpec(
                    aParent.getString(aTabHostTextIds[i])).setIndicator(aTabItemView(i));
            mButtomTabHost.addTab(tabSpec, aTabHostFragments[i], null);
        }
    }


    /**
     * @param index
     * @return
     */
    private View aTabItemView(int index) {
        View bottomTabItemView = mLayoutInflater.inflate(R.layout.bottom_tab_item, null);
        ImageView tabImageView = (ImageView) bottomTabItemView.findViewById(R.id.tab_image);
        tabImageView.setImageResource(aTabHostImageIds[index]);
        TextView textView = (TextView) bottomTabItemView.findViewById(R.id.tab_text);
        textView.setText(aTabHostTextIds[index]);

        return bottomTabItemView;
    }

    public void setCurrentTab(int index) {
        mButtomTabHost.setCurrentTab(index);
    }

}
