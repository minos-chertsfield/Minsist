package com.example.pc.connect_2;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


/**
 * Created by PC on 2017/11/9.
 */

public class Mac extends Activity {


    /* public String getLocalMac()throws SocketException
         {
             String address = null;
             // 把当前机器上的访问网络接口的存入 Enumeration集合中
             Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
             while (interfaces.hasMoreElements()) {
                 NetworkInterface netWork = interfaces.nextElement();
                 // 如果存在硬件地址并可以使用给定的当前权限访问，则返回该硬件地址（通常是 MAC）。
                 byte[] by = netWork.getHardwareAddress();
                 if (by == null || by.length == 0) {
                     continue;
                 }
                 StringBuilder builder = new StringBuilder();
                 for (byte b : by) {
                     builder.append(String.format("%02X:", b));
                 }
                 if (builder.length() > 0) {
                     builder.deleteCharAt(builder.length() - 1);
                 }
                 String mac = builder.toString();
                 if (netWork.getName().equals("eth0")) {
                   *//*  System.out.println(mac);*//*
                address = mac;
            }
        }
        return address;
    *//*}*/
    public String GenerateRandom(SharedPreferences preferences) {



        String name = preferences.getString("minsist", "");
        if(name.equals("")) {
            int z = (int)((Math.random() * 9 + 1) * 100000);    //产生随机数
            //实例化SharedPreferences.Editor对象
            SharedPreferences.Editor editor = preferences.edit();

            //用putString的方法保存数据
            editor.putString("minsist", z + "");

            //提交数据
            editor.commit();
            return z+"";
        }
    return name;
    }






    }
