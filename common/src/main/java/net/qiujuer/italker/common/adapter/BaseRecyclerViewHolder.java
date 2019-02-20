package net.qiujuer.italker.common.adapter;

import android.view.View;

import butterknife.Unbinder;

/**
 * Created by Administrator on 2019-02-20.
 * <p>
 * by author wz
 * 封装了 biter黄牛刀组件
 * <p>
 * net.qiujuer.italker.common.adapter
 */
public abstract class BaseRecyclerViewHolder<Data> extends BaseViewHolder{

    public Unbinder unbinder;
    public QuickAdapterCallback<Data> callback;
    protected Data mData;


    public BaseRecyclerViewHolder(View view) {
        super(view);
    }

    /**
     * 用于绑定数据的触发
     *
     * @param data 绑定的数据
     */
    void bind(Data data) {
        this.mData = data;
        onBind(data);
    }

    /**
     * 当触发绑定数据的时候，的回掉；必须复写
     *
     * @param data 绑定的数据
     */
    protected abstract void onBind(Data data);

    /**
     * Holder自己对自己对应的Data进行更新操作
     *
     * @param data Data数据
     */
    public void updateData(Data data) {
        if (this.callback != null) {
            this.callback.update(data, this);
        }
    }

}
