package com.example.pc.connect_2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by PC on 2018/2/3.音效类
 */

public class SoundPlay extends Activity{
    private SoundPool soundPool;
    private int soundID;    //音频的id

    @SuppressLint("NewApi")
    private void initSound() {
        soundPool = new SoundPool.Builder().build();
        soundID = soundPool.load(this, R.raw.ac, 1);   //声音素材
    }

    public void playSound() {
        soundPool.play(
                soundID,
                0.1f,   //左耳道音量【0~1】
                0.5f,   //右耳道音量【0~1】
                0,     //播放优先级【0表示最低优先级】
                1,     //循环模式【0表示循环一次，-1表示一直循环，其他表示数字+1表示当前数字对应的循环次数】
                1     //播放速度【1是正常，范围从0~2】
        );
    }
}
