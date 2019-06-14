package zhcw.lottery.znzd.com.selflottery.event;

/**
 * 作者：XPZ on 2018/12/26 14:15.
 */
public class NetworkChangeEvent {
    public boolean isConnected; //是否存在网络

    public NetworkChangeEvent(boolean isConnected) {
        this.isConnected = isConnected;
    }
}
