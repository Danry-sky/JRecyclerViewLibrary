package com.jtech.jrecyclerviewlibrary;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    protected View createView(LayoutInflater layoutInflater, ViewGroup viewGroup, int viewType) {
        return layoutInflater.inflate(R.layout.view_item_test, viewGroup, false);
    }

    @Override
    protected void convert(RecyclerHolder holder, String model, int position) {
        holder.setText(R.id.textview, model);
    }
}