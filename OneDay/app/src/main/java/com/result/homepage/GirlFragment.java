package com.result.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
public class GirlFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.girl_rl)
    RecyclerView girlRl;
    @Bind(R.id.girl_srl)
    SwipeRefreshLayout girlSrl;
    @Bind(R.id.fab_add_comment)
    FloatingActionButton fabAddComment;
    private int type;
    private MyGirlRecyclerViewAdapter adapter;
    private int pageSize = 16;
    private int page = 1;
    private boolean isFirst=true;

    private List<GirlBean.ResultsBean> list = new ArrayList<>();


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

            girlRl.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            getData(page, pageSize, isFirst);
            setSwipe();
            girlRl.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (isSlideToBottom(girlRl)) {
                        Toast.makeText(getActivity(), "正在加载", Toast.LENGTH_SHORT).show();
                        page++;
                        isFirst = false;
                        getData(page, pageSize, isFirst);

                    }
                }
            });
            fabAddComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fabAddComment.setBackgroundResource(R.mipmap.ic_about);
                    girlRl.smoothScrollToPosition(0);
                }
            });
        }
    }
    private void setSwipe() {
        girlSrl.setOnRefreshListener(this);//设置下拉监听
        girlSrl.setSize(SwipeRefreshLayout.DEFAULT);//设置刷新进入圈的大小
        girlSrl.setColorSchemeResources(R.color.colorAccent,
                R.color.colorHpBack,
                R.color.colorPrimaryDark,
                R.color.colorPrimary);
        girlSrl.setProgressBackgroundColor(R.color.colorHpTitle);
    }

    public void getData(int page, int pageSize, final boolean isFirs) {
        OkHttp.getAsync("http://gank.io/api/data/福利/"+pageSize+"/"+page, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson=new Gson();
                GirlBean bean = gson.fromJson(result,GirlBean.class);
                list.addAll(bean.getResults());
                setAdapter(isFirs);
                isFirst=false;
            }
        });
    }

    private void setAdapter(Boolean isFirst) {
        if(isFirst){
            adapter = new MyGirlRecyclerViewAdapter(getActivity(),list);
            girlRl.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }

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

    //下拉刷新
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list.clear();
                page=1;
                pageSize=16;
                getData(page,pageSize,true);
                setAdapter(true);
                girlSrl.setRefreshing(false);//设置刷新状态
            }
        },1000);
    }


    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        //已经滑动的高度+整体的高度>整体高度
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
