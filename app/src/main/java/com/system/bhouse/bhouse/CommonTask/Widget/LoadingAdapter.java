package com.system.bhouse.bhouse.CommonTask.Widget;

import android.support.annotation.IntDef;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.system.bhouse.bhouse.R;

import java.util.List;

/**
 * Created by Administrator on 2018-03-09.
 * <p>
 * by author wz
 * <p>
 * com.baozi.demo.activity.Widget
 */

public abstract class LoadingAdapter<B> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int FOOTER = 10000;
    private static final int NORMAL = 10001;
    private static final int COMPLETE = 10002;
    private static final int EMPTY_VIEW = 10003;

    public static final int STATUS_LOADING = 10;
    public static final int STATUS_LOADED = 11;
    public static final int STATUS_LOADCOMPLETE = 12;
//    public static final int STATUS_LOADED = 11;

    private boolean mIsUseEmpty = true;

    private FrameLayout mEmptyLayout;

    @IntDef({STATUS_LOADING, STATUS_LOADED, STATUS_LOADCOMPLETE})
    public @interface Status {
    }

    private int mStatus = STATUS_LOADED;

    private List<B> mDataList;

    public List<B> getDataList() {
        return mDataList;
    }

    public void setDataList(List<B> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }

    public void setStatus(@Status int status) {
        mStatus = status;
        notifyDataSetChanged();
    }

    public int getStatus()
    {
        return mStatus;
    }

    public int getEmptyViewCount() {
        if (mEmptyLayout == null || mEmptyLayout.getChildCount() == 0) {
            return 0;
        }
        if (!mIsUseEmpty) {
            return 0;
        }
        if (mDataList.size() != 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER) {
            if (mStatus == STATUS_LOADING) {
                return new LoadingViewHolder(LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.common_load_more, parent, false));
            }
        }
        else if (viewType == COMPLETE) {
            if (mStatus == STATUS_LOADCOMPLETE) {
                return new CompeleteViewHolder(LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.common_load_more_complete, parent, false));
            }
        }
        else if (viewType == EMPTY_VIEW) {
            return new EmptyViewHolder(mEmptyLayout);
        }
        return onCreateHolder(parent, viewType);
    }

    protected abstract RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoadingViewHolder || holder instanceof CompeleteViewHolder || holder instanceof EmptyViewHolder)
            return;
        onBindHolder(holder, position);
    }

    protected abstract void onBindHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemViewType(int position) {
        if (getEmptyViewCount() == 1) {
            return EMPTY_VIEW;
        }
        if ((position == getItemCount() - 1 && mStatus == STATUS_LOADING)) {
            return FOOTER;
        }
        else if ((position == getItemCount() - 1 && mStatus == STATUS_LOADCOMPLETE)) {
            return COMPLETE;
        }
        else
            return NORMAL;
    }


    @Override
    public int getItemCount() {
        int count;
        if (getEmptyViewCount() == 1) {
            count = 1;
            return count;
        }
        return mDataList == null ? 0 : mDataList.size() + (mStatus == STATUS_LOADING ? 1 : 0) + (mStatus == STATUS_LOADCOMPLETE ? 1 : 0);
    }

    public void setEmptyView(View emptyView) {
        boolean insert = false;
        if (mEmptyLayout == null) {
            mEmptyLayout = new FrameLayout(emptyView.getContext());
            final RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
            final ViewGroup.LayoutParams lp = emptyView.getLayoutParams();
            if (lp != null) {
                layoutParams.width = lp.width;
                layoutParams.height = lp.height;
            }
            mEmptyLayout.setLayoutParams(layoutParams);
            insert = true;
        }
        mEmptyLayout.removeAllViews();
        mEmptyLayout.addView(emptyView);
        mIsUseEmpty = true;
        if (insert) {
            if (getEmptyViewCount() == 1) {
                int position = 0;
                notifyItemInserted(position);
            }
        }
    }

    //解决GridLayoutManager 占用问题
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return isFooter(position) ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    private boolean isFooter(int position) {
        return (mStatus == STATUS_LOADING && position == getItemCount()) || (mStatus == STATUS_LOADCOMPLETE && position == getItemCount());
    }

    //处理StaggeredGridLayoutManager 占用问题
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isStaggeredGridLayout(holder)) {
            handleLayoutIfStaggeredGridLayout(holder, holder.getLayoutPosition());
        }
    }

    private boolean isStaggeredGridLayout(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        return layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams;
    }

    private void handleLayoutIfStaggeredGridLayout(RecyclerView.ViewHolder holder, int position) {
        if (/*isHeader(position) ||*/ isFooter(position)) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            p.setFullSpan(true);//设置满span
        }
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class CompeleteViewHolder extends RecyclerView.ViewHolder {

        public CompeleteViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
