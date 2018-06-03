package com.system.bhouse.bhouse.setup.notification.adapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.system.bhouse.base.App;
import com.system.bhouse.utils.MeasureUtil;

/**
 * Created by Administrator on 2018-05-30.
 * <p>
 * by author wz
 * ItemDecoration类中仅有3个方法，具体如下：
 * <p>
 * com.system.bhouse.bhouse.setup.notification.adapter
 */

public class MyDividerItemDecoration  extends RecyclerView.ItemDecoration{

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
        int topdip = MeasureUtil.px2dip(App.getContextApp(), 13);
        outRect.set(0,topdip,0,topdip);

    }
}
