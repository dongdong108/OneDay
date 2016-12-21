package com.result.details;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.result.R;
import com.result.dao.ScUser;
import com.result.dao.ScUserDao;
import com.result.homepage.bean.HpDetailsBean;
import com.result.tools.CollEvent;
import com.result.tools.OkHttp;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import okhttp3.Request;

public class DetailsActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener,View.OnClickListener {

    @Bind(R.id.destails_toolbar)
    Toolbar destailsToolbar;
    @Bind(R.id.details_abl)
    AppBarLayout detailsAbl;
    @Bind(R.id.fab_add_comment)
    FloatingActionButton fabAddComment;
    @Bind(R.id.getails_iv)
    ImageView getailsIv;
    @Bind(R.id.getails_tv_content)
    TextView getailsTvContent;
    private String url = "http://v.juhe.cn/todayOnhistory/queryDetail.php?key=69a7eeba7869f8bdcdee7b2bc3bb5aa2&e_id=";
    private String id;
    private HpDetailsBean hdb;
    private ScUserDao scdao;
    private ScUser sc;
    //判断收藏按钮
    private boolean ishave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        //注册EventBus，哪里需要值哪里就需要注册
        EventBus.getDefault().register(this);
        //AppBarLayout滑动方法
        detailsAbl.addOnOffsetChangedListener(this);
        fabAddComment.setOnClickListener(this);
        scdao = new ScUserDao(this);
    }

    @Subscribe(sticky = true)
    public void onEventMainThread(CollEvent event) {
        sc = event.getData();
        id = sc.getE_id();
        getDatas();
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

    public void getDatas() {
        OkHttp.getAsync(url + id, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();

                hdb = gson.fromJson(result,HpDetailsBean.class);
                //设置数据
                setView(hdb.getResult().get(0).getPicUrl().get(0).getUrl(),hdb.getResult().get(0).getContent(),hdb.getResult().get(0).getTitle());

            }
        });
    }
    //设置数据方法
    public void setView(String url,String content,String title){
        //加载图片
        Glide.with(DetailsActivity.this)
                .load(url)
                .error(R.mipmap.default_img)
                .into(getailsIv);
        getailsTvContent.setText(content);

        destailsToolbar.setTitle(title);
        destailsToolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(destailsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//返回箭头
        destailsToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //收藏按钮的点击方法
            case R.id.fab_add_comment:
                ishave=!ishave;

                if (ishave){
                    //添加到数据库
                    try {
                        scdao.insert(sc.getData(), hdb.getResult().get(0).getTitle(), hdb.getResult().get(0).getPicUrl().get(0).getUrl(),sc.getE_id());
                        fabAddComment.setImageResource(R.mipmap.ic_like);
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"不可收藏",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    //删除，根据e_id
                    scdao.delete(id);
                    fabAddComment.setImageResource(R.mipmap.ic_toolbar_like_n);
                }
                break;
        }
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
        ishave = scdao.isHaveid(id);
        if (ishave){
            fabAddComment.setImageResource(R.mipmap.ic_like);
        }else {
            fabAddComment.setImageResource(R.mipmap.ic_unlike);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        detailsAbl.removeOnOffsetChangedListener(this);
    }
}
