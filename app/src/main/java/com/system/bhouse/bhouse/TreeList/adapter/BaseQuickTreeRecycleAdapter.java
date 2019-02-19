package com.system.bhouse.bhouse.TreeList.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019-02-19.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.TreeList.adapter
 */
public abstract class BaseQuickTreeRecycleAdapter extends BaseQuickAdapter{

    protected Context context;

    /**
     * 所有可见的mNodes
     */
    protected List<Node> mNodes =new ArrayList<>();

    protected LayoutInflater mInflater;

    /**
     * 存储所有的Nodes
     */
    protected List<Node> mAllNodes = new ArrayList<>();

    public void setOnTreeNodeClickListener(OnTreeNodeClickListener onTreeNodeClickListener) {
        this.onTreeNodeClickListener = onTreeNodeClickListener;
    }

    private OnTreeNodeClickListener onTreeNodeClickListener;


    public BaseQuickTreeRecycleAdapter(int layoutResId, @Nullable List data ,Context context,List<Node> mNodes,
                                       int defaultExpandLevel,int iconExpand,int iconNoExpand) {
        super(layoutResId, data);

        //成员变量复制

        //node icon 赋值

        /**
         * 对有所有node排序
         */

        /**
         * 过滤出可见node
         */

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //基本父类的操作

        //设置itemview 的padding

        /**
         * 设置节点点击回传
         */

//        onBindViewHolder(node,holder,position);
    }


}
