package net.qiujuer.italker.push.adapter;

/**
 * Created by Administrator on 2019-02-19.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter
 */
public class Level0Item extends ExpandableMultiItemEntity<Level1Item>{


    public Level0Item(String name) {
        this.name = name;
    }

    @Override
    public int getLevel() {
        return 1;
    }
}
