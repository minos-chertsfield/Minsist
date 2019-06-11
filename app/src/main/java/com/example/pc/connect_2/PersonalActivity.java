package com.example.pc.connect_2;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
个人信息模块
 */

public class PersonalActivity extends BaseActivity {

    private String[] str;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                Intent intent = new Intent(PersonalActivity.this,HomeActivity.class);
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
        setContentView(R.layout.activity_personal);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);    //标题栏添加返回键


        //--------------------List  View--------------------------------
        str = new String[]{"账号：" + new Gson().fromJson(getSharedPreferences("user", MODE_PRIVATE).getString("User", ""), User.class).getId(),
                "姓名：" + new Gson().fromJson(getSharedPreferences("user", MODE_PRIVATE).getString("User", ""), User.class).getName(),
                "出生日期："+new Date((new Gson().fromJson(getSharedPreferences("user", MODE_PRIVATE).getString("User", ""), User.class).getBirthday())),
                "电话号码："+new Gson().fromJson(getSharedPreferences("user", MODE_PRIVATE).getString("User", ""), User.class).getTelephone(),
                    "身份证号："+new Gson().fromJson(getSharedPreferences("user", MODE_PRIVATE).getString("User", ""), User.class).getIdentity()};
        /*Toast.makeText(PersonalActivity.this,(new Gson().fromJson(getSharedPreferences("user", MODE_PRIVATE).getString("User", ""), User.class).getId()),Toast.LENGTH_SHORT).show();*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PersonalActivity.this, R.layout.support_simple_spinner_dropdown_item, str);
        ListView listView = (ListView) findViewById(R.id.listView_usr);
        listView.setAdapter(adapter);
    }
}
