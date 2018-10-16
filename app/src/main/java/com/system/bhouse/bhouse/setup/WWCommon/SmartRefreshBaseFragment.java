package com.system.bhouse.bhouse.setup.WWCommon;

import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.system.bhouse.bhouse.R;

/**
 * Created by Administrator on 2018-10-16.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.setup.WWCommon
 */

public abstract class SmartRefreshBaseFragment extends WWBaseFragment implements OnRefreshListener,OnLoadMoreListener {

    private SmartRefreshLayout mSmartRefreshLayout;

    protected void initRefreshView(){
        final View rootView = getView();
        if (rootView==null)
        {
            return;
        }
        mSmartRefreshLayout = (SmartRefreshLayout)rootView.findViewById(R.id.swipeRefreshLayout);
        mSmartRefreshLayout.setOnRefreshListener(this);

    }

    protected final void stopRefresh() {
        mSmartRefreshLayout.finishRefresh();
    }

    protected final void startRefresh(){
        mSmartRefreshLayout.autoRefresh();
    }

    protected final void disableRefreshing() {
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.setEnableRefresh(false);
        }
    }

    protected final void enableSwipeRefresh(boolean enable) {
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.setEnableRefresh(enable);
        }
    }

}
