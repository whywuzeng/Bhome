package net.qiujuer.italker.common.adapter;

/**
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public interface QuickAdapterCallback<Data> {
    void update(Data data, BaseRecyclerViewHolder<Data> holder);
}
