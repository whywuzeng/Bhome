package net.qiujuer.italker.push.adapter;

import android.view.View;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.italker.common.adapter.BaseMultiItemQuickAdapter;
import net.qiujuer.italker.common.adapter.BaseRecyclerViewHolder;
import net.qiujuer.italker.push.R;

import java.util.List;

/**
 * Created by Administrator on 2019-02-19.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter
 */
public class TreeItemAdapter<T extends ExpandableMultiItemEntity , K extends BaseRecyclerViewHolder<T>> extends BaseMultiItemQuickAdapter<T,K> {

    public static final int TYPE_LEVEL_0=0x254;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public TreeItemAdapter(List<T> data) {
        super(data);
    }

    @Override
    protected void convert(final BaseRecyclerViewHolder<T> holder, final T item) {
        super.convert(holder, item);
        if (holder.getItemViewType() == TYPE_LEVEL_0) {
            holder.itemView.setPadding((int) (item.getLevel() * Ui.dipToPx(mContext.getResources(), 15)), 3, 3, 3);

            holder.setText(R.id.id_treenode_label, item.name);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getAdapterPosition();
                    if (item.isExpanded()) {
                        collapse(pos);
                    }
                    else {
                        expand(pos);
                    }
                }
            });
        }
    }

}
