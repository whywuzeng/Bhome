package com.system.bhouse.bhouse.CommonTask.ProduceManagement.adapter.entity;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public abstract class MultiItemEntity<T>  {

    protected boolean isHeader;
    protected T t;
    protected String headtitle;

    public MultiItemEntity (boolean isHeader,String headtitle){
        this.isHeader=isHeader;
        this.headtitle=headtitle;
    }

    public MultiItemEntity(T item){
        this.isHeader=false;
        this.headtitle=null;
        this.t=item;
    }

   abstract int getItemType();
}
