package zhcw.lottery.znzd.com.selflottery.util;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * 
 * @ClassName: SignUtil 
 * @Description: 加密签名工具类
 * @date 2018年04月04日 10:00:00 
 *
 */
public class SignUtil {

    /**
     * @title 获取加密签名
     * @param parameters
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String getSign(SortedMap<String,String> parameters , String key){
        StringBuffer sb = new StringBuffer();
        //所有参与传参的参数按照accsii排序（升序）
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        //循环拼接每个参数
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"secretKey".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        //最后拼接加密密钥，拼上日期变为动态密钥，每天变化
        sb.append("secretKey=" + key);
        //进行MD5加密，并转换为大写
        String sign = MD5Util.getStrrMD5(sb.toString()).toUpperCase();
        return sign;
    }

    public static SortedMap<String, String> getParamsMap(JSONObject jsonParams)  {
    	SortedMap<String, String> parameters = new TreeMap<String, String>();
        Iterator<String> keySet = jsonParams.keys();
        for (Iterator<String> it = keySet; it.hasNext(); ) {
            String o = it.next();
            String key = String.valueOf(o);
            try {
                parameters.put(key, jsonParams.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    	return parameters;
    }

}
