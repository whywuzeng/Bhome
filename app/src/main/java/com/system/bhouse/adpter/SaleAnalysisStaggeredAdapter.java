package com.system.bhouse.adpter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.system.bhouse.bhouse.R;

import java.util.List;

/**
 * Created by Administrator on 2016-3-23.
 */
public class SaleAnalysisStaggeredAdapter extends
        RecyclerView.Adapter<SaleAnalysisStaggeredAdapter.MyViewHolder> {

    private LayoutInflater mInflater;
    private List<Integer> mDatas;
    private float dimension_top;
    private float dimension_right;
    private float dimension_left;
    private float dimension_bottom;
    private float dimension;
    private int[] ints={R.drawable.hetongluru,R.drawable.dingdanchaxun, R.drawable.dingdanchaxun,R.drawable.dingdanchaxun};
    //R.drawable.shouhoufenxi, R.drawable.fankuichaxun, R.drawable.shigongrenyuanxinxi


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

    public SaleAnalysisStaggeredAdapter(Context context, List<Integer> datas)
    {
        this.mDatas=datas;
        mInflater = LayoutInflater.from(context);
        Resources resources = context.getResources();
         dimension = resources.getDimension(R.dimen.a15x);
        dimension_top = resources.getDimension(R.dimen.a15x);
        dimension_left= resources.getDimension(R.dimen.a15x);
        dimension_right= resources.getDimension(R.dimen.a15x);
        dimension_bottom= resources.getDimension(R.dimen.a15x);

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(
                R.layout.item_saleanalysis_mian, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        ViewGroup.LayoutParams lp = holder.tv.getLayoutParams();
        ViewGroup.LayoutParams layoutParams1=holder.fram.getLayoutParams();
        FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(layoutParams1);
        layoutParams.setMargins((int) dimension_left, (int) dimension_top, (int) dimension_right, (int) dimension_bottom);
        if(position==4) {
            layoutParams.width = (int) dimension;
            ViewGroup.LayoutParams layoutParams12=holder.item_sale_rela.getLayoutParams();
            layoutParams12.width= (int) dimension;
            layoutParams12.height= (int) dimension;
            holder.item_sale_rela.setLayoutParams(layoutParams12);
        }

                holder.tv.setLayoutParams(lp);
        holder.fram.setLayoutParams(layoutParams);

        holder.tv.setText(mDatas.get(position));
        holder.iv.setImageResource(ints[position]);
        holder.fram.setBackgroundResource(R.drawable.item_bg_sale_lan);
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
        return mDatas.size();
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

        TextView tv;
        FrameLayout fram;
        ImageView iv;
        RelativeLayout item_sale_rela;

        public MyViewHolder(View view)
        {
            super(view);
            tv = (TextView) view.findViewById(R.id.id_num);
fram=(FrameLayout)view.findViewById(R.id.saleanalysis_fram);
            iv=(ImageView)view.findViewById(R.id.image_sale_item);
            item_sale_rela=(RelativeLayout)view.findViewById(R.id.item_sale_rela);
        }
    }

}
