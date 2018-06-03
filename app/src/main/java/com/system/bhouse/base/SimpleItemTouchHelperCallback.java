package com.system.bhouse.base;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Administrator on 2016-4-18.
 * ClassName: com.system.bhouse.base
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final OnMoveAndSwipeListener moveAndSwipeListener;

    public interface OnMoveAndSwipeListener {
        boolean onItemMove(int fromPosition, int toPosition);

        void onItemDismiss(int position);
    }

    public SimpleItemTouchHelperCallback(OnMoveAndSwipeListener moveAndSwipeListener) {
        this.moveAndSwipeListener = moveAndSwipeListener;
    }

    public interface OnStateChangedListener{
        void onItemSelected();

        void onItemClear();
    }

    /**
     * 这个方法是用来设置我们拖动的方向以及侧滑的方向的
     */

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            // 如果是GridView样式的RecyclerView
            // 设置拖拽方向为上下左右
            final int dragflag = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

            final int swipeflag = 0;

            return makeFlag(dragflag, swipeflag);
        }
        else {
            int dragflag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeflag = ItemTouchHelper.START | ItemTouchHelper.END;

            return makeFlag(dragflag,swipeflag);
        }

    }

    private boolean mIsLongPressDragEnabled=true;

    public void setMoveAndSwipeListener(boolean mIsLongPressDragEnabled){
        this.mIsLongPressDragEnabled=mIsLongPressDragEnabled;
    }

    /**
     * 能不能长按拖动
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return mIsLongPressDragEnabled;
    }

    /**
     * 当我们拖动一个item的时候回掉这个方法
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        // 如果两个item不是一个类型的，我们让他不可以拖拽
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }

        // 回调adapter中的onItemMove方法
        return moveAndSwipeListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    }

    /**
     * 当我们侧滑item时会回调此方法
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        moveAndSwipeListener .onItemDismiss(viewHolder.getAdapterPosition());
    }

    /**
     * 当状态改变时回调此方法
     */

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        //当前状态不是idel（空闲）状态时，说明当前正在拖拽或者侧滑
        if(actionState!=ItemTouchHelper.ACTION_STATE_IDLE){
            if(viewHolder instanceof OnStateChangedListener){
                OnStateChangedListener listener =(OnStateChangedListener) viewHolder;
                listener.onItemSelected();
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * 当用户拖拽完或者侧滑完一个item时回调此方法，用来清除施加在item上的一些状态
     */

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if(viewHolder instanceof OnStateChangedListener){
            OnStateChangedListener viewHolder1 = (OnStateChangedListener) viewHolder;
            viewHolder1.onItemClear();
        }
    }

    /**
     * 这个方法可以判断当前是拖拽还是侧滑
     */
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if(actionState==ItemTouchHelper.ACTION_STATE_SWIPE){
            //根据侧滑的位移来修改item的透明度
            final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);

        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
