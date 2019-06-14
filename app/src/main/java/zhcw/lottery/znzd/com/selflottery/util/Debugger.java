package zhcw.lottery.znzd.com.selflottery.util;

import com.lzy.okgo.model.Response;

import java.util.Set;

import okhttp3.Call;
import okhttp3.Headers;

/**
 * Created by xpz on 2018/5/11.
 * 接口错误调试
 */

public class Debugger {

    private static final String TAG = "XPZ";

    /**
     * 接口失败状态描述  可用于调试
     * @param response
     * @param <T>
     */
    public static <T> void handleError(Response<T> response) {
        if (response == null) return;
        if (response.getException() != null) response.getException().printStackTrace();
        StringBuilder sb;
        Call call = response.getRawCall();
        if (call != null) {
            Logger.d(TAG,call.request().method() + "\n" + "url：" + call.request().url());
            Headers requestHeadersString = call.request().headers();
            Set<String> requestNames = requestHeadersString.names();
            sb = new StringBuilder();
            for (String name : requestNames) {
                sb.append(name).append(" ： ").append(requestHeadersString.get(name)).append("\n");
            }
            Logger.d(TAG,sb.toString());
        }
        okhttp3.Response rawResponse = response.getRawResponse();
        if (rawResponse != null) {
            Headers responseHeadersString = rawResponse.headers();
            Set<String> names = responseHeadersString.names();
            sb = new StringBuilder();
            sb.append("stateCode ： ").append(rawResponse.code()).append("\n");
            for (String name : names) {
                sb.append(name).append(" ： ").append(responseHeadersString.get(name)).append("\n");
            }
            Logger.d(TAG,sb.toString());
        }
    }
}
