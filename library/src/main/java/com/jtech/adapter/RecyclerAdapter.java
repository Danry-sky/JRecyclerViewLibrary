package com.jtech.adapter;

import android.app.Activity;

import com.jtech.view.RecyclerHolder;

/**
 * 通用适配器
 * Created by jianghan on 2016/9/9.
 */
public abstract class RecyclerAdapter<D> extends BaseJAdapter<RecyclerHolder, D> {

    /**
     * 主构造
     *
     * @param activity Activity对象
     */
    public RecyclerAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public void convert(RecyclerHolder holder, int viewType, int position) {
        convert(holder, getItem(position), position);
    }

    protected abstract void convert(RecyclerHolder holder, D model, int position);
}