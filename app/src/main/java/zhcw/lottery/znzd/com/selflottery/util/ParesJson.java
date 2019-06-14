package zhcw.lottery.znzd.com.selflottery.util;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

import zhcw.lottery.znzd.com.selflottery.config.ConstantValue;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.ChildDetail;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.Detail_Info;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.PatrolGroup;


/**
 * Created by XPZ on 2017/10/11.
 * 解析订单数据
 */

public class ParesJson {

    private static String subRed;
    private static String subblue;

    /***
     * 订单信息数据解析
     * @param object
     * @return
     */
    public static ArrayList<MultiItemEntity> getDetailInforResult_new(Detail_Info object) {

        ArrayList<MultiItemEntity> dataList = new ArrayList<>();

        List<Detail_Info.OrdersBean> orders = object.getOrders();
        for (Detail_Info.OrdersBean ordersBean : orders) {

            PatrolGroup patrolGroup = new PatrolGroup();
            patrolGroup.setLotteryType(ordersBean.getLotteryType());
            patrolGroup.setOrderTime(ordersBean.getOrderTime());
            patrolGroup.setQrcodeStr(ordersBean.getQrcodeStr());
            patrolGroup.setOrderNum(ordersBean.getOrderNum());
            patrolGroup.setMultiple(ordersBean.getMultiple());
            patrolGroup.setOrderStake(ordersBean.getOrderStake());
            patrolGroup.setOrderAmount(ordersBean.getOrderAmount());
            patrolGroup.setIssue(ordersBean.getIssue());
            patrolGroup.setAllPrint(ordersBean.getAllPrint());
            patrolGroup.setSelect(false);

            List<Detail_Info.OrdersBean.LotterysBean> lotterys = ordersBean.getLotterys();
            for (Detail_Info.OrdersBean.LotterysBean lottery : lotterys) {
                ChildDetail childDetail = new ChildDetail();
                childDetail.setLotteryNum(lottery.getLotteryNum());
                childDetail.setPlayType(lottery.getPlayType());
                childDetail.setLotteryAmount(lottery.getLotteryAmount());
                childDetail.setLotteryStake(lottery.getLotteryStake());
                childDetail.setPrintStatus(lottery.getPrintStatus());

                String lotteryNum = lottery.getLotteryNum();
                String lotteryType = ordersBean.getLotteryType();
                if ("双色球".equals(lotteryType)) {

                    if (lottery.getPlayType().equals("单式")) {
                        //截取每一注
                        String[] split = lotteryNum.split("\\|");
                        StringBuffer stringBufferR = new StringBuffer("");
                        StringBuffer stringBufferB = new StringBuffer("");
                        int tepy = 0;
                        int length = split.length;
                        for (String str : split) {
                            tepy = ++tepy;
                            try {
                                subRed = str.substring(0, lotteryNum.indexOf("#"));
                                subblue = str.substring(lotteryNum.indexOf("#") + 1);
                            } catch (Exception e) {
                                
                            }
                           
                            if (tepy == length) {
                                stringBufferR.append(subRed);
                                stringBufferB.append(subblue);
                            } else {
                                stringBufferR.append(subRed + "\n");
                                stringBufferB.append(subblue + "\n");
                            }
                            childDetail.setNumRed(stringBufferR.toString());
                            childDetail.setNumBlue(stringBufferB.toString());
                        }
                    } else if (lottery.getPlayType().equals("胆拖")) {
                        //截取红球蓝球
                        String subRed = lotteryNum.substring(0, lotteryNum.indexOf("~")); //胆码*拖码
                        String subblue = lotteryNum.substring(lotteryNum.indexOf("~") + 1, lotteryNum.length());
                        childDetail.setNumRed(getDigui(subRed, new StringBuffer(""), 9));
                        childDetail.setNumBlue(getDigui(subblue, new StringBuffer(""), 5));
                    } else if (lottery.getPlayType().equals("复式")) {
                        String subRed = lotteryNum.substring(0, lotteryNum.indexOf("#"));
                        String subblue = lotteryNum.substring(lotteryNum.indexOf("#") + 1, lotteryNum.length());
                        childDetail.setNumRed(getDigui(subRed, new StringBuffer(""), 9));
                        childDetail.setNumBlue(getDigui(subblue, new StringBuffer(""), 5));
                    }
                } else if ("快3".equals(lotteryType)) {
                    if (ConstantValue.gameK3Type_SLHTX.equals(lottery.getPlayType())) {
                        childDetail.setNumRed(ConstantValue.gameK3Type_SLHTX);
                        childDetail.setNumBlue("");
                    } else if (ConstantValue.gameK3Type_STHTX.equals(lottery.getPlayType())) {
                        childDetail.setNumRed(ConstantValue.gameK3Type_STHTX);
                        childDetail.setNumBlue("");
                    } else {
                        int tepy = 0;
                        //截取每一注
                        String[] split = lotteryNum.split("\\|");
                        int length = split.length;
                        StringBuffer stringBufferR = new StringBuffer("");
                        for (String str : split) {
                            tepy = ++tepy;
                            if (tepy == length) {
                                stringBufferR.append(str);
                            } else {
                                stringBufferR.append(str + "\n");
                            }
                            childDetail.setNumRed(stringBufferR.toString());
                            childDetail.setNumBlue("");
                        }
                    }
                } else if ("3D".equals(lotteryType)) {
                    int tepy = 0;
                    //截取每一注
                    String[] split = lotteryNum.split("\\|");
                    int length = split.length;
                    StringBuffer stringBufferR = new StringBuffer("");
                    for (String str : split) {
                        tepy = ++tepy;
                        if (tepy == length) {
                            stringBufferR.append(str);
                        } else {
                            stringBufferR.append(str + "\n");
                        }
                        childDetail.setNumRed(stringBufferR.toString());
                        childDetail.setNumBlue("");
                    }
                } else if ("时时彩".equals(lotteryType)) {
                    int tepy = 0;
                    //截取每一注
                    String[] split = lotteryNum.split("\\|");
                    int length = split.length;
                    StringBuffer stringBufferR = new StringBuffer("");
                    for (String str : split) {
                        tepy = ++tepy;
                        if (tepy == length) {
                            stringBufferR.append(str);
                        } else {
                            stringBufferR.append(str + "\n");
                        }
                        childDetail.setNumRed(stringBufferR.toString());
                        childDetail.setNumBlue("");
                    }

                } else if ("七乐彩".equals(lotteryType)) {
                    int tepy = 0;
                    //截取每一注
                    String[] split = lotteryNum.split("\\|");
                    int length = split.length;
                    StringBuffer stringBufferR = new StringBuffer("");
                    for (String str : split) {
                        tepy = ++tepy;
                        if (tepy == length) {
                            stringBufferR.append(str.replace("#00#", "*"));
                        } else {
                            stringBufferR.append(str.replace("#00#", "*") + "\n");
                        }
                        childDetail.setNumRed(stringBufferR.toString());
                        childDetail.setNumBlue("");
                    }
                }
                patrolGroup.addSubItem(childDetail);
            }
            dataList.add(patrolGroup);
        }
        return dataList;
    }

    private static String getDigui(String strings, StringBuffer stringBuffer, int i) {
        System.out.print("字符串长度: " + strings.length());
        String[] split = strings.split(",");
        if (split.length > i) {
            String substring = strings.substring(0, 3 * i - 1);
            stringBuffer.append(substring + "\n");
            String subNew = strings.substring(3 * i, strings.length());
            getDigui(subNew, stringBuffer, i);
        } else {
            stringBuffer.append(strings);
        }
        return stringBuffer.toString();
    }

}
