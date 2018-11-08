package com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Fragment.Bean;

import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.entity.SectionEntity;

/**
 * Created by Administrator on 2018-08-22.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouse.Bean
 */

public class LoadingIntoPickBeanSection  extends SectionEntity<LoadingIntoPickBean>{

    private boolean isMore;
    public LoadingIntoPickBeanSection(boolean isHeader, String header,boolean isMroe) {
        super(isHeader, header);
        this.isMore=isMroe;
    }

    public LoadingIntoPickBeanSection(LoadingIntoPickBean loadingIntoPickBean) {
        super(loadingIntoPickBean);
    }
}
