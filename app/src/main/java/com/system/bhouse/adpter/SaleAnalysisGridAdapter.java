package com.system.bhouse.adpter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.bhouse.bhouse.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-6-17.
 * ClassName: com.system.bhouse.adpter
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class SaleAnalysisGridAdapter extends
        RecyclerView.Adapter<SaleAnalysisGridAdapter.MyViewHolder> {

    private LayoutInflater mInflater;
    private List<Integer> mDatas;
    private int[] ints={R.drawable.hetongluru,R.drawable.dingdanchaxun, R.drawable.dingdanchaxun,R.drawable.dingdanchaxun};
    //R.drawable.shouhoufenxi, R.drawable.fankuichaxun, R.drawable.shigongrenyuanxinxi
    private int[] ints_pictrue={R.drawable.dingdanguanli,R.drawable.dingdangengxin,R.drawable.dingdanmingxi,R.drawable.shouhouguanli};

/*
    <string name="dingdanguanli">订单管理</string>
    <string name="dingdanzhuangtaigengxin">订单状态更新</string>
    <string name="shouhouguanli">售后管理</string>
    <string name="dingdanmingxiluru">订单明细录入</string>*/

    private List<String> list_name;



    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public SaleAnalysisGridAdapter(Context context, List<Integer> datas)
    {
        this.mDatas=datas;
        mInflater = LayoutInflater.from(context);
        Resources resources = context.getResources();
        list_name=new ArrayList<>();
        list_name.add(resources.getString(R.string.dingdanguanli));
        list_name.add(resources.getString(R.string.dingdanzhuangtaigengxin));
        list_name.add(resources.getString(R.string.shouhouguanli));
        list_name.add(resources.getString(R.string.dingdanmingxiluru));

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(
                R.layout.item_saleanalysis_gridview, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {


        holder.grid_name.setText(list_name.get(position));
        holder.grid_icon.setImageResource(ints_pictrue[position]);

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
//                    removeData(pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return list_name.size();
    }

    public void addData(int position)
    {
        notifyItemInserted(position);
    }

    public void removeData(int position)
    {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView grid_name;
        ImageView grid_icon;

        public MyViewHolder(View view)
        {
            super(view);
            grid_name=(TextView)view.findViewById(R.id.grid_name);
            grid_icon=(ImageView)view.findViewById(R.id.grid_icon);
        }
    }

}

