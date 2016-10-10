package com.jtech.jrecyclerviewlibrary;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.jtech.listener.OnItemClickListener;
import com.jtech.listener.OnItemLongClickListener;
import com.jtech.listener.OnItemViewMoveListener;
import com.jtech.listener.OnItemViewSwipeListener;
import com.jtech.listener.OnLoadListener;
import com.jtech.view.JRecyclerView;
import com.jtech.view.RecyclerHolder;
import com.jtech.view.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends Activity implements OnItemClickListener, OnItemLongClickListener, RefreshLayout.OnRefreshListener, OnLoadListener, OnItemViewSwipeListener, OnItemViewMoveListener {

    private JRecyclerView jRecyclerView;
    private RefreshLayout refreshLayout;
    private TestAdapter testAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //控件实例化
        jRecyclerView = (JRecyclerView) findViewById(R.id.jrecyclerview);
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshlayout);
        //设置layoutmanager
        jRecyclerView.setLayoutManager(new LinearLayoutManager(TestActivity.this));
        //设置适配器
        testAdapter = new TestAdapter(TestActivity.this);
        jRecyclerView.setAdapter(testAdapter);
        //开启滑动到底部加载更多功能
        jRecyclerView.setLoadMore(true);
        //开启滑动删除(默认状态，可以手动设置)
        jRecyclerView.setSwipeFree(true, this);
        //开启长点击拖动换位(默认状态，可以手动设置)
        jRecyclerView.setMoveFree(true, this);
        //设置事件
        jRecyclerView.setOnLoadListener(this);
        refreshLayout.setOnRefreshListener(this);
        jRecyclerView.setOnItemClickListener(this);
        jRecyclerView.setOnItemLongClickListener(this);
        //主动发起下拉刷新
        refreshLayout.startRefreshing();
    }

    /**
     * 模拟数据请求
     *
     * @param loadMore 是否为加载更多的标记
     */
    private void loadData(final boolean loadMore) {
        new AsyncTask<Boolean, String, List<String>>() {
            @Override
            protected List<String> doInBackground(Boolean... params) {
                try {
                    Thread.sleep(1300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean loadMore = params[0];
                int pageIndex = testAdapter.getPage(loadMore);
                int displayNumber = testAdapter.getDisplayNumber();
                List<String> datas = new ArrayList<>();
                for (int i = pageIndex * displayNumber; i < pageIndex * displayNumber + displayNumber; i++) {
                    datas.add("当前第" + i + "行");
                }
                return datas;
            }

            @Override
            protected void onPostExecute(List<String> strings) {
                //设置数据
                testAdapter.setDatas(strings, loadMore);
                //标记为请求完成
                refreshLayout.refreshingComplete();
                jRecyclerView.setLoadCompleteState();
            }
        }.execute(loadMore);
    }

    /**
     * item点击事件
     *
     * @param holder
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(RecyclerHolder holder, View view, int position) {
        Toast.makeText(TestActivity.this, "第" + position + "行点击事件", Toast.LENGTH_SHORT).show();
    }

    /**
     * item长点击事件
     *
     * @param holder
     * @param view
     * @param position
     * @return
     */
    @Override
    public boolean onItemLongClick(RecyclerHolder holder, View view, int position) {
        Toast.makeText(TestActivity.this, "第" + position + "行长点击事件", Toast.LENGTH_SHORT).show();
        return false;//因为这里return false 所以长点击拖动才有效，演示功能用，所以会触发两次震动
    }

    /**
     * item长点击拖动换位事件
     *
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onItemViewMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        testAdapter.moveData(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return false;
    }

    /**
     * item滑动删除事件
     *
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onItemViewSwipe(RecyclerView.ViewHolder viewHolder, int direction) {
        testAdapter.removeData(viewHolder.getAdapterPosition());
        if (direction == ItemTouchHelper.START) {
            Toast.makeText(this, "Delete!", Toast.LENGTH_SHORT).show();
        } else if (direction == ItemTouchHelper.END) {
            Toast.makeText(this, "Android!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 加载更多的回调
     */
    @Override
    public void loadMore() {
        loadData(true);
    }

    /**
     * 下拉刷新的回调
     */
    @Override
    public void onRefresh() {
//        loadData(false);
        refreshLayout.refreshingComplete();
    }
}