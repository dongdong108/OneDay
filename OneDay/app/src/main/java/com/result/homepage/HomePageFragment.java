package com.result.homepage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.result.R;
import com.result.content.HomePageActivity;
import com.result.dao.ScUser;
import com.result.details.CalendarActivity;
import com.result.details.DetailsActivity;
import com.result.homepage.adapter.MyHpRecyclerViewAdapter;
import com.result.homepage.bean.HpBean;
import com.result.tools.BaseFragment;
import com.result.tools.CollEvent;
import com.result.tools.FirstEvent_Rili;
import com.result.tools.Network;
import com.result.tools.OkHttp;
import com.result.utils.DateUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import okhttp3.Request;

/**
 * autour: 刘东东
 * date: 2016/12/15 20:03
 * update: 2016/12/15
 */
public class HomePageFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.hp_toolBar)
    Toolbar hpToolBar;
    @Bind(R.id.hp_rv)
    RecyclerView hpRv;
    @Bind(R.id.fab_add_comment)
    FloatingActionButton fabAddComment;
    @Bind(R.id.hp_srl)
    SwipeRefreshLayout hpSrl;
    @Bind(R.id.xiangzuo1)
    ImageView xiangzuo1;
    @Bind(R.id.data1)
    TextView data;
    @Bind(R.id.xiangyou1)
    ImageView xiangyou1;
    private ArrayList<HpBean.ResultBean> list;
    private Activity mActivity = getActivity();
    private HomePageActivity mHomePageActivity;

    private String url = "http://v.juhe.cn/todayOnhistory/queryEvent.php?key=69a7eeba7869f8bdcdee7b2bc3bb5aa2&date=";

    private int type;
    private MyHpRecyclerViewAdapter adapter;

    private Calendar mCalendar = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
    private SimpleDateFormat dateFormat;
    private String date;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        //调用工具类判断网络
        type = Network.GetNetype(getActivity());
        if (type == -1) {
            view = inflater.inflate(R.layout.hp_home_no_network, null);
        } else {
            view = inflater.inflate(R.layout.hp_fragment, null);
            ButterKnife.bind(this, view);
        }


        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (type != -1) {
            //注册EventBus，哪里需要值哪里就需要注册
            EventBus.getDefault().register(this);
            mActivity = getActivity();
            mHomePageActivity = (HomePageActivity) mActivity;
            hpToolBar.setTitle("历史上的今天");
            hpToolBar.setTitleTextColor(Color.WHITE);

            mHomePageActivity.setSupportActionBar(hpToolBar);
            fabAddComment.setOnClickListener(this);
            /**
             * 获取系统时间
             */
            initView();

            hpSrl.setColorSchemeResources(R.color.colorAccent,
                    R.color.colorHpBack,
                    R.color.colorPrimaryDark,
                    R.color.colorPrimary);
            hpSrl.setSize(SwipeRefreshLayout.LARGE);
            hpSrl.setProgressBackgroundColor(R.color.colorHpTitle);
            hpSrl.setProgressViewEndTarget(true, 200);
            //下拉刷新
            hpSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hpSrl.setRefreshing(false);
                        }
                    },1000);
                }
            });
        }
    }

    /**
     * 获取系统时间
     */
    private void initView() {

        //改变日期方法
        changeDate(mCalendar);

        xiangzuo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                //取当前日期的前一天.
                list.clear();
                DateUtil.num = -1;
                date = DateUtil.getDateStr(data.getText().toString(), DateUtil.num);
                data.setText(date);
                int month3 = Integer.parseInt(DateUtil.getMonth(date));
                int day3 = Integer.parseInt(DateUtil.getMonthDate(date));
                addDatas(month3,day3);
            }
        });
        xiangyou1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                //取当前日期的后一天.
                list.clear();
                DateUtil.num = 1;
                date = DateUtil.getDateStr(data.getText().toString(), DateUtil.num);
                data.setText(date);
                int month3 = Integer.parseInt(DateUtil.getMonth(date));
                int day3 = Integer.parseInt(DateUtil.getMonthDate(date));
                addDatas(month3,day3);
            }
        });

    }

    private void changeDate(Calendar mCalendar) {
        //格式化日期
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //获取月份，0表示1月份
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH) + 1;
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        date = year + "年"+month + "月" +day + "日";
        data.setText(date);
        Log.d("Data", month + day + "***999999999999");
        addDatas(month,day);
    }

    private void addDatas(int i,int y) {
        OkHttp.getAsync(url + i + "/" + y, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();

                HpBean mHpBean = gson.fromJson(result, HpBean.class);
                list = (ArrayList<HpBean.ResultBean>) mHpBean.getResult();

                hpRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
                adapter = new MyHpRecyclerViewAdapter(getActivity(), list);
                hpRv.setAdapter(adapter);
                adapter.setOnItemClickListener(new MyHpRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, HpBean.ResultBean data) {
                        ScUser sc = new ScUser();
                        sc.setE_id(data.getE_id());
                        sc.setData(data.getDate());
                        sc.setTitle(data.getTitle());
                        //发布事件
                        EventBus.getDefault().postSticky(
                                new CollEvent(sc));
                        startActivity(new Intent(getActivity(), DetailsActivity.class));
                    }

                });
            }
        });
    }
    //接收日期回传的值
    @Subscribe(sticky = true)
    public void onEventMainThread(FirstEvent_Rili event) {
        int month2 = event.getMonth();
        int day2 = event.getDay();
        int year2 = event.getYear();
        data.setText(year2 + "年"+(month2+1) + "月" +day2 + "日");
        addDatas(month2+1,day2);
    }
    
    //生命周期
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_add_comment:
                startActivity(new Intent(getActivity(), CalendarActivity.class));
                break;
        }
    }
}
