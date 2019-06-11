package com.example.pc.connect_2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/*
注册模块
 */

public class MainActivity extends BaseActivity {
    SharedPreferences preferences = null;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);    //标题栏上设置返回键

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Button button_1 = (Button) findViewById(R.id.btn_1);    //注册按钮
        Button button_2 = (Button) findViewById(R.id.btn_2);    //返回按钮

        final EditText edit_id = (EditText) findViewById(R.id.edit_1);   //用户id
        final EditText edit_pwd = (EditText) findViewById(R.id.edit_2);   //用户密码
        final EditText edit_name = (EditText) findViewById(R.id.edit_3);   //用户姓名
        final EditText edit_tel = (EditText) findViewById(R.id.edit_4);   //用户电话
        final EditText edit_birth = (EditText) findViewById(R.id.edit_5);   //用户出生日期
        final EditText edit_identity = (EditText) findViewById(R.id.edit_6);   //用户身份证号

        edit_id.setOnFocusChangeListener(new View.OnFocusChangeListener() { //账号焦点改变
            @Override
            public void onFocusChange(View view, boolean b) {
                if(hasWindowFocus() && edit_id.length() == 0){
                    edit_id.setError("账号不能为空");
                }
            }
        });

        edit_pwd.setOnFocusChangeListener(new View.OnFocusChangeListener() { //密码焦点改变
            @Override
            public void onFocusChange(View view, boolean b) {
                if(hasWindowFocus() && edit_id.length() == 0){
                    edit_id.setError("账号不能为空");
                }
                else if(hasWindowFocus()&&edit_id.length()<6)
                {
                    edit_id.setError("账号不得小于6位");
                }
            }
        });

        edit_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {   //姓名焦点改变
            @Override
            public void onFocusChange(View view, boolean b) {
                if(hasWindowFocus() && edit_pwd.length() == 0){
                    edit_pwd.setError("密码不能为空");
                }
                else if(hasWindowFocus()&&edit_pwd.length()<6)
                {
                    edit_pwd.setError("密码不得小于6位");
                }
            }
        });

        edit_tel.setOnFocusChangeListener(new View.OnFocusChangeListener() {   //电话焦点改变
            @Override
            public void onFocusChange(View view, boolean b) {
                if(hasWindowFocus() && edit_name.length() == 0){
                    edit_name.setError("姓名不能为空");
                }
                else if(hasWindowFocus()&&edit_name.length()<2)
                {
                    edit_name.setError("姓名不得小于2位");
                }
            }
        });

        edit_identity.setOnFocusChangeListener(new View.OnFocusChangeListener() {  //身份证号焦点改变
            @Override
            public void onFocusChange(View view, boolean b) {
                if(hasWindowFocus() && edit_tel.length() == 0){
                    edit_tel.setError("电话号码不能为空");
                }
                else if(hasWindowFocus()&&edit_tel.length()!=11)
                {
                    edit_tel.setError("电话号码应为11位");
                }
                if(hasWindowFocus() && edit_birth.length() == 0){
                    edit_birth.setError("出生日期不能为空");
                }
                /*if(hasWindowFocus() && edit_identity.length() == 0){
                    edit_identity.setError("身份证号不能为空");
                }
                else if(hasWindowFocus()&&edit_identity.length()!=18)
                {
                    edit_identity.setError("身份证号应为18位");
                }*/
            }
        });





        button_1.setOnClickListener(new View.OnClickListener() {      //注册按钮事件
            @Override
            public void onClick(View view) {

                TranslateAnimation animation = new TranslateAnimation(5, -5, 0, 0);   //抖动动画
                animation.setInterpolator(new OvershootInterpolator());
                animation.setDuration(100);
                animation.setRepeatCount(10);
                animation.setRepeatMode(Animation.REVERSE);

                String id = edit_id.getText().toString();   //字符串获取用户ID
                String Regx1 = "^[a-zA-Z0-9]{6,12}$";     //正则表达式1
                Pattern pattern1 = Pattern.compile(Regx1);
                Matcher matcher1 = pattern1.matcher(id);
                boolean b1 = matcher1.matches();
                if(!b1)
                {
                    edit_id.setText("");    //清空
                    edit_id.startAnimation(animation);     //输入框抖动提示
                    edit_id.setError("用户名输入不合法！");   //报错
                }

                String pwd = edit_pwd.getText().toString();  //字符串获取用户密码
                String Regx2 = "^[a-zA-Z0-9]{6,12}$";      //正则表达式2
                Pattern pattern2 = Pattern.compile(Regx2);
                Matcher matcher2 = pattern2.matcher(pwd);
                boolean b2 = matcher2.matches();
                if(!b2)
                {
                    edit_pwd.setText("");    //清空
                    edit_pwd.startAnimation(animation);    //抖动动画
                    edit_pwd.setError("密码输入不合法！");
                }

                String name = edit_name.getText().toString();  //字符串获取用户姓名
                String Regx3 =  "^[\u4e00-\u9fa5]{0,}$";      //正则表达式3
                Pattern pattern3 = Pattern.compile(Regx3);
                Matcher matcher3 = pattern3.matcher(name);
                boolean b3 = matcher3.matches();
                if(!b3)
                {
                    edit_name.setText("");    //清空
                    edit_name.startAnimation(animation);    //抖动动画
                    edit_name.setError("姓名输入不合法！");   //报错
                }

                String tel = edit_tel.getText().toString();    //字符串获取用户电话
                String Regx4 = "1(3[4-9]|4[7]|5[012789]|8[278])\\d{8}";        //正则表达式4
                Pattern pattern4 = Pattern.compile(Regx4);
                Matcher matcher4 = pattern4.matcher(tel);
                boolean b4 = matcher4.matches();
                if(!b4)
                {
                    edit_tel.setText("");   //清空
                    edit_tel.startAnimation(animation);     //抖动动画
                    edit_tel.setError( "手机号码输入不合法！");   //报错
                }

                String birth = edit_birth.getText().toString();   //字符串获取用户出生日期
              /*  String Regx5 = "";        //正则表达式4
                Pattern pattern5 = Pattern.compile(Regx5);
                Matcher matcher5 = pattern5.matcher(birth);
                boolean b5 = matcher5.matches();
                if(!b5)
                {
                    Toast.makeText(MainActivity.this, "出生日期输入不规范" ,Toast.LENGTH_SHORT).show();
                }
*/

                SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
                Date date_time = null;
                try {
                    date_time = sdf.parse(birth);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long long_birth = date_time.getTime() / 1000;

                String identity = edit_identity.getText().toString();   //字符串获取身份证号
                String Regx6 = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";        //正则表达式6
                Pattern pattern6 = Pattern.compile(Regx6);
                Matcher matcher6 = pattern6.matcher(identity);
                boolean b6 = matcher6.matches();
                if(!b6)
                {
                    edit_identity.setText("");   //清空
                    edit_identity.startAnimation(animation);    //抖动动画
                    edit_identity.setError("身份证号输入不合法！");
                    /*Drawable d = getResources().getDrawable(R.drawable.error);
                    d.setBounds(0, 0, 72, 72);
                    edit_identity.setError("身份证号输入不规范",d);*/
                }
                //对于错误进行汇总
                if(!(b1&&b2&&b3&&b4&&b6))
                {
                    Toast.makeText(MainActivity.this, "输入不规范", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Mac mac = new Mac();   //硬件地址对象
                    Gson gson = new Gson();
                    User user = null;
                    user = new User(id, pwd, name, tel, long_birth, identity, 0.0, mac.GenerateRandom(preferences));
                    String json = gson.toJson(user);
                    System.out.println(json);

                    String encoding = "UTF-8";
                    try {
                        byte[] data = json.getBytes(encoding);

                        EquipMessage equip = new EquipMessage();
                        URL url = new URL(equip.url);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setDoOutput(true);
                        conn.setDoInput(true);
                        conn.setRequestProperty("Content-Type", "application/x-javascript; charset=" + encoding);
                        conn.setRequestProperty("Content-Length", String.valueOf(data.length));
                        conn.setConnectTimeout(5 * 1000);
                        OutputStream outStream = conn.getOutputStream();
                        outStream.write(data);
                        outStream.flush();
                        outStream.close();
                        System.out.println(conn.getResponseCode()); //响应代码 200表示成功

                        InputStream inputStream = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        String string =  reader.readLine();
                        if(string.equals("0"))
                        {
                            Toast.makeText(MainActivity.this, "失败,该用户已存在", Toast.LENGTH_SHORT).show();  //返回值为0，注册失败
                        }
                        else{
                            Toast.makeText(MainActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();   //返回值为1，显示注册成功
                            Intent intent_back = new Intent(MainActivity.this, LoginActivity.class);   //页面跳转
                            startActivity(intent_back);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                }


        });

        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Calendar cal;         //日历对象
        final int year,month,day;

        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);       //获取年月日时分秒
        month=cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day=cal.get(Calendar.DAY_OF_MONTH);

        edit_birth.setOnClickListener(new View.OnClickListener() {    //出生日期选择
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.edit_5:
                        DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker arg0, int year, int month, int day) {
                                edit_birth.setText(year+"-"+(++month)+"-"+day);      //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                            }
                        };
                        DatePickerDialog dialog=new DatePickerDialog(MainActivity.this, 0,listener,year,month,day);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                        dialog.show();
                        break;

                    default:
                        break;
                }
            }
        });
    }

}
