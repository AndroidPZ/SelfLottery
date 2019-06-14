package zhcw.lottery.znzd.com.selflottery.ui.my;

import android.Manifest;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.base.BaseFragment;
import zhcw.lottery.znzd.com.selflottery.contact.MyContact;
import zhcw.lottery.znzd.com.selflottery.presenter.MyPresenter;
import zhcw.lottery.znzd.com.selflottery.ready_entity.bean_info.UserInfo;
import zhcw.lottery.znzd.com.selflottery.ui.my.activity.CertificationActivity;
import zhcw.lottery.znzd.com.selflottery.ui.my.activity.DetailViewActivity;
import zhcw.lottery.znzd.com.selflottery.ui.my.activity.OwnerApplyActivity;
import zhcw.lottery.znzd.com.selflottery.ui.my.activity.SettingActivity;
import zhcw.lottery.znzd.com.selflottery.ui.my.activity.WalletActivity;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.User_Info;
import zhcw.lottery.znzd.com.selflottery.util.Logger;
import zhcw.lottery.znzd.com.selflottery.util.PreferenceUtil;
import zhcw.lottery.znzd.com.selflottery.util.ToastUtil;
import zhcw.lottery.znzd.com.selflottery.util.Utils;
import zhcw.lottery.znzd.com.selflottery.widgets.ViewBase;


/**
 * Created by xpz on 2018/9/17.
 * 我的
 */
public class MyFragment extends BaseFragment implements MyContact.IMyView, EasyPermissions.PermissionCallbacks {

    @BindView(R.id.telephone_number)
    TextView telephoneNumber;
    @BindView(R.id.my_seting)
    AutoRelativeLayout mySeting;
    @BindView(R.id.iv_my_certification_ico)
    ImageView ivMyCerIco;
    @BindView(R.id.tv_my_certification_type)
    TextView tvMyCerType;
    @BindView(R.id.my_certification)
    AutoRelativeLayout myCertification;
    @BindView(R.id.iv_my_head_pic)
    SimpleDraweeView ivHead;
    @BindView(R.id.iv_my_default_head_pic)
    ImageView ivDefaultHead;

    private MyPresenter myPresenter;
    private static final int READ_STORAGE = 102;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_my;
    }

    @Override
    protected void init() {
        mySeting.setOnLongClickListener(v -> {
            UserInfo.logout();
            PreferenceUtil.getInstance().getEditor().clear().commit();
            ToastUtil.showShortToast("已退出");
          /*  ivMyCerIco.setImageDrawable(getResources().getDrawable((R.drawable.ic_person_certification_no)));
            tvMyCerType.setText("未认证");
            tvMyCerType.setTextColor(Color.parseColor("#E53935"));*/
            telephoneNumber.setText("请登录");
            return true;
        });
        methodRequiresTwoPermission();
    }

    @OnClick({R.id.headers, R.id.my_wallet, R.id.my_details, R.id.my_seting
            , R.id.my_certification, R.id.my_shenfen})
    public void onViewClicked(View view) {
        if (UserInfo.isIsLogin()) {
            switch (view.getId()) {
                case R.id.headers://头像
                    break;
                case R.id.my_wallet://我的钱包
                    launchActivity(WalletActivity.class);
                    break;
                case R.id.my_details: //订单详情
                    launchActivity(DetailViewActivity.class);
                    break;
                case R.id.my_seting://设置
                    launchActivity(SettingActivity.class);
                    break;
                case R.id.my_certification: //实名
                    launchActivity(CertificationActivity.class);
                    break;
                case R.id.my_shenfen: //身份
                    if (Utils.isNetworkConnected()) {
                        launchActivity(OwnerApplyActivity.class);
                    } else {
                        ToastUtil.showShortToast("");
                    }
                    break;
                default:
            }
        } else {
            ViewBase.showISLogin(mContext, 1);
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            Logger.i("XPZ", "My不可见");
        } else {
            Logger.i("XPZ", "My当前可见");
            methodRequiresTwoPermission();

            if (UserInfo.isIsLogin()) {
                telephoneNumber.setText(UserInfo.getAppUserNickName());
            } else {
                telephoneNumber.setText("请登录");
            }
            if (!"".equals(UserInfo.getToken()) && !MyFragment.this.isHidden()) {
                if (myPresenter != null) {
                    myPresenter.getDefaultDataRequest(UserInfo.getToken());
                }
            }
        }
    }

    @AfterPermissionGranted(READ_STORAGE)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if (!(EasyPermissions.hasPermissions(mContext, perms) && !MyFragment.this.isHidden())) {
            EasyPermissions.requestPermissions(this, "为了您的正常使用请打启读取权限!",
                    READ_STORAGE, perms);
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
                    .setRequestCode(READ_STORAGE)
                    .build()
                    .show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!MyFragment.this.isHidden()) {
            myPresenter = new MyPresenter(getActivity(), this);
        }
        if (!"".equals(UserInfo.getAppUserHeadImgUrl())) {
            ivDefaultHead.setVisibility(View.GONE);
            ivHead.setVisibility(View.VISIBLE);
            Utils.setRoundImage(ivHead, UserInfo.getAppUserHeadImgUrl());
        } else {
            ivDefaultHead.setVisibility(View.VISIBLE);
            ivHead.setVisibility(View.GONE);
        }

        if (UserInfo.isIsLogin()) {
            telephoneNumber.setText(UserInfo.getAppUserNickName());
        } else {
            telephoneNumber.setText("请登录");
        }
        if (!"".equals(UserInfo.getToken()) && !MyFragment.this.isHidden()) {
            myPresenter.getDefaultDataRequest(UserInfo.getToken());
        }

      /*  if (UserInfo.getAudit() == 1) {
            ivMyCerIco.setImageDrawable(getResources().getDrawable((R.drawable.ic_person_certification_yes)));
            tvMyCerType.setText("已认证");
            tvMyCerType.setTextColor(Color.parseColor("#20d39a"));
        } else {
            ivMyCerIco.setImageDrawable(getResources().getDrawable((R.drawable.ic_person_certification_no)));
            tvMyCerType.setText("未认证");
            tvMyCerType.setTextColor(Color.parseColor("#E53935"));
        }*/
    }

    @Override
    public void setDefaultSucess(User_Info.UserBean entity) {
        Logger.i("业主认证: " + entity.getIsOwner());
        Logger.i("微信授权登录: " + entity.getIsAuth());

     /*   if (UserInfo.getAudit() == 1) {
            ivMyCerIco.setImageDrawable(getResources().getDrawable((R.drawable.ic_person_certification_yes)));
            tvMyCerType.setText("已认证");
            tvMyCerType.setTextColor(Color.parseColor("#20d39a"));
        } else {
            ivMyCerIco.setImageDrawable(getResources().getDrawable((R.drawable.ic_person_certification_no)));
            tvMyCerType.setText("未认证");
            tvMyCerType.setTextColor(Color.parseColor("#E53935"));
        }*/
    }

    @Override
    public void setDefaultError(String message) {
     /*   ivMyCerIco.setImageDrawable(getResources().getDrawable((R.drawable.ic_person_certification_no)));
        tvMyCerType.setText("未认证");
        tvMyCerType.setTextColor(Color.parseColor("#E53935"));*/
    }
}
