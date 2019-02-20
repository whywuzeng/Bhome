package net.qiujuer.italker.push.adapter;


import net.qiujuer.italker.common.adapter.entity.AbstractExpandableItem;
import net.qiujuer.italker.common.adapter.entity.MultiItemEntity;

/**
 * Created by Administrator on 2019-02-19.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.entity
 */
public abstract class ExpandableMultiItemEntity<T> extends AbstractExpandableItem<T> implements MultiItemEntity {

    public String name;

    @Override
    public int getItemType() {
        return TreeItemAdapter.TYPE_LEVEL_0;
    }
}
