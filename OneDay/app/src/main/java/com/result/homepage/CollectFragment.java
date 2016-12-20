package com.result.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.result.R;
import com.result.dao.ScUser;
import com.result.dao.ScUserDao;
import com.result.details.DetailsActivity;
import com.result.tools.CollEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * autour: 刘东东
 * date: 2016/12/19 18:57
 * update: 2016/12/19
 */
public class CollectFragment extends Fragment {

    @Bind(R.id.coll_rv)
    RecyclerView collRv;
    @Bind(R.id.coll_srl)
    SwipeRefreshLayout collSrl;
    private MyCollRecyclerViewAdapter adapter;
    private List<ScUser> list;
    private ScUserDao dao;

    /************************************************/
    /**
     * RecyclerView点击和长按时间的准备工作
     */
    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        public interface OnItemClickListener {
            void onItemClick(View view, int position);

            void onItemLongClick(View view, int position);
        }

        private OnItemClickListener mListener;

        private GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
            mListener = listener;

            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());

                    if (childView != null && mListener != null) {
                        mListener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());

            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

    /********************************************/


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.collect_fragment, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

        /**
         * RecyclerView的点击和长按事件
         * */
        collRv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), collRv, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {

                //发布事件
                EventBus.getDefault().postSticky(
                        new CollEvent(list.get(position)));
                Intent intent = new Intent(getActivity(), DetailsActivity.class);

                startActivity(intent);
            }

            /**
             * RecyclerView的长按的方法
             * */
            @Override
            public void onItemLongClick(View view, int position) {
            }
        }));

        collSrl.setColorSchemeResources(R.color.colorAccent,
                R.color.colorHpBack,
                R.color.colorPrimaryDark,
                R.color.colorPrimary);
        collSrl.setSize(SwipeRefreshLayout.LARGE);
        collSrl.setProgressBackgroundColor(R.color.colorHpTitle);
        collSrl.setProgressViewEndTarget(true, 200);
        collSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread() {
                    @Override
                    public void run() {
                        list.clear();
                        init();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        mHandler.sendEmptyMessage(1);
                    }
                }.start();
            }
        });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                    collSrl.setRefreshing(false);
                    //swipeRefreshLayout.setEnabled(false);
                    break;
                default:
                    break;
            }
        }
    };


    // 点击跳转到商品详情
    private void init() {
        list = new ArrayList<>();
        dao = new ScUserDao(getActivity());
        xiba();
    }

    public void xiba() {
        list = dao.select();
        adapter = new MyCollRecyclerViewAdapter(getActivity(), list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        collRv.setLayoutManager(layoutManager);
        collRv.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    /*适配器*/
    class MyCollRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private Context context;
        private List<ScUser> list;
        private ScUserDao dao;


        public MyCollRecyclerViewAdapter(Context context, List<ScUser> list) {
            this.context = context;
            this.list = list;
            dao = new ScUserDao(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.coll_rv_item,parent,false);
            myViewHolder holder = new myViewHolder(view);
            return holder;
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ((myViewHolder)holder).tvTitle.setText(list.get(position).getTitle());
            ((myViewHolder)holder).tvYear.setText(list.get(position).getData());
            Glide.with(context)
                    .load(list.get(position).getImage()).into(((myViewHolder)holder).iv);

            ((myViewHolder)holder).btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = list.get(position).getId();
                    dao.delete(id);

                    xiba();
                }
            });
            //将数据保存在itemView的Tag中，以便点击时进行获取
            holder.itemView.setTag(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }



        class myViewHolder extends RecyclerView.ViewHolder{
            TextView tvTitle;
            TextView tvYear;
            ImageView iv;
            Button btnDelete;
            public myViewHolder(View itemView) {
                super(itemView);

                tvTitle = (TextView) itemView.findViewById(R.id.title);
                tvYear = (TextView) itemView.findViewById(R.id.data);
                iv = (ImageView) itemView.findViewById(R.id.image);
                btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
            }
        }
    }

}



