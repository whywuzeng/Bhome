package com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter;

import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.entity.ExpandableMultiItemEntity;

/**
 * Created by Administrator on 2019-02-19.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter
 */
public class Level1Item extends ExpandableMultiItemEntity<Level2Item> {


    public Level1Item(String name) {
        this.name = name;
    }

    @Override
    public int getLevel() {
        return 2;
    }
}
