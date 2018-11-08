package com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.item;

import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.base.ViewHolder;
import com.system.bhouse.bhouse.R;

/**
 * Created by baozi on 2017/8/19.
 */

public class SortChildItem extends TreeItem<SortChildItem.ViewModel> {
    public final static String NoQRcode="NoQRcode";

    @Override
    protected int initLayoutId() {
        return R.layout.recycleview_comtask_top_item;
    }

    @Override
    public void onClick(ViewHolder viewHolder) {
        super.onClick(viewHolder);

        if (mOnItemClickListener!=null)
        {
            mOnItemClickListener.onItemClick(getData(),viewHolder);
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder) {
        viewHolder.setText(R.id.tv_task_done1,getData().name);
        viewHolder.setText(R.id.tv_task_done,getData().value);
        if ((getData().name.equals("车次")&&!getData().key.endsWith(NoQRcode))||(getData().name.equals("货柜")&&!getData().key.endsWith(NoQRcode))||getData().isQrcodeBtn)
        viewHolder.setImageResource(R.id.iv_right_arrow,R.drawable.ic_eqcode_grey);
        viewHolder.setVisible(R.id.iv_right_arrow,getData().isClick);
        viewHolder.setEnable(R.id.main_layout_ll,getData().isClick);
    }

    public static class ViewModel
    {
      public  String name;
      public  String value;
      public  boolean isClick=false;
      public  boolean isQrcodeBtn=false;
      public  String key;
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(ViewModel data,ViewHolder holder);
    }
}
