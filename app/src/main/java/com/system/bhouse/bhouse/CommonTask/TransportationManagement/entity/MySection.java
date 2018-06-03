package com.system.bhouse.bhouse.CommonTask.TransportationManagement.entity;


import com.system.bhouse.bean.BProBOM;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.entity.SectionEntity;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class MySection extends SectionEntity<BProBOM> {
    private boolean isMore;
    public MySection(boolean isHeader, String header, boolean isMroe) {
        super(isHeader, header);
        this.isMore = isMroe;
    }

    public MySection(BProBOM t) {
        super(t);
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean mroe) {
        isMore = mroe;
    }
}
