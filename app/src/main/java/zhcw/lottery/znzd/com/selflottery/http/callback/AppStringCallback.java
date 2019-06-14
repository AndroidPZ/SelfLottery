package zhcw.lottery.znzd.com.selflottery.http.callback;

import android.content.Context;

import com.lzy.okgo.request.base.Request;

import zhcw.lottery.znzd.com.selflottery.widgets.ViewBase;

/**
 * Created by xpz on 2018/10/11.
 */

public abstract class AppStringCallback extends StringCallback {


    private Context context;

    @Override
    public void isLogin() {
        ViewBase.showISLogin(context, 2);
    }

    protected AppStringCallback(Context context) {
        this.context = context;
    }

    @Override
    public void onStart(Request<String, ? extends Request> request) {

    }

    @Override
    public void onFinish() {
    }
}
