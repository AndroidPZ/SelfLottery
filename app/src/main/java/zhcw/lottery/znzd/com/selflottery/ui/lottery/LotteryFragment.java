package zhcw.lottery.znzd.com.selflottery.ui.lottery;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.base.BaseFragment;
import zhcw.lottery.znzd.com.selflottery.config.Config;
import zhcw.lottery.znzd.com.selflottery.contact.LottreyContact;
import zhcw.lottery.znzd.com.selflottery.presenter.LotteryPresenter;
import zhcw.lottery.znzd.com.selflottery.ready_entity.bean_info.UserInfo;
import zhcw.lottery.znzd.com.selflottery.ui.WebActivity;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.adapter.lottery_adapter.ListAdapter;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.adapter.lottery_adapter.MsgAdapter;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.adapter.lottery_adapter.TitleAdapter;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.entity.BannerInfo;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.entity.JiangQiInfo;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.entity.UpDataInfo;
import zhcw.lottery.znzd.com.selflottery.util.Logger;
import zhcw.lottery.znzd.com.selflottery.util.SystemUtil;
import zhcw.lottery.znzd.com.selflottery.util.Utils;
import zhcw.lottery.znzd.com.selflottery.widgets.ViewBase;

/**
 * Created by xpz on 2018/9/7.
 * 首页
 */

public class LotteryFragment extends BaseFragment implements LottreyContact.ILotteryFgmView,
        TitleAdapter.OnBannerItemClickListener, EasyPermissions.PermissionCallbacks {

    @BindView(R.id.lottery_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    private ListAdapter listAdapter;
    private LotteryPresenter lotteryPresenter;
    private TitleAdapter bannerAdapter;
    private ArrayList<String> imgList = new ArrayList<>();
    private List<BannerInfo.BannerBean> banerLists = new ArrayList<>();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_lottery;
    }

    @Override
    protected void init() {

        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_dark, android.R.color.holo_orange_light
                , android.R.color.holo_red_light, android.R.color.holo_green_dark);
        if (!LotteryFragment.this.isHidden()) {
            lotteryPresenter = new LotteryPresenter(mContext, this);
            lotteryPresenter.getBannerDataRequest(73);
        }
        viewLayout();
        methodRequiresTwoPermission();
    }

    private void viewLayout() {
        /**
         * 步骤1：创建RecyclerView & VirtualLayoutManager 对象并进行绑定
         * */
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        // 创建VirtualLayoutManager对象
        // 同时内部会创建一个LayoutHelperFinder对象，用来后续的LayoutHelper查找
        // 将VirtualLayoutManager绑定到recyclerView
        mRecycler.setLayoutManager(layoutManager);
        /**
         * 步骤2：设置组件复用回收池
         * */
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        viewPool.setMaxRecycledViews(0, 15);
        mRecycler.setRecycledViewPool(viewPool);
        //添加自定义分割线()
        DividerItemDecoration divider = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(mContext, R.drawable.custom_divider)));
        mRecycler.addItemDecoration(divider);
        /**
         * 步骤4:根据数据列表,创建对应的LayoutHelper
         * */

        /*=====================================线性布局==========================================*/
        // 轮播
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setAspectRatio(2);// 设置设置布局内每行布局的宽与高的比
        linearLayoutHelper.setBgColor(getResources().getColor(R.color.white));// 设置背景颜色
        linearLayoutHelper.setDividerHeight(10); // 设置每行Item的距离
        // 设置布局底部与下个布局的间隔
        // 创建自定义的Adapter对象 & 绑定数据 & 绑定对应的LayoutHelper进行布局绘制
        bannerAdapter = new TitleAdapter(mContext, linearLayoutHelper, 1, imgList);
        bannerAdapter.setOnItemClickListener(this);
        // 消息
        LinearLayoutHelper msgLayoutHelper = new LinearLayoutHelper();
        MsgAdapter msgAdapter = new MsgAdapter(mContext, msgLayoutHelper, 1,
                getResources().getStringArray(R.array.msgList));
        //  列表
        LinearLayoutHelper listLayoutHelper = new LinearLayoutHelper();
//              listLayoutHelper.setAspectRatio(3);// 设置设置布局内每行布局的宽与高的比
        listAdapter = new ListAdapter(mContext, getActivity(), listLayoutHelper);
        /**
         *步骤5:将生成的LayoutHelper 交给Adapter，并绑定到RecyclerView 对象
         **/
        // 1. 设置Adapter列表（同时也是设置LayoutHelper列表）
        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
        // 2. 将上述创建的Adapter对象放入到DelegateAdapter.Adapter列表里
        adapters.add(bannerAdapter);
        adapters.add(msgAdapter);
        adapters.add(listAdapter);
        // 3. 创建DelegateAdapter对象 & 将layoutManager绑定到DelegateAdapter
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
        // 4. 将DelegateAdapter.Adapter列表绑定到DelegateAdapter
        delegateAdapter.setAdapters(adapters);
        // 5. 将delegateAdapter绑定到recyclerView
        mRecycler.setAdapter(delegateAdapter);
    }

    private void initRefreshLayout() {
        swipeLayout.setOnRefreshListener(() -> {
            lotteryPresenter.getBannerDataRequest(73);
            lotteryPresenter.getJiangQiRequest("ALL");
        });
    }

    @Override
    public void onItemClickListener(View view, int position) {
        if (imgList.get(0).equals(""))
            return;
        if (0 == (banerLists.get(position).getType())) {
            String h5Url = Utils.getAddTokenUrl(banerLists.get(position).getBannerUrl(),
                    UserInfo.getToken());
            Bundle bundle = new Bundle();
            bundle.putString("h5Url", h5Url);
            if (UserInfo.isLogin()) {
                launchActivity(WebActivity.class, bundle);
            } else {
                ViewBase.showISLogin(mContext, 1);
            }
        } else if (banerLists.get(position).getType() == 1) {
            if ("VOTE_PROMOTE".equals(banerLists.get(position).getBannerNative())) {
                if (UserInfo.isLogin()) {
                    // TODO: 2019/1/28  跳转原生
                } else {
                    ViewBase.showISLogin(mContext, 1);
                }
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (bannerAdapter.getBanner() != null) {
                bannerAdapter.getBanner().stopAutoPlay();
            }
            if (MsgAdapter.MsgViewHolder.mViewFlipper != null) {
                if (MsgAdapter.MsgViewHolder.mViewFlipper.isFlipping()) {
                    MsgAdapter.MsgViewHolder.mViewFlipper.stopFlipping();
                }
            }
        } else {
            if (lotteryPresenter != null) {
                lotteryPresenter.getJiangQiRequest("ALL");
            }
            Logger.i("banner: " + 0);
            if (bannerAdapter.getBanner() != null) {
                bannerAdapter.getBanner().startAutoPlay();
            }
            methodRequiresTwoPermission();

            if (MsgAdapter.MsgViewHolder.mViewFlipper != null) {
                if (!MsgAdapter.MsgViewHolder.mViewFlipper.isFlipping()) {
                    MsgAdapter.MsgViewHolder.mViewFlipper.startFlipping();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!LotteryFragment.this.isHidden()) {
            lotteryPresenter.getJiangQiRequest("ALL");
            lotteryPresenter.getVersionUpdateRequest();
            initRefreshLayout();
        }
    }

    @Override
    public void setSucessLodeData(JiangQiInfo lodeData) {
        swipeLayout.setRefreshing(false);
        listAdapter.setData(lodeData);
    }

    @Override
    public void setDefaultMessage(String message) {
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void setBannerData(List<BannerInfo.BannerBean> bannerInfo) {
        imgList.clear();
        banerLists.clear();
        banerLists = bannerInfo;
        if (bannerInfo.size() != 0) {
            for (BannerInfo.BannerBean entity : bannerInfo) {
                imgList.add(entity.getImgUrl());
            }
        } else {
            imgList.add("");
        }
        bannerAdapter.setData(imgList);
    }

    @Override
    public void setVersionUpdate(UpDataInfo.DataBean data) {
        if (Utils.isUpDate(SystemUtil.getVersionName(mContext), data.getVersionNum())
                && !"".equals(data.getVersionUrl())) {
            ViewBase.showMessageDialog(mContext, data.getVersionUrl());
        }
    }

    @AfterPermissionGranted(Config.READ_EXTERNAL_STORAGE)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if (!(EasyPermissions.hasPermissions(mContext, perms) && !LotteryFragment.this.isHidden())) {
            EasyPermissions.requestPermissions(this, "为了您的正常使用请开启读取权限!",
                    Config.READ_EXTERNAL_STORAGE, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle("提示")
                    .setRationale("为了您的正常使用，请开启相应权限!")
                    .setPositiveButton("去设置")
                    .setNegativeButton("取消")
                    .setRequestCode(Config.READ_EXTERNAL_STORAGE)
                    .build()
                    .show();
        }
    }
}
