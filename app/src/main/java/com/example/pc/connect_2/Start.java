package com.example.pc.connect_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/*
启动动画
 */

public class Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.activity_start, null);    //填充内容
        setContentView(view);

        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);    //透明度从30%~100%渐变
        aa.setDuration(5000);    //动画时间为5秒
        view.startAnimation(aa);    //开始播放动画
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                redirectTo();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }

        });
    }

        private void redirectTo(){    //重定向方法
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }
