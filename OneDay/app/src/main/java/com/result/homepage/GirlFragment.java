package com.result.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.result.R;
import com.result.details.GirlDetailsActivity;
import com.result.homepage.adapter.MyGirlRecyclerViewAdapter;
import com.result.homepage.bean.GirlBean;
import com.result.tools.BaseFragment;
import com.result.tools.GirlEvent;
import com.result.tools.Network;
import com.result.tools.OkHttp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import okhttp3.Request;

/**
 * autour: 刘东东
 * date: 2016/12/16 10:41
 * update: 2016/12/16
 */
public class GirlFragment extends BaseFragment {

    @Bind(R.id.girl_rl)
    RecyclerView girlRl;
    @Bind(R.id.girl_srl)
    SwipeRefreshLayout girlSrl;
    @Bind(R.id.fab_add_comment)
    FloatingActionButton fabAddComment;
    private int type;
    private String url = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/16/";
    private int i = 1;
    private List<GirlBean.ResultsBean> list = new ArrayList<>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                    girlSrl.setRefreshing(false);

                    break;
                default:
                    break;
            }
        }
    };
    private MyGirlRecyclerViewAdapter adapter;
    private GridLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        //调用工具类判断网络
        type = Network.GetNetype(getActivity());
        if (type == -1) {
            view = inflater.inflate(R.layout.hp_home_no_network, null);
        } else {
            view = View.inflate(getActivity(), R.layout.girl_fragment, null);
            ButterKnife.bind(this, view);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (type != -1) {
            list.clear();
            //获取数据方法
            getDatas();
            //点击置顶
            fabAddComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    girlRl.smoothScrollToPosition(0);
                }
            });

            girlSrl.setColorSchemeResources(R.color.colorAccent,
                    R.color.colorHpBack,
                    R.color.colorPrimaryDark,
                    R.color.colorPrimary);
            girlSrl.setSize(SwipeRefreshLayout.LARGE);
            girlSrl.setProgressBackgroundColor(R.color.colorHpTitle);
            girlSrl.setProgressViewEndTarget(true, 200);
            //下拉加载
            girlSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new Thread() {
                        @Override
                        public void run() {
//                            list.clear();
                            getDatas();
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            mHandler.sendEmptyMessage(1);
                        }
                    }.start();
                }
            });
            //RecyclerView滑动监听
            girlRl.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (isSlideToBottom(girlRl)){
                        Toast.makeText(getActivity(),"滑到底部了", Toast.LENGTH_SHORT).show();
                        i++;
                        getDatas();

                    }
                }
            });


        }
    }

    private void getDatas() {
        OkHttp.getAsync(url+(i+""), new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {

                Gson gson = new Gson();
                GirlBean girlBean = gson.fromJson(result, GirlBean.class);
                list.addAll(girlBean.getResults());

                layoutManager = new GridLayoutManager(getActivity(), 2);
                girlRl.setLayoutManager(layoutManager);
                adapter = new MyGirlRecyclerViewAdapter(getActivity(), list);
                girlRl.setAdapter(adapter);
                adapter.setOnItemClickListener(new MyGirlRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, String data) {
                        //发布事件
                        EventBus.getDefault().postSticky(
                                new GirlEvent(data));
                        startActivity(new Intent(getActivity(), GirlDetailsActivity.class));
                    }
                });
            }
        });
    }
    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
