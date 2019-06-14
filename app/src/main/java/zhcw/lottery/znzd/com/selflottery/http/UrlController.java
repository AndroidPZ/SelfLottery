package zhcw.lottery.znzd.com.selflottery.http;

/**
 * @author ZhengTiantian
 * @version V1.0
 * @Description: 开发环境控制器
 */
public class UrlController {

    private String url;

    public static UrlController getInstance(boolean isOfficial) {
        SingletonController.instance.init(isOfficial);
        return SingletonController.instance;
    }

    public void init(boolean isOfficial) {
        if (isOfficial) {
            setOfficialUrl();
        } else {
            setTestUrl();
        }
    }

    private void setOfficialUrl() {
        url = "http://self.zhcvideo.com";
    }

    private void setTestUrl() {
        url = "http://selftest.zhcvideo.com";
    }

    public String getUrl() {
        return url;
    }

    private static class SingletonController {
        public final static UrlController instance = new UrlController();
    }
}
