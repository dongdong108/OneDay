package com.result.details;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.result.R;
import com.result.tools.GirlEvent;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class GirlDetailsActivity extends AppCompatActivity {

    @Bind(R.id.gd_toolBar)
    Toolbar gdToolBar;
    @Bind(R.id.gd_iv)
    ImageView gdIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girl_details);
        ButterKnife.bind(this);
        //标题栏
        settoolbar();
        //注册EventBus，哪里需要值哪里就需要注册
        EventBus.getDefault().register(this);

    }

    @Subscribe(sticky = true)
    public void onEventMainThread(GirlEvent event) {
        String url = event.getData();
        Glide.with(this)
                .load(url).into(gdIv);
    }

    private void settoolbar() {
        gdToolBar.setTitle("妹纸");
        gdToolBar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(gdToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//返回箭头
        gdToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(getApplicationContext(), "返回", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //生命周期
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }
}
