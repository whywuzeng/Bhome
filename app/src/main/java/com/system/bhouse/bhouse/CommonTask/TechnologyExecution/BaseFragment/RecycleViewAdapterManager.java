package com.system.bhouse.bhouse.CommonTask.TechnologyExecution.BaseFragment;


import android.support.v7.widget.LinearLayoutManager;

import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseQuickAdapter;
import com.system.bhouse.bhouse.R;

/**
 * Created by Administrator on 2018-08-03.
 * <p>
 * by author wz
 * 管理侧滑删除
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution.BaseFragment
 */
public class RecycleViewAdapterManager {
    private BaseQuickAdapter adapter;
    private LinearLayoutManager manager;

    public RecycleViewAdapterManager(BaseQuickAdapter adapter,LinearLayoutManager manager) {
        this.adapter = adapter;
        this.manager=manager;
    }

    public void setItemMultiClose(int position) {
        int itemCount = adapter.getItemCount();
        SwipeItemLayout openitem = (SwipeItemLayout) adapter.getViewByPosition(position, R.id.swipe_layout);
        for (int i = 0; i < itemCount; i++) {

            SwipeItemLayout viewByPosition = (SwipeItemLayout) adapter.getViewByPosition(i, R.id.swipe_layout);
            if (openitem.isOpen() && i != position  && viewByPosition.isOpen()) {
                viewByPosition.close();
            }
        }
    }

    public void setItemMultiViewClose(int position){
        int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();
        int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
        SwipeItemLayout openitem = (SwipeItemLayout) adapter.getViewByPosition(position, R.id.swipe_layout);

        for (int i = firstVisibleItemPosition; i <= lastVisibleItemPosition; i++)
        {
            SwipeItemLayout viewByPosition = (SwipeItemLayout) adapter.getViewByPosition(i, R.id.swipe_layout);
            if (openitem.isOpen() && i != position  && viewByPosition.isOpen()) {
                viewByPosition.close();
            }
        }
    }
}
