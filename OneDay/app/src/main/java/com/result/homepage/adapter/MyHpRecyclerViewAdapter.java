package com.result.homepage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.result.R;
import com.result.homepage.bean.HpBean;

import java.util.ArrayList;

/**
 * autour: 刘东东
 * date: 2016/12/15 20:23 
 * update: 2016/12/15
 */
public class MyHpRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private Context context;
    private ArrayList<HpBean.ResultBean> list;
    private int month;
    private int day;


    public MyHpRecyclerViewAdapter(Context context, ArrayList<HpBean.ResultBean> list,int month, int day) {
        this.context = context;
        this.list = list;
        this.month = month;
        this.day = day;
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , HpBean.ResultBean data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hp_rv_item,parent,false);
        myViewHolder holder = new myViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return holder;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((myViewHolder)holder).tvTitle.setText(list.get(position).getTitle());
        ((myViewHolder)holder).tvYear.setText(list.get(position).getYear()+"年"+month+"月"+day+"日");
        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (HpBean.ResultBean) v.getTag());
        }
    }


    class myViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvYear;
        public myViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.hp_rv_tv_title);
            tvYear = (TextView) itemView.findViewById(R.id.hp_rv_tv_year);
        }
    }
}
