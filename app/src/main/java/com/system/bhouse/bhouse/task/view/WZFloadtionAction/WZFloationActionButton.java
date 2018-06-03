package com.system.bhouse.bhouse.task.view.WZFloadtionAction;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.widget.AbsListView;

import com.system.bhouse.bhouse.R;

/**
 * Created by Administrator on 2017-12-14.
 */

public class WZFloationActionButton extends FloatingActionButton{

    private int mScrollThreshold;

    public WZFloationActionButton(Context context) {
        this(context,null);
    }

    public WZFloationActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScrollThreshold = getResources().getDimensionPixelOffset(R.dimen.list_padding);
    }

    public WZFloationActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public void attachToListView(@NonNull AbsListView listView) {
        attachToListView(listView,null,null);
    }

    public void attachToListView(@NonNull AbsListView listView,ScrollDirectionListener scrollDirectionListener,
                                 AbsListView.OnScrollListener onScrollListener) {
        AbsListViewScrollDetectorImpl scrollDetector = new AbsListViewScrollDetectorImpl();
        scrollDetector.setScrollDirectionListener(scrollDirectionListener);
        scrollDetector.setOnScrollListener(onScrollListener);
        scrollDetector.setListView(listView);
        scrollDetector.setScrollThreshold(mScrollThreshold); //一个什么速度的数值
        listView.setOnScrollListener(scrollDetector);
    }

    private class AbsListViewScrollDetectorImpl extends AbsListViewScrollDetector {
        private AbsListView.OnScrollListener mOnScrollListener;
        private ScrollDirectionListener mScrollDirectionListener;

        private void setScrollDirectionListener(ScrollDirectionListener scrollDirectionListener) {
            mScrollDirectionListener = scrollDirectionListener;
        }

        public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
            mOnScrollListener = onScrollListener;
        }

        @Override
        public void onScrollDown() {
            show();
            if (mScrollDirectionListener != null) {
                mScrollDirectionListener.onScrollDown();
            }
        }

        @Override
        public void onScrollUp() {
            hide();
            if (mScrollDirectionListener != null) {
                mScrollDirectionListener.onScrollUp();
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                             int totalItemCount) {
            if (mOnScrollListener != null) {
                mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }

            super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (mOnScrollListener != null) {
                mOnScrollListener.onScrollStateChanged(view, scrollState);
            }

            super.onScrollStateChanged(view, scrollState);
        }
    }
}
