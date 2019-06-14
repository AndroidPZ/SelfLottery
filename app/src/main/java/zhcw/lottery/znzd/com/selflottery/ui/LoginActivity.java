package zhcw.lottery.znzd.com.selflottery.ui;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import butterknife.BindView;
import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.base.BaseActivity;
import zhcw.lottery.znzd.com.selflottery.contact.LoginContact;
import zhcw.lottery.znzd.com.selflottery.presenter.LoginPresenter;
import zhcw.lottery.znzd.com.selflottery.util.SpannableStringUtils;
import zhcw.lottery.znzd.com.selflottery.util.ToastUtil;
import zhcw.lottery.znzd.com.selflottery.util.Utils;
import zhcw.lottery.znzd.com.selflottery.util.VerificationCodeUtil;
import zhcw.lottery.znzd.com.selflottery.widgets.ViewBase;

/**
 * Created by XPZ on 2018/4/16.
 */
public class LoginActivity extends BaseActivity implements LoginContact.ILoginView, View.OnClickListener,
        View.OnLayoutChangeListener, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.iv_login)
    ImageView ivLogin;
    @BindView(R.id.et_operator)
    EditText mPhone;
    @BindView(R.id.et_login_pwd)
    EditText smsCode;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.login_view)
    ScrollView loginView;
    @BindView(R.id.root_view)
    LinearLayout rootView;
    @BindView(R.id.huoqu_yanzhegnma)
    TextView mCode;
    @BindView(R.id.cb_login_disclaimer)
    AppCompatCheckBox mCbDisclaimer;
    @BindView(R.id.tv_login_disclaimer_msg)
    TextView mTvDisclaimerMsg;


    private int mLogoHeight;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;
    private boolean checkUpResult = true;
    private VerificationCodeUtil mTimeCountUtil;
    private LoginPresenter loginPresenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        loginPresenter = new LoginPresenter(this, this);
        initViews();
        initListeners();
    }

    private void initViews() {
        IsChecked(false);
        //获取屏幕高度
        int screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
        mTimeCountUtil = new VerificationCodeUtil(mCode, 1000 * 60, 1000, this);

        mTvDisclaimerMsg.setMovementMethod(LinkMovementMethod.getInstance());
        mTvDisclaimerMsg.setText(SpannableStringUtils.getBuilder("选中及表示同意")
                .append("《智能终端服务协议》")
                .setForegroundColor(getResources().getColor(R.color.red_900))
                .setUnderline()
                .setClickSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        launchActivity(DisclaimerActivity.class);
                    }
                })
                .append("，点击可查看协议内容。").create());
    }

    private void initListeners() {
        mCbDisclaimer.setChecked(false);
        mCbDisclaimer.setOnCheckedChangeListener(this::onCheckedChanged);
        btnLogin.setOnClickListener(this::onClick);
        mCode.setOnClickListener(this::onClick);
        mPhone.addTextChangedListener(new MyTextWatcher(11));
        smsCode.addTextChangedListener(new MyTextWatcher(4));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.huoqu_yanzhegnma://获取验证码
                smsCode.setText("");
                getCode();
                break;
            case R.id.btn_login:
                loginData();
                doLogin();
                break;
        }
    }

    /**
     * 获取验证码
     */
    public void getCode() {
        String _edtPhone = mPhone.getText().toString().trim();
        if (Utils.isMobileNO(_edtPhone)) {
            mTimeCountUtil.start();
            loginPresenter.getSecurityCode(_edtPhone);
        } else {
            ToastUtil.showShortToast("您输入的号码有误，请检查输入的号码");
        }
    }

    private void loginData() {
        String operatorId = mPhone.getText().toString().trim();
        String password = smsCode.getText().toString().trim();
        if (operatorId.equals("")) {
            YoYo.with(Techniques.Tada).duration(700).playOn(mPhone);
            ToastUtil.showShortToast("手机号不能为空");
            checkUpResult = false;
            return;
        } else {
            if (Utils.isMobileNO(operatorId)) {
                checkUpResult = true;
            } else {
                YoYo.with(Techniques.Tada).duration(700).playOn(mPhone);
                ToastUtil.showShortToast("请输入正确的手机号");
                checkUpResult = false;
                return;
            }
        }
        if (password.equals("")) {
            YoYo.with(Techniques.Tada).duration(700).playOn(smsCode);
            ToastUtil.showShortToast("验证码不能为空");
            checkUpResult = false;
        } else {
            checkUpResult = true;
        }
    }

    /**
     * 发起登录
     */
    private void doLogin() {
        if (checkUpResult) {
            if (mCbDisclaimer.isChecked()) {
                String strPhone = mPhone.getText().toString().trim(); //手机号
                String strPwd = smsCode.getText().toString().trim(); //验证码
                loginPresenter.sendLogin(strPhone, strPwd);
            } else {
                ViewBase.showSnackMessage(this, "请选中底部的服务协议!");
            }
        }
    }

    @Override
    public void setSecurityCodeError(String message) {
        ToastUtil.showShortToast(message);
        IsChecked(false);
    }

    @Override
    public void setSecurityCodeSucess() {
        ToastUtil.showShortToast("发送验证码成功");
    }

    @Override
    public void setLoginError(String message) {
        ToastUtil.showShortToast(message);
        smsCode.setText("");
        IsChecked(false);
    }

    @Override
    public void setLoginSucess() {
        if (activity != null) {
            launchActivity(activity);
        } else {
            launchActivity(MainAdminActivity.class);
        }
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        rootView.addOnLayoutChangeListener(this);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        //old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            mLogoHeight = ivLogin.getHeight();
            performAnimation(ivLogin, mLogoHeight, 0);
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            performAnimation(ivLogin, 0, mLogoHeight);
        }
    }

    private void performAnimation(final View target, final int start, final int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        final IntEvaluator mEvaluator = new IntEvaluator();//int类型的计算器
        valueAnimator.addUpdateListener(animation -> {
            int current = (int) animation.getAnimatedValue();
            float fraction = current / 100f;
            target.getLayoutParams().height = mEvaluator.evaluate(fraction, start, end);
            target.requestLayout();
        });
        valueAnimator.setDuration(500).start();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        IsChecked(isChecked);
    }

    /**
     * 对按钮的控制
     *
     * @param isChecked
     */
    private void IsChecked(boolean isChecked) {
        btnLogin.setEnabled(isChecked);
        btnLogin.setSelected(!isChecked);
    }

    /**
     * 键盘操作
     */
    class MyTextWatcher implements TextWatcher {

        /**
         * 设置响应输入长度
         */
        private final int mLength;

        public MyTextWatcher(int lh) {
            this.mLength = lh;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            int length = charSequence.length();
            if (mLength == 4) {
                if (length == mLength) {
                    InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    if (mCbDisclaimer.isChecked()) {
                        IsChecked(true);
                    } else {
                        ViewBase.showSnackMessage(LoginActivity.this, "请选中底部的服务协议!");
                    }
                }
            } else {
                if (length == mLength) {
                    String phone = mPhone.getText().toString();
                    if (Utils.isMobileNO(phone)) {
                        smsCode.requestFocus();
                    } else {
                        ToastUtil.showShortToast("请输入正确的手机号");
                    }
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }
}
