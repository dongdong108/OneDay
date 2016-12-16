package com.result.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private int type;
    private String url = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/16/1";
    private List<GirlBean.ResultsBean> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        //调用工具类判断网络
        type = Network.GetNetype(getActivity());
        if (type==-1){
            view = inflater.inflate(R.layout.hp_home_no_network, null);
        }else{
            view = View.inflate(getActivity(), R.layout.girl_fragment, null);
            ButterKnife.bind(this, view);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (type!=-1){
            //获取数据方法
            getDatas();
        }
    }

    private void getDatas() {
        OkHttp.getAsync(url, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                GirlBean girlBean = gson.fromJson(result,GirlBean.class);
                list = girlBean.getResults();

                girlRl.setLayoutManager(new GridLayoutManager(getActivity(),2));
                MyGirlRecyclerViewAdapter adapter = new MyGirlRecyclerViewAdapter(getActivity(), list);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
