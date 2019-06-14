package zhcw.lottery.znzd.com.selflottery.presenter;

import android.content.Context;

import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import zhcw.lottery.znzd.com.selflottery.base.BasePresenterImpl;
import zhcw.lottery.znzd.com.selflottery.config.Config;
import zhcw.lottery.znzd.com.selflottery.config.ConstantValue;
import zhcw.lottery.znzd.com.selflottery.contact.ShoppingContact;
import zhcw.lottery.znzd.com.selflottery.http.HttpUtils;
import zhcw.lottery.znzd.com.selflottery.http.callback.AppStringCallback;
import zhcw.lottery.znzd.com.selflottery.http.callback.StringDialogCallback;
import zhcw.lottery.znzd.com.selflottery.ready_entity.bean_info.UserInfo;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.ShoppingCart7LC;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.ShoppingCartK3;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.ShoppingCartSD;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.ShoppingCartSSC;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.ShoppingCartSSQ;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.Ticket7LC;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.TicketK3;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.TicketSD;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.TicketSSC;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.TicketSSQ;
import zhcw.lottery.znzd.com.selflottery.ready_entity.send_json.NumberInfo;
import zhcw.lottery.znzd.com.selflottery.ready_entity.send_json.OrderEntity;
import zhcw.lottery.znzd.com.selflottery.ready_entity.send_json.TicketService;
import zhcw.lottery.znzd.com.selflottery.ui.lottery.entity.JiangQiInfo;
import zhcw.lottery.znzd.com.selflottery.util.Debugger;
import zhcw.lottery.znzd.com.selflottery.util.GsonUtil;
import zhcw.lottery.znzd.com.selflottery.util.Logger;
import zhcw.lottery.znzd.com.selflottery.util.PreferenceUtil;


/**
 * Created by xpz on 2018/10/16.
 */

public class ShoppingPresenter extends BasePresenterImpl<ShoppingContact.IShoppingView>
        implements ShoppingContact.IShoppingPresenter {
    private Context context;
    private String type;//区分彩种

    public ShoppingPresenter(Context context, ShoppingContact.IShoppingView  IShoppingView, String type) {
        this.context = context;
        this.type = type;
        attachView(IShoppingView);
    }

    @Override
    public void getAccountRequest(String token) {
        AppStringCallback callback = new AppStringCallback(context) {
            @Override
            public void onSuccess(String json) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(json);
                    String account = jsonObject.getString("account");
                    mView.setAccount(Float.valueOf(account));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMessage(String message) {
            }
        };
        HttpUtils.GetAccount(context, token, callback);
    }

    @Override
    public void sendLotteryJsonRequest() {
        StringDialogCallback callback = new StringDialogCallback(context) {
            @Override
            public void onSuccess(String Json) {
            }

            @Override
            public void onError(Response<String> response) {
                Debugger.handleError(response);
            }

            @Override
            public void onSuccess_Obj(JSONObject object) {
                super.onSuccess_Obj(object);
                String qrcode;
                Integer number = 0;
                Integer money = 0;
                try {
                    qrcode = object.getString("qrcode");
                    Logger.i("qrcode: " + qrcode);
                    switch (type) {
                        case ConstantValue.SSQTYPE:
                            number = ShoppingCartSSQ.getInstance().getLotterynumber();
                            money = ShoppingCartSSQ.getInstance().getLotteryvalue()
                                    * ShoppingCartSSQ.getInstance().getAppnumbers();
                            break;
                        case ConstantValue.K3TYPE:
                            number = ShoppingCartK3.getInstance().getLotterynumber();
                            money = ShoppingCartK3.getInstance().getLotteryvalue()
                                    * ShoppingCartK3.getInstance().getAppnumbers();
                            break;
                        case ConstantValue.SDTYPE:
                            number = ShoppingCartSD.getInstance().getLotterynumber();
                            money = ShoppingCartSD.getInstance().getLotteryvalue() *
                                    ShoppingCartSD.getInstance().getAppnumbers();
                            break;
                        case ConstantValue.T7LCTYPE:
                            number = ShoppingCart7LC.getInstance().getLotterynumber();
                            money = ShoppingCart7LC.getInstance().getLotteryvalue()
                                    * ShoppingCart7LC.getInstance().getAppnumbers();
                            break;
                        case ConstantValue.SSCTYPE:
                            number = ShoppingCartSSC.getInstance().getLotterynumber();
                            money = ShoppingCartSSC.getInstance().getLotteryvalue()
                                    * ShoppingCartSSC.getInstance().getAppnumbers();
                            break;
                    }

                    mView.setSucessLottery(qrcode, number, money);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMessage(String message) {
                mView.setDefaultMsg(message);
            }
        };

        HttpUtils.sendLotteryJson(context, getJosnDate(), callback);
    }

    @Override
    public void getJiangQiRequest(String type) {
        // 请求奖期
        HttpUtils.getJiangQi(context, type, new StringDialogCallback(context) {

            @Override
            public void onSuccess(String json) {
                JiangQiInfo jiangQiInfo = GsonUtil.GsonToBean(json, JiangQiInfo.class);
                List<JiangQiInfo.IssuesBean> issues = jiangQiInfo.getIssues();
                JiangQiInfo.IssuesBean issuesBean = issues.get(0);
                mView.setStatus(issuesBean.getStatus());
            }

            @Override
            public void onMessage(String message) {
                mView.setDefaultMsg(message);
            }
        });
    }


    public String getJosnDate() {
        Logger.i("准备数据");
        switch (type) {
            case ConstantValue.SSQTYPE:
                return toJsonInfoSSQ();
            case ConstantValue.K3TYPE:
                return toJsonInfoK3();
            case ConstantValue.SDTYPE:
                return toJsonInfoSD();
            case ConstantValue.T7LCTYPE:
                return toJsonInfo7LC();
            case ConstantValue.SSCTYPE:
                return toJsonInfoSSC();
        }
        return "data=null";
    }


    /**
     * 封装--k3--json数据
     */
    private String toJsonInfoK3() {
        /*内部数据信息*/
        List<NumberInfo> numberInfos = new ArrayList<>();

        for (TicketK3 ti : ShoppingCartK3.getInstance().getTicketK3()) {
            NumberInfo numberInfo = new NumberInfo();
            //注数
            numberInfo.setLotteryStake(String.valueOf(ti.getNum()));
            //金额
            numberInfo.setLotteryAmount(String.valueOf(ti.getNum() * 2));
            //玩法
            numberInfo.setPlayType(ti.getLotteryid());
            //彩票信息 区分和值 单选
            numberInfo.setLotteryNum(ti.getJosnNum());
            numberInfos.add(numberInfo);
        }

        /*外部数据信息*/
        TicketService ticketService = new TicketService();
        //彩种
        ticketService.setLotteryType(ShoppingCartK3.getInstance().getLotteryid());
        //倍数
        ticketService.setMultiple(ShoppingCartK3.getInstance().getAppnumbers().toString());
        //总注数
        ticketService.setOrderStake(ShoppingCartK3.getInstance().getLotterynumber().toString());
        //总金额
        ticketService.setOrderAmount(String.valueOf(ShoppingCartK3.getInstance().getLotteryvalue() * ShoppingCartK3.getInstance().getAppnumbers()));
        //彩票信息
        ticketService.setLotterys(numberInfos);
        /*最外层的订单封装*/
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setToken(PreferenceUtil.getInstance().getPreferences(Config.TOKEN, ""));
        orderEntity.setOrder(ticketService);

        Logger.i(GsonUtil.GsonString(orderEntity) + "\n" + PreferenceUtil.getInstance().getPreferences(Config.TOKEN, ""));//将上述设置的代码转为json
        return GsonUtil.GsonString(orderEntity);
    }

    /**
     * 封装--SD--json数据
     */
    private String toJsonInfoSD() {
        /*内部数据信息*/
        List<NumberInfo> numberInfos = new ArrayList<>();

        for (TicketSD ti : ShoppingCartSD.getInstance().getTicketSD()) {
            if (ConstantValue.SD.equals(ShoppingCartSD.getInstance().getLotteryid())) {
                NumberInfo numberInfo = new NumberInfo();
                //注数
                numberInfo.setLotteryStake(String.valueOf(ti.getNum()));
                //金额
                numberInfo.setLotteryAmount(String.valueOf(ti.getNum() * 2));
                //玩法
                numberInfo.setPlayType(ti.getLotteryid());
                numberInfo.setLotteryNum(ti.getJsonNum());
                numberInfos.add(numberInfo);
            }
        }

        /*外部数据信息*/
        TicketService ticketService = new TicketService();
        //彩种
        ticketService.setLotteryType(ShoppingCartSD.getInstance().getLotteryid());
        //倍数
        ticketService.setMultiple(ShoppingCartSD.getInstance().getAppnumbers().toString());
        //总注数
        ticketService.setOrderStake(ShoppingCartSD.getInstance().getLotterynumber().toString());
        //总金额
        ticketService.setOrderAmount(String.valueOf(ShoppingCartSD.getInstance().getLotteryvalue() * ShoppingCartSD.getInstance().getAppnumbers()));
        //彩票信息
        ticketService.setLotterys(numberInfos);
        /*最外层的订单封装*/
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setToken(PreferenceUtil.getInstance().getPreferences(Config.TOKEN, "null"));
        orderEntity.setOrder(ticketService);

        Logger.i(GsonUtil.GsonString(orderEntity) + "\n" + PreferenceUtil.getInstance().getPreferences(Config.TOKEN, "null"));//将上述设置的代码转为json
        return GsonUtil.GsonString(orderEntity);
    }

    /**
     * 封装--SSC--json数据
     */
    private String toJsonInfoSSC() {
        /*内部数据信息*/
        List<NumberInfo> numberInfos = new ArrayList<>();

        for (TicketSSC ti : ShoppingCartSSC.getInstance().getTicketSSC()) {
            StringBuffer stringBuffer = new StringBuffer("");
            if (ConstantValue.SSC == ShoppingCartSSC.getInstance().getLotteryid()) {
                NumberInfo numberInfo = new NumberInfo();
                //注数
                numberInfo.setLotteryStake(String.valueOf(ti.getNum()));
                //金额
                numberInfo.setLotteryAmount(String.valueOf(ti.getNum() * 2));
                //玩法
                numberInfo.setPlayType(ti.getLotteryid());
                //彩票信息
                numberInfo.setLotteryNum(ti.getRedNumSeed());
                numberInfos.add(numberInfo);
            }
        }

        /*外部数据信息*/
        TicketService ticketService = new TicketService();
        //彩种
        ticketService.setLotteryType(ShoppingCartSSC.getInstance().getLotteryid());
        //倍数
        ticketService.setMultiple(ShoppingCartSSC.getInstance().getAppnumbers().toString());
        //总注数
        ticketService.setOrderStake(ShoppingCartSSC.getInstance().getLotterynumber().toString());
        //总金额
        ticketService.setOrderAmount(String.valueOf(ShoppingCartSSC.getInstance().getLotteryvalue() * ShoppingCartSSC.getInstance().getAppnumbers()));
        //彩票信息
        ticketService.setLotterys(numberInfos);
        /*最外层的订单封装*/
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setToken(PreferenceUtil.getInstance().getPreferences(Config.TOKEN, "null"));
        orderEntity.setOrder(ticketService);

        Logger.i(GsonUtil.GsonString(orderEntity) + "\n" + PreferenceUtil.getInstance().getPreferences(Config.TOKEN, "null"));//将上述设置的代码转为json
        return GsonUtil.GsonString(orderEntity);
    }

    /**
     * 封装--7乐彩--json数据
     */
    private String toJsonInfo7LC() {
        /*内部数据信息*/
        List<NumberInfo> numberInfos = new ArrayList<>();

        for (Ticket7LC ti : ShoppingCart7LC.getInstance().getTicket7LCS()) {
            StringBuffer stringBuffer = new StringBuffer("");
            if (ConstantValue.T7LC == ShoppingCart7LC.getInstance().getLotteryid()) {
                NumberInfo numberInfo = new NumberInfo();
                //注数
                numberInfo.setLotteryStake(String.valueOf(ti.getNum()));
                //金额
                numberInfo.setLotteryAmount(String.valueOf(ti.getNum() * 2));
                //玩法
                numberInfo.setPlayType(ti.getLotteryid());
                //彩票信息 区分普通投注和胆拖
                if (ti.getLotteryid().equals(ConstantValue.T7LC_DT)) { //挑出胆拖的拖码
                    stringBuffer.append(ti.getRedNumJ()).append("#00#" + ti.getRedNumTuoJ());
                    numberInfo.setLotteryNum(stringBuffer.toString());
                } else {
                    numberInfo.setLotteryNum(ti.getRedNumJ());
                }
                numberInfos.add(numberInfo);
            }
        }

        /*外部数据信息*/
        TicketService ticketService = new TicketService();
        //彩种
        ticketService.setLotteryType(ShoppingCart7LC.getInstance().getLotteryid());
        //倍数
        ticketService.setMultiple(ShoppingCart7LC.getInstance().getAppnumbers().toString());
        //总注数
        ticketService.setOrderStake(ShoppingCart7LC.getInstance().getLotterynumber().toString());
        //总金额
        ticketService.setOrderAmount(String.valueOf(ShoppingCart7LC.getInstance().getLotteryvalue() * ShoppingCart7LC.getInstance().getAppnumbers()));
        //彩票信息
        ticketService.setLotterys(numberInfos);
        /*最外层的订单封装*/
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setToken(UserInfo.getToken());
        orderEntity.setOrder(ticketService);

        Logger.i(GsonUtil.GsonString(orderEntity) + "\n" + PreferenceUtil.getInstance().getPreferences(Config.TOKEN, "null"));//将上述设置的代码转为json
        return GsonUtil.GsonString(orderEntity);
    }

    /**
     * 封装--SSQ--json数据
     */
    private String toJsonInfoSSQ() {
        /*内部数据信息*/
        List<NumberInfo> numberInfos = new ArrayList<>();

        for (TicketSSQ ti : ShoppingCartSSQ.getInstance().getTicketSSQS()) {
            StringBuffer stringBuffer = new StringBuffer("");
            if (ConstantValue.SSQ == ShoppingCartSSQ.getInstance().getLotteryid()) {
                NumberInfo numberInfo = new NumberInfo();
                //注数
                numberInfo.setLotteryStake(String.valueOf(ti.getNum()));
                //金额
                numberInfo.setLotteryAmount(String.valueOf(ti.getNum() * 2));
                //玩法
                numberInfo.setPlayType(ti.getLotteryid());
                //彩票信息 区分普通投注和胆拖
                if (ti.getLotteryid().equals(ConstantValue.SSQ_DT)) { //挑出胆拖的拖码
                    stringBuffer.append(ti.getRedNumJ()).append("*")
                            .append(ti.getRedNumTuoJ()).append("~").append(ti.getBlueNumJ());
                } else {
                    //玩法 区分复试
                    stringBuffer.append(ti.getRedNumJ()).append("#").append(ti.getBlueNumJ());
                }
                numberInfo.setLotteryNum(stringBuffer.toString());
                numberInfos.add(numberInfo);
            }
        }

        /*外部数据信息*/
        TicketService ticketService = new TicketService();
        //彩种
        ticketService.setLotteryType(ShoppingCartSSQ.getInstance().getLotteryid());
        //倍数
        ticketService.setMultiple(ShoppingCartSSQ.getInstance().getAppnumbers().toString());
        //总注数
        ticketService.setOrderStake(ShoppingCartSSQ.getInstance().getLotterynumber().toString());
        //总金额
        ticketService.setOrderAmount(String.valueOf(ShoppingCartSSQ.getInstance().getLotteryvalue() * ShoppingCartSSQ.getInstance().getAppnumbers()));
        //彩票信息
        ticketService.setLotterys(numberInfos);
        /*最外层的订单封装*/
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setToken(PreferenceUtil.getInstance().getPreferences(Config.TOKEN, "null"));
        orderEntity.setOrder(ticketService);

        Logger.i(GsonUtil.GsonString(orderEntity) + "\n" + PreferenceUtil.getInstance().getPreferences(Config.TOKEN, "null"));//将上述设置的代码转为json
        return GsonUtil.GsonString(orderEntity);
    }

}
