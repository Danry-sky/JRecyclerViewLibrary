package com.jtech.jrecyclerviewlibrary;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.jtech.adapter.RecyclerAdapter;
import com.jtech.view.RecyclerHolder;

/**
 * 测试适配器
 * Created by wuxubaiyang on 16/5/9.
 */
public class TestAdapter extends RecyclerAdapter<String> {
    public TestAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return inflater.inflate(R.layout.view_item_test, parent, false);
    }

    @Override
    public void convert(RecyclerHolder holder, String item, int position) {
        holder.setText(R.id.textview, item);
    }
}