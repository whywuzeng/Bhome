package com.system.bhouse.bhouse.setup.notification.adapter;

import android.util.Log;

import com.system.bhouse.bean.BProBOM;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseViewHolder;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.entity.MySection;

import java.util.List;

/**
 * Created by Administrator on 2018-05-11.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TransportationManagement.LoadingCarOrder
 */

public class NotificationSectionAdapter extends CommonBaseSectionQuickAdapter<MySection, BaseViewHolder>{

    public NotificationSectionAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, MySection item) {

    }

    @Override
    protected void convert(BaseViewHolder helper, MySection item) {
        BProBOM bprobom = (BProBOM) item.t;
//        helper.setText(R.id.tv_fromname,bprobom);
        int layoutPosition = helper.getLayoutPosition();
        Log.e(TAG,layoutPosition+"");
    }
}
