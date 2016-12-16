package com.result.homepage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.result.R;
import com.result.homepage.bean.GirlBean;

import java.util.List;

/**
 * autour: 刘东东
 * date: 2016/12/15 20:23 
 * update: 2016/12/15
 */
public class MyGirlRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private Context context;
    private List<GirlBean.ResultsBean> list;


    public MyGirlRecyclerViewAdapter(Context context, List<GirlBean.ResultsBean> list) {
        this.context = context;
        this.list = list;

    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.girl_rv_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return holder;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Glide.with(context)
                .load(list.get(position).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓冲全尺寸
                .centerCrop()//设置居中
                .placeholder(R.mipmap.ic_unlike)//设置占位图
                .error(R.mipmap.ic_history)//加载错误图
                .into(((MyViewHolder)holder).iv);

//        Glide.with(context)
//                .load(
//                .placeholder(R.mipmap.ic_launcher)
//                .error(R.mipmap.ic_launcher)
//                .dontAnimate()
//                .into(((MyViewHolder)holder).iv);

        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(list.get(position).getUrl());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (String) v.getTag());
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        public MyViewHolder(View itemView) {
            super(itemView);

            iv = (ImageView) itemView.findViewById(R.id.girl_rv_iv);
        }
    }
}
