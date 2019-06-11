package com.example.pc.connect_2;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    int lastSelectedPosition = 0;
    private String TAG = HomeActivity.class.getSimpleName();
   /* private FindFragment mFindFragment;
    private FavoritesFragment mFavoritesFragment;
    private BookFragment mBookFragment;*/

    // -----------------------------------------------------------

    private void showNormalDialog(){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(HomeActivity.this);
        normalDialog.setTitle("警告");
        normalDialog.setMessage("您真的要退出吗?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityController.finishAll();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        // 显示
        normalDialog.show();
    }
    //
    private View view1, view2, view3;
    private ViewPager mViewPager;    //总的ViewPager
    List<View> viewList;    //view的集合
   /* private int index;
    private Timer timer;
    private TimerTask task;*/
    private String zh;   //账号
    private String xm;    //姓名
    private String csrq;   //出生日期
    private String sfzh;   //身份证号
    private String dhhm;    //电话号码
//-------------------------------------------------------------------
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button butt = (Button) findViewById(R.id.food);
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, VideoActivity.class);
                startActivity(intent);
            }
        });

        Button buta = (Button) findViewById(R.id.zengji);
        buta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CountActivity.class);
                startActivity(intent);
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* Intent intent21 = getIntent(); //获取
        String str_userid = intent21.getStringExtra("userid");  //提取用户名
        uid = str_userid;
*/


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent countIntent = new Intent(HomeActivity.this, CountActivity.class);
                startActivity(countIntent);
            }
        });*/
        //---------------------------------------------

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        LayoutInflater mInflater = getLayoutInflater().from(this);

        view1 = mInflater.inflate(R.layout.layout1, null);
        view2 = mInflater.inflate(R.layout.layout2, null);
        view3 = mInflater.inflate(R.layout.layout3, null);

        //添加页面数据
        viewList = new ArrayList<View>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        //实例化适配器

        PagerAdapter mPagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                container.addView(viewList.get(position));
              /*  index = position;*/
                return viewList.get(position);
            }
        };
        mViewPager.setAdapter(mPagerAdapter);



       /* timer = new Timer();
        timer.schedule(task,2000);
        final Handler handler = new Handler(){
            @Override

            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                index++;
                mViewPager.setCurrentItem(index % 3);
            }
        };*/



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);  //建立导航视图对象
        View headerView = navigationView.getHeaderView(0);
        TextView txt1 = (TextView) headerView.findViewById(R.id.id_nav);
        TextView txt2 = (TextView) headerView.findViewById(R.id.cost_nav);
        txt1.append((new Gson().fromJson(getSharedPreferences("user", MODE_PRIVATE).getString("User", ""), User.class).getId()));
        txt2.append(String.valueOf(new Gson().fromJson(getSharedPreferences("user", MODE_PRIVATE).getString("User", ""), User.class).getMoney()));
        //--------------------------------------------------------------------------------------------------------------------
        navigationView.setNavigationItemSelectedListener(this);



    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()) {
            case R.id.nav_personal:
                Intent PersonalIntent = new Intent(HomeActivity.this, PersonalActivity.class);
                startActivity(PersonalIntent);
               /* Toast.makeText(HomeActivity.this, "敬请期待！", Toast.LENGTH_SHORT).show();    //编辑功能，开发中*/
                finish();
                break;
            case R.id.nav_order:
                Intent OrderIntent = new Intent(HomeActivity.this, TradeActivity.class);
                OrderIntent.putExtra("user", uid);
                startActivity(OrderIntent);
                finish();
                break;
            case R.id.nav_video:
                Intent VideoIntent = new Intent(HomeActivity.this, VideoActivity.class);
                startActivity(VideoIntent);
                finish();
                break;
            case R.id.nav_history:
                Intent HistoryIntent = new Intent(HomeActivity.this, HistoryActivity.class);
                startActivity(HistoryIntent);
                finish();
                break;
            case R.id.nav_quit:
               /* Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(intent);*/
                showNormalDialog();
                break;
        }
       /* if (id == R.id.nav_personal) {

        } else if (id == R.id.nav_order) {

        } else if (id == R.id.nav_video) {

        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_quit) {

        } else if (id == R.id.nav_send) {

        }*/


        /*BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_apps_black_24dp, "Home"))
                .addItem(new BottomNavigationItem(R.drawable.ic_phone_android_black_24dp, "Books"))
                .addItem(new BottomNavigationItem(R.drawable.ic_music_note_black_24dp, "Music"))
                .addItem(new BottomNavigationItem(R.drawable.ic_tv_black_24dp, "Movies & TV"))
                .addItem(new BottomNavigationItem(R.drawable.ic_games_black_24dp, "Games"))
                .initialise();
*/
      /*  bottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
