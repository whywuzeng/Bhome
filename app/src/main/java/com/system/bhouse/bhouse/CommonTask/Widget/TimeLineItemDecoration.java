package com.system.bhouse.bhouse.CommonTask.Widget;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.system.bhouse.base.App;
import com.system.bhouse.utils.MeasureUtil;

/**
 * Created by Administrator on 2018-06-12.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.Widget
 */

public class TimeLineItemDecoration extends RecyclerView.ItemDecoration{
    //方法2 onDraw
//    作用:在指示图上OnDraw一样
//    绘制的图层在Itemview一下，会与ItemView重叠，并被ItemView覆盖
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    //方法1 getItemoffsets
    //作用 设置itemView Outrect 内置偏移长度 inset
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        // 参数说明：
        // 1. outRect：全为 0 的 Rect（包括着Item）
        // 2. view：RecyclerView 中的 视图Item
        // 3. parent：RecyclerView 本身
        // 4. state：状态

        // 4个参数分别对应左（Left）、上（Top）、右（Right）、下（Bottom）
        // 上述语句代表：左&下偏移长度=50px，右 & 上 偏移长度 = 0
        int bottomdip = MeasureUtil.px2dip(App.getContextApp(), 16);
        int topdip = MeasureUtil.px2dip(App.getContextApp(), 40);
        int rightdip = MeasureUtil.px2dip(App.getContextApp(), 50);
        int leftdip = MeasureUtil.px2dip(App.getContextApp(), 117);

        outRect.set(leftdip,topdip,rightdip,bottomdip);
    }
}
