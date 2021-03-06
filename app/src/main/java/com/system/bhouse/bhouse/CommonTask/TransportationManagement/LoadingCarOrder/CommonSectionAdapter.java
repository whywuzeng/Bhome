package com.system.bhouse.bhouse.CommonTask.TransportationManagement.LoadingCarOrder;

import android.text.TextUtils;

import com.system.bhouse.bean.BProBOM;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseSectionQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseViewHolder;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.entity.MySection;
import com.system.bhouse.bhouse.R;

import java.util.List;

/**
 * Created by Administrator on 2018-05-11.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TransportationManagement.LoadingCarOrder
 */

public class CommonSectionAdapter extends BaseSectionQuickAdapter<MySection, BaseViewHolder>{

    public CommonSectionAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, MySection item) {

    }

    @Override
    protected void convert(BaseViewHolder helper, MySection item) {
        BProBOM bprobom = (BProBOM) item.t;
        helper.setText(R.id.tv_title,bprobom.getCoding() + "");
        helper.setText(R.id.tv_content, TextUtils.isEmpty(bprobom.getProjectname())?bprobom.getProjectming():bprobom.getProjectname());
        helper.setText(R.id.tv_title_1,bprobom.getDong()+"栋");
        helper.setText(R.id.tv_content_1,bprobom.getCeng()+"层");
        helper.addOnClickListener(R.id.main_layout);
    }
}
