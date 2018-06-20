package com.system.bhouse.bhouse.CommonTask.ProduceManagement.adapter;

import android.content.Context;

import com.system.bhouse.bhouse.CommonTask.ProduceManagement.adapter.entity.MultipleItem;
import com.system.bhouse.bhouse.CommonTask.ProduceManagement.adapter.entity.ProduceItemDataBean;
import com.system.bhouse.bhouse.CommonTask.ProduceManagement.adapter.entity.SectionMultipleItem;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseViewHolder;
import com.system.bhouse.bhouse.R;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * modify by AllenCoder
 */
public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<SectionMultipleItem, BaseViewHolder> {

    public MultipleItemQuickAdapter(Context context, List data) {
        super(data);
        addItemType(MultipleItem.TEXT, R.layout.produce_item_text_view);
        addItemType(MultipleItem.IMG, R.layout.produce_item_image_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, SectionMultipleItem item) {
        switch (helper.getItemViewType()) {
            case MultipleItem.TEXT:
                helper.setText(R.id.tv_qiye,item.headtitle);
                break;
            case MultipleItem.IMG:
                helper.setImageResource(R.id.iv,((ProduceItemDataBean)item.t).resId);
                helper.setText(R.id.tv_subtext,((ProduceItemDataBean) item.t).subtext);
                helper.addOnClickListener(R.id.iv);
                break;
        }
    }

}
