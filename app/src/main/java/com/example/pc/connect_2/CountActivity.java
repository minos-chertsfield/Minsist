package com.example.pc.connect_2;

/**
 * Created by PC on 2018/2/8.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class CountActivity extends BaseActivity implements SensorEventListener {
    private ViewStep fsp;
    private Button btn;
    private String walk_qty;
    private String historyvalue;
    private Context context;
    private static  CountActivity countActivity;
    //当前日期
    Calendar c = Calendar.getInstance();
    Calendar c0 = Calendar.getInstance();
    int day = c.get(Calendar.DAY_OF_MONTH);
    private SharedPreferences preferences;
    private SharedPreferences history;
    private String temphistory;
    private String dayvalue;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                Intent intent = new Intent(CountActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public CountActivity()
    {
        countActivity=this;
    }
    public static CountActivity getCountActivity() {
        return countActivity;
    }

    TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        btn=(Button) findViewById(R.id.button);
        btn.setOnClickListener(onClick);
        fsp=(ViewStep) findViewById(R.id.view);
        textView =(TextView) findViewById(R.id.textView);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);// TYPE_GRAVITY
        if (null == mSensorManager) {
            Log.d(TAG, "deveice not support SensorManager");
        }
        // 参数三，检测的精准度
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);// SENSOR_DELAY_GAME

        preferences=getSharedPreferences("arrays",Context.MODE_PRIVATE);
        history=getSharedPreferences("record",Context.MODE_PRIVATE);
        //判断是否今日，是否清空step0
        dayinit();
        //historyinit();
        gethistory();
//        textView.setText(String.valueOf(preferences.getInt("currentstep",step)));

        if(step==0)
        {}
        else
        {
            begin();
        }
    }
    public void begin(){
        ViewStep sv = (ViewStep) findViewById(R.id.view2);
        // sv.setCurrentCount(5000, 1000);
        String planWalk_QTY =  preferences.getString("planWalk_QTY", "5000");
        //sv.setCurrentCount(Integer.parseInt(planWalk_QTY),step);
        sv.setCurrentCount(Integer.parseInt(planWalk_QTY),step);
    }
    private View.OnClickListener onClick=new View.OnClickListener()
    {

        @Override
        public void onClick(View v) {

            showNormalDialog();
        }
    };
    private void showNormalDialog(){
        final EditText editText = new EditText(CountActivity.this);
        final AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(CountActivity.this);
        inputDialog.setTitle("请输入").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences.Editor editor=preferences.edit();
                        walk_qty = editText.getText().toString().trim();
                        if (walk_qty.isEmpty() || "0".equals(walk_qty)) {
                            editor.putString("planWalk_QTY", "5000");
                        } else {
                            editor.putString("planWalk_QTY",walk_qty);
                        }
                        editor.commit();
                        getedit();

                        Toast.makeText(CountActivity.this,
                                "已设置步数："+editText.getText().toString()+"步",
                                Toast.LENGTH_SHORT).show();
                        if(step==0)
                        {}
                        else
                        {
                            begin();
                        }


                    }
                }).show();

    }

    private void dayinit()
    {
        //SharedPreferences daycheck=getSharedPreferences("arrays",Context.MODE_PRIVATE);
        //如果取出值为0或者和今日日期不同,将步数插入历史记录，清空记录步数，以及textview内容
        //dayche用以记录日期，默认值是0，和今日日期不同则更新
        if (preferences.getInt("dayche",0)==0||(preferences.getInt("dayche",0)!=day))
        {

            //按照 日期-步数 存储历史记录
            SharedPreferences.Editor editor1=history.edit();

            dayvalue=String.valueOf(preferences.getInt("dayche",day));

            editor1.putInt(dayvalue,preferences.getInt("currentstep",step));
            editor1.commit();

            SharedPreferences.Editor editor0=preferences.edit();
            editor0.putInt("dayche",day);

            editor0.commit();
            step=0;
            textView.setText(String.valueOf(step));

        }
        else
        {
            step=preferences.getInt("currentstep",step);
            textView.setText(String.valueOf(preferences.getInt("currentstep",step)));
        }


    }


    private void historyinit()
    {
        String daystringinit;

//        for(int j=1;j<32;j++){
//            daystringinit=String.valueOf(j);
//            SharedPreferences.Editor editor4=history.edit();
//            editor4.putInt(daystringinit,j);
//            editor4.commit();
//        }
//        SharedPreferences.Editor editor4=history.edit();
//            editor4.clear();
//            editor4.commit();
    }

    public int[] gethistory() {

        String daystringnow;
        int[] arr=new int[7];



        int distance;
        int day0;
        for (int i = 0; i < 6; i++) {

            distance=6-i;
            c0.set(Calendar.DATE,c.get(Calendar.DATE));
            c0.set(Calendar.DATE, c0.get(Calendar.DATE) - distance);
            //c.add(Calendar.DAY_OF_MONTH,-i); // -1 前一天
            day0=c0.get(Calendar.DAY_OF_MONTH);

            daystringnow=String.valueOf(day0-6+i);
            arr[i]=history.getInt(daystringnow,0);

        }
        //daystringnow=String.valueOf(day);
        arr[6]=step;

        return arr;
    }


    public String getedit() {
        //获取设置的步数
        // SharedPreferences read=getSharedPreferences("arrays",Context.MODE_PRIVATE);
        // String number=read.getString("planWalk_QTY","");
        return preferences.getString("planWalk_QTY","5000");
        //  tv_step_number.setText(number);
    }

//计步

    //将步数放在sp中
    public void stepjilu(int shuju)
    {
        //SharedPreferences steprecord=getSharedPreferences("arrays",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3=preferences.edit();
        editor3.putInt("currentstep",shuju);
        editor3.commit();

    }




    public String getstep()
    {
        return  textView.getText().toString();
    }


    //-------------------------------------------------------------------------------------------
//---jibu--------------------------------------------------------------------
//-------------------------------------------------------------------------------------------
    private static final String TAG = MainActivity.class.getSimpleName();
    private SensorManager mSensorManager;
    private Sensor mSensor;


    private int upCount = 0;
    private boolean ifup = true;

//    private int downCount = 0;
//    private boolean ifdown = false;

    private int step = 0;

    private long timeOfLastPeak = System.currentTimeMillis();
    private long timeOfThisPeak;
    private long timeOfNow;

    private Double oldG = 0.0;

    private double[] list = new double[5];
    private int listSize = 0;
    private double yuzhi = 2.0;

    private boolean lastStatus;
    private int continueUpFormerCount;
    private double peakOfWave;
    private double valleyOfWave;
    private double initialValue = 1.7;
    private int valueNum = 5;


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == null) {
            return;
        }

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            int x = (int) event.values[0];
            int y = (int) event.values[1];
            int z = (int) event.values[2];
            Double bo = (Double) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)
                    + Math.pow(z, 2));
            stepDetector(bo);
        }
    }

    private void stepDetector(double bo){
        if (oldG == 0) {
            oldG = bo;
        } else {
            if (ifPeak(bo, oldG)) {
                timeOfLastPeak = timeOfThisPeak;
                timeOfNow = System.currentTimeMillis();
                if (timeOfNow - timeOfLastPeak >= 200
                        && (peakOfWave - valleyOfWave >= yuzhi) && timeOfNow - timeOfLastPeak <= 2000) {
                    timeOfThisPeak = timeOfNow;
                    step++;
                    stepjilu(step);
                    begin();
                    textView.setText(String.valueOf(step));
                }
                if (timeOfNow - timeOfLastPeak >= 200
                        && (peakOfWave - valleyOfWave >= initialValue)) {
                    timeOfThisPeak = timeOfNow;
                    yuzhi = Peak_Valley_Thread(peakOfWave - valleyOfWave);
                }
            }
        }
        oldG = bo;

//        boView.setText(String.valueOf(Math.ceil(bo)));
    }
    ////1波峰 2波谷 3其他
    public boolean ifPeak(double newValue, double oldValue) {
        lastStatus = ifup;
        if (newValue >= oldValue) {
            ifup = true;
            upCount++;
        } else {
            continueUpFormerCount = upCount;
            upCount = 0;
            ifup = false;
        }

        if (!ifup && lastStatus
                && (continueUpFormerCount >= 2 && (oldValue >= 11.76 && oldValue < 19.6))) {
            peakOfWave = oldValue;
            return true;
        } else if (!lastStatus && ifup) {
            valleyOfWave = oldValue;
            return false;
        } else {
            return false;
        }
    }

    public double Peak_Valley_Thread(double value) {
        double tempThread = yuzhi;
        if (listSize < valueNum) {
            list[listSize] = value;
            listSize++;
        } else {
            tempThread = averageValue(list, valueNum);
            for (int i = 1; i < valueNum; i++) {
                list[i - 1] = list[i];
            }
            list[listSize - 1] = value;
        }
        return tempThread;
    }

    public double averageValue(double value[], int n) {
        double ave = 0;
        for (int i = 0; i < n; i++) {
            ave += value[i];
        }
        ave = ave / valueNum;
        if (ave >= 8) {
            Log.v(TAG, "超过8");
            ave = (double) 3.3;
        } else if (ave >= 7 && ave < 8) {
            Log.v(TAG, "7-8");
            ave = (double) 2.3;
        } else if (ave >= 4 && ave < 7) {
            Log.v(TAG, "4-7");
            ave = (double) 2.3;
        } else if (ave >= 3 && ave < 4) {
            Log.v(TAG, "3-4");
            ave = (double) 2.0;
        } else {
            Log.v(TAG, "else");
            ave = (double) 1.7;
        }
        return ave;
    }
}
