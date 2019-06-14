package zhcw.lottery.znzd.com.selflottery.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import zhcw.lottery.znzd.com.selflottery.ready_entity.bean_info.UserInfo;
import zhcw.lottery.znzd.com.selflottery.ui.MainAdminActivity;
import zhcw.lottery.znzd.com.selflottery.widgets.ViewBase;


/**
 * Created by XPZ
 *
 * @Description:Fragment基类
 */


public abstract class BaseFragment extends Fragment {

    protected static final int REQUESET_CODE = 10000;
    protected LayoutInflater inflater;
    private Unbinder unbinder;
    protected Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        if (getContentViewLayoutID() != 0) {
            return inflater.inflate(getContentViewLayoutID(), container, false);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        mContext = getActivity();
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 获取布局ID
     */
    protected abstract int getContentViewLayoutID();

    /**
     * 界面初始化
     */
    protected abstract void init();

    protected void launchActivity(Class<?> c) {
        launchActivity(c, null, false);
    }

    protected void launchActivity(Class<?> c, Bundle b, boolean isCloseSelf) {
        Intent i = new Intent(getActivity(), c);
        if (b != null) {
            i.putExtras(b);
        }
        startActivity(i);
        if (isCloseSelf)
            getActivity().finish();
    }

    protected void launchActivity(Class<?> c, Bundle b) {
        launchActivity(c, b, false);
    }

    protected void launchActivity(Class<?> c, boolean isCloseSelf) {
        launchActivity(c, null, isCloseSelf);
    }

    public void launchActivityForResult(Class<?> c) {
        launchActivityForResult(c, null);
    }

    protected void launchActivityForResult(Class<?> c, Bundle b) {
        Intent i = new Intent(getActivity(), c);
        if (b != null) {
            i.putExtras(b);
        }
        startActivityForResult(i, REQUESET_CODE);
    }

    /**
     * @param c 登录之后需要跳转的页面 , 默认是MainAdminActivity.class
     * @return 返回登录状态
     */
    /**
     * @param Activityclass 登录之后需要跳转的页面的class , 默认是MainAdminActivity.class
     * @return 返回登录状态
     */
    public boolean isLoginAndStartLogin(@Nullable Class<? extends Activity> Activityclass) {
        if (UserInfo.isIsLogin()) {
            return true;
        } else {
            if (Activityclass != null) {
                BaseActivity.activity = Activityclass;
                ViewBase.showISLogin(mContext, 1);
            } else {
                BaseActivity.activity = MainAdminActivity.class;
                ViewBase.showISLogin(mContext, 1);
            }
            return false;
        }
    }

}