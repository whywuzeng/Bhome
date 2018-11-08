package com.system.bhouse.Common.filewidget.databean;

import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ModuleAssignMent.Bean.HeaderAndFooterSectionEntity;

/**
 * Created by Administrator on 2018-11-08.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.Common.filewidget.databean
 */
public class AttachmentHeadFooter extends HeaderAndFooterSectionEntity<AttachmentFileObject> {

    public AttachmentHeadFooter(boolean isHeader, String header, boolean isFooter, String footer) {
        super(isHeader, header, isFooter, footer);
    }

    public AttachmentHeadFooter(AttachmentFileObject attachmentFileObject) {
        super(attachmentFileObject);
    }
}
