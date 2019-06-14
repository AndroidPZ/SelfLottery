/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package zhcw.lottery.znzd.com.selflottery.http.callback;

import android.app.Dialog;
import android.content.Context;

import com.lzy.okgo.request.base.Request;

import zhcw.lottery.znzd.com.selflottery.base.SimpleBaseUIFactory;
import zhcw.lottery.znzd.com.selflottery.widgets.ViewBase;

public abstract class StringDialogCallback extends StringCallback {
    public Context mContext;
    //    protected CustomDialog mDialog;
    protected Dialog mDialog;
    private SimpleBaseUIFactory simpleBaseUIFactory;

    @Override
    public void isLogin() {
        ViewBase.showISLogin(mContext, 2);
    }

    public StringDialogCallback(Context context) {
        mContext = context;
        simpleBaseUIFactory = new SimpleBaseUIFactory(context);
        mDialog = simpleBaseUIFactory.createLoadingDialog();
  /*      CustomDialog.Builder builder = new CustomDialog.Builder(context)
                .isShowDecorView(true)//默认显示
                .cancelTouchout(false)
                .view(R.layout.callback_logingview)
                .heightDimenRes(R.dimen.dialog_loging_height)
                .widthDimenRes(R.dimen.dialog_loging_width)
                .style(R.style.CallbackDialog);
        mDialog = builder.build();*/
    }

    @Override
    public void onStart(Request<String, ? extends Request> request) {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    @Override
    public void onFinish() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog.cancel();
        }
    }
}
