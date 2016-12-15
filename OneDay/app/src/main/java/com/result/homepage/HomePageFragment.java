package com.result.homepage;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.result.R;
import com.result.content.HomePageActivity;
import com.result.homepage.adapter.MyHpRecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * autour: 刘东东
 * date: 2016/12/15 20:03
 * update: 2016/12/15
 */
public class HomePageFragment extends Fragment {

    @Bind(R.id.hp_toolBar)
    Toolbar hpToolBar;
    @Bind(R.id.hp_rv)
    RecyclerView hpRv;
    private ArrayList<String> list;
    private Activity mActivity = getActivity();
    private HomePageActivity mHomePageActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hp_fragment, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        mHomePageActivity = (HomePageActivity) mActivity;
        hpToolBar.setTitle("历史上的今天");
        hpToolBar.setTitleTextColor(Color.WHITE);

        mHomePageActivity.setSupportActionBar(hpToolBar);

        //添加数据
        addDatas();
        hpRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
        hpRv.setAdapter(new MyHpRecyclerViewAdapter(getActivity(), list));

    }

    private void addDatas() {
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("你好" + i);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
