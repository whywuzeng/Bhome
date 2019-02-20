package com.system.bhouse.bhouse.setup.notification.adapter;

import net.qiujuer.italker.common.adapter.BaseViewHolder;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.notification.bean.XGNotification;
import com.system.bhouse.bhouse.setup.notification.bean.XGNotificationSectionEntity;

import java.util.List;

/**
 * Created by Administrator on 2018-05-11.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TransportationManagement.LoadingCarOrder
 */

public class NotificationSectionAdapter extends CommonBaseSectionQuickAdapter<XGNotificationSectionEntity, BaseViewHolder>{

    public NotificationSectionAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, XGNotificationSectionEntity item) {
//        if (item.isMore()){
//            helper.addOnClickListener(R.id.rl_dealWith);
//        }
    }

    @Override
    protected void convert(BaseViewHolder helper, XGNotificationSectionEntity item) {
        XGNotification bprobom = (XGNotification) item.t;
//        helper.setText(R.id.tv_fromname,bprobom);
        int layoutPosition = helper.getLayoutPosition();
        helper.setVisible(R.id.iv_redpoint_item,bprobom.isNews());
        helper.setText(R.id.tv_item_time,bprobom.getUpdate_time());
        helper.setText(R.id.tv_content_item,bprobom.getContent());
        helper.setText(R.id.tv_from_item,bprobom.getTitle());
        helper.addOnClickListener(R.id.iv_three_dot);
    }
}
