package com.system.bhouse.bhouse.CommonTask.MaterialControl.PlateMaterial.StationCarBean;

import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.entity.SectionEntity;

/**
 * Created by Administrator on 2018-07-03.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.MaterialControl.PlateMaterial.StationCarBean
 */

public class MyStation extends SectionEntity<StationCarBean> {
    private boolean isMore;
    public MyStation(boolean isHeader, String header, boolean isMroe) {
        super(isHeader, header);
        this.isMore = isMroe;
    }

    public MyStation(StationCarBean t) {
        super(t);
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean mroe) {
        isMore = mroe;
    }
}

