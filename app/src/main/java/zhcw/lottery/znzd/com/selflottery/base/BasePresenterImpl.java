package zhcw.lottery.znzd.com.selflottery.base;


import java.util.Calendar;

/**
 * Created by xpz on 2018/10/02.
 */

public class BasePresenterImpl<T> implements BasePresenter {
    public BaseView View;
    private Calendar compositeDisposable;

    public T mView;

    protected void attachView(T mView) {
        this.mView = mView;
    }

    @Override
    public void attachView(BaseView mView) {
        this.View = mView;
    }

    @Override
    public void detachView() {
        this.mView = null;
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }
}
