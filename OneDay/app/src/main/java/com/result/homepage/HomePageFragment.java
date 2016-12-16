package com.result.homepage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.result.R;
import com.result.content.HomePageActivity;
import com.result.details.DetailsActivity;
import com.result.homepage.adapter.MyHpRecyclerViewAdapter;
import com.result.homepage.bean.HpBean;
import com.result.tools.BaseFragment;
import com.result.tools.FirstEvent;
import com.result.tools.Network;
import com.result.tools.OkHttp;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import okhttp3.Request;

/**
 * autour: 刘东东
 * date: 2016/12/15 20:03
 * update: 2016/12/15
 */
public class HomePageFragment extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.hp_toolBar)
    Toolbar hpToolBar;
    @Bind(R.id.hp_rv)
    RecyclerView hpRv;
    @Bind(R.id.fab_add_comment)
    FloatingActionButton fabAddComment;
    private ArrayList<HpBean.ResultBean> list;
    private Activity mActivity = getActivity();
    private HomePageActivity mHomePageActivity;

    private String url = "http://api.juheapi.com/japi/toh?key=69a7eeba7869f8bdcdee7b2bc3bb5aa2&v=1.0&month=12&day=17";
    private int month=12;
    private int day=17;
    private int type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        //调用工具类判断网络
        type = Network.GetNetype(getActivity());
        if (type==-1){
            view = inflater.inflate(R.layout.hp_home_no_network, null);
        }else{
            view = inflater.inflate(R.layout.hp_fragment, null);
            ButterKnife.bind(this, view);
        }


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (type!=-1) {
            mActivity = getActivity();
            mHomePageActivity = (HomePageActivity) mActivity;
            hpToolBar.setTitle("历史上的今天");
            hpToolBar.setTitleTextColor(Color.WHITE);

            mHomePageActivity.setSupportActionBar(hpToolBar);
            fabAddComment.setOnClickListener(this);
            //获取数据
            addDatas();
        }


    }

    private void addDatas() {
        OkHttp.getAsync(url, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();

                HpBean mHpBean = gson.fromJson(result,HpBean.class);
                list = (ArrayList<HpBean.ResultBean>) mHpBean.getResult();

                hpRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
                MyHpRecyclerViewAdapter adapter = new MyHpRecyclerViewAdapter(getActivity(), list,month,day);
                hpRv.setAdapter(adapter);
                adapter.setOnItemClickListener(new MyHpRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, HpBean.ResultBean data) {
                        //发布事件
                        EventBus.getDefault().postSticky(
                                new FirstEvent(data));
                        startActivity(new Intent(getActivity(), DetailsActivity.class));
                    }

                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_add_comment:
                Toast.makeText(getActivity(),"日历",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
