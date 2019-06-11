package com.example.pc.connect_2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SetPlanActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferencesUtils sp;
    private SharedPreferences spp;
    private EditText tv_step_number;
    private Button btn_save;
    private String walk_qty;

    private void assignViews() {
        tv_step_number = (EditText) findViewById(R.id.tv_step_number);
        btn_save = (Button) findViewById(R.id.btn_save);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jibu);
        assignViews();
        initData();
        addListener();
    }

    public void initData() {//获取锻炼计划
        sp = new SharedPreferencesUtils(this);
        String planWalk_QTY = (String) sp.getParam("planWalk_QTY", "5000");
        if (!planWalk_QTY.isEmpty()) {
            if ("0".equals(planWalk_QTY)) {
                tv_step_number.setText("5000");
            } else {
                tv_step_number.setText(planWalk_QTY);
            }
        }
    }


    public void addListener() {

        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

                save();

    }

    private void save() {
        walk_qty = tv_step_number.getText().toString().trim();
        if (walk_qty.isEmpty() || "0".equals(walk_qty)) {
            sp.setParam("planWalk_QTY", "5000");
        } else {
            sp.setParam("planWalk_QTY", walk_qty);
        }
        finish();
    }
}

