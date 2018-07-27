package com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ModuleAssignMent.Bean;

import java.io.Serializable;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public abstract class HeaderAndFooterSectionEntity<T> implements Serializable {
    public boolean isHeader;
    public boolean isFooter;
    public T t;
    public String header;
    public String Footer;

    public HeaderAndFooterSectionEntity(boolean isHeader, String header,boolean isFooter,String footer) {
        this.isHeader = isHeader;
        this.header = header;
        this.t = null;
        this.isFooter=isFooter;
        this.Footer=footer;
    }

    public HeaderAndFooterSectionEntity(T t) {
        this.isHeader = false;
        this.header = null;
        this.isFooter=false;
        this.Footer=null;
        this.t = t;
    }
}
