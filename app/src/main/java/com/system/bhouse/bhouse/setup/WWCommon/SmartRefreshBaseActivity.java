package com.system.bhouse.bhouse.setup.WWCommon;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.system.bhouse.bhouse.R;

/**
 * Created by Administrator on 2018-11-08.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.setup.WWCommon
 */
public abstract class SmartRefreshBaseActivity extends WWBackActivity implements OnRefreshListener,OnLoadMoreListener {

    private SmartRefreshLayout mSmartRefreshLayout;

    protected void initRefreshView(){
        mSmartRefreshLayout = (SmartRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        if (mSmartRefreshLayout!=null) {
            mSmartRefreshLayout.setOnRefreshListener(this);
        }
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
