package com.system.bhouse.bhouse.CommonTask.ProduceManagement.adapter.entity;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class MultipleItem<T> extends MultiItemEntity {
    public static final int TEXT = 1;
    public static final int IMG = 2;
    public static final int IMG_TEXT = 3;

    public static final int TEXT_SPAN_SIZE = 3;
    public static final int IMG_SPAN_SIZE = 1;
    public static final int IMG_TEXT_SPAN_SIZE = 4;
    public static final int IMG_TEXT_SPAN_SIZE_MIN = 2;
    private int itemType;
    private int spanSize;

    public MultipleItem(T t, int itemType, int spanSize, String content) {
        super(t);
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.content = content;
    }

    public MultipleItem(T t,int itemType, int spanSize) {
        super(t);
        this.itemType = itemType;
        this.spanSize = spanSize;
    }

    public MultipleItem (boolean isHeader,String headtitle){
        super(isHeader,headtitle);
        this.itemType = MultipleItem.TEXT;
        this.spanSize = MultipleItem.IMG_TEXT_SPAN_SIZE;
    }

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
