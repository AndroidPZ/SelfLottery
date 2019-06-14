package zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity;

import java.io.Serializable;

/**
 * Created by XPZ on 2018/7/25.
 */
public class ItemModel implements Serializable {

    public static final int ONE = 1001;
    public static final int TWO = 1002;

    public int type;
    public Object data;

    public ItemModel(int type, Object data) {
        this.type = type;
        this.data = data;
    }
}
