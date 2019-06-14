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

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.utils.OkLogger;

import org.json.JSONObject;

import okhttp3.Response;
import zhcw.lottery.znzd.com.selflottery.ready_entity.bean_info.UserInfo;

/**
 * 版    本：1.0
 * 创建日期：2017/9/11
 * 描    述：返回字符串类型的数据
 */
public abstract class StringCallback extends AbsCallback<String> {
    private StringConvert convert;
    private String json;
    private String state;
    private String message;

    protected StringCallback() {
        convert = new StringConvert();
    }

    @Override
    public String convertResponse(Response response) throws Throwable {
        json = convert.convertResponse(response);
        OkLogger.i("body-json: " + json);
        response.close();
        return json;
    }

    @Override
    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
        try {
            JSONObject object = new JSONObject(json);
            state = object.getString("return_code");
            message = object.getString("return_msg");
            if ("SUCCESS".equals(state)) {
                onSuccess(json);
                onSuccess_Obj(object);
            } else if ("TOKEN_ERROR".equals(state)) {
                UserInfo.logout();
                UserInfo.refresh();
                isLogin();
            } else {//if("02".equals(state) || "03".equals(state))
                onMessage(message);
                onMessage(message, state); //需要单独判断返回码的 , 来重写
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void onSuccess(String json);

    public void onSuccess_Obj(JSONObject object) {
    }

    public void onMessage(String message) {
    }

    public abstract void isLogin();

    public void onMessage(String message, String state) {
    }
}
