package com.example.pc.connect_2;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/*
订单处理模块
 */

public class TradeActivity extends BaseActivity {

    private String json;
    private String people_num;
    private String hope_time;
    private String hope_cost;
    private String address;
    private String type = "0";   //种类
    private Button v0;
    private Button v1;
    private Button v2;
    private Button v3;
    private Button v4;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                Intent intent = new Intent(TradeActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case RESULT_OK:
                     *//*取得来自B页面的数据，并显示到画面*//*
                            Intent intent = getIntent();
                            String a = intent.getDataString("user");
                    *//*获取Bundle中的数据，注意类型和key*//*
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);

        v0 = (Button) findViewById(R.id.op1);   //获取按钮的选项
        v0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "0";
                v0.setBackgroundColor(Color.argb(255,51,119,187));
                v1.setBackgroundColor(Color.argb(255, 85, 195, 239));
                v2.setBackgroundColor(Color.argb(255, 85, 195, 239));
                v3.setBackgroundColor(Color.argb(255, 85, 195, 239));
                v4.setBackgroundColor(Color.argb(255, 85, 195, 239));
            }
        });
        v1 = (Button) findViewById(R.id.op2);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "1";
                v1.setBackgroundColor(Color.argb(255,51,119,187));
                v0.setBackgroundColor(Color.argb(255, 85, 195, 239));
                v2.setBackgroundColor(Color.argb(255, 85, 195, 239));
                v3.setBackgroundColor(Color.argb(255, 85, 195, 239));
                v4.setBackgroundColor(Color.argb(255, 85, 195, 239));
            }
        });
        v2 = (Button) findViewById(R.id.op3);
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "2";
                v2.setBackgroundColor(Color.argb(255,51,119,187));
                v0.setBackgroundColor(Color.argb(255, 85, 195, 239));
                v1.setBackgroundColor(Color.argb(255, 85, 195, 239));
                v3.setBackgroundColor(Color.argb(255, 85, 195, 239));
                v4.setBackgroundColor(Color.argb(255, 85, 195, 239));
            }
        });
        v3 = (Button) findViewById(R.id.op4);
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "3";
                v3.setBackgroundColor(Color.argb(255,51,119,187));
                v0.setBackgroundColor(Color.argb(255, 85, 195, 239));
                v2.setBackgroundColor(Color.argb(255, 85, 195, 239));
                v1.setBackgroundColor(Color.argb(255, 85, 195, 239));
                v4.setBackgroundColor(Color.argb(255, 85, 195, 239));
            }
        });
        v4 = (Button) findViewById(R.id.op5);
        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "4";
                v4.setBackgroundColor(Color.argb(255,51,119,187));
                v0.setBackgroundColor(Color.argb(255, 85, 195, 239));
                v2.setBackgroundColor(Color.argb(255, 85, 195, 239));
                v3.setBackgroundColor(Color.argb(255, 85, 195, 239));
                v1.setBackgroundColor(Color.argb(255, 85, 195, 239));
            }
        });

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        final Button btn_submit = (Button) findViewById(R.id.submit);
        Button btn_cancel = (Button) findViewById(R.id.cancel);

        final Spinner people = (Spinner) findViewById(R.id.people_num);
        String[] pItems = getResources().getStringArray(R.array.people);
        ArrayAdapter<String> adapter3=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, pItems);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        people.setAdapter(adapter3);
        /*final EditText type = (EditText) findViewById(R.id.trade_type);*/

        final Spinner cost = (Spinner) findViewById(R.id.trade_cost);
        String[] nItems = getResources().getStringArray(R.array.cost);
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, nItems);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cost.setAdapter(adapter2);
        final Spinner hopetime = (Spinner) findViewById(R.id.trade_hopetime);    //期望时间选择
        String[] mItems = getResources().getStringArray(R.array.time);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hopetime.setAdapter(adapter);

        Intent intent25 = getIntent();
        address = intent25.getStringExtra("address");
        final EditText hopeplace = (EditText) findViewById(R.id.trade_hopeplace);
        hopeplace.setText(address);   //将获取值填入
        /*final EditText num = (EditText) findViewById(R.id.trade_num);*/


                people.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {   //人数
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String[] mpeople = getResources().getStringArray(R.array.people);
                        people_num = mpeople[i];   //获取
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                hopetime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {   //期望时间
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String[] time= getResources().getStringArray(R.array.time);
                        hope_time = time[i];   //获取时间
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

        cost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {    //价格
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] cost= getResources().getStringArray(R.array.cost);
                hope_cost = cost[i];   //获取价格
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        hopeplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent up = getIntent();
                String uid = up.getStringExtra("user");
                Intent choser = new Intent(TradeActivity.this, MapActivity.class);    //开地图
                choser.putExtra("user",uid);
                startActivity(choser);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                String strtype = type;   //获取类型
                String strcost = hope_cost;

                String strhopetime = hope_time;
                String[] my =strhopetime.split(":");
                int hour =Integer.parseInt(my[0]);
                int min =Integer.parseInt(my[1]);
                //------------------------------
               /* Calendar calendar = Calendar.getInstance();
                String date = calendar.toString();
                System.out.println(date);*/
                //-----------------------------
                long totalSec =hour*3600000+min*60000;
                //处理期望时间为long类型
                String strhopeplace = hopeplace.getText().toString();   //获取地址信息
               /* final String strnum = num.getText().toString();*/

                String encoding = "UTF-8";
                Gson gson = new Gson();
                Trade trade = null;

                Intent intent21 = getIntent(); //获取
                String str_userid = intent21.getStringExtra("user");

                if(hopeplace.getText().equals(""))
                {
                    Toast.makeText(TradeActivity.this,"内容不能为空！",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    try {
                        trade = new Trade(Integer.parseInt(strtype), "0", (new Gson().fromJson(getSharedPreferences("user", MODE_PRIVATE).getString("User", ""), User.class).getId()), "0", Integer.parseInt(people_num), totalSec, new String(strhopeplace.getBytes(), "utf-8"), Double.parseDouble(strcost), 1);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String json = gson.toJson(trade);
                    System.out.println(json);
                    try {
                        byte[] data = json.getBytes(encoding);

                        EquipMessage equip = new EquipMessage();
                        URL url = new URL(equip.trade_url);
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
                        String string = reader.readLine();
                        System.out.println(string);
                        Map<String, Double> map = gson.fromJson(string, new TypeToken<Map<String, Double>>() {}.getType());
                        reader.close();
                        inputStream.close();
                        if(string.equals("0"))
                        {
                            AlertDialog.Builder builder =  new AlertDialog.Builder(view.getContext());
                            builder.setTitle("警告");
                            builder.setMessage("您有订单未完成");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            });
                            builder.show();
                        }
                        else if(string.equals("1"))
                        {
                            Toast.makeText(TradeActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TradeActivity.this,AllOrdersActivity.class);
                            startActivity(intent);
                        }
                        else if(map.get("money")>0)
                        {
                            AlertDialog.Builder builder =  new AlertDialog.Builder(view.getContext());
                            builder.setTitle("警告");
                            builder.setMessage("余额不足，请充值");
                            builder.setPositiveButton("充值", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(TradeActivity.this, RechargeActivity.class);
                                    startActivity(intent);
                                }
                            });
                            builder.show();
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });



        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        Button button_check = (Button) findViewById(R.id.check);    //查看历史订单按钮
        button_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TradeActivity.this, AllOrdersActivity.class);
                startActivity(intent);
            }
        });
    }
}
