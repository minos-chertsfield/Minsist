package com.example.pc.connect_2;


/**
 * Created by Administrator on 2017/12/28 0028.
 */
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;



/**
 * Created by Administrator on 2017/12/28 0028.
 */

/*
充值模块
 */

public class RechargeActivity extends BaseActivity {

    private Double money = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        RadioGroup group = (RadioGroup) findViewById(R.id.RadioGroup);
        RadioButton radioButton = (RadioButton) findViewById(R.id.radioButton);
        RadioButton radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        RadioButton radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        RadioButton radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
        RadioButton radioButton5 = (RadioButton) findViewById(R.id.radioButton5);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                // TODO Auto-generated method stub
                //获取变更后的选中项的ID
                int radioButtonId = arg0.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton)RechargeActivity.this.findViewById(radioButtonId);
                //更新文本内容，以符合选中项
                switch (radioButtonId){
                    case R.id.radioButton:money = 68.0;break;
                    case R.id.radioButton2:money = 128.0;break;
                    case R.id.radioButton3:money = 198.0;break;
                    case R.id.radioButton4:money = 328.0;break;
                    case R.id.radioButton5:money = 648.0;break;
                }
            }
        });

        Button button = (Button)findViewById(R.id.chongqian);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                try {
                    handler.post(new NetActivity(new URL(new EquipMessage().ChongQian)));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public class NetActivity extends Thread{
        URL url;
        public NetActivity(URL url){
            this.url = url;
        }

        public void run(){
            try {
                HttpURLConnection connection = (HttpURLConnection) this.url.openConnection();
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                OutputStream out = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
                Map<String, String> map = new HashMap<String, String>();
                map.put("money", money + "");
                map.put("id", new Gson().fromJson(getSharedPreferences("user", MODE_PRIVATE).getString("user", "111111"), User.class).getId());
                writer.write(new Gson().toJson(map));
                writer.flush();
                writer.close();
                out.close();

                InputStream in = connection.getInputStream();
                BufferedReader bufr = new BufferedReader(new InputStreamReader(in));

                String response = bufr.readLine();
                System.out.println(response);
                bufr.close();
                in.close();

                if (response.equals("1")){
                    Toast.makeText(RechargeActivity.this, "充值成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(RechargeActivity.this, "发生未知错误", Toast.LENGTH_SHORT).show();
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}















