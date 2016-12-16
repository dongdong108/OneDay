package com.result.content;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.result.R;
import com.result.homepage.AsRegardsFragment;
import com.result.homepage.CollectFragment;
import com.result.homepage.GirlFragment;
import com.result.homepage.HomePageFragment;

public class HomePageActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private DrawerLayout mDrawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private HomePageFragment mHomePageFragment;
    private CollectFragment mCollectFragment;
    private GirlFragment mGirlFragment;
    private AsRegardsFragment mAsRegardsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //控件初始化
        initView();
        //添加数据源
        addDatas();

        //得到管理者
        final FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction().replace(R.id.frame,mHomePageFragment).commit();

        //设置左上角的图标响应
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolBar, 0, 0) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerToggle.syncState();
        mDrawer.setDrawerListener(mDrawerToggle); //设置侧滑监听
        //侧滑每个item的点击事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mToolBar.setTitle(item.getTitle());
                Toast.makeText(getApplicationContext(),item.getTitle(),Toast.LENGTH_SHORT).show();
                switch(item.getItemId()){
                    case R.id.item1:
                        fm.beginTransaction().replace(R.id.frame,mHomePageFragment).commit();
                        item.setChecked(true);

                        break;
                    case R.id.item2:
                        fm.beginTransaction().replace(R.id.frame,mGirlFragment).commit();
                        item.setChecked(true);
                        break;
                    case R.id.item3:
                        fm.beginTransaction().replace(R.id.frame,mCollectFragment).commit();
                        item.setChecked(true);
                        break;
                    case R.id.item4:
                        fm.beginTransaction().replace(R.id.frame,mAsRegardsFragment).commit();
                        item.setChecked(true);
                        break;
                }
                mDrawer.closeDrawer(Gravity.LEFT);
                return false;
            }
        });
    }


    private void addDatas() {
        mHomePageFragment = new HomePageFragment();
        mCollectFragment = new CollectFragment();
        mGirlFragment = new GirlFragment();
        mAsRegardsFragment = new AsRegardsFragment();


    }

    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.mToolBar); //ToolBar
        mDrawer = (DrawerLayout) findViewById(R.id.myDrawer); //DrawerLayout
        navigationView = (NavigationView) findViewById(R.id.nav); //NavigationView导航栏

        mToolBar.setTitle("历史上的今天");
        mToolBar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(mToolBar);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
            builder.setTitle("提示"); //设置标题
            builder.setMessage("您确认要退出么?"); //设置内容
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    System.exit(0);
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            //参数都设置完成了，创建并显示出来
            builder.create().show();

        }

        return false;

    }

}
