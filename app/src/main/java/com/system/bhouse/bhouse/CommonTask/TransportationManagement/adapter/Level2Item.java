package com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter;

import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.entity.ExpandableMultiItemEntity;

/**
 * Created by Administrator on 2019-02-19.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter
 */
public class Level2Item extends ExpandableMultiItemEntity {


    public Level2Item(String name) {
        this.name = name;
    }

    @Override
    public int getLevel() {
        return 3;
    }
}
