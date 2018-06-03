package com.system.bhouse.bhouse.produce.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.produce.bean.ProductionOrderDetail;
import com.system.bhouse.ui.sectioned.Section;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-9-5.
 */

public class OrderDetailSection extends Section {

    private List<ProductionOrderDetail> mProductionOrderDetails = new ArrayList<>();

    public OrderDetailSection(List<ProductionOrderDetail> ProductionOrderDetails) {
        super(R.layout.detail_headview, R.layout.activity_detail_item, R.layout.layout_home_recommend_empty, R.layout.layout_home_recommend_empty);
        this.mProductionOrderDetails = ProductionOrderDetails;

    }

    @Override
    public int getContentItemsTotal() {
        return mProductionOrderDetails.size() ;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        activity_detail_itemViewHolder activity_detail_itemViewHolder = new activity_detail_itemViewHolder(view);
        return activity_detail_itemViewHolder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof activity_detail_itemViewHolder)
        {
            activity_detail_itemViewHolder holder1 = (activity_detail_itemViewHolder) holder;
            holder1.detailItemDanCode.setText(mProductionOrderDetails.get(position).getWuliaoCode()+"");
            holder1.detailItemCailiao.setText(mProductionOrderDetails.get(position).getWuliaoName()+"");
            holder1.detailItemGuige.setText(mProductionOrderDetails.get(position).getGuigexinghao()+"");
            holder1.detailItemJiliang.setText(mProductionOrderDetails.get(position).getJiliangdanwei()+"");
            holder1.detailItemSum.setText(mProductionOrderDetails.get(position).getSum()+"");
        }
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        detail_headviewViewHolder detail_headviewViewHolder = new detail_headviewViewHolder(view);
        return detail_headviewViewHolder;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        if (holder instanceof detail_headviewViewHolder)
        {
            if (mProductionOrderDetails.size()>0) {
                detail_headviewViewHolder holder1 = (detail_headviewViewHolder) holder;
                holder1.detailDanCode.setText(mProductionOrderDetails.get(0).getDanCode() + "");
                holder1.detailPlanstartDate.setText(mProductionOrderDetails.get(0).getPlanstartdate() + "");
                holder1.detailPlancloseDate.setText(mProductionOrderDetails.get(0).getPlanclosedate() + "");
                holder1.detailDanStatus.setText(mProductionOrderDetails.get(0).getDanStatus() + "");
                holder1.detailZhidanpeople.setText(mProductionOrderDetails.get(0).getZhidanpeople() + "");
                holder1.detailShenhepeople.setText(mProductionOrderDetails.get(0).getShenhepeople() + "");
            }

        }

    }

    static class detail_headviewViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.detail_danCode)
        TextView detailDanCode;
        @Bind(R.id.detail_planstartDate)
        TextView detailPlanstartDate;
        @Bind(R.id.detail_plancloseDate)
        TextView detailPlancloseDate;
        @Bind(R.id.detail_danStatus)
        TextView detailDanStatus;
        @Bind(R.id.detail_zhidanpeople)
        TextView detailZhidanpeople;
        @Bind(R.id.detail_shenhepeople)
        TextView detailShenhepeople;

        detail_headviewViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class activity_detail_itemViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.detail_item_danCode)
        TextView detailItemDanCode;
        @Bind(R.id.detail_item_cailiao)
        TextView detailItemCailiao;
        @Bind(R.id.detail_item_guige)
        TextView detailItemGuige;
        @Bind(R.id.detail_item_jiliang)
        TextView detailItemJiliang;
        @Bind(R.id.detail_item_sum)
        TextView detailItemSum;

        activity_detail_itemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
