package com.system.bhouse.base;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

import com.system.bhouse.utils.MeasureUtil;

/**
 * Created by Administrator on 2018-10-16.
 * <p>
 * by author wz  stickyHead
 * <p>
 * com.system.bhouse.base
 */

public class TimeLineItemTopBottomDecoration extends RecyclerView.ItemDecoration {
    private final Paint mGroutPaint;
    private final TextPaint mTextPaint;
    private final int mLeftMargin;

    interface GroupNameListener {
        String getGroupName(int pos);
    }

    public void setGroupNameListener(GroupNameListener groupNameListener) {
        this.groupNameListener = groupNameListener;
    }

    private GroupNameListener groupNameListener;


    public void setmSpace(int mSpace) {
        this.mSpace = mSpace;
    }

    public String getGroupName(int pos) {
        if (groupNameListener != null) {
            groupNameListener.getGroupName(pos);
        }
        else {
            switch (pos) {
                case 0:
                    return "测试组名0";
                case 1:
                    return "测试组名1";
                case 2:
                    return "测试组名2";
                case 3:
                    return "测试组名3";
                case 4:
                    return "测试组名4";
                case 5:
                    return "测试组名5";
                case 6:
                    return "测试组名6";
                case 7:
                    return "测试组名7";
                case 8:
                    return "测试组名8";
                case 9:
                    return "测试组名9";
                case 10:
                    return "测试组名10";
                case 11:
                    return "测试组名11";
                case 12:
                    return "测试组名12";
                case 13:
                    return "测试组名13";
                case 14:
                    return "测试组名14";
                case 15:
                    return "测试组名15";
                default:
                    return "错误组名";
            }
        }
        return "错误组名";
    }

    /**
     * 判断是否 第一个Item 或者新组的Item
     */
    private boolean isFirstGroup(int pos) {
        if (pos == 0) {
            return true;
        }
        else {
            String pregroupName = getGroupName(pos);
            String currentGroupName = getGroupName(pos + 1);
            return !TextUtils.equals(pregroupName, currentGroupName);
        }
    }

    private int mSpace;

    public void setmGroupHeight(int mGroupHeight) {
        this.mGroupHeight = mGroupHeight;
    }

    private int mGroupHeight;

    public TimeLineItemTopBottomDecoration() {
        mLeftMargin = 5;
        //设置悬浮栏的画笔---mGroutPaint
        mGroutPaint = new Paint();
        mGroutPaint.setColor(Color.BLUE);
        //设置悬浮栏中文本的画笔
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(15);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

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
        if (childAdapterPosition == itemCount - 1 || childAdapterPosition == itemCount - 2) {
            outRect.bottom = mSpace;
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        final int itemCount = state.getItemCount();
        final int childCount = parent.getChildCount();
        final int left = parent.getLeft() + parent.getPaddingLeft();
        final int right = parent.getRight() - parent.getPaddingRight();
        String preGroupName;      //标记上一个item对应的Group
        String currentGroupName = null;       //当前item对应的Group
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            preGroupName = currentGroupName;
            currentGroupName = getGroupName(position);
            //比较 currentGroupName是否一样
            if (currentGroupName == null || TextUtils.equals(currentGroupName, preGroupName))
                continue;
            int viewBottom = view.getBottom();
            float top = Math.max(mGroupHeight, view.getTop());//top 决定当前顶部第一个悬浮Group的位置
            if (position + 1 < itemCount) {
                //获取下个GroupName
                String nextGroupName = getGroupName(position + 1);
                //下一组的第一个View接近头部
                if (!currentGroupName.equals(nextGroupName) && viewBottom < top) {
                    top = viewBottom;
                }
            }
            //根据top绘制group
            c.drawRect(left, top - mGroupHeight, right, top, mGroutPaint);
            Paint.FontMetrics fm = mTextPaint.getFontMetrics();
            //文字竖直居中显示
            float baseLine = top - (mGroupHeight - (fm.bottom - fm.top)) / 2 - fm.bottom;
            c.drawText(currentGroupName, left + mLeftMargin, baseLine, mTextPaint);
        }
    }
}
