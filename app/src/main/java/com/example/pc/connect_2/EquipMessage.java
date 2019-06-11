package com.example.pc.connect_2;

/**
 * Created by PC on 2017/11/8.
 */

/*
连接配置类
 */

public class EquipMessage {

    public String tradelist;
    public String url;
    public String turl;
    public String url_2;
    public String turl_2;
    public String curl;
    public String trade_url;
    public String cancel_url;
    public String ChongQian;

    public EquipMessage(){

        this.url = "http://101.132.130.60:80/Login/servlet/Register";   //用户注册URL
        this.turl = "http://101.132.130.60:80/Login/servlet/TRegister";   //教练注册URL
        this.url_2 = "http://101.132.130.60:80/Login/servlet/Login";   //用户登录URL
        this.turl_2 = "http://101.132.130.60:80/Login/servlet/TLogin";   //教练登录URL
        this.curl = "http://101.132.130.60:80/Login/servlet/CheckText";   //验证
        this.trade_url = "http://101.132.130.60:80/Login/servlet/TradeOrder";  //提交订单
        this.tradelist = "http://101.132.130.60:80/Login/servlet/TradeList";  //yonghuudingdan
        this.cancel_url = "http://101.132.130.60:80/Login/servlet/TradeCancel1";  //
        this.ChongQian =  "http://101.132.130.60:80/Login/servlet/ChongQian";   //充值
    }



}
