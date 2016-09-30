package com.jtech.listener;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.jtech.adapter.LoadMoreAdapter;

/**
 * 自定义滑动删除以及拖动换位
 */
public class ItemTouchCallback extends ItemTouchHelper.Callback {
    private boolean isItemViewSwipeEnabled = false;
    private boolean isLongPressDragEnabled = false;
    private int dragFlags, swipeFlags;

    private LoadMoreAdapter loadMoreAdapter;

    private OnItemViewMoveListener onItemViewMoveListener;
    private OnItemViewSwipeListener onItemViewSwipeListener;

    public void setLoadMoreAdapter(LoadMoreAdapter loadMoreAdapter) {
        this.loadMoreAdapter = loadMoreAdapter;
    }

    public void setDragFlags(int dragFlags) {
        this.dragFlags = dragFlags;
    }

    public void setSwipeFlags(int swipeFlags) {
        this.swipeFlags = swipeFlags;
    }

    public void setItemViewSwipeEnabled(boolean itemViewSwipeEnabled) {
        isItemViewSwipeEnabled = itemViewSwipeEnabled;
    }

    public void setLongPressDragEnabled(boolean longPressDragEnabled) {
        isLongPressDragEnabled = longPressDragEnabled;
    }

    public void setOnItemViewMoveListener(OnItemViewMoveListener onItemViewMoveListener) {
        this.onItemViewMoveListener = onItemViewMoveListener;
    }

    public void setOnItemViewSwipeListener(OnItemViewSwipeListener onItemViewSwipeListener) {
        this.onItemViewSwipeListener = onItemViewSwipeListener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (null != loadMoreAdapter && loadMoreAdapter.canPress(viewHolder.getAdapterPosition())) {
            return makeMovementFlags(dragFlags, swipeFlags);
        }
        return 0;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (null != onItemViewMoveListener && null != loadMoreAdapter && loadMoreAdapter.canPress(target.getAdapterPosition())) {
            return onItemViewMoveListener.onItemViewMove(recyclerView, viewHolder, target);
        }
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (null != onItemViewSwipeListener) {
            onItemViewSwipeListener.onItemViewSwipe(viewHolder, direction);
        }
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return isItemViewSwipeEnabled;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return isLongPressDragEnabled;
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}