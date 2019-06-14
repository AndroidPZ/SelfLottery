package zhcw.lottery.znzd.com.selflottery.ui.my.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.base.BaseActivity;
import zhcw.lottery.znzd.com.selflottery.config.Config;
import zhcw.lottery.znzd.com.selflottery.contact.OwnerContact;
import zhcw.lottery.znzd.com.selflottery.presenter.OwnerPresenter;
import zhcw.lottery.znzd.com.selflottery.ready_entity.bean_info.UserInfo;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.OwnerUser_Info;
import zhcw.lottery.znzd.com.selflottery.util.Logger;
import zhcw.lottery.znzd.com.selflottery.util.ToastUtil;
import zhcw.lottery.znzd.com.selflottery.util.Utils;
import zhcw.lottery.znzd.com.selflottery.widgets.ClearEditText;
import zhcw.lottery.znzd.com.selflottery.widgets.ViewBase;

/**
 * 业主认证
 *
 * @author XPZ
 */
public class OwnerApplyActivity extends BaseActivity implements OwnerContact.IOwnerView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    /**
     * 姓名
     */
    @BindView(R.id.cet_name)
    ClearEditText cetName;
    /**
     * 站点编号
     */
    @BindView(R.id.cet_station_num)
    ClearEditText cetStationNum;
    /**
     * 身份证号
     */
    @BindView(R.id.cet_id_number)
    ClearEditText cetIdNumber;
    /**
     * 手机号
     */
    @BindView(R.id.cet_station_phone)
    ClearEditText cetStationPhone;
    /**
     * 站点地址
     */
    @BindView(R.id.cet_station_address)
    ClearEditText cetStationAddress;
    @BindView(R.id.tv_type_id)
    TextView tvTypeId;
    @BindView(R.id.tb_true)
    Button tbTrue;
    @BindView(R.id.owner_apply_edit)
    AutoLinearLayout ownerApplyEdit;
    @BindView(R.id.owner_apply_view)
    AutoLinearLayout ownerApplyView;
    @BindView(R.id.ll_status)
    AutoLinearLayout llStatus;
    @BindView(R.id.tv_name_view)
    TextView tvNameView;
    @BindView(R.id.tv_phone_view)
    TextView tvPhoneView;
    @BindView(R.id.tv_idnumber_view)
    TextView tvIdnumberView;
    @BindView(R.id.tv_stationnum_view)
    TextView tvStationnumView;
    @BindView(R.id.tv_stationaddress_view)
    TextView tvStationaddressView;
    @BindView(R.id.tv_time_view)
    TextView tvTimeView;
    @BindView(R.id.tv_type_id_view)
    TextView tvTypeIdView;
    @BindView(R.id.tb_getcode)
    Button tbGetcode;
    @BindView(R.id.tv_pcnum_view)
    TextView tvPcnumView;
    @BindView(R.id.ll_pc_num)
    AutoLinearLayout llPcNum;
    @BindView(R.id.tv_mac_view)
    TextView tvMacView;
    @BindView(R.id.ll_mac_num)
    AutoLinearLayout llMacNum;
    @BindView(R.id.cet_station_city)
    TextView cetStationCity;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    private OwnerPresenter ownerPresenter;
    private boolean checkUpResult = true;
    private OptionsPickerView<Object> pvOptions;
    private String city;
    private ArrayList<Object> mCtiyArrayMap;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_owner_apply;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ownerPresenter = new OwnerPresenter(this, this);
        toolbar.setTitle(R.string.owner_title);
        setSupportActionBar(toolbar);
        //添加回弹
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);
        //设置是否有返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initOptionPicker();
        initView();
    }

    private void initView() {
        ownerPresenter.getOwnerRequest(UserInfo.getToken());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //重写ToolBar返回按钮的行为，防止重新打开父Activity重走生命周期方法
            case android.R.id.home:
                finish();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setSucessLodeData(OwnerUser_Info.TerminalBean lodeData) {
        tvTypeId.setText(Utils.getIntegerArrayMap().get(lodeData.getStatus()));
        tvTypeIdView.setText(Utils.getIntegerArrayMap().get(lodeData.getStatus()));
        tvNameView.setText(lodeData.getOwnerName());
        tvPhoneView.setText(lodeData.getStationPhone());
        tvIdnumberView.setText(lodeData.getIdNumber());
        tvStationnumView.setText(lodeData.getStationNum());
        tvStationaddressView.setText(lodeData.getStationAddress());
        tvTimeView.setText(lodeData.getCreateTime());
        tvMacView.setText(lodeData.getMacAddress());
        tvPcnumView.setText(lodeData.getTerminalNum());
    }

    /**
     * 按照状态修改UI
     *
     * @param status
     */
    @Override
    public void setChangeView(String status) {
        Logger.i("XPZ_业主信息状态: ", status);
        switch (status) {
            case "1"://业主认证成功但未绑定机器
                llMacNum.setVisibility(View.VISIBLE);
                llPcNum.setVisibility(View.VISIBLE);
                ownerApplyEdit.setVisibility(View.GONE);
                ownerApplyView.setVisibility(View.VISIBLE);
                tbGetcode.setVisibility(View.GONE);
                break;
            case "3"://机器信息未审核
            case "9"://机器禁用
                ownerApplyEdit.setVisibility(View.GONE);
                ownerApplyView.setVisibility(View.VISIBLE);
                tbGetcode.setVisibility(View.GONE);
                break;
            case "2"://业主认证成功但未绑定机器
                tbGetcode.setVisibility(View.VISIBLE);
                ownerApplyEdit.setVisibility(View.GONE);
                ownerApplyView.setVisibility(View.VISIBLE);
                break;
            case "5"://机器信息验证失败 (需要重新获取)
                ownerApplyEdit.setVisibility(View.GONE);
                ownerApplyView.setVisibility(View.VISIBLE);
                IsChecked(true, "tbGetcode");
                tbGetcode.setText("重新获取绑定码");
                break;
            case "0"://业主信息未审核
                ownerApplyEdit.setVisibility(View.GONE);
                ownerApplyView.setVisibility(View.VISIBLE);
                llStatus.setVisibility(View.VISIBLE);
                tbTrue.setVisibility(View.GONE);
                break;
            case "4"://业主认证失败
                ownerApplyEdit.setVisibility(View.VISIBLE);
                ownerApplyView.setVisibility(View.GONE);
                llStatus.setVisibility(View.VISIBLE);
                IsChecked(true, "tbTrue");
                tbTrue.setText("重新提交");
                break;
            case "Enabled"://业主认证失败
                IsChecked(false, "tbTrue");
                break;
            case "TER_NULL":
                ownerApplyEdit.setVisibility(View.VISIBLE);
                llStatus.setVisibility(View.INVISIBLE);
                ownerApplyView.setVisibility(View.GONE);
                IsChecked(true, "tbTrue");
                tbTrue.setText("提  交");
                break;
            default:
        }
    }

    @Override
    public void setDefaultMessge(String message) {
        ToastUtil.showShortToast(message);
    }

    /**
     * 对按钮的控制
     *
     * @param isChecked
     */
    private void IsChecked(boolean isChecked, String name) {
        switch (name) {
            case "tbTrue":
                tbTrue.setEnabled(isChecked);
                tbTrue.setSelected(!isChecked);
                break;
            case "tbGetcode":
                tbGetcode.setEnabled(isChecked);
                tbGetcode.setSelected(!isChecked);
                break;
            default:
        }
    }

    @Override
    public void setBindQRcode(String QRcode) {
        ViewBase.DetailCodeDialog(getResources().getString(R.string.owner_qrtxt),
                ViewBase.encodeAsBitmap(QRcode), this);
        IsChecked(false, "tbGetcode");
    }

    private void verificationData() {
        String mName = cetName.getText().toString().trim();
        String mStationNum = cetStationNum.getText().toString().trim();
        String mIdNumber = cetIdNumber.getText().toString().trim();
        String mAddress = cetStationAddress.getText().toString().trim();
        String mPhone = cetStationPhone.getText().toString().trim();
        CharSequence cityText = cetStationCity.getText();
        if ("".equals(mName)) {
            YoYo.with(Techniques.Tada).duration(700).playOn(cetName);
            ToastUtil.showShortToast("姓名不能为空");
            checkUpResult = false;
            return;
        } else {
            checkUpResult = true;
        }
        if ("".equals(mStationNum)) {
            YoYo.with(Techniques.Tada).duration(700).playOn(cetStationNum);
            ToastUtil.showShortToast("站点号不能为空");
            checkUpResult = false;
            return;
        } else if (!TextUtils.isEmpty(mStationNum) && mStationNum.length() < Config.STATIONNUM_SIZE) {
            YoYo.with(Techniques.Tada).duration(700).playOn(cetStationNum);
            ToastUtil.showShortToast("请输入正确的站点编号");
            checkUpResult = false;
            return;
        } else {
            checkUpResult = true;
        }
        if (!"".equals(Utils.IDCardValidate(mIdNumber))) {
            YoYo.with(Techniques.Tada).duration(700).playOn(cetIdNumber);
            ToastUtil.showShortToast(Utils.IDCardValidate(mIdNumber));
            checkUpResult = false;
            return; //身份账号
        } else {
            checkUpResult = true;
        }
        if (!Utils.isMobileNO(mPhone)) {
            YoYo.with(Techniques.Tada).duration(700).playOn(cetStationPhone);
            ToastUtil.showShortToast("请输入正确的手机号");
            checkUpResult = false;
            return;
        } else {
            checkUpResult = true;
        }
        if ("".equals(cityText)) {
            YoYo.with(Techniques.Tada).duration(700).playOn(cetStationCity);
            ToastUtil.showShortToast("区域不能为空,请选择...");
            checkUpResult = false;
            return;
        } else {
            checkUpResult = true;
        }

        if ("".equals(mAddress)) {
            YoYo.with(Techniques.Tada).duration(700).playOn(cetStationAddress);
            ToastUtil.showShortToast("站点地址不能为空");
            checkUpResult = false;
            return;
        } else {
            checkUpResult = true;
        }
        if (checkUpResult) {
            ownerPresenter.getOwnerBindRequest(UserInfo.getToken(), mName, mStationNum, mIdNumber,
                    mPhone, mAddress, city);
        }
    }

    private void initOptionPicker() {//条件选择器初始化

        //准备数据
        mCtiyArrayMap = getListCity();
        pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            //返回的分别是三个级别的选中位置
            city = (String) mCtiyArrayMap.get(options1);
            cetStationCity.setText(city);
            Logger.i(city);
        }).setOptionsSelectChangeListener((options1, options2, options3) -> cetStationCity.setText((String) mCtiyArrayMap.get(options1)))
                .setTitleText("选择城市")
                .setContentTextSize(20)//设置滚轮文字大小
                .setSelectOptions(0)//默认选中项
                .setDividerColor(getResources().getColor(R.color.red_900))//设置分割线的颜色
                .setBgColor(getResources().getColor(R.color.white))
                .setTitleBgColor(getResources().getColor(R.color.grey_300))
                .setTitleColor(getResources().getColor(R.color.grey_500))
                .setCancelColor(getResources().getColor(R.color.grey_700))
                .setSubmitColor(getResources().getColor(R.color.blue_900))
                .setTextColorCenter(getResources().getColor(R.color.red_700))
                .setBackgroundId(getResources().getColor(R.color.white)) //设置外部遮罩颜色
                .isRestoreItem(false)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .build();
        pvOptions.setPicker(mCtiyArrayMap);//一级选择器
        /*pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        //pvOptions.setPicker(Utils.getCityArrayMap(), null,null);//三级选择器
    }

    @NonNull
    private ArrayList<Object> getListCity() {
        ArrayList<Object> mCtiyArrayMap = new ArrayList<>();
        mCtiyArrayMap.add("呼和浩特");
        mCtiyArrayMap.add("赤峰");
        mCtiyArrayMap.add("包头");
        mCtiyArrayMap.add("乌海");
        mCtiyArrayMap.add("通辽");
        mCtiyArrayMap.add("鄂尔多斯");
        mCtiyArrayMap.add("呼伦贝尔");
        mCtiyArrayMap.add("巴彦淖尔");
        mCtiyArrayMap.add("乌兰察布");
        mCtiyArrayMap.add("锡林郭勒盟");
        mCtiyArrayMap.add("兴安盟");
        mCtiyArrayMap.add("阿拉善盟");
        mCtiyArrayMap.add("满洲里 ");
        mCtiyArrayMap.add("二连浩特");
        mCtiyArrayMap.add("自治区直属");
        return mCtiyArrayMap;
    }

    @OnClick({R.id.tb_true, R.id.tb_getcode, R.id.cet_station_city})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tb_true: //提交认证数据
                verificationData();
                break;
            case R.id.tb_getcode: // 获取绑定码
                ownerPresenter.getBindQrcodeRequest(UserInfo.getToken());
                break;
            case R.id.cet_station_city: // 选择区域
                //选择地区是 , 先隐藏软键盘
                hideKeyboard(cetStationCity);
                if (pvOptions != null) {
                    pvOptions.show();
                }
                break;
            default:
        }
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive()) {
            Logger.i("关闭");
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
