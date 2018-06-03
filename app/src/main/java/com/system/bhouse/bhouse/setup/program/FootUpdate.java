package com.system.bhouse.bhouse.setup.program;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.base.Global;

import java.lang.reflect.Method;

/**
 * Created by chaochen on 14-10-22.
 * 上拉加载更多
 */
public class FootUpdate {

    View mLayout;
    View mClick;
    View mLoading;

    public FootUpdate() {
    }

    public int getHigh() {
        if (mLayout == null) {
            return 0;
        }

        return mLayout.getHeight();
    }

    public void initToHead(Object listView, LayoutInflater inflater, final LoadMore loadMore) {
        init(listView, inflater, loadMore, "addHeaderView");
    }

    public void init(Object listView, LayoutInflater inflater, final LoadMore loadMore) {
        init(listView, inflater, loadMore, "addFooterView");
    }

    public void initToRecycler(Object listView, LayoutInflater inflater, final LoadMore loadMore) {
        init(listView, inflater, loadMore, null);
    }

    private void init(Object listView, LayoutInflater inflater, final LoadMore loadMore, String callMethod) {
        View v = inflater.inflate(R.layout.listview_foot, null, false);

        // 为了防止触发listview的onListItemClick事件
        mLayout = v.findViewById(R.id.layout);
        mLayout.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {

                                       }
                                   });


                mClick = v.findViewById(R.id.textView);
        mClick.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                loadMore.loadMore();
                showLoading();
            }
        });

        mLoading = v.findViewById(R.id.progressBar);

        if (!TextUtils.isEmpty(callMethod)) {
            try {
                Method method = listView.getClass().getMethod(callMethod, View.class);
                method.invoke(listView, v);
            } catch (Exception e) {
                Global.errorLog(e);
            }
        }

        mLayout.setVisibility(View.GONE);
    }

    public View getView() {
        return mLayout;
    }

    public void showLoading() {
        show(true, true);
    }

    public void showFail() {
        show(true, false);
    }

    public void dismiss() {
        show(false, true);
    }

    private void show(boolean show, boolean loading) {
        if (mLayout == null) {
            return;
        }

        if (show) {
            mLayout.setVisibility(View.VISIBLE);
            mLayout.setPadding(0, 0, 0, 0);
            if (loading) {
                mClick.setVisibility(View.INVISIBLE);
                mLoading.setVisibility(View.VISIBLE);
            } else {
                mClick.setVisibility(View.VISIBLE);
                mLoading.setVisibility(View.INVISIBLE);
            }
        } else {
            mLayout.setVisibility(View.INVISIBLE);
            mLayout.setPadding(0, -mLayout.getHeight(), 0, 0);
        }
    }

    public void updateState(int code, boolean isLastPage, int locatedSize) {
        if (code == 0) {
            if (isLastPage) {
                dismiss();
            } else {
                showLoading();
            }
        } else {
            if (locatedSize > 0) {
                showFail();
            } else {
                dismiss(); // 显示猴子照片
            }
        }
    }

    public interface LoadMore {
        void loadMore();
    }
}
