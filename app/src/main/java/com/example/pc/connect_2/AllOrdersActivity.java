package com.example.pc.connect_2;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllOrdersActivity extends AppCompatActivity {
    /*
    显示用户的订单列表
     */
    ListView listView;    //创建ListView的对象
    ArrayAdapter adapter;    //创建数组适配器的对象
    Trade t;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:         //点击返回键出发的事件
                Intent intent = new Intent(AllOrdersActivity.this, TradeActivity.class);    //跳转上一个活动
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
        setContentView(R.layout.activity_all_orders);

        ActionBar actionBar = this.getSupportActionBar();    //获取标题栏
        actionBar.setDisplayHomeAsUpEnabled(true);      //显示后退图标
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Button qx = (Button) findViewById(R.id.quxiao);
        qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EquipMessage equip = new EquipMessage();   //
                try {
                    if(t != null)
                    {
                        URL url = new URL(equip.cancel_url);
                        String tradeid = t.getId();    //获取订单号
                        Map<String, String> tradeID = new HashMap<String, String>();
                        tradeID.put("id",tradeid);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setDoOutput(true);
                        conn.setDoInput(true);
                        conn.setConnectTimeout(5 * 1000);
                        OutputStream outStream = conn.getOutputStream();
                        outStream.write(new Gson().toJson(tradeID).getBytes("UTF-8"));
                        outStream.flush();
                        outStream.close();

                        InputStream inputStream = conn.getInputStream();
                        BufferedReader reader3 = new BufferedReader(new InputStreamReader(inputStream));
                        String string3 =  reader3.readLine();
                        if (string3.equals("1")){
                            Toast.makeText(AllOrdersActivity.this, "取消订单成功", Toast.LENGTH_SHORT).show();
                        }else if (string3.equals("2")){
                            Toast.makeText(AllOrdersActivity.this, "等待教练确认", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(AllOrdersActivity.this, "发生未知错误", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(AllOrdersActivity.this, "暂无可取消订单", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        String encoding = "UTF-8";
        try {
            EquipMessage equip = new EquipMessage();
            URL url = new URL(equip.tradelist);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(5 * 1000);
            OutputStream outStream = conn.getOutputStream();
            Map<String, String> map = new HashMap<String, String>();
            map.put("userid", new Gson().fromJson(getSharedPreferences("user", MODE_PRIVATE).getString("User", ""), User.class).getId());
            System.out.println(getSharedPreferences("user", MODE_PRIVATE).getString("User", "1111111111"));
            outStream.write(new Gson().toJson(map).getBytes(encoding));
            outStream.flush();
            outStream.close();
            System.out.println(conn.getResponseCode()); //响应代码 200表示成功

            InputStream inputStream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            final List<Trade> trades = new Gson().fromJson(reader.readLine(),  new TypeToken<List<Trade>>() {}.getType());

            for (Trade trade : trades){
                if (trade.getState() == 0 || trade.getState() == 1){
                    t = trade;
                }
            }

            TradeAdapter adapter = new TradeAdapter(AllOrdersActivity.this, R.layout.tradeitem, trades);

            listView = (ListView)findViewById(R.id.listviewtrade);
            listView.setAdapter(adapter);



        } catch (Exception e) {
            e.printStackTrace();
            //新建数组

//            String[] mylistdata = {"a", "b", "c"};
////注意：一定要将String[]转成List，否则不能动态增加和删除
//
//            List list = new ArrayList<>(Arrays.asList(mylistdata));     //集合类对象
////新增加adapter适配器
//            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
////创建ListView
//            listView = new ListView(this);    //实例化ListView
//
//            listView.setAdapter(adapter);        //为当前创建的ListView设置适配器
//
//            setContentView(listView);           //设置内容布局
//
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {       ///设置ListView的点击事件
//                @Override
//                public void onItemClick(AdapterView parent, View view, int position, long id) {      //被点击条目
//                    String item = (String) adapter.getItem(position);     //获取条目位置
//               /* adapter.remove(item);      //移除被点中的项
//                adapter.add(item+" city");    //增添新项+"city"*/
//                    p = item;
//                    Toast.makeText(AllOrdersActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(AllOrdersActivity.this, OrderInfoActivity.class);
//                    startActivity(intent);
//                }
//            });
        }




    }


    public class TradeAdapter extends ArrayAdapter<Trade> {

        private int resourceId;

        /**
         *context:当前活动上下文
         *textViewResourceId:ListView子项布局的ID
         *objects：要适配的数据
         */
        public TradeAdapter(Context context, int textViewResourceId,
                            List<Trade> objects) {
            super(context, textViewResourceId, objects);
            //拿取到子项布局ID
            resourceId = textViewResourceId;
        }

        /**
         * LIstView中每一个子项被滚动到屏幕的时候调用
         * position：滚到屏幕中的子项位置，可以通过这个位置拿到子项实例
         * convertView：之前加载好的布局进行缓存
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Trade trade = getItem(position);  //获取当前项的Trade实例
            System.out.println("position is " + position);
            System.out.println(trade.getUserid());
            //为子项动态加载布局
            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            TextView userid = (TextView) view.findViewById(R.id.userid);
            TextView type = (TextView) view.findViewById(R.id.type);
            TextView num = (TextView) view.findViewById(R.id.num);
            TextView hopetime = (TextView) view.findViewById(R.id.hopetime);
            TextView hopeplace = (TextView) view.findViewById(R.id.hopeplace);
            TextView cost = (TextView) view.findViewById(R.id.cost);

            switch (trade.getState()) {
                case 0:
                    userid.setText("待接单");
                    userid.setTextColor(Color.RED);
                    break;
                case 1:
                    userid.setText("待服务");
                    userid.setTextColor(Color.RED);
                    break;
                case 2:
                    userid.setText("服务完成");
                    break;
                case 3:
                    userid.setText("服务完成");
                    break;
                case 4:
                    userid.setText("待教练确认");
                    break;
                default:
                    userid.setText("订单取消");
                    break;
            }
            switch (trade.getType()) {
                case 0:
                    type.setText("慢跑");
                    break;
                case 1:
                    type.setText("室外有氧");
                    break;
                case 2:
                    type.setText("专业器械");
                    break;
                case 3:
                    type.setText("专业有氧");
                    break;
                case 4:
                    type.setText("定制塑形");
                    break;
                default:
                    type.setText("NULL");
                    break;
            }
            num.setText(trade.getNum() + "");
            hopetime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(trade.getHopetime())));
            hopeplace.setText(trade.getHopeplace());
            cost.setText(trade.getCost() + "");
            return view;
        }



    }
}
