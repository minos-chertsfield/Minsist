package com.example.pc.connect_2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static android.content.SharedPreferences.*;

/*
登录模块
 */

public class LoginActivity extends BaseActivity {

    String sf;  //全局变量，获取身份信息
    byte[] dx;
    SharedPreferences preferences = null;

    //----------------------------------------------------------------记住密码------------------------------------------------------
   /* SharedPreferences.Editor editor;*/
    CheckBox rememberpass;


     //----------------------------------------------------------记住密码-----------------------------------------------------------
//-----------------------------------------------------------------------

//-----------------------------------------------------
    private void showInputDialog(final String str_id) {
    /*@setView 装入一个EditView
     */
        final EditText editText = new EditText(LoginActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(LoginActivity.this);
        inputDialog.setTitle("请输入短信验证码").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dx = ("{\"id\":"+"\""+str_id+"\""+",\"code\":"+"\""+ editText.getText().toString() +"\"}").getBytes();
                        try {
                            EquipMessage equip2 = new EquipMessage();
                            URL url = null;
                            try {
                                url = new URL(equip2.curl);

                                System.out.println(url);

                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();   //打开连接
                            try {
                                con.setRequestMethod("POST");
                            } catch (ProtocolException e) {
                                e.printStackTrace();
                            }
                            con.setDoOutput(true);
                            con.setDoInput(true);
                            con.setRequestProperty("Content-Type", "application/x-javascript; charset=" + "UTF-8");
                            con.setRequestProperty("Content-Length", String.valueOf(dx.length));
                            con.setConnectTimeout(5 * 10000);
                            OutputStream outStream = null;
                            try {
                                outStream = con.getOutputStream();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            outStream.write(dx);
                            outStream.flush();
                            System.out.println(con.getResponseCode()); //响应代码 200表示成功

                            InputStream inputStream = con.getInputStream();
                            BufferedReader reader3 = new BufferedReader(new InputStreamReader(inputStream));
                            String string3 =  reader3.readLine();
                            if(string3.equals("-1"))
                            {
                                Toast.makeText(LoginActivity.this, "失败，请重新输入！", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                                //将返回信息储存
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class); //登录成功自动跳转
                                startActivity(intent);
                                Toast.makeText(LoginActivity.this, "欢迎登陆,"+"用户"+new Gson().fromJson(getSharedPreferences("user", MODE_PRIVATE).getString("User", ""), User.class).getId(), Toast.LENGTH_SHORT).show();
                                SharedPreferences preferences = getSharedPreferences("user",MODE_PRIVATE);
                                Editor editor = preferences.edit();    //编辑器
                                editor.putString("User",string3);
                                editor.commit();
                                System.out.println(string3);
                                //-----------------
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Button register = (Button) findViewById(R.id.register);   //注册按钮
        Button quit = (Button) findViewById(R.id.quit);    //退出按钮
        final Button login = (Button) findViewById(R.id.login);  //登录按钮
        final EditText id_login = (EditText) findViewById(R.id.usr);   //用户登录的ID
        final EditText pwd_login = (EditText) findViewById(R.id.pwd);  //用户登录的密码
        //-------------------------------------记住密码-------------------------------------
        rememberpass = (CheckBox) findViewById(R.id.remember_pass);   //获取记住密码复选框
        boolean isRemember = preferences.getBoolean("remeber_pass",false);   //判断
        if(isRemember)
        {
            String usr = preferences.getString("usr","");   //
            String pwd = preferences.getString("pwd","");   //
            id_login.setText(usr);    //填写用户名
            pwd_login.setText(pwd);    //填写密码
            rememberpass.setChecked(true);    //勾选记住密码选项
        }
        //------------------------------------记住密码--------------------------------------


        final EditText id_tlogin = (EditText) findViewById(R.id.tusr);   //教练登录的ID
        final EditText pwd_tlogin = (EditText) findViewById(R.id.tpwd);   //教练登录的ID

        sf = "普通用户";

        /*group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedID) {
                String judgement = null;  //判断身份
                switch(checkedID) {
                    case R.id.customer:
                        judgement = customer.getText().toString();
                            id_login.setVisibility(View.VISIBLE);    //选中转换普通用户登录
                            id_tlogin.setVisibility(View.GONE);
                            pwd_tlogin.setVisibility(View.GONE);
                            pwd_login.setVisibility(View.VISIBLE);
                        break;
                    case R.id.trainer:
                        judgement = trainer.getText().toString();
                            id_tlogin.setVisibility(View.VISIBLE);   //选中转换教练登录
                            id_login.setVisibility(View.GONE);
                            pwd_tlogin.setVisibility(View.VISIBLE);
                            pwd_login.setVisibility(View.GONE);
                        break;
                }
                sf = judgement;
            }
        });*/

        register.setOnClickListener(new View.OnClickListener() {    //注册按钮事件，跳转到注册页面
            @Override
            public void onClick(View view) {
                Intent intent_register = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent_register);
                finish();
            }
        });
        quit.setOnClickListener(new View.OnClickListener() {    //退出按钮事件
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {    //登录按钮事件
            @Override
            public void onClick(View view) {
                String str_id = id_login.getText().toString();    //字符串获取用户登录ID
                String str_pwd = pwd_login.getText().toString();   ///字符串获取用户登录密码
                //-----------------------------------------------------记住密码---------------------------------------
                SharedPreferences.Editor editor = preferences.edit();
                if(rememberpass.isChecked()) {
                    editor.putBoolean("remeber_pass", true);
                    editor.putString("usr", str_id);
                    editor.putString("pwd", str_pwd);
                }
                else
                {
                    editor.clear();
                }
                editor.apply();
                //-----------------------------------------------------记住密码----------------------------------------
                Gson gson = new Gson();
                Mac mac2 = new Mac();
                String json_login = null;
                json_login = ("{\"id\":"+"\""+str_id+"\""+",\"password\":"+"\""+str_pwd+"\""+",\"mac\":"+"\""+mac2.GenerateRandom(preferences)+"\"}"+"\n");
                System.out.println(json_login);
                //请求
                String encoding = "UTF-8";
                try {
                    byte[] data = json_login.getBytes(encoding);

                    EquipMessage equip = new EquipMessage();
                    URL url = null;
                    System.out.println(sf);
                    if(sf.equals("普通用户")){
                         url = new URL(equip.url_2);     //普通用户
                    }
                    else if(sf.equals("教练")){
                         url = new URL(equip.turl_2);    //教练
                    }
                    System.out.println(url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();   //打开连接
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setRequestProperty("Content-Type", "application/x-javascript; charset=" + encoding);
                    conn.setRequestProperty("Content-Length", String.valueOf(data.length));
                    conn.setConnectTimeout(5 * 10000);
                    OutputStream outStream = conn.getOutputStream();
                    outStream.write(data);
                    outStream.flush();
                    System.out.println(conn.getResponseCode()); //响应代码 200表示成功

                    InputStream inputStream = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String string =  reader.readLine();
                    if(string.equals("0"))
                    {
                        Toast.makeText(LoginActivity.this, "失败，用户不存在！", Toast.LENGTH_SHORT).show();
                    }
                    else if(string.equals("1")){
                        Toast.makeText(LoginActivity.this, "登录成功，请等待短信验证", Toast.LENGTH_SHORT).show();
                       /* outStream.write();*/

                        showInputDialog(str_id);       //调用

                    }
                    else if(string.equals("2")) {
                        Toast.makeText(LoginActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
                    }
                    else if(string.equals("7")) {
                        Toast.makeText(LoginActivity.this, "验证码已发送，请查阅短信！", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        InputStream inputS = conn.getInputStream();
                        BufferedReader reader1 = new BufferedReader(new InputStreamReader(inputS));
                        String str = reader1.readLine();
                        User myuser = gson.fromJson(str, User.class);   //传递对象……
                        /**
                        SharedPreferences preferences = getSharedPreferences("user",MODE_PRIVATE);
                        Editor editor = preferences.edit();
                        editor.putString("User",myuser);
                        editor.commit();
                         */
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class); //跳转
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "欢迎登陆,"+"用户"+new Gson().fromJson(getSharedPreferences("user", MODE_PRIVATE).getString("User", ""), User.class).getId(), Toast.LENGTH_SHORT).show();
                        //-----------------------------------------------------------------------------------------??

                    }

                    outStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
      }
}
