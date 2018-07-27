package com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ModuleAssignMent;

import android.view.ViewGroup;
import android.widget.Filter;

import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ModuleAssignMent.Bean.HeaderAndFooterSectionEntity;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public abstract class HeaderAndFooterSectionQuickAdapter<T extends HeaderAndFooterSectionEntity, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> implements android.widget.Filterable{


    protected int mSectionHeadResId;
    protected static final int SECTION_HEADER_VIEW = 0x00000444;
    protected int mSectionFooterResId;
    protected static final int SECTION_FOOTER_VIEW= 0x00000333;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param sectionHeadResId The section head layout id for each item
     * @param layoutResId      The layout resource id of each item.
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public HeaderAndFooterSectionQuickAdapter(int layoutResId, int sectionHeadResId,int sectionFooterResId, List<T> data) {
        super(layoutResId, data);
        this.mSectionHeadResId = sectionHeadResId;
        this.mSectionFooterResId=sectionFooterResId;
    }

    @Override
    protected int getDefItemViewType(int position) {
        if (mData.get(position).isHeader)
        {
            return SECTION_HEADER_VIEW;
        }else if (mData.get(position).isFooter)
        {
            return SECTION_FOOTER_VIEW;
        }else {
            return 0;
        }
    }

    @Override
    protected K onCreateDefViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SECTION_HEADER_VIEW) {
            return createBaseViewHolder(getItemView(mSectionHeadResId, parent));
        }else if (viewType == SECTION_FOOTER_VIEW)
        {
            return createBaseViewHolder(getItemView(mSectionFooterResId,parent));
        }

        return super.onCreateDefViewHolder(parent, viewType);
    }

    @Override
    protected boolean isFixedViewType(int type) {
        return super.isFixedViewType(type) || type == SECTION_HEADER_VIEW ||type ==SECTION_FOOTER_VIEW;
    }

    @Override
    public void onBindViewHolder(K holder, int position) {
        switch (holder.getItemViewType()) {
            case SECTION_HEADER_VIEW:
                setFullSpan(holder);
                convertHead(holder, getItem(position - getHeaderLayoutCount()));
                break;
            case SECTION_FOOTER_VIEW:
                setFullSpan(holder);
                converFooter(holder,getItem(position -getHeaderLayoutCount() -getFooterLayoutCount()));
            default:
                super.onBindViewHolder(holder, position);
                break;
        }
    }

    protected abstract void convertHead(K helper, T item);

    protected abstract void converFooter(K helper , T item);

    private ArrayList<T> copyBProBOMs= new ArrayList<>();

    public void setFriends(ArrayList<T> data) {
        //复制数据
        this.copyBProBOMs.addAll(data);
        mData = data;
        this.notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                //初始化过滤结果对象
                FilterResults results = new FilterResults();
                //假如搜索为空的时候，将复制的数据添加到原始数据，用于继续过滤操作
                if (results.values == null) {
                    mData.clear();
                    mData.addAll(copyBProBOMs);
                }
                //关键字为空的时候，搜索结果为复制的结果
                if (constraint == null || constraint.length() == 0) {
                    results.values = copyBProBOMs;
                    results.count = copyBProBOMs.size();
                }
                else {
                    String prefixString = constraint.toString();
                    final int count = mData.size();
                    //用于存放暂时的过滤结果
                    final ArrayList<T> newValues = new ArrayList<T>();
                    for (int i = 0; i < count; i++) {
                        final T value = mData.get(i);
                       String username= mRefreshListener.getIsFilter(prefixString, value);
                        if (username.contains(prefixString)) {
                            newValues.add(value);
                        }
                        else {
                            //过来空字符开头
                            final String[] words = username.split(" ");
                            final int wordCount = words.length;

                            // Start at index 0, in case valueText starts with space(s)
                            for (int k = 0; k < wordCount; k++) {
                                if (words[k].contains(prefixString)) {
                                    newValues.add(value);
                                    break;
                                }
                            }
                        }
                    }
                    results.values = newValues;
                    results.count = newValues.size();
                }
                return results;//过滤结果
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mData.clear();//清除原始数据
                mData.addAll((ArrayList<T>) results.values);//将过滤结果添加到这个对象
                if (results.count > 0) {
                    mRefreshListener.onAdapterRefresh();//有关键字的时候刷新数据
                }
                else {
                    //关键字不为零但是过滤结果为空刷新数据
                    if (constraint.length() != 0) {
                        mRefreshListener.onAdapterRefresh();
                        return;
                    }
                    //加载复制的数据，即为最初的数据
                    //复制数据
                    copyBProBOMs.addAll(copyBProBOMs);
                    mData = copyBProBOMs;
                    mRefreshListener.onAdapterRefresh();
                }
            }
        };

    }

    public void setmRefreshListener(FilterRefreshListener mRefreshListener) {
        this.mRefreshListener = mRefreshListener;
    }

    private FilterRefreshListener mRefreshListener;

    public interface FilterRefreshListener<T>{
        //是否包含 prefixString
        public String getIsFilter(String prefixString, T value);
        //更新数据位置
        public void onAdapterRefresh();
    }

}
