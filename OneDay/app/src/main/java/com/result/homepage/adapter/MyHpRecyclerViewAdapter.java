package com.result.homepage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.result.R;

import java.util.ArrayList;

/**
 * autour: 刘东东
 * date: 2016/12/15 20:23 
 * update: 2016/12/15
 */
public class MyHpRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<String> list;

    public MyHpRecyclerViewAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hp_rv_item,parent,false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((myViewHolder)holder).tv.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public myViewHolder(View itemView) {
            super(itemView);

            tv = (TextView) itemView.findViewById(R.id.hp_rv_tv);
        }
    }
}
