package com.system.bhouse.bhouse.CommonTask.Widget;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;

import com.system.bhouse.base.App;
import com.system.bhouse.utils.MeasureUtil;

/**
 * Created by Administrator on 2018-06-15.
 * <p>
 * by author wz
 * 底 和 高加距离的 Decoration
 * <p>
 * com.system.bhouse.bhouse.CommonTask.Widget
 */

public class TimeLineItemTopBottomDecoration extends ItemDecoration{

    public void setmSpace(int mSpace) {
        this.mSpace = mSpace;
    }

    private int mSpace;
    public TimeLineItemTopBottomDecoration(){

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mSpace <= 0) {
            mSpace = MeasureUtil.dip2px(App.getContextApp(), 30);
        }
        int childAdapterPosition = parent.getChildAdapterPosition(view);

        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        int itemCount = layoutManager.getItemCount();

        if (childAdapterPosition == 0) {
//            outRect.top = mSpace;
        }
        if (childAdapterPosition == itemCount - 1) {
            outRect.bottom = mSpace;
        }
    }
}
