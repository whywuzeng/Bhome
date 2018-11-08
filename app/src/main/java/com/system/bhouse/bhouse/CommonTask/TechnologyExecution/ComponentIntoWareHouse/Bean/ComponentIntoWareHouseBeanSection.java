package com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ComponentIntoWareHouse.Bean;

import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ModuleAssignMent.Bean.HeaderAndFooterSectionEntity;

/**
 * Created by Administrator on 2018-07-25.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ModuleAssignMent.Bean
 */

public class ComponentIntoWareHouseBeanSection extends HeaderAndFooterSectionEntity<ComponentIntoWareHouseBean> {

    private boolean isMore;

    public ComponentIntoWareHouseBeanSection(boolean isHeader, String header , boolean isMore, boolean isFooter , String footer) {
        super(isHeader, header,isFooter,footer);
        this.isMore=isMore;
    }

    public ComponentIntoWareHouseBeanSection(ComponentIntoWareHouseBean item)
    {
        super(item);
        /**
         * delete禁止显示
         */
        this.t.setDisableDelete(true);
    }

    public void setMore(boolean isMore){
        this.isMore=isMore;
    }

    public boolean getMore()
    {
        return isMore;
    }
}
