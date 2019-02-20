package com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Fragment.adpter;

import com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Fragment.Bean.LoadingIntoPickBean;
import com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Fragment.Bean.LoadingIntoPickBeanSection;
import net.qiujuer.italker.common.adapter.BaseSectionQuickAdapter;
import net.qiujuer.italker.common.adapter.BaseViewHolder;
import com.system.bhouse.bhouse.R;

import java.util.List;

/**
 * Created by Administrator on 2018-05-11.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TransportationManagement.LoadingCarOrder
 */

public class LoadingIntoSectionAdapter extends BaseSectionQuickAdapter<LoadingIntoPickBeanSection, BaseViewHolder>{

    public LoadingIntoSectionAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, LoadingIntoPickBeanSection item) {

    }

    @Override
    protected void convert(BaseViewHolder helper, LoadingIntoPickBeanSection item) {
        LoadingIntoPickBean bprobom = (LoadingIntoPickBean) item.t;
        helper.setText(R.id.tv_title,bprobom.getLoadingCarNumber() + "");
        helper.setText(R.id.tv_content,bprobom.getProjectName());
        helper.setText(R.id.tv_title_1,bprobom.getDong()+"栋");
        helper.setText(R.id.tv_content_1,bprobom.getCeng()+"层");
        helper.setText(R.id.tv_title_2,bprobom.getContainerName());
        helper.setText(R.id.tv_content_2,bprobom.getContainerDate());
        helper.setText(R.id.tv_title_3,bprobom.getEntryPeople());
        helper.setText(R.id.tv_content_3,bprobom.getEntryTime());
        helper.addOnClickListener(R.id.main_layout);
    }
}
