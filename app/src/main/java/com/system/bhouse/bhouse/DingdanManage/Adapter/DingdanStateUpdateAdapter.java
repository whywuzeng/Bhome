package com.system.bhouse.bhouse.DingdanManage.Adapter;

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
 * Created by Administrator on 2016-6-28.
 * ClassName: com.system.bhouse.bhouse.DingdanManage.Adapter
 * Author:Administrator
 * Fuction: 订单 状态管理的九宫格适配器
 * UpdateUser:
 * UpdateDate:
 */
public class DingdanStateUpdateAdapter extends RecyclerView.Adapter<DingdanStateUpdateAdapter.MyViewHolder> {

    private LayoutInflater mInflater;
    private List<Integer> mDatas;
    private int[] ints={R.drawable.hetongluru,R.drawable.dingdanchaxun, R.drawable.dingdanchaxun,R.drawable.dingdanchaxun};
    //R.drawable.shouhoufenxi, R.drawable.fankuichaxun, R.drawable.shigongrenyuanxinxi
//    private int[] ints_pictrue={R.drawable.dingdanguanli,R.drawable.dingdangengxin,R.drawable.dingdanmingxi,R.drawable.shouhouguanli};

    private int[] ints_pictrue={R.mipmap.card_item_yjdz,R.mipmap.card_item_yjdz,R.mipmap.intergral_popwind_down,R.mipmap.menu_make_card,R.mipmap.menu_market,
    R.mipmap.my_chit,R.mipmap.my_app
    };
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

/*    <string name="jichuyanshouqueren">基础验收确认</string>
    <string name="dingdanfahuo">订单发货</string>
    <string name="dingdananzhuang">订单安装</string>
    <string name="yanshoushengqiu">验收申请</string>
    <string name="dingdanyanshou">订单验收</string>
    <string name="yaoshishangjiao">钥匙上交</string>*/

    public DingdanStateUpdateAdapter(Context context, List<Integer> datas)
    {
        this.mDatas=datas;
        mInflater = LayoutInflater.from(context);
        Resources resources = context.getResources();
        list_name=new ArrayList<>();

        list_name.add(resources.getString(R.string.gongchangpaichan));
        list_name.add(resources.getString(R.string.jichuyanshouqueren));
        list_name.add(resources.getString(R.string.dingdanfahuo));
        list_name.add(resources.getString(R.string.dingdananzhuang));
        list_name.add(resources.getString(R.string.yanshoushengqiu));
        list_name.add(resources.getString(R.string.dingdanyanshou));
        list_name.add(resources.getString(R.string.yaoshishangjiao));

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(
                R.layout.dingdanzhuangtaigengxin, parent, false));
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

