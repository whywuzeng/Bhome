package com.system.bhouse.bhouse.Workflowlist.adapter.section;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.system.bhouse.bean.SiteSearchTwoWayBean;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.ui.sectioned.Section;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-03-20.
 * ClassName: com.system.bhouse.bhouse.Workflowlist.adapter.section
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class WorkflowSection extends Section {


    private List<SiteSearchTwoWayBean> searchHistroyBeans = new ArrayList<>();

    private List<Boolean> isClicks;

    public WorkflowSection(List<SiteSearchTwoWayBean> searchHistroyBeans) {
        super(R.layout.woriflowsection_head, R.layout.site_search_gv_ll, R.layout.layout_home_recommend_empty, R.layout.layout_home_recommend_empty);
        this.searchHistroyBeans = searchHistroyBeans;

        isClicks=new ArrayList<>();
        for (int i=0;i<searchHistroyBeans.size();i++)
        {
            isClicks.add(false);
        }
    }


    @Override
    public int getContentItemsTotal() {
        return searchHistroyBeans.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        WorkflowViewHolder workflowViewHolder = new WorkflowViewHolder(view);
        return workflowViewHolder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof WorkflowViewHolder)
        {
            final WorkflowViewHolder holder1 = (WorkflowViewHolder) holder;
            holder1.tvNum1.setText(searchHistroyBeans.get(position).SPH);
            holder1.tvNum2.setText(searchHistroyBeans.get(position).numberType);
            holder1.tvNum3.setText(searchHistroyBeans.get(position).DocumentNumber);
            holder1.tvNum4.setText(searchHistroyBeans.get(position).ManagementOrganization);

            if (isClicks.get(position))
            {
                holder1.tvNum1.setBackgroundColor(Color.parseColor("#78909c"));
            }else {
                holder1.tvNum1.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }

            if (this.onItemClickListener!=null)
            {
                holder1.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i=0;i<isClicks.size();i++)
                        {
                            isClicks.set(i,false);
                        }
                        isClicks.set(position, true);

                        onItemClickListener.onItemClick(v,holder1.tvNum1,position);
                    }
                });
            }
        }
    }

    static class WorkflowViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_num_1)
        TextView tvNum1;
        @BindView(R.id.tv_num_2)
        TextView tvNum2;
        @BindView(R.id.tv_num_3)
        TextView tvNum3;
        @BindView(R.id.tv_num_4)
        TextView tvNum4;

        WorkflowViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener=onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view,TextView textView,int position);
    }
}
