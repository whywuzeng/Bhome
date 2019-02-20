package net.qiujuer.italker.common.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019-02-20.
 * <p>
 * by author wz
 * 加了回调功能
 * <p>
 * net.qiujuer.italker.common.adapter
 */
public abstract class BaseRecyclerQuickAdapter<Data> extends BaseQuickAdapter<Data,BaseRecyclerViewHolder<Data>> implements QuickAdapterCallback<Data> {

    public BaseRecyclerQuickAdapter(int layoutResId, @Nullable List<Data> data) {
        super(layoutResId, data);
    }

    public BaseRecyclerQuickAdapter(@Nullable List<Data> data) {
        super(data);
    }

    public BaseRecyclerQuickAdapter(int layoutResId) {
        super(layoutResId);
    }

    /**
     * 专门为了绑定 butter
     * @param baseViewHolder
     * @param viewType
     */
    @Override
    protected void onButterKnifeBind(BaseRecyclerViewHolder<Data> baseViewHolder, int viewType) {
        int layoutId = mLayoutResId;
        if (getMultiTypeDelegate() != null) {
            layoutId = getMultiTypeDelegate().getLayoutId(viewType);
        }
        View root = baseViewHolder.getView(layoutId);
        baseViewHolder.unbinder = ButterKnife.bind(baseViewHolder, root);
        // 绑定callback
        baseViewHolder.callback = this;
    }

    @Override
    protected void convert(BaseRecyclerViewHolder<Data> holder, Data item) {
        holder.bind(item);
    }

    @Override
    public void update(Data data, BaseRecyclerViewHolder<Data> holder) {

        // 得到当前ViewHolder的坐标
        int pos = holder.getAdapterPosition();
        if (pos >= 0) {
            // 进行数据的移除与更新
            mData.remove(pos);
            mData.add(pos, data);
            // 通知这个坐标下的数据有更新
            notifyItemChanged(pos);
        }
    }


}
