package com.system.bhouse.bhouse.phone.activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.system.bhouse.base.TimeLineItemTopBottomDecoration;
import com.system.bhouse.bhouse.CommonTask.ProduceManagement.adapter.BaseMultiItemQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.ProduceManagement.adapter.entity.MultipleItem;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseViewHolder;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.phone.activity.bean.ScanBean;
import com.system.bhouse.bhouse.phone.activity.bean.ScanSectionMultipleiItem;

import java.util.List;

/**
 * Created by Administrator on 2018-10-16.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.phone.activity.adapter
 */

public class ScanMultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<ScanSectionMultipleiItem,BaseViewHolder> implements BaseQuickAdapter.SpanSizeLookup,TimeLineItemTopBottomDecoration.GroupNameListener {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ScanMultipleItemQuickAdapter(List<ScanSectionMultipleiItem> data) {
        super(data);
        addItemType(MultipleItem.TEXT, R.layout.produce_item_text_view);
        addItemType(MultipleItem.IMG,R.layout.item_scanresult_layout);
        setSpanSizeLookup(this);
    }

    @Override
    protected void convert(BaseViewHolder helper, ScanSectionMultipleiItem item) {
        switch (helper.getItemViewType())
        {
            case MultipleItem.TEXT:
                helper.setText(R.id.tv_qiye,item.headtitle);
                break;
            case MultipleItem.IMG:
                final ScanBean t = (ScanBean) item.t;
                helper.setText(R.id.title,t.text);
                helper.setText(R.id.content,t.projectname);
                helper.setText(R.id.time,t.time);
                helper.setText(R.id.name,t.name);
                break;
                default:
                break;
        }
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return mData.get(position).getSpanSize();
    }

    private String tempTitle =null;
    @Override
    public String getGroupName(int pos) {
        if (pos<getData().size()) {
            final ScanSectionMultipleiItem item = getData().get(pos);
            final ScanBean bean = (ScanBean) item.t;
            tempTitle = bean.groupString;
        }
        return tempTitle;
    }
    private View mInflate;
    @Override
    public View getGroupView(int pos) {

        if (pos<getData().size()) {
            mInflate=getGroupView();
        }
        return mInflate;
    }

    @NonNull
    private View getGroupView() {
        final View inflate = mLayoutInflater.inflate(R.layout.produce_item_text_view, null, false);
        ((TextView)inflate.findViewById(R.id.tv_qiye)).setText(tempTitle);
        return inflate;
    }

}
