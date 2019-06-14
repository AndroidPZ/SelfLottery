package zhcw.lottery.znzd.com.selflottery.ui.lottery.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xpz on 2018/10/9.
 * 首页轮播图实体
 */
public class BannerInfo implements Serializable {
    /**
     * banner : [{"bannerNative":"","bannerUrl":"http://lj.zhcvideo.com/lottery_lj/html/scan_ssq/wzssq_index.html","id":4,"imgUrl":"http://lf.zhcvideo.com/images/banner/e723d1432509475d8f1bc0630120d30f.png","name":"福彩双色球","type":0},{"bannerNative":"","bannerUrl":"http://lj.zhcvideo.com/lottery_lj/html/scan_ssq/scanssq_liucheng.html","id":2,"imgUrl":"http://lf.zhcvideo.com/images/banner/b96d61877874414983781cdc90c86572.png","name":"提现流程","type":0},{"bannerNative":"","bannerUrl":"http://lj.zhcvideo.com/lottery_lj/html/scan_klsf/klsf_index.html","id":5,"imgUrl":"http://lf.zhcvideo.com/images/banner/3a9c9417e2bc41529c274777410ae22b.png","name":"福彩快乐十分","type":0},{"bannerNative":"","bannerUrl":"https://lj.zhcvideo.com/lottery_lj/html/huodong/IndexLong.html","id":1,"imgUrl":"http://lf.zhcvideo.com/images/banner/abab8c3da6bf434f96cc738556e73fc5.jpg","name":"福彩大家乐","type":0},{"bannerNative":"VOTE_PROMOTE","bannerUrl":"https://lj.zhcvideo.com/lottery_lj/html/huodong/bannerLong.html","id":3,"imgUrl":"http://lf.zhcvideo.com/images/banner/9c9251eecb7a478cbd87dc12be645770.jpg","name":"推广有好礼","type":0}]
     * return_code : SUCCESS
     * return_msg : 成功
     */

    private String return_code;
    private String return_msg;
    private List<BannerBean> banner;

    public String getReturn_code() {
        return return_code == null ? "" : return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg == null ? "" : return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public static class BannerBean {
        /**
         * bannerNative :
         * bannerUrl : http://lj.zhcvideo.com/lottery_lj/html/scan_ssq/wzssq_index.html
         * id : 4
         * imgUrl : http://lf.zhcvideo.com/images/banner/e723d1432509475d8f1bc0630120d30f.png
         * name : 福彩双色球
         * type : 0:跳H5；1:跳原生；2:不跳转
         */
        private String imgUrl;        //图片地址
        private String name;          //标题
        private String bannerUrl;     //H5跳转地址
        private String bannerNative;  //原生跳转地址
        private int type;          //0：跳转H5  1跳转原生 2：不做任何操作
        private int id;

        public String getImgUrl() {
            return imgUrl == null ? "" : imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBannerUrl() {
            return bannerUrl == null ? "" : bannerUrl;
        }

        public void setBannerUrl(String bannerUrl) {
            this.bannerUrl = bannerUrl;
        }

        public String getBannerNative() {
            return bannerNative == null ? "" : bannerNative;
        }

        public void setBannerNative(String bannerNative) {
            this.bannerNative = bannerNative;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }


        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
