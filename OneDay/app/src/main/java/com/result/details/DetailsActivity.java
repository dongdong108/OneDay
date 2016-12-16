package com.result.details;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.result.R;
import com.result.homepage.bean.HpBean;
import com.result.tools.FirstEvent;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class DetailsActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    @Bind(R.id.destails_toolbar)
    Toolbar destailsToolbar;
    @Bind(R.id.details_abl)
    AppBarLayout detailsAbl;
    @Bind(R.id.fab_add_comment)
    FloatingActionButton fabAddComment;
    @Bind(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.getails_iv)
    ImageView getailsIv;
    @Bind(R.id.getails_tv_content)
    TextView getailsTvContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);


        //注册EventBus，哪里需要值哪里就需要注册
        EventBus.getDefault().register(this);


        detailsAbl.addOnOffsetChangedListener(this);

    }

    @Subscribe(sticky = true)
    public void onEventMainThread(FirstEvent event) {
        HpBean.ResultBean rb = event.getData();
        getailsTvContent.setText(rb.getDes());
         Glide.with(this)
          .load(rb.getPic()).into(getailsIv);
        destailsToolbar.setTitle(rb.getTitle());
        destailsToolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(destailsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//返回箭头
        destailsToolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
    @Override
    protected void onResume() {
        super.onResume();
        detailsAbl.addOnOffsetChangedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        detailsAbl.removeOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            appBarLayout.setEnabled(false);
            fabAddComment.setVisibility(View.INVISIBLE);
        } else {
            appBarLayout.setEnabled(true);
            fabAddComment.setVisibility(View.VISIBLE);
        }
    }
}
