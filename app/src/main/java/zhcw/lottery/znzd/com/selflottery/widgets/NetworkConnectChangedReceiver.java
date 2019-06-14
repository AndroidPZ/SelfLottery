package zhcw.lottery.znzd.com.selflottery.widgets;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import org.greenrobot.eventbus.EventBus;

import zhcw.lottery.znzd.com.selflottery.event.NetworkChangeEvent;
import zhcw.lottery.znzd.com.selflottery.util.Logger;
import zhcw.lottery.znzd.com.selflottery.util.Utils;

/**
 * 作者：XPZ on 2018/12/26 14:13.
 */
public class NetworkConnectChangedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // 监听网络连接，包括wifi和移动数据的打开和关闭,以及连接上可用的连接都会接到监听
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            //**判断当前的网络连接状态是否可用*/
            boolean isConnected = Utils.isNetworkConnected();
            Logger.i("onReceive: 当前网络 " + isConnected);
            EventBus.getDefault().post(new NetworkChangeEvent(isConnected));
        }
    }
}
